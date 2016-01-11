package com.google.android.gms.internal;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class zzks<K, V>
  extends zzkw<K, V>
  implements Map<K, V>
{
  zzkv<K, V> zzabl;
  
  private zzkv<K, V> zzog()
  {
    if (this.zzabl == null) {
      this.zzabl = new zzkv()
      {
        protected void colClear()
        {
          zzks.this.clear();
        }
        
        protected Object colGetEntry(int paramAnonymousInt1, int paramAnonymousInt2)
        {
          return zzks.this.mArray[((paramAnonymousInt1 << 1) + paramAnonymousInt2)];
        }
        
        protected Map<K, V> colGetMap()
        {
          return zzks.this;
        }
        
        protected int colGetSize()
        {
          return zzks.this.mSize;
        }
        
        protected int colIndexOfKey(Object paramAnonymousObject)
        {
          if (paramAnonymousObject == null) {
            return zzks.this.indexOfNull();
          }
          return zzks.this.indexOf(paramAnonymousObject, paramAnonymousObject.hashCode());
        }
        
        protected int colIndexOfValue(Object paramAnonymousObject)
        {
          return zzks.this.indexOfValue(paramAnonymousObject);
        }
        
        protected void colPut(K paramAnonymousK, V paramAnonymousV)
        {
          zzks.this.put(paramAnonymousK, paramAnonymousV);
        }
        
        protected void colRemoveAt(int paramAnonymousInt)
        {
          zzks.this.removeAt(paramAnonymousInt);
        }
        
        protected V colSetValue(int paramAnonymousInt, V paramAnonymousV)
        {
          return (V)zzks.this.setValueAt(paramAnonymousInt, paramAnonymousV);
        }
      };
    }
    return this.zzabl;
  }
  
  public Set<Map.Entry<K, V>> entrySet()
  {
    return zzog().getEntrySet();
  }
  
  public Set<K> keySet()
  {
    return zzog().getKeySet();
  }
  
  public void putAll(Map<? extends K, ? extends V> paramMap)
  {
    ensureCapacity(this.mSize + paramMap.size());
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)paramMap.next();
      put(localEntry.getKey(), localEntry.getValue());
    }
  }
  
  public Collection<V> values()
  {
    return zzog().getValues();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzks.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */