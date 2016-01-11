package io.fabric.sdk.android.services.cache;

import android.content.Context;

public abstract interface ValueLoader<T>
{
  public abstract T load(Context paramContext)
    throws Exception;
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/cache/ValueLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */