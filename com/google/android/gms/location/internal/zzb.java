package com.google.android.gms.location.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zze;
import com.google.android.gms.common.internal.zzi;

public class zzb
  extends zzi<zzg>
{
  protected final zzn<zzg> zzayq = new zzn()
  {
    public void zznL()
    {
      zzb.zza(zzb.this);
    }
    
    public zzg zzut()
      throws DeadObjectException
    {
      return (zzg)zzb.this.zznM();
    }
  };
  private final String zzayw;
  
  public zzb(Context paramContext, Looper paramLooper, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener, String paramString, zze paramzze)
  {
    super(paramContext, paramLooper, 23, paramConnectionCallbacks, paramOnConnectionFailedListener, paramzze);
    this.zzayw = paramString;
  }
  
  protected String getServiceDescriptor()
  {
    return "com.google.android.gms.location.internal.IGoogleLocationManagerService";
  }
  
  protected String getStartServiceAction()
  {
    return "com.google.android.location.internal.GoogleLocationManagerService.START";
  }
  
  protected zzg zzbU(IBinder paramIBinder)
  {
    return zzg.zza.zzbW(paramIBinder);
  }
  
  protected Bundle zzkR()
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("client_name", this.zzayw);
    return localBundle;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/internal/zzb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */