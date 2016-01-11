package com.crashlytics.android.core;

import android.os.Looper;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

class CrashlyticsExecutorServiceWrapper
{
  private final ExecutorService executorService;
  
  public CrashlyticsExecutorServiceWrapper(ExecutorService paramExecutorService)
  {
    this.executorService = paramExecutorService;
  }
  
  Future<?> executeAsync(final Runnable paramRunnable)
  {
    try
    {
      paramRunnable = this.executorService.submit(new Runnable()
      {
        public void run()
        {
          try
          {
            paramRunnable.run();
            return;
          }
          catch (Exception localException)
          {
            Fabric.getLogger().e("CrashlyticsCore", "Failed to execute task.", localException);
          }
        }
      });
      return paramRunnable;
    }
    catch (RejectedExecutionException paramRunnable)
    {
      Fabric.getLogger().d("CrashlyticsCore", "Executor is shut down because we're handling a fatal crash.");
    }
    return null;
  }
  
  <T> Future<T> executeAsync(final Callable<T> paramCallable)
  {
    try
    {
      paramCallable = this.executorService.submit(new Callable()
      {
        public T call()
          throws Exception
        {
          try
          {
            Object localObject = paramCallable.call();
            return (T)localObject;
          }
          catch (Exception localException)
          {
            Fabric.getLogger().e("CrashlyticsCore", "Failed to execute task.", localException);
          }
          return null;
        }
      });
      return paramCallable;
    }
    catch (RejectedExecutionException paramCallable)
    {
      Fabric.getLogger().d("CrashlyticsCore", "Executor is shut down because we're handling a fatal crash.");
    }
    return null;
  }
  
  <T> T executeSyncLoggingException(Callable<T> paramCallable)
  {
    try
    {
      if (Looper.getMainLooper() == Looper.myLooper()) {
        return (T)this.executorService.submit(paramCallable).get(4L, TimeUnit.SECONDS);
      }
      paramCallable = this.executorService.submit(paramCallable).get();
      return paramCallable;
    }
    catch (RejectedExecutionException paramCallable)
    {
      Fabric.getLogger().d("CrashlyticsCore", "Executor is shut down because we're handling a fatal crash.");
      return null;
    }
    catch (Exception paramCallable)
    {
      Fabric.getLogger().e("CrashlyticsCore", "Failed to execute task.", paramCallable);
    }
    return null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/crashlytics/android/core/CrashlyticsExecutorServiceWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */