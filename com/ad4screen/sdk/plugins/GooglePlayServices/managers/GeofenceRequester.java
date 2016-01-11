package com.ad4screen.sdk.plugins.GooglePlayServices.managers;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.ad4screen.sdk.A4SGeofencingService;
import com.ad4screen.sdk.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingApi;
import com.google.android.gms.location.LocationServices;
import java.util.ArrayList;
import java.util.List;

public class GeofenceRequester
  implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<Status>
{
  private static final int DEFAULT_BACKOFF_MS = 3000;
  private static final int MAX_BACKOFF_MS = 3600000;
  private final Context mContext;
  private int mCurrentBackoff;
  private PendingIntent mGeofencePendingIntent;
  private ArrayList<Geofence> mGeofencesToAdd;
  private boolean mInProgress;
  private onGeofenceRequestListener mListener;
  private GoogleApiClient mLocationClient;
  
  public GeofenceRequester(Context paramContext, onGeofenceRequestListener paramonGeofenceRequestListener)
  {
    this.mContext = paramContext;
    this.mGeofencePendingIntent = null;
    this.mLocationClient = null;
    this.mInProgress = false;
    this.mListener = paramonGeofenceRequestListener;
  }
  
  private void continueAddGeofences()
  {
    this.mGeofencePendingIntent = createRequestPendingIntent();
    LocationServices.GeofencingApi.addGeofences(this.mLocationClient, this.mGeofencesToAdd, this.mGeofencePendingIntent).setResultCallback(this);
  }
  
  private PendingIntent createRequestPendingIntent()
  {
    if (this.mGeofencePendingIntent != null) {
      return this.mGeofencePendingIntent;
    }
    Intent localIntent = new Intent(this.mContext, A4SGeofencingService.class);
    return PendingIntent.getService(this.mContext, 0, localIntent, 134217728);
  }
  
  private GoogleApiClient getLocationClient()
  {
    if (this.mLocationClient == null) {
      this.mLocationClient = new GoogleApiClient.Builder(this.mContext).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
    }
    return this.mLocationClient;
  }
  
  private void requestConnection()
  {
    getLocationClient().connect();
  }
  
  private void requestDisconnection()
  {
    this.mInProgress = false;
    getLocationClient().disconnect();
    Log.debug("Geofence Plugin|Location client disconnected");
  }
  
  private void retry()
  {
    new Handler().postDelayed(new Runnable()
    {
      public void run()
      {
        if ((GeofenceRequester.this.mGeofencesToAdd != null) && (GeofenceRequester.this.mGeofencesToAdd.size() > 0)) {
          GeofenceRequester.this.addGeofences(GeofenceRequester.this.mGeofencesToAdd);
        }
      }
    }, this.mCurrentBackoff);
    this.mCurrentBackoff *= 2;
    if (this.mCurrentBackoff > 3600000) {
      this.mCurrentBackoff = 3600000;
    }
  }
  
  public void addGeofences(List<Geofence> paramList)
  {
    this.mGeofencesToAdd = ((ArrayList)paramList);
    if (!this.mInProgress)
    {
      this.mInProgress = true;
      requestConnection();
    }
  }
  
  public boolean getInProgressFlag()
  {
    return this.mInProgress;
  }
  
  public PendingIntent getRequestPendingIntent()
  {
    return createRequestPendingIntent();
  }
  
  public void onConnected(Bundle paramBundle)
  {
    try
    {
      this.mCurrentBackoff = 3000;
      Log.debug("Geofence Plugin|Location client connected");
      continueAddGeofences();
      return;
    }
    catch (IllegalStateException paramBundle)
    {
      Log.debug("Geofence Plugin|Location client error, reconnecting", paramBundle);
      this.mLocationClient.connect();
    }
  }
  
  public void onConnectionFailed(ConnectionResult paramConnectionResult)
  {
    this.mInProgress = false;
    Log.debug("Geofence Plugin|Connection failed to Location client");
    retry();
  }
  
  public void onConnectionSuspended(int paramInt)
  {
    this.mInProgress = false;
    Log.debug("Geofence Plugin|Location client connection lost");
    this.mLocationClient = null;
  }
  
  public void onResult(Status paramStatus)
  {
    int j = paramStatus.getStatusCode();
    paramStatus = new String[this.mGeofencesToAdd.size()];
    int i = 0;
    if (i >= this.mGeofencesToAdd.size())
    {
      if (j != 0) {
        break label150;
      }
      if (this.mListener != null) {
        this.mListener.onGeofencesAdded(paramStatus);
      }
      i = 0;
      if (i < paramStatus.length) {
        break label89;
      }
    }
    for (;;)
    {
      requestDisconnection();
      return;
      paramStatus[i] = ((Geofence)this.mGeofencesToAdd.get(i)).getRequestId();
      i += 1;
      break;
      label89:
      j = 0;
      for (;;)
      {
        if (j >= this.mGeofencesToAdd.size())
        {
          i += 1;
          break;
        }
        if (((Geofence)this.mGeofencesToAdd.get(j)).getRequestId().equals(paramStatus[i])) {
          this.mGeofencesToAdd.remove(j);
        }
        j += 1;
      }
      label150:
      if (j != 1001)
      {
        retry();
      }
      else
      {
        Log.debug("Geofence Plugin|Can't add more Geofences. Limit reached? Error code : " + j);
        if (this.mListener != null) {
          this.mListener.onGeofencesAdded(paramStatus);
        }
        this.mGeofencesToAdd.clear();
      }
    }
  }
  
  public void setInProgressFlag(boolean paramBoolean)
  {
    this.mInProgress = paramBoolean;
  }
  
  public static abstract interface onGeofenceRequestListener
  {
    public abstract void onGeofencesAdded(String[] paramArrayOfString);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/plugins/GooglePlayServices/managers/GeofenceRequester.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */