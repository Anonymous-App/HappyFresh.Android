package com.google.android.gms.location.places.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzu
  implements Parcelable.Creator<AutocompletePredictionEntity.SubstringEntity>
{
  static void zza(AutocompletePredictionEntity.SubstringEntity paramSubstringEntity, Parcel paramParcel, int paramInt)
  {
    paramInt = zzb.zzac(paramParcel);
    zzb.zzc(paramParcel, 1, paramSubstringEntity.mOffset);
    zzb.zzc(paramParcel, 1000, paramSubstringEntity.zzCY);
    zzb.zzc(paramParcel, 2, paramSubstringEntity.mLength);
    zzb.zzH(paramParcel, paramInt);
  }
  
  public AutocompletePredictionEntity.SubstringEntity zzeC(Parcel paramParcel)
  {
    int k = 0;
    int m = zza.zzab(paramParcel);
    int j = 0;
    int i = 0;
    while (paramParcel.dataPosition() < m)
    {
      int n = zza.zzaa(paramParcel);
      switch (zza.zzbA(n))
      {
      default: 
        zza.zzb(paramParcel, n);
        break;
      case 1: 
        j = zza.zzg(paramParcel, n);
        break;
      case 1000: 
        i = zza.zzg(paramParcel, n);
        break;
      case 2: 
        k = zza.zzg(paramParcel, n);
      }
    }
    if (paramParcel.dataPosition() != m) {
      throw new zza.zza("Overread allowed size end=" + m, paramParcel);
    }
    return new AutocompletePredictionEntity.SubstringEntity(i, j, k);
  }
  
  public AutocompletePredictionEntity.SubstringEntity[] zzhc(int paramInt)
  {
    return new AutocompletePredictionEntity.SubstringEntity[paramInt];
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/internal/zzu.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */