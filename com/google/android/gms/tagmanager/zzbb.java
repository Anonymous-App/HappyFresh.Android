package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzag.zza;
import java.util.Locale;
import java.util.Map;

public class zzbb
  extends zzak
{
  private static final String ID = zzad.zzbv.toString();
  
  public zzbb()
  {
    super(ID, new String[0]);
  }
  
  public zzag.zza zzE(Map<String, zzag.zza> paramMap)
  {
    paramMap = Locale.getDefault();
    if (paramMap == null) {
      return zzdf.zzzQ();
    }
    paramMap = paramMap.getLanguage();
    if (paramMap == null) {
      return zzdf.zzzQ();
    }
    return zzdf.zzI(paramMap.toLowerCase());
  }
  
  public boolean zzyh()
  {
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzbb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */