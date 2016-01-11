package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.tagmanager.zzbg;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class zzqp
{
  private boolean mClosed;
  private String zzaLc = null;
  private final ScheduledExecutorService zzaMX;
  private ScheduledFuture<?> zzaMZ = null;
  
  public zzqp()
  {
    this(Executors.newSingleThreadScheduledExecutor());
  }
  
  public zzqp(String paramString)
  {
    this(Executors.newSingleThreadScheduledExecutor());
    this.zzaLc = paramString;
  }
  
  zzqp(ScheduledExecutorService paramScheduledExecutorService)
  {
    this.zzaMX = paramScheduledExecutorService;
    this.mClosed = false;
  }
  
  public void zza(Context paramContext, zzqd paramzzqd, long paramLong, zzqn paramzzqn)
  {
    for (;;)
    {
      try
      {
        zzbg.zzaB("ResourceLoaderScheduler: Loading new resource.");
        if (this.zzaMZ != null) {
          return;
        }
        if (this.zzaLc != null)
        {
          paramContext = new zzqo(paramContext, paramzzqd, paramzzqn, this.zzaLc);
          this.zzaMZ = this.zzaMX.schedule(paramContext, paramLong, TimeUnit.MILLISECONDS);
          return;
        }
      }
      finally {}
      paramContext = new zzqo(paramContext, paramzzqd, paramzzqn);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzqp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */