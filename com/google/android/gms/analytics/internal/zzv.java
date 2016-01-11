package com.google.android.gms.analytics.internal;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import com.google.android.gms.analytics.AnalyticsReceiver;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.internal.zzlb;

public class zzv
  extends zzd
{
  private boolean zzKW;
  private boolean zzKX;
  private AlarmManager zzKY = (AlarmManager)getContext().getSystemService("alarm");
  
  protected zzv(zzf paramzzf)
  {
    super(paramzzf);
  }
  
  private PendingIntent zzjI()
  {
    Intent localIntent = new Intent(getContext(), AnalyticsReceiver.class);
    localIntent.setAction("com.google.android.gms.analytics.ANALYTICS_DISPATCH");
    return PendingIntent.getBroadcast(getContext(), 0, localIntent, 0);
  }
  
  public void cancel()
  {
    zzia();
    this.zzKX = false;
    this.zzKY.cancel(zzjI());
  }
  
  public boolean zzbp()
  {
    return this.zzKX;
  }
  
  protected void zzhn()
  {
    try
    {
      this.zzKY.cancel(zzjI());
      if (zzhR().zzjf() > 0L)
      {
        ActivityInfo localActivityInfo = getContext().getPackageManager().getReceiverInfo(new ComponentName(getContext(), AnalyticsReceiver.class), 2);
        if ((localActivityInfo != null) && (localActivityInfo.enabled))
        {
          zzaT("Receiver registered. Using alarm for local dispatch.");
          this.zzKW = true;
        }
      }
      return;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException) {}
  }
  
  public boolean zzjG()
  {
    return this.zzKW;
  }
  
  public void zzjH()
  {
    zzia();
    zzu.zza(zzjG(), "Receiver not registered");
    long l1 = zzhR().zzjf();
    if (l1 > 0L)
    {
      cancel();
      long l2 = zzhP().elapsedRealtime();
      this.zzKX = true;
      this.zzKY.setInexactRepeating(2, l2 + l1, 0L, zzjI());
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/analytics/internal/zzv.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */