package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzae;
import com.google.android.gms.internal.zzag.zza;
import java.util.Map;

class zzcc
  extends zzak
{
  private static final String ID = zzad.zzby.toString();
  private static final String zzaMM = zzae.zzfM.toString();
  private static final String zzaMN = zzae.zzfK.toString();
  
  public zzcc()
  {
    super(ID, new String[0]);
  }
  
  public zzag.zza zzE(Map<String, zzag.zza> paramMap)
  {
    Object localObject = (zzag.zza)paramMap.get(zzaMM);
    paramMap = (zzag.zza)paramMap.get(zzaMN);
    double d2;
    double d1;
    if ((localObject != null) && (localObject != zzdf.zzzQ()) && (paramMap != null) && (paramMap != zzdf.zzzQ()))
    {
      localObject = zzdf.zzh((zzag.zza)localObject);
      paramMap = zzdf.zzh(paramMap);
      if ((localObject != zzdf.zzzO()) && (paramMap != zzdf.zzzO()))
      {
        d2 = ((zzde)localObject).doubleValue();
        d1 = paramMap.doubleValue();
        if (d2 > d1) {}
      }
    }
    for (;;)
    {
      return zzdf.zzI(Long.valueOf(Math.round((d1 - d2) * Math.random() + d2)));
      d1 = 2.147483647E9D;
      d2 = 0.0D;
    }
  }
  
  public boolean zzyh()
  {
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzcc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */