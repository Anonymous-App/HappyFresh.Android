package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.Binder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class zza
  extends IAccountAccessor.zza
{
  private Context mContext;
  private Account zzMY;
  int zzZN;
  
  public static Account zza(IAccountAccessor paramIAccountAccessor)
  {
    Account localAccount = null;
    long l;
    if (paramIAccountAccessor != null) {
      l = Binder.clearCallingIdentity();
    }
    try
    {
      localAccount = paramIAccountAccessor.getAccount();
      return localAccount;
    }
    catch (RemoteException paramIAccountAccessor)
    {
      Log.w("AccountAccessor", "Remote account accessor probably died");
      return null;
    }
    finally
    {
      Binder.restoreCallingIdentity(l);
    }
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof zza)) {
      return false;
    }
    return this.zzMY.equals(((zza)paramObject).zzMY);
  }
  
  public Account getAccount()
  {
    int i = Binder.getCallingUid();
    if (i == this.zzZN) {
      return this.zzMY;
    }
    if (GooglePlayServicesUtil.zzd(this.mContext, i))
    {
      this.zzZN = i;
      return this.zzMY;
    }
    throw new SecurityException("Caller is not GooglePlayServices");
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/internal/zza.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */