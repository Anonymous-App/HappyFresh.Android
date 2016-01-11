package com.happyfresh.activities;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.happyfresh.listeners.LocationRetrievedListener;
import com.happyfresh.utils.DialogUtils;

public abstract class LocationActivity
  extends BaseActivity
  implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener
{
  protected static final String TAG = LocationActivity.class.getSimpleName();
  protected static final long TIMEOUT = 5000L;
  protected GoogleApiClient mGoogleApiClient;
  private Handler mHandler = new Handler(Looper.getMainLooper());
  private LocationRequest mLocationRequest = new LocationRequest();
  private LocationRetrievedListener mLocationRetrievedListener;
  private Runnable mRunnable;
  
  private boolean servicesConnected()
  {
    int i = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
    if (i == 0)
    {
      Log.d(TAG, "Google Play services is available.");
      return true;
    }
    Dialog localDialog = GooglePlayServicesUtil.getErrorDialog(i, this, 9000);
    if (localDialog != null)
    {
      ErrorDialogFragment localErrorDialogFragment = new ErrorDialogFragment();
      localErrorDialogFragment.setDialog(localDialog);
      localErrorDialogFragment.show(getFragmentManager(), "Location Updates");
    }
    return false;
  }
  
  public boolean checkLocationService()
  {
    LocationManager localLocationManager = (LocationManager)getSystemService("location");
    if ((!localLocationManager.isProviderEnabled("gps")) && (!localLocationManager.isProviderEnabled("network")))
    {
      DialogUtils.showDialog(this, getString(2131165503), getString(2131165310), getString(2131165478), null, null, null);
      return false;
    }
    return true;
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    switch (paramInt1)
    {
    }
  }
  
  public void onConnected(Bundle paramBundle)
  {
    Log.d(TAG, "Location service connected");
    this.mLocationRetrievedListener = null;
    LocationServices.FusedLocationApi.requestLocationUpdates(this.mGoogleApiClient, this.mLocationRequest, this);
    this.mHandler.postDelayed(new Runnable()
    {
      public void run()
      {
        if (LocationActivity.this.mGoogleApiClient.isConnected()) {
          LocationActivity.this.stopLocationUpdates();
        }
      }
    }, 5000L);
  }
  
  public void onConnectionFailed(ConnectionResult paramConnectionResult)
  {
    Log.d(TAG, "Location service connection failed");
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    servicesConnected();
    this.mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).build();
    this.mGoogleApiClient.connect();
    this.mLocationRequest.setPriority(100);
    this.mLocationRequest.setInterval(500L);
    this.mLocationRequest.setFastestInterval(500L);
  }
  
  public void onLocationChanged(Location paramLocation)
  {
    this.mHandler.removeCallbacks(this.mRunnable);
    if (this.mLocationRetrievedListener != null) {
      this.mLocationRetrievedListener.onLocationRetrieved(paramLocation);
    }
    if ((paramLocation.hasAccuracy()) && (paramLocation.getAccuracy() < 7000.0F) && (this.mGoogleApiClient.isConnected())) {
      stopLocationUpdates();
    }
  }
  
  public void requestLocation(LocationRetrievedListener paramLocationRetrievedListener)
  {
    this.mLocationRetrievedListener = paramLocationRetrievedListener;
    if (!isLocationPermissionGranted()) {}
    do
    {
      return;
      this.mRunnable = new Runnable()
      {
        public void run()
        {
          if (LocationActivity.this.mLocationRetrievedListener != null)
          {
            LocationActivity.this.mLocationRetrievedListener.onLocationNotRetrieved();
            if (LocationActivity.this.mGoogleApiClient.isConnected()) {
              LocationActivity.this.stopLocationUpdates();
            }
          }
        }
      };
      this.mHandler.postDelayed(this.mRunnable, 5000L);
      if (!this.mGoogleApiClient.isConnected()) {
        this.mGoogleApiClient.connect();
      }
    } while (!this.mGoogleApiClient.isConnected());
    startLocationUpdates();
  }
  
  protected void startLocationUpdates()
  {
    LocationServices.FusedLocationApi.requestLocationUpdates(this.mGoogleApiClient, this.mLocationRequest, this);
  }
  
  protected void stopLocationUpdates()
  {
    LocationServices.FusedLocationApi.removeLocationUpdates(this.mGoogleApiClient, this);
  }
  
  public static class ErrorDialogFragment
    extends DialogFragment
  {
    private Dialog mDialog = null;
    
    public Dialog onCreateDialog(Bundle paramBundle)
    {
      return this.mDialog;
    }
    
    public void setDialog(Dialog paramDialog)
    {
      this.mDialog = paramDialog;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/activities/LocationActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */