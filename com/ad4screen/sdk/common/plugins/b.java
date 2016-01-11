package com.ad4screen.sdk.common.plugins;

import com.ad4screen.sdk.plugins.ADMPlugin;
import com.ad4screen.sdk.plugins.AdvertiserPlugin;
import com.ad4screen.sdk.plugins.BasePlugin;
import com.ad4screen.sdk.plugins.BeaconPlugin;
import com.ad4screen.sdk.plugins.GCMPlugin;
import com.ad4screen.sdk.plugins.GeofencePlugin;
import com.ad4screen.sdk.plugins.LocationPlugin;

public class b
{
  public static GCMPlugin a()
  {
    BasePlugin localBasePlugin = a.a("com.ad4screen.sdk.plugins.GooglePlayServices.GCM", 1);
    if ((localBasePlugin instanceof GCMPlugin)) {
      return (GCMPlugin)localBasePlugin;
    }
    return null;
  }
  
  public static ADMPlugin b()
  {
    BasePlugin localBasePlugin = a.a("com.ad4screen.sdk.plugins.amazon.ADM", 1);
    if ((localBasePlugin instanceof ADMPlugin)) {
      return (ADMPlugin)localBasePlugin;
    }
    return null;
  }
  
  public static AdvertiserPlugin c()
  {
    BasePlugin localBasePlugin = a.a("com.ad4screen.sdk.plugins.GooglePlayServices.AdvertiserId", 1);
    if ((localBasePlugin instanceof AdvertiserPlugin)) {
      return (AdvertiserPlugin)localBasePlugin;
    }
    return null;
  }
  
  public static GeofencePlugin d()
  {
    BasePlugin localBasePlugin = a.a("com.ad4screen.sdk.plugins.GooglePlayServices.Geofencing", 2);
    if ((localBasePlugin instanceof GeofencePlugin)) {
      return (GeofencePlugin)localBasePlugin;
    }
    return null;
  }
  
  public static BeaconPlugin e()
  {
    BasePlugin localBasePlugin = a.a("com.ad4screen.sdk.plugins.beacons.Beacon", 1);
    if ((localBasePlugin instanceof BeaconPlugin)) {
      return (BeaconPlugin)localBasePlugin;
    }
    return null;
  }
  
  public static LocationPlugin f()
  {
    BasePlugin localBasePlugin = a.a("com.ad4screen.sdk.plugins.GooglePlayServices.Location", 1);
    if ((localBasePlugin instanceof LocationPlugin)) {
      return (LocationPlugin)localBasePlugin;
    }
    return null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/plugins/b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */