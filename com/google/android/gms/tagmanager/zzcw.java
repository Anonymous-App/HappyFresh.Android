package com.google.android.gms.tagmanager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class zzcw<K, V>
  implements zzl<K, V>
{
  private final Map<K, V> zzaNR = new HashMap();
  private final int zzaNS;
  private final zzm.zza<K, V> zzaNT;
  private int zzaNU;
  
  zzcw(int paramInt, zzm.zza<K, V> paramzza)
  {
    this.zzaNS = paramInt;
    this.zzaNT = paramzza;
  }
  
  public V get(K paramK)
  {
    try
    {
      paramK = this.zzaNR.get(paramK);
      return paramK;
    }
    finally
    {
      paramK = finally;
      throw paramK;
    }
  }
  
  public void zzf(K paramK, V paramV)
  {
    if ((paramK == null) || (paramV == null)) {
      try
      {
        throw new NullPointerException("key == null || value == null");
      }
      finally {}
    }
    this.zzaNU += this.zzaNT.sizeOf(paramK, paramV);
    if (this.zzaNU > this.zzaNS)
    {
      Iterator localIterator = this.zzaNR.entrySet().iterator();
      do
      {
        if (!localIterator.hasNext()) {
          break;
        }
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        this.zzaNU -= this.zzaNT.sizeOf(localEntry.getKey(), localEntry.getValue());
        localIterator.remove();
      } while (this.zzaNU > this.zzaNS);
    }
    this.zzaNR.put(paramK, paramV);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzcw.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */