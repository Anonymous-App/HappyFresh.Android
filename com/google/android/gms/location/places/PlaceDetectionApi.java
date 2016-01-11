package com.google.android.gms.location.places;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;

public abstract interface PlaceDetectionApi
{
  public abstract PendingResult<PlaceLikelihoodBuffer> getCurrentPlace(GoogleApiClient paramGoogleApiClient, PlaceFilter paramPlaceFilter);
  
  public abstract PendingResult<Status> reportDeviceAtPlace(GoogleApiClient paramGoogleApiClient, PlaceReport paramPlaceReport);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/PlaceDetectionApi.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */