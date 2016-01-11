package com.google.android.gms.analytics.internal;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.internal.zzlb;
import com.google.android.gms.internal.zzns;

abstract class zzt
{
  private static volatile Handler zzKS;
  private final zzf zzJy;
  private volatile long zzKT;
  private boolean zzKU;
  private final Runnable zzx;
  
  zzt(zzf paramzzf)
  {
    zzu.zzu(paramzzf);
    this.zzJy = paramzzf;
    this.zzx = new Runnable()
    {
      public void run()
      {
        if (Looper.myLooper() == Looper.getMainLooper()) {
          zzt.zza(zzt.this).zzhS().zze(this);
        }
        boolean bool;
        do
        {
          return;
          bool = zzt.this.zzbp();
          zzt.zza(zzt.this, 0L);
        } while ((!bool) || (zzt.zzb(zzt.this)));
        zzt.this.run();
      }
    };
  }
  
  private Handler getHandler()
  {
    if (zzKS != null) {
      return zzKS;
    }
    try
    {
      if (zzKS == null) {
        zzKS = new Handler(this.zzJy.getContext().getMainLooper());
      }
      Handler localHandler = zzKS;
      return localHandler;
    }
    finally {}
  }
  
  public void cancel()
  {
    this.zzKT = 0L;
    getHandler().removeCallbacks(this.zzx);
  }
  
  public abstract void run();
  
  public boolean zzbp()
  {
    return this.zzKT != 0L;
  }
  
  public long zzjD()
  {
    if (this.zzKT == 0L) {
      return 0L;
    }
    return Math.abs(this.zzJy.zzhP().currentTimeMillis() - this.zzKT);
  }
  
  public void zzt(long paramLong)
  {
    cancel();
    if (paramLong >= 0L)
    {
      this.zzKT = this.zzJy.zzhP().currentTimeMillis();
      if (!getHandler().postDelayed(this.zzx, paramLong)) {
        this.zzJy.zzhQ().zze("Failed to schedule delayed post. time", Long.valueOf(paramLong));
      }
    }
  }
  
  public void zzu(long paramLong)
  {
    long l = 0L;
    if (!zzbp()) {
      return;
    }
    if (paramLong < 0L)
    {
      cancel();
      return;
    }
    paramLong -= Math.abs(this.zzJy.zzhP().currentTimeMillis() - this.zzKT);
    if (paramLong < 0L) {
      paramLong = l;
    }
    for (;;)
    {
      getHandler().removeCallbacks(this.zzx);
      if (getHandler().postDelayed(this.zzx, paramLong)) {
        break;
      }
      this.zzJy.zzhQ().zze("Failed to adjust delayed post. time", Long.valueOf(paramLong));
      return;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/analytics/internal/zzt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */