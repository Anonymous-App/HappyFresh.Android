package com.ad4screen.sdk.plugins.GooglePlayServices;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import com.ad4screen.sdk.A4S.Callback;
import com.ad4screen.sdk.A4SService;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.plugins.GooglePlayServices.common.Utils;
import com.ad4screen.sdk.plugins.LocationPlugin;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class Location
  implements LocationPlugin, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener
{
  private static final int DEFAULT_BACKOFF_MS = 3000;
  private static final int MAX_BACKOFF_MS = 3600000;
  private static final String METADATA_LOCATION_PRIORITY = "com.ad4screen.location.priority";
  private static final int PLUGIN_VERSION = 1;
  private A4S.Callback<android.location.Location> mCallback;
  private Context mContext;
  private int mCurrentBackoff;
  private android.location.Location mCurrentLocation;
  private long mFastestInterval;
  private GoogleApiClient mLocationClient;
  LocationRequest mLocationRequest;
  private long mUpdateInterval;
  private boolean started = false;
  
  public boolean connect(Context paramContext, long paramLong1, long paramLong2, A4S.Callback<android.location.Location> paramCallback)
  {
    this.mContext = paramContext;
    this.mUpdateInterval = paramLong1;
    this.mFastestInterval = paramLong2;
    this.mCallback = paramCallback;
    if ((this.mLocationClient != null) && (this.started)) {
      return true;
    }
    try
    {
      if (!Utils.checkPlayServices(this.mContext)) {
        break label223;
      }
      this.mLocationRequest = LocationRequest.create();
      paramContext = Utils.loadServiceMetadata(paramContext, "com.ad4screen.location.priority", A4SService.class);
      if (paramContext == null) {
        break label225;
      }
      if (paramContext.equals("normal")) {
        this.mLocationRequest.setPriority(102);
      }
      if (paramContext.equals("high")) {
        this.mLocationRequest.setPriority(100);
      }
      if (paramContext.equals("low")) {
        this.mLocationRequest.setPriority(104);
      }
      if (paramContext.equals("none")) {
        this.mLocationRequest.setPriority(105);
      }
    }
    catch (Exception paramContext)
    {
      for (;;)
      {
        Log.error("Location Plugin|Error occured when connecting to Google Play Services Location Client", paramContext);
        return false;
        this.mLocationRequest.setPriority(102);
      }
    }
    catch (Error paramContext)
    {
      for (;;)
      {
        Log.error("Location Plugin|Error occured when connecting to Google Play Services Location Client", paramContext);
      }
    }
    this.mLocationRequest.setInterval(this.mUpdateInterval);
    this.mLocationRequest.setFastestInterval(this.mFastestInterval);
    this.mLocationClient = new GoogleApiClient.Builder(this.mContext).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
    this.mLocationClient.connect();
    this.started = true;
    return true;
  }
  
  public void disconnect()
  {
    if ((this.mLocationClient != null) && (this.mLocationClient.isConnected()))
    {
      this.started = false;
      LocationServices.FusedLocationApi.removeLocationUpdates(this.mLocationClient, this);
      this.mLocationClient.disconnect();
      Log.debug("Location Plugin|Location client disconnected");
    }
  }
  
  public int getPluginVersion()
  {
    return 1;
  }
  
  public void onConnected(Bundle paramBundle)
  {
    try
    {
      Log.debug("Location Plugin|Location client connected");
      this.mCurrentBackoff = 3000;
      this.mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(this.mLocationClient);
      if (this.mCallback != null) {
        this.mCallback.onResult(this.mCurrentLocation);
      }
      LocationServices.FusedLocationApi.requestLocationUpdates(this.mLocationClient, this.mLocationRequest, this);
      return;
    }
    catch (IllegalStateException paramBundle)
    {
      Log.debug("Location Plugin|Location client error, reconnecting", paramBundle);
      this.mLocationClient.connect();
    }
  }
  
  public void onConnectionFailed(ConnectionResult paramConnectionResult)
  {
    Log.error("Location Plugin|Can't connect to Google Play Services Location Service. Connection Result : " + paramConnectionResult.toString());
  }
  
  public void onConnectionSuspended(int paramInt)
  {
    if (!this.started) {}
    do
    {
      return;
      new Handler().postDelayed(new Runnable()
      {
        public void run()
        {
          if (!Location.this.started) {
            return;
          }
          Location.this.connect(Location.this.mContext, Location.this.mUpdateInterval, Location.this.mFastestInterval, Location.this.mCallback);
        }
      }, this.mCurrentBackoff);
      this.mCurrentBackoff *= 2;
    } while (this.mCurrentBackoff <= 3600000);
    this.mCurrentBackoff = 3600000;
  }
  
  public void onLocationChanged(android.location.Location paramLocation)
  {
    this.mCurrentLocation = paramLocation;
    if (this.mCallback != null) {
      this.mCallback.onResult(this.mCurrentLocation);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/plugins/GooglePlayServices/Location.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */