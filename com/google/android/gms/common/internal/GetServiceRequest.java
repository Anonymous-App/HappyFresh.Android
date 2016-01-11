package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.Collection;

public class GetServiceRequest
  implements SafeParcelable
{
  public static final Parcelable.Creator<GetServiceRequest> CREATOR = new zzh();
  final int version;
  final int zzaad;
  int zzaae;
  String zzaaf;
  IBinder zzaag;
  Scope[] zzaah;
  Bundle zzaai;
  Account zzaaj;
  
  public GetServiceRequest(int paramInt)
  {
    this.version = 2;
    this.zzaae = GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    this.zzaad = paramInt;
  }
  
  GetServiceRequest(int paramInt1, int paramInt2, int paramInt3, String paramString, IBinder paramIBinder, Scope[] paramArrayOfScope, Bundle paramBundle, Account paramAccount)
  {
    this.version = paramInt1;
    this.zzaad = paramInt2;
    this.zzaae = paramInt3;
    this.zzaaf = paramString;
    if (paramInt1 < 2) {}
    for (this.zzaaj = zzaC(paramIBinder);; this.zzaaj = paramAccount)
    {
      this.zzaah = paramArrayOfScope;
      this.zzaai = paramBundle;
      return;
      this.zzaag = paramIBinder;
    }
  }
  
  private Account zzaC(IBinder paramIBinder)
  {
    Account localAccount = null;
    if (paramIBinder != null) {
      localAccount = zza.zza(IAccountAccessor.zza.zzaD(paramIBinder));
    }
    return localAccount;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzh.zza(this, paramParcel, paramInt);
  }
  
  public GetServiceRequest zzb(Account paramAccount)
  {
    this.zzaaj = paramAccount;
    return this;
  }
  
  public GetServiceRequest zzb(IAccountAccessor paramIAccountAccessor)
  {
    if (paramIAccountAccessor != null) {
      this.zzaag = paramIAccountAccessor.asBinder();
    }
    return this;
  }
  
  public GetServiceRequest zzb(Collection<Scope> paramCollection)
  {
    this.zzaah = ((Scope[])paramCollection.toArray(new Scope[paramCollection.size()]));
    return this;
  }
  
  public GetServiceRequest zzcb(String paramString)
  {
    this.zzaaf = paramString;
    return this;
  }
  
  public GetServiceRequest zzf(Bundle paramBundle)
  {
    this.zzaai = paramBundle;
    return this;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/internal/GetServiceRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */