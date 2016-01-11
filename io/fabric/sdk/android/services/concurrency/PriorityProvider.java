package io.fabric.sdk.android.services.concurrency;

public abstract interface PriorityProvider<T>
  extends Comparable<T>
{
  public abstract Priority getPriority();
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/concurrency/PriorityProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */