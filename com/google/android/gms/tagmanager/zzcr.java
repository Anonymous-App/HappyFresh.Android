package com.google.android.gms.tagmanager;

import android.os.Build.VERSION;
import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzag.zza;
import java.util.Map;

class zzcr
  extends zzak
{
  private static final String ID = zzad.zzbC.toString();
  
  public zzcr()
  {
    super(ID, new String[0]);
  }
  
  public zzag.zza zzE(Map<String, zzag.zza> paramMap)
  {
    return zzdf.zzI(Integer.valueOf(Build.VERSION.SDK_INT));
  }
  
  public boolean zzyh()
  {
    return true;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzcr.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */