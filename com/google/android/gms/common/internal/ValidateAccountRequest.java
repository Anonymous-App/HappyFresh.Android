package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class ValidateAccountRequest
  implements SafeParcelable
{
  public static final Parcelable.Creator<ValidateAccountRequest> CREATOR = new zzaa();
  final int zzCY;
  final IBinder zzZO;
  private final Scope[] zzZP;
  private final int zzabg;
  private final Bundle zzabh;
  private final String zzabi;
  
  ValidateAccountRequest(int paramInt1, int paramInt2, IBinder paramIBinder, Scope[] paramArrayOfScope, Bundle paramBundle, String paramString)
  {
    this.zzCY = paramInt1;
    this.zzabg = paramInt2;
    this.zzZO = paramIBinder;
    this.zzZP = paramArrayOfScope;
    this.zzabh = paramBundle;
    this.zzabi = paramString;
  }
  
  public ValidateAccountRequest(IAccountAccessor paramIAccountAccessor, Scope[] paramArrayOfScope, String paramString, Bundle paramBundle) {}
  
  public int describeContents()
  {
    return 0;
  }
  
  public String getCallingPackage()
  {
    return this.zzabi;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzaa.zza(this, paramParcel, paramInt);
  }
  
  public int zzod()
  {
    return this.zzabg;
  }
  
  public Scope[] zzoe()
  {
    return this.zzZP;
  }
  
  public Bundle zzof()
  {
    return this.zzabh;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/internal/ValidateAccountRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */