package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzh
  implements Parcelable.Creator<LocationSettingsResult>
{
  static void zza(LocationSettingsResult paramLocationSettingsResult, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzac(paramParcel);
    zzb.zza(paramParcel, 1, paramLocationSettingsResult.getStatus(), paramInt, false);
    zzb.zzc(paramParcel, 1000, paramLocationSettingsResult.getVersionCode());
    zzb.zza(paramParcel, 2, paramLocationSettingsResult.getLocationSettingsStates(), paramInt, false);
    zzb.zzH(paramParcel, i);
  }
  
  public LocationSettingsResult zzeg(Parcel paramParcel)
  {
    LocationSettingsStates localLocationSettingsStates = null;
    int j = zza.zzab(paramParcel);
    int i = 0;
    Status localStatus = null;
    if (paramParcel.dataPosition() < j)
    {
      int k = zza.zzaa(paramParcel);
      switch (zza.zzbA(k))
      {
      default: 
        zza.zzb(paramParcel, k);
      }
      for (;;)
      {
        break;
        localStatus = (Status)zza.zza(paramParcel, k, Status.CREATOR);
        continue;
        i = zza.zzg(paramParcel, k);
        continue;
        localLocationSettingsStates = (LocationSettingsStates)zza.zza(paramParcel, k, LocationSettingsStates.CREATOR);
      }
    }
    if (paramParcel.dataPosition() != j) {
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    }
    return new LocationSettingsResult(i, localStatus, localLocationSettingsStates);
  }
  
  public LocationSettingsResult[] zzgy(int paramInt)
  {
    return new LocationSettingsResult[paramInt];
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/zzh.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */