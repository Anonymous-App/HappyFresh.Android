package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.text.TextUtils;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.Auth.zza;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zze;
import com.google.android.gms.common.internal.zzi;
import java.util.Set;

public final class zzjj
  extends zzi<zzjl>
{
  private final Bundle zzOR;
  
  public zzjj(Context paramContext, Looper paramLooper, zze paramzze, Auth.zza paramzza, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
  {
    super(paramContext, paramLooper, 16, paramConnectionCallbacks, paramOnConnectionFailedListener, paramzze);
    if (paramzza == null) {}
    for (paramContext = new Bundle();; paramContext = paramzza.zzkY())
    {
      this.zzOR = paramContext;
      return;
    }
  }
  
  protected String getServiceDescriptor()
  {
    return "com.google.android.gms.auth.api.internal.IAuthService";
  }
  
  protected String getStartServiceAction()
  {
    return "com.google.android.gms.auth.service.START";
  }
  
  public boolean requiresSignIn()
  {
    zze localzze = zznK();
    return (!TextUtils.isEmpty(localzze.getAccountName())) && (!localzze.zzb(Auth.zzOL).isEmpty());
  }
  
  protected zzjl zzar(IBinder paramIBinder)
  {
    return zzjl.zza.zzat(paramIBinder);
  }
  
  protected Bundle zzkR()
  {
    return this.zzOR;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzjj.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */