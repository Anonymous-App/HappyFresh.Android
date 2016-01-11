package com.ad4screen.sdk.plugins;

import android.content.Context;
import com.ad4screen.sdk.plugins.model.Geofence;

public abstract interface GeofencePlugin
  extends BasePlugin
{
  public abstract void add(Context paramContext, Geofence[] paramArrayOfGeofence);
  
  public abstract boolean isGeofencingServiceDeclared(Context paramContext);
  
  public abstract void remove(Context paramContext, String[] paramArrayOfString);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/plugins/GeofencePlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */