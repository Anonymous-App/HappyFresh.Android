package com.google.android.gms.common.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

public class zzz<T extends IInterface>
  extends zzi<T>
{
  private final Api.zzb<T> zzabf;
  
  public zzz(Context paramContext, Looper paramLooper, int paramInt, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener, zze paramzze, Api.zzb paramzzb)
  {
    super(paramContext, paramLooper, paramInt, paramConnectionCallbacks, paramOnConnectionFailedListener, paramzze);
    this.zzabf = paramzzb;
  }
  
  protected String getServiceDescriptor()
  {
    return this.zzabf.getServiceDescriptor();
  }
  
  protected String getStartServiceAction()
  {
    return this.zzabf.getStartServiceAction();
  }
  
  protected T zzT(IBinder paramIBinder)
  {
    return this.zzabf.zzT(paramIBinder);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/internal/zzz.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */