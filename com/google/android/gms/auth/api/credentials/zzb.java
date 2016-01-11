package com.google.android.gms.auth.api.credentials;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;

public class zzb
  implements Parcelable.Creator<CredentialRequest>
{
  static void zza(CredentialRequest paramCredentialRequest, Parcel paramParcel, int paramInt)
  {
    paramInt = com.google.android.gms.common.internal.safeparcel.zzb.zzac(paramParcel);
    com.google.android.gms.common.internal.safeparcel.zzb.zza(paramParcel, 1, paramCredentialRequest.getSupportsPasswordLogin());
    com.google.android.gms.common.internal.safeparcel.zzb.zzc(paramParcel, 1000, paramCredentialRequest.zzCY);
    com.google.android.gms.common.internal.safeparcel.zzb.zza(paramParcel, 2, paramCredentialRequest.getAccountTypes(), false);
    com.google.android.gms.common.internal.safeparcel.zzb.zzH(paramParcel, paramInt);
  }
  
  public CredentialRequest zzD(Parcel paramParcel)
  {
    boolean bool = false;
    int j = zza.zzab(paramParcel);
    String[] arrayOfString = null;
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
        arrayOfString = zza.zzA(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != j) {
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    }
    return new CredentialRequest(i, bool, arrayOfString);
  }
  
  public CredentialRequest[] zzat(int paramInt)
  {
    return new CredentialRequest[paramInt];
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/auth/api/credentials/zzb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */