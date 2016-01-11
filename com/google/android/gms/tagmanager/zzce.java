package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzae;
import com.google.android.gms.internal.zzag.zza;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

class zzce
  extends zzak
{
  private static final String ID = zzad.zzbO.toString();
  private static final String zzaMO = zzae.zzdw.toString();
  private static final String zzaMP = zzae.zzdx.toString();
  private static final String zzaMQ = zzae.zzfo.toString();
  private static final String zzaMR = zzae.zzfi.toString();
  
  public zzce()
  {
    super(ID, new String[] { zzaMO, zzaMP });
  }
  
  public zzag.zza zzE(Map<String, zzag.zza> paramMap)
  {
    Object localObject = (zzag.zza)paramMap.get(zzaMO);
    zzag.zza localzza = (zzag.zza)paramMap.get(zzaMP);
    if ((localObject == null) || (localObject == zzdf.zzzQ()) || (localzza == null) || (localzza == zzdf.zzzQ())) {
      return zzdf.zzzQ();
    }
    int i = 64;
    if (zzdf.zzk((zzag.zza)paramMap.get(zzaMQ)).booleanValue()) {
      i = 66;
    }
    paramMap = (zzag.zza)paramMap.get(zzaMR);
    int j;
    if (paramMap != null)
    {
      paramMap = zzdf.zzi(paramMap);
      if (paramMap == zzdf.zzzL()) {
        return zzdf.zzzQ();
      }
      int k = paramMap.intValue();
      j = k;
      if (k < 0) {
        return zzdf.zzzQ();
      }
    }
    else
    {
      j = 1;
    }
    try
    {
      paramMap = zzdf.zzg((zzag.zza)localObject);
      localObject = zzdf.zzg(localzza);
      localzza = null;
      localObject = Pattern.compile((String)localObject, i).matcher(paramMap);
      paramMap = localzza;
      if (((Matcher)localObject).find())
      {
        paramMap = localzza;
        if (((Matcher)localObject).groupCount() >= j) {
          paramMap = ((Matcher)localObject).group(j);
        }
      }
      if (paramMap == null) {
        return zzdf.zzzQ();
      }
      paramMap = zzdf.zzI(paramMap);
      return paramMap;
    }
    catch (PatternSyntaxException paramMap) {}
    return zzdf.zzzQ();
  }
  
  public boolean zzyh()
  {
    return true;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzce.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */