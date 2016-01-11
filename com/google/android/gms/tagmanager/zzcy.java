package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzag.zza;
import java.util.Map;

class zzcy
  extends zzcz
{
  private static final String ID = zzad.zzcc.toString();
  
  public zzcy()
  {
    super(ID);
  }
  
  protected boolean zza(String paramString1, String paramString2, Map<String, zzag.zza> paramMap)
  {
    return paramString1.startsWith(paramString2);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzcy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */