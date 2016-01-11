package io.fabric.sdk.android.services.cache;

import android.content.Context;

public class MemoryValueCache<T>
  extends AbstractValueCache<T>
{
  private T value;
  
  public MemoryValueCache()
  {
    this(null);
  }
  
  public MemoryValueCache(ValueCache<T> paramValueCache)
  {
    super(paramValueCache);
  }
  
  protected void cacheValue(Context paramContext, T paramT)
  {
    this.value = paramT;
  }
  
  protected void doInvalidate(Context paramContext)
  {
    this.value = null;
  }
  
  protected T getCached(Context paramContext)
  {
    return (T)this.value;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/cache/MemoryValueCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */