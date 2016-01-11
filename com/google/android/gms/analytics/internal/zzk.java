package com.google.android.gms.analytics.internal;

import com.google.android.gms.internal.zzns;
import com.google.android.gms.internal.zznx;

public class zzk
  extends zzd
{
  private final zznx zzKm = new zznx();
  
  zzk(zzf paramzzf)
  {
    super(paramzzf);
  }
  
  public void zzhi()
  {
    Object localObject = zzhm();
    String str = ((zzan)localObject).zzjL();
    if (str != null) {
      this.zzKm.setAppName(str);
    }
    localObject = ((zzan)localObject).zzjN();
    if (localObject != null) {
      this.zzKm.setAppVersion((String)localObject);
    }
  }
  
  protected void zzhn()
  {
    zzhS().zzwc().zza(this.zzKm);
    zzhi();
  }
  
  public zznx zzix()
  {
    zzia();
    return this.zzKm;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/analytics/internal/zzk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */