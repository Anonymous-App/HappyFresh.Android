package com.parse;

import bolts.Task;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

class ParseExecutors
{
  private static final Object SCHEDULED_EXECUTOR_LOCK = new Object();
  private static ScheduledExecutorService scheduledExecutor;
  
  static Executor io()
  {
    return Task.BACKGROUND_EXECUTOR;
  }
  
  static Executor main()
  {
    return Task.UI_THREAD_EXECUTOR;
  }
  
  static ScheduledExecutorService scheduled()
  {
    synchronized (SCHEDULED_EXECUTOR_LOCK)
    {
      if (scheduledExecutor == null) {
        scheduledExecutor = Executors.newScheduledThreadPool(1);
      }
      return scheduledExecutor;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseExecutors.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */