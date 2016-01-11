package com.google.android.gms.internal;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.zzu;
import java.util.ArrayList;
import java.util.List;

public class zznw
{
  private static final zza[] zzaEq = new zza[0];
  private static zznw zzaEr;
  private final Application zzaEs;
  private zzod zzaEt;
  private final List<zza> zzaEu;
  private zzog zzaEv;
  
  private zznw(Application paramApplication)
  {
    zzu.zzu(paramApplication);
    this.zzaEs = paramApplication;
    this.zzaEu = new ArrayList();
  }
  
  public static zznw zzaC(Context paramContext)
  {
    zzu.zzu(paramContext);
    paramContext = (Application)paramContext.getApplicationContext();
    zzu.zzu(paramContext);
    try
    {
      if (zzaEr == null) {
        zzaEr = new zznw(paramContext);
      }
      paramContext = zzaEr;
      return paramContext;
    }
    finally {}
  }
  
  private zza[] zzwh()
  {
    synchronized (this.zzaEu)
    {
      if (this.zzaEu.isEmpty())
      {
        arrayOfzza = zzaEq;
        return arrayOfzza;
      }
      zza[] arrayOfzza = (zza[])this.zzaEu.toArray(new zza[this.zzaEu.size()]);
      return arrayOfzza;
    }
  }
  
  public void zza(zza paramzza)
  {
    zzu.zzu(paramzza);
    synchronized (this.zzaEu)
    {
      this.zzaEu.remove(paramzza);
      this.zzaEu.add(paramzza);
      return;
    }
  }
  
  public void zzaf(boolean paramBoolean)
  {
    if (Build.VERSION.SDK_INT < 14) {
      Log.i("com.google.android.gms.measurement.ScreenViewService", "AutoScreeViewTracking is not supported on API 14 or earlier devices");
    }
    while (zzwg() == paramBoolean) {
      return;
    }
    if (paramBoolean)
    {
      this.zzaEv = new zzog(this);
      this.zzaEs.registerActivityLifecycleCallbacks(this.zzaEv);
      return;
    }
    this.zzaEs.unregisterActivityLifecycleCallbacks(this.zzaEv);
    this.zzaEv = null;
  }
  
  public void zzb(zzod paramzzod, Activity paramActivity)
  {
    int j = 0;
    zzu.zzu(paramzzod);
    zza[] arrayOfzza = null;
    int i;
    if (paramzzod.isMutable())
    {
      if ((paramActivity instanceof zznv)) {
        ((zznv)paramActivity).zzb(paramzzod);
      }
      if (this.zzaEt != null)
      {
        paramzzod.zzhL(this.zzaEt.zzbn());
        paramzzod.zzdJ(this.zzaEt.zzwB());
      }
      arrayOfzza = zzwh();
      i = 0;
      while (i < arrayOfzza.length)
      {
        arrayOfzza[i].zza(paramzzod, paramActivity);
        i += 1;
      }
      paramzzod.zzwF();
      if (!TextUtils.isEmpty(paramzzod.zzwB())) {}
    }
    for (;;)
    {
      return;
      if ((this.zzaEt != null) && (this.zzaEt.zzbn() == paramzzod.zzbn()))
      {
        this.zzaEt = paramzzod;
        return;
      }
      zzwf();
      this.zzaEt = paramzzod;
      paramActivity = arrayOfzza;
      i = j;
      if (arrayOfzza == null)
      {
        paramActivity = zzwh();
        i = j;
      }
      while (i < paramActivity.length)
      {
        paramActivity[i].zza(paramzzod);
        i += 1;
      }
    }
  }
  
  public zzod zzwe()
  {
    return this.zzaEt;
  }
  
  public void zzwf()
  {
    this.zzaEt = null;
  }
  
  public boolean zzwg()
  {
    return this.zzaEv != null;
  }
  
  public static abstract interface zza
  {
    public abstract void zza(zzod paramzzod);
    
    public abstract void zza(zzod paramzzod, Activity paramActivity);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zznw.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */