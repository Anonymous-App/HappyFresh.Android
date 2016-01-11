package com.google.android.gms.analytics;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.analytics.internal.zzae;
import com.google.android.gms.analytics.internal.zzak;
import com.google.android.gms.analytics.internal.zzal;
import com.google.android.gms.analytics.internal.zzan;
import com.google.android.gms.analytics.internal.zzb;
import com.google.android.gms.analytics.internal.zzf;
import com.google.android.gms.analytics.internal.zzn;
import com.google.android.gms.analytics.internal.zzy;
import com.google.android.gms.analytics.internal.zzy.zza;
import com.google.android.gms.common.internal.zzu;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class GoogleAnalytics
  extends zza
{
  private static List<Runnable> zzIt = new ArrayList();
  private boolean zzIu;
  private Set<zza> zzIv = new HashSet();
  private boolean zzIw;
  private boolean zzIx;
  private volatile boolean zzIy;
  private boolean zzIz;
  private boolean zzpb;
  
  public GoogleAnalytics(zzf paramzzf)
  {
    super(paramzzf);
  }
  
  public static GoogleAnalytics getInstance(Context paramContext)
  {
    return zzf.zzV(paramContext).zzie();
  }
  
  public static void zzhj()
  {
    try
    {
      if (zzIt != null)
      {
        Iterator localIterator = zzIt.iterator();
        while (localIterator.hasNext()) {
          ((Runnable)localIterator.next()).run();
        }
        zzIt = null;
      }
    }
    finally {}
  }
  
  private zzb zzhl()
  {
    return zzhb().zzhl();
  }
  
  private zzan zzhm()
  {
    return zzhb().zzhm();
  }
  
  public void dispatchLocalHits()
  {
    zzhl().zzhH();
  }
  
  public void enableAutoActivityReports(Application paramApplication)
  {
    if ((Build.VERSION.SDK_INT >= 14) && (!this.zzIw))
    {
      paramApplication.registerActivityLifecycleCallbacks(new zzb());
      this.zzIw = true;
    }
  }
  
  public boolean getAppOptOut()
  {
    return this.zzIy;
  }
  
  public String getClientId()
  {
    zzu.zzbZ("getClientId can not be called from the main thread");
    return zzhb().zzih().zziP();
  }
  
  @Deprecated
  public Logger getLogger()
  {
    return zzae.getLogger();
  }
  
  public boolean isDryRunEnabled()
  {
    return this.zzIx;
  }
  
  public boolean isInitialized()
  {
    return (this.zzpb) && (!this.zzIu);
  }
  
  public Tracker newTracker(int paramInt)
  {
    try
    {
      Tracker localTracker = new Tracker(zzhb(), null, null);
      if (paramInt > 0)
      {
        zzal localzzal = (zzal)new zzak(zzhb()).zzab(paramInt);
        if (localzzal != null) {
          localTracker.zza(localzzal);
        }
      }
      localTracker.zza();
      return localTracker;
    }
    finally {}
  }
  
  public Tracker newTracker(String paramString)
  {
    try
    {
      paramString = new Tracker(zzhb(), paramString, null);
      paramString.zza();
      return paramString;
    }
    finally {}
  }
  
  public void reportActivityStart(Activity paramActivity)
  {
    if (!this.zzIw) {
      zzl(paramActivity);
    }
  }
  
  public void reportActivityStop(Activity paramActivity)
  {
    if (!this.zzIw) {
      zzm(paramActivity);
    }
  }
  
  public void setAppOptOut(boolean paramBoolean)
  {
    this.zzIy = paramBoolean;
    if (this.zzIy) {
      zzhl().zzhG();
    }
  }
  
  public void setDryRun(boolean paramBoolean)
  {
    this.zzIx = paramBoolean;
  }
  
  public void setLocalDispatchPeriod(int paramInt)
  {
    zzhl().setLocalDispatchPeriod(paramInt);
  }
  
  @Deprecated
  public void setLogger(Logger paramLogger)
  {
    zzae.setLogger(paramLogger);
    if (!this.zzIz)
    {
      Log.i((String)zzy.zzLb.get(), "GoogleAnalytics.setLogger() is deprecated. To enable debug logging, please run:\nadb shell setprop log.tag." + (String)zzy.zzLb.get() + " DEBUG");
      this.zzIz = true;
    }
  }
  
  public void zza()
  {
    zzhi();
    this.zzpb = true;
  }
  
  void zza(zza paramzza)
  {
    this.zzIv.add(paramzza);
    paramzza = zzhb().getContext();
    if ((paramzza instanceof Application)) {
      enableAutoActivityReports((Application)paramzza);
    }
  }
  
  void zzb(zza paramzza)
  {
    this.zzIv.remove(paramzza);
  }
  
  void zzhi()
  {
    zzan localzzan = zzhm();
    if (localzzan.zzjO()) {
      getLogger().setLogLevel(localzzan.getLogLevel());
    }
    if (localzzan.zzjS()) {
      setDryRun(localzzan.zzjT());
    }
    if (localzzan.zzjO())
    {
      Logger localLogger = zzae.getLogger();
      if (localLogger != null) {
        localLogger.setLogLevel(localzzan.getLogLevel());
      }
    }
  }
  
  void zzhk()
  {
    zzhl().zzhI();
  }
  
  void zzl(Activity paramActivity)
  {
    Iterator localIterator = this.zzIv.iterator();
    while (localIterator.hasNext()) {
      ((zza)localIterator.next()).zzn(paramActivity);
    }
  }
  
  void zzm(Activity paramActivity)
  {
    Iterator localIterator = this.zzIv.iterator();
    while (localIterator.hasNext()) {
      ((zza)localIterator.next()).zzo(paramActivity);
    }
  }
  
  static abstract interface zza
  {
    public abstract void zzn(Activity paramActivity);
    
    public abstract void zzo(Activity paramActivity);
  }
  
  class zzb
    implements Application.ActivityLifecycleCallbacks
  {
    zzb() {}
    
    public void onActivityCreated(Activity paramActivity, Bundle paramBundle) {}
    
    public void onActivityDestroyed(Activity paramActivity) {}
    
    public void onActivityPaused(Activity paramActivity) {}
    
    public void onActivityResumed(Activity paramActivity) {}
    
    public void onActivitySaveInstanceState(Activity paramActivity, Bundle paramBundle) {}
    
    public void onActivityStarted(Activity paramActivity)
    {
      GoogleAnalytics.this.zzl(paramActivity);
    }
    
    public void onActivityStopped(Activity paramActivity)
    {
      GoogleAnalytics.this.zzm(paramActivity);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/analytics/GoogleAnalytics.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */