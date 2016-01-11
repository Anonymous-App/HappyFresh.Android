package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzag.zza;
import java.util.Map;

class zzb
  extends zzak
{
  private static final String ID = zzad.zzbc.toString();
  private final zza zzaKo;
  
  public zzb(Context paramContext)
  {
    this(zza.zzaE(paramContext));
  }
  
  zzb(zza paramzza)
  {
    super(ID, new String[0]);
    this.zzaKo = paramzza;
  }
  
  public zzag.zza zzE(Map<String, zzag.zza> paramMap)
  {
    paramMap = this.zzaKo.zzyd();
    if (paramMap == null) {
      return zzdf.zzzQ();
    }
    return zzdf.zzI(paramMap);
  }
  
  public boolean zzyh()
  {
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */