package com.google.android.gms.common.server;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zza
  implements Parcelable.Creator<FavaDiagnosticsEntity>
{
  static void zza(FavaDiagnosticsEntity paramFavaDiagnosticsEntity, Parcel paramParcel, int paramInt)
  {
    paramInt = zzb.zzac(paramParcel);
    zzb.zzc(paramParcel, 1, paramFavaDiagnosticsEntity.zzCY);
    zzb.zza(paramParcel, 2, paramFavaDiagnosticsEntity.zzaby, false);
    zzb.zzc(paramParcel, 3, paramFavaDiagnosticsEntity.zzabz);
    zzb.zzH(paramParcel, paramInt);
  }
  
  public FavaDiagnosticsEntity zzad(Parcel paramParcel)
  {
    int j = 0;
    int k = com.google.android.gms.common.internal.safeparcel.zza.zzab(paramParcel);
    String str = null;
    int i = 0;
    while (paramParcel.dataPosition() < k)
    {
      int m = com.google.android.gms.common.internal.safeparcel.zza.zzaa(paramParcel);
      switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(m))
      {
      default: 
        com.google.android.gms.common.internal.safeparcel.zza.zzb(paramParcel, m);
        break;
      case 1: 
        i = com.google.android.gms.common.internal.safeparcel.zza.zzg(paramParcel, m);
        break;
      case 2: 
        str = com.google.android.gms.common.internal.safeparcel.zza.zzo(paramParcel, m);
        break;
      case 3: 
        j = com.google.android.gms.common.internal.safeparcel.zza.zzg(paramParcel, m);
      }
    }
    if (paramParcel.dataPosition() != k) {
      throw new zza.zza("Overread allowed size end=" + k, paramParcel);
    }
    return new FavaDiagnosticsEntity(i, str, j);
  }
  
  public FavaDiagnosticsEntity[] zzbD(int paramInt)
  {
    return new FavaDiagnosticsEntity[paramInt];
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/server/zza.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */