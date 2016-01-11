package com.google.android.gms.analytics;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager.WakeLock;
import com.google.android.gms.analytics.internal.zzaf;
import com.google.android.gms.analytics.internal.zzam;
import com.google.android.gms.analytics.internal.zzb;
import com.google.android.gms.analytics.internal.zzf;
import com.google.android.gms.analytics.internal.zzr;
import com.google.android.gms.analytics.internal.zzw;
import com.google.android.gms.common.internal.zzu;

public final class AnalyticsService
  extends Service
{
  private static Boolean zzIe;
  private final Handler mHandler = new Handler();
  
  public static boolean zzU(Context paramContext)
  {
    zzu.zzu(paramContext);
    if (zzIe != null) {
      return zzIe.booleanValue();
    }
    boolean bool = zzam.zza(paramContext, AnalyticsService.class);
    zzIe = Boolean.valueOf(bool);
    return bool;
  }
  
  private void zzhd()
  {
    try
    {
      synchronized (AnalyticsReceiver.zzoW)
      {
        PowerManager.WakeLock localWakeLock = AnalyticsReceiver.zzIc;
        if ((localWakeLock != null) && (localWakeLock.isHeld())) {
          localWakeLock.release();
        }
        return;
      }
      return;
    }
    catch (SecurityException localSecurityException) {}
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }
  
  public void onCreate()
  {
    super.onCreate();
    zzf localzzf = zzf.zzV(this);
    zzaf localzzaf = localzzf.zzhQ();
    if (localzzf.zzhR().zziW())
    {
      localzzaf.zzaT("Device AnalyticsService is starting up");
      return;
    }
    localzzaf.zzaT("Local AnalyticsService is starting up");
  }
  
  public void onDestroy()
  {
    zzf localzzf = zzf.zzV(this);
    zzaf localzzaf = localzzf.zzhQ();
    if (localzzf.zzhR().zziW()) {
      localzzaf.zzaT("Device AnalyticsService is shutting down");
    }
    for (;;)
    {
      super.onDestroy();
      return;
      localzzaf.zzaT("Local AnalyticsService is shutting down");
    }
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, final int paramInt2)
  {
    zzhd();
    final zzf localzzf = zzf.zzV(this);
    final zzaf localzzaf = localzzf.zzhQ();
    paramIntent = paramIntent.getAction();
    if (localzzf.zzhR().zziW()) {
      localzzaf.zza("Device AnalyticsService called. startId, action", Integer.valueOf(paramInt2), paramIntent);
    }
    for (;;)
    {
      if ("com.google.android.gms.analytics.ANALYTICS_DISPATCH".equals(paramIntent)) {
        localzzf.zzhl().zza(new zzw()
        {
          public void zzc(Throwable paramAnonymousThrowable)
          {
            AnalyticsService.zza(AnalyticsService.this).post(new Runnable()
            {
              public void run()
              {
                if (AnalyticsService.this.stopSelfResult(AnalyticsService.1.this.zzIf))
                {
                  if (AnalyticsService.1.this.zzIg.zzhR().zziW()) {
                    AnalyticsService.1.this.zzIh.zzaT("Device AnalyticsService processed last dispatch request");
                  }
                }
                else {
                  return;
                }
                AnalyticsService.1.this.zzIh.zzaT("Local AnalyticsService processed last dispatch request");
              }
            });
          }
        });
      }
      return 2;
      localzzaf.zza("Local AnalyticsService called. startId, action", Integer.valueOf(paramInt2), paramIntent);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/analytics/AnalyticsService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */