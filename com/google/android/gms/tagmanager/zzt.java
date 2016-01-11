package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzae;
import com.google.android.gms.internal.zzag.zza;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class zzt
  extends zzak
{
  private static final String ID = zzad.zzbt.toString();
  private static final String zzaKr = zzae.zzdk.toString();
  private static final String zzaLd = zzae.zzfh.toString();
  private final zza zzaLe;
  
  public zzt(zza paramzza)
  {
    super(ID, new String[] { zzaLd });
    this.zzaLe = paramzza;
  }
  
  public zzag.zza zzE(Map<String, zzag.zza> paramMap)
  {
    String str = zzdf.zzg((zzag.zza)paramMap.get(zzaLd));
    HashMap localHashMap = new HashMap();
    paramMap = (zzag.zza)paramMap.get(zzaKr);
    if (paramMap != null)
    {
      paramMap = zzdf.zzl(paramMap);
      if (!(paramMap instanceof Map))
      {
        zzbg.zzaC("FunctionCallMacro: expected ADDITIONAL_PARAMS to be a map.");
        return zzdf.zzzQ();
      }
      paramMap = ((Map)paramMap).entrySet().iterator();
      while (paramMap.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)paramMap.next();
        localHashMap.put(localEntry.getKey().toString(), localEntry.getValue());
      }
    }
    try
    {
      paramMap = zzdf.zzI(this.zzaLe.zzd(str, localHashMap));
      return paramMap;
    }
    catch (Exception paramMap)
    {
      zzbg.zzaC("Custom macro/tag " + str + " threw exception " + paramMap.getMessage());
    }
    return zzdf.zzzQ();
  }
  
  public boolean zzyh()
  {
    return false;
  }
  
  public static abstract interface zza
  {
    public abstract Object zzd(String paramString, Map<String, Object> paramMap);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */