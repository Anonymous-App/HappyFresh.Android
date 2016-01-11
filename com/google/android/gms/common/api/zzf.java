package com.google.android.gms.common.api;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;

public class zzf
  implements zzh
{
  private final zzg zzWK;
  
  public zzf(zzg paramzzg)
  {
    this.zzWK = paramzzg;
  }
  
  public void begin()
  {
    this.zzWK.zzmL();
  }
  
  public void connect()
  {
    this.zzWK.zzmM();
  }
  
  public String getName()
  {
    return "DISCONNECTED";
  }
  
  public void onConnected(Bundle paramBundle) {}
  
  public void onConnectionSuspended(int paramInt) {}
  
  public <A extends Api.Client, R extends Result, T extends zza.zza<R, A>> T zza(T paramT)
  {
    this.zzWK.zzXo.add(paramT);
    return paramT;
  }
  
  public void zza(ConnectionResult paramConnectionResult, Api<?> paramApi, int paramInt) {}
  
  public void zzaV(int paramInt)
  {
    if (paramInt == -1) {
      paramInt = 1;
    }
    while (paramInt != 0)
    {
      Iterator localIterator = this.zzWK.zzXo.iterator();
      for (;;)
      {
        if (localIterator.hasNext())
        {
          ((zzg.zze)localIterator.next()).cancel();
          continue;
          paramInt = 0;
          break;
        }
      }
      this.zzWK.zzXo.clear();
      this.zzWK.zzmK();
      this.zzWK.zzXv.clear();
    }
  }
  
  public <A extends Api.Client, T extends zza.zza<? extends Result, A>> T zzb(T paramT)
  {
    throw new IllegalStateException("GoogleApiClient is not connected yet.");
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/api/zzf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */