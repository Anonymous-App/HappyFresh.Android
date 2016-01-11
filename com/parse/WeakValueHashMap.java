package com.parse;

import java.lang.ref.WeakReference;
import java.util.HashMap;

class WeakValueHashMap<K, V>
{
  private HashMap<K, WeakReference<V>> map = new HashMap();
  
  public void clear()
  {
    this.map.clear();
  }
  
  public V get(K paramK)
  {
    Object localObject1 = (WeakReference)this.map.get(paramK);
    if (localObject1 == null) {
      localObject1 = null;
    }
    Object localObject2;
    do
    {
      return (V)localObject1;
      localObject2 = ((WeakReference)localObject1).get();
      localObject1 = localObject2;
    } while (localObject2 != null);
    this.map.remove(paramK);
    return (V)localObject2;
  }
  
  public void put(K paramK, V paramV)
  {
    this.map.put(paramK, new WeakReference(paramV));
  }
  
  public void remove(K paramK)
  {
    this.map.remove(paramK);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/WeakValueHashMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */