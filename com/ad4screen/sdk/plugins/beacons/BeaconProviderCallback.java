package com.ad4screen.sdk.plugins.beacons;

import java.util.List;

public abstract interface BeaconProviderCallback<T>
{
  public abstract void onProvidersBinded(List<T> paramList);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/plugins/beacons/BeaconProviderCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */