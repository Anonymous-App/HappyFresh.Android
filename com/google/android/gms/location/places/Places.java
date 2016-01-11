package com.google.android.gms.location.places;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ClientKey;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.location.places.internal.zzc;
import com.google.android.gms.location.places.internal.zzd;
import com.google.android.gms.location.places.internal.zzd.zza;
import com.google.android.gms.location.places.internal.zzi;
import com.google.android.gms.location.places.internal.zzj;
import com.google.android.gms.location.places.internal.zzj.zza;

public class Places
{
  public static final Api<PlacesOptions> GEO_DATA_API;
  public static final GeoDataApi GeoDataApi = new zzc();
  public static final Api<PlacesOptions> PLACE_DETECTION_API;
  public static final PlaceDetectionApi PlaceDetectionApi = new zzi();
  public static final Api.ClientKey<zzd> zzazQ = new Api.ClientKey();
  public static final Api.ClientKey<zzj> zzazR = new Api.ClientKey();
  
  static
  {
    GEO_DATA_API = new Api("Places.GEO_DATA_API", new zzd.zza(null, null), zzazQ, new Scope[0]);
    PLACE_DETECTION_API = new Api("Places.PLACE_DETECTION_API", new zzj.zza(null, null), zzazR, new Scope[0]);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/Places.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */