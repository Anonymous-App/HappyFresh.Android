package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders.ScreenViewBuilder;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.tagmanager.Container;

class zzpu
{
  private final Context mContext;
  private final zzpv zzoY;
  
  public zzpu(Context paramContext, Container paramContainer, zzpv paramzzpv)
  {
    this.mContext = paramContext;
    this.zzoY = zza(paramContainer, paramzzpv);
    zzzS();
  }
  
  static zzpv zza(Container paramContainer, zzpv paramzzpv)
  {
    if ((paramContainer == null) || (paramContainer.isDefault())) {
      return paramzzpv;
    }
    paramzzpv = new zzpv.zza(paramzzpv.zzzT());
    paramzzpv.zzeS(paramContainer.getString("trackingId")).zzap(paramContainer.getBoolean("trackScreenViews")).zzaq(paramContainer.getBoolean("collectAdIdentifiers"));
    return paramzzpv.zzzW();
  }
  
  private void zzzS()
  {
    if ((this.zzoY.zzzU()) && (!TextUtils.isEmpty(this.zzoY.getTrackingId())))
    {
      Tracker localTracker = zzeR(this.zzoY.getTrackingId());
      localTracker.enableAdvertisingIdCollection(this.zzoY.zzzV());
      zzb(new zza(localTracker));
    }
  }
  
  void zzb(zznw.zza paramzza)
  {
    zzu.zzu(paramzza);
    zznw localzznw = zznw.zzaC(this.mContext);
    localzznw.zzaf(true);
    localzznw.zza(paramzza);
  }
  
  Tracker zzeR(String paramString)
  {
    return GoogleAnalytics.getInstance(this.mContext).newTracker(paramString);
  }
  
  public zzpv zzzR()
  {
    return this.zzoY;
  }
  
  static class zza
    implements zznw.zza
  {
    private final Tracker zzIq;
    
    zza(Tracker paramTracker)
    {
      this.zzIq = paramTracker;
    }
    
    public void zza(zzod paramzzod)
    {
      this.zzIq.setScreenName(paramzzod.zzwB());
      HitBuilders.ScreenViewBuilder localScreenViewBuilder = new HitBuilders.ScreenViewBuilder();
      localScreenViewBuilder.set("&a", String.valueOf(paramzzod.zzbn()));
      this.zzIq.send(localScreenViewBuilder.build());
    }
    
    public void zza(zzod paramzzod, Activity paramActivity) {}
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzpu.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */