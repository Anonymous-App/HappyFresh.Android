package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public class zzc
  implements Parcelable.Creator<AutocompleteFilter>
{
  static void zza(AutocompleteFilter paramAutocompleteFilter, Parcel paramParcel, int paramInt)
  {
    paramInt = zzb.zzac(paramParcel);
    zzb.zza(paramParcel, 1, paramAutocompleteFilter.zzuE());
    zzb.zzc(paramParcel, 1000, paramAutocompleteFilter.zzCY);
    zzb.zza(paramParcel, 2, paramAutocompleteFilter.zzazs, false);
    zzb.zzH(paramParcel, paramInt);
  }
  
  public AutocompleteFilter zzeo(Parcel paramParcel)
  {
    boolean bool = false;
    int j = zza.zzab(paramParcel);
    ArrayList localArrayList = null;
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
        bool = zza.zzc(paramParcel, k);
        break;
      case 1000: 
        i = zza.zzg(paramParcel, k);
        break;
      case 2: 
        localArrayList = zza.zzB(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != j) {
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    }
    return new AutocompleteFilter(i, bool, localArrayList);
  }
  
  public AutocompleteFilter[] zzgK(int paramInt)
  {
    return new AutocompleteFilter[paramInt];
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/zzc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */