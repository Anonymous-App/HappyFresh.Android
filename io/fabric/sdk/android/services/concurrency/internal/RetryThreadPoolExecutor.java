package io.fabric.sdk.android.services.concurrency.internal;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

public class RetryThreadPoolExecutor
  extends ScheduledThreadPoolExecutor
{
  private final Backoff backoff;
  private final RetryPolicy retryPolicy;
  
  public RetryThreadPoolExecutor(int paramInt, RetryPolicy paramRetryPolicy, Backoff paramBackoff)
  {
    this(paramInt, Executors.defaultThreadFactory(), paramRetryPolicy, paramBackoff);
  }
  
  public RetryThreadPoolExecutor(int paramInt, ThreadFactory paramThreadFactory, RetryPolicy paramRetryPolicy, Backoff paramBackoff)
  {
    super(paramInt, paramThreadFactory);
    if (paramRetryPolicy == null) {
      throw new NullPointerException("retry policy must not be null");
    }
    if (paramBackoff == null) {
      throw new NullPointerException("backoff must not be null");
    }
    this.retryPolicy = paramRetryPolicy;
    this.backoff = paramBackoff;
  }
  
  private <T> Future<T> scheduleWithRetryInternal(Callable<T> paramCallable)
  {
    if (paramCallable == null) {
      throw new NullPointerException();
    }
    paramCallable = new RetryFuture(paramCallable, new RetryState(this.backoff, this.retryPolicy), this);
    execute(paramCallable);
    return paramCallable;
  }
  
  public Backoff getBackoff()
  {
    return this.backoff;
  }
  
  public RetryPolicy getRetryPolicy()
  {
    return this.retryPolicy;
  }
  
  public Future<?> scheduleWithRetry(Runnable paramRunnable)
  {
    return scheduleWithRetryInternal(Executors.callable(paramRunnable));
  }
  
  public <T> Future<T> scheduleWithRetry(Runnable paramRunnable, T paramT)
  {
    return scheduleWithRetryInternal(Executors.callable(paramRunnable, paramT));
  }
  
  public <T> Future<T> scheduleWithRetry(Callable<T> paramCallable)
  {
    return scheduleWithRetryInternal(paramCallable);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/concurrency/internal/RetryThreadPoolExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */