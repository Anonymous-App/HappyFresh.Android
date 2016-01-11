package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class AuthAccountResult
  implements SafeParcelable
{
  public static final Parcelable.Creator<AuthAccountResult> CREATOR = new zza();
  final int zzCY;
  
  public AuthAccountResult()
  {
    this(1);
  }
  
  AuthAccountResult(int paramInt)
  {
    this.zzCY = paramInt;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zza.zza(this, paramParcel, paramInt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/signin/internal/AuthAccountResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */