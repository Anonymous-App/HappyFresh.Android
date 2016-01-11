package com.ad4screen.sdk.common.plugins;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.plugins.BasePlugin;

public class a
{
  public static <T> BasePlugin a(String paramString, int paramInt)
  {
    try
    {
      BasePlugin localBasePlugin2 = (BasePlugin)Class.forName(paramString).newInstance();
      Log.debug("PluginLoader|" + paramString + " loaded");
      BasePlugin localBasePlugin1 = localBasePlugin2;
      if (localBasePlugin2 != null)
      {
        localBasePlugin1 = localBasePlugin2;
        if (localBasePlugin2.getPluginVersion() != paramInt)
        {
          Log.error("PluginLoader|" + paramString + " version is too old ! Please update it");
          localBasePlugin1 = null;
        }
      }
      return localBasePlugin1;
    }
    catch (Exception localException)
    {
      Log.debug("PluginLoader|" + paramString + " not found");
      return null;
    }
    catch (Error localError)
    {
      Log.debug("PluginLoader|" + paramString + " initialization failed. Maybe you forgot to include a required library?");
    }
    return null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/plugins/a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */