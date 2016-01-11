package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zze;
import com.google.android.gms.common.internal.zzi;

public class zzjg
  extends zzi<zzji>
{
  public zzjg(Context paramContext, Looper paramLooper, zze paramzze, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
  {
    super(paramContext, paramLooper, 74, paramConnectionCallbacks, paramOnConnectionFailedListener, paramzze);
  }
  
  protected String getServiceDescriptor()
  {
    return "com.google.android.gms.auth.api.accountstatus.internal.IAccountStatusService";
  }
  
  protected String getStartServiceAction()
  {
    return "com.google.android.gms.auth.api.accountstatus.START";
  }
  
  protected zzji zzam(IBinder paramIBinder)
  {
    return zzji.zza.zzao(paramIBinder);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzjg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */