package com.crashlytics.android.core;

import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Environment;
import com.crashlytics.android.core.internal.models.SessionEventData;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.settings.SessionSettingsData;
import io.fabric.sdk.android.services.settings.Settings;
import io.fabric.sdk.android.services.settings.SettingsData;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class CrashlyticsUncaughtExceptionHandler
  implements Thread.UncaughtExceptionHandler
{
  private static final int ANALYZER_VERSION = 1;
  static final FilenameFilter ANY_SESSION_FILENAME_FILTER;
  static final String CLS_CRASH_MARKER_FILE_NAME = "crash_marker";
  private static final String EVENT_TYPE_CRASH = "crash";
  private static final String EVENT_TYPE_LOGGED = "error";
  private static final String GENERATOR_FORMAT = "Crashlytics Android SDK/%s";
  static final String INVALID_CLS_CACHE_DIR = "invalidClsFiles";
  static final Comparator<File> LARGEST_FILE_NAME_FIRST;
  private static final int MAX_COMPLETE_SESSIONS_COUNT = 4;
  private static final int MAX_LOCAL_LOGGED_EXCEPTIONS = 64;
  static final int MAX_OPEN_SESSIONS = 8;
  private static final Map<String, String> SEND_AT_CRASHTIME_HEADER = Collections.singletonMap("X-CRASHLYTICS-SEND-FLAGS", "1");
  static final String SESSION_APP_TAG = "SessionApp";
  static final String SESSION_BEGIN_TAG = "BeginSession";
  static final String SESSION_DEVICE_TAG = "SessionDevice";
  static final String SESSION_FATAL_TAG = "SessionCrash";
  static final FilenameFilter SESSION_FILE_FILTER = new FilenameFilter()
  {
    public boolean accept(File paramAnonymousFile, String paramAnonymousString)
    {
      return (paramAnonymousString.length() == ".cls".length() + 35) && (paramAnonymousString.endsWith(".cls"));
    }
  };
  private static final Pattern SESSION_FILE_PATTERN;
  private static final int SESSION_ID_LENGTH = 35;
  static final String SESSION_NON_FATAL_TAG = "SessionEvent";
  static final String SESSION_OS_TAG = "SessionOS";
  static final String SESSION_USER_TAG = "SessionUser";
  static final Comparator<File> SMALLEST_FILE_NAME_FIRST;
  private final CrashlyticsCore crashlyticsCore;
  private final Thread.UncaughtExceptionHandler defaultHandler;
  private final AtomicInteger eventCounter = new AtomicInteger(0);
  private final CrashlyticsExecutorServiceWrapper executorServiceWrapper;
  private final File filesDir;
  private final IdManager idManager;
  private final AtomicBoolean isHandlingException;
  private final LogFileManager logFileManager;
  private boolean powerConnected;
  private final BroadcastReceiver powerConnectedReceiver;
  private final BroadcastReceiver powerDisconnectedReceiver;
  private final AtomicBoolean receiversRegistered = new AtomicBoolean(false);
  private final SessionDataWriter sessionDataWriter;
  
  static
  {
    LARGEST_FILE_NAME_FIRST = new Comparator()
    {
      public int compare(File paramAnonymousFile1, File paramAnonymousFile2)
      {
        return paramAnonymousFile2.getName().compareTo(paramAnonymousFile1.getName());
      }
    };
    SMALLEST_FILE_NAME_FIRST = new Comparator()
    {
      public int compare(File paramAnonymousFile1, File paramAnonymousFile2)
      {
        return paramAnonymousFile1.getName().compareTo(paramAnonymousFile2.getName());
      }
    };
    ANY_SESSION_FILENAME_FILTER = new FilenameFilter()
    {
      public boolean accept(File paramAnonymousFile, String paramAnonymousString)
      {
        return CrashlyticsUncaughtExceptionHandler.SESSION_FILE_PATTERN.matcher(paramAnonymousString).matches();
      }
    };
    SESSION_FILE_PATTERN = Pattern.compile("([\\d|A-Z|a-z]{12}\\-[\\d|A-Z|a-z]{4}\\-[\\d|A-Z|a-z]{4}\\-[\\d|A-Z|a-z]{12}).+");
  }
  
  CrashlyticsUncaughtExceptionHandler(Thread.UncaughtExceptionHandler paramUncaughtExceptionHandler, CrashlyticsListener paramCrashlyticsListener, CrashlyticsExecutorServiceWrapper paramCrashlyticsExecutorServiceWrapper, IdManager paramIdManager, SessionDataWriter paramSessionDataWriter, CrashlyticsCore paramCrashlyticsCore)
  {
    this.defaultHandler = paramUncaughtExceptionHandler;
    this.executorServiceWrapper = paramCrashlyticsExecutorServiceWrapper;
    this.idManager = paramIdManager;
    this.crashlyticsCore = paramCrashlyticsCore;
    this.sessionDataWriter = paramSessionDataWriter;
    this.isHandlingException = new AtomicBoolean(false);
    this.filesDir = paramCrashlyticsCore.getSdkDirectory();
    this.logFileManager = new LogFileManager(paramCrashlyticsCore.getContext(), this.filesDir);
    notifyCrashlyticsListenerOfPreviousCrash(paramCrashlyticsListener);
    this.powerConnectedReceiver = new BroadcastReceiver()
    {
      public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
      {
        CrashlyticsUncaughtExceptionHandler.access$102(CrashlyticsUncaughtExceptionHandler.this, true);
      }
    };
    paramUncaughtExceptionHandler = new IntentFilter("android.intent.action.ACTION_POWER_CONNECTED");
    this.powerDisconnectedReceiver = new BroadcastReceiver()
    {
      public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
      {
        CrashlyticsUncaughtExceptionHandler.access$102(CrashlyticsUncaughtExceptionHandler.this, false);
      }
    };
    paramCrashlyticsListener = new IntentFilter("android.intent.action.ACTION_POWER_DISCONNECTED");
    paramCrashlyticsExecutorServiceWrapper = paramCrashlyticsCore.getContext();
    paramCrashlyticsExecutorServiceWrapper.registerReceiver(this.powerConnectedReceiver, paramUncaughtExceptionHandler);
    paramCrashlyticsExecutorServiceWrapper.registerReceiver(this.powerDisconnectedReceiver, paramCrashlyticsListener);
    this.receiversRegistered.set(true);
  }
  
  private void closeWithoutRenamingOrLog(ClsFileOutputStream paramClsFileOutputStream)
  {
    if (paramClsFileOutputStream != null) {}
    try
    {
      paramClsFileOutputStream.closeInProgressStream();
      return;
    }
    catch (IOException paramClsFileOutputStream)
    {
      Fabric.getLogger().e("CrashlyticsCore", "Error closing session file stream in the presence of an exception", paramClsFileOutputStream);
    }
  }
  
  private void deleteLegacyInvalidCacheDir()
  {
    File localFile = new File(this.crashlyticsCore.getSdkDirectory(), "invalidClsFiles");
    if (localFile.exists())
    {
      if (localFile.isDirectory())
      {
        File[] arrayOfFile = localFile.listFiles();
        int j = arrayOfFile.length;
        int i = 0;
        while (i < j)
        {
          arrayOfFile[i].delete();
          i += 1;
        }
      }
      localFile.delete();
    }
  }
  
  private void deleteSessionPartFilesFor(String paramString)
  {
    paramString = listSessionPartFilesFor(paramString);
    int j = paramString.length;
    int i = 0;
    while (i < j)
    {
      paramString[i].delete();
      i += 1;
    }
  }
  
  private void doCloseSessions(boolean paramBoolean)
    throws Exception
  {
    if (paramBoolean) {}
    for (int i = 1;; i = 0)
    {
      trimOpenSessions(i + 8);
      File[] arrayOfFile = listSessionBeginFiles();
      Arrays.sort(arrayOfFile, LARGEST_FILE_NAME_FIRST);
      if (arrayOfFile.length <= i) {
        break label170;
      }
      writeSessionUser(getSessionIdFromSessionFile(arrayOfFile[i]));
      Object localObject = this.crashlyticsCore.getSessionSettingsData();
      if (localObject == null) {
        break;
      }
      int j = ((SessionSettingsData)localObject).maxCustomExceptionEvents;
      Fabric.getLogger().d("CrashlyticsCore", "Closing open sessions.");
      while (i < arrayOfFile.length)
      {
        localObject = arrayOfFile[i];
        String str = getSessionIdFromSessionFile((File)localObject);
        Fabric.getLogger().d("CrashlyticsCore", "Closing session: " + str);
        writeSessionPartsToSessionFile((File)localObject, str, j);
        i += 1;
      }
    }
    Fabric.getLogger().d("CrashlyticsCore", "Unable to close session. Settings are not loaded.");
    return;
    label170:
    Fabric.getLogger().d("CrashlyticsCore", "No open sessions to be closed.");
  }
  
  private void doOpenSession()
    throws Exception
  {
    Date localDate = new Date();
    String str = new CLSUUID(this.idManager).toString();
    Fabric.getLogger().d("CrashlyticsCore", "Opening an new session with ID " + str);
    writeBeginSession(str, localDate);
    writeSessionApp(str);
    writeSessionOS(str);
    writeSessionDevice(str);
    this.logFileManager.setCurrentSession(str);
  }
  
  /* Error */
  private void doWriteNonFatal(Date paramDate, Thread paramThread, Throwable paramThrowable)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 297	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:getCurrentSessionId	()Ljava/lang/String;
    //   4: astore 12
    //   6: aload 12
    //   8: ifnull +308 -> 316
    //   11: aload 12
    //   13: invokestatic 437	com/crashlytics/android/core/CrashlyticsCore:recordLoggedExceptionEvent	(Ljava/lang/String;)V
    //   16: aconst_null
    //   17: astore 6
    //   19: aconst_null
    //   20: astore 8
    //   22: aconst_null
    //   23: astore 11
    //   25: aconst_null
    //   26: astore 10
    //   28: aconst_null
    //   29: astore 9
    //   31: aconst_null
    //   32: astore 7
    //   34: aload 11
    //   36: astore 4
    //   38: aload 6
    //   40: astore 5
    //   42: invokestatic 318	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   45: ldc_w 320
    //   48: new 389	java/lang/StringBuilder
    //   51: dup
    //   52: invokespecial 390	java/lang/StringBuilder:<init>	()V
    //   55: ldc_w 439
    //   58: invokevirtual 396	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   61: aload_3
    //   62: invokevirtual 442	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   65: ldc_w 444
    //   68: invokevirtual 396	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   71: aload_2
    //   72: invokevirtual 449	java/lang/Thread:getName	()Ljava/lang/String;
    //   75: invokevirtual 396	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   78: invokevirtual 399	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   81: invokeinterface 387 3 0
    //   86: aload 11
    //   88: astore 4
    //   90: aload 6
    //   92: astore 5
    //   94: aload_0
    //   95: getfield 179	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:eventCounter	Ljava/util/concurrent/atomic/AtomicInteger;
    //   98: invokevirtual 453	java/util/concurrent/atomic/AtomicInteger:getAndIncrement	()I
    //   101: invokestatic 459	io/fabric/sdk/android/services/common/CommonUtils:padWithZerosToMaxIntWidth	(I)Ljava/lang/String;
    //   104: astore 13
    //   106: aload 11
    //   108: astore 4
    //   110: aload 6
    //   112: astore 5
    //   114: new 389	java/lang/StringBuilder
    //   117: dup
    //   118: invokespecial 390	java/lang/StringBuilder:<init>	()V
    //   121: aload 12
    //   123: invokevirtual 396	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   126: ldc 100
    //   128: invokevirtual 396	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   131: aload 13
    //   133: invokevirtual 396	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   136: invokevirtual 399	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   139: astore 13
    //   141: aload 11
    //   143: astore 4
    //   145: aload 6
    //   147: astore 5
    //   149: new 309	com/crashlytics/android/core/ClsFileOutputStream
    //   152: dup
    //   153: aload_0
    //   154: getfield 206	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:filesDir	Ljava/io/File;
    //   157: aload 13
    //   159: invokespecial 460	com/crashlytics/android/core/ClsFileOutputStream:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   162: astore 6
    //   164: aload 10
    //   166: astore 4
    //   168: aload 9
    //   170: astore 5
    //   172: aload 6
    //   174: invokestatic 466	com/crashlytics/android/core/CodedOutputStream:newInstance	(Ljava/io/OutputStream;)Lcom/crashlytics/android/core/CodedOutputStream;
    //   177: astore 7
    //   179: aload 7
    //   181: astore 4
    //   183: aload 7
    //   185: astore 5
    //   187: aload_0
    //   188: aload 7
    //   190: aload_1
    //   191: aload_2
    //   192: aload_3
    //   193: ldc 62
    //   195: iconst_0
    //   196: invokespecial 470	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:writeSessionEvent	(Lcom/crashlytics/android/core/CodedOutputStream;Ljava/util/Date;Ljava/lang/Thread;Ljava/lang/Throwable;Ljava/lang/String;Z)V
    //   199: aload 7
    //   201: ldc_w 472
    //   204: invokestatic 476	io/fabric/sdk/android/services/common/CommonUtils:flushOrLog	(Ljava/io/Flushable;Ljava/lang/String;)V
    //   207: aload 6
    //   209: ldc_w 478
    //   212: invokestatic 482	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   215: aload_0
    //   216: aload 12
    //   218: bipush 64
    //   220: invokespecial 486	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:trimSessionEventFiles	(Ljava/lang/String;I)V
    //   223: return
    //   224: astore_3
    //   225: aload 8
    //   227: astore_2
    //   228: aload 7
    //   230: astore_1
    //   231: aload_1
    //   232: astore 4
    //   234: aload_2
    //   235: astore 5
    //   237: invokestatic 318	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   240: ldc_w 320
    //   243: ldc_w 488
    //   246: aload_3
    //   247: invokeinterface 328 4 0
    //   252: aload_1
    //   253: astore 4
    //   255: aload_2
    //   256: astore 5
    //   258: aload_3
    //   259: aload_2
    //   260: invokestatic 494	com/crashlytics/android/core/ExceptionUtils:writeStackTraceIfNotNull	(Ljava/lang/Throwable;Ljava/io/OutputStream;)V
    //   263: aload_1
    //   264: ldc_w 472
    //   267: invokestatic 476	io/fabric/sdk/android/services/common/CommonUtils:flushOrLog	(Ljava/io/Flushable;Ljava/lang/String;)V
    //   270: aload_2
    //   271: ldc_w 478
    //   274: invokestatic 482	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   277: goto -62 -> 215
    //   280: astore_1
    //   281: aload 4
    //   283: ldc_w 472
    //   286: invokestatic 476	io/fabric/sdk/android/services/common/CommonUtils:flushOrLog	(Ljava/io/Flushable;Ljava/lang/String;)V
    //   289: aload 5
    //   291: ldc_w 478
    //   294: invokestatic 482	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   297: aload_1
    //   298: athrow
    //   299: astore_1
    //   300: invokestatic 318	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   303: ldc_w 320
    //   306: ldc_w 496
    //   309: aload_1
    //   310: invokeinterface 328 4 0
    //   315: return
    //   316: invokestatic 318	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   319: ldc_w 320
    //   322: ldc_w 498
    //   325: aconst_null
    //   326: invokeinterface 328 4 0
    //   331: return
    //   332: astore_1
    //   333: aload 6
    //   335: astore 5
    //   337: goto -56 -> 281
    //   340: astore_3
    //   341: aload 6
    //   343: astore_2
    //   344: aload 5
    //   346: astore_1
    //   347: goto -116 -> 231
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	350	0	this	CrashlyticsUncaughtExceptionHandler
    //   0	350	1	paramDate	Date
    //   0	350	2	paramThread	Thread
    //   0	350	3	paramThrowable	Throwable
    //   36	246	4	localObject1	Object
    //   40	305	5	localObject2	Object
    //   17	325	6	localClsFileOutputStream	ClsFileOutputStream
    //   32	197	7	localCodedOutputStream	CodedOutputStream
    //   20	206	8	localObject3	Object
    //   29	140	9	localObject4	Object
    //   26	139	10	localObject5	Object
    //   23	119	11	localObject6	Object
    //   4	213	12	str1	String
    //   104	54	13	str2	String
    // Exception table:
    //   from	to	target	type
    //   42	86	224	java/lang/Exception
    //   94	106	224	java/lang/Exception
    //   114	141	224	java/lang/Exception
    //   149	164	224	java/lang/Exception
    //   42	86	280	finally
    //   94	106	280	finally
    //   114	141	280	finally
    //   149	164	280	finally
    //   237	252	280	finally
    //   258	263	280	finally
    //   215	223	299	java/lang/Exception
    //   172	179	332	finally
    //   187	199	332	finally
    //   172	179	340	java/lang/Exception
    //   187	199	340	java/lang/Exception
  }
  
  private File[] ensureFileArrayNotNull(File[] paramArrayOfFile)
  {
    File[] arrayOfFile = paramArrayOfFile;
    if (paramArrayOfFile == null) {
      arrayOfFile = new File[0];
    }
    return arrayOfFile;
  }
  
  private String getCurrentSessionId()
  {
    File[] arrayOfFile = listSessionBeginFiles();
    Arrays.sort(arrayOfFile, LARGEST_FILE_NAME_FIRST);
    if (arrayOfFile.length > 0) {
      return getSessionIdFromSessionFile(arrayOfFile[0]);
    }
    return null;
  }
  
  private String getPreviousSessionId()
  {
    File[] arrayOfFile = listSessionBeginFiles();
    Arrays.sort(arrayOfFile, LARGEST_FILE_NAME_FIRST);
    if (arrayOfFile.length > 1) {
      return getSessionIdFromSessionFile(arrayOfFile[1]);
    }
    return null;
  }
  
  private String getSessionIdFromSessionFile(File paramFile)
  {
    return paramFile.getName().substring(0, 35);
  }
  
  private UserMetaData getUserMetaData(String paramString)
  {
    if (isHandlingException()) {
      return new UserMetaData(this.crashlyticsCore.getUserIdentifier(), this.crashlyticsCore.getUserName(), this.crashlyticsCore.getUserEmail());
    }
    return new MetaDataStore(this.filesDir).readUserData(paramString);
  }
  
  private void handleUncaughtException(Date paramDate, Thread paramThread, Throwable paramThrowable)
    throws Exception
  {
    writeFatal(paramDate, paramThread, paramThrowable);
    doCloseSessions();
    doOpenSession();
    trimSessionFiles();
    if (!this.crashlyticsCore.shouldPromptUserBeforeSendingCrashReports()) {
      sendSessionReports();
    }
  }
  
  private File[] listCompleteSessionFiles()
  {
    return listFilesMatching(SESSION_FILE_FILTER);
  }
  
  private File[] listFilesMatching(FilenameFilter paramFilenameFilter)
  {
    return ensureFileArrayNotNull(this.filesDir.listFiles(paramFilenameFilter));
  }
  
  private File[] listSessionPartFilesFor(String paramString)
  {
    return listFilesMatching(new SessionPartFileFilter(paramString));
  }
  
  private void notifyCrashlyticsListenerOfPreviousCrash(CrashlyticsListener paramCrashlyticsListener)
  {
    Fabric.getLogger().d("CrashlyticsCore", "Checking for previous crash marker.");
    File localFile = new File(this.crashlyticsCore.getSdkDirectory(), "crash_marker");
    if (localFile.exists())
    {
      localFile.delete();
      if (paramCrashlyticsListener == null) {}
    }
    try
    {
      paramCrashlyticsListener.crashlyticsDidDetectCrashDuringPreviousExecution();
      return;
    }
    catch (Exception paramCrashlyticsListener)
    {
      Fabric.getLogger().e("CrashlyticsCore", "Exception thrown by CrashlyticsListener while notifying of previous crash.", paramCrashlyticsListener);
    }
  }
  
  private void sendSessionReports()
  {
    File[] arrayOfFile = listCompleteSessionFiles();
    int j = arrayOfFile.length;
    int i = 0;
    while (i < j)
    {
      final File localFile = arrayOfFile[i];
      this.executorServiceWrapper.executeAsync(new Runnable()
      {
        public void run()
        {
          if (CommonUtils.canTryConnection(CrashlyticsUncaughtExceptionHandler.this.crashlyticsCore.getContext()))
          {
            Fabric.getLogger().d("CrashlyticsCore", "Attempting to send crash report at time of crash...");
            Object localObject = Settings.getInstance().awaitSettingsData();
            localObject = CrashlyticsUncaughtExceptionHandler.this.crashlyticsCore.getCreateReportSpiCall((SettingsData)localObject);
            if (localObject != null) {
              new ReportUploader((CreateReportSpiCall)localObject).forceUpload(new SessionReport(localFile, CrashlyticsUncaughtExceptionHandler.SEND_AT_CRASHTIME_HEADER));
            }
          }
        }
      });
      i += 1;
    }
  }
  
  private void trimOpenSessions(int paramInt)
  {
    HashSet localHashSet = new HashSet();
    File[] arrayOfFile = listSessionBeginFiles();
    Arrays.sort(arrayOfFile, LARGEST_FILE_NAME_FIRST);
    int i = Math.min(paramInt, arrayOfFile.length);
    paramInt = 0;
    while (paramInt < i)
    {
      localHashSet.add(getSessionIdFromSessionFile(arrayOfFile[paramInt]));
      paramInt += 1;
    }
    this.logFileManager.discardOldLogFiles(localHashSet);
    arrayOfFile = listFilesMatching(new AnySessionPartFileFilter(null));
    i = arrayOfFile.length;
    paramInt = 0;
    while (paramInt < i)
    {
      File localFile = arrayOfFile[paramInt];
      String str = localFile.getName();
      Matcher localMatcher = SESSION_FILE_PATTERN.matcher(str);
      localMatcher.matches();
      if (!localHashSet.contains(localMatcher.group(1)))
      {
        Fabric.getLogger().d("CrashlyticsCore", "Trimming open session file: " + str);
        localFile.delete();
      }
      paramInt += 1;
    }
  }
  
  private void trimSessionEventFiles(String paramString, int paramInt)
  {
    Utils.capFileCount(this.filesDir, new FileNameContainsFilter(paramString + "SessionEvent"), paramInt, SMALLEST_FILE_NAME_FIRST);
  }
  
  /* Error */
  private void writeBeginSession(String paramString, Date paramDate)
    throws Exception
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 6
    //   3: aconst_null
    //   4: astore 9
    //   6: aconst_null
    //   7: astore 5
    //   9: aconst_null
    //   10: astore 11
    //   12: aconst_null
    //   13: astore 10
    //   15: aconst_null
    //   16: astore 8
    //   18: new 309	com/crashlytics/android/core/ClsFileOutputStream
    //   21: dup
    //   22: aload_0
    //   23: getfield 206	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:filesDir	Ljava/io/File;
    //   26: new 389	java/lang/StringBuilder
    //   29: dup
    //   30: invokespecial 390	java/lang/StringBuilder:<init>	()V
    //   33: aload_1
    //   34: invokevirtual 396	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   37: ldc 86
    //   39: invokevirtual 396	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   42: invokevirtual 399	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   45: invokespecial 460	com/crashlytics/android/core/ClsFileOutputStream:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   48: astore 7
    //   50: aload 11
    //   52: astore 5
    //   54: aload 10
    //   56: astore 6
    //   58: aload 7
    //   60: invokestatic 466	com/crashlytics/android/core/CodedOutputStream:newInstance	(Ljava/io/OutputStream;)Lcom/crashlytics/android/core/CodedOutputStream;
    //   63: astore 8
    //   65: aload 8
    //   67: astore 5
    //   69: aload 8
    //   71: astore 6
    //   73: getstatic 626	java/util/Locale:US	Ljava/util/Locale;
    //   76: ldc 65
    //   78: iconst_1
    //   79: anewarray 4	java/lang/Object
    //   82: dup
    //   83: iconst_0
    //   84: aload_0
    //   85: getfield 194	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:crashlyticsCore	Lcom/crashlytics/android/core/CrashlyticsCore;
    //   88: invokevirtual 629	com/crashlytics/android/core/CrashlyticsCore:getVersion	()Ljava/lang/String;
    //   91: aastore
    //   92: invokestatic 633	java/lang/String:format	(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   95: astore 9
    //   97: aload 8
    //   99: astore 5
    //   101: aload 8
    //   103: astore 6
    //   105: aload_2
    //   106: invokevirtual 637	java/util/Date:getTime	()J
    //   109: ldc2_w 638
    //   112: ldiv
    //   113: lstore_3
    //   114: aload 8
    //   116: astore 5
    //   118: aload 8
    //   120: astore 6
    //   122: aload_0
    //   123: getfield 196	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:sessionDataWriter	Lcom/crashlytics/android/core/SessionDataWriter;
    //   126: aload 8
    //   128: aload_1
    //   129: aload 9
    //   131: lload_3
    //   132: invokevirtual 644	com/crashlytics/android/core/SessionDataWriter:writeBeginSession	(Lcom/crashlytics/android/core/CodedOutputStream;Ljava/lang/String;Ljava/lang/String;J)V
    //   135: aload 8
    //   137: ldc_w 646
    //   140: invokestatic 476	io/fabric/sdk/android/services/common/CommonUtils:flushOrLog	(Ljava/io/Flushable;Ljava/lang/String;)V
    //   143: aload 7
    //   145: ldc_w 648
    //   148: invokestatic 482	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   151: return
    //   152: astore 7
    //   154: aload 9
    //   156: astore_2
    //   157: aload 8
    //   159: astore_1
    //   160: aload_1
    //   161: astore 5
    //   163: aload_2
    //   164: astore 6
    //   166: aload 7
    //   168: aload_2
    //   169: invokestatic 494	com/crashlytics/android/core/ExceptionUtils:writeStackTraceIfNotNull	(Ljava/lang/Throwable;Ljava/io/OutputStream;)V
    //   172: aload_1
    //   173: astore 5
    //   175: aload_2
    //   176: astore 6
    //   178: aload 7
    //   180: athrow
    //   181: astore_1
    //   182: aload 5
    //   184: ldc_w 646
    //   187: invokestatic 476	io/fabric/sdk/android/services/common/CommonUtils:flushOrLog	(Ljava/io/Flushable;Ljava/lang/String;)V
    //   190: aload 6
    //   192: ldc_w 648
    //   195: invokestatic 482	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   198: aload_1
    //   199: athrow
    //   200: astore_1
    //   201: aload 7
    //   203: astore 6
    //   205: goto -23 -> 182
    //   208: astore 5
    //   210: aload 7
    //   212: astore_2
    //   213: aload 6
    //   215: astore_1
    //   216: aload 5
    //   218: astore 7
    //   220: goto -60 -> 160
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	223	0	this	CrashlyticsUncaughtExceptionHandler
    //   0	223	1	paramString	String
    //   0	223	2	paramDate	Date
    //   113	19	3	l	long
    //   7	176	5	localObject1	Object
    //   208	9	5	localException1	Exception
    //   1	213	6	localObject2	Object
    //   48	96	7	localClsFileOutputStream	ClsFileOutputStream
    //   152	59	7	localException2	Exception
    //   218	1	7	localException3	Exception
    //   16	142	8	localCodedOutputStream	CodedOutputStream
    //   4	151	9	str	String
    //   13	42	10	localObject3	Object
    //   10	41	11	localObject4	Object
    // Exception table:
    //   from	to	target	type
    //   18	50	152	java/lang/Exception
    //   18	50	181	finally
    //   166	172	181	finally
    //   178	181	181	finally
    //   58	65	200	finally
    //   73	97	200	finally
    //   105	114	200	finally
    //   122	135	200	finally
    //   58	65	208	java/lang/Exception
    //   73	97	208	java/lang/Exception
    //   105	114	208	java/lang/Exception
    //   122	135	208	java/lang/Exception
  }
  
  /* Error */
  private void writeExternalCrashEvent(SessionEventData paramSessionEventData)
    throws IOException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 6
    //   3: aconst_null
    //   4: astore 10
    //   6: aconst_null
    //   7: astore 4
    //   9: aconst_null
    //   10: astore 5
    //   12: aconst_null
    //   13: astore 11
    //   15: aconst_null
    //   16: astore 8
    //   18: aconst_null
    //   19: astore 7
    //   21: aconst_null
    //   22: astore 9
    //   24: aload 11
    //   26: astore_2
    //   27: aload 10
    //   29: astore_3
    //   30: aload_0
    //   31: invokespecial 650	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:getPreviousSessionId	()Ljava/lang/String;
    //   34: astore 12
    //   36: aload 12
    //   38: ifnull +139 -> 177
    //   41: aload 11
    //   43: astore_2
    //   44: aload 10
    //   46: astore_3
    //   47: aload 12
    //   49: invokestatic 653	com/crashlytics/android/core/CrashlyticsCore:recordFatalExceptionEvent	(Ljava/lang/String;)V
    //   52: aload 11
    //   54: astore_2
    //   55: aload 10
    //   57: astore_3
    //   58: new 309	com/crashlytics/android/core/ClsFileOutputStream
    //   61: dup
    //   62: aload_0
    //   63: getfield 206	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:filesDir	Ljava/io/File;
    //   66: new 389	java/lang/StringBuilder
    //   69: dup
    //   70: invokespecial 390	java/lang/StringBuilder:<init>	()V
    //   73: aload 12
    //   75: invokevirtual 396	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   78: ldc 92
    //   80: invokevirtual 396	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   83: invokevirtual 399	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   86: invokespecial 460	com/crashlytics/android/core/ClsFileOutputStream:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   89: astore 4
    //   91: aload 8
    //   93: astore_2
    //   94: aload 7
    //   96: astore 5
    //   98: aload 4
    //   100: invokestatic 466	com/crashlytics/android/core/CodedOutputStream:newInstance	(Ljava/io/OutputStream;)Lcom/crashlytics/android/core/CodedOutputStream;
    //   103: astore_3
    //   104: aload_3
    //   105: astore_2
    //   106: aload_3
    //   107: astore 5
    //   109: new 528	com/crashlytics/android/core/MetaDataStore
    //   112: dup
    //   113: aload_0
    //   114: getfield 206	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:filesDir	Ljava/io/File;
    //   117: invokespecial 531	com/crashlytics/android/core/MetaDataStore:<init>	(Ljava/io/File;)V
    //   120: aload 12
    //   122: invokevirtual 657	com/crashlytics/android/core/MetaDataStore:readKeyData	(Ljava/lang/String;)Ljava/util/Map;
    //   125: astore 6
    //   127: aload_3
    //   128: astore_2
    //   129: aload_3
    //   130: astore 5
    //   132: aload_1
    //   133: new 208	com/crashlytics/android/core/LogFileManager
    //   136: dup
    //   137: aload_0
    //   138: getfield 194	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:crashlyticsCore	Lcom/crashlytics/android/core/CrashlyticsCore;
    //   141: invokevirtual 212	com/crashlytics/android/core/CrashlyticsCore:getContext	()Landroid/content/Context;
    //   144: aload_0
    //   145: getfield 206	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:filesDir	Ljava/io/File;
    //   148: aload 12
    //   150: invokespecial 660	com/crashlytics/android/core/LogFileManager:<init>	(Landroid/content/Context;Ljava/io/File;Ljava/lang/String;)V
    //   153: aload 6
    //   155: aload_3
    //   156: invokestatic 666	com/crashlytics/android/core/NativeCrashWriter:writeNativeCrash	(Lcom/crashlytics/android/core/internal/models/SessionEventData;Lcom/crashlytics/android/core/LogFileManager;Ljava/util/Map;Lcom/crashlytics/android/core/CodedOutputStream;)V
    //   159: aload 4
    //   161: astore_1
    //   162: aload_3
    //   163: ldc_w 646
    //   166: invokestatic 476	io/fabric/sdk/android/services/common/CommonUtils:flushOrLog	(Ljava/io/Flushable;Ljava/lang/String;)V
    //   169: aload_1
    //   170: ldc_w 668
    //   173: invokestatic 482	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   176: return
    //   177: aload 11
    //   179: astore_2
    //   180: aload 10
    //   182: astore_3
    //   183: invokestatic 318	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   186: ldc_w 320
    //   189: ldc_w 670
    //   192: aconst_null
    //   193: invokeinterface 328 4 0
    //   198: aload 9
    //   200: astore_3
    //   201: aload 4
    //   203: astore_1
    //   204: goto -42 -> 162
    //   207: astore 4
    //   209: aload 6
    //   211: astore_1
    //   212: aload 5
    //   214: astore_2
    //   215: aload_1
    //   216: astore_3
    //   217: invokestatic 318	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   220: ldc_w 320
    //   223: ldc_w 672
    //   226: aload 4
    //   228: invokeinterface 328 4 0
    //   233: aload 5
    //   235: astore_2
    //   236: aload_1
    //   237: astore_3
    //   238: aload 4
    //   240: aload_1
    //   241: invokestatic 494	com/crashlytics/android/core/ExceptionUtils:writeStackTraceIfNotNull	(Ljava/lang/Throwable;Ljava/io/OutputStream;)V
    //   244: aload 5
    //   246: ldc_w 646
    //   249: invokestatic 476	io/fabric/sdk/android/services/common/CommonUtils:flushOrLog	(Ljava/io/Flushable;Ljava/lang/String;)V
    //   252: aload_1
    //   253: ldc_w 668
    //   256: invokestatic 482	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   259: return
    //   260: astore_1
    //   261: aload_2
    //   262: ldc_w 646
    //   265: invokestatic 476	io/fabric/sdk/android/services/common/CommonUtils:flushOrLog	(Ljava/io/Flushable;Ljava/lang/String;)V
    //   268: aload_3
    //   269: ldc_w 668
    //   272: invokestatic 482	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   275: aload_1
    //   276: athrow
    //   277: astore_1
    //   278: aload 4
    //   280: astore_3
    //   281: goto -20 -> 261
    //   284: astore_2
    //   285: aload 4
    //   287: astore_1
    //   288: aload_2
    //   289: astore 4
    //   291: goto -79 -> 212
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	294	0	this	CrashlyticsUncaughtExceptionHandler
    //   0	294	1	paramSessionEventData	SessionEventData
    //   26	236	2	localObject1	Object
    //   284	5	2	localException1	Exception
    //   29	252	3	localObject2	Object
    //   7	195	4	localClsFileOutputStream	ClsFileOutputStream
    //   207	79	4	localException2	Exception
    //   289	1	4	localObject3	Object
    //   10	235	5	localObject4	Object
    //   1	209	6	localMap	Map
    //   19	76	7	localObject5	Object
    //   16	76	8	localObject6	Object
    //   22	177	9	localObject7	Object
    //   4	177	10	localObject8	Object
    //   13	165	11	localObject9	Object
    //   34	115	12	str	String
    // Exception table:
    //   from	to	target	type
    //   30	36	207	java/lang/Exception
    //   47	52	207	java/lang/Exception
    //   58	91	207	java/lang/Exception
    //   183	198	207	java/lang/Exception
    //   30	36	260	finally
    //   47	52	260	finally
    //   58	91	260	finally
    //   183	198	260	finally
    //   217	233	260	finally
    //   238	244	260	finally
    //   98	104	277	finally
    //   109	127	277	finally
    //   132	159	277	finally
    //   98	104	284	java/lang/Exception
    //   109	127	284	java/lang/Exception
    //   132	159	284	java/lang/Exception
  }
  
  /* Error */
  private void writeFatal(Date paramDate, Thread paramThread, Throwable paramThrowable)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 8
    //   3: aconst_null
    //   4: astore 12
    //   6: aconst_null
    //   7: astore 6
    //   9: aconst_null
    //   10: astore 7
    //   12: aconst_null
    //   13: astore 13
    //   15: aconst_null
    //   16: astore 10
    //   18: aconst_null
    //   19: astore 9
    //   21: aconst_null
    //   22: astore 11
    //   24: aload 13
    //   26: astore 4
    //   28: aload 12
    //   30: astore 5
    //   32: new 331	java/io/File
    //   35: dup
    //   36: aload_0
    //   37: getfield 206	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:filesDir	Ljava/io/File;
    //   40: ldc 56
    //   42: invokespecial 334	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   45: invokevirtual 675	java/io/File:createNewFile	()Z
    //   48: pop
    //   49: aload 13
    //   51: astore 4
    //   53: aload 12
    //   55: astore 5
    //   57: aload_0
    //   58: invokespecial 297	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:getCurrentSessionId	()Ljava/lang/String;
    //   61: astore 14
    //   63: aload 14
    //   65: ifnull +111 -> 176
    //   68: aload 13
    //   70: astore 4
    //   72: aload 12
    //   74: astore 5
    //   76: aload 14
    //   78: invokestatic 653	com/crashlytics/android/core/CrashlyticsCore:recordFatalExceptionEvent	(Ljava/lang/String;)V
    //   81: aload 13
    //   83: astore 4
    //   85: aload 12
    //   87: astore 5
    //   89: new 309	com/crashlytics/android/core/ClsFileOutputStream
    //   92: dup
    //   93: aload_0
    //   94: getfield 206	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:filesDir	Ljava/io/File;
    //   97: new 389	java/lang/StringBuilder
    //   100: dup
    //   101: invokespecial 390	java/lang/StringBuilder:<init>	()V
    //   104: aload 14
    //   106: invokevirtual 396	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   109: ldc 92
    //   111: invokevirtual 396	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   114: invokevirtual 399	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   117: invokespecial 460	com/crashlytics/android/core/ClsFileOutputStream:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   120: astore 6
    //   122: aload 10
    //   124: astore 4
    //   126: aload 9
    //   128: astore 7
    //   130: aload 6
    //   132: invokestatic 466	com/crashlytics/android/core/CodedOutputStream:newInstance	(Ljava/io/OutputStream;)Lcom/crashlytics/android/core/CodedOutputStream;
    //   135: astore 5
    //   137: aload 5
    //   139: astore 4
    //   141: aload 5
    //   143: astore 7
    //   145: aload_0
    //   146: aload 5
    //   148: aload_1
    //   149: aload_2
    //   150: aload_3
    //   151: ldc 59
    //   153: iconst_1
    //   154: invokespecial 470	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:writeSessionEvent	(Lcom/crashlytics/android/core/CodedOutputStream;Ljava/util/Date;Ljava/lang/Thread;Ljava/lang/Throwable;Ljava/lang/String;Z)V
    //   157: aload 6
    //   159: astore_1
    //   160: aload 5
    //   162: ldc_w 646
    //   165: invokestatic 476	io/fabric/sdk/android/services/common/CommonUtils:flushOrLog	(Ljava/io/Flushable;Ljava/lang/String;)V
    //   168: aload_1
    //   169: ldc_w 668
    //   172: invokestatic 482	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   175: return
    //   176: aload 13
    //   178: astore 4
    //   180: aload 12
    //   182: astore 5
    //   184: invokestatic 318	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   187: ldc_w 320
    //   190: ldc_w 677
    //   193: aconst_null
    //   194: invokeinterface 328 4 0
    //   199: aload 11
    //   201: astore 5
    //   203: aload 6
    //   205: astore_1
    //   206: goto -46 -> 160
    //   209: astore_2
    //   210: aload 8
    //   212: astore_1
    //   213: aload 7
    //   215: astore 4
    //   217: aload_1
    //   218: astore 5
    //   220: invokestatic 318	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   223: ldc_w 320
    //   226: ldc_w 679
    //   229: aload_2
    //   230: invokeinterface 328 4 0
    //   235: aload 7
    //   237: astore 4
    //   239: aload_1
    //   240: astore 5
    //   242: aload_2
    //   243: aload_1
    //   244: invokestatic 494	com/crashlytics/android/core/ExceptionUtils:writeStackTraceIfNotNull	(Ljava/lang/Throwable;Ljava/io/OutputStream;)V
    //   247: aload 7
    //   249: ldc_w 646
    //   252: invokestatic 476	io/fabric/sdk/android/services/common/CommonUtils:flushOrLog	(Ljava/io/Flushable;Ljava/lang/String;)V
    //   255: aload_1
    //   256: ldc_w 668
    //   259: invokestatic 482	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   262: return
    //   263: astore_1
    //   264: aload 4
    //   266: ldc_w 646
    //   269: invokestatic 476	io/fabric/sdk/android/services/common/CommonUtils:flushOrLog	(Ljava/io/Flushable;Ljava/lang/String;)V
    //   272: aload 5
    //   274: ldc_w 668
    //   277: invokestatic 482	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   280: aload_1
    //   281: athrow
    //   282: astore_1
    //   283: aload 6
    //   285: astore 5
    //   287: goto -23 -> 264
    //   290: astore_2
    //   291: aload 6
    //   293: astore_1
    //   294: goto -81 -> 213
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	297	0	this	CrashlyticsUncaughtExceptionHandler
    //   0	297	1	paramDate	Date
    //   0	297	2	paramThread	Thread
    //   0	297	3	paramThrowable	Throwable
    //   26	239	4	localObject1	Object
    //   30	256	5	localObject2	Object
    //   7	285	6	localClsFileOutputStream	ClsFileOutputStream
    //   10	238	7	localObject3	Object
    //   1	210	8	localObject4	Object
    //   19	108	9	localObject5	Object
    //   16	107	10	localObject6	Object
    //   22	178	11	localObject7	Object
    //   4	177	12	localObject8	Object
    //   13	164	13	localObject9	Object
    //   61	44	14	str	String
    // Exception table:
    //   from	to	target	type
    //   32	49	209	java/lang/Exception
    //   57	63	209	java/lang/Exception
    //   76	81	209	java/lang/Exception
    //   89	122	209	java/lang/Exception
    //   184	199	209	java/lang/Exception
    //   32	49	263	finally
    //   57	63	263	finally
    //   76	81	263	finally
    //   89	122	263	finally
    //   184	199	263	finally
    //   220	235	263	finally
    //   242	247	263	finally
    //   130	137	282	finally
    //   145	157	282	finally
    //   130	137	290	java/lang/Exception
    //   145	157	290	java/lang/Exception
  }
  
  private void writeInitialPartsTo(CodedOutputStream paramCodedOutputStream, String paramString)
    throws IOException
  {
    String[] arrayOfString = new String[4];
    arrayOfString[0] = "SessionUser";
    arrayOfString[1] = "SessionApp";
    arrayOfString[2] = "SessionOS";
    arrayOfString[3] = "SessionDevice";
    int j = arrayOfString.length;
    int i = 0;
    if (i < j)
    {
      String str = arrayOfString[i];
      File[] arrayOfFile = listFilesMatching(new FileNameContainsFilter(paramString + str));
      if (arrayOfFile.length == 0) {
        Fabric.getLogger().e("CrashlyticsCore", "Can't find " + str + " data for session ID " + paramString, null);
      }
      for (;;)
      {
        i += 1;
        break;
        Fabric.getLogger().d("CrashlyticsCore", "Collecting " + str + " data for session ID " + paramString);
        writeToCosFromFile(paramCodedOutputStream, arrayOfFile[0]);
      }
    }
  }
  
  private void writeNonFatalEventsTo(CodedOutputStream paramCodedOutputStream, File[] paramArrayOfFile, String paramString)
  {
    Arrays.sort(paramArrayOfFile, CommonUtils.FILE_MODIFIED_COMPARATOR);
    int j = paramArrayOfFile.length;
    int i = 0;
    for (;;)
    {
      if (i < j)
      {
        File localFile = paramArrayOfFile[i];
        try
        {
          Fabric.getLogger().d("CrashlyticsCore", String.format(Locale.US, "Found Non Fatal for session ID %s in %s ", new Object[] { paramString, localFile.getName() }));
          writeToCosFromFile(paramCodedOutputStream, localFile);
          i += 1;
        }
        catch (Exception localException)
        {
          for (;;)
          {
            Fabric.getLogger().e("CrashlyticsCore", "Error writting non-fatal to session.", localException);
          }
        }
      }
    }
  }
  
  /* Error */
  private void writeSessionApp(String paramString)
    throws Exception
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 4
    //   3: aconst_null
    //   4: astore 6
    //   6: aconst_null
    //   7: astore_3
    //   8: aconst_null
    //   9: astore 9
    //   11: aconst_null
    //   12: astore 8
    //   14: aconst_null
    //   15: astore 7
    //   17: new 309	com/crashlytics/android/core/ClsFileOutputStream
    //   20: dup
    //   21: aload_0
    //   22: getfield 206	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:filesDir	Ljava/io/File;
    //   25: new 389	java/lang/StringBuilder
    //   28: dup
    //   29: invokespecial 390	java/lang/StringBuilder:<init>	()V
    //   32: aload_1
    //   33: invokevirtual 396	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   36: ldc 83
    //   38: invokevirtual 396	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   41: invokevirtual 399	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   44: invokespecial 460	com/crashlytics/android/core/ClsFileOutputStream:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   47: astore 5
    //   49: aload 9
    //   51: astore_1
    //   52: aload 8
    //   54: astore_3
    //   55: aload 5
    //   57: invokestatic 466	com/crashlytics/android/core/CodedOutputStream:newInstance	(Ljava/io/OutputStream;)Lcom/crashlytics/android/core/CodedOutputStream;
    //   60: astore 4
    //   62: aload 4
    //   64: astore_1
    //   65: aload 4
    //   67: astore_3
    //   68: aload_0
    //   69: getfield 194	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:crashlyticsCore	Lcom/crashlytics/android/core/CrashlyticsCore;
    //   72: invokevirtual 703	com/crashlytics/android/core/CrashlyticsCore:getPackageName	()Ljava/lang/String;
    //   75: astore 6
    //   77: aload 4
    //   79: astore_1
    //   80: aload 4
    //   82: astore_3
    //   83: aload_0
    //   84: getfield 194	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:crashlyticsCore	Lcom/crashlytics/android/core/CrashlyticsCore;
    //   87: invokevirtual 706	com/crashlytics/android/core/CrashlyticsCore:getVersionCode	()Ljava/lang/String;
    //   90: astore 7
    //   92: aload 4
    //   94: astore_1
    //   95: aload 4
    //   97: astore_3
    //   98: aload_0
    //   99: getfield 194	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:crashlyticsCore	Lcom/crashlytics/android/core/CrashlyticsCore;
    //   102: invokevirtual 709	com/crashlytics/android/core/CrashlyticsCore:getVersionName	()Ljava/lang/String;
    //   105: astore 8
    //   107: aload 4
    //   109: astore_1
    //   110: aload 4
    //   112: astore_3
    //   113: aload_0
    //   114: getfield 192	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:idManager	Lio/fabric/sdk/android/services/common/IdManager;
    //   117: invokevirtual 714	io/fabric/sdk/android/services/common/IdManager:getAppInstallIdentifier	()Ljava/lang/String;
    //   120: astore 9
    //   122: aload 4
    //   124: astore_1
    //   125: aload 4
    //   127: astore_3
    //   128: aload_0
    //   129: getfield 194	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:crashlyticsCore	Lcom/crashlytics/android/core/CrashlyticsCore;
    //   132: invokevirtual 717	com/crashlytics/android/core/CrashlyticsCore:getInstallerPackageName	()Ljava/lang/String;
    //   135: invokestatic 723	io/fabric/sdk/android/services/common/DeliveryMechanism:determineFrom	(Ljava/lang/String;)Lio/fabric/sdk/android/services/common/DeliveryMechanism;
    //   138: invokevirtual 726	io/fabric/sdk/android/services/common/DeliveryMechanism:getId	()I
    //   141: istore_2
    //   142: aload 4
    //   144: astore_1
    //   145: aload 4
    //   147: astore_3
    //   148: aload_0
    //   149: getfield 196	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:sessionDataWriter	Lcom/crashlytics/android/core/SessionDataWriter;
    //   152: aload 4
    //   154: aload 6
    //   156: aload 7
    //   158: aload 8
    //   160: aload 9
    //   162: iload_2
    //   163: invokevirtual 729	com/crashlytics/android/core/SessionDataWriter:writeSessionApp	(Lcom/crashlytics/android/core/CodedOutputStream;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)V
    //   166: aload 4
    //   168: ldc_w 731
    //   171: invokestatic 476	io/fabric/sdk/android/services/common/CommonUtils:flushOrLog	(Ljava/io/Flushable;Ljava/lang/String;)V
    //   174: aload 5
    //   176: ldc_w 733
    //   179: invokestatic 482	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   182: return
    //   183: astore_1
    //   184: aload 6
    //   186: astore 5
    //   188: aload_1
    //   189: astore 6
    //   191: aload 7
    //   193: astore_1
    //   194: aload_1
    //   195: astore_3
    //   196: aload 5
    //   198: astore 4
    //   200: aload 6
    //   202: aload 5
    //   204: invokestatic 494	com/crashlytics/android/core/ExceptionUtils:writeStackTraceIfNotNull	(Ljava/lang/Throwable;Ljava/io/OutputStream;)V
    //   207: aload_1
    //   208: astore_3
    //   209: aload 5
    //   211: astore 4
    //   213: aload 6
    //   215: athrow
    //   216: astore 5
    //   218: aload_3
    //   219: astore_1
    //   220: aload 5
    //   222: astore_3
    //   223: aload_1
    //   224: ldc_w 731
    //   227: invokestatic 476	io/fabric/sdk/android/services/common/CommonUtils:flushOrLog	(Ljava/io/Flushable;Ljava/lang/String;)V
    //   230: aload 4
    //   232: ldc_w 733
    //   235: invokestatic 482	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   238: aload_3
    //   239: athrow
    //   240: astore_3
    //   241: aload 5
    //   243: astore 4
    //   245: goto -22 -> 223
    //   248: astore 6
    //   250: aload_3
    //   251: astore_1
    //   252: goto -58 -> 194
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	255	0	this	CrashlyticsUncaughtExceptionHandler
    //   0	255	1	paramString	String
    //   141	22	2	i	int
    //   7	232	3	localObject1	Object
    //   240	11	3	localObject2	Object
    //   1	243	4	localObject3	Object
    //   47	163	5	localObject4	Object
    //   216	26	5	localObject5	Object
    //   4	210	6	str1	String
    //   248	1	6	localException	Exception
    //   15	177	7	str2	String
    //   12	147	8	str3	String
    //   9	152	9	str4	String
    // Exception table:
    //   from	to	target	type
    //   17	49	183	java/lang/Exception
    //   17	49	216	finally
    //   200	207	216	finally
    //   213	216	216	finally
    //   55	62	240	finally
    //   68	77	240	finally
    //   83	92	240	finally
    //   98	107	240	finally
    //   113	122	240	finally
    //   128	142	240	finally
    //   148	166	240	finally
    //   55	62	248	java/lang/Exception
    //   68	77	248	java/lang/Exception
    //   83	92	248	java/lang/Exception
    //   98	107	248	java/lang/Exception
    //   113	122	248	java/lang/Exception
    //   128	142	248	java/lang/Exception
    //   148	166	248	java/lang/Exception
  }
  
  /* Error */
  private void writeSessionDevice(String paramString)
    throws Exception
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 13
    //   3: aconst_null
    //   4: astore 15
    //   6: aconst_null
    //   7: astore 12
    //   9: aconst_null
    //   10: astore 18
    //   12: aconst_null
    //   13: astore 17
    //   15: aconst_null
    //   16: astore 16
    //   18: new 309	com/crashlytics/android/core/ClsFileOutputStream
    //   21: dup
    //   22: aload_0
    //   23: getfield 206	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:filesDir	Ljava/io/File;
    //   26: new 389	java/lang/StringBuilder
    //   29: dup
    //   30: invokespecial 390	java/lang/StringBuilder:<init>	()V
    //   33: aload_1
    //   34: invokevirtual 396	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   37: ldc 89
    //   39: invokevirtual 396	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   42: invokevirtual 399	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   45: invokespecial 460	com/crashlytics/android/core/ClsFileOutputStream:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   48: astore 14
    //   50: aload 18
    //   52: astore_1
    //   53: aload 17
    //   55: astore 12
    //   57: aload 14
    //   59: invokestatic 466	com/crashlytics/android/core/CodedOutputStream:newInstance	(Ljava/io/OutputStream;)Lcom/crashlytics/android/core/CodedOutputStream;
    //   62: astore 13
    //   64: aload 13
    //   66: astore_1
    //   67: aload 13
    //   69: astore 12
    //   71: aload_0
    //   72: getfield 194	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:crashlyticsCore	Lcom/crashlytics/android/core/CrashlyticsCore;
    //   75: invokevirtual 212	com/crashlytics/android/core/CrashlyticsCore:getContext	()Landroid/content/Context;
    //   78: astore 15
    //   80: aload 13
    //   82: astore_1
    //   83: aload 13
    //   85: astore 12
    //   87: new 735	android/os/StatFs
    //   90: dup
    //   91: invokestatic 740	android/os/Environment:getDataDirectory	()Ljava/io/File;
    //   94: invokevirtual 743	java/io/File:getPath	()Ljava/lang/String;
    //   97: invokespecial 744	android/os/StatFs:<init>	(Ljava/lang/String;)V
    //   100: astore 17
    //   102: aload 13
    //   104: astore_1
    //   105: aload 13
    //   107: astore 12
    //   109: aload_0
    //   110: getfield 192	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:idManager	Lio/fabric/sdk/android/services/common/IdManager;
    //   113: invokevirtual 747	io/fabric/sdk/android/services/common/IdManager:getDeviceUUID	()Ljava/lang/String;
    //   116: astore 16
    //   118: aload 13
    //   120: astore_1
    //   121: aload 13
    //   123: astore 12
    //   125: invokestatic 750	io/fabric/sdk/android/services/common/CommonUtils:getCpuArchitectureInt	()I
    //   128: istore_2
    //   129: aload 13
    //   131: astore_1
    //   132: aload 13
    //   134: astore 12
    //   136: invokestatic 756	java/lang/Runtime:getRuntime	()Ljava/lang/Runtime;
    //   139: invokevirtual 759	java/lang/Runtime:availableProcessors	()I
    //   142: istore_3
    //   143: aload 13
    //   145: astore_1
    //   146: aload 13
    //   148: astore 12
    //   150: invokestatic 762	io/fabric/sdk/android/services/common/CommonUtils:getTotalRamInBytes	()J
    //   153: lstore 5
    //   155: aload 13
    //   157: astore_1
    //   158: aload 13
    //   160: astore 12
    //   162: aload 17
    //   164: invokevirtual 765	android/os/StatFs:getBlockCount	()I
    //   167: i2l
    //   168: lstore 7
    //   170: aload 13
    //   172: astore_1
    //   173: aload 13
    //   175: astore 12
    //   177: aload 17
    //   179: invokevirtual 768	android/os/StatFs:getBlockSize	()I
    //   182: i2l
    //   183: lstore 9
    //   185: aload 13
    //   187: astore_1
    //   188: aload 13
    //   190: astore 12
    //   192: aload 15
    //   194: invokestatic 772	io/fabric/sdk/android/services/common/CommonUtils:isEmulator	(Landroid/content/Context;)Z
    //   197: istore 11
    //   199: aload 13
    //   201: astore_1
    //   202: aload 13
    //   204: astore 12
    //   206: aload_0
    //   207: getfield 192	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:idManager	Lio/fabric/sdk/android/services/common/IdManager;
    //   210: invokevirtual 775	io/fabric/sdk/android/services/common/IdManager:getDeviceIdentifiers	()Ljava/util/Map;
    //   213: astore 17
    //   215: aload 13
    //   217: astore_1
    //   218: aload 13
    //   220: astore 12
    //   222: aload 15
    //   224: invokestatic 779	io/fabric/sdk/android/services/common/CommonUtils:getDeviceState	(Landroid/content/Context;)I
    //   227: istore 4
    //   229: aload 13
    //   231: astore_1
    //   232: aload 13
    //   234: astore 12
    //   236: aload_0
    //   237: getfield 196	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:sessionDataWriter	Lcom/crashlytics/android/core/SessionDataWriter;
    //   240: aload 13
    //   242: aload 16
    //   244: iload_2
    //   245: getstatic 784	android/os/Build:MODEL	Ljava/lang/String;
    //   248: iload_3
    //   249: lload 5
    //   251: lload 7
    //   253: lload 9
    //   255: lmul
    //   256: iload 11
    //   258: aload 17
    //   260: iload 4
    //   262: getstatic 787	android/os/Build:MANUFACTURER	Ljava/lang/String;
    //   265: getstatic 790	android/os/Build:PRODUCT	Ljava/lang/String;
    //   268: invokevirtual 793	com/crashlytics/android/core/SessionDataWriter:writeSessionDevice	(Lcom/crashlytics/android/core/CodedOutputStream;Ljava/lang/String;ILjava/lang/String;IJJZLjava/util/Map;ILjava/lang/String;Ljava/lang/String;)V
    //   271: aload 13
    //   273: ldc_w 795
    //   276: invokestatic 476	io/fabric/sdk/android/services/common/CommonUtils:flushOrLog	(Ljava/io/Flushable;Ljava/lang/String;)V
    //   279: aload 14
    //   281: ldc_w 797
    //   284: invokestatic 482	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   287: return
    //   288: astore_1
    //   289: aload 15
    //   291: astore 14
    //   293: aload_1
    //   294: astore 15
    //   296: aload 16
    //   298: astore_1
    //   299: aload_1
    //   300: astore 12
    //   302: aload 14
    //   304: astore 13
    //   306: aload 15
    //   308: aload 14
    //   310: invokestatic 494	com/crashlytics/android/core/ExceptionUtils:writeStackTraceIfNotNull	(Ljava/lang/Throwable;Ljava/io/OutputStream;)V
    //   313: aload_1
    //   314: astore 12
    //   316: aload 14
    //   318: astore 13
    //   320: aload 15
    //   322: athrow
    //   323: astore 14
    //   325: aload 12
    //   327: astore_1
    //   328: aload 14
    //   330: astore 12
    //   332: aload_1
    //   333: ldc_w 795
    //   336: invokestatic 476	io/fabric/sdk/android/services/common/CommonUtils:flushOrLog	(Ljava/io/Flushable;Ljava/lang/String;)V
    //   339: aload 13
    //   341: ldc_w 797
    //   344: invokestatic 482	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   347: aload 12
    //   349: athrow
    //   350: astore 12
    //   352: aload 14
    //   354: astore 13
    //   356: goto -24 -> 332
    //   359: astore 15
    //   361: aload 12
    //   363: astore_1
    //   364: goto -65 -> 299
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	367	0	this	CrashlyticsUncaughtExceptionHandler
    //   0	367	1	paramString	String
    //   128	117	2	i	int
    //   142	107	3	j	int
    //   227	34	4	k	int
    //   153	97	5	l1	long
    //   168	84	7	l2	long
    //   183	71	9	l3	long
    //   197	60	11	bool	boolean
    //   7	341	12	localObject1	Object
    //   350	12	12	localObject2	Object
    //   1	354	13	localObject3	Object
    //   48	269	14	localObject4	Object
    //   323	30	14	localObject5	Object
    //   4	317	15	localObject6	Object
    //   359	1	15	localException	Exception
    //   16	281	16	str	String
    //   13	246	17	localObject7	Object
    //   10	41	18	localObject8	Object
    // Exception table:
    //   from	to	target	type
    //   18	50	288	java/lang/Exception
    //   18	50	323	finally
    //   306	313	323	finally
    //   320	323	323	finally
    //   57	64	350	finally
    //   71	80	350	finally
    //   87	102	350	finally
    //   109	118	350	finally
    //   125	129	350	finally
    //   136	143	350	finally
    //   150	155	350	finally
    //   162	170	350	finally
    //   177	185	350	finally
    //   192	199	350	finally
    //   206	215	350	finally
    //   222	229	350	finally
    //   236	271	350	finally
    //   57	64	359	java/lang/Exception
    //   71	80	359	java/lang/Exception
    //   87	102	359	java/lang/Exception
    //   109	118	359	java/lang/Exception
    //   125	129	359	java/lang/Exception
    //   136	143	359	java/lang/Exception
    //   150	155	359	java/lang/Exception
    //   162	170	359	java/lang/Exception
    //   177	185	359	java/lang/Exception
    //   192	199	359	java/lang/Exception
    //   206	215	359	java/lang/Exception
    //   222	229	359	java/lang/Exception
    //   236	271	359	java/lang/Exception
  }
  
  private void writeSessionEvent(CodedOutputStream paramCodedOutputStream, Date paramDate, Thread paramThread, Throwable paramThrowable, String paramString, boolean paramBoolean)
    throws Exception
  {
    Object localObject2 = this.crashlyticsCore.getContext();
    long l1 = paramDate.getTime() / 1000L;
    float f = CommonUtils.getBatteryLevel((Context)localObject2);
    int j = CommonUtils.getBatteryVelocity((Context)localObject2, this.powerConnected);
    boolean bool = CommonUtils.getProximitySensorEnabled((Context)localObject2);
    int k = ((Context)localObject2).getResources().getConfiguration().orientation;
    long l2 = CommonUtils.getTotalRamInBytes();
    long l3 = CommonUtils.calculateFreeRamInBytes((Context)localObject2);
    long l4 = CommonUtils.calculateUsedDiskSpaceInBytes(Environment.getDataDirectory().getPath());
    ActivityManager.RunningAppProcessInfo localRunningAppProcessInfo = CommonUtils.getAppProcessInfo(((Context)localObject2).getPackageName(), (Context)localObject2);
    LinkedList localLinkedList = new LinkedList();
    StackTraceElement[] arrayOfStackTraceElement = paramThrowable.getStackTrace();
    if (paramBoolean)
    {
      localObject1 = Thread.getAllStackTraces();
      paramDate = new Thread[((Map)localObject1).size()];
      int i = 0;
      Iterator localIterator = ((Map)localObject1).entrySet().iterator();
      for (;;)
      {
        localObject1 = paramDate;
        if (!localIterator.hasNext()) {
          break;
        }
        localObject1 = (Map.Entry)localIterator.next();
        paramDate[i] = ((Thread)((Map.Entry)localObject1).getKey());
        localLinkedList.add(((Map.Entry)localObject1).getValue());
        i += 1;
      }
    }
    Object localObject1 = new Thread[0];
    if (!CommonUtils.getBooleanResourceValue((Context)localObject2, "com.crashlytics.CollectCustomKeys", true)) {
      paramDate = new TreeMap();
    }
    for (;;)
    {
      this.sessionDataWriter.writeSessionEvent(paramCodedOutputStream, l1, paramThread, paramThrowable, paramString, (Thread[])localObject1, f, j, bool, k, l2 - l3, l4, localRunningAppProcessInfo, localLinkedList, arrayOfStackTraceElement, this.logFileManager, paramDate);
      return;
      localObject2 = this.crashlyticsCore.getAttributes();
      paramDate = (Date)localObject2;
      if (localObject2 != null)
      {
        paramDate = (Date)localObject2;
        if (((Map)localObject2).size() > 1) {
          paramDate = new TreeMap((Map)localObject2);
        }
      }
    }
  }
  
  /* Error */
  private void writeSessionOS(String paramString)
    throws Exception
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 4
    //   3: aconst_null
    //   4: astore 6
    //   6: aconst_null
    //   7: astore_3
    //   8: aconst_null
    //   9: astore 9
    //   11: aconst_null
    //   12: astore 8
    //   14: aconst_null
    //   15: astore 7
    //   17: new 309	com/crashlytics/android/core/ClsFileOutputStream
    //   20: dup
    //   21: aload_0
    //   22: getfield 206	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:filesDir	Ljava/io/File;
    //   25: new 389	java/lang/StringBuilder
    //   28: dup
    //   29: invokespecial 390	java/lang/StringBuilder:<init>	()V
    //   32: aload_1
    //   33: invokevirtual 396	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   36: ldc 103
    //   38: invokevirtual 396	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   41: invokevirtual 399	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   44: invokespecial 460	com/crashlytics/android/core/ClsFileOutputStream:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   47: astore 5
    //   49: aload 9
    //   51: astore_1
    //   52: aload 8
    //   54: astore_3
    //   55: aload 5
    //   57: invokestatic 466	com/crashlytics/android/core/CodedOutputStream:newInstance	(Ljava/io/OutputStream;)Lcom/crashlytics/android/core/CodedOutputStream;
    //   60: astore 4
    //   62: aload 4
    //   64: astore_1
    //   65: aload 4
    //   67: astore_3
    //   68: aload_0
    //   69: getfield 194	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:crashlyticsCore	Lcom/crashlytics/android/core/CrashlyticsCore;
    //   72: invokevirtual 212	com/crashlytics/android/core/CrashlyticsCore:getContext	()Landroid/content/Context;
    //   75: invokestatic 902	io/fabric/sdk/android/services/common/CommonUtils:isRooted	(Landroid/content/Context;)Z
    //   78: istore_2
    //   79: aload 4
    //   81: astore_1
    //   82: aload 4
    //   84: astore_3
    //   85: aload_0
    //   86: getfield 196	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:sessionDataWriter	Lcom/crashlytics/android/core/SessionDataWriter;
    //   89: aload 4
    //   91: iload_2
    //   92: invokevirtual 905	com/crashlytics/android/core/SessionDataWriter:writeSessionOS	(Lcom/crashlytics/android/core/CodedOutputStream;Z)V
    //   95: aload 4
    //   97: ldc_w 907
    //   100: invokestatic 476	io/fabric/sdk/android/services/common/CommonUtils:flushOrLog	(Ljava/io/Flushable;Ljava/lang/String;)V
    //   103: aload 5
    //   105: ldc_w 909
    //   108: invokestatic 482	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   111: return
    //   112: astore_1
    //   113: aload 6
    //   115: astore 5
    //   117: aload_1
    //   118: astore 6
    //   120: aload 7
    //   122: astore_1
    //   123: aload_1
    //   124: astore_3
    //   125: aload 5
    //   127: astore 4
    //   129: aload 6
    //   131: aload 5
    //   133: invokestatic 494	com/crashlytics/android/core/ExceptionUtils:writeStackTraceIfNotNull	(Ljava/lang/Throwable;Ljava/io/OutputStream;)V
    //   136: aload_1
    //   137: astore_3
    //   138: aload 5
    //   140: astore 4
    //   142: aload 6
    //   144: athrow
    //   145: astore 5
    //   147: aload_3
    //   148: astore_1
    //   149: aload_1
    //   150: ldc_w 907
    //   153: invokestatic 476	io/fabric/sdk/android/services/common/CommonUtils:flushOrLog	(Ljava/io/Flushable;Ljava/lang/String;)V
    //   156: aload 4
    //   158: ldc_w 909
    //   161: invokestatic 482	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   164: aload 5
    //   166: athrow
    //   167: astore_3
    //   168: aload 5
    //   170: astore 4
    //   172: aload_3
    //   173: astore 5
    //   175: goto -26 -> 149
    //   178: astore 6
    //   180: aload_3
    //   181: astore_1
    //   182: goto -59 -> 123
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	185	0	this	CrashlyticsUncaughtExceptionHandler
    //   0	185	1	paramString	String
    //   78	14	2	bool	boolean
    //   7	141	3	localObject1	Object
    //   167	14	3	localObject2	Object
    //   1	170	4	localObject3	Object
    //   47	92	5	localObject4	Object
    //   145	24	5	localObject5	Object
    //   173	1	5	localObject6	Object
    //   4	139	6	str	String
    //   178	1	6	localException	Exception
    //   15	106	7	localObject7	Object
    //   12	41	8	localObject8	Object
    //   9	41	9	localObject9	Object
    // Exception table:
    //   from	to	target	type
    //   17	49	112	java/lang/Exception
    //   17	49	145	finally
    //   129	136	145	finally
    //   142	145	145	finally
    //   55	62	167	finally
    //   68	79	167	finally
    //   85	95	167	finally
    //   55	62	178	java/lang/Exception
    //   68	79	178	java/lang/Exception
    //   85	95	178	java/lang/Exception
  }
  
  /* Error */
  private void writeSessionPartsToSessionFile(File paramFile, String paramString, int paramInt)
  {
    // Byte code:
    //   0: invokestatic 318	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   3: ldc_w 320
    //   6: new 389	java/lang/StringBuilder
    //   9: dup
    //   10: invokespecial 390	java/lang/StringBuilder:<init>	()V
    //   13: ldc_w 911
    //   16: invokevirtual 396	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   19: aload_2
    //   20: invokevirtual 396	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   23: invokevirtual 399	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   26: invokeinterface 387 3 0
    //   31: aload_0
    //   32: new 43	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler$FileNameContainsFilter
    //   35: dup
    //   36: new 389	java/lang/StringBuilder
    //   39: dup
    //   40: invokespecial 390	java/lang/StringBuilder:<init>	()V
    //   43: aload_2
    //   44: invokevirtual 396	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   47: ldc 92
    //   49: invokevirtual 396	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   52: invokevirtual 399	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   55: invokespecial 614	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler$FileNameContainsFilter:<init>	(Ljava/lang/String;)V
    //   58: invokespecial 275	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:listFilesMatching	(Ljava/io/FilenameFilter;)[Ljava/io/File;
    //   61: astore 14
    //   63: aload 14
    //   65: ifnull +541 -> 606
    //   68: aload 14
    //   70: arraylength
    //   71: ifle +535 -> 606
    //   74: iconst_1
    //   75: istore 4
    //   77: invokestatic 318	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   80: ldc_w 320
    //   83: getstatic 626	java/util/Locale:US	Ljava/util/Locale;
    //   86: ldc_w 913
    //   89: iconst_2
    //   90: anewarray 4	java/lang/Object
    //   93: dup
    //   94: iconst_0
    //   95: aload_2
    //   96: aastore
    //   97: dup
    //   98: iconst_1
    //   99: iload 4
    //   101: invokestatic 919	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   104: aastore
    //   105: invokestatic 633	java/lang/String:format	(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   108: invokeinterface 387 3 0
    //   113: aload_0
    //   114: new 43	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler$FileNameContainsFilter
    //   117: dup
    //   118: new 389	java/lang/StringBuilder
    //   121: dup
    //   122: invokespecial 390	java/lang/StringBuilder:<init>	()V
    //   125: aload_2
    //   126: invokevirtual 396	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   129: ldc 100
    //   131: invokevirtual 396	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   134: invokevirtual 399	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   137: invokespecial 614	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler$FileNameContainsFilter:<init>	(Ljava/lang/String;)V
    //   140: invokespecial 275	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:listFilesMatching	(Ljava/io/FilenameFilter;)[Ljava/io/File;
    //   143: astore 10
    //   145: aload 10
    //   147: ifnull +465 -> 612
    //   150: aload 10
    //   152: arraylength
    //   153: ifle +459 -> 612
    //   156: iconst_1
    //   157: istore 5
    //   159: invokestatic 318	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   162: ldc_w 320
    //   165: getstatic 626	java/util/Locale:US	Ljava/util/Locale;
    //   168: ldc_w 921
    //   171: iconst_2
    //   172: anewarray 4	java/lang/Object
    //   175: dup
    //   176: iconst_0
    //   177: aload_2
    //   178: aastore
    //   179: dup
    //   180: iconst_1
    //   181: iload 5
    //   183: invokestatic 919	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   186: aastore
    //   187: invokestatic 633	java/lang/String:format	(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   190: invokeinterface 387 3 0
    //   195: iload 4
    //   197: ifne +8 -> 205
    //   200: iload 5
    //   202: ifeq +555 -> 757
    //   205: aconst_null
    //   206: astore 7
    //   208: aconst_null
    //   209: astore 9
    //   211: aconst_null
    //   212: astore 6
    //   214: aconst_null
    //   215: astore 13
    //   217: aconst_null
    //   218: astore 12
    //   220: aconst_null
    //   221: astore 11
    //   223: new 309	com/crashlytics/android/core/ClsFileOutputStream
    //   226: dup
    //   227: aload_0
    //   228: getfield 206	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:filesDir	Ljava/io/File;
    //   231: aload_2
    //   232: invokespecial 460	com/crashlytics/android/core/ClsFileOutputStream:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   235: astore 8
    //   237: aload 13
    //   239: astore 6
    //   241: aload 12
    //   243: astore 7
    //   245: aload 8
    //   247: invokestatic 466	com/crashlytics/android/core/CodedOutputStream:newInstance	(Ljava/io/OutputStream;)Lcom/crashlytics/android/core/CodedOutputStream;
    //   250: astore 9
    //   252: aload 9
    //   254: astore 6
    //   256: aload 9
    //   258: astore 7
    //   260: invokestatic 318	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   263: ldc_w 320
    //   266: new 389	java/lang/StringBuilder
    //   269: dup
    //   270: invokespecial 390	java/lang/StringBuilder:<init>	()V
    //   273: ldc_w 923
    //   276: invokevirtual 396	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   279: aload_2
    //   280: invokevirtual 396	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   283: invokevirtual 399	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   286: invokeinterface 387 3 0
    //   291: aload 9
    //   293: astore 6
    //   295: aload 9
    //   297: astore 7
    //   299: aload_0
    //   300: aload 9
    //   302: aload_1
    //   303: invokespecial 691	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:writeToCosFromFile	(Lcom/crashlytics/android/core/CodedOutputStream;Ljava/io/File;)V
    //   306: aload 9
    //   308: astore 6
    //   310: aload 9
    //   312: astore 7
    //   314: aload 9
    //   316: iconst_4
    //   317: new 409	java/util/Date
    //   320: dup
    //   321: invokespecial 410	java/util/Date:<init>	()V
    //   324: invokevirtual 637	java/util/Date:getTime	()J
    //   327: ldc2_w 638
    //   330: ldiv
    //   331: invokevirtual 927	com/crashlytics/android/core/CodedOutputStream:writeUInt64	(IJ)V
    //   334: aload 9
    //   336: astore 6
    //   338: aload 9
    //   340: astore 7
    //   342: aload 9
    //   344: iconst_5
    //   345: iload 4
    //   347: invokevirtual 931	com/crashlytics/android/core/CodedOutputStream:writeBool	(IZ)V
    //   350: aload 9
    //   352: astore 6
    //   354: aload 9
    //   356: astore 7
    //   358: aload_0
    //   359: aload 9
    //   361: aload_2
    //   362: invokespecial 933	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:writeInitialPartsTo	(Lcom/crashlytics/android/core/CodedOutputStream;Ljava/lang/String;)V
    //   365: iload 5
    //   367: ifeq +129 -> 496
    //   370: aload 10
    //   372: astore_1
    //   373: aload 9
    //   375: astore 6
    //   377: aload 9
    //   379: astore 7
    //   381: aload 10
    //   383: arraylength
    //   384: iload_3
    //   385: if_icmple +95 -> 480
    //   388: aload 9
    //   390: astore 6
    //   392: aload 9
    //   394: astore 7
    //   396: invokestatic 318	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   399: ldc_w 320
    //   402: getstatic 626	java/util/Locale:US	Ljava/util/Locale;
    //   405: ldc_w 935
    //   408: iconst_1
    //   409: anewarray 4	java/lang/Object
    //   412: dup
    //   413: iconst_0
    //   414: iload_3
    //   415: invokestatic 940	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   418: aastore
    //   419: invokestatic 633	java/lang/String:format	(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   422: invokeinterface 387 3 0
    //   427: aload 9
    //   429: astore 6
    //   431: aload 9
    //   433: astore 7
    //   435: aload_0
    //   436: aload_2
    //   437: iload_3
    //   438: invokespecial 486	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:trimSessionEventFiles	(Ljava/lang/String;I)V
    //   441: aload 9
    //   443: astore 6
    //   445: aload 9
    //   447: astore 7
    //   449: aload_0
    //   450: new 43	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler$FileNameContainsFilter
    //   453: dup
    //   454: new 389	java/lang/StringBuilder
    //   457: dup
    //   458: invokespecial 390	java/lang/StringBuilder:<init>	()V
    //   461: aload_2
    //   462: invokevirtual 396	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   465: ldc 100
    //   467: invokevirtual 396	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   470: invokevirtual 399	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   473: invokespecial 614	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler$FileNameContainsFilter:<init>	(Ljava/lang/String;)V
    //   476: invokespecial 275	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:listFilesMatching	(Ljava/io/FilenameFilter;)[Ljava/io/File;
    //   479: astore_1
    //   480: aload 9
    //   482: astore 6
    //   484: aload 9
    //   486: astore 7
    //   488: aload_0
    //   489: aload 9
    //   491: aload_1
    //   492: aload_2
    //   493: invokespecial 942	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:writeNonFatalEventsTo	(Lcom/crashlytics/android/core/CodedOutputStream;[Ljava/io/File;Ljava/lang/String;)V
    //   496: iload 4
    //   498: ifeq +21 -> 519
    //   501: aload 9
    //   503: astore 6
    //   505: aload 9
    //   507: astore 7
    //   509: aload_0
    //   510: aload 9
    //   512: aload 14
    //   514: iconst_0
    //   515: aaload
    //   516: invokespecial 691	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:writeToCosFromFile	(Lcom/crashlytics/android/core/CodedOutputStream;Ljava/io/File;)V
    //   519: aload 9
    //   521: astore 6
    //   523: aload 9
    //   525: astore 7
    //   527: aload 9
    //   529: bipush 11
    //   531: iconst_1
    //   532: invokevirtual 946	com/crashlytics/android/core/CodedOutputStream:writeUInt32	(II)V
    //   535: aload 9
    //   537: astore 6
    //   539: aload 9
    //   541: astore 7
    //   543: aload 9
    //   545: bipush 12
    //   547: iconst_3
    //   548: invokevirtual 949	com/crashlytics/android/core/CodedOutputStream:writeEnum	(II)V
    //   551: aload 9
    //   553: ldc_w 951
    //   556: invokestatic 476	io/fabric/sdk/android/services/common/CommonUtils:flushOrLog	(Ljava/io/Flushable;Ljava/lang/String;)V
    //   559: iconst_0
    //   560: ifeq +58 -> 618
    //   563: aload_0
    //   564: aload 8
    //   566: invokespecial 953	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:closeWithoutRenamingOrLog	(Lcom/crashlytics/android/core/ClsFileOutputStream;)V
    //   569: invokestatic 318	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   572: ldc_w 320
    //   575: new 389	java/lang/StringBuilder
    //   578: dup
    //   579: invokespecial 390	java/lang/StringBuilder:<init>	()V
    //   582: ldc_w 955
    //   585: invokevirtual 396	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   588: aload_2
    //   589: invokevirtual 396	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   592: invokevirtual 399	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   595: invokeinterface 387 3 0
    //   600: aload_0
    //   601: aload_2
    //   602: invokespecial 957	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:deleteSessionPartFilesFor	(Ljava/lang/String;)V
    //   605: return
    //   606: iconst_0
    //   607: istore 4
    //   609: goto -532 -> 77
    //   612: iconst_0
    //   613: istore 5
    //   615: goto -456 -> 159
    //   618: aload 8
    //   620: ldc_w 959
    //   623: invokestatic 482	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   626: goto -57 -> 569
    //   629: astore_1
    //   630: aload 9
    //   632: astore 8
    //   634: aload_1
    //   635: astore 9
    //   637: aload 11
    //   639: astore_1
    //   640: aload_1
    //   641: astore 6
    //   643: aload 8
    //   645: astore 7
    //   647: invokestatic 318	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   650: ldc_w 320
    //   653: new 389	java/lang/StringBuilder
    //   656: dup
    //   657: invokespecial 390	java/lang/StringBuilder:<init>	()V
    //   660: ldc_w 961
    //   663: invokevirtual 396	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   666: aload_2
    //   667: invokevirtual 396	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   670: invokevirtual 399	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   673: aload 9
    //   675: invokeinterface 328 4 0
    //   680: aload_1
    //   681: astore 6
    //   683: aload 8
    //   685: astore 7
    //   687: aload 9
    //   689: aload 8
    //   691: invokestatic 494	com/crashlytics/android/core/ExceptionUtils:writeStackTraceIfNotNull	(Ljava/lang/Throwable;Ljava/io/OutputStream;)V
    //   694: aload_1
    //   695: ldc_w 951
    //   698: invokestatic 476	io/fabric/sdk/android/services/common/CommonUtils:flushOrLog	(Ljava/io/Flushable;Ljava/lang/String;)V
    //   701: iconst_1
    //   702: ifeq +12 -> 714
    //   705: aload_0
    //   706: aload 8
    //   708: invokespecial 953	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:closeWithoutRenamingOrLog	(Lcom/crashlytics/android/core/ClsFileOutputStream;)V
    //   711: goto -142 -> 569
    //   714: aload 8
    //   716: ldc_w 959
    //   719: invokestatic 482	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   722: goto -153 -> 569
    //   725: astore_1
    //   726: aload 6
    //   728: ldc_w 951
    //   731: invokestatic 476	io/fabric/sdk/android/services/common/CommonUtils:flushOrLog	(Ljava/io/Flushable;Ljava/lang/String;)V
    //   734: iconst_0
    //   735: ifeq +11 -> 746
    //   738: aload_0
    //   739: aload 7
    //   741: invokespecial 953	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:closeWithoutRenamingOrLog	(Lcom/crashlytics/android/core/ClsFileOutputStream;)V
    //   744: aload_1
    //   745: athrow
    //   746: aload 7
    //   748: ldc_w 959
    //   751: invokestatic 482	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   754: goto -10 -> 744
    //   757: invokestatic 318	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   760: ldc_w 320
    //   763: new 389	java/lang/StringBuilder
    //   766: dup
    //   767: invokespecial 390	java/lang/StringBuilder:<init>	()V
    //   770: ldc_w 963
    //   773: invokevirtual 396	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   776: aload_2
    //   777: invokevirtual 396	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   780: invokevirtual 399	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   783: invokeinterface 387 3 0
    //   788: goto -219 -> 569
    //   791: astore_1
    //   792: aload 8
    //   794: astore 7
    //   796: goto -70 -> 726
    //   799: astore 9
    //   801: aload 7
    //   803: astore_1
    //   804: goto -164 -> 640
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	807	0	this	CrashlyticsUncaughtExceptionHandler
    //   0	807	1	paramFile	File
    //   0	807	2	paramString	String
    //   0	807	3	paramInt	int
    //   75	533	4	bool1	boolean
    //   157	457	5	bool2	boolean
    //   212	515	6	localObject1	Object
    //   206	596	7	localObject2	Object
    //   235	558	8	localObject3	Object
    //   209	479	9	localObject4	Object
    //   799	1	9	localException	Exception
    //   143	239	10	arrayOfFile1	File[]
    //   221	417	11	localObject5	Object
    //   218	24	12	localObject6	Object
    //   215	23	13	localObject7	Object
    //   61	452	14	arrayOfFile2	File[]
    // Exception table:
    //   from	to	target	type
    //   223	237	629	java/lang/Exception
    //   223	237	725	finally
    //   647	680	725	finally
    //   687	694	725	finally
    //   245	252	791	finally
    //   260	291	791	finally
    //   299	306	791	finally
    //   314	334	791	finally
    //   342	350	791	finally
    //   358	365	791	finally
    //   381	388	791	finally
    //   396	427	791	finally
    //   435	441	791	finally
    //   449	480	791	finally
    //   488	496	791	finally
    //   509	519	791	finally
    //   527	535	791	finally
    //   543	551	791	finally
    //   245	252	799	java/lang/Exception
    //   260	291	799	java/lang/Exception
    //   299	306	799	java/lang/Exception
    //   314	334	799	java/lang/Exception
    //   342	350	799	java/lang/Exception
    //   358	365	799	java/lang/Exception
    //   381	388	799	java/lang/Exception
    //   396	427	799	java/lang/Exception
    //   435	441	799	java/lang/Exception
    //   449	480	799	java/lang/Exception
    //   488	496	799	java/lang/Exception
    //   509	519	799	java/lang/Exception
    //   527	535	799	java/lang/Exception
    //   543	551	799	java/lang/Exception
  }
  
  /* Error */
  private void writeSessionUser(String paramString)
    throws Exception
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 4
    //   3: aconst_null
    //   4: astore 6
    //   6: aconst_null
    //   7: astore_3
    //   8: aconst_null
    //   9: astore 9
    //   11: aconst_null
    //   12: astore 8
    //   14: aconst_null
    //   15: astore 7
    //   17: new 309	com/crashlytics/android/core/ClsFileOutputStream
    //   20: dup
    //   21: aload_0
    //   22: getfield 206	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:filesDir	Ljava/io/File;
    //   25: new 389	java/lang/StringBuilder
    //   28: dup
    //   29: invokespecial 390	java/lang/StringBuilder:<init>	()V
    //   32: aload_1
    //   33: invokevirtual 396	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   36: ldc 106
    //   38: invokevirtual 396	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   41: invokevirtual 399	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   44: invokespecial 460	com/crashlytics/android/core/ClsFileOutputStream:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   47: astore 5
    //   49: aload 9
    //   51: astore_3
    //   52: aload 8
    //   54: astore 4
    //   56: aload 5
    //   58: invokestatic 466	com/crashlytics/android/core/CodedOutputStream:newInstance	(Ljava/io/OutputStream;)Lcom/crashlytics/android/core/CodedOutputStream;
    //   61: astore 6
    //   63: aload 6
    //   65: astore_3
    //   66: aload 6
    //   68: astore 4
    //   70: aload_0
    //   71: aload_1
    //   72: invokespecial 965	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:getUserMetaData	(Ljava/lang/String;)Lcom/crashlytics/android/core/UserMetaData;
    //   75: astore_1
    //   76: aload 6
    //   78: astore_3
    //   79: aload 6
    //   81: astore 4
    //   83: aload_1
    //   84: invokevirtual 968	com/crashlytics/android/core/UserMetaData:isEmpty	()Z
    //   87: istore_2
    //   88: iload_2
    //   89: ifeq +20 -> 109
    //   92: aload 6
    //   94: ldc_w 970
    //   97: invokestatic 476	io/fabric/sdk/android/services/common/CommonUtils:flushOrLog	(Ljava/io/Flushable;Ljava/lang/String;)V
    //   100: aload 5
    //   102: ldc_w 972
    //   105: invokestatic 482	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   108: return
    //   109: aload 6
    //   111: astore_3
    //   112: aload 6
    //   114: astore 4
    //   116: aload_0
    //   117: getfield 196	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:sessionDataWriter	Lcom/crashlytics/android/core/SessionDataWriter;
    //   120: aload 6
    //   122: aload_1
    //   123: getfield 975	com/crashlytics/android/core/UserMetaData:id	Ljava/lang/String;
    //   126: aload_1
    //   127: getfield 978	com/crashlytics/android/core/UserMetaData:name	Ljava/lang/String;
    //   130: aload_1
    //   131: getfield 981	com/crashlytics/android/core/UserMetaData:email	Ljava/lang/String;
    //   134: invokevirtual 984	com/crashlytics/android/core/SessionDataWriter:writeSessionUser	(Lcom/crashlytics/android/core/CodedOutputStream;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   137: aload 6
    //   139: ldc_w 970
    //   142: invokestatic 476	io/fabric/sdk/android/services/common/CommonUtils:flushOrLog	(Ljava/io/Flushable;Ljava/lang/String;)V
    //   145: aload 5
    //   147: ldc_w 972
    //   150: invokestatic 482	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   153: return
    //   154: astore_1
    //   155: aload 6
    //   157: astore 5
    //   159: aload_1
    //   160: astore 6
    //   162: aload 7
    //   164: astore_1
    //   165: aload_1
    //   166: astore_3
    //   167: aload 5
    //   169: astore 4
    //   171: aload 6
    //   173: aload 5
    //   175: invokestatic 494	com/crashlytics/android/core/ExceptionUtils:writeStackTraceIfNotNull	(Ljava/lang/Throwable;Ljava/io/OutputStream;)V
    //   178: aload_1
    //   179: astore_3
    //   180: aload 5
    //   182: astore 4
    //   184: aload 6
    //   186: athrow
    //   187: astore_1
    //   188: aload_3
    //   189: ldc_w 970
    //   192: invokestatic 476	io/fabric/sdk/android/services/common/CommonUtils:flushOrLog	(Ljava/io/Flushable;Ljava/lang/String;)V
    //   195: aload 4
    //   197: ldc_w 972
    //   200: invokestatic 482	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   203: aload_1
    //   204: athrow
    //   205: astore_1
    //   206: aload 5
    //   208: astore 4
    //   210: goto -22 -> 188
    //   213: astore 6
    //   215: aload 4
    //   217: astore_1
    //   218: goto -53 -> 165
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	221	0	this	CrashlyticsUncaughtExceptionHandler
    //   0	221	1	paramString	String
    //   87	2	2	bool	boolean
    //   7	182	3	localObject1	Object
    //   1	215	4	localObject2	Object
    //   47	160	5	localObject3	Object
    //   4	181	6	localObject4	Object
    //   213	1	6	localException	Exception
    //   15	148	7	localObject5	Object
    //   12	41	8	localObject6	Object
    //   9	41	9	localObject7	Object
    // Exception table:
    //   from	to	target	type
    //   17	49	154	java/lang/Exception
    //   17	49	187	finally
    //   171	178	187	finally
    //   184	187	187	finally
    //   56	63	205	finally
    //   70	76	205	finally
    //   83	88	205	finally
    //   116	137	205	finally
    //   56	63	213	java/lang/Exception
    //   70	76	213	java/lang/Exception
    //   83	88	213	java/lang/Exception
    //   116	137	213	java/lang/Exception
  }
  
  /* Error */
  private void writeToCosFromFile(CodedOutputStream paramCodedOutputStream, File paramFile)
    throws IOException
  {
    // Byte code:
    //   0: aload_2
    //   1: invokevirtual 338	java/io/File:exists	()Z
    //   4: ifeq +87 -> 91
    //   7: aload_2
    //   8: invokevirtual 987	java/io/File:length	()J
    //   11: l2i
    //   12: newarray <illegal type>
    //   14: astore 6
    //   16: aconst_null
    //   17: astore 5
    //   19: new 989	java/io/FileInputStream
    //   22: dup
    //   23: aload_2
    //   24: invokespecial 990	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   27: astore_2
    //   28: iconst_0
    //   29: istore_3
    //   30: iload_3
    //   31: aload 6
    //   33: arraylength
    //   34: if_icmpge +30 -> 64
    //   37: aload_2
    //   38: aload 6
    //   40: iload_3
    //   41: aload 6
    //   43: arraylength
    //   44: iload_3
    //   45: isub
    //   46: invokevirtual 994	java/io/FileInputStream:read	([BII)I
    //   49: istore 4
    //   51: iload 4
    //   53: iflt +11 -> 64
    //   56: iload_3
    //   57: iload 4
    //   59: iadd
    //   60: istore_3
    //   61: goto -31 -> 30
    //   64: aload_2
    //   65: ldc_w 996
    //   68: invokestatic 482	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   71: aload_1
    //   72: aload 6
    //   74: invokevirtual 1000	com/crashlytics/android/core/CodedOutputStream:writeRawBytes	([B)V
    //   77: return
    //   78: astore_2
    //   79: aload 5
    //   81: astore_1
    //   82: aload_1
    //   83: ldc_w 996
    //   86: invokestatic 482	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   89: aload_2
    //   90: athrow
    //   91: invokestatic 318	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   94: ldc_w 320
    //   97: new 389	java/lang/StringBuilder
    //   100: dup
    //   101: invokespecial 390	java/lang/StringBuilder:<init>	()V
    //   104: ldc_w 1002
    //   107: invokevirtual 396	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   110: aload_2
    //   111: invokevirtual 502	java/io/File:getName	()Ljava/lang/String;
    //   114: invokevirtual 396	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   117: invokevirtual 399	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   120: aconst_null
    //   121: invokeinterface 328 4 0
    //   126: return
    //   127: astore 5
    //   129: aload_2
    //   130: astore_1
    //   131: aload 5
    //   133: astore_2
    //   134: goto -52 -> 82
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	137	0	this	CrashlyticsUncaughtExceptionHandler
    //   0	137	1	paramCodedOutputStream	CodedOutputStream
    //   0	137	2	paramFile	File
    //   29	32	3	i	int
    //   49	11	4	j	int
    //   17	63	5	localObject1	Object
    //   127	5	5	localObject2	Object
    //   14	59	6	arrayOfByte	byte[]
    // Exception table:
    //   from	to	target	type
    //   19	28	78	finally
    //   30	51	127	finally
  }
  
  void cacheKeyData(final Map<String, String> paramMap)
  {
    this.executorServiceWrapper.executeAsync(new Callable()
    {
      public Void call()
        throws Exception
      {
        String str = CrashlyticsUncaughtExceptionHandler.this.getCurrentSessionId();
        new MetaDataStore(CrashlyticsUncaughtExceptionHandler.this.filesDir).writeKeyData(str, paramMap);
        return null;
      }
    });
  }
  
  void cacheUserData(final String paramString1, final String paramString2, final String paramString3)
  {
    this.executorServiceWrapper.executeAsync(new Callable()
    {
      public Void call()
        throws Exception
      {
        String str = CrashlyticsUncaughtExceptionHandler.this.getCurrentSessionId();
        new MetaDataStore(CrashlyticsUncaughtExceptionHandler.this.filesDir).writeUserData(str, new UserMetaData(paramString1, paramString2, paramString3));
        return null;
      }
    });
  }
  
  void cleanInvalidTempFiles()
  {
    this.executorServiceWrapper.executeAsync(new Runnable()
    {
      public void run()
      {
        CrashlyticsUncaughtExceptionHandler.this.doCleanInvalidTempFiles(CrashlyticsUncaughtExceptionHandler.this.listFilesMatching(ClsFileOutputStream.TEMP_FILENAME_FILTER));
      }
    });
  }
  
  void doCleanInvalidTempFiles(File[] paramArrayOfFile)
  {
    deleteLegacyInvalidCacheDir();
    int k = paramArrayOfFile.length;
    int i = 0;
    while (i < k)
    {
      final Object localObject = paramArrayOfFile[i];
      Fabric.getLogger().d("CrashlyticsCore", "Found invalid session part file: " + localObject);
      localObject = getSessionIdFromSessionFile((File)localObject);
      FilenameFilter local15 = new FilenameFilter()
      {
        public boolean accept(File paramAnonymousFile, String paramAnonymousString)
        {
          return paramAnonymousString.startsWith(localObject);
        }
      };
      Fabric.getLogger().d("CrashlyticsCore", "Deleting all part files for invalid session: " + (String)localObject);
      localObject = listFilesMatching(local15);
      int m = localObject.length;
      int j = 0;
      while (j < m)
      {
        local15 = localObject[j];
        Fabric.getLogger().d("CrashlyticsCore", "Deleting session file: " + local15);
        local15.delete();
        j += 1;
      }
      i += 1;
    }
  }
  
  void doCloseSessions()
    throws Exception
  {
    doCloseSessions(false);
  }
  
  boolean finalizeSessions()
  {
    ((Boolean)this.executorServiceWrapper.executeSyncLoggingException(new Callable()
    {
      public Boolean call()
        throws Exception
      {
        if (!CrashlyticsUncaughtExceptionHandler.this.isHandlingException.get())
        {
          Fabric.getLogger().d("CrashlyticsCore", "Finalizing previously open sessions.");
          SessionEventData localSessionEventData = CrashlyticsUncaughtExceptionHandler.this.crashlyticsCore.getExternalCrashEventData();
          if (localSessionEventData != null) {
            CrashlyticsUncaughtExceptionHandler.this.writeExternalCrashEvent(localSessionEventData);
          }
          CrashlyticsUncaughtExceptionHandler.this.doCloseSessions(true);
          Fabric.getLogger().d("CrashlyticsCore", "Closed all previously open sessions");
          return Boolean.valueOf(true);
        }
        Fabric.getLogger().d("CrashlyticsCore", "Skipping session finalization because a crash has already occurred.");
        return Boolean.valueOf(false);
      }
    })).booleanValue();
  }
  
  boolean hasOpenSession()
  {
    return listSessionBeginFiles().length > 0;
  }
  
  boolean isHandlingException()
  {
    return this.isHandlingException.get();
  }
  
  File[] listSessionBeginFiles()
  {
    return listFilesMatching(new FileNameContainsFilter("BeginSession"));
  }
  
  void openSession()
  {
    this.executorServiceWrapper.executeAsync(new Callable()
    {
      public Void call()
        throws Exception
      {
        CrashlyticsUncaughtExceptionHandler.this.doOpenSession();
        return null;
      }
    });
  }
  
  void trimSessionFiles()
  {
    Utils.capFileCount(this.filesDir, SESSION_FILE_FILTER, 4, SMALLEST_FILE_NAME_FIRST);
  }
  
  /* Error */
  public void uncaughtException(final Thread paramThread, final Throwable paramThrowable)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 198	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:isHandlingException	Ljava/util/concurrent/atomic/AtomicBoolean;
    //   6: iconst_1
    //   7: invokevirtual 247	java/util/concurrent/atomic/AtomicBoolean:set	(Z)V
    //   10: invokestatic 318	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   13: ldc_w 320
    //   16: new 389	java/lang/StringBuilder
    //   19: dup
    //   20: invokespecial 390	java/lang/StringBuilder:<init>	()V
    //   23: ldc_w 1049
    //   26: invokevirtual 396	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   29: aload_2
    //   30: invokevirtual 442	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   33: ldc_w 444
    //   36: invokevirtual 396	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   39: aload_1
    //   40: invokevirtual 449	java/lang/Thread:getName	()Ljava/lang/String;
    //   43: invokevirtual 396	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   46: invokevirtual 399	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   49: invokeinterface 387 3 0
    //   54: aload_0
    //   55: getfield 186	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:receiversRegistered	Ljava/util/concurrent/atomic/AtomicBoolean;
    //   58: iconst_1
    //   59: invokevirtual 1053	java/util/concurrent/atomic/AtomicBoolean:getAndSet	(Z)Z
    //   62: ifne +41 -> 103
    //   65: invokestatic 318	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   68: ldc_w 320
    //   71: ldc_w 1055
    //   74: invokeinterface 387 3 0
    //   79: aload_0
    //   80: getfield 194	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:crashlyticsCore	Lcom/crashlytics/android/core/CrashlyticsCore;
    //   83: invokevirtual 212	com/crashlytics/android/core/CrashlyticsCore:getContext	()Landroid/content/Context;
    //   86: astore_3
    //   87: aload_3
    //   88: aload_0
    //   89: getfield 226	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:powerConnectedReceiver	Landroid/content/BroadcastReceiver;
    //   92: invokevirtual 1059	android/content/Context:unregisterReceiver	(Landroid/content/BroadcastReceiver;)V
    //   95: aload_3
    //   96: aload_0
    //   97: getfield 236	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:powerDisconnectedReceiver	Landroid/content/BroadcastReceiver;
    //   100: invokevirtual 1059	android/content/Context:unregisterReceiver	(Landroid/content/BroadcastReceiver;)V
    //   103: new 409	java/util/Date
    //   106: dup
    //   107: invokespecial 410	java/util/Date:<init>	()V
    //   110: astore_3
    //   111: aload_0
    //   112: getfield 190	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:executorServiceWrapper	Lcom/crashlytics/android/core/CrashlyticsExecutorServiceWrapper;
    //   115: new 34	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler$7
    //   118: dup
    //   119: aload_0
    //   120: aload_3
    //   121: aload_1
    //   122: aload_2
    //   123: invokespecial 1061	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler$7:<init>	(Lcom/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler;Ljava/util/Date;Ljava/lang/Thread;Ljava/lang/Throwable;)V
    //   126: invokevirtual 1036	com/crashlytics/android/core/CrashlyticsExecutorServiceWrapper:executeSyncLoggingException	(Ljava/util/concurrent/Callable;)Ljava/lang/Object;
    //   129: pop
    //   130: invokestatic 318	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   133: ldc_w 320
    //   136: ldc_w 1063
    //   139: invokeinterface 387 3 0
    //   144: aload_0
    //   145: getfield 188	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:defaultHandler	Ljava/lang/Thread$UncaughtExceptionHandler;
    //   148: aload_1
    //   149: aload_2
    //   150: invokeinterface 1065 3 0
    //   155: aload_0
    //   156: getfield 198	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:isHandlingException	Ljava/util/concurrent/atomic/AtomicBoolean;
    //   159: iconst_0
    //   160: invokevirtual 247	java/util/concurrent/atomic/AtomicBoolean:set	(Z)V
    //   163: aload_0
    //   164: monitorexit
    //   165: return
    //   166: astore_3
    //   167: invokestatic 318	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   170: ldc_w 320
    //   173: ldc_w 1067
    //   176: aload_3
    //   177: invokeinterface 328 4 0
    //   182: invokestatic 318	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   185: ldc_w 320
    //   188: ldc_w 1063
    //   191: invokeinterface 387 3 0
    //   196: aload_0
    //   197: getfield 188	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:defaultHandler	Ljava/lang/Thread$UncaughtExceptionHandler;
    //   200: aload_1
    //   201: aload_2
    //   202: invokeinterface 1065 3 0
    //   207: aload_0
    //   208: getfield 198	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:isHandlingException	Ljava/util/concurrent/atomic/AtomicBoolean;
    //   211: iconst_0
    //   212: invokevirtual 247	java/util/concurrent/atomic/AtomicBoolean:set	(Z)V
    //   215: goto -52 -> 163
    //   218: astore_1
    //   219: aload_0
    //   220: monitorexit
    //   221: aload_1
    //   222: athrow
    //   223: astore_3
    //   224: invokestatic 318	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   227: ldc_w 320
    //   230: ldc_w 1063
    //   233: invokeinterface 387 3 0
    //   238: aload_0
    //   239: getfield 188	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:defaultHandler	Ljava/lang/Thread$UncaughtExceptionHandler;
    //   242: aload_1
    //   243: aload_2
    //   244: invokeinterface 1065 3 0
    //   249: aload_0
    //   250: getfield 198	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:isHandlingException	Ljava/util/concurrent/atomic/AtomicBoolean;
    //   253: iconst_0
    //   254: invokevirtual 247	java/util/concurrent/atomic/AtomicBoolean:set	(Z)V
    //   257: aload_3
    //   258: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	259	0	this	CrashlyticsUncaughtExceptionHandler
    //   0	259	1	paramThread	Thread
    //   0	259	2	paramThrowable	Throwable
    //   86	35	3	localObject1	Object
    //   166	11	3	localException	Exception
    //   223	35	3	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   10	103	166	java/lang/Exception
    //   103	130	166	java/lang/Exception
    //   2	10	218	finally
    //   130	163	218	finally
    //   182	215	218	finally
    //   224	259	218	finally
    //   10	103	223	finally
    //   103	130	223	finally
    //   167	182	223	finally
  }
  
  void writeNonFatalException(final Thread paramThread, final Throwable paramThrowable)
  {
    final Date localDate = new Date();
    this.executorServiceWrapper.executeAsync(new Runnable()
    {
      public void run()
      {
        if (!CrashlyticsUncaughtExceptionHandler.this.isHandlingException.get()) {
          CrashlyticsUncaughtExceptionHandler.this.doWriteNonFatal(localDate, paramThread, paramThrowable);
        }
      }
    });
  }
  
  void writeToLog(final long paramLong, String paramString)
  {
    this.executorServiceWrapper.executeAsync(new Callable()
    {
      public Void call()
        throws Exception
      {
        if (!CrashlyticsUncaughtExceptionHandler.this.isHandlingException.get()) {
          CrashlyticsUncaughtExceptionHandler.this.logFileManager.writeToLog(paramLong, this.val$msg);
        }
        return null;
      }
    });
  }
  
  private static class AnySessionPartFileFilter
    implements FilenameFilter
  {
    public boolean accept(File paramFile, String paramString)
    {
      return (!CrashlyticsUncaughtExceptionHandler.SESSION_FILE_FILTER.accept(paramFile, paramString)) && (CrashlyticsUncaughtExceptionHandler.SESSION_FILE_PATTERN.matcher(paramString).matches());
    }
  }
  
  static class FileNameContainsFilter
    implements FilenameFilter
  {
    private final String string;
    
    public FileNameContainsFilter(String paramString)
    {
      this.string = paramString;
    }
    
    public boolean accept(File paramFile, String paramString)
    {
      return (paramString.contains(this.string)) && (!paramString.endsWith(".cls_temp"));
    }
  }
  
  static class SessionPartFileFilter
    implements FilenameFilter
  {
    private final String sessionId;
    
    public SessionPartFileFilter(String paramString)
    {
      this.sessionId = paramString;
    }
    
    public boolean accept(File paramFile, String paramString)
    {
      if (paramString.equals(this.sessionId + ".cls")) {}
      while ((!paramString.contains(this.sessionId)) || (paramString.endsWith(".cls_temp"))) {
        return false;
      }
      return true;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */