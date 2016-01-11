package com.optimizely.utils;

import android.support.annotation.NonNull;
import android.util.Log;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class OptimizelyThreadPoolExecutor
{
  public static final String BASE_THREAD_NAME = "OptimizelyAsyncTask";
  static final int CORE_POOL_SIZE;
  private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
  private static final int KEEP_ALIVE_TIME = 1;
  private static final TimeUnit KEEP_ALIVE_TIME_UNIT;
  static final int MAXIMUM_POOL_SIZE;
  private static final int MAXIMUM_QUEUE_SIZE = 64;
  @NonNull
  private static final ThreadPoolExecutor OPTIMIZELY_THREAD_POOL = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, 1L, KEEP_ALIVE_TIME_UNIT, POOL_WORK_QUEUE, THREAD_FACTORY)
  {
    public void execute(Runnable paramAnonymousRunnable)
    {
      try
      {
        super.execute(paramAnonymousRunnable);
        return;
      }
      catch (RejectedExecutionException paramAnonymousRunnable)
      {
        Log.w(OptimizelyThreadPoolExecutor.class.getSimpleName(), "Optimizely thread pool is full.  It can't do things like network tasks.  Is some other thread starving it?");
      }
    }
  };
  private static final BlockingQueue<Runnable> POOL_WORK_QUEUE;
  static final ThreadFactory THREAD_FACTORY;
  
  static
  {
    CORE_POOL_SIZE = CPU_COUNT + 1;
    MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
    THREAD_FACTORY = new ThreadFactory()
    {
      @NonNull
      private final AtomicInteger counter = new AtomicInteger(1);
      
      @NonNull
      public Thread newThread(@NonNull Runnable paramAnonymousRunnable)
      {
        return new Thread(paramAnonymousRunnable, "OptimizelyAsyncTask #" + this.counter.getAndIncrement());
      }
    };
    POOL_WORK_QUEUE = new LinkedBlockingQueue(64);
  }
  
  @NonNull
  public static ThreadPoolExecutor instance()
  {
    return OPTIMIZELY_THREAD_POOL;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/utils/OptimizelyThreadPoolExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */