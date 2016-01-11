package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class LocationAvailabilityCreator
  implements Parcelable.Creator<LocationAvailability>
{
  public static final int CONTENT_DESCRIPTION = 0;
  
  static void zza(LocationAvailability paramLocationAvailability, Parcel paramParcel, int paramInt)
  {
    paramInt = zzb.zzac(paramParcel);
    zzb.zzc(paramParcel, 1, paramLocationAvailability.zzaxQ);
    zzb.zzc(paramParcel, 1000, paramLocationAvailability.getVersionCode());
    zzb.zzc(paramParcel, 2, paramLocationAvailability.zzaxR);
    zzb.zza(paramParcel, 3, paramLocationAvailability.zzaxS);
    zzb.zzc(paramParcel, 4, paramLocationAvailability.zzaxT);
    zzb.zzH(paramParcel, paramInt);
  }
  
  public LocationAvailability createFromParcel(Parcel paramParcel)
  {
    int i = 1;
    int n = zza.zzab(paramParcel);
    int m = 0;
    int k = 1000;
    long l = 0L;
    int j = 1;
    while (paramParcel.dataPosition() < n)
    {
      int i1 = zza.zzaa(paramParcel);
      switch (zza.zzbA(i1))
      {
      default: 
        zza.zzb(paramParcel, i1);
        break;
      case 1: 
        j = zza.zzg(paramParcel, i1);
        break;
      case 1000: 
        m = zza.zzg(paramParcel, i1);
        break;
      case 2: 
        i = zza.zzg(paramParcel, i1);
        break;
      case 3: 
        l = zza.zzi(paramParcel, i1);
        break;
      case 4: 
        k = zza.zzg(paramParcel, i1);
      }
    }
    if (paramParcel.dataPosition() != n) {
      throw new zza.zza("Overread allowed size end=" + n, paramParcel);
    }
    return new LocationAvailability(m, k, j, i, l);
  }
  
  public LocationAvailability[] newArray(int paramInt)
  {
    return new LocationAvailability[paramInt];
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/LocationAvailabilityCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */