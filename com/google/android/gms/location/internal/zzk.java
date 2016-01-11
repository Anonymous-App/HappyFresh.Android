package com.google.android.gms.location.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.location.LocationRequest;
import java.util.List;

public class zzk
  implements Parcelable.Creator<LocationRequestInternal>
{
  static void zza(LocationRequestInternal paramLocationRequestInternal, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzac(paramParcel);
    zzb.zza(paramParcel, 1, paramLocationRequestInternal.zzamz, paramInt, false);
    zzb.zzc(paramParcel, 1000, paramLocationRequestInternal.getVersionCode());
    zzb.zza(paramParcel, 2, paramLocationRequestInternal.zzazb);
    zzb.zza(paramParcel, 3, paramLocationRequestInternal.zzazc);
    zzb.zza(paramParcel, 4, paramLocationRequestInternal.zzazd);
    zzb.zzc(paramParcel, 5, paramLocationRequestInternal.zzaze, false);
    zzb.zza(paramParcel, 6, paramLocationRequestInternal.mTag, false);
    zzb.zzH(paramParcel, i);
  }
  
  public LocationRequestInternal zzek(Parcel paramParcel)
  {
    String str = null;
    boolean bool1 = true;
    boolean bool3 = false;
    int j = zza.zzab(paramParcel);
    Object localObject = LocationRequestInternal.zzaza;
    boolean bool2 = true;
    LocationRequest localLocationRequest = null;
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
        localLocationRequest = (LocationRequest)zza.zza(paramParcel, k, LocationRequest.CREATOR);
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
        break;
      case 5: 
        localObject = zza.zzc(paramParcel, k, ClientIdentity.CREATOR);
        break;
      case 6: 
        str = zza.zzo(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != j) {
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    }
    return new LocationRequestInternal(i, localLocationRequest, bool3, bool2, bool1, (List)localObject, str);
  }
  
  public LocationRequestInternal[] zzgE(int paramInt)
  {
    return new LocationRequestInternal[paramInt];
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/internal/zzk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */