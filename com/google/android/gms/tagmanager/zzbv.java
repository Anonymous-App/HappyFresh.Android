package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag.zza;
import java.util.Map;

abstract class zzbv
  extends zzca
{
  public zzbv(String paramString)
  {
    super(paramString);
  }
  
  protected boolean zza(zzag.zza paramzza1, zzag.zza paramzza2, Map<String, zzag.zza> paramMap)
  {
    paramzza1 = zzdf.zzh(paramzza1);
    paramzza2 = zzdf.zzh(paramzza2);
    if ((paramzza1 == zzdf.zzzO()) || (paramzza2 == zzdf.zzzO())) {
      return false;
    }
    return zza(paramzza1, paramzza2, paramMap);
  }
  
  protected abstract boolean zza(zzde paramzzde1, zzde paramzzde2, Map<String, zzag.zza> paramMap);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzbv.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */