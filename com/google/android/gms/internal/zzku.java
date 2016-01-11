package com.google.android.gms.internal;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

public class zzku<K, V>
{
  private int size;
  private final LinkedHashMap<K, V> zzabn;
  private int zzabo;
  private int zzabp;
  private int zzabq;
  private int zzabr;
  private int zzabs;
  private int zzabt;
  
  public zzku(int paramInt)
  {
    if (paramInt <= 0) {
      throw new IllegalArgumentException("maxSize <= 0");
    }
    this.zzabo = paramInt;
    this.zzabn = new LinkedHashMap(0, 0.75F, true);
  }
  
  private int zzc(K paramK, V paramV)
  {
    int i = sizeOf(paramK, paramV);
    if (i < 0) {
      throw new IllegalStateException("Negative size: " + paramK + "=" + paramV);
    }
    return i;
  }
  
  protected V create(K paramK)
  {
    return null;
  }
  
  protected void entryRemoved(boolean paramBoolean, K paramK, V paramV1, V paramV2) {}
  
  public final void evictAll()
  {
    trimToSize(-1);
  }
  
  public final V get(K paramK)
  {
    if (paramK == null) {
      throw new NullPointerException("key == null");
    }
    Object localObject1;
    try
    {
      localObject1 = this.zzabn.get(paramK);
      if (localObject1 != null)
      {
        this.zzabs += 1;
        return (V)localObject1;
      }
      this.zzabt += 1;
      localObject1 = create(paramK);
      if (localObject1 == null) {
        return null;
      }
    }
    finally {}
    try
    {
      this.zzabq += 1;
      Object localObject2 = this.zzabn.put(paramK, localObject1);
      if (localObject2 != null) {
        this.zzabn.put(paramK, localObject2);
      }
      for (;;)
      {
        if (localObject2 == null) {
          break;
        }
        entryRemoved(false, paramK, localObject1, localObject2);
        return (V)localObject2;
        this.size += zzc(paramK, localObject1);
      }
      trimToSize(this.zzabo);
    }
    finally {}
    return (V)localObject1;
  }
  
  public final V put(K paramK, V paramV)
  {
    if ((paramK == null) || (paramV == null)) {
      throw new NullPointerException("key == null || value == null");
    }
    try
    {
      this.zzabp += 1;
      this.size += zzc(paramK, paramV);
      Object localObject = this.zzabn.put(paramK, paramV);
      if (localObject != null) {
        this.size -= zzc(paramK, localObject);
      }
      if (localObject != null) {
        entryRemoved(false, paramK, localObject, paramV);
      }
      trimToSize(this.zzabo);
      return (V)localObject;
    }
    finally {}
  }
  
  public final int size()
  {
    try
    {
      int i = this.size;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  protected int sizeOf(K paramK, V paramV)
  {
    return 1;
  }
  
  public final String toString()
  {
    int i = 0;
    try
    {
      int j = this.zzabs + this.zzabt;
      if (j != 0) {
        i = this.zzabs * 100 / j;
      }
      String str = String.format("LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]", new Object[] { Integer.valueOf(this.zzabo), Integer.valueOf(this.zzabs), Integer.valueOf(this.zzabt), Integer.valueOf(i) });
      return str;
    }
    finally {}
  }
  
  public void trimToSize(int paramInt)
  {
    Object localObject3;
    Object localObject2;
    try
    {
      if ((this.size < 0) || ((this.zzabn.isEmpty()) && (this.size != 0))) {
        throw new IllegalStateException(getClass().getName() + ".sizeOf() is reporting inconsistent results!");
      }
    }
    finally
    {
      throw ((Throwable)localObject1);
      if ((this.size <= paramInt) || (this.zzabn.isEmpty())) {
        return;
      }
      localObject3 = (Map.Entry)this.zzabn.entrySet().iterator().next();
      localObject2 = ((Map.Entry)localObject3).getKey();
      localObject3 = ((Map.Entry)localObject3).getValue();
      this.zzabn.remove(localObject2);
      this.size -= zzc(localObject2, localObject3);
      this.zzabr += 1;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzku.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */