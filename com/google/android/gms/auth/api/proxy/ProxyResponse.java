package com.google.android.gms.auth.api.proxy;

import android.app.PendingIntent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class ProxyResponse
  implements SafeParcelable
{
  public static final Parcelable.Creator<ProxyResponse> CREATOR = new zzc();
  final int versionCode;
  public final byte[] zzPs;
  final Bundle zzPt;
  public final int zzPu;
  public final PendingIntent zzPv;
  public final int zzPw;
  
  ProxyResponse(int paramInt1, int paramInt2, PendingIntent paramPendingIntent, int paramInt3, Bundle paramBundle, byte[] paramArrayOfByte)
  {
    this.versionCode = paramInt1;
    this.zzPu = paramInt2;
    this.zzPw = paramInt3;
    this.zzPt = paramBundle;
    this.zzPs = paramArrayOfByte;
    this.zzPv = paramPendingIntent;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzc.zza(this, paramParcel, paramInt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/auth/api/proxy/ProxyResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */