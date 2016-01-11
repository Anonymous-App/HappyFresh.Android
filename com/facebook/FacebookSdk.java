package com.facebook;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import com.facebook.internal.Validate;
import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public final class FacebookSdk
{
  public static final String APPLICATION_ID_PROPERTY = "com.facebook.sdk.ApplicationId";
  public static final String APPLICATION_NAME_PROPERTY = "com.facebook.sdk.ApplicationName";
  private static final String ATTRIBUTION_PREFERENCES = "com.facebook.sdk.attributionTracking";
  static final String CALLBACK_OFFSET_CHANGED_AFTER_INIT = "The callback request code offset can't be updated once the SDK is initialized.";
  static final String CALLBACK_OFFSET_NEGATIVE = "The callback request code offset can't be negative.";
  public static final String CLIENT_TOKEN_PROPERTY = "com.facebook.sdk.ClientToken";
  private static final int DEFAULT_CORE_POOL_SIZE = 5;
  private static final int DEFAULT_KEEP_ALIVE = 1;
  private static final int DEFAULT_MAXIMUM_POOL_SIZE = 128;
  private static final ThreadFactory DEFAULT_THREAD_FACTORY = new ThreadFactory()
  {
    private final AtomicInteger counter = new AtomicInteger(0);
    
    public Thread newThread(Runnable paramAnonymousRunnable)
    {
      return new Thread(paramAnonymousRunnable, "FacebookSdk #" + this.counter.incrementAndGet());
    }
  };
  private static final BlockingQueue<Runnable> DEFAULT_WORK_QUEUE;
  private static final String FACEBOOK_COM = "facebook.com";
  private static final Object LOCK;
  private static final int MAX_REQUEST_CODE_RANGE = 100;
  private static final String PUBLISH_ACTIVITY_PATH = "%s/activities";
  private static final String TAG = FacebookSdk.class.getCanonicalName();
  private static volatile String appClientToken;
  private static Context applicationContext;
  private static volatile String applicationId;
  private static volatile String applicationName;
  private static File cacheDir;
  private static int callbackRequestCodeOffset;
  private static volatile Executor executor;
  private static volatile String facebookDomain;
  private static volatile boolean isDebugEnabled;
  private static boolean isLegacyTokenUpgradeSupported;
  private static final HashSet<LoggingBehavior> loggingBehaviors = new HashSet(Arrays.asList(new LoggingBehavior[] { LoggingBehavior.DEVELOPER_ERRORS }));
  private static AtomicLong onProgressThreshold;
  private static Boolean sdkInitialized = Boolean.valueOf(false);
  
  static
  {
    facebookDomain = "facebook.com";
    onProgressThreshold = new AtomicLong(65536L);
    isDebugEnabled = false;
    isLegacyTokenUpgradeSupported = false;
    callbackRequestCodeOffset = 64206;
    LOCK = new Object();
    DEFAULT_WORK_QUEUE = new LinkedBlockingQueue(10);
  }
  
  public static void addLoggingBehavior(LoggingBehavior paramLoggingBehavior)
  {
    synchronized (loggingBehaviors)
    {
      loggingBehaviors.add(paramLoggingBehavior);
      updateGraphDebugBehavior();
      return;
    }
  }
  
  public static void clearLoggingBehaviors()
  {
    synchronized (loggingBehaviors)
    {
      loggingBehaviors.clear();
      return;
    }
  }
  
  public static Context getApplicationContext()
  {
    Validate.sdkInitialized();
    return applicationContext;
  }
  
  public static String getApplicationId()
  {
    Validate.sdkInitialized();
    return applicationId;
  }
  
  public static String getApplicationName()
  {
    Validate.sdkInitialized();
    return applicationName;
  }
  
  /* Error */
  public static String getApplicationSignature(Context paramContext)
  {
    // Byte code:
    //   0: invokestatic 163	com/facebook/internal/Validate:sdkInitialized	()V
    //   3: aload_0
    //   4: ifnonnull +5 -> 9
    //   7: aconst_null
    //   8: areturn
    //   9: aload_0
    //   10: invokevirtual 183	android/content/Context:getPackageManager	()Landroid/content/pm/PackageManager;
    //   13: astore_1
    //   14: aload_1
    //   15: ifnull -8 -> 7
    //   18: aload_0
    //   19: invokevirtual 186	android/content/Context:getPackageName	()Ljava/lang/String;
    //   22: astore_0
    //   23: aload_1
    //   24: aload_0
    //   25: bipush 64
    //   27: invokevirtual 192	android/content/pm/PackageManager:getPackageInfo	(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;
    //   30: astore_0
    //   31: aload_0
    //   32: getfield 198	android/content/pm/PackageInfo:signatures	[Landroid/content/pm/Signature;
    //   35: astore_1
    //   36: aload_1
    //   37: ifnull -30 -> 7
    //   40: aload_1
    //   41: arraylength
    //   42: ifeq -35 -> 7
    //   45: ldc -56
    //   47: invokestatic 206	java/security/MessageDigest:getInstance	(Ljava/lang/String;)Ljava/security/MessageDigest;
    //   50: astore_1
    //   51: aload_1
    //   52: aload_0
    //   53: getfield 198	android/content/pm/PackageInfo:signatures	[Landroid/content/pm/Signature;
    //   56: iconst_0
    //   57: aaload
    //   58: invokevirtual 212	android/content/pm/Signature:toByteArray	()[B
    //   61: invokevirtual 216	java/security/MessageDigest:update	([B)V
    //   64: aload_1
    //   65: invokevirtual 219	java/security/MessageDigest:digest	()[B
    //   68: bipush 9
    //   70: invokestatic 225	android/util/Base64:encodeToString	([BI)Ljava/lang/String;
    //   73: areturn
    //   74: astore_0
    //   75: aconst_null
    //   76: areturn
    //   77: astore_0
    //   78: aconst_null
    //   79: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	80	0	paramContext	Context
    //   13	52	1	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   23	31	74	android/content/pm/PackageManager$NameNotFoundException
    //   45	51	77	java/security/NoSuchAlgorithmException
  }
  
  private static Executor getAsyncTaskExecutor()
  {
    try
    {
      Object localObject = AsyncTask.class.getField("THREAD_POOL_EXECUTOR");
      if ((localIllegalAccessException instanceof Executor)) {
        break label35;
      }
    }
    catch (NoSuchFieldException localNoSuchFieldException)
    {
      try
      {
        localObject = ((Field)localObject).get(null);
        if (localObject != null) {
          break label26;
        }
        return null;
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        return null;
      }
      localNoSuchFieldException = localNoSuchFieldException;
      return null;
    }
    label26:
    return null;
    label35:
    return (Executor)localIllegalAccessException;
  }
  
  public static File getCacheDir()
  {
    Validate.sdkInitialized();
    return cacheDir;
  }
  
  public static int getCallbackRequestCodeOffset()
  {
    Validate.sdkInitialized();
    return callbackRequestCodeOffset;
  }
  
  public static String getClientToken()
  {
    Validate.sdkInitialized();
    return appClientToken;
  }
  
  public static Executor getExecutor()
  {
    synchronized (LOCK)
    {
      if (executor == null)
      {
        Executor localExecutor = getAsyncTaskExecutor();
        Object localObject1 = localExecutor;
        if (localExecutor == null) {
          localObject1 = new ThreadPoolExecutor(5, 128, 1L, TimeUnit.SECONDS, DEFAULT_WORK_QUEUE, DEFAULT_THREAD_FACTORY);
        }
        executor = (Executor)localObject1;
      }
      return executor;
    }
  }
  
  public static String getFacebookDomain()
  {
    return facebookDomain;
  }
  
  public static boolean getLimitEventAndDataUsage(Context paramContext)
  {
    Validate.sdkInitialized();
    return paramContext.getSharedPreferences("com.facebook.sdk.appEventPreferences", 0).getBoolean("limitEventUsage", false);
  }
  
  public static Set<LoggingBehavior> getLoggingBehaviors()
  {
    synchronized (loggingBehaviors)
    {
      Set localSet = Collections.unmodifiableSet(new HashSet(loggingBehaviors));
      return localSet;
    }
  }
  
  public static long getOnProgressThreshold()
  {
    Validate.sdkInitialized();
    return onProgressThreshold.get();
  }
  
  public static String getSdkVersion()
  {
    Validate.sdkInitialized();
    return "4.1.0";
  }
  
  public static boolean isDebugEnabled()
  {
    return isDebugEnabled;
  }
  
  public static boolean isFacebookRequestCode(int paramInt)
  {
    return (paramInt >= callbackRequestCodeOffset) && (paramInt < callbackRequestCodeOffset + 100);
  }
  
  public static boolean isInitialized()
  {
    try
    {
      boolean bool = sdkInitialized.booleanValue();
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public static boolean isLegacyTokenUpgradeSupported()
  {
    return isLegacyTokenUpgradeSupported;
  }
  
  public static boolean isLoggingBehaviorEnabled(LoggingBehavior paramLoggingBehavior)
  {
    for (;;)
    {
      synchronized (loggingBehaviors)
      {
        if ((isDebugEnabled()) && (loggingBehaviors.contains(paramLoggingBehavior)))
        {
          bool = true;
          return bool;
        }
      }
      boolean bool = false;
    }
  }
  
  static void loadDefaultsFromMetadata(Context paramContext)
  {
    if (paramContext == null) {
      return;
    }
    for (;;)
    {
      Object localObject;
      try
      {
        paramContext = paramContext.getPackageManager().getApplicationInfo(paramContext.getPackageName(), 128);
        if ((paramContext == null) || (paramContext.metaData == null)) {
          break;
        }
        if (applicationId == null)
        {
          localObject = paramContext.metaData.get("com.facebook.sdk.ApplicationId");
          if ((localObject instanceof String)) {
            applicationId = (String)localObject;
          }
        }
        else
        {
          if (applicationName == null) {
            applicationName = paramContext.metaData.getString("com.facebook.sdk.ApplicationName");
          }
          if (appClientToken != null) {
            break;
          }
          appClientToken = paramContext.metaData.getString("com.facebook.sdk.ClientToken");
          return;
        }
      }
      catch (PackageManager.NameNotFoundException paramContext)
      {
        return;
      }
      if ((localObject instanceof Integer)) {
        applicationId = localObject.toString();
      }
    }
  }
  
  /* Error */
  static GraphResponse publishInstallAndWaitForResponse(Context paramContext, String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnull +7 -> 8
    //   4: aload_1
    //   5: ifnonnull +41 -> 46
    //   8: new 356	java/lang/IllegalArgumentException
    //   11: dup
    //   12: ldc_w 358
    //   15: invokespecial 361	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
    //   18: athrow
    //   19: astore_0
    //   20: ldc_w 363
    //   23: aload_0
    //   24: invokestatic 369	com/facebook/internal/Utility:logd	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   27: new 371	com/facebook/GraphResponse
    //   30: dup
    //   31: aconst_null
    //   32: aconst_null
    //   33: new 373	com/facebook/FacebookRequestError
    //   36: dup
    //   37: aconst_null
    //   38: aload_0
    //   39: invokespecial 376	com/facebook/FacebookRequestError:<init>	(Ljava/net/HttpURLConnection;Ljava/lang/Exception;)V
    //   42: invokespecial 379	com/facebook/GraphResponse:<init>	(Lcom/facebook/GraphRequest;Ljava/net/HttpURLConnection;Lcom/facebook/FacebookRequestError;)V
    //   45: areturn
    //   46: aload_0
    //   47: invokestatic 385	com/facebook/internal/AttributionIdentifiers:getAttributionIdentifiers	(Landroid/content/Context;)Lcom/facebook/internal/AttributionIdentifiers;
    //   50: astore 8
    //   52: aload_0
    //   53: ldc 20
    //   55: iconst_0
    //   56: invokevirtual 281	android/content/Context:getSharedPreferences	(Ljava/lang/String;I)Landroid/content/SharedPreferences;
    //   59: astore 7
    //   61: new 387	java/lang/StringBuilder
    //   64: dup
    //   65: invokespecial 388	java/lang/StringBuilder:<init>	()V
    //   68: aload_1
    //   69: invokevirtual 392	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   72: ldc_w 394
    //   75: invokevirtual 392	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   78: invokevirtual 395	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   81: astore 4
    //   83: new 387	java/lang/StringBuilder
    //   86: dup
    //   87: invokespecial 388	java/lang/StringBuilder:<init>	()V
    //   90: aload_1
    //   91: invokevirtual 392	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   94: ldc_w 397
    //   97: invokevirtual 392	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   100: invokevirtual 395	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   103: astore 5
    //   105: aload 7
    //   107: aload 4
    //   109: lconst_0
    //   110: invokeinterface 401 4 0
    //   115: lstore_2
    //   116: aload 7
    //   118: aload 5
    //   120: aconst_null
    //   121: invokeinterface 404 3 0
    //   126: astore 6
    //   128: getstatic 410	com/facebook/internal/AppEventsLoggerUtility$GraphAPIActivityType:MOBILE_INSTALL_EVENT	Lcom/facebook/internal/AppEventsLoggerUtility$GraphAPIActivityType;
    //   131: aload 8
    //   133: aload_0
    //   134: invokestatic 415	com/facebook/appevents/AppEventsLogger:getAnonymousAppDeviceGUID	(Landroid/content/Context;)Ljava/lang/String;
    //   137: aload_0
    //   138: invokestatic 417	com/facebook/FacebookSdk:getLimitEventAndDataUsage	(Landroid/content/Context;)Z
    //   141: aload_0
    //   142: invokestatic 423	com/facebook/internal/AppEventsLoggerUtility:getJSONObjectForGraphAPICall	(Lcom/facebook/internal/AppEventsLoggerUtility$GraphAPIActivityType;Lcom/facebook/internal/AttributionIdentifiers;Ljava/lang/String;ZLandroid/content/Context;)Lorg/json/JSONObject;
    //   145: astore_0
    //   146: aconst_null
    //   147: ldc 51
    //   149: iconst_1
    //   150: anewarray 4	java/lang/Object
    //   153: dup
    //   154: iconst_0
    //   155: aload_1
    //   156: aastore
    //   157: invokestatic 427	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   160: aload_0
    //   161: aconst_null
    //   162: invokestatic 433	com/facebook/GraphRequest:newPostRequest	(Lcom/facebook/AccessToken;Ljava/lang/String;Lorg/json/JSONObject;Lcom/facebook/GraphRequest$Callback;)Lcom/facebook/GraphRequest;
    //   165: astore 8
    //   167: lload_2
    //   168: lconst_0
    //   169: lcmp
    //   170: ifeq +84 -> 254
    //   173: aconst_null
    //   174: astore_1
    //   175: aload_1
    //   176: astore_0
    //   177: aload 6
    //   179: ifnull +13 -> 192
    //   182: new 435	org/json/JSONObject
    //   185: dup
    //   186: aload 6
    //   188: invokespecial 436	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   191: astore_0
    //   192: aload_0
    //   193: ifnonnull +49 -> 242
    //   196: ldc_w 438
    //   199: aconst_null
    //   200: new 440	com/facebook/GraphRequestBatch
    //   203: dup
    //   204: iconst_1
    //   205: anewarray 429	com/facebook/GraphRequest
    //   208: dup
    //   209: iconst_0
    //   210: aload 8
    //   212: aastore
    //   213: invokespecial 443	com/facebook/GraphRequestBatch:<init>	([Lcom/facebook/GraphRequest;)V
    //   216: invokestatic 447	com/facebook/GraphResponse:createResponsesFromString	(Ljava/lang/String;Ljava/net/HttpURLConnection;Lcom/facebook/GraphRequestBatch;)Ljava/util/List;
    //   219: iconst_0
    //   220: invokeinterface 452 2 0
    //   225: checkcast 371	com/facebook/GraphResponse
    //   228: areturn
    //   229: astore_0
    //   230: new 454	com/facebook/FacebookException
    //   233: dup
    //   234: ldc_w 456
    //   237: aload_0
    //   238: invokespecial 459	com/facebook/FacebookException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   241: athrow
    //   242: new 371	com/facebook/GraphResponse
    //   245: dup
    //   246: aconst_null
    //   247: aconst_null
    //   248: aconst_null
    //   249: aload_0
    //   250: invokespecial 462	com/facebook/GraphResponse:<init>	(Lcom/facebook/GraphRequest;Ljava/net/HttpURLConnection;Ljava/lang/String;Lorg/json/JSONObject;)V
    //   253: areturn
    //   254: aload 8
    //   256: invokevirtual 466	com/facebook/GraphRequest:executeAndWait	()Lcom/facebook/GraphResponse;
    //   259: astore_0
    //   260: aload 7
    //   262: invokeinterface 470 1 0
    //   267: astore_1
    //   268: aload_1
    //   269: aload 4
    //   271: invokestatic 475	java/lang/System:currentTimeMillis	()J
    //   274: invokeinterface 481 4 0
    //   279: pop
    //   280: aload_0
    //   281: invokevirtual 485	com/facebook/GraphResponse:getJSONObject	()Lorg/json/JSONObject;
    //   284: ifnull +19 -> 303
    //   287: aload_1
    //   288: aload 5
    //   290: aload_0
    //   291: invokevirtual 485	com/facebook/GraphResponse:getJSONObject	()Lorg/json/JSONObject;
    //   294: invokevirtual 486	org/json/JSONObject:toString	()Ljava/lang/String;
    //   297: invokeinterface 490 3 0
    //   302: pop
    //   303: aload_1
    //   304: invokeinterface 493 1 0
    //   309: aload_0
    //   310: areturn
    //   311: astore_0
    //   312: aload_1
    //   313: astore_0
    //   314: goto -122 -> 192
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	317	0	paramContext	Context
    //   0	317	1	paramString	String
    //   115	53	2	l	long
    //   81	189	4	str1	String
    //   103	186	5	str2	String
    //   126	61	6	str3	String
    //   59	202	7	localSharedPreferences	SharedPreferences
    //   50	205	8	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   8	19	19	java/lang/Exception
    //   46	128	19	java/lang/Exception
    //   128	146	19	java/lang/Exception
    //   146	167	19	java/lang/Exception
    //   182	192	19	java/lang/Exception
    //   196	229	19	java/lang/Exception
    //   230	242	19	java/lang/Exception
    //   242	254	19	java/lang/Exception
    //   254	303	19	java/lang/Exception
    //   303	309	19	java/lang/Exception
    //   128	146	229	org/json/JSONException
    //   182	192	311	org/json/JSONException
  }
  
  public static void publishInstallAsync(Context paramContext, final String paramString)
  {
    paramContext = paramContext.getApplicationContext();
    getExecutor().execute(new Runnable()
    {
      public void run()
      {
        FacebookSdk.publishInstallAndWaitForResponse(this.val$applicationContext, paramString);
      }
    });
  }
  
  public static void removeLoggingBehavior(LoggingBehavior paramLoggingBehavior)
  {
    synchronized (loggingBehaviors)
    {
      loggingBehaviors.remove(paramLoggingBehavior);
      return;
    }
  }
  
  /* Error */
  public static void sdkInitialize(Context paramContext)
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: getstatic 143	com/facebook/FacebookSdk:sdkInitialized	Ljava/lang/Boolean;
    //   6: invokevirtual 313	java/lang/Boolean:booleanValue	()Z
    //   9: istore_1
    //   10: iload_1
    //   11: iconst_1
    //   12: if_icmpne +7 -> 19
    //   15: ldc 2
    //   17: monitorexit
    //   18: return
    //   19: aload_0
    //   20: ldc_w 511
    //   23: invokestatic 515	com/facebook/internal/Validate:notNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   26: aload_0
    //   27: iconst_0
    //   28: invokestatic 519	com/facebook/internal/Validate:hasFacebookActivity	(Landroid/content/Context;Z)V
    //   31: aload_0
    //   32: iconst_0
    //   33: invokestatic 522	com/facebook/internal/Validate:hasInternetPermissions	(Landroid/content/Context;Z)V
    //   36: aload_0
    //   37: invokevirtual 497	android/content/Context:getApplicationContext	()Landroid/content/Context;
    //   40: putstatic 165	com/facebook/FacebookSdk:applicationContext	Landroid/content/Context;
    //   43: getstatic 165	com/facebook/FacebookSdk:applicationContext	Landroid/content/Context;
    //   46: invokestatic 524	com/facebook/FacebookSdk:loadDefaultsFromMetadata	(Landroid/content/Context;)V
    //   49: getstatic 165	com/facebook/FacebookSdk:applicationContext	Landroid/content/Context;
    //   52: getstatic 168	com/facebook/FacebookSdk:applicationId	Ljava/lang/String;
    //   55: invokestatic 527	com/facebook/internal/Utility:loadAppSettingsAsync	(Landroid/content/Context;Ljava/lang/String;)V
    //   58: invokestatic 532	com/facebook/internal/NativeProtocol:updateAllAvailableProtocolVersionsAsync	()V
    //   61: getstatic 165	com/facebook/FacebookSdk:applicationContext	Landroid/content/Context;
    //   64: invokestatic 537	com/facebook/internal/BoltsMeasurementEventListener:getInstance	(Landroid/content/Context;)Lcom/facebook/internal/BoltsMeasurementEventListener;
    //   67: pop
    //   68: getstatic 165	com/facebook/FacebookSdk:applicationContext	Landroid/content/Context;
    //   71: invokevirtual 539	android/content/Context:getCacheDir	()Ljava/io/File;
    //   74: putstatic 251	com/facebook/FacebookSdk:cacheDir	Ljava/io/File;
    //   77: new 541	java/util/concurrent/FutureTask
    //   80: dup
    //   81: new 8	com/facebook/FacebookSdk$2
    //   84: dup
    //   85: invokespecial 542	com/facebook/FacebookSdk$2:<init>	()V
    //   88: invokespecial 545	java/util/concurrent/FutureTask:<init>	(Ljava/util/concurrent/Callable;)V
    //   91: astore_0
    //   92: invokestatic 499	com/facebook/FacebookSdk:getExecutor	()Ljava/util/concurrent/Executor;
    //   95: aload_0
    //   96: invokeinterface 505 2 0
    //   101: iconst_1
    //   102: invokestatic 141	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   105: putstatic 143	com/facebook/FacebookSdk:sdkInitialized	Ljava/lang/Boolean;
    //   108: goto -93 -> 15
    //   111: astore_0
    //   112: ldc 2
    //   114: monitorexit
    //   115: aload_0
    //   116: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	117	0	paramContext	Context
    //   9	4	1	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   3	10	111	finally
    //   19	108	111	finally
  }
  
  public static void sdkInitialize(Context paramContext, int paramInt)
  {
    try
    {
      if ((sdkInitialized.booleanValue()) && (paramInt != callbackRequestCodeOffset)) {
        throw new FacebookException("The callback request code offset can't be updated once the SDK is initialized.");
      }
    }
    finally {}
    if (paramInt < 0) {
      throw new FacebookException("The callback request code offset can't be negative.");
    }
    callbackRequestCodeOffset = paramInt;
    sdkInitialize(paramContext);
  }
  
  public static void setApplicationId(String paramString)
  {
    applicationId = paramString;
  }
  
  public static void setApplicationName(String paramString)
  {
    applicationName = paramString;
  }
  
  public static void setCacheDir(File paramFile)
  {
    cacheDir = paramFile;
  }
  
  public static void setClientToken(String paramString)
  {
    appClientToken = paramString;
  }
  
  public static void setExecutor(Executor paramExecutor)
  {
    Validate.notNull(paramExecutor, "executor");
    synchronized (LOCK)
    {
      executor = paramExecutor;
      return;
    }
  }
  
  public static void setFacebookDomain(String paramString)
  {
    Log.w(TAG, "WARNING: Calling setFacebookDomain from non-DEBUG code.");
    facebookDomain = paramString;
  }
  
  public static void setIsDebugEnabled(boolean paramBoolean)
  {
    isDebugEnabled = paramBoolean;
  }
  
  public static void setLegacyTokenUpgradeSupported(boolean paramBoolean)
  {
    isLegacyTokenUpgradeSupported = paramBoolean;
  }
  
  public static void setLimitEventAndDataUsage(Context paramContext, boolean paramBoolean)
  {
    paramContext.getSharedPreferences("com.facebook.sdk.appEventPreferences", 0).edit().putBoolean("limitEventUsage", paramBoolean).apply();
  }
  
  public static void setOnProgressThreshold(long paramLong)
  {
    onProgressThreshold.set(paramLong);
  }
  
  private static void updateGraphDebugBehavior()
  {
    if ((loggingBehaviors.contains(LoggingBehavior.GRAPH_API_DEBUG_INFO)) && (!loggingBehaviors.contains(LoggingBehavior.GRAPH_API_DEBUG_WARNING))) {
      loggingBehaviors.add(LoggingBehavior.GRAPH_API_DEBUG_WARNING);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/FacebookSdk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */