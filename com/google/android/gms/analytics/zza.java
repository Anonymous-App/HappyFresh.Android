package com.google.android.gms.analytics;

import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.analytics.internal.zzf;
import com.google.android.gms.analytics.internal.zzk;
import com.google.android.gms.analytics.internal.zzn;
import com.google.android.gms.internal.zzip;
import com.google.android.gms.internal.zzno;
import com.google.android.gms.internal.zznr;
import com.google.android.gms.internal.zznu;
import java.util.List;
import java.util.ListIterator;

public class zza
  extends zznr<zza>
{
  private final zzf zzIa;
  private boolean zzIb;
  
  public zza(zzf paramzzf)
  {
    super(paramzzf.zzhS(), paramzzf.zzhP());
    this.zzIa = paramzzf;
  }
  
  public void enableAdvertisingIdCollection(boolean paramBoolean)
  {
    this.zzIb = paramBoolean;
  }
  
  protected void zza(zzno paramzzno)
  {
    paramzzno = (zzip)paramzzno.zze(zzip.class);
    if (TextUtils.isEmpty(paramzzno.getClientId())) {
      paramzzno.setClientId(this.zzIa.zzih().zziP());
    }
    if ((this.zzIb) && (TextUtils.isEmpty(paramzzno.zzhx())))
    {
      com.google.android.gms.analytics.internal.zza localzza = this.zzIa.zzig();
      paramzzno.zzaO(localzza.zzhC());
      paramzzno.zzE(localzza.zzhy());
    }
  }
  
  public void zzaI(String paramString)
  {
    com.google.android.gms.common.internal.zzu.zzcj(paramString);
    zzaJ(paramString);
    zzwb().add(new zzb(this.zzIa, paramString));
  }
  
  public void zzaJ(String paramString)
  {
    paramString = zzb.zzaK(paramString);
    ListIterator localListIterator = zzwb().listIterator();
    while (localListIterator.hasNext()) {
      if (paramString.equals(((zznu)localListIterator.next()).zzhe())) {
        localListIterator.remove();
      }
    }
  }
  
  zzf zzhb()
  {
    return this.zzIa;
  }
  
  public zzno zzhc()
  {
    zzno localzzno = zzwa().zzvP();
    localzzno.zzb(this.zzIa.zzhX().zzix());
    localzzno.zzb(this.zzIa.zzhY().zzjE());
    zzd(localzzno);
    return localzzno;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/analytics/zza.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */