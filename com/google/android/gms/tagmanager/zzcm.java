package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzaf.zzj;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

class zzcm
  implements zzp.zze
{
  private boolean mClosed;
  private final Context mContext;
  private String zzaKV;
  private final String zzaKy;
  private zzbf<zzaf.zzj> zzaMU;
  private zzs zzaMV;
  private final ScheduledExecutorService zzaMX;
  private final zza zzaMY;
  private ScheduledFuture<?> zzaMZ;
  
  public zzcm(Context paramContext, String paramString, zzs paramzzs)
  {
    this(paramContext, paramString, paramzzs, null, null);
  }
  
  zzcm(Context paramContext, String paramString, zzs paramzzs, zzb paramzzb, zza paramzza)
  {
    this.zzaMV = paramzzs;
    this.mContext = paramContext;
    this.zzaKy = paramString;
    paramContext = paramzzb;
    if (paramzzb == null) {
      paramContext = new zzb()
      {
        public ScheduledExecutorService zzzm()
        {
          return Executors.newSingleThreadScheduledExecutor();
        }
      };
    }
    this.zzaMX = paramContext.zzzm();
    if (paramzza == null)
    {
      this.zzaMY = new zza()
      {
        public zzcl zza(zzs paramAnonymouszzs)
        {
          return new zzcl(zzcm.zza(zzcm.this), zzcm.zzb(zzcm.this), paramAnonymouszzs);
        }
      };
      return;
    }
    this.zzaMY = paramzza;
  }
  
  private zzcl zzeC(String paramString)
  {
    zzcl localzzcl = this.zzaMY.zza(this.zzaMV);
    localzzcl.zza(this.zzaMU);
    localzzcl.zzem(this.zzaKV);
    localzzcl.zzeB(paramString);
    return localzzcl;
  }
  
  private void zzzl()
  {
    try
    {
      if (this.mClosed) {
        throw new IllegalStateException("called method after closed");
      }
    }
    finally {}
  }
  
  public void release()
  {
    try
    {
      zzzl();
      if (this.zzaMZ != null) {
        this.zzaMZ.cancel(false);
      }
      this.zzaMX.shutdown();
      this.mClosed = true;
      return;
    }
    finally {}
  }
  
  public void zza(zzbf<zzaf.zzj> paramzzbf)
  {
    try
    {
      zzzl();
      this.zzaMU = paramzzbf;
      return;
    }
    finally
    {
      paramzzbf = finally;
      throw paramzzbf;
    }
  }
  
  public void zzem(String paramString)
  {
    try
    {
      zzzl();
      this.zzaKV = paramString;
      return;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  public void zzf(long paramLong, String paramString)
  {
    try
    {
      zzbg.zzaB("loadAfterDelay: containerId=" + this.zzaKy + " delay=" + paramLong);
      zzzl();
      if (this.zzaMU == null) {
        throw new IllegalStateException("callback must be set before loadAfterDelay() is called.");
      }
    }
    finally {}
    if (this.zzaMZ != null) {
      this.zzaMZ.cancel(false);
    }
    this.zzaMZ = this.zzaMX.schedule(zzeC(paramString), paramLong, TimeUnit.MILLISECONDS);
  }
  
  static abstract interface zza
  {
    public abstract zzcl zza(zzs paramzzs);
  }
  
  static abstract interface zzb
  {
    public abstract ScheduledExecutorService zzzm();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzcm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */