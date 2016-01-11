package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzh
  implements Parcelable.Creator<GetServiceRequest>
{
  static void zza(GetServiceRequest paramGetServiceRequest, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzac(paramParcel);
    zzb.zzc(paramParcel, 1, paramGetServiceRequest.version);
    zzb.zzc(paramParcel, 2, paramGetServiceRequest.zzaad);
    zzb.zzc(paramParcel, 3, paramGetServiceRequest.zzaae);
    zzb.zza(paramParcel, 4, paramGetServiceRequest.zzaaf, false);
    zzb.zza(paramParcel, 5, paramGetServiceRequest.zzaag, false);
    zzb.zza(paramParcel, 6, paramGetServiceRequest.zzaah, paramInt, false);
    zzb.zza(paramParcel, 7, paramGetServiceRequest.zzaai, false);
    zzb.zza(paramParcel, 8, paramGetServiceRequest.zzaaj, paramInt, false);
    zzb.zzH(paramParcel, i);
  }
  
  public GetServiceRequest zzW(Parcel paramParcel)
  {
    int i = 0;
    Account localAccount = null;
    int m = zza.zzab(paramParcel);
    Bundle localBundle = null;
    Scope[] arrayOfScope = null;
    IBinder localIBinder = null;
    String str = null;
    int j = 0;
    int k = 0;
    while (paramParcel.dataPosition() < m)
    {
      int n = zza.zzaa(paramParcel);
      switch (zza.zzbA(n))
      {
      default: 
        zza.zzb(paramParcel, n);
        break;
      case 1: 
        k = zza.zzg(paramParcel, n);
        break;
      case 2: 
        j = zza.zzg(paramParcel, n);
        break;
      case 3: 
        i = zza.zzg(paramParcel, n);
        break;
      case 4: 
        str = zza.zzo(paramParcel, n);
        break;
      case 5: 
        localIBinder = zza.zzp(paramParcel, n);
        break;
      case 6: 
        arrayOfScope = (Scope[])zza.zzb(paramParcel, n, Scope.CREATOR);
        break;
      case 7: 
        localBundle = zza.zzq(paramParcel, n);
        break;
      case 8: 
        localAccount = (Account)zza.zza(paramParcel, n, Account.CREATOR);
      }
    }
    if (paramParcel.dataPosition() != m) {
      throw new zza.zza("Overread allowed size end=" + m, paramParcel);
    }
    return new GetServiceRequest(k, j, i, str, localIBinder, arrayOfScope, localBundle, localAccount);
  }
  
  public GetServiceRequest[] zzbr(int paramInt)
  {
    return new GetServiceRequest[paramInt];
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/internal/zzh.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */