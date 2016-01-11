package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;

public class zzb
  implements Parcelable.Creator<CircleOptions>
{
  static void zza(CircleOptions paramCircleOptions, Parcel paramParcel, int paramInt)
  {
    int i = com.google.android.gms.common.internal.safeparcel.zzb.zzac(paramParcel);
    com.google.android.gms.common.internal.safeparcel.zzb.zzc(paramParcel, 1, paramCircleOptions.getVersionCode());
    com.google.android.gms.common.internal.safeparcel.zzb.zza(paramParcel, 2, paramCircleOptions.getCenter(), paramInt, false);
    com.google.android.gms.common.internal.safeparcel.zzb.zza(paramParcel, 3, paramCircleOptions.getRadius());
    com.google.android.gms.common.internal.safeparcel.zzb.zza(paramParcel, 4, paramCircleOptions.getStrokeWidth());
    com.google.android.gms.common.internal.safeparcel.zzb.zzc(paramParcel, 5, paramCircleOptions.getStrokeColor());
    com.google.android.gms.common.internal.safeparcel.zzb.zzc(paramParcel, 6, paramCircleOptions.getFillColor());
    com.google.android.gms.common.internal.safeparcel.zzb.zza(paramParcel, 7, paramCircleOptions.getZIndex());
    com.google.android.gms.common.internal.safeparcel.zzb.zza(paramParcel, 8, paramCircleOptions.isVisible());
    com.google.android.gms.common.internal.safeparcel.zzb.zzH(paramParcel, i);
  }
  
  public CircleOptions zzeM(Parcel paramParcel)
  {
    float f1 = 0.0F;
    boolean bool = false;
    int m = zza.zzab(paramParcel);
    LatLng localLatLng = null;
    double d = 0.0D;
    int i = 0;
    int j = 0;
    float f2 = 0.0F;
    int k = 0;
    while (paramParcel.dataPosition() < m)
    {
      int n = zza.zzaa(paramParcel);
      switch (zza.zzbA(n))
      {
      default: 
        zza.zzb(paramParcel, n);
        break;
      case 1: 
        k = zza.zzg(paramParcel, n);
        break;
      case 2: 
        localLatLng = (LatLng)zza.zza(paramParcel, n, LatLng.CREATOR);
        break;
      case 3: 
        d = zza.zzm(paramParcel, n);
        break;
      case 4: 
        f2 = zza.zzl(paramParcel, n);
        break;
      case 5: 
        j = zza.zzg(paramParcel, n);
        break;
      case 6: 
        i = zza.zzg(paramParcel, n);
        break;
      case 7: 
        f1 = zza.zzl(paramParcel, n);
        break;
      case 8: 
        bool = zza.zzc(paramParcel, n);
      }
    }
    if (paramParcel.dataPosition() != m) {
      throw new zza.zza("Overread allowed size end=" + m, paramParcel);
    }
    return new CircleOptions(k, localLatLng, d, f2, j, i, f1, bool);
  }
  
  public CircleOptions[] zzhm(int paramInt)
  {
    return new CircleOptions[paramInt];
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/maps/model/zzb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */