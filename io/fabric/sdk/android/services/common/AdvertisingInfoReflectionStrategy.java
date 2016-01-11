package io.fabric.sdk.android.services.common;

import android.content.Context;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import java.lang.reflect.Method;

class AdvertisingInfoReflectionStrategy
  implements AdvertisingInfoStrategy
{
  private static final String CLASS_NAME_ADVERTISING_ID_CLIENT = "com.google.android.gms.ads.identifier.AdvertisingIdClient";
  private static final String CLASS_NAME_ADVERTISING_ID_CLIENT_INFO = "com.google.android.gms.ads.identifier.AdvertisingIdClient$Info";
  private static final String CLASS_NAME_GOOGLE_PLAY_SERVICES_UTILS = "com.google.android.gms.common.GooglePlayServicesUtil";
  private static final int GOOGLE_PLAY_SERVICES_SUCCESS_CODE = 0;
  private static final String METHOD_NAME_GET_ADVERTISING_ID_INFO = "getAdvertisingIdInfo";
  private static final String METHOD_NAME_GET_ID = "getId";
  private static final String METHOD_NAME_IS_GOOGLE_PLAY_SERVICES_AVAILABLE = "isGooglePlayServicesAvailable";
  private static final String METHOD_NAME_IS_LIMITED_AD_TRACKING_ENABLED = "isLimitAdTrackingEnabled";
  private final Context context;
  
  public AdvertisingInfoReflectionStrategy(Context paramContext)
  {
    this.context = paramContext.getApplicationContext();
  }
  
  private String getAdvertisingId()
  {
    try
    {
      String str = (String)Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient$Info").getMethod("getId", new Class[0]).invoke(getInfo(), new Object[0]);
      return str;
    }
    catch (Exception localException)
    {
      Fabric.getLogger().w("Fabric", "Could not call getId on com.google.android.gms.ads.identifier.AdvertisingIdClient$Info");
    }
    return null;
  }
  
  private Object getInfo()
  {
    try
    {
      Object localObject = Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient").getMethod("getAdvertisingIdInfo", new Class[] { Context.class }).invoke(null, new Object[] { this.context });
      return localObject;
    }
    catch (Exception localException)
    {
      Fabric.getLogger().w("Fabric", "Could not call getAdvertisingIdInfo on com.google.android.gms.ads.identifier.AdvertisingIdClient");
    }
    return null;
  }
  
  private boolean isLimitAdTrackingEnabled()
  {
    try
    {
      boolean bool = ((Boolean)Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient$Info").getMethod("isLimitAdTrackingEnabled", new Class[0]).invoke(getInfo(), new Object[0])).booleanValue();
      return bool;
    }
    catch (Exception localException)
    {
      Fabric.getLogger().w("Fabric", "Could not call isLimitAdTrackingEnabled on com.google.android.gms.ads.identifier.AdvertisingIdClient$Info");
    }
    return false;
  }
  
  public AdvertisingInfo getAdvertisingInfo()
  {
    if (isGooglePlayServiceAvailable(this.context)) {
      return new AdvertisingInfo(getAdvertisingId(), isLimitAdTrackingEnabled());
    }
    return null;
  }
  
  boolean isGooglePlayServiceAvailable(Context paramContext)
  {
    try
    {
      int i = ((Integer)Class.forName("com.google.android.gms.common.GooglePlayServicesUtil").getMethod("isGooglePlayServicesAvailable", new Class[] { Context.class }).invoke(null, new Object[] { paramContext })).intValue();
      return i == 0;
    }
    catch (Exception paramContext) {}
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/common/AdvertisingInfoReflectionStrategy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */