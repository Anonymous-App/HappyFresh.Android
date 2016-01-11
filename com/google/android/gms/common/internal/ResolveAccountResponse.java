package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class ResolveAccountResponse
  implements SafeParcelable
{
  public static final Parcelable.Creator<ResolveAccountResponse> CREATOR = new zzw();
  final int zzCY;
  private boolean zzWY;
  private ConnectionResult zzYh;
  IBinder zzZO;
  private boolean zzabd;
  
  public ResolveAccountResponse(int paramInt)
  {
    this(new ConnectionResult(paramInt, null));
  }
  
  ResolveAccountResponse(int paramInt, IBinder paramIBinder, ConnectionResult paramConnectionResult, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.zzCY = paramInt;
    this.zzZO = paramIBinder;
    this.zzYh = paramConnectionResult;
    this.zzWY = paramBoolean1;
    this.zzabd = paramBoolean2;
  }
  
  public ResolveAccountResponse(ConnectionResult paramConnectionResult)
  {
    this(1, null, paramConnectionResult, false, false);
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    do
    {
      return true;
      if (!(paramObject instanceof ResolveAccountResponse)) {
        return false;
      }
      paramObject = (ResolveAccountResponse)paramObject;
    } while ((this.zzYh.equals(((ResolveAccountResponse)paramObject).zzYh)) && (zznZ().equals(((ResolveAccountResponse)paramObject).zznZ())));
    return false;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzw.zza(this, paramParcel, paramInt);
  }
  
  public IAccountAccessor zznZ()
  {
    return IAccountAccessor.zza.zzaD(this.zzZO);
  }
  
  public ConnectionResult zzoa()
  {
    return this.zzYh;
  }
  
  public boolean zzob()
  {
    return this.zzWY;
  }
  
  public boolean zzoc()
  {
    return this.zzabd;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/internal/ResolveAccountResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */