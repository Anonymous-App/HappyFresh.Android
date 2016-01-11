package com.google.android.gms.location.places.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public class zza
  implements Parcelable.Creator<AutocompletePredictionEntity>
{
  static void zza(AutocompletePredictionEntity paramAutocompletePredictionEntity, Parcel paramParcel, int paramInt)
  {
    paramInt = zzb.zzac(paramParcel);
    zzb.zza(paramParcel, 1, paramAutocompletePredictionEntity.zzakM, false);
    zzb.zzc(paramParcel, 1000, paramAutocompletePredictionEntity.zzCY);
    zzb.zza(paramParcel, 2, paramAutocompletePredictionEntity.zzazK, false);
    zzb.zza(paramParcel, 3, paramAutocompletePredictionEntity.zzazo, false);
    zzb.zzc(paramParcel, 4, paramAutocompletePredictionEntity.zzaAe, false);
    zzb.zzc(paramParcel, 5, paramAutocompletePredictionEntity.zzaAf);
    zzb.zzH(paramParcel, paramInt);
  }
  
  public AutocompletePredictionEntity zzex(Parcel paramParcel)
  {
    int i = 0;
    ArrayList localArrayList1 = null;
    int k = com.google.android.gms.common.internal.safeparcel.zza.zzab(paramParcel);
    ArrayList localArrayList2 = null;
    String str1 = null;
    String str2 = null;
    int j = 0;
    while (paramParcel.dataPosition() < k)
    {
      int m = com.google.android.gms.common.internal.safeparcel.zza.zzaa(paramParcel);
      switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(m))
      {
      default: 
        com.google.android.gms.common.internal.safeparcel.zza.zzb(paramParcel, m);
        break;
      case 1: 
        str2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(paramParcel, m);
        break;
      case 1000: 
        j = com.google.android.gms.common.internal.safeparcel.zza.zzg(paramParcel, m);
        break;
      case 2: 
        str1 = com.google.android.gms.common.internal.safeparcel.zza.zzo(paramParcel, m);
        break;
      case 3: 
        localArrayList2 = com.google.android.gms.common.internal.safeparcel.zza.zzB(paramParcel, m);
        break;
      case 4: 
        localArrayList1 = com.google.android.gms.common.internal.safeparcel.zza.zzc(paramParcel, m, AutocompletePredictionEntity.SubstringEntity.CREATOR);
        break;
      case 5: 
        i = com.google.android.gms.common.internal.safeparcel.zza.zzg(paramParcel, m);
      }
    }
    if (paramParcel.dataPosition() != k) {
      throw new zza.zza("Overread allowed size end=" + k, paramParcel);
    }
    return new AutocompletePredictionEntity(j, str2, str1, localArrayList2, localArrayList1, i);
  }
  
  public AutocompletePredictionEntity[] zzgW(int paramInt)
  {
    return new AutocompletePredictionEntity[paramInt];
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/internal/zza.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */