package com.ad4screen.sdk.plugins.GooglePlayServices;

import android.content.Context;
import com.ad4screen.sdk.A4SGeofencingService;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.plugins.GeofencePlugin;
import com.ad4screen.sdk.plugins.GooglePlayServices.common.Utils;
import com.ad4screen.sdk.plugins.GooglePlayServices.managers.GeofenceRemover;
import com.ad4screen.sdk.plugins.GooglePlayServices.managers.GeofenceRemover.OnGeofencesRemoveListener;
import com.ad4screen.sdk.plugins.GooglePlayServices.managers.GeofenceRequester;
import com.ad4screen.sdk.plugins.GooglePlayServices.managers.GeofenceRequester.onGeofenceRequestListener;
import com.ad4screen.sdk.plugins.GooglePlayServices.model.SimpleGeofence;
import java.util.ArrayList;

public class Geofencing
  implements GeofencePlugin
{
  private static final int PLUGIN_VERSION = 2;
  private ArrayList<com.google.android.gms.location.Geofence> mGeofencesToAdd;
  private ArrayList<String> mGeofencesToRemove;
  private GeofenceRemover remover;
  private GeofenceRequester requester;
  
  public void add(Context paramContext, com.ad4screen.sdk.plugins.model.Geofence[] paramArrayOfGeofence)
  {
    if (!Utils.checkPlayServices(paramContext))
    {
      Log.error("Geofence Plugin|Google Play Services unavailable");
      return;
    }
    for (;;)
    {
      try
      {
        if (this.requester == null) {
          this.requester = new GeofenceRequester(paramContext, new GeofenceRequester.onGeofenceRequestListener()
          {
            public void onGeofencesAdded(String[] paramAnonymousArrayOfString)
            {
              Log.debug("Geofence Plugin|Added " + paramAnonymousArrayOfString.length + " geofences");
              int i = 0;
              if (i >= paramAnonymousArrayOfString.length) {
                return;
              }
              int j = 0;
              for (;;)
              {
                if (j >= Geofencing.this.mGeofencesToAdd.size())
                {
                  i += 1;
                  break;
                }
                if (((com.google.android.gms.location.Geofence)Geofencing.this.mGeofencesToAdd.get(j)).getRequestId().equals(paramAnonymousArrayOfString[i])) {
                  Geofencing.this.mGeofencesToAdd.remove(j);
                }
                j += 1;
              }
            }
          });
        }
        if (this.mGeofencesToAdd != null) {
          break label148;
        }
        this.mGeofencesToAdd = new ArrayList();
      }
      catch (Exception paramContext)
      {
        Log.error("Geofence Plugin|Exception occured when adding Geofence", paramContext);
        return;
        this.mGeofencesToAdd.add(new SimpleGeofence(paramArrayOfGeofence[i].id, paramArrayOfGeofence[i].latitude, paramArrayOfGeofence[i].longitude, paramArrayOfGeofence[i].radius, -1L, 3).toGeofence());
        i += 1;
        continue;
      }
      catch (Error paramContext)
      {
        Log.error("Geofence Plugin|Error occured when adding Geofence", paramContext);
        return;
      }
      if (i >= paramArrayOfGeofence.length)
      {
        this.requester.addGeofences(this.mGeofencesToAdd);
        return;
      }
      label148:
      int i = 0;
    }
  }
  
  public int getPluginVersion()
  {
    return 2;
  }
  
  public boolean isGeofencingServiceDeclared(Context paramContext)
  {
    return Utils.isServiceDeclared(paramContext, A4SGeofencingService.class);
  }
  
  public void remove(Context paramContext, String[] paramArrayOfString)
  {
    if (!Utils.checkPlayServices(paramContext))
    {
      Log.error("Geofence Plugin|Google Play Services unavailable");
      return;
    }
    for (;;)
    {
      try
      {
        if (this.remover == null) {
          this.remover = new GeofenceRemover(paramContext, new GeofenceRemover.OnGeofencesRemoveListener()
          {
            public void onGeofencesRemoved(String[] paramAnonymousArrayOfString)
            {
              Log.debug("Geofence Plugin|Removed " + paramAnonymousArrayOfString.length + " geofences");
              int i = 0;
              for (;;)
              {
                if (i >= paramAnonymousArrayOfString.length) {
                  return;
                }
                Geofencing.this.mGeofencesToRemove.remove(paramAnonymousArrayOfString[i]);
                i += 1;
              }
            }
          });
        }
        if (this.mGeofencesToRemove != null) {
          break label113;
        }
        this.mGeofencesToRemove = new ArrayList();
      }
      catch (Exception paramContext)
      {
        Log.error("Geofence Plugin|Exception occured when removing Geofence", paramContext);
        return;
        this.mGeofencesToRemove.add(paramArrayOfString[i]);
        i += 1;
        continue;
      }
      catch (Error paramContext)
      {
        Log.error("Geofence Plugin|Error occured when removing Geofence", paramContext);
        return;
      }
      if (i >= paramArrayOfString.length)
      {
        this.remover.removeGeofencesById(this.mGeofencesToRemove);
        return;
      }
      label113:
      int i = 0;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/plugins/GooglePlayServices/Geofencing.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */