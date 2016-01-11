package com.google.android.gms.location;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.List;

public class zzf
  implements Parcelable.Creator<LocationResult>
{
  static void zza(LocationResult paramLocationResult, Parcel paramParcel, int paramInt)
  {
    paramInt = zzb.zzac(paramParcel);
    zzb.zzc(paramParcel, 1, paramLocationResult.getLocations(), false);
    zzb.zzc(paramParcel, 1000, paramLocationResult.getVersionCode());
    zzb.zzH(paramParcel, paramInt);
  }
  
  public LocationResult zzee(Parcel paramParcel)
  {
    int j = zza.zzab(paramParcel);
    int i = 0;
    Object localObject = LocationResult.zzaxZ;
    while (paramParcel.dataPosition() < j)
    {
      int k = zza.zzaa(paramParcel);
      switch (zza.zzbA(k))
      {
      default: 
        zza.zzb(paramParcel, k);
        break;
      case 1: 
        localObject = zza.zzc(paramParcel, k, Location.CREATOR);
        break;
      case 1000: 
        i = zza.zzg(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != j) {
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    }
    return new LocationResult(i, (List)localObject);
  }
  
  public LocationResult[] zzgw(int paramInt)
  {
    return new LocationResult[paramInt];
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/zzf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */