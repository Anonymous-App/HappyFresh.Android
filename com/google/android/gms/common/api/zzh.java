package com.google.android.gms.common.api;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;

public abstract interface zzh
{
  public abstract void begin();
  
  public abstract void connect();
  
  public abstract String getName();
  
  public abstract void onConnected(Bundle paramBundle);
  
  public abstract void onConnectionSuspended(int paramInt);
  
  public abstract <A extends Api.Client, R extends Result, T extends zza.zza<R, A>> T zza(T paramT);
  
  public abstract void zza(ConnectionResult paramConnectionResult, Api<?> paramApi, int paramInt);
  
  public abstract void zzaV(int paramInt);
  
  public abstract <A extends Api.Client, T extends zza.zza<? extends Result, A>> T zzb(T paramT);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/api/zzh.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */