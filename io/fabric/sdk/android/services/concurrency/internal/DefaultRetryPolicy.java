package io.fabric.sdk.android.services.concurrency.internal;

public class DefaultRetryPolicy
  implements RetryPolicy
{
  private final int maxRetries;
  
  public DefaultRetryPolicy()
  {
    this(1);
  }
  
  public DefaultRetryPolicy(int paramInt)
  {
    this.maxRetries = paramInt;
  }
  
  public boolean shouldRetry(int paramInt, Throwable paramThrowable)
  {
    return paramInt < this.maxRetries;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/concurrency/internal/DefaultRetryPolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */