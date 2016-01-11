package com.crashlytics.android.core;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.widget.ScrollView;
import android.widget.TextView;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.core.internal.CrashEventDataProvider;
import com.crashlytics.android.core.internal.models.SessionEventData;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.ApiKey;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.Crash.FatalException;
import io.fabric.sdk.android.services.common.Crash.LoggedException;
import io.fabric.sdk.android.services.common.ExecutorUtils;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.concurrency.DependsOn;
import io.fabric.sdk.android.services.concurrency.Priority;
import io.fabric.sdk.android.services.concurrency.PriorityCallable;
import io.fabric.sdk.android.services.concurrency.Task;
import io.fabric.sdk.android.services.concurrency.UnmetDependencyException;
import io.fabric.sdk.android.services.network.DefaultHttpRequestFactory;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.persistence.FileStoreImpl;
import io.fabric.sdk.android.services.persistence.PreferenceStore;
import io.fabric.sdk.android.services.persistence.PreferenceStoreImpl;
import io.fabric.sdk.android.services.settings.AppSettingsData;
import io.fabric.sdk.android.services.settings.FeaturesSettingsData;
import io.fabric.sdk.android.services.settings.PromptSettingsData;
import io.fabric.sdk.android.services.settings.SessionSettingsData;
import io.fabric.sdk.android.services.settings.Settings;
import io.fabric.sdk.android.services.settings.Settings.SettingsAccess;
import io.fabric.sdk.android.services.settings.SettingsData;
import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.net.ssl.HttpsURLConnection;

@DependsOn({CrashEventDataProvider.class})
public class CrashlyticsCore
  extends Kit<Void>
{
  static final float CLS_DEFAULT_PROCESS_DELAY = 1.0F;
  static final String COLLECT_CUSTOM_KEYS = "com.crashlytics.CollectCustomKeys";
  static final String COLLECT_CUSTOM_LOGS = "com.crashlytics.CollectCustomLogs";
  static final String CRASHLYTICS_API_ENDPOINT = "com.crashlytics.ApiEndpoint";
  static final String CRASHLYTICS_REQUIRE_BUILD_ID = "com.crashlytics.RequireBuildId";
  static final boolean CRASHLYTICS_REQUIRE_BUILD_ID_DEFAULT = true;
  static final int DEFAULT_MAIN_HANDLER_TIMEOUT_SEC = 4;
  private static final String INITIALIZATION_MARKER_FILE_NAME = "initialization_marker";
  static final int MAX_ATTRIBUTES = 64;
  static final int MAX_ATTRIBUTE_SIZE = 1024;
  private static final String PREF_ALWAYS_SEND_REPORTS_KEY = "always_send_reports_opt_in";
  private static final boolean SHOULD_PROMPT_BEFORE_SENDING_REPORTS_DEFAULT = false;
  public static final String TAG = "CrashlyticsCore";
  private final ConcurrentHashMap<String, String> attributes = new ConcurrentHashMap();
  private String buildId;
  private float delay;
  private boolean disabled;
  private CrashlyticsExecutorServiceWrapper executorServiceWrapper;
  private CrashEventDataProvider externalCrashEventDataProvider;
  private CrashlyticsUncaughtExceptionHandler handler;
  private HttpRequestFactory httpRequestFactory;
  private File initializationMarkerFile;
  private String installerPackageName;
  private CrashlyticsListener listener;
  private String packageName;
  private final PinningInfoProvider pinningInfo;
  private File sdkDir;
  private final long startTime = System.currentTimeMillis();
  private String userEmail = null;
  private String userId = null;
  private String userName = null;
  private String versionCode;
  private String versionName;
  
  public CrashlyticsCore()
  {
    this(1.0F, null, null, false);
  }
  
  CrashlyticsCore(float paramFloat, CrashlyticsListener paramCrashlyticsListener, PinningInfoProvider paramPinningInfoProvider, boolean paramBoolean)
  {
    this(paramFloat, paramCrashlyticsListener, paramPinningInfoProvider, paramBoolean, ExecutorUtils.buildSingleThreadExecutorService("Crashlytics Exception Handler"));
  }
  
  CrashlyticsCore(float paramFloat, CrashlyticsListener paramCrashlyticsListener, PinningInfoProvider paramPinningInfoProvider, boolean paramBoolean, ExecutorService paramExecutorService)
  {
    this.delay = paramFloat;
    this.listener = paramCrashlyticsListener;
    this.pinningInfo = paramPinningInfoProvider;
    this.disabled = paramBoolean;
    this.executorServiceWrapper = new CrashlyticsExecutorServiceWrapper(paramExecutorService);
  }
  
  private int dipsToPixels(float paramFloat, int paramInt)
  {
    return (int)(paramInt * paramFloat);
  }
  
  private void doLog(int paramInt, String paramString1, String paramString2)
  {
    if (this.disabled) {}
    while (!ensureFabricWithCalled("prior to logging messages.")) {
      return;
    }
    long l1 = System.currentTimeMillis();
    long l2 = this.startTime;
    this.handler.writeToLog(l1 - l2, formatLogMessage(paramInt, paramString1, paramString2));
  }
  
  private static boolean ensureFabricWithCalled(String paramString)
  {
    CrashlyticsCore localCrashlyticsCore = getInstance();
    if ((localCrashlyticsCore == null) || (localCrashlyticsCore.handler == null))
    {
      Fabric.getLogger().e("CrashlyticsCore", "Crashlytics must be initialized by calling Fabric.with(Context) " + paramString, null);
      return false;
    }
    return true;
  }
  
  private void finishInitSynchronously()
  {
    Object localObject = new PriorityCallable()
    {
      public Void call()
        throws Exception
      {
        return CrashlyticsCore.this.doInBackground();
      }
      
      public Priority getPriority()
      {
        return Priority.IMMEDIATE;
      }
    };
    Iterator localIterator = getDependencies().iterator();
    while (localIterator.hasNext()) {
      ((PriorityCallable)localObject).addDependency((Task)localIterator.next());
    }
    localObject = getFabric().getExecutorService().submit((Callable)localObject);
    Fabric.getLogger().d("CrashlyticsCore", "Crashlytics detected incomplete initialization on previous app launch. Will initialize synchronously.");
    try
    {
      ((Future)localObject).get(4L, TimeUnit.SECONDS);
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
      Fabric.getLogger().e("CrashlyticsCore", "Crashlytics was interrupted during initialization.", localInterruptedException);
      return;
    }
    catch (ExecutionException localExecutionException)
    {
      Fabric.getLogger().e("CrashlyticsCore", "Problem encountered during Crashlytics initialization.", localExecutionException);
      return;
    }
    catch (TimeoutException localTimeoutException)
    {
      Fabric.getLogger().e("CrashlyticsCore", "Crashlytics timed out during initialization.", localTimeoutException);
    }
  }
  
  private static String formatLogMessage(int paramInt, String paramString1, String paramString2)
  {
    return CommonUtils.logPriorityToString(paramInt) + "/" + paramString1 + " " + paramString2;
  }
  
  public static CrashlyticsCore getInstance()
  {
    return (CrashlyticsCore)Fabric.getKit(CrashlyticsCore.class);
  }
  
  private boolean getSendDecisionFromUser(final Activity paramActivity, final PromptSettingsData paramPromptSettingsData)
  {
    final DialogStringResolver localDialogStringResolver = new DialogStringResolver(paramActivity, paramPromptSettingsData);
    final OptInLatch localOptInLatch = new OptInLatch(null);
    paramActivity.runOnUiThread(new Runnable()
    {
      public void run()
      {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(paramActivity);
        Object localObject = new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
          {
            CrashlyticsCore.7.this.val$latch.setOptIn(true);
            paramAnonymous2DialogInterface.dismiss();
          }
        };
        float f = paramActivity.getResources().getDisplayMetrics().density;
        int i = CrashlyticsCore.this.dipsToPixels(f, 5);
        TextView localTextView = new TextView(paramActivity);
        localTextView.setAutoLinkMask(15);
        localTextView.setText(localDialogStringResolver.getMessage());
        localTextView.setTextAppearance(paramActivity, 16973892);
        localTextView.setPadding(i, i, i, i);
        localTextView.setFocusable(false);
        ScrollView localScrollView = new ScrollView(paramActivity);
        localScrollView.setPadding(CrashlyticsCore.this.dipsToPixels(f, 14), CrashlyticsCore.this.dipsToPixels(f, 2), CrashlyticsCore.this.dipsToPixels(f, 10), CrashlyticsCore.this.dipsToPixels(f, 12));
        localScrollView.addView(localTextView);
        localBuilder.setView(localScrollView).setTitle(localDialogStringResolver.getTitle()).setCancelable(false).setNeutralButton(localDialogStringResolver.getSendButtonTitle(), (DialogInterface.OnClickListener)localObject);
        if (paramPromptSettingsData.showCancelButton)
        {
          localObject = new DialogInterface.OnClickListener()
          {
            public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
            {
              CrashlyticsCore.7.this.val$latch.setOptIn(false);
              paramAnonymous2DialogInterface.dismiss();
            }
          };
          localBuilder.setNegativeButton(localDialogStringResolver.getCancelButtonTitle(), (DialogInterface.OnClickListener)localObject);
        }
        if (paramPromptSettingsData.showAlwaysSendButton)
        {
          localObject = new DialogInterface.OnClickListener()
          {
            public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
            {
              CrashlyticsCore.this.setShouldSendUserReportsWithoutPrompting(true);
              CrashlyticsCore.7.this.val$latch.setOptIn(true);
              paramAnonymous2DialogInterface.dismiss();
            }
          };
          localBuilder.setPositiveButton(localDialogStringResolver.getAlwaysSendButtonTitle(), (DialogInterface.OnClickListener)localObject);
        }
        localBuilder.show();
      }
    });
    Fabric.getLogger().d("CrashlyticsCore", "Waiting for user opt-in.");
    localOptInLatch.await();
    return localOptInLatch.getOptIn();
  }
  
  private boolean isRequiringBuildId(Context paramContext)
  {
    return CommonUtils.getBooleanResourceValue(paramContext, "com.crashlytics.RequireBuildId", true);
  }
  
  static void recordFatalExceptionEvent(String paramString)
  {
    Answers localAnswers = (Answers)Fabric.getKit(Answers.class);
    if (localAnswers != null) {
      localAnswers.onException(new Crash.FatalException(paramString));
    }
  }
  
  static void recordLoggedExceptionEvent(String paramString)
  {
    Answers localAnswers = (Answers)Fabric.getKit(Answers.class);
    if (localAnswers != null) {
      localAnswers.onException(new Crash.LoggedException(paramString));
    }
  }
  
  private static String sanitizeAttribute(String paramString)
  {
    String str = paramString;
    if (paramString != null)
    {
      paramString = paramString.trim();
      str = paramString;
      if (paramString.length() > 1024) {
        str = paramString.substring(0, 1024);
      }
    }
    return str;
  }
  
  private void setAndValidateKitProperties(Context paramContext, String paramString)
  {
    Object localObject;
    if (this.pinningInfo != null)
    {
      localObject = new CrashlyticsPinningInfoProvider(this.pinningInfo);
      this.httpRequestFactory = new DefaultHttpRequestFactory(Fabric.getLogger());
      this.httpRequestFactory.setPinningInfoProvider((io.fabric.sdk.android.services.network.PinningInfoProvider)localObject);
    }
    for (;;)
    {
      try
      {
        this.packageName = paramContext.getPackageName();
        this.installerPackageName = getIdManager().getInstallerPackageName();
        Fabric.getLogger().d("CrashlyticsCore", "Installer package name is: " + this.installerPackageName);
        localObject = paramContext.getPackageManager().getPackageInfo(this.packageName, 0);
        this.versionCode = Integer.toString(((PackageInfo)localObject).versionCode);
        if (((PackageInfo)localObject).versionName != null) {
          continue;
        }
        localObject = "0.0";
        this.versionName = ((String)localObject);
        this.buildId = CommonUtils.resolveBuildId(paramContext);
      }
      catch (Exception localException)
      {
        Fabric.getLogger().e("CrashlyticsCore", "Error setting up app properties", localException);
        continue;
      }
      getIdManager().getBluetoothMacAddress();
      getBuildIdValidator(this.buildId, isRequiringBuildId(paramContext)).validate(paramString, this.packageName);
      return;
      localObject = null;
      break;
      localObject = ((PackageInfo)localObject).versionName;
    }
  }
  
  boolean canSendWithUserApproval()
  {
    ((Boolean)Settings.getInstance().withSettings(new Settings.SettingsAccess()
    {
      public Boolean usingSettings(SettingsData paramAnonymousSettingsData)
      {
        boolean bool2 = true;
        Activity localActivity = CrashlyticsCore.this.getFabric().getCurrentActivity();
        boolean bool1 = bool2;
        if (localActivity != null)
        {
          bool1 = bool2;
          if (!localActivity.isFinishing())
          {
            bool1 = bool2;
            if (CrashlyticsCore.this.shouldPromptUserBeforeSendingCrashReports()) {
              bool1 = CrashlyticsCore.this.getSendDecisionFromUser(localActivity, paramAnonymousSettingsData.promptData);
            }
          }
        }
        return Boolean.valueOf(bool1);
      }
    }, Boolean.valueOf(true))).booleanValue();
  }
  
  public void crash()
  {
    new CrashTest().indexOutOfBounds();
  }
  
  boolean didPreviousInitializationComplete()
  {
    ((Boolean)this.executorServiceWrapper.executeSyncLoggingException(new Callable()
    {
      public Boolean call()
        throws Exception
      {
        return Boolean.valueOf(CrashlyticsCore.this.initializationMarkerFile.exists());
      }
    })).booleanValue();
  }
  
  protected Void doInBackground()
  {
    markInitializationStarted();
    this.handler.cleanInvalidTempFiles();
    int k = 1;
    j = 1;
    i = k;
    for (;;)
    {
      try
      {
        Object localObject1 = Settings.getInstance().awaitSettingsData();
        if (localObject1 == null)
        {
          i = k;
          Fabric.getLogger().w("CrashlyticsCore", "Received null settings, skipping initialization!");
          return null;
        }
        i = k;
        if (((SettingsData)localObject1).featuresData.collectReports)
        {
          k = 0;
          j = 0;
          i = k;
          this.handler.finalizeSessions();
          i = k;
          localObject1 = getCreateReportSpiCall((SettingsData)localObject1);
          if (localObject1 == null) {
            continue;
          }
          i = k;
          new ReportUploader((CreateReportSpiCall)localObject1).uploadReports(this.delay);
        }
      }
      catch (Exception localException1)
      {
        Fabric.getLogger().e("CrashlyticsCore", "Error dealing with settings", localException1);
        j = i;
        continue;
      }
      finally
      {
        markInitializationComplete();
      }
      if (j != 0) {}
      try
      {
        Fabric.getLogger().d("CrashlyticsCore", "Crash reporting disabled.");
        markInitializationComplete();
        return null;
      }
      catch (Exception localException2)
      {
        Fabric.getLogger().e("CrashlyticsCore", "Problem encountered during Crashlytics initialization.", localException2);
        markInitializationComplete();
        return null;
      }
      i = k;
      Fabric.getLogger().w("CrashlyticsCore", "Unable to create a call to upload reports.");
    }
  }
  
  Map<String, String> getAttributes()
  {
    return Collections.unmodifiableMap(this.attributes);
  }
  
  String getBuildId()
  {
    return this.buildId;
  }
  
  BuildIdValidator getBuildIdValidator(String paramString, boolean paramBoolean)
  {
    return new BuildIdValidator(paramString, paramBoolean);
  }
  
  CreateReportSpiCall getCreateReportSpiCall(SettingsData paramSettingsData)
  {
    if (paramSettingsData != null) {
      return new DefaultCreateReportSpiCall(this, getOverridenSpiEndpoint(), paramSettingsData.appData.reportsUrl, this.httpRequestFactory);
    }
    return null;
  }
  
  SessionEventData getExternalCrashEventData()
  {
    SessionEventData localSessionEventData = null;
    if (this.externalCrashEventDataProvider != null) {
      localSessionEventData = this.externalCrashEventDataProvider.getCrashEventData();
    }
    return localSessionEventData;
  }
  
  CrashlyticsUncaughtExceptionHandler getHandler()
  {
    return this.handler;
  }
  
  public String getIdentifier()
  {
    return "com.crashlytics.sdk.android.crashlytics-core";
  }
  
  String getInstallerPackageName()
  {
    return this.installerPackageName;
  }
  
  String getOverridenSpiEndpoint()
  {
    return CommonUtils.getStringsFileValue(getContext(), "com.crashlytics.ApiEndpoint");
  }
  
  String getPackageName()
  {
    return this.packageName;
  }
  
  public PinningInfoProvider getPinningInfoProvider()
  {
    if (!this.disabled) {
      return this.pinningInfo;
    }
    return null;
  }
  
  File getSdkDirectory()
  {
    if (this.sdkDir == null) {
      this.sdkDir = new FileStoreImpl(this).getFilesDir();
    }
    return this.sdkDir;
  }
  
  SessionSettingsData getSessionSettingsData()
  {
    SettingsData localSettingsData = Settings.getInstance().awaitSettingsData();
    if (localSettingsData == null) {
      return null;
    }
    return localSettingsData.sessionData;
  }
  
  String getUserEmail()
  {
    if (getIdManager().canCollectUserIds()) {
      return this.userEmail;
    }
    return null;
  }
  
  String getUserIdentifier()
  {
    if (getIdManager().canCollectUserIds()) {
      return this.userId;
    }
    return null;
  }
  
  String getUserName()
  {
    if (getIdManager().canCollectUserIds()) {
      return this.userName;
    }
    return null;
  }
  
  public String getVersion()
  {
    return "2.3.4.74";
  }
  
  String getVersionCode()
  {
    return this.versionCode;
  }
  
  String getVersionName()
  {
    return this.versionName;
  }
  
  boolean internalVerifyPinning(URL paramURL)
  {
    boolean bool = false;
    if (getPinningInfoProvider() != null)
    {
      paramURL = this.httpRequestFactory.buildHttpRequest(HttpMethod.GET, paramURL.toString());
      ((HttpsURLConnection)paramURL.getConnection()).setInstanceFollowRedirects(false);
      paramURL.code();
      bool = true;
    }
    return bool;
  }
  
  public void log(int paramInt, String paramString1, String paramString2)
  {
    doLog(paramInt, paramString1, paramString2);
    Fabric.getLogger().log(paramInt, "" + paramString1, "" + paramString2, true);
  }
  
  public void log(String paramString)
  {
    doLog(3, "CrashlyticsCore", paramString);
  }
  
  public void logException(Throwable paramThrowable)
  {
    if (this.disabled) {}
    while (!ensureFabricWithCalled("prior to logging exceptions.")) {
      return;
    }
    if (paramThrowable == null)
    {
      Fabric.getLogger().log(5, "CrashlyticsCore", "Crashlytics is ignoring a request to log a null exception.");
      return;
    }
    this.handler.writeNonFatalException(Thread.currentThread(), paramThrowable);
  }
  
  void markInitializationComplete()
  {
    this.executorServiceWrapper.executeAsync(new Callable()
    {
      public Boolean call()
        throws Exception
      {
        try
        {
          boolean bool = CrashlyticsCore.this.initializationMarkerFile.delete();
          Fabric.getLogger().d("CrashlyticsCore", "Initialization marker file removed: " + bool);
          return Boolean.valueOf(bool);
        }
        catch (Exception localException)
        {
          Fabric.getLogger().e("CrashlyticsCore", "Problem encountered deleting Crashlytics initialization marker.", localException);
        }
        return Boolean.valueOf(false);
      }
    });
  }
  
  void markInitializationStarted()
  {
    this.executorServiceWrapper.executeSyncLoggingException(new Callable()
    {
      public Void call()
        throws Exception
      {
        CrashlyticsCore.this.initializationMarkerFile.createNewFile();
        Fabric.getLogger().d("CrashlyticsCore", "Initialization marker file created.");
        return null;
      }
    });
  }
  
  protected boolean onPreExecute()
  {
    return onPreExecute(super.getContext());
  }
  
  boolean onPreExecute(Context paramContext)
  {
    if (this.disabled) {
      return false;
    }
    Object localObject = new ApiKey().getValue(paramContext);
    if (localObject == null) {
      return false;
    }
    Fabric.getLogger().i("CrashlyticsCore", "Initializing Crashlytics " + getVersion());
    this.initializationMarkerFile = new File(getSdkDirectory(), "initialization_marker");
    boolean bool2 = false;
    try
    {
      setAndValidateKitProperties(paramContext, (String)localObject);
      boolean bool1 = bool2;
      try
      {
        localObject = new SessionDataWriter(getContext(), this.buildId, getPackageName());
        bool1 = bool2;
        Fabric.getLogger().d("CrashlyticsCore", "Installing exception handler...");
        bool1 = bool2;
        this.handler = new CrashlyticsUncaughtExceptionHandler(Thread.getDefaultUncaughtExceptionHandler(), this.listener, this.executorServiceWrapper, getIdManager(), (SessionDataWriter)localObject, this);
        bool1 = bool2;
        bool2 = didPreviousInitializationComplete();
        bool1 = bool2;
        this.handler.openSession();
        bool1 = bool2;
        Thread.setDefaultUncaughtExceptionHandler(this.handler);
        bool1 = bool2;
        Fabric.getLogger().d("CrashlyticsCore", "Successfully installed exception handler.");
        bool1 = bool2;
      }
      catch (Exception localException)
      {
        for (;;)
        {
          Fabric.getLogger().e("CrashlyticsCore", "There was a problem installing the exception handler.", localException);
        }
      }
      if ((bool1) && (CommonUtils.canTryConnection(paramContext)))
      {
        finishInitSynchronously();
        return false;
      }
    }
    catch (CrashlyticsMissingDependencyException paramContext)
    {
      throw new UnmetDependencyException(paramContext);
      return true;
    }
    catch (Exception paramContext)
    {
      Fabric.getLogger().e("CrashlyticsCore", "Crashlytics was not started due to an exception during initialization", paramContext);
    }
    return false;
  }
  
  public void setBool(String paramString, boolean paramBoolean)
  {
    setString(paramString, Boolean.toString(paramBoolean));
  }
  
  public void setDouble(String paramString, double paramDouble)
  {
    setString(paramString, Double.toString(paramDouble));
  }
  
  void setExternalCrashEventDataProvider(CrashEventDataProvider paramCrashEventDataProvider)
  {
    this.externalCrashEventDataProvider = paramCrashEventDataProvider;
  }
  
  public void setFloat(String paramString, float paramFloat)
  {
    setString(paramString, Float.toString(paramFloat));
  }
  
  public void setInt(String paramString, int paramInt)
  {
    setString(paramString, Integer.toString(paramInt));
  }
  
  @Deprecated
  public void setListener(CrashlyticsListener paramCrashlyticsListener)
  {
    try
    {
      Fabric.getLogger().w("CrashlyticsCore", "Use of setListener is deprecated.");
      if (paramCrashlyticsListener == null) {
        throw new IllegalArgumentException("listener must not be null.");
      }
    }
    finally {}
    this.listener = paramCrashlyticsListener;
  }
  
  public void setLong(String paramString, long paramLong)
  {
    setString(paramString, Long.toString(paramLong));
  }
  
  @SuppressLint({"CommitPrefEdits"})
  void setShouldSendUserReportsWithoutPrompting(boolean paramBoolean)
  {
    PreferenceStoreImpl localPreferenceStoreImpl = new PreferenceStoreImpl(this);
    localPreferenceStoreImpl.save(localPreferenceStoreImpl.edit().putBoolean("always_send_reports_opt_in", paramBoolean));
  }
  
  public void setString(String paramString1, String paramString2)
  {
    if (this.disabled) {
      return;
    }
    if (paramString1 == null)
    {
      if ((getContext() != null) && (CommonUtils.isAppDebuggable(getContext()))) {
        throw new IllegalArgumentException("Custom attribute key must not be null.");
      }
      Fabric.getLogger().e("CrashlyticsCore", "Attempting to set custom attribute with null key, ignoring.", null);
      return;
    }
    String str = sanitizeAttribute(paramString1);
    if ((this.attributes.size() < 64) || (this.attributes.containsKey(str)))
    {
      if (paramString2 == null) {}
      for (paramString1 = "";; paramString1 = sanitizeAttribute(paramString2))
      {
        this.attributes.put(str, paramString1);
        this.handler.cacheKeyData(this.attributes);
        return;
      }
    }
    Fabric.getLogger().d("CrashlyticsCore", "Exceeded maximum number of custom attributes (64)");
  }
  
  public void setUserEmail(String paramString)
  {
    if (this.disabled) {
      return;
    }
    this.userEmail = sanitizeAttribute(paramString);
    this.handler.cacheUserData(this.userId, this.userName, this.userEmail);
  }
  
  public void setUserIdentifier(String paramString)
  {
    if (this.disabled) {
      return;
    }
    this.userId = sanitizeAttribute(paramString);
    this.handler.cacheUserData(this.userId, this.userName, this.userEmail);
  }
  
  public void setUserName(String paramString)
  {
    if (this.disabled) {
      return;
    }
    this.userName = sanitizeAttribute(paramString);
    this.handler.cacheUserData(this.userId, this.userName, this.userEmail);
  }
  
  boolean shouldPromptUserBeforeSendingCrashReports()
  {
    ((Boolean)Settings.getInstance().withSettings(new Settings.SettingsAccess()
    {
      public Boolean usingSettings(SettingsData paramAnonymousSettingsData)
      {
        boolean bool = false;
        if (paramAnonymousSettingsData.featuresData.promptEnabled)
        {
          if (!CrashlyticsCore.this.shouldSendReportsWithoutPrompting()) {
            bool = true;
          }
          return Boolean.valueOf(bool);
        }
        return Boolean.valueOf(false);
      }
    }, Boolean.valueOf(false))).booleanValue();
  }
  
  boolean shouldSendReportsWithoutPrompting()
  {
    return new PreferenceStoreImpl(this).get().getBoolean("always_send_reports_opt_in", false);
  }
  
  public boolean verifyPinning(URL paramURL)
  {
    try
    {
      boolean bool = internalVerifyPinning(paramURL);
      return bool;
    }
    catch (Exception paramURL)
    {
      Fabric.getLogger().e("CrashlyticsCore", "Could not verify SSL pinning", paramURL);
    }
    return false;
  }
  
  public static class Builder
  {
    private float delay = -1.0F;
    private boolean disabled = false;
    private CrashlyticsListener listener;
    private PinningInfoProvider pinningInfoProvider;
    
    public CrashlyticsCore build()
    {
      if (this.delay < 0.0F) {
        this.delay = 1.0F;
      }
      return new CrashlyticsCore(this.delay, this.listener, this.pinningInfoProvider, this.disabled);
    }
    
    public Builder delay(float paramFloat)
    {
      if (paramFloat <= 0.0F) {
        throw new IllegalArgumentException("delay must be greater than 0");
      }
      if (this.delay > 0.0F) {
        throw new IllegalStateException("delay already set.");
      }
      this.delay = paramFloat;
      return this;
    }
    
    public Builder disabled(boolean paramBoolean)
    {
      this.disabled = paramBoolean;
      return this;
    }
    
    public Builder listener(CrashlyticsListener paramCrashlyticsListener)
    {
      if (paramCrashlyticsListener == null) {
        throw new IllegalArgumentException("listener must not be null.");
      }
      if (this.listener != null) {
        throw new IllegalStateException("listener already set.");
      }
      this.listener = paramCrashlyticsListener;
      return this;
    }
    
    @Deprecated
    public Builder pinningInfo(PinningInfoProvider paramPinningInfoProvider)
    {
      if (paramPinningInfoProvider == null) {
        throw new IllegalArgumentException("pinningInfoProvider must not be null.");
      }
      if (this.pinningInfoProvider != null) {
        throw new IllegalStateException("pinningInfoProvider already set.");
      }
      this.pinningInfoProvider = paramPinningInfoProvider;
      return this;
    }
  }
  
  private class OptInLatch
  {
    private final CountDownLatch latch = new CountDownLatch(1);
    private boolean send = false;
    
    private OptInLatch() {}
    
    void await()
    {
      try
      {
        this.latch.await();
        return;
      }
      catch (InterruptedException localInterruptedException) {}
    }
    
    boolean getOptIn()
    {
      return this.send;
    }
    
    void setOptIn(boolean paramBoolean)
    {
      this.send = paramBoolean;
      this.latch.countDown();
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/crashlytics/android/core/CrashlyticsCore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */