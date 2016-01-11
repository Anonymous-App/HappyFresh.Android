package com.google.android.gms.tagmanager;

import android.util.Base64;
import com.google.android.gms.internal.zzae;
import com.google.android.gms.internal.zzag.zza;
import java.util.Map;

class zzad
  extends zzak
{
  private static final String ID = com.google.android.gms.internal.zzad.zzbI.toString();
  private static final String zzaLE = zzae.zzdw.toString();
  private static final String zzaLF = zzae.zzfU.toString();
  private static final String zzaLG = zzae.zzfq.toString();
  private static final String zzaLH = zzae.zzgc.toString();
  
  public zzad()
  {
    super(ID, new String[] { zzaLE });
  }
  
  public zzag.zza zzE(Map<String, zzag.zza> paramMap)
  {
    Object localObject = (zzag.zza)paramMap.get(zzaLE);
    if ((localObject == null) || (localObject == zzdf.zzzQ())) {
      return zzdf.zzzQ();
    }
    String str2 = zzdf.zzg((zzag.zza)localObject);
    localObject = (zzag.zza)paramMap.get(zzaLG);
    String str1;
    if (localObject == null)
    {
      str1 = "text";
      localObject = (zzag.zza)paramMap.get(zzaLH);
      if (localObject != null) {
        break label148;
      }
      localObject = "base16";
      label75:
      paramMap = (zzag.zza)paramMap.get(zzaLF);
      if ((paramMap == null) || (!zzdf.zzk(paramMap).booleanValue())) {
        break label322;
      }
    }
    label148:
    label257:
    label296:
    label322:
    for (int i = 3;; i = 2)
    {
      for (;;)
      {
        try
        {
          if ("text".equals(str1))
          {
            paramMap = str2.getBytes();
            if (!"base16".equals(localObject)) {
              break label257;
            }
            paramMap = zzk.zzi(paramMap);
            return zzdf.zzI(paramMap);
            str1 = zzdf.zzg((zzag.zza)localObject);
            break;
            localObject = zzdf.zzg((zzag.zza)localObject);
            break label75;
          }
          if ("base16".equals(str1))
          {
            paramMap = zzk.zzee(str2);
            continue;
          }
          if ("base64".equals(str1))
          {
            paramMap = Base64.decode(str2, i);
            continue;
          }
          if ("base64url".equals(str1))
          {
            paramMap = Base64.decode(str2, i | 0x8);
            continue;
          }
          zzbg.zzaz("Encode: unknown input format: " + str1);
          paramMap = zzdf.zzzQ();
          return paramMap;
        }
        catch (IllegalArgumentException paramMap)
        {
          zzbg.zzaz("Encode: invalid input:");
          return zzdf.zzzQ();
        }
        if ("base64".equals(localObject))
        {
          paramMap = Base64.encodeToString(paramMap, i);
        }
        else
        {
          if (!"base64url".equals(localObject)) {
            break label296;
          }
          paramMap = Base64.encodeToString(paramMap, i | 0x8);
        }
      }
      zzbg.zzaz("Encode: unknown output format: " + (String)localObject);
      return zzdf.zzzQ();
    }
  }
  
  public boolean zzyh()
  {
    return true;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzad.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */