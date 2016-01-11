package com.ad4screen.sdk.plugins;

import android.content.Context;
import android.location.Location;
import com.ad4screen.sdk.A4S.Callback;

public abstract interface LocationPlugin
  extends BasePlugin
{
  public abstract boolean connect(Context paramContext, long paramLong1, long paramLong2, A4S.Callback<Location> paramCallback);
  
  public abstract void disconnect();
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/plugins/LocationPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */