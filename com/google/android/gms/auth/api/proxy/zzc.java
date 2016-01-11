package com.google.android.gms.auth.api.proxy;

import android.app.PendingIntent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzc
  implements Parcelable.Creator<ProxyResponse>
{
  static void zza(ProxyResponse paramProxyResponse, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzac(paramParcel);
    zzb.zzc(paramParcel, 1, paramProxyResponse.zzPu);
    zzb.zzc(paramParcel, 1000, paramProxyResponse.versionCode);
    zzb.zza(paramParcel, 2, paramProxyResponse.zzPv, paramInt, false);
    zzb.zzc(paramParcel, 3, paramProxyResponse.zzPw);
    zzb.zza(paramParcel, 4, paramProxyResponse.zzPt, false);
    zzb.zza(paramParcel, 5, paramProxyResponse.zzPs, false);
    zzb.zzH(paramParcel, i);
  }
  
  public ProxyResponse zzI(Parcel paramParcel)
  {
    byte[] arrayOfByte = null;
    int i = 0;
    int m = zza.zzab(paramParcel);
    Bundle localBundle = null;
    PendingIntent localPendingIntent = null;
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
        j = zza.zzg(paramParcel, n);
        break;
      case 1000: 
        k = zza.zzg(paramParcel, n);
        break;
      case 2: 
        localPendingIntent = (PendingIntent)zza.zza(paramParcel, n, PendingIntent.CREATOR);
        break;
      case 3: 
        i = zza.zzg(paramParcel, n);
        break;
      case 4: 
        localBundle = zza.zzq(paramParcel, n);
        break;
      case 5: 
        arrayOfByte = zza.zzr(paramParcel, n);
      }
    }
    if (paramParcel.dataPosition() != m) {
      throw new zza.zza("Overread allowed size end=" + m, paramParcel);
    }
    return new ProxyResponse(k, j, localPendingIntent, i, localBundle, arrayOfByte);
  }
  
  public ProxyResponse[] zzay(int paramInt)
  {
    return new ProxyResponse[paramInt];
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/auth/api/proxy/zzc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */