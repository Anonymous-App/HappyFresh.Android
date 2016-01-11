package com.google.android.gms.tagmanager;

import android.content.Context;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.LinkedBlockingQueue;

class zzat
  extends Thread
  implements zzas
{
  private static zzat zzaLP;
  private volatile boolean mClosed = false;
  private final Context mContext;
  private volatile boolean zzKU = false;
  private final LinkedBlockingQueue<Runnable> zzaLO = new LinkedBlockingQueue();
  private volatile zzau zzaLQ;
  
  private zzat(Context paramContext)
  {
    super("GAThread");
    if (paramContext != null) {}
    for (this.mContext = paramContext.getApplicationContext();; this.mContext = paramContext)
    {
      start();
      return;
    }
  }
  
  static zzat zzaH(Context paramContext)
  {
    if (zzaLP == null) {
      zzaLP = new zzat(paramContext);
    }
    return zzaLP;
  }
  
  private String zzd(Throwable paramThrowable)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream localPrintStream = new PrintStream(localByteArrayOutputStream);
    paramThrowable.printStackTrace(localPrintStream);
    localPrintStream.flush();
    return new String(localByteArrayOutputStream.toByteArray());
  }
  
  public void run()
  {
    while (!this.mClosed) {
      try
      {
        Runnable localRunnable = (Runnable)this.zzaLO.take();
        if (!this.zzKU) {
          localRunnable.run();
        }
      }
      catch (InterruptedException localInterruptedException)
      {
        zzbg.zzaA(localInterruptedException.toString());
      }
      catch (Throwable localThrowable)
      {
        zzbg.zzaz("Error on Google TagManager Thread: " + zzd(localThrowable));
        zzbg.zzaz("Google TagManager is shutting down.");
        this.zzKU = true;
      }
    }
  }
  
  public void zzew(String paramString)
  {
    zzh(paramString, System.currentTimeMillis());
  }
  
  public void zzf(Runnable paramRunnable)
  {
    this.zzaLO.add(paramRunnable);
  }
  
  void zzh(String paramString, final long paramLong)
  {
    zzf(new Runnable()
    {
      public void run()
      {
        if (zzat.zza(zzat.this) == null)
        {
          zzcu localzzcu = zzcu.zzzz();
          localzzcu.zza(zzat.zzb(zzat.this), jdField_this);
          zzat.zza(zzat.this, localzzcu.zzzC());
        }
        zzat.zza(zzat.this).zzg(paramLong, this.zzwJ);
      }
    });
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */