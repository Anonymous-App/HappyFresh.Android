package com.google.android.gms.common.api;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.common.internal.zzu;

public final class zzi<L>
{
  private volatile L mListener;
  private final zzi<L>.zza zzXL;
  
  zzi(Looper paramLooper, L paramL)
  {
    this.zzXL = new zza(paramLooper);
    this.mListener = zzu.zzb(paramL, "Listener must not be null");
  }
  
  public void clear()
  {
    this.mListener = null;
  }
  
  public void zza(zzb<? super L> paramzzb)
  {
    zzu.zzb(paramzzb, "Notifier must not be null");
    paramzzb = this.zzXL.obtainMessage(1, paramzzb);
    this.zzXL.sendMessage(paramzzb);
  }
  
  void zzb(zzb<? super L> paramzzb)
  {
    Object localObject = this.mListener;
    if (localObject == null)
    {
      paramzzb.zzmw();
      return;
    }
    try
    {
      paramzzb.zzn(localObject);
      return;
    }
    catch (RuntimeException localRuntimeException)
    {
      paramzzb.zzmw();
      throw localRuntimeException;
    }
  }
  
  private final class zza
    extends Handler
  {
    public zza(Looper paramLooper)
    {
      super();
    }
    
    public void handleMessage(Message paramMessage)
    {
      boolean bool = true;
      if (paramMessage.what == 1) {}
      for (;;)
      {
        zzu.zzV(bool);
        zzi.this.zzb((zzi.zzb)paramMessage.obj);
        return;
        bool = false;
      }
    }
  }
  
  public static abstract interface zzb<L>
  {
    public abstract void zzmw();
    
    public abstract void zzn(L paramL);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/api/zzi.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */