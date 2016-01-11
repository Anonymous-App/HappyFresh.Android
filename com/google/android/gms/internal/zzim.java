package com.google.android.gms.internal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class zzim
  extends zznq<zzim>
{
  private Map<Integer, String> zzJa = new HashMap(4);
  
  public String toString()
  {
    HashMap localHashMap = new HashMap();
    Iterator localIterator = this.zzJa.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      localHashMap.put("dimension" + localEntry.getKey(), localEntry.getValue());
    }
    return zzy(localHashMap);
  }
  
  public void zza(zzim paramzzim)
  {
    paramzzim.zzJa.putAll(this.zzJa);
  }
  
  public Map<Integer, String> zzht()
  {
    return Collections.unmodifiableMap(this.zzJa);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzim.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */