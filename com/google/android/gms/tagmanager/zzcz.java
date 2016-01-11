package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag.zza;
import java.util.Map;

abstract class zzcz
  extends zzca
{
  public zzcz(String paramString)
  {
    super(paramString);
  }
  
  protected boolean zza(zzag.zza paramzza1, zzag.zza paramzza2, Map<String, zzag.zza> paramMap)
  {
    paramzza1 = zzdf.zzg(paramzza1);
    paramzza2 = zzdf.zzg(paramzza2);
    if ((paramzza1 == zzdf.zzzP()) || (paramzza2 == zzdf.zzzP())) {
      return false;
    }
    return zza(paramzza1, paramzza2, paramMap);
  }
  
  protected abstract boolean zza(String paramString1, String paramString2, Map<String, zzag.zza> paramMap);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzcz.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */