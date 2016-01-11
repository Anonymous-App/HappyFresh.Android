package com.ad4screen.sdk.plugins;

import android.content.Context;

public abstract interface AdvertiserPlugin
  extends BasePlugin
{
  public abstract String getId(Context paramContext);
  
  public abstract boolean isLimitAdTrackingEnabled(Context paramContext);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/plugins/AdvertiserPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */