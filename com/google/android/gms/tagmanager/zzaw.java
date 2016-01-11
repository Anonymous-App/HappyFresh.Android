package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzae;
import com.google.android.gms.internal.zzag.zza;
import java.util.Map;

class zzaw
  extends zzak
{
  private static final String ID = zzad.zzbL.toString();
  private static final String zzaKp = zzae.zzdY.toString();
  private final Context zzpH;
  
  public zzaw(Context paramContext)
  {
    super(ID, new String[0]);
    this.zzpH = paramContext;
  }
  
  public zzag.zza zzE(Map<String, zzag.zza> paramMap)
  {
    if ((zzag.zza)paramMap.get(zzaKp) != null) {}
    for (paramMap = zzdf.zzg((zzag.zza)paramMap.get(zzaKp));; paramMap = null)
    {
      paramMap = zzax.zzj(this.zzpH, paramMap);
      if (paramMap == null) {
        break;
      }
      return zzdf.zzI(paramMap);
    }
    return zzdf.zzzQ();
  }
  
  public boolean zzyh()
  {
    return true;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzaw.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */