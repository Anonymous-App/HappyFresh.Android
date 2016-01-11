package com.google.android.gms.location.places.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzl
  implements Parcelable.Creator<PlaceLikelihoodEntity>
{
  static void zza(PlaceLikelihoodEntity paramPlaceLikelihoodEntity, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzac(paramParcel);
    zzb.zza(paramParcel, 1, paramPlaceLikelihoodEntity.zzaAK, paramInt, false);
    zzb.zzc(paramParcel, 1000, paramPlaceLikelihoodEntity.zzCY);
    zzb.zza(paramParcel, 2, paramPlaceLikelihoodEntity.zzaAL);
    zzb.zzH(paramParcel, i);
  }
  
  public PlaceLikelihoodEntity zzez(Parcel paramParcel)
  {
    int j = zza.zzab(paramParcel);
    int i = 0;
    PlaceImpl localPlaceImpl = null;
    float f = 0.0F;
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
        localPlaceImpl = (PlaceImpl)zza.zza(paramParcel, k, PlaceImpl.CREATOR);
        continue;
        i = zza.zzg(paramParcel, k);
        continue;
        f = zza.zzl(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != j) {
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    }
    return new PlaceLikelihoodEntity(i, localPlaceImpl, f);
  }
  
  public PlaceLikelihoodEntity[] zzgZ(int paramInt)
  {
    return new PlaceLikelihoodEntity[paramInt];
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/internal/zzl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */