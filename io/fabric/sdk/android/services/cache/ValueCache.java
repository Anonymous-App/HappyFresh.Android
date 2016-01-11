package io.fabric.sdk.android.services.cache;

import android.content.Context;

public abstract interface ValueCache<T>
{
  public abstract T get(Context paramContext, ValueLoader<T> paramValueLoader)
    throws Exception;
  
  public abstract void invalidate(Context paramContext);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/cache/ValueCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */