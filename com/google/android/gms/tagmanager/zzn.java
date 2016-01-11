package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzae;
import com.google.android.gms.internal.zzag.zza;
import java.util.Map;

class zzn
  extends zzak
{
  private static final String ID = zzad.zzbi.toString();
  private static final String VALUE = zzae.zzhC.toString();
  
  public zzn()
  {
    super(ID, new String[] { VALUE });
  }
  
  public static String zzyk()
  {
    return ID;
  }
  
  public static String zzyl()
  {
    return VALUE;
  }
  
  public zzag.zza zzE(Map<String, zzag.zza> paramMap)
  {
    return (zzag.zza)paramMap.get(VALUE);
  }
  
  public boolean zzyh()
  {
    return true;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */