package com.google.android.gms.auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zza
  implements Parcelable.Creator<AccountChangeEvent>
{
  static void zza(AccountChangeEvent paramAccountChangeEvent, Parcel paramParcel, int paramInt)
  {
    paramInt = zzb.zzac(paramParcel);
    zzb.zzc(paramParcel, 1, paramAccountChangeEvent.mVersion);
    zzb.zza(paramParcel, 2, paramAccountChangeEvent.zzOw);
    zzb.zza(paramParcel, 3, paramAccountChangeEvent.zzOx, false);
    zzb.zzc(paramParcel, 4, paramAccountChangeEvent.zzOy);
    zzb.zzc(paramParcel, 5, paramAccountChangeEvent.zzOz);
    zzb.zza(paramParcel, 6, paramAccountChangeEvent.zzOA, false);
    zzb.zzH(paramParcel, paramInt);
  }
  
  public AccountChangeEvent[] zzap(int paramInt)
  {
    return new AccountChangeEvent[paramInt];
  }
  
  public AccountChangeEvent zzz(Parcel paramParcel)
  {
    String str1 = null;
    int i = 0;
    int m = com.google.android.gms.common.internal.safeparcel.zza.zzab(paramParcel);
    long l = 0L;
    int j = 0;
    String str2 = null;
    int k = 0;
    while (paramParcel.dataPosition() < m)
    {
      int n = com.google.android.gms.common.internal.safeparcel.zza.zzaa(paramParcel);
      switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(n))
      {
      default: 
        com.google.android.gms.common.internal.safeparcel.zza.zzb(paramParcel, n);
        break;
      case 1: 
        k = com.google.android.gms.common.internal.safeparcel.zza.zzg(paramParcel, n);
        break;
      case 2: 
        l = com.google.android.gms.common.internal.safeparcel.zza.zzi(paramParcel, n);
        break;
      case 3: 
        str2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(paramParcel, n);
        break;
      case 4: 
        j = com.google.android.gms.common.internal.safeparcel.zza.zzg(paramParcel, n);
        break;
      case 5: 
        i = com.google.android.gms.common.internal.safeparcel.zza.zzg(paramParcel, n);
        break;
      case 6: 
        str1 = com.google.android.gms.common.internal.safeparcel.zza.zzo(paramParcel, n);
      }
    }
    if (paramParcel.dataPosition() != m) {
      throw new zza.zza("Overread allowed size end=" + m, paramParcel);
    }
    return new AccountChangeEvent(k, l, str2, j, i, str1);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/auth/zza.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */