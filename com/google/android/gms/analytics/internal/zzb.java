package com.google.android.gms.analytics.internal;

import android.content.Context;
import android.content.Intent;
import com.google.android.gms.analytics.AnalyticsReceiver;
import com.google.android.gms.analytics.AnalyticsService;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.internal.zzns;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class zzb
  extends zzd
{
  private final zzl zzJq;
  
  public zzb(zzf paramzzf, zzg paramzzg)
  {
    super(paramzzf);
    zzu.zzu(paramzzg);
    this.zzJq = paramzzg.zzj(paramzzf);
  }
  
  void onServiceConnected()
  {
    zzhO();
    this.zzJq.onServiceConnected();
  }
  
  public void setLocalDispatchPeriod(final int paramInt)
  {
    zzia();
    zzb("setLocalDispatchPeriod (sec)", Integer.valueOf(paramInt));
    zzhS().zze(new Runnable()
    {
      public void run()
      {
        zzb.zza(zzb.this).zzs(paramInt * 1000L);
      }
    });
  }
  
  public void start()
  {
    this.zzJq.start();
  }
  
  public void zzG(final boolean paramBoolean)
  {
    zza("Network connectivity status changed", Boolean.valueOf(paramBoolean));
    zzhS().zze(new Runnable()
    {
      public void run()
      {
        zzb.zza(zzb.this).zzG(paramBoolean);
      }
    });
  }
  
  public long zza(zzh paramzzh)
  {
    zzia();
    zzu.zzu(paramzzh);
    zzhO();
    long l = this.zzJq.zza(paramzzh, true);
    if (l == 0L) {
      this.zzJq.zzc(paramzzh);
    }
    return l;
  }
  
  public void zza(final zzab paramzzab)
  {
    zzu.zzu(paramzzab);
    zzia();
    zzb("Hit delivery requested", paramzzab);
    zzhS().zze(new Runnable()
    {
      public void run()
      {
        zzb.zza(zzb.this).zza(paramzzab);
      }
    });
  }
  
  public void zza(final zzw paramzzw)
  {
    zzia();
    zzhS().zze(new Runnable()
    {
      public void run()
      {
        zzb.zza(zzb.this).zzb(paramzzw);
      }
    });
  }
  
  public void zza(final String paramString, final Runnable paramRunnable)
  {
    zzu.zzh(paramString, "campaign param can't be empty");
    zzhS().zze(new Runnable()
    {
      public void run()
      {
        zzb.zza(zzb.this).zzbb(paramString);
        if (paramRunnable != null) {
          paramRunnable.run();
        }
      }
    });
  }
  
  public void zzhG()
  {
    zzia();
    zzhN();
    zzhS().zze(new Runnable()
    {
      public void run()
      {
        zzb.zza(zzb.this).zzhG();
      }
    });
  }
  
  public void zzhH()
  {
    zzia();
    Context localContext = getContext();
    if ((AnalyticsReceiver.zzT(localContext)) && (AnalyticsService.zzU(localContext)))
    {
      Intent localIntent = new Intent(localContext, AnalyticsService.class);
      localIntent.setAction("com.google.android.gms.analytics.ANALYTICS_DISPATCH");
      localContext.startService(localIntent);
      return;
    }
    zza(null);
  }
  
  public boolean zzhI()
  {
    zzia();
    Future localFuture = zzhS().zzb(new Callable()
    {
      public Void zzgk()
        throws Exception
      {
        zzb.zza(zzb.this).zziF();
        return null;
      }
    });
    try
    {
      localFuture.get();
      return true;
    }
    catch (InterruptedException localInterruptedException)
    {
      zzd("syncDispatchLocalHits interrupted", localInterruptedException);
      return false;
    }
    catch (ExecutionException localExecutionException)
    {
      zze("syncDispatchLocalHits failed", localExecutionException);
    }
    return false;
  }
  
  public void zzhJ()
  {
    zzia();
    zzns.zzhO();
    this.zzJq.zzhJ();
  }
  
  public void zzhK()
  {
    zzaT("Radio powered up");
    zzhH();
  }
  
  void zzhL()
  {
    zzhO();
    this.zzJq.zzhL();
  }
  
  protected void zzhn()
  {
    this.zzJq.zza();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/analytics/internal/zzb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */