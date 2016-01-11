package com.mixpanel.android.mpmetrics;

class SynchronizedReference<T>
{
  private T mContents = null;
  
  public T get()
  {
    try
    {
      Object localObject1 = this.mContents;
      return (T)localObject1;
    }
    finally
    {
      localObject2 = finally;
      throw ((Throwable)localObject2);
    }
  }
  
  public T getAndClear()
  {
    try
    {
      Object localObject1 = this.mContents;
      this.mContents = null;
      return (T)localObject1;
    }
    finally
    {
      localObject2 = finally;
      throw ((Throwable)localObject2);
    }
  }
  
  public void set(T paramT)
  {
    try
    {
      this.mContents = paramT;
      return;
    }
    finally
    {
      paramT = finally;
      throw paramT;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/mpmetrics/SynchronizedReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */