package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzae;
import com.google.android.gms.internal.zzag.zza;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class zzx
  extends zzdd
{
  private static final String ID = zzad.zzbP.toString();
  private static final String VALUE = zzae.zzhC.toString();
  private static final String zzaLz = zzae.zzdT.toString();
  private final DataLayer zzaKz;
  
  public zzx(DataLayer paramDataLayer)
  {
    super(ID, new String[] { VALUE });
    this.zzaKz = paramDataLayer;
  }
  
  private void zza(zzag.zza paramzza)
  {
    if ((paramzza == null) || (paramzza == zzdf.zzzK())) {}
    do
    {
      return;
      paramzza = zzdf.zzg(paramzza);
    } while (paramzza == zzdf.zzzP());
    this.zzaKz.zzen(paramzza);
  }
  
  private void zzb(zzag.zza paramzza)
  {
    if ((paramzza == null) || (paramzza == zzdf.zzzK())) {}
    for (;;)
    {
      return;
      paramzza = zzdf.zzl(paramzza);
      if ((paramzza instanceof List))
      {
        paramzza = ((List)paramzza).iterator();
        while (paramzza.hasNext())
        {
          Object localObject = paramzza.next();
          if ((localObject instanceof Map))
          {
            localObject = (Map)localObject;
            this.zzaKz.push((Map)localObject);
          }
        }
      }
    }
  }
  
  public void zzG(Map<String, zzag.zza> paramMap)
  {
    zzb((zzag.zza)paramMap.get(VALUE));
    zza((zzag.zza)paramMap.get(zzaLz));
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzx.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */