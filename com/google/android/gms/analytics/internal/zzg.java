package com.google.android.gms.analytics.internal;

import android.content.Context;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.internal.zzlb;
import com.google.android.gms.internal.zzld;
import com.google.android.gms.internal.zzns;

public class zzg
{
  private final Context zzJR;
  private final Context zzqw;
  
  public zzg(Context paramContext)
  {
    com.google.android.gms.common.internal.zzu.zzu(paramContext);
    paramContext = paramContext.getApplicationContext();
    com.google.android.gms.common.internal.zzu.zzb(paramContext, "Application context can't be null");
    this.zzqw = paramContext;
    this.zzJR = paramContext;
  }
  
  public Context getApplicationContext()
  {
    return this.zzqw;
  }
  
  protected zzns zzW(Context paramContext)
  {
    return zzns.zzaB(paramContext);
  }
  
  protected zzu zza(zzf paramzzf)
  {
    return new zzu(paramzzf);
  }
  
  protected zzk zzb(zzf paramzzf)
  {
    return new zzk(paramzzf);
  }
  
  protected zza zzc(zzf paramzzf)
  {
    return new zza(paramzzf);
  }
  
  protected zzn zzd(zzf paramzzf)
  {
    return new zzn(paramzzf);
  }
  
  protected zzan zze(zzf paramzzf)
  {
    return new zzan(paramzzf);
  }
  
  protected zzaf zzf(zzf paramzzf)
  {
    return new zzaf(paramzzf);
  }
  
  protected zzr zzg(zzf paramzzf)
  {
    return new zzr(paramzzf);
  }
  
  protected zzlb zzh(zzf paramzzf)
  {
    return zzld.zzoQ();
  }
  
  protected GoogleAnalytics zzi(zzf paramzzf)
  {
    return new GoogleAnalytics(paramzzf);
  }
  
  public Context zzic()
  {
    return this.zzJR;
  }
  
  zzl zzj(zzf paramzzf)
  {
    return new zzl(paramzzf, this);
  }
  
  zzag zzk(zzf paramzzf)
  {
    return new zzag(paramzzf);
  }
  
  protected zzb zzl(zzf paramzzf)
  {
    return new zzb(paramzzf, this);
  }
  
  public zzj zzm(zzf paramzzf)
  {
    return new zzj(paramzzf);
  }
  
  public zzah zzn(zzf paramzzf)
  {
    return new zzah(paramzzf);
  }
  
  public zzi zzo(zzf paramzzf)
  {
    return new zzi(paramzzf);
  }
  
  public zzv zzp(zzf paramzzf)
  {
    return new zzv(paramzzf);
  }
  
  public zzai zzq(zzf paramzzf)
  {
    return new zzai(paramzzf);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/analytics/internal/zzg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */