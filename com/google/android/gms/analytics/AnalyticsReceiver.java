package com.google.android.gms.analytics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import com.google.android.gms.analytics.internal.zzaf;
import com.google.android.gms.analytics.internal.zzam;
import com.google.android.gms.analytics.internal.zzf;
import com.google.android.gms.analytics.internal.zzr;
import com.google.android.gms.common.internal.zzu;

public final class AnalyticsReceiver
  extends BroadcastReceiver
{
  static PowerManager.WakeLock zzIc;
  static Boolean zzId;
  static Object zzoW = new Object();
  
  public static boolean zzT(Context paramContext)
  {
    zzu.zzu(paramContext);
    if (zzId != null) {
      return zzId.booleanValue();
    }
    boolean bool = zzam.zza(paramContext, AnalyticsReceiver.class, false);
    zzId = Boolean.valueOf(bool);
    return bool;
  }
  
  public void onReceive(Context paramContext, Intent arg2)
  {
    Object localObject = zzf.zzV(paramContext);
    localzzaf = ((zzf)localObject).zzhQ();
    ??? = ???.getAction();
    if (((zzf)localObject).zzhR().zziW()) {
      localzzaf.zza("Device AnalyticsReceiver got", ???);
    }
    for (;;)
    {
      boolean bool;
      if ("com.google.android.gms.analytics.ANALYTICS_DISPATCH".equals(???))
      {
        bool = AnalyticsService.zzU(paramContext);
        localObject = new Intent(paramContext, AnalyticsService.class);
        ((Intent)localObject).setAction("com.google.android.gms.analytics.ANALYTICS_DISPATCH");
      }
      synchronized (zzoW)
      {
        paramContext.startService((Intent)localObject);
        if (!bool)
        {
          return;
          localzzaf.zza("Local AnalyticsReceiver got", ???);
          continue;
        }
        try
        {
          paramContext = (PowerManager)paramContext.getSystemService("power");
          if (zzIc == null)
          {
            zzIc = paramContext.newWakeLock(1, "Analytics WakeLock");
            zzIc.setReferenceCounted(false);
          }
          zzIc.acquire(1000L);
        }
        catch (SecurityException paramContext)
        {
          for (;;)
          {
            localzzaf.zzaW("Analytics service at risk of not starting. For more reliable analytics, add the WAKE_LOCK permission to your manifest. See http://goo.gl/8Rd3yj for instructions.");
          }
        }
        return;
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/analytics/AnalyticsReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */