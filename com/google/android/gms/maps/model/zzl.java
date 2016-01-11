package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzl
  implements Parcelable.Creator<StreetViewPanoramaOrientation>
{
  static void zza(StreetViewPanoramaOrientation paramStreetViewPanoramaOrientation, Parcel paramParcel, int paramInt)
  {
    paramInt = zzb.zzac(paramParcel);
    zzb.zzc(paramParcel, 1, paramStreetViewPanoramaOrientation.getVersionCode());
    zzb.zza(paramParcel, 2, paramStreetViewPanoramaOrientation.tilt);
    zzb.zza(paramParcel, 3, paramStreetViewPanoramaOrientation.bearing);
    zzb.zzH(paramParcel, paramInt);
  }
  
  public StreetViewPanoramaOrientation zzeW(Parcel paramParcel)
  {
    float f2 = 0.0F;
    int j = zza.zzab(paramParcel);
    int i = 0;
    float f1 = 0.0F;
    while (paramParcel.dataPosition() < j)
    {
      int k = zza.zzaa(paramParcel);
      switch (zza.zzbA(k))
      {
      default: 
        zza.zzb(paramParcel, k);
        break;
      case 1: 
        i = zza.zzg(paramParcel, k);
        break;
      case 2: 
        f1 = zza.zzl(paramParcel, k);
        break;
      case 3: 
        f2 = zza.zzl(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != j) {
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    }
    return new StreetViewPanoramaOrientation(i, f1, f2);
  }
  
  public StreetViewPanoramaOrientation[] zzhw(int paramInt)
  {
    return new StreetViewPanoramaOrientation[paramInt];
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/maps/model/zzl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */