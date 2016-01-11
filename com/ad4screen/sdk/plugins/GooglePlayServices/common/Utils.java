package com.ad4screen.sdk.plugins.GooglePlayServices.common;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import com.ad4screen.sdk.Log;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class Utils
{
  public static boolean checkPlayServices(Context paramContext)
  {
    try
    {
      int i = GooglePlayServicesUtil.isGooglePlayServicesAvailable(paramContext);
      return i == 0;
    }
    catch (NoClassDefFoundError paramContext)
    {
      Log.error("Plugin|GooglePlayServices not found. Please add GooglePlayServices Library to your app", paramContext);
      return false;
    }
    catch (Exception paramContext)
    {
      Log.error("Plugin|Error while checking Google Play Services", paramContext);
    }
    return false;
  }
  
  public static boolean isServiceDeclared(Context paramContext, Class<? extends Service> paramClass)
  {
    if (paramContext == null) {
      return false;
    }
    paramContext = paramContext.getApplicationContext();
    try
    {
      paramContext.getPackageManager().getServiceInfo(new ComponentName(paramContext, paramClass), 128);
      return true;
    }
    catch (Exception paramContext) {}
    return false;
  }
  
  public static String loadServiceMetadata(Context paramContext, String paramString, Class<? extends Service> paramClass)
  {
    if (paramContext == null) {}
    for (;;)
    {
      return null;
      paramContext = paramContext.getApplicationContext();
      try
      {
        paramContext = paramContext.getPackageManager().getServiceInfo(new ComponentName(paramContext, paramClass), 128);
        if ((paramContext.metaData != null) && (paramContext.metaData.containsKey(paramString)))
        {
          paramContext = paramContext.metaData.get(paramString).toString();
          return paramContext;
        }
      }
      catch (Exception paramContext)
      {
        Log.internal("Could not load service metadata", paramContext);
      }
    }
    return null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/plugins/GooglePlayServices/common/Utils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */