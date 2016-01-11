package com.adjust.sdk;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Locale;
import java.util.Map;

class DeviceInfo
{
  String androidId;
  String appVersion;
  String clientSdk;
  String country;
  String deviceManufacturer;
  String deviceName;
  String deviceType;
  String displayHeight;
  String displayWidth;
  String fbAttributionId;
  String language;
  String macSha1;
  String macShortMd5;
  String osName;
  String osVersion;
  String packageName;
  Map<String, String> pluginKeys;
  String screenDensity;
  String screenFormat;
  String screenSize;
  
  DeviceInfo(Context paramContext, String paramString)
  {
    Object localObject1 = paramContext.getResources();
    DisplayMetrics localDisplayMetrics = ((Resources)localObject1).getDisplayMetrics();
    Object localObject2 = ((Resources)localObject1).getConfiguration();
    localObject1 = ((Configuration)localObject2).locale;
    int i = ((Configuration)localObject2).screenLayout;
    boolean bool = Reflection.isGooglePlayServicesAvailable(paramContext);
    localObject2 = getMacAddress(paramContext, bool);
    this.packageName = getPackageName(paramContext);
    this.appVersion = getAppVersion(paramContext);
    this.deviceType = getDeviceType(i);
    this.deviceName = getDeviceName();
    this.deviceManufacturer = getDeviceManufacturer();
    this.osName = getOsName();
    this.osVersion = getOsVersion();
    this.language = getLanguage((Locale)localObject1);
    this.country = getCountry((Locale)localObject1);
    this.screenSize = getScreenSize(i);
    this.screenFormat = getScreenFormat(i);
    this.screenDensity = getScreenDensity(localDisplayMetrics);
    this.displayWidth = getDisplayWidth(localDisplayMetrics);
    this.displayHeight = getDisplayHeight(localDisplayMetrics);
    this.clientSdk = getClientSdk(paramString);
    this.androidId = getAndroidId(paramContext, bool);
    this.fbAttributionId = getFacebookAttributionId(paramContext);
    this.pluginKeys = Reflection.getPluginKeys(paramContext);
    this.macSha1 = getMacSha1((String)localObject2);
    this.macShortMd5 = getMacShortMd5((String)localObject2);
  }
  
  private static String convertToHex(byte[] paramArrayOfByte)
  {
    BigInteger localBigInteger = new BigInteger(1, paramArrayOfByte);
    paramArrayOfByte = "%0" + (paramArrayOfByte.length << 1) + "x";
    return String.format(Locale.US, paramArrayOfByte, new Object[] { localBigInteger });
  }
  
  private String getAndroidId(Context paramContext, boolean paramBoolean)
  {
    if (!paramBoolean) {
      return Reflection.getAndroidId(paramContext);
    }
    return null;
  }
  
  private String getAppVersion(Context paramContext)
  {
    try
    {
      paramContext = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0).versionName;
      return paramContext;
    }
    catch (PackageManager.NameNotFoundException paramContext) {}
    return null;
  }
  
  private String getClientSdk(String paramString)
  {
    if (paramString == null) {
      return "android4.0.2";
    }
    return String.format(Locale.US, "%s@%s", new Object[] { paramString, "android4.0.2" });
  }
  
  private String getCountry(Locale paramLocale)
  {
    return paramLocale.getCountry();
  }
  
  private String getDeviceManufacturer()
  {
    return Build.MANUFACTURER;
  }
  
  private String getDeviceName()
  {
    return Build.MODEL;
  }
  
  private String getDeviceType(int paramInt)
  {
    switch (paramInt & 0xF)
    {
    default: 
      return null;
    case 1: 
    case 2: 
      return "phone";
    }
    return "tablet";
  }
  
  private String getDisplayHeight(DisplayMetrics paramDisplayMetrics)
  {
    return String.valueOf(paramDisplayMetrics.heightPixels);
  }
  
  private String getDisplayWidth(DisplayMetrics paramDisplayMetrics)
  {
    return String.valueOf(paramDisplayMetrics.widthPixels);
  }
  
  private String getFacebookAttributionId(Context paramContext)
  {
    try
    {
      paramContext = paramContext.getContentResolver().query(Uri.parse("content://com.facebook.katana.provider.AttributionIdProvider"), new String[] { "aid" }, null, null, null);
      if (paramContext == null) {
        return null;
      }
      if (!paramContext.moveToFirst())
      {
        paramContext.close();
        return null;
      }
      String str = paramContext.getString(paramContext.getColumnIndex("aid"));
      paramContext.close();
      return str;
    }
    catch (Exception paramContext) {}
    return null;
  }
  
  private String getLanguage(Locale paramLocale)
  {
    return paramLocale.getLanguage();
  }
  
  private String getMacAddress(Context paramContext, boolean paramBoolean)
  {
    if (!paramBoolean)
    {
      if (Util.checkPermission(paramContext, "android.permission.ACCESS_WIFI_STATE")) {
        AdjustFactory.getLogger().warn("Missing permission: ACCESS_WIFI_STATE", new Object[0]);
      }
      return Reflection.getMacAddress(paramContext);
    }
    return null;
  }
  
  private String getMacSha1(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    return sha1(paramString);
  }
  
  private String getMacShortMd5(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    return md5(paramString.replaceAll(":", ""));
  }
  
  private String getOsName()
  {
    return "android";
  }
  
  private String getOsVersion()
  {
    String str = "" + Build.VERSION.SDK_INT;
    this.osVersion = str;
    return str;
  }
  
  private String getPackageName(Context paramContext)
  {
    return paramContext.getPackageName();
  }
  
  private String getScreenDensity(DisplayMetrics paramDisplayMetrics)
  {
    int i = paramDisplayMetrics.densityDpi;
    if (i == 0) {
      return null;
    }
    if (i < 140) {
      return "low";
    }
    if (i > 200) {
      return "high";
    }
    return "medium";
  }
  
  private String getScreenFormat(int paramInt)
  {
    switch (paramInt & 0x30)
    {
    default: 
      return null;
    case 32: 
      return "long";
    }
    return "normal";
  }
  
  private String getScreenSize(int paramInt)
  {
    switch (paramInt & 0xF)
    {
    default: 
      return null;
    case 1: 
      return "small";
    case 2: 
      return "normal";
    case 3: 
      return "large";
    }
    return "xlarge";
  }
  
  private String hash(String paramString1, String paramString2)
  {
    try
    {
      paramString1 = paramString1.getBytes("UTF-8");
      paramString2 = MessageDigest.getInstance(paramString2);
      paramString2.update(paramString1, 0, paramString1.length);
      paramString1 = convertToHex(paramString2.digest());
      return paramString1;
    }
    catch (Exception paramString1) {}
    return null;
  }
  
  private String md5(String paramString)
  {
    return hash(paramString, "MD5");
  }
  
  private String sha1(String paramString)
  {
    return hash(paramString, "SHA-1");
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/adjust/sdk/DeviceInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */