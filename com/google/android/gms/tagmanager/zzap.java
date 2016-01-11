package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzae;
import com.google.android.gms.internal.zzag.zza;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

class zzap
  extends zzak
{
  private static final String ID = zzad.zzbK.toString();
  private static final String zzaLE = zzae.zzdw.toString();
  private static final String zzaLG = zzae.zzfq.toString();
  private static final String zzaLK = zzae.zzdn.toString();
  
  public zzap()
  {
    super(ID, new String[] { zzaLE });
  }
  
  private byte[] zzd(String paramString, byte[] paramArrayOfByte)
    throws NoSuchAlgorithmException
  {
    paramString = MessageDigest.getInstance(paramString);
    paramString.update(paramArrayOfByte);
    return paramString.digest();
  }
  
  public zzag.zza zzE(Map<String, zzag.zza> paramMap)
  {
    Object localObject = (zzag.zza)paramMap.get(zzaLE);
    if ((localObject == null) || (localObject == zzdf.zzzQ())) {
      return zzdf.zzzQ();
    }
    String str = zzdf.zzg((zzag.zza)localObject);
    localObject = (zzag.zza)paramMap.get(zzaLK);
    if (localObject == null)
    {
      localObject = "MD5";
      paramMap = (zzag.zza)paramMap.get(zzaLG);
      if (paramMap != null) {
        break label110;
      }
      paramMap = "text";
      label73:
      if (!"text".equals(paramMap)) {
        break label118;
      }
      paramMap = str.getBytes();
    }
    for (;;)
    {
      try
      {
        paramMap = zzdf.zzI(zzk.zzi(zzd((String)localObject, paramMap)));
        return paramMap;
      }
      catch (NoSuchAlgorithmException paramMap)
      {
        label110:
        label118:
        zzbg.zzaz("Hash: unknown algorithm: " + (String)localObject);
      }
      localObject = zzdf.zzg((zzag.zza)localObject);
      break;
      paramMap = zzdf.zzg(paramMap);
      break label73;
      if ("base16".equals(paramMap))
      {
        paramMap = zzk.zzee(str);
      }
      else
      {
        zzbg.zzaz("Hash: unknown input format: " + paramMap);
        return zzdf.zzzQ();
      }
    }
    return zzdf.zzzQ();
  }
  
  public boolean zzyh()
  {
    return true;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */