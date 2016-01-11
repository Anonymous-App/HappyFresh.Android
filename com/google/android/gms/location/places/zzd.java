package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public class zzd
  implements Parcelable.Creator<NearbyAlertFilter>
{
  static void zza(NearbyAlertFilter paramNearbyAlertFilter, Parcel paramParcel, int paramInt)
  {
    paramInt = zzb.zzac(paramParcel);
    zzb.zzb(paramParcel, 1, paramNearbyAlertFilter.zzazu, false);
    zzb.zzc(paramParcel, 1000, paramNearbyAlertFilter.zzCY);
    zzb.zza(paramParcel, 2, paramNearbyAlertFilter.zzazs, false);
    zzb.zzc(paramParcel, 3, paramNearbyAlertFilter.zzazv, false);
    zzb.zzH(paramParcel, paramInt);
  }
  
  public NearbyAlertFilter zzep(Parcel paramParcel)
  {
    ArrayList localArrayList3 = null;
    int j = zza.zzab(paramParcel);
    int i = 0;
    ArrayList localArrayList2 = null;
    ArrayList localArrayList1 = null;
    while (paramParcel.dataPosition() < j)
    {
      int k = zza.zzaa(paramParcel);
      switch (zza.zzbA(k))
      {
      default: 
        zza.zzb(paramParcel, k);
        break;
      case 1: 
        localArrayList1 = zza.zzC(paramParcel, k);
        break;
      case 1000: 
        i = zza.zzg(paramParcel, k);
        break;
      case 2: 
        localArrayList2 = zza.zzB(paramParcel, k);
        break;
      case 3: 
        localArrayList3 = zza.zzc(paramParcel, k, UserDataType.CREATOR);
      }
    }
    if (paramParcel.dataPosition() != j) {
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    }
    return new NearbyAlertFilter(i, localArrayList1, localArrayList2, localArrayList3);
  }
  
  public NearbyAlertFilter[] zzgL(int paramInt)
  {
    return new NearbyAlertFilter[paramInt];
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/zzd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */