package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzag.zza;
import java.util.Map;

class zzai
  extends zzak
{
  private static final String ID = zzad.zzbs.toString();
  private final zzcp zzaKA;
  
  public zzai(zzcp paramzzcp)
  {
    super(ID, new String[0]);
    this.zzaKA = paramzzcp;
  }
  
  public zzag.zza zzE(Map<String, zzag.zza> paramMap)
  {
    paramMap = this.zzaKA.zzzp();
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


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzai.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */