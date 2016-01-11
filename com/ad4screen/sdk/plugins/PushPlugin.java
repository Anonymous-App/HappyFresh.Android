package com.ad4screen.sdk.plugins;

import android.content.Context;

public abstract interface PushPlugin
  extends BasePlugin
{
  public abstract String register(Context paramContext, String paramString);
  
  public abstract boolean unregister(Context paramContext);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/plugins/PushPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */