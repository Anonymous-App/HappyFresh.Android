package io.fabric.sdk.android.services.concurrency.internal;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public abstract class AbstractFuture<V>
  implements Future<V>
{
  private final Sync<V> sync = new Sync();
  
  static final CancellationException cancellationExceptionWithCause(String paramString, Throwable paramThrowable)
  {
    paramString = new CancellationException(paramString);
    paramString.initCause(paramThrowable);
    return paramString;
  }
  
  public boolean cancel(boolean paramBoolean)
  {
    if (!this.sync.cancel(paramBoolean)) {
      return false;
    }
    if (paramBoolean) {
      interruptTask();
    }
    return true;
  }
  
  public V get()
    throws InterruptedException, ExecutionException
  {
    return (V)this.sync.get();
  }
  
  public V get(long paramLong, TimeUnit paramTimeUnit)
    throws InterruptedException, TimeoutException, ExecutionException
  {
    return (V)this.sync.get(paramTimeUnit.toNanos(paramLong));
  }
  
  protected void interruptTask() {}
  
  public boolean isCancelled()
  {
    return this.sync.isCancelled();
  }
  
  public boolean isDone()
  {
    return this.sync.isDone();
  }
  
  protected boolean set(V paramV)
  {
    return this.sync.set(paramV);
  }
  
  protected boolean setException(Throwable paramThrowable)
  {
    if (paramThrowable == null) {
      throw new NullPointerException();
    }
    return this.sync.setException(paramThrowable);
  }
  
  protected final boolean wasInterrupted()
  {
    return this.sync.wasInterrupted();
  }
  
  static final class Sync<V>
    extends AbstractQueuedSynchronizer
  {
    static final int CANCELLED = 4;
    static final int COMPLETED = 2;
    static final int COMPLETING = 1;
    static final int INTERRUPTED = 8;
    static final int RUNNING = 0;
    private static final long serialVersionUID = 0L;
    private Throwable exception;
    private V value;
    
    private boolean complete(V paramV, Throwable paramThrowable, int paramInt)
    {
      boolean bool = compareAndSetState(0, 1);
      if (bool)
      {
        this.value = paramV;
        if ((paramInt & 0xC) != 0) {
          paramThrowable = new CancellationException("Future.cancel() was called.");
        }
        this.exception = paramThrowable;
        releaseShared(paramInt);
      }
      while (getState() != 1) {
        return bool;
      }
      acquireShared(-1);
      return bool;
    }
    
    private V getValue()
      throws CancellationException, ExecutionException
    {
      int i = getState();
      switch (i)
      {
      default: 
        throw new IllegalStateException("Error, synchronizer in invalid state: " + i);
      case 2: 
        if (this.exception != null) {
          throw new ExecutionException(this.exception);
        }
        return (V)this.value;
      }
      throw AbstractFuture.cancellationExceptionWithCause("Task was cancelled.", this.exception);
    }
    
    boolean cancel(boolean paramBoolean)
    {
      if (paramBoolean) {}
      for (int i = 8;; i = 4) {
        return complete(null, null, i);
      }
    }
    
    V get()
      throws CancellationException, ExecutionException, InterruptedException
    {
      acquireSharedInterruptibly(-1);
      return (V)getValue();
    }
    
    V get(long paramLong)
      throws TimeoutException, CancellationException, ExecutionException, InterruptedException
    {
      if (!tryAcquireSharedNanos(-1, paramLong)) {
        throw new TimeoutException("Timeout waiting for task.");
      }
      return (V)getValue();
    }
    
    boolean isCancelled()
    {
      return (getState() & 0xC) != 0;
    }
    
    boolean isDone()
    {
      return (getState() & 0xE) != 0;
    }
    
    boolean set(V paramV)
    {
      return complete(paramV, null, 2);
    }
    
    boolean setException(Throwable paramThrowable)
    {
      return complete(null, paramThrowable, 2);
    }
    
    protected int tryAcquireShared(int paramInt)
    {
      if (isDone()) {
        return 1;
      }
      return -1;
    }
    
    protected boolean tryReleaseShared(int paramInt)
    {
      setState(paramInt);
      return true;
    }
    
    boolean wasInterrupted()
    {
      return getState() == 8;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/concurrency/internal/AbstractFuture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */