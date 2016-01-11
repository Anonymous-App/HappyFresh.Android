package io.fabric.sdk.android;

public abstract interface InitializationCallback<T>
{
  public static final InitializationCallback EMPTY = new Empty(null);
  
  public abstract void failure(Exception paramException);
  
  public abstract void success(T paramT);
  
  public static class Empty
    implements InitializationCallback<Object>
  {
    public void failure(Exception paramException) {}
    
    public void success(Object paramObject) {}
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/InitializationCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */