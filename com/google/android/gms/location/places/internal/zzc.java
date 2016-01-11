package com.google.android.gms.location.places.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.ClientKey;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.location.places.AddPlaceRequest;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.GeoDataApi;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.zzm;
import com.google.android.gms.location.places.zzm.zza;
import com.google.android.gms.location.places.zzm.zzc;
import com.google.android.gms.maps.model.LatLngBounds;
import java.util.Arrays;
import java.util.List;

public class zzc
  implements GeoDataApi
{
  public PendingResult<PlaceBuffer> addPlace(GoogleApiClient paramGoogleApiClient, final AddPlaceRequest paramAddPlaceRequest)
  {
    paramGoogleApiClient.zzb(new zzm.zzc(Places.zzazQ, paramGoogleApiClient)
    {
      protected void zza(zzd paramAnonymouszzd)
        throws RemoteException
      {
        paramAnonymouszzd.zza(new zzm(this, paramAnonymouszzd.getContext()), paramAddPlaceRequest);
      }
    });
  }
  
  public PendingResult<AutocompletePredictionBuffer> getAutocompletePredictions(GoogleApiClient paramGoogleApiClient, final String paramString, final LatLngBounds paramLatLngBounds, final AutocompleteFilter paramAutocompleteFilter)
  {
    paramGoogleApiClient.zza(new zzm.zza(Places.zzazQ, paramGoogleApiClient)
    {
      protected void zza(zzd paramAnonymouszzd)
        throws RemoteException
      {
        paramAnonymouszzd.zza(new zzm(this), paramString, paramLatLngBounds, paramAutocompleteFilter);
      }
    });
  }
  
  public PendingResult<PlaceBuffer> getPlaceById(GoogleApiClient paramGoogleApiClient, final String... paramVarArgs)
  {
    boolean bool = true;
    if ((paramVarArgs != null) && (paramVarArgs.length >= 1)) {}
    for (;;)
    {
      zzu.zzV(bool);
      paramGoogleApiClient.zza(new zzm.zzc(Places.zzazQ, paramGoogleApiClient)
      {
        protected void zza(zzd paramAnonymouszzd)
          throws RemoteException
        {
          List localList = Arrays.asList(paramVarArgs);
          paramAnonymouszzd.zza(new zzm(this, paramAnonymouszzd.getContext()), localList);
        }
      });
      bool = false;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/internal/zzc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */