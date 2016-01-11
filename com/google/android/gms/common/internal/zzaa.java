package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzaa
  implements Parcelable.Creator<ValidateAccountRequest>
{
  static void zza(ValidateAccountRequest paramValidateAccountRequest, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzac(paramParcel);
    zzb.zzc(paramParcel, 1, paramValidateAccountRequest.zzCY);
    zzb.zzc(paramParcel, 2, paramValidateAccountRequest.zzod());
    zzb.zza(paramParcel, 3, paramValidateAccountRequest.zzZO, false);
    zzb.zza(paramParcel, 4, paramValidateAccountRequest.zzoe(), paramInt, false);
    zzb.zza(paramParcel, 5, paramValidateAccountRequest.zzof(), false);
    zzb.zza(paramParcel, 6, paramValidateAccountRequest.getCallingPackage(), false);
    zzb.zzH(paramParcel, i);
  }
  
  public ValidateAccountRequest zzZ(Parcel paramParcel)
  {
    int i = 0;
    String str = null;
    int k = zza.zzab(paramParcel);
    Bundle localBundle = null;
    Scope[] arrayOfScope = null;
    IBinder localIBinder = null;
    int j = 0;
    while (paramParcel.dataPosition() < k)
    {
      int m = zza.zzaa(paramParcel);
      switch (zza.zzbA(m))
      {
      default: 
        zza.zzb(paramParcel, m);
        break;
      case 1: 
        j = zza.zzg(paramParcel, m);
        break;
      case 2: 
        i = zza.zzg(paramParcel, m);
        break;
      case 3: 
        localIBinder = zza.zzp(paramParcel, m);
        break;
      case 4: 
        arrayOfScope = (Scope[])zza.zzb(paramParcel, m, Scope.CREATOR);
        break;
      case 5: 
        localBundle = zza.zzq(paramParcel, m);
        break;
      case 6: 
        str = zza.zzo(paramParcel, m);
      }
    }
    if (paramParcel.dataPosition() != k) {
      throw new zza.zza("Overread allowed size end=" + k, paramParcel);
    }
    return new ValidateAccountRequest(j, i, localIBinder, arrayOfScope, localBundle, str);
  }
  
  public ValidateAccountRequest[] zzbz(int paramInt)
  {
    return new ValidateAccountRequest[paramInt];
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/internal/zzaa.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */