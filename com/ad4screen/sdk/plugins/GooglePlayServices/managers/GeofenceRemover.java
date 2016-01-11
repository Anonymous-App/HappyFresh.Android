package com.ad4screen.sdk.plugins.GooglePlayServices.managers;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import com.ad4screen.sdk.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.GeofencingApi;
import com.google.android.gms.location.LocationServices;
import java.util.List;

public class GeofenceRemover
  implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<Status>
{
  private static final int DEFAULT_BACKOFF_MS = 3000;
  private static final int MAX_BACKOFF_MS = 3600000;
  private Context mContext;
  private int mCurrentBackoff;
  private List<String> mGeofencesToRemove;
  private boolean mInProgress;
  private OnGeofencesRemoveListener mListener;
  private GoogleApiClient mLocationClient;
  
  public GeofenceRemover(Context paramContext, OnGeofencesRemoveListener paramOnGeofencesRemoveListener)
  {
    this.mContext = paramContext;
    this.mGeofencesToRemove = null;
    this.mLocationClient = null;
    this.mInProgress = false;
    this.mListener = paramOnGeofencesRemoveListener;
  }
  
  private void continueRemoveGeofences()
  {
    LocationServices.GeofencingApi.removeGeofences(this.mLocationClient, this.mGeofencesToRemove).setResultCallback(this);
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
        if ((GeofenceRemover.this.mGeofencesToRemove != null) && (GeofenceRemover.this.mGeofencesToRemove.size() > 0)) {
          GeofenceRemover.this.removeGeofencesById(GeofenceRemover.this.mGeofencesToRemove);
        }
      }
    }, this.mCurrentBackoff);
    this.mCurrentBackoff *= 2;
    if (this.mCurrentBackoff > 3600000) {
      this.mCurrentBackoff = 3600000;
    }
  }
  
  public boolean getInProgressFlag()
  {
    return this.mInProgress;
  }
  
  public void onConnected(Bundle paramBundle)
  {
    try
    {
      this.mCurrentBackoff = 3000;
      Log.debug("Geofence Plugin|Location client connected");
      continueRemoveGeofences();
      return;
    }
    catch (IllegalStateException paramBundle)
    {
      Log.debug("Geofence Plugin|Location client error, retrying", paramBundle);
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
    int i = paramStatus.getStatusCode();
    paramStatus = new String[this.mGeofencesToRemove.size()];
    this.mGeofencesToRemove.toArray(paramStatus);
    if (i == 0)
    {
      if (this.mListener != null) {
        this.mListener.onGeofencesRemoved(paramStatus);
      }
      i = 0;
    }
    for (;;)
    {
      if (i >= paramStatus.length)
      {
        requestDisconnection();
        return;
      }
      this.mGeofencesToRemove.remove(paramStatus[i]);
      i += 1;
    }
  }
  
  public void removeGeofencesById(List<String> paramList)
  {
    if ((paramList == null) || (paramList.size() == 0)) {
      return;
    }
    if (!this.mInProgress)
    {
      this.mGeofencesToRemove = paramList;
      requestConnection();
      return;
    }
    retry();
  }
  
  public void setInProgressFlag(boolean paramBoolean)
  {
    this.mInProgress = paramBoolean;
  }
  
  public static abstract interface OnGeofencesRemoveListener
  {
    public abstract void onGeofencesRemoved(String[] paramArrayOfString);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/plugins/GooglePlayServices/managers/GeofenceRemover.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */