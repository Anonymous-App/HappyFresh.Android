package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzae;
import com.google.android.gms.internal.zzag.zza;
import java.util.Map;

class zzv
  extends zzak
{
  private static final String ID = zzad.zzbk.toString();
  private static final String NAME = zzae.zzfO.toString();
  private static final String zzaLo = zzae.zzez.toString();
  private final DataLayer zzaKz;
  
  public zzv(DataLayer paramDataLayer)
  {
    super(ID, new String[] { NAME });
    this.zzaKz = paramDataLayer;
  }
  
  public zzag.zza zzE(Map<String, zzag.zza> paramMap)
  {
    Object localObject = this.zzaKz.get(zzdf.zzg((zzag.zza)paramMap.get(NAME)));
    if (localObject == null)
    {
      paramMap = (zzag.zza)paramMap.get(zzaLo);
      if (paramMap != null) {
        return paramMap;
      }
      return zzdf.zzzQ();
    }
    return zzdf.zzI(localObject);
  }
  
  public boolean zzyh()
  {
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzv.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */