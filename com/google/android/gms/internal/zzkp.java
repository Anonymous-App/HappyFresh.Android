package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzi;

public class zzkp
  extends zzi<zzkr>
{
  public zzkp(Context paramContext, Looper paramLooper, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
  {
    super(paramContext, paramLooper, 39, paramConnectionCallbacks, paramOnConnectionFailedListener);
  }
  
  protected String getServiceDescriptor()
  {
    return "com.google.android.gms.common.internal.service.ICommonService";
  }
  
  public String getStartServiceAction()
  {
    return "com.google.android.gms.common.service.START";
  }
  
  protected zzkr zzaK(IBinder paramIBinder)
  {
    return zzkr.zza.zzaM(paramIBinder);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzkp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */