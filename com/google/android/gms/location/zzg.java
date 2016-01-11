package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public class zzg
  implements Parcelable.Creator<LocationSettingsRequest>
{
  static void zza(LocationSettingsRequest paramLocationSettingsRequest, Parcel paramParcel, int paramInt)
  {
    paramInt = zzb.zzac(paramParcel);
    zzb.zzc(paramParcel, 1, paramLocationSettingsRequest.zzrj(), false);
    zzb.zzc(paramParcel, 1000, paramLocationSettingsRequest.getVersionCode());
    zzb.zza(paramParcel, 2, paramLocationSettingsRequest.zzup());
    zzb.zza(paramParcel, 3, paramLocationSettingsRequest.zzuq());
    zzb.zza(paramParcel, 4, paramLocationSettingsRequest.zzur());
    zzb.zzH(paramParcel, paramInt);
  }
  
  public LocationSettingsRequest zzef(Parcel paramParcel)
  {
    boolean bool1 = false;
    int j = zza.zzab(paramParcel);
    ArrayList localArrayList = null;
    boolean bool2 = false;
    boolean bool3 = false;
    int i = 0;
    while (paramParcel.dataPosition() < j)
    {
      int k = zza.zzaa(paramParcel);
      switch (zza.zzbA(k))
      {
      default: 
        zza.zzb(paramParcel, k);
        break;
      case 1: 
        localArrayList = zza.zzc(paramParcel, k, LocationRequest.CREATOR);
        break;
      case 1000: 
        i = zza.zzg(paramParcel, k);
        break;
      case 2: 
        bool3 = zza.zzc(paramParcel, k);
        break;
      case 3: 
        bool2 = zza.zzc(paramParcel, k);
        break;
      case 4: 
        bool1 = zza.zzc(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != j) {
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    }
    return new LocationSettingsRequest(i, localArrayList, bool3, bool2, bool1);
  }
  
  public LocationSettingsRequest[] zzgx(int paramInt)
  {
    return new LocationSettingsRequest[paramInt];
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/zzg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */