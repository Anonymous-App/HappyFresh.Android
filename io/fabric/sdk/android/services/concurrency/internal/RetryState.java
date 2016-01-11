package io.fabric.sdk.android.services.concurrency.internal;

public class RetryState
{
  private final Backoff backoff;
  private final int retryCount;
  private final RetryPolicy retryPolicy;
  
  public RetryState(int paramInt, Backoff paramBackoff, RetryPolicy paramRetryPolicy)
  {
    this.retryCount = paramInt;
    this.backoff = paramBackoff;
    this.retryPolicy = paramRetryPolicy;
  }
  
  public RetryState(Backoff paramBackoff, RetryPolicy paramRetryPolicy)
  {
    this(0, paramBackoff, paramRetryPolicy);
  }
  
  public Backoff getBackoff()
  {
    return this.backoff;
  }
  
  public int getRetryCount()
  {
    return this.retryCount;
  }
  
  public long getRetryDelay()
  {
    return this.backoff.getDelayMillis(this.retryCount);
  }
  
  public RetryPolicy getRetryPolicy()
  {
    return this.retryPolicy;
  }
  
  public RetryState initialRetryState()
  {
    return new RetryState(this.backoff, this.retryPolicy);
  }
  
  public RetryState nextRetryState()
  {
    return new RetryState(this.retryCount + 1, this.backoff, this.retryPolicy);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/concurrency/internal/RetryState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */