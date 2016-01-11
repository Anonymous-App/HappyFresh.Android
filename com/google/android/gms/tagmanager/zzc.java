package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzag.zza;
import java.util.Map;

class zzc
  extends zzak
{
  private static final String ID = zzad.zzbd.toString();
  private final zza zzaKo;
  
  public zzc(Context paramContext)
  {
    this(zza.zzaE(paramContext));
  }
  
  zzc(zza paramzza)
  {
    super(ID, new String[0]);
    this.zzaKo = paramzza;
  }
  
  public zzag.zza zzE(Map<String, zzag.zza> paramMap)
  {
    if (!this.zzaKo.isLimitAdTrackingEnabled()) {}
    for (boolean bool = true;; bool = false) {
      return zzdf.zzI(Boolean.valueOf(bool));
    }
  }
  
  public boolean zzyh()
  {
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */