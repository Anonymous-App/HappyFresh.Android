package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.server.converter.ConverterWrapper;

public class zza
  implements Parcelable.Creator<FastJsonResponse.Field>
{
  static void zza(FastJsonResponse.Field paramField, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzac(paramParcel);
    zzb.zzc(paramParcel, 1, paramField.getVersionCode());
    zzb.zzc(paramParcel, 2, paramField.zzok());
    zzb.zza(paramParcel, 3, paramField.zzoq());
    zzb.zzc(paramParcel, 4, paramField.zzol());
    zzb.zza(paramParcel, 5, paramField.zzor());
    zzb.zza(paramParcel, 6, paramField.zzos(), false);
    zzb.zzc(paramParcel, 7, paramField.zzot());
    zzb.zza(paramParcel, 8, paramField.zzov(), false);
    zzb.zza(paramParcel, 9, paramField.zzox(), paramInt, false);
    zzb.zzH(paramParcel, i);
  }
  
  public FastJsonResponse.Field zzah(Parcel paramParcel)
  {
    ConverterWrapper localConverterWrapper = null;
    int i = 0;
    int n = com.google.android.gms.common.internal.safeparcel.zza.zzab(paramParcel);
    String str1 = null;
    String str2 = null;
    boolean bool1 = false;
    int j = 0;
    boolean bool2 = false;
    int k = 0;
    int m = 0;
    while (paramParcel.dataPosition() < n)
    {
      int i1 = com.google.android.gms.common.internal.safeparcel.zza.zzaa(paramParcel);
      switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(i1))
      {
      default: 
        com.google.android.gms.common.internal.safeparcel.zza.zzb(paramParcel, i1);
        break;
      case 1: 
        m = com.google.android.gms.common.internal.safeparcel.zza.zzg(paramParcel, i1);
        break;
      case 2: 
        k = com.google.android.gms.common.internal.safeparcel.zza.zzg(paramParcel, i1);
        break;
      case 3: 
        bool2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(paramParcel, i1);
        break;
      case 4: 
        j = com.google.android.gms.common.internal.safeparcel.zza.zzg(paramParcel, i1);
        break;
      case 5: 
        bool1 = com.google.android.gms.common.internal.safeparcel.zza.zzc(paramParcel, i1);
        break;
      case 6: 
        str2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(paramParcel, i1);
        break;
      case 7: 
        i = com.google.android.gms.common.internal.safeparcel.zza.zzg(paramParcel, i1);
        break;
      case 8: 
        str1 = com.google.android.gms.common.internal.safeparcel.zza.zzo(paramParcel, i1);
        break;
      case 9: 
        localConverterWrapper = (ConverterWrapper)com.google.android.gms.common.internal.safeparcel.zza.zza(paramParcel, i1, ConverterWrapper.CREATOR);
      }
    }
    if (paramParcel.dataPosition() != n) {
      throw new zza.zza("Overread allowed size end=" + n, paramParcel);
    }
    return new FastJsonResponse.Field(m, k, bool2, j, bool1, str2, i, str1, localConverterWrapper);
  }
  
  public FastJsonResponse.Field[] zzbH(int paramInt)
  {
    return new FastJsonResponse.Field[paramInt];
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/server/response/zza.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */