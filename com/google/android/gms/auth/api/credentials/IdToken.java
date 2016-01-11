package com.google.android.gms.auth.api.credentials;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class IdToken
  implements SafeParcelable
{
  public static final Parcelable.Creator<IdToken> CREATOR = new zzc();
  final int zzCY;
  private final String zzOX;
  private final String zzPa;
  
  IdToken(int paramInt, String paramString1, String paramString2)
  {
    this.zzCY = paramInt;
    this.zzOX = paramString1;
    this.zzPa = paramString2;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public String getAccountType()
  {
    return this.zzOX;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzc.zza(this, paramParcel, paramInt);
  }
  
  public String zzlc()
  {
    return this.zzPa;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/auth/api/credentials/IdToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */