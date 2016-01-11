package com.google.android.gms.location.places.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.ClientKey;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.PlaceDetectionApi;
import com.google.android.gms.location.places.PlaceFilter;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.PlaceReport;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.zzm;
import com.google.android.gms.location.places.zzm.zzd;
import com.google.android.gms.location.places.zzm.zzf;

public class zzi
  implements PlaceDetectionApi
{
  public PendingResult<PlaceLikelihoodBuffer> getCurrentPlace(GoogleApiClient paramGoogleApiClient, final PlaceFilter paramPlaceFilter)
  {
    paramGoogleApiClient.zza(new zzm.zzd(Places.zzazR, paramGoogleApiClient)
    {
      protected void zza(zzj paramAnonymouszzj)
        throws RemoteException
      {
        paramAnonymouszzj.zza(new zzm(this, paramAnonymouszzj.getContext()), paramPlaceFilter);
      }
    });
  }
  
  public PendingResult<Status> reportDeviceAtPlace(GoogleApiClient paramGoogleApiClient, final PlaceReport paramPlaceReport)
  {
    paramGoogleApiClient.zzb(new zzm.zzf(Places.zzazR, paramGoogleApiClient)
    {
      protected void zza(zzj paramAnonymouszzj)
        throws RemoteException
      {
        paramAnonymouszzj.zza(new zzm(this), paramPlaceReport);
      }
    });
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/internal/zzi.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */