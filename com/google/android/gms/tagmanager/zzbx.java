package com.google.android.gms.tagmanager;

import android.os.Build.VERSION;
import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzag.zza;
import java.util.Map;

class zzbx
  extends zzak
{
  private static final String ID = zzad.zzbw.toString();
  
  public zzbx()
  {
    super(ID, new String[0]);
  }
  
  public zzag.zza zzE(Map<String, zzag.zza> paramMap)
  {
    return zzdf.zzI(Build.VERSION.RELEASE);
  }
  
  public boolean zzyh()
  {
    return true;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzbx.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */