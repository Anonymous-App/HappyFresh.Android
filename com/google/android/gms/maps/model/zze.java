package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zze
  implements Parcelable.Creator<LatLng>
{
  static void zza(LatLng paramLatLng, Parcel paramParcel, int paramInt)
  {
    paramInt = zzb.zzac(paramParcel);
    zzb.zzc(paramParcel, 1, paramLatLng.getVersionCode());
    zzb.zza(paramParcel, 2, paramLatLng.latitude);
    zzb.zza(paramParcel, 3, paramLatLng.longitude);
    zzb.zzH(paramParcel, paramInt);
  }
  
  public LatLng zzeP(Parcel paramParcel)
  {
    double d1 = 0.0D;
    int j = zza.zzab(paramParcel);
    int i = 0;
    double d2 = 0.0D;
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
        d2 = zza.zzm(paramParcel, k);
        break;
      case 3: 
        d1 = zza.zzm(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != j) {
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    }
    return new LatLng(i, d2, d1);
  }
  
  public LatLng[] zzhp(int paramInt)
  {
    return new LatLng[paramInt];
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/maps/model/zze.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */