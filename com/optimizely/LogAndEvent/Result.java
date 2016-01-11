package com.optimizely.LogAndEvent;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Result<T>
  implements Future<T>
{
  static final int CANCELLED = 8;
  @NonNull
  public static final Result FAILURE = new Result(4, false, null);
  static final int FINISHED = 4;
  private static final int NOT_STARTED = 1;
  static final int RUNNING = 2;
  @NonNull
  public static final Result SUCCESS = new Result(4, true, null);
  @NonNull
  private final List<Handler<T>> mHandlers = new ArrayList();
  @Nullable
  private T mPayload;
  int mStatus;
  private boolean mSuccess;
  
  public Result()
  {
    this.mStatus = 1;
    this.mPayload = null;
    this.mSuccess = false;
  }
  
  private Result(int paramInt, boolean paramBoolean, @Nullable T paramT)
  {
    this.mStatus = paramInt;
    this.mPayload = paramT;
    this.mSuccess = paramBoolean;
  }
  
  private boolean isSuccessful()
  {
    return this.mSuccess;
  }
  
  private void notifyHandlers()
  {
    Iterator localIterator;
    if (this.mStatus == 8)
    {
      localIterator = this.mHandlers.iterator();
      while (localIterator.hasNext()) {
        ((Handler)localIterator.next()).onCancelled();
      }
    }
    if (this.mStatus == 4)
    {
      localIterator = this.mHandlers.iterator();
      while (localIterator.hasNext()) {
        ((Handler)localIterator.next()).onResolve(this.mSuccess, this.mPayload);
      }
    }
  }
  
  private void notifyHandlersOfTimeout()
  {
    Iterator localIterator = this.mHandlers.iterator();
    while (localIterator.hasNext()) {
      ((Handler)localIterator.next()).onTimedOut();
    }
  }
  
  public boolean cancel(boolean paramBoolean)
  {
    if (!isDone())
    {
      this.mStatus = 8;
      this.mSuccess = false;
      notifyHandlers();
    }
    return true;
  }
  
  @Nullable
  public T get()
  {
    for (;;)
    {
      if (isDone())
      {
        notifyHandlers();
        return (T)this.mPayload;
      }
      Thread.yield();
    }
  }
  
  @Nullable
  public T get(long paramLong, @NonNull TimeUnit paramTimeUnit)
  {
    paramLong = paramTimeUnit.toNanos(paramLong);
    long l = System.nanoTime();
    for (;;)
    {
      if (isDone()) {
        notifyHandlers();
      }
      for (;;)
      {
        return (T)this.mPayload;
        if (System.nanoTime() < paramLong + l) {
          break;
        }
        notifyHandlersOfTimeout();
      }
      Thread.yield();
    }
  }
  
  public boolean getSuccess()
  {
    get();
    return isSuccessful();
  }
  
  public boolean getSuccess(long paramLong, @NonNull TimeUnit paramTimeUnit)
  {
    get(paramLong, paramTimeUnit);
    return isSuccessful();
  }
  
  public boolean isCancelled()
  {
    return this.mStatus == 8;
  }
  
  public boolean isDone()
  {
    return (this.mStatus & 0xC) != 0;
  }
  
  public void resolve(boolean paramBoolean, @Nullable T paramT)
  {
    if (!isDone())
    {
      this.mPayload = paramT;
      this.mSuccess = paramBoolean;
      this.mStatus = 4;
      Iterator localIterator = this.mHandlers.iterator();
      while (localIterator.hasNext()) {
        ((Handler)localIterator.next()).onResolve(paramBoolean, paramT);
      }
    }
  }
  
  @NonNull
  public Result<T> then(@NonNull Handler<T> paramHandler)
  {
    if (isDone())
    {
      if (this.mStatus == 8) {
        paramHandler.onCancelled();
      }
      while (this.mStatus != 4) {
        return this;
      }
      paramHandler.onResolve(this.mSuccess, this.mPayload);
      return this;
    }
    this.mHandlers.add(paramHandler);
    return this;
  }
  
  public String toString()
  {
    return "Result{mStatus=" + this.mStatus + ", mPayload=" + this.mPayload + ", mSuccess=" + this.mSuccess + ", mHandlersCount=" + this.mHandlers.size() + '}';
  }
  
  public static abstract class Handler<T>
  {
    public void onCancelled() {}
    
    public void onResolve(boolean paramBoolean, @Nullable T paramT) {}
    
    public void onTimedOut() {}
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/LogAndEvent/Result.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */