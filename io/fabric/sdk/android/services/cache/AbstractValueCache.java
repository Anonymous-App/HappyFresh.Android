package io.fabric.sdk.android.services.cache;

import android.content.Context;

public abstract class AbstractValueCache<T>
  implements ValueCache<T>
{
  private final ValueCache<T> childCache;
  
  public AbstractValueCache()
  {
    this(null);
  }
  
  public AbstractValueCache(ValueCache<T> paramValueCache)
  {
    this.childCache = paramValueCache;
  }
  
  private void cache(Context paramContext, T paramT)
  {
    if (paramT == null) {
      throw new NullPointerException();
    }
    cacheValue(paramContext, paramT);
  }
  
  protected abstract void cacheValue(Context paramContext, T paramT);
  
  protected abstract void doInvalidate(Context paramContext);
  
  /* Error */
  public final T get(Context paramContext, ValueLoader<T> paramValueLoader)
    throws java.lang.Exception
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: aload_1
    //   4: invokevirtual 41	io/fabric/sdk/android/services/cache/AbstractValueCache:getCached	(Landroid/content/Context;)Ljava/lang/Object;
    //   7: astore 4
    //   9: aload 4
    //   11: astore_3
    //   12: aload 4
    //   14: ifnonnull +30 -> 44
    //   17: aload_0
    //   18: getfield 20	io/fabric/sdk/android/services/cache/AbstractValueCache:childCache	Lio/fabric/sdk/android/services/cache/ValueCache;
    //   21: ifnull +27 -> 48
    //   24: aload_0
    //   25: getfield 20	io/fabric/sdk/android/services/cache/AbstractValueCache:childCache	Lio/fabric/sdk/android/services/cache/ValueCache;
    //   28: aload_1
    //   29: aload_2
    //   30: invokeinterface 43 3 0
    //   35: astore_2
    //   36: aload_0
    //   37: aload_1
    //   38: aload_2
    //   39: invokespecial 45	io/fabric/sdk/android/services/cache/AbstractValueCache:cache	(Landroid/content/Context;Ljava/lang/Object;)V
    //   42: aload_2
    //   43: astore_3
    //   44: aload_0
    //   45: monitorexit
    //   46: aload_3
    //   47: areturn
    //   48: aload_2
    //   49: aload_1
    //   50: invokeinterface 50 2 0
    //   55: astore_2
    //   56: goto -20 -> 36
    //   59: astore_1
    //   60: aload_0
    //   61: monitorexit
    //   62: aload_1
    //   63: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	64	0	this	AbstractValueCache
    //   0	64	1	paramContext	Context
    //   0	64	2	paramValueLoader	ValueLoader<T>
    //   11	36	3	localObject1	Object
    //   7	6	4	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   2	9	59	finally
    //   17	36	59	finally
    //   36	42	59	finally
    //   48	56	59	finally
  }
  
  protected abstract T getCached(Context paramContext);
  
  public final void invalidate(Context paramContext)
  {
    try
    {
      doInvalidate(paramContext);
      return;
    }
    finally
    {
      paramContext = finally;
      throw paramContext;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/cache/AbstractValueCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */