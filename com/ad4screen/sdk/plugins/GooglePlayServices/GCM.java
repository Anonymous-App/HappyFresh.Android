package com.ad4screen.sdk.plugins.GooglePlayServices;

import android.content.Context;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.plugins.GCMPlugin;
import com.ad4screen.sdk.plugins.GooglePlayServices.common.Utils;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import java.io.IOException;

public class GCM
  implements GCMPlugin
{
  private static final int PLUGIN_VERSION = 1;
  GoogleCloudMessaging gcm;
  String registrationId;
  
  public int getPluginVersion()
  {
    return 1;
  }
  
  public String register(Context paramContext, String paramString)
  {
    try
    {
      if (Utils.checkPlayServices(paramContext))
      {
        this.gcm = GoogleCloudMessaging.getInstance(paramContext);
        this.registrationId = this.gcm.register(new String[] { paramString });
      }
      return this.registrationId;
    }
    catch (Exception paramContext)
    {
      for (;;)
      {
        Log.error("Plugin|Error occured when registering to GCM", paramContext);
      }
    }
    catch (Error paramContext)
    {
      for (;;)
      {
        Log.error("Plugin|Error occured when registering to GCM", paramContext);
      }
    }
  }
  
  public boolean unregister(Context paramContext)
  {
    try
    {
      if (Utils.checkPlayServices(paramContext))
      {
        this.gcm = GoogleCloudMessaging.getInstance(paramContext);
        this.gcm.unregister();
        return true;
      }
    }
    catch (IOException paramContext)
    {
      Log.error("Plugin|Exception occured when unregistering to GCM", paramContext);
      return false;
    }
    catch (Error paramContext)
    {
      for (;;)
      {
        Log.error("Plugin|Error occured when unregistering to GCM", paramContext);
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/plugins/GooglePlayServices/GCM.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */