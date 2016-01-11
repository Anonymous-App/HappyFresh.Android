package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzag.zza;
import java.util.Map;

class zzq
  extends zzak
{
  private static final String ID = zzad.zzbl.toString();
  private final String zzTQ;
  
  public zzq(String paramString)
  {
    super(ID, new String[0]);
    this.zzTQ = paramString;
  }
  
  public zzag.zza zzE(Map<String, zzag.zza> paramMap)
  {
    if (this.zzTQ == null) {
      return zzdf.zzzQ();
    }
    return zzdf.zzI(this.zzTQ);
  }
  
  public boolean zzyh()
  {
    return true;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzq.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */