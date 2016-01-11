package io.fabric.sdk.android.services.concurrency.internal;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

class RetryFuture<T>
  extends AbstractFuture<T>
  implements Runnable
{
  private final RetryThreadPoolExecutor executor;
  RetryState retryState;
  private final AtomicReference<Thread> runner;
  private final Callable<T> task;
  
  RetryFuture(Callable<T> paramCallable, RetryState paramRetryState, RetryThreadPoolExecutor paramRetryThreadPoolExecutor)
  {
    this.task = paramCallable;
    this.retryState = paramRetryState;
    this.executor = paramRetryThreadPoolExecutor;
    this.runner = new AtomicReference();
  }
  
  private Backoff getBackoff()
  {
    return this.retryState.getBackoff();
  }
  
  private int getRetryCount()
  {
    return this.retryState.getRetryCount();
  }
  
  private RetryPolicy getRetryPolicy()
  {
    return this.retryState.getRetryPolicy();
  }
  
  protected void interruptTask()
  {
    Thread localThread = (Thread)this.runner.getAndSet(null);
    if (localThread != null) {
      localThread.interrupt();
    }
  }
  
  public void run()
  {
    if ((isDone()) || (!this.runner.compareAndSet(null, Thread.currentThread()))) {
      return;
    }
    try
    {
      set(this.task.call());
      return;
    }
    catch (Throwable localThrowable)
    {
      if (getRetryPolicy().shouldRetry(getRetryCount(), localThrowable))
      {
        long l = getBackoff().getDelayMillis(getRetryCount());
        this.retryState = this.retryState.nextRetryState();
        this.executor.schedule(this, l, TimeUnit.MILLISECONDS);
      }
      for (;;)
      {
        return;
        setException(localThrowable);
      }
    }
    finally
    {
      this.runner.getAndSet(null);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/concurrency/internal/RetryFuture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */