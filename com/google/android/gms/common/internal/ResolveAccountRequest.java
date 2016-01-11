package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class ResolveAccountRequest
  implements SafeParcelable
{
  public static final Parcelable.Creator<ResolveAccountRequest> CREATOR = new zzv();
  final int zzCY;
  private final Account zzMY;
  private final int zzabc;
  
  ResolveAccountRequest(int paramInt1, Account paramAccount, int paramInt2)
  {
    this.zzCY = paramInt1;
    this.zzMY = paramAccount;
    this.zzabc = paramInt2;
  }
  
  public ResolveAccountRequest(Account paramAccount, int paramInt)
  {
    this(1, paramAccount, paramInt);
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public Account getAccount()
  {
    return this.zzMY;
  }
  
  public int getSessionId()
  {
    return this.zzabc;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzv.zza(this, paramParcel, paramInt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/internal/ResolveAccountRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */