package com.adjust.sdk;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

public class ActivityHandler
  extends HandlerThread
  implements IActivityHandler
{
  private static final String ACTIVITY_STATE_NAME = "Activity state";
  private static final String ADJUST_PREFIX = "adjust_";
  private static final String ATTRIBUTION_NAME = "Attribution";
  private static long SESSION_INTERVAL = 0L;
  private static long SUBSESSION_INTERVAL = 0L;
  private static long TIMER_INTERVAL = 0L;
  private static long TIMER_START = 0L;
  private static final String TIME_TRAVEL = "Time travel!";
  private static ScheduledExecutorService timer;
  private ActivityState activityState;
  private AdjustConfig adjustConfig;
  private AdjustAttribution attribution;
  private IAttributionHandler attributionHandler;
  private DeviceInfo deviceInfo;
  private boolean enabled;
  private ILogger logger;
  private boolean offline;
  private IPackageHandler packageHandler;
  private SessionHandler sessionHandler;
  
  private ActivityHandler(AdjustConfig paramAdjustConfig)
  {
    super("Adjust", 1);
    setDaemon(true);
    start();
    this.logger = AdjustFactory.getLogger();
    this.sessionHandler = new SessionHandler(getLooper(), this);
    this.enabled = true;
    init(paramAdjustConfig);
    paramAdjustConfig = Message.obtain();
    paramAdjustConfig.arg1 = 72631;
    this.sessionHandler.sendMessage(paramAdjustConfig);
  }
  
  private ActivityPackage buildQueryStringClickPackage(String paramString1, String paramString2, long paramLong)
  {
    if (paramString1 == null) {
      return null;
    }
    long l = System.currentTimeMillis();
    HashMap localHashMap = new HashMap();
    AdjustAttribution localAdjustAttribution = new AdjustAttribution();
    int j = 0;
    paramString1 = paramString1.split("&");
    int k = paramString1.length;
    int i = 0;
    while (i < k)
    {
      if (readQueryString(paramString1[i], localHashMap, localAdjustAttribution)) {
        j = 1;
      }
      i += 1;
    }
    if (j == 0) {
      return null;
    }
    paramString1 = (String)localHashMap.remove("reftag");
    PackageBuilder localPackageBuilder = new PackageBuilder(this.adjustConfig, this.deviceInfo, this.activityState, l);
    localPackageBuilder.extraParameters = localHashMap;
    localPackageBuilder.attribution = localAdjustAttribution;
    localPackageBuilder.reftag = paramString1;
    return localPackageBuilder.buildClickPackage(paramString2, paramLong);
  }
  
  private void checkAttributionState()
  {
    if ((this.attribution == null) || (this.activityState.askingAttribution)) {
      getAttributionHandler().getAttribution();
    }
  }
  
  private boolean checkEvent(AdjustEvent paramAdjustEvent)
  {
    if (paramAdjustEvent == null)
    {
      this.logger.error("Event missing", new Object[0]);
      return false;
    }
    if (!paramAdjustEvent.isValid())
    {
      this.logger.error("Event not initialized correctly", new Object[0]);
      return false;
    }
    return true;
  }
  
  public static boolean deleteActivityState(Context paramContext)
  {
    return paramContext.deleteFile("AdjustIoActivityState");
  }
  
  public static boolean deleteAttribution(Context paramContext)
  {
    return paramContext.deleteFile("AdjustAttribution");
  }
  
  private void endInternal()
  {
    this.packageHandler.pauseSending();
    getAttributionHandler().pauseSending();
    stopTimer();
    if (updateActivityState(System.currentTimeMillis())) {
      writeActivityState();
    }
  }
  
  private void finishedTrackingActivityInternal(JSONObject paramJSONObject)
  {
    if (paramJSONObject == null) {
      return;
    }
    launchDeeplinkMain(paramJSONObject.optString("deeplink", null));
    getAttributionHandler().checkAttribution(paramJSONObject);
  }
  
  private IAttributionHandler getAttributionHandler()
  {
    if (this.attributionHandler == null) {
      this.attributionHandler = AdjustFactory.getAttributionHandler(this, getAttributionPackage(), toPause());
    }
    return this.attributionHandler;
  }
  
  public static ActivityHandler getInstance(AdjustConfig paramAdjustConfig)
  {
    if (paramAdjustConfig == null)
    {
      AdjustFactory.getLogger().error("AdjustConfig missing", new Object[0]);
      return null;
    }
    if (!paramAdjustConfig.isValid())
    {
      AdjustFactory.getLogger().error("AdjustConfig not initialized correctly", new Object[0]);
      return null;
    }
    return new ActivityHandler(paramAdjustConfig);
  }
  
  private void initInternal()
  {
    TIMER_INTERVAL = AdjustFactory.getTimerInterval();
    TIMER_START = AdjustFactory.getTimerStart();
    SESSION_INTERVAL = AdjustFactory.getSessionInterval();
    SUBSESSION_INTERVAL = AdjustFactory.getSubsessionInterval();
    this.deviceInfo = new DeviceInfo(this.adjustConfig.context, this.adjustConfig.sdkPrefix);
    if (this.adjustConfig.environment == "production") {
      this.logger.setLogLevel(LogLevel.ASSERT);
    }
    for (;;)
    {
      if (this.adjustConfig.eventBufferingEnabled.booleanValue()) {
        this.logger.info("Event buffering is enabled", new Object[0]);
      }
      if (Util.getPlayAdId(this.adjustConfig.context) == null) {
        this.logger.info("Unable to get Google Play Services Advertising ID at start time", new Object[0]);
      }
      if (this.adjustConfig.defaultTracker != null) {
        this.logger.info("Default tracker: '%s'", new Object[] { this.adjustConfig.defaultTracker });
      }
      if (this.adjustConfig.referrer != null) {
        sendReferrer(this.adjustConfig.referrer, this.adjustConfig.referrerClickTime);
      }
      readAttribution();
      readActivityState();
      this.packageHandler = AdjustFactory.getPackageHandler(this, this.adjustConfig.context, toPause());
      startInternal();
      return;
      this.logger.setLogLevel(this.adjustConfig.logLevel);
    }
  }
  
  private void launchAttributionListener()
  {
    if (this.adjustConfig.onAttributionChangedListener == null) {
      return;
    }
    new Handler(this.adjustConfig.context.getMainLooper()).post(new Runnable()
    {
      public void run()
      {
        ActivityHandler.this.adjustConfig.onAttributionChangedListener.onAttributionChanged(ActivityHandler.this.attribution);
      }
    });
  }
  
  private void launchDeeplinkMain(String paramString)
  {
    if (paramString == null) {
      return;
    }
    Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse(paramString));
    localIntent.setFlags(268435456);
    if (this.adjustConfig.context.getPackageManager().queryIntentActivities(localIntent, 0).size() > 0) {}
    for (int i = 1; i == 0; i = 0)
    {
      this.logger.error("Unable to open deep link (%s)", new Object[] { paramString });
      return;
    }
    this.logger.info("Open deep link (%s)", new Object[] { paramString });
    this.adjustConfig.context.startActivity(localIntent);
  }
  
  private void processSession()
  {
    long l1 = System.currentTimeMillis();
    if (this.activityState == null)
    {
      this.activityState = new ActivityState();
      this.activityState.sessionCount = 1;
      transferSessionPackage(l1);
      this.activityState.resetSessionAttributes(l1);
      this.activityState.enabled = this.enabled;
      writeActivityState();
    }
    long l2;
    do
    {
      return;
      l2 = l1 - this.activityState.lastActivity;
      if (l2 < 0L)
      {
        this.logger.error("Time travel!", new Object[0]);
        this.activityState.lastActivity = l1;
        writeActivityState();
        return;
      }
      if (l2 > SESSION_INTERVAL)
      {
        localActivityState = this.activityState;
        localActivityState.sessionCount += 1;
        this.activityState.lastInterval = l2;
        transferSessionPackage(l1);
        this.activityState.resetSessionAttributes(l1);
        writeActivityState();
        return;
      }
    } while (l2 <= SUBSESSION_INTERVAL);
    ActivityState localActivityState = this.activityState;
    localActivityState.subsessionCount += 1;
    localActivityState = this.activityState;
    localActivityState.sessionLength += l2;
    this.activityState.lastActivity = l1;
    writeActivityState();
    this.logger.info("Started subsession %d of session %d", new Object[] { Integer.valueOf(this.activityState.subsessionCount), Integer.valueOf(this.activityState.sessionCount) });
  }
  
  private void readActivityState()
  {
    this.activityState = ((ActivityState)Util.readObject(this.adjustConfig.context, "AdjustIoActivityState", "Activity state"));
  }
  
  private void readAttribution()
  {
    this.attribution = ((AdjustAttribution)Util.readObject(this.adjustConfig.context, "AdjustAttribution", "Attribution"));
  }
  
  private void readOpenUrlInternal(Uri paramUri, long paramLong)
  {
    if (paramUri == null) {}
    do
    {
      return;
      paramUri = buildQueryStringClickPackage(paramUri.getQuery(), "deeplink", paramLong);
    } while (paramUri == null);
    getAttributionHandler().getAttribution();
    this.packageHandler.sendClickPackage(paramUri);
  }
  
  private boolean readQueryString(String paramString, Map<String, String> paramMap, AdjustAttribution paramAdjustAttribution)
  {
    Object localObject = paramString.split("=");
    if (localObject.length != 2) {}
    do
    {
      do
      {
        do
        {
          return false;
          paramString = localObject[0];
        } while (!paramString.startsWith("adjust_"));
        localObject = localObject[1];
      } while (((String)localObject).length() == 0);
      paramString = paramString.substring("adjust_".length());
    } while (paramString.length() == 0);
    if (!trySetAttribution(paramAdjustAttribution, paramString, (String)localObject)) {
      paramMap.put(paramString, localObject);
    }
    return true;
  }
  
  private void saveAttribution(AdjustAttribution paramAdjustAttribution)
  {
    this.attribution = paramAdjustAttribution;
    writeAttribution();
  }
  
  private void sendReferrerInternal(String paramString, long paramLong)
  {
    paramString = buildQueryStringClickPackage(paramString, "reftag", paramLong);
    if (paramString == null) {
      return;
    }
    getAttributionHandler().getAttribution();
    this.packageHandler.sendClickPackage(paramString);
  }
  
  private void startInternal()
  {
    if ((this.activityState != null) && (!this.activityState.enabled)) {
      return;
    }
    updateStatusInternal();
    processSession();
    checkAttributionState();
    startTimer();
  }
  
  private void startTimer()
  {
    stopTimer();
    if (!this.activityState.enabled) {
      return;
    }
    timer = Executors.newSingleThreadScheduledExecutor();
    timer.scheduleWithFixedDelay(new Runnable()
    {
      public void run()
      {
        ActivityHandler.this.timerFired();
      }
    }, TIMER_START, TIMER_INTERVAL, TimeUnit.MILLISECONDS);
  }
  
  private void stopTimer()
  {
    if (timer != null)
    {
      timer.shutdown();
      timer = null;
    }
  }
  
  private void timerFired()
  {
    if (!this.activityState.enabled) {
      stopTimer();
    }
    do
    {
      return;
      this.packageHandler.sendFirstPackage();
    } while (!updateActivityState(System.currentTimeMillis()));
    writeActivityState();
  }
  
  private boolean toPause()
  {
    return (this.offline) || (!isEnabled());
  }
  
  private void trackEventInternal(AdjustEvent paramAdjustEvent)
  {
    if (!checkEvent(paramAdjustEvent)) {}
    while (!this.activityState.enabled) {
      return;
    }
    long l = System.currentTimeMillis();
    ActivityState localActivityState = this.activityState;
    localActivityState.eventCount += 1;
    updateActivityState(l);
    paramAdjustEvent = new PackageBuilder(this.adjustConfig, this.deviceInfo, this.activityState, l).buildEventPackage(paramAdjustEvent);
    this.packageHandler.addPackage(paramAdjustEvent);
    if (this.adjustConfig.eventBufferingEnabled.booleanValue()) {
      this.logger.info("Buffered event %s", new Object[] { paramAdjustEvent.getSuffix() });
    }
    for (;;)
    {
      writeActivityState();
      return;
      this.packageHandler.sendFirstPackage();
    }
  }
  
  private void transferSessionPackage(long paramLong)
  {
    ActivityPackage localActivityPackage = new PackageBuilder(this.adjustConfig, this.deviceInfo, this.activityState, paramLong).buildSessionPackage();
    this.packageHandler.addPackage(localActivityPackage);
    this.packageHandler.sendFirstPackage();
  }
  
  private boolean trySetAttribution(AdjustAttribution paramAdjustAttribution, String paramString1, String paramString2)
  {
    if (paramString1.equals("tracker"))
    {
      paramAdjustAttribution.trackerName = paramString2;
      return true;
    }
    if (paramString1.equals("campaign"))
    {
      paramAdjustAttribution.campaign = paramString2;
      return true;
    }
    if (paramString1.equals("adgroup"))
    {
      paramAdjustAttribution.adgroup = paramString2;
      return true;
    }
    if (paramString1.equals("creative"))
    {
      paramAdjustAttribution.creative = paramString2;
      return true;
    }
    return false;
  }
  
  private boolean updateActivityState(long paramLong)
  {
    long l = paramLong - this.activityState.lastActivity;
    if (l > SESSION_INTERVAL) {
      return false;
    }
    this.activityState.lastActivity = paramLong;
    if (l < 0L) {
      this.logger.error("Time travel!", new Object[0]);
    }
    for (;;)
    {
      return true;
      ActivityState localActivityState = this.activityState;
      localActivityState.sessionLength += l;
      localActivityState = this.activityState;
      localActivityState.timeSpent += l;
    }
  }
  
  private void updateAttributionHandlerStatus()
  {
    if (this.attributionHandler == null) {
      return;
    }
    if (toPause())
    {
      this.attributionHandler.pauseSending();
      return;
    }
    this.attributionHandler.resumeSending();
  }
  
  private void updatePackageHandlerStatus()
  {
    if (this.packageHandler == null) {
      return;
    }
    if (toPause())
    {
      this.packageHandler.pauseSending();
      return;
    }
    this.packageHandler.resumeSending();
  }
  
  private void updateStatus()
  {
    Message localMessage = Message.obtain();
    localMessage.arg1 = 72638;
    this.sessionHandler.sendMessage(localMessage);
  }
  
  private void updateStatusInternal()
  {
    updateAttributionHandlerStatus();
    updatePackageHandlerStatus();
  }
  
  private void writeActivityState()
  {
    Util.writeObject(this.activityState, this.adjustConfig.context, "AdjustIoActivityState", "Activity state");
  }
  
  private void writeAttribution()
  {
    Util.writeObject(this.attribution, this.adjustConfig.context, "AdjustAttribution", "Attribution");
  }
  
  public void finishedTrackingActivity(JSONObject paramJSONObject)
  {
    if (paramJSONObject == null) {
      return;
    }
    Message localMessage = Message.obtain();
    localMessage.arg1 = 72635;
    localMessage.obj = paramJSONObject;
    this.sessionHandler.sendMessage(localMessage);
  }
  
  public ActivityPackage getAttributionPackage()
  {
    long l = System.currentTimeMillis();
    return new PackageBuilder(this.adjustConfig, this.deviceInfo, this.activityState, l).buildAttributionPackage();
  }
  
  public void init(AdjustConfig paramAdjustConfig)
  {
    this.adjustConfig = paramAdjustConfig;
  }
  
  public boolean isEnabled()
  {
    if (this.activityState != null) {
      return this.activityState.enabled;
    }
    return this.enabled;
  }
  
  public void readOpenUrl(Uri paramUri, long paramLong)
  {
    Message localMessage = Message.obtain();
    localMessage.arg1 = 72636;
    localMessage.obj = new UrlClickTime(paramUri, paramLong);
    this.sessionHandler.sendMessage(localMessage);
  }
  
  public void sendReferrer(String paramString, long paramLong)
  {
    Message localMessage = Message.obtain();
    localMessage.arg1 = 72637;
    localMessage.obj = new ReferrerClickTime(paramString, paramLong);
    this.sessionHandler.sendMessage(localMessage);
  }
  
  public void setAskingAttribution(boolean paramBoolean)
  {
    this.activityState.askingAttribution = paramBoolean;
    writeActivityState();
  }
  
  public void setEnabled(boolean paramBoolean)
  {
    if (paramBoolean == this.enabled)
    {
      if (paramBoolean)
      {
        this.logger.debug("Adjust already enabled", new Object[0]);
        return;
      }
      this.logger.debug("Adjust already disabled", new Object[0]);
      return;
    }
    this.enabled = paramBoolean;
    if (this.activityState != null) {
      this.activityState.enabled = paramBoolean;
    }
    if (paramBoolean)
    {
      if (toPause()) {
        this.logger.info("Package and attribution handler remain paused due to the SDK is offline", new Object[0]);
      }
      for (;;)
      {
        trackSubsessionStart();
        return;
        this.logger.info("Resuming package handler and attribution handler to enabled the SDK", new Object[0]);
      }
    }
    this.logger.info("Pausing package handler and attribution handler to disable the SDK", new Object[0]);
    trackSubsessionEnd();
  }
  
  public void setOfflineMode(boolean paramBoolean)
  {
    if (paramBoolean == this.offline)
    {
      if (paramBoolean)
      {
        this.logger.debug("Adjust already in offline mode", new Object[0]);
        return;
      }
      this.logger.debug("Adjust already in online mode", new Object[0]);
      return;
    }
    this.offline = paramBoolean;
    if (paramBoolean) {
      this.logger.info("Pausing package and attribution handler to put in offline mode", new Object[0]);
    }
    for (;;)
    {
      updateStatus();
      return;
      if (toPause()) {
        this.logger.info("Package and attribution handler remain paused because the SDK is disabled", new Object[0]);
      } else {
        this.logger.info("Resuming package handler and attribution handler to put in online mode", new Object[0]);
      }
    }
  }
  
  public void trackEvent(AdjustEvent paramAdjustEvent)
  {
    Message localMessage = Message.obtain();
    localMessage.arg1 = 72634;
    localMessage.obj = paramAdjustEvent;
    this.sessionHandler.sendMessage(localMessage);
  }
  
  public void trackSubsessionEnd()
  {
    Message localMessage = Message.obtain();
    localMessage.arg1 = 72633;
    this.sessionHandler.sendMessage(localMessage);
  }
  
  public void trackSubsessionStart()
  {
    Message localMessage = Message.obtain();
    localMessage.arg1 = 72632;
    this.sessionHandler.sendMessage(localMessage);
  }
  
  public boolean tryUpdateAttribution(AdjustAttribution paramAdjustAttribution)
  {
    if (paramAdjustAttribution == null) {}
    while (paramAdjustAttribution.equals(this.attribution)) {
      return false;
    }
    saveAttribution(paramAdjustAttribution);
    launchAttributionListener();
    return true;
  }
  
  private class ReferrerClickTime
  {
    long clickTime;
    String referrer;
    
    ReferrerClickTime(String paramString, long paramLong)
    {
      this.referrer = paramString;
      this.clickTime = paramLong;
    }
  }
  
  private static final class SessionHandler
    extends Handler
  {
    private static final int BASE_ADDRESS = 72630;
    private static final int DEEP_LINK = 72636;
    private static final int END = 72633;
    private static final int EVENT = 72634;
    private static final int FINISH_TRACKING = 72635;
    private static final int INIT = 72631;
    private static final int SEND_REFERRER = 72637;
    private static final int START = 72632;
    private static final int UPDATE_STATUS = 72638;
    private final WeakReference<ActivityHandler> sessionHandlerReference;
    
    protected SessionHandler(Looper paramLooper, ActivityHandler paramActivityHandler)
    {
      super();
      this.sessionHandlerReference = new WeakReference(paramActivityHandler);
    }
    
    public void handleMessage(Message paramMessage)
    {
      super.handleMessage(paramMessage);
      ActivityHandler localActivityHandler = (ActivityHandler)this.sessionHandlerReference.get();
      if (localActivityHandler == null) {
        return;
      }
      switch (paramMessage.arg1)
      {
      default: 
        return;
      case 72631: 
        localActivityHandler.initInternal();
        return;
      case 72632: 
        localActivityHandler.startInternal();
        return;
      case 72633: 
        localActivityHandler.endInternal();
        return;
      case 72634: 
        localActivityHandler.trackEventInternal((AdjustEvent)paramMessage.obj);
        return;
      case 72635: 
        localActivityHandler.finishedTrackingActivityInternal((JSONObject)paramMessage.obj);
        return;
      case 72636: 
        paramMessage = (ActivityHandler.UrlClickTime)paramMessage.obj;
        localActivityHandler.readOpenUrlInternal(paramMessage.url, paramMessage.clickTime);
        return;
      case 72637: 
        paramMessage = (ActivityHandler.ReferrerClickTime)paramMessage.obj;
        localActivityHandler.sendReferrerInternal(paramMessage.referrer, paramMessage.clickTime);
        return;
      }
      localActivityHandler.updateStatusInternal();
    }
  }
  
  private class UrlClickTime
  {
    long clickTime;
    Uri url;
    
    UrlClickTime(Uri paramUri, long paramLong)
    {
      this.url = paramUri;
      this.clickTime = paramLong;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/adjust/sdk/ActivityHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */