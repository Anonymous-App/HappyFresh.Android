package com.ad4screen.sdk.plugins.beacons;

import android.content.ServiceConnection;

public abstract interface IBeaconServiceConnection<T>
  extends ServiceConnection
{
  public abstract T getProvider();
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/plugins/beacons/IBeaconServiceConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */