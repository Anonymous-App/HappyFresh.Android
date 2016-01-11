package com.google.android.gms.location.places.personalized;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzc
  implements Parcelable.Creator<PlaceAlias>
{
  static void zza(PlaceAlias paramPlaceAlias, Parcel paramParcel, int paramInt)
  {
    paramInt = zzb.zzac(paramParcel);
    zzb.zza(paramParcel, 1, paramPlaceAlias.zzva(), false);
    zzb.zzc(paramParcel, 1000, paramPlaceAlias.zzCY);
    zzb.zzH(paramParcel, paramInt);
  }
  
  public PlaceAlias zzeF(Parcel paramParcel)
  {
    int j = zza.zzab(paramParcel);
    int i = 0;
    String str = null;
    while (paramParcel.dataPosition() < j)
    {
      int k = zza.zzaa(paramParcel);
      switch (zza.zzbA(k))
      {
      default: 
        zza.zzb(paramParcel, k);
        break;
      case 1: 
        str = zza.zzo(paramParcel, k);
        break;
      case 1000: 
        i = zza.zzg(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != j) {
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    }
    return new PlaceAlias(i, str);
  }
  
  public PlaceAlias[] zzhf(int paramInt)
  {
    return new PlaceAlias[paramInt];
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/personalized/zzc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */