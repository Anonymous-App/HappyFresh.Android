package com.google.android.gms.maps.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzz
  implements Parcelable.Creator<Point>
{
  static void zza(Point paramPoint, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzac(paramParcel);
    zzb.zzc(paramParcel, 1, paramPoint.getVersionCode());
    zzb.zza(paramParcel, 2, paramPoint.zzvG(), paramInt, false);
    zzb.zzH(paramParcel, i);
  }
  
  public Point zzeK(Parcel paramParcel)
  {
    int j = zza.zzab(paramParcel);
    int i = 0;
    android.graphics.Point localPoint = null;
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
        localPoint = (android.graphics.Point)zza.zza(paramParcel, k, android.graphics.Point.CREATOR);
      }
    }
    if (paramParcel.dataPosition() != j) {
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    }
    return new Point(i, localPoint);
  }
  
  public Point[] zzhk(int paramInt)
  {
    return new Point[paramInt];
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/maps/internal/zzz.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */