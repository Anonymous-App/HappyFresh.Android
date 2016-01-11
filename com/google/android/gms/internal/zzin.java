package com.google.android.gms.internal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class zzin
  extends zznq<zzin>
{
  private Map<Integer, Double> zzJb = new HashMap(4);
  
  public String toString()
  {
    HashMap localHashMap = new HashMap();
    Iterator localIterator = this.zzJb.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      localHashMap.put("metric" + localEntry.getKey(), localEntry.getValue());
    }
    return zzy(localHashMap);
  }
  
  public void zza(zzin paramzzin)
  {
    paramzzin.zzJb.putAll(this.zzJb);
  }
  
  public Map<Integer, Double> zzhu()
  {
    return Collections.unmodifiableMap(this.zzJb);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */