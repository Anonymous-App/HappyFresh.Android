package com.ad4screen.sdk.plugins.GooglePlayServices;

import android.content.Context;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.plugins.AdvertiserPlugin;
import com.ad4screen.sdk.plugins.GooglePlayServices.common.Utils;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;

public class AdvertiserId
  implements AdvertiserPlugin
{
  private static int PLUGIN_VERSION = 1;
  
  public String getId(Context paramContext)
  {
    String str = null;
    try
    {
      if (Utils.checkPlayServices(paramContext)) {
        str = AdvertisingIdClient.getAdvertisingIdInfo(paramContext).getId();
      }
      return str;
    }
    catch (Exception paramContext)
    {
      Log.error("Plugin|Error occured when retrieving advertiser id", paramContext);
      return null;
    }
    catch (Error paramContext)
    {
      Log.error("Plugin|Error occured when retrieving advertiser id", paramContext);
    }
    return null;
  }
  
  public int getPluginVersion()
  {
    return PLUGIN_VERSION;
  }
  
  public boolean isLimitAdTrackingEnabled(Context paramContext)
  {
    boolean bool = false;
    try
    {
      if (Utils.checkPlayServices(paramContext)) {
        bool = AdvertisingIdClient.getAdvertisingIdInfo(paramContext).isLimitAdTrackingEnabled();
      }
      return bool;
    }
    catch (Exception paramContext)
    {
      Log.error("Plugin|Error occured when registering to GCM", paramContext);
      return false;
    }
    catch (Error paramContext)
    {
      Log.error("Plugin|Error occured when registering to GCM", paramContext);
    }
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/plugins/GooglePlayServices/AdvertiserId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */