package com.google.android.gms.common.api;

import android.os.Bundle;
import android.os.DeadObjectException;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zzj;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class zzd
  implements zzh
{
  private final zzg zzWK;
  
  public zzd(zzg paramzzg)
  {
    this.zzWK = paramzzg;
  }
  
  private <A extends Api.Client> void zza(zzg.zze<A> paramzze)
    throws DeadObjectException
  {
    this.zzWK.zzb(paramzze);
    Api.Client localClient = this.zzWK.zza(paramzze.zzms());
    if ((!localClient.isConnected()) && (this.zzWK.zzXv.containsKey(paramzze.zzms())))
    {
      paramzze.zzr(new Status(17));
      return;
    }
    paramzze.zzb(localClient);
  }
  
  public void begin()
  {
    while (!this.zzWK.zzXo.isEmpty()) {
      try
      {
        zza((zzg.zze)this.zzWK.zzXo.remove());
      }
      catch (DeadObjectException localDeadObjectException)
      {
        Log.w("GoogleApiClientConnected", "Service died while flushing queue", localDeadObjectException);
      }
    }
  }
  
  public void connect() {}
  
  public String getName()
  {
    return "CONNECTED";
  }
  
  public void onConnected(Bundle paramBundle) {}
  
  public void onConnectionSuspended(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return;
    case 2: 
      zzaV(paramInt);
      this.zzWK.connect();
      return;
    }
    this.zzWK.zzmQ();
    zzaV(paramInt);
  }
  
  public <A extends Api.Client, R extends Result, T extends zza.zza<R, A>> T zza(T paramT)
  {
    return zzb(paramT);
  }
  
  public void zza(ConnectionResult paramConnectionResult, Api<?> paramApi, int paramInt) {}
  
  public void zzaV(int paramInt)
  {
    int i;
    if (paramInt == -1)
    {
      i = 1;
      if (i == 0) {
        break label69;
      }
      this.zzWK.zzmK();
      this.zzWK.zzXv.clear();
    }
    for (;;)
    {
      this.zzWK.zze(null);
      if (i == 0) {
        this.zzWK.zzXn.zzbu(paramInt);
      }
      this.zzWK.zzXn.zznT();
      return;
      i = 0;
      break;
      label69:
      Iterator localIterator = this.zzWK.zzXA.iterator();
      while (localIterator.hasNext()) {
        ((zzg.zze)localIterator.next()).forceFailureUnlessReady(new Status(8, "The connection to Google Play services was lost"));
      }
    }
  }
  
  public <A extends Api.Client, T extends zza.zza<? extends Result, A>> T zzb(T paramT)
  {
    try
    {
      zza(paramT);
      return paramT;
    }
    catch (DeadObjectException localDeadObjectException)
    {
      zzaV(1);
    }
    return paramT;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/api/zzd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */