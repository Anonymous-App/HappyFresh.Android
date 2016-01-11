package com.adjust.sdk;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONObject;

class PackageBuilder
{
  private static ILogger logger = ;
  private ActivityState activityState;
  private AdjustConfig adjustConfig;
  AdjustAttribution attribution;
  private long createdAt;
  private DeviceInfo deviceInfo;
  Map<String, String> extraParameters;
  String reftag;
  
  public PackageBuilder(AdjustConfig paramAdjustConfig, DeviceInfo paramDeviceInfo, ActivityState paramActivityState, long paramLong)
  {
    this.adjustConfig = paramAdjustConfig;
    this.deviceInfo = paramDeviceInfo;
    this.activityState = paramActivityState.clone();
    this.createdAt = paramLong;
  }
  
  private void addBoolean(Map<String, String> paramMap, String paramString, Boolean paramBoolean)
  {
    if (paramBoolean == null) {
      return;
    }
    if (paramBoolean.booleanValue()) {}
    for (int i = 1;; i = 0)
    {
      addInt(paramMap, paramString, i);
      return;
    }
  }
  
  private void addDate(Map<String, String> paramMap, String paramString, long paramLong)
  {
    if (paramLong < 0L) {
      return;
    }
    addString(paramMap, paramString, Util.dateFormat(paramLong));
  }
  
  private void addDouble(Map<String, String> paramMap, String paramString, Double paramDouble)
  {
    if (paramDouble == null) {
      return;
    }
    addString(paramMap, paramString, String.format(Locale.US, "%.5f", new Object[] { paramDouble }));
  }
  
  private void addDuration(Map<String, String> paramMap, String paramString, long paramLong)
  {
    if (paramLong < 0L) {
      return;
    }
    addInt(paramMap, paramString, (500L + paramLong) / 1000L);
  }
  
  private void addInt(Map<String, String> paramMap, String paramString, long paramLong)
  {
    if (paramLong < 0L) {
      return;
    }
    addString(paramMap, paramString, Long.toString(paramLong));
  }
  
  private void addMapJson(Map<String, String> paramMap1, String paramString, Map<String, String> paramMap2)
  {
    if (paramMap2 == null) {}
    while (paramMap2.size() == 0) {
      return;
    }
    addString(paramMap1, paramString, new JSONObject(paramMap2).toString());
  }
  
  private void addString(Map<String, String> paramMap, String paramString1, String paramString2)
  {
    if (TextUtils.isEmpty(paramString2)) {
      return;
    }
    paramMap.put(paramString1, paramString2);
  }
  
  private void checkDeviceIds(Map<String, String> paramMap)
  {
    if ((!paramMap.containsKey("mac_sha1")) && (!paramMap.containsKey("mac_md5")) && (!paramMap.containsKey("android_id")) && (!paramMap.containsKey("gps_adid"))) {
      logger.error("Missing device id's. Please check if Proguard is correctly set with Adjust SDK", new Object[0]);
    }
  }
  
  private void fillPluginKeys(Map<String, String> paramMap)
  {
    if (this.deviceInfo.pluginKeys == null) {}
    for (;;)
    {
      return;
      Iterator localIterator = this.deviceInfo.pluginKeys.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        addString(paramMap, (String)localEntry.getKey(), (String)localEntry.getValue());
      }
    }
  }
  
  private ActivityPackage getDefaultActivityPackage(ActivityKind paramActivityKind)
  {
    paramActivityKind = new ActivityPackage(paramActivityKind);
    paramActivityKind.setClientSdk(this.deviceInfo.clientSdk);
    return paramActivityKind;
  }
  
  private Map<String, String> getDefaultParameters()
  {
    HashMap localHashMap = new HashMap();
    injectDeviceInfo(localHashMap);
    injectConfig(localHashMap);
    injectActivityState(localHashMap);
    addDate(localHashMap, "created_at", this.createdAt);
    checkDeviceIds(localHashMap);
    return localHashMap;
  }
  
  private String getEventSuffix(AdjustEvent paramAdjustEvent)
  {
    if (paramAdjustEvent.revenue == null) {
      return String.format(Locale.US, " '%s'", new Object[] { paramAdjustEvent.eventToken });
    }
    return String.format(Locale.US, " (%.4f %s, '%s')", new Object[] { paramAdjustEvent.revenue, paramAdjustEvent.currency, paramAdjustEvent.eventToken });
  }
  
  private Map<String, String> getIdsParameters()
  {
    HashMap localHashMap = new HashMap();
    injectDeviceInfoIds(localHashMap);
    injectConfig(localHashMap);
    injectActivityStateIds(localHashMap);
    checkDeviceIds(localHashMap);
    return localHashMap;
  }
  
  private void injectActivityState(Map<String, String> paramMap)
  {
    injectActivityStateIds(paramMap);
    addInt(paramMap, "session_count", this.activityState.sessionCount);
    addInt(paramMap, "subsession_count", this.activityState.subsessionCount);
    addDuration(paramMap, "session_length", this.activityState.sessionLength);
    addDuration(paramMap, "time_spent", this.activityState.timeSpent);
  }
  
  private void injectActivityStateIds(Map<String, String> paramMap)
  {
    addString(paramMap, "android_uuid", this.activityState.uuid);
  }
  
  private void injectAttribution(Map<String, String> paramMap)
  {
    if (this.attribution == null) {
      return;
    }
    addString(paramMap, "tracker", this.attribution.trackerName);
    addString(paramMap, "campaign", this.attribution.campaign);
    addString(paramMap, "adgroup", this.attribution.adgroup);
    addString(paramMap, "creative", this.attribution.creative);
  }
  
  private void injectConfig(Map<String, String> paramMap)
  {
    addString(paramMap, "app_token", this.adjustConfig.appToken);
    addString(paramMap, "environment", this.adjustConfig.environment);
    addBoolean(paramMap, "device_known", this.adjustConfig.knownDevice);
    addBoolean(paramMap, "needs_attribution_data", Boolean.valueOf(this.adjustConfig.hasListener()));
    addString(paramMap, "gps_adid", Util.getPlayAdId(this.adjustConfig.context));
    addBoolean(paramMap, "tracking_enabled", Util.isPlayTrackingEnabled(this.adjustConfig.context));
  }
  
  private void injectDeviceInfo(Map<String, String> paramMap)
  {
    injectDeviceInfoIds(paramMap);
    addString(paramMap, "fb_id", this.deviceInfo.fbAttributionId);
    addString(paramMap, "package_name", this.deviceInfo.packageName);
    addString(paramMap, "app_version", this.deviceInfo.appVersion);
    addString(paramMap, "device_type", this.deviceInfo.deviceType);
    addString(paramMap, "device_name", this.deviceInfo.deviceName);
    addString(paramMap, "device_manufacturer", this.deviceInfo.deviceManufacturer);
    addString(paramMap, "os_name", this.deviceInfo.osName);
    addString(paramMap, "os_version", this.deviceInfo.osVersion);
    addString(paramMap, "language", this.deviceInfo.language);
    addString(paramMap, "country", this.deviceInfo.country);
    addString(paramMap, "screen_size", this.deviceInfo.screenSize);
    addString(paramMap, "screen_format", this.deviceInfo.screenFormat);
    addString(paramMap, "screen_density", this.deviceInfo.screenDensity);
    addString(paramMap, "display_width", this.deviceInfo.displayWidth);
    addString(paramMap, "display_height", this.deviceInfo.displayHeight);
    fillPluginKeys(paramMap);
  }
  
  private void injectDeviceInfoIds(Map<String, String> paramMap)
  {
    addString(paramMap, "mac_sha1", this.deviceInfo.macSha1);
    addString(paramMap, "mac_md5", this.deviceInfo.macShortMd5);
    addString(paramMap, "android_id", this.deviceInfo.androidId);
  }
  
  public ActivityPackage buildAttributionPackage()
  {
    Map localMap = getIdsParameters();
    ActivityPackage localActivityPackage = getDefaultActivityPackage(ActivityKind.ATTRIBUTION);
    localActivityPackage.setPath("attribution");
    localActivityPackage.setSuffix("");
    localActivityPackage.setParameters(localMap);
    return localActivityPackage;
  }
  
  public ActivityPackage buildClickPackage(String paramString, long paramLong)
  {
    Map localMap = getDefaultParameters();
    addString(localMap, "source", paramString);
    addDate(localMap, "click_time", paramLong);
    addString(localMap, "reftag", this.reftag);
    addMapJson(localMap, "params", this.extraParameters);
    injectAttribution(localMap);
    paramString = getDefaultActivityPackage(ActivityKind.CLICK);
    paramString.setPath("/sdk_click");
    paramString.setSuffix("");
    paramString.setParameters(localMap);
    return paramString;
  }
  
  public ActivityPackage buildEventPackage(AdjustEvent paramAdjustEvent)
  {
    Map localMap = getDefaultParameters();
    addInt(localMap, "event_count", this.activityState.eventCount);
    addString(localMap, "event_token", paramAdjustEvent.eventToken);
    addDouble(localMap, "revenue", paramAdjustEvent.revenue);
    addString(localMap, "currency", paramAdjustEvent.currency);
    addMapJson(localMap, "callback_params", paramAdjustEvent.callbackParameters);
    addMapJson(localMap, "partner_params", paramAdjustEvent.partnerParameters);
    ActivityPackage localActivityPackage = getDefaultActivityPackage(ActivityKind.EVENT);
    localActivityPackage.setPath("/event");
    localActivityPackage.setSuffix(getEventSuffix(paramAdjustEvent));
    localActivityPackage.setParameters(localMap);
    return localActivityPackage;
  }
  
  public ActivityPackage buildSessionPackage()
  {
    Map localMap = getDefaultParameters();
    addDuration(localMap, "last_interval", this.activityState.lastInterval);
    addString(localMap, "default_tracker", this.adjustConfig.defaultTracker);
    ActivityPackage localActivityPackage = getDefaultActivityPackage(ActivityKind.SESSION);
    localActivityPackage.setPath("/session");
    localActivityPackage.setSuffix("");
    localActivityPackage.setParameters(localMap);
    return localActivityPackage;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/adjust/sdk/PackageBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */