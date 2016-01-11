package bolts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Task<TResult>
{
  public static final ExecutorService BACKGROUND_EXECUTOR = ;
  private static final Executor IMMEDIATE_EXECUTOR = BoltsExecutors.immediate();
  private static Task<?> TASK_CANCELLED = new Task(true);
  private static Task<Boolean> TASK_FALSE;
  private static Task<?> TASK_NULL;
  private static Task<Boolean> TASK_TRUE;
  public static final Executor UI_THREAD_EXECUTOR = AndroidExecutors.uiThread();
  private boolean cancelled;
  private boolean complete;
  private List<Continuation<TResult, Void>> continuations = new ArrayList();
  private Exception error;
  private final Object lock = new Object();
  private TResult result;
  
  static
  {
    TASK_NULL = new Task(null);
    TASK_TRUE = new Task(Boolean.valueOf(true));
    TASK_FALSE = new Task(Boolean.valueOf(false));
  }
  
  Task() {}
  
  private Task(TResult paramTResult)
  {
    trySetResult(paramTResult);
  }
  
  private Task(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      trySetCancelled();
      return;
    }
    trySetResult(null);
  }
  
  public static <TResult> Task<TResult> call(Callable<TResult> paramCallable)
  {
    return call(paramCallable, IMMEDIATE_EXECUTOR, null);
  }
  
  public static <TResult> Task<TResult> call(Callable<TResult> paramCallable, CancellationToken paramCancellationToken)
  {
    return call(paramCallable, IMMEDIATE_EXECUTOR, paramCancellationToken);
  }
  
  public static <TResult> Task<TResult> call(Callable<TResult> paramCallable, Executor paramExecutor)
  {
    return call(paramCallable, paramExecutor, null);
  }
  
  public static <TResult> Task<TResult> call(final Callable<TResult> paramCallable, Executor paramExecutor, CancellationToken paramCancellationToken)
  {
    final TaskCompletionSource localTaskCompletionSource = new TaskCompletionSource();
    paramExecutor.execute(new Runnable()
    {
      public void run()
      {
        if ((this.val$ct != null) && (this.val$ct.isCancellationRequested()))
        {
          localTaskCompletionSource.setCancelled();
          return;
        }
        try
        {
          localTaskCompletionSource.setResult(paramCallable.call());
          return;
        }
        catch (CancellationException localCancellationException)
        {
          localTaskCompletionSource.setCancelled();
          return;
        }
        catch (Exception localException)
        {
          localTaskCompletionSource.setError(localException);
        }
      }
    });
    return localTaskCompletionSource.getTask();
  }
  
  public static <TResult> Task<TResult> callInBackground(Callable<TResult> paramCallable)
  {
    return call(paramCallable, BACKGROUND_EXECUTOR, null);
  }
  
  public static <TResult> Task<TResult> callInBackground(Callable<TResult> paramCallable, CancellationToken paramCancellationToken)
  {
    return call(paramCallable, BACKGROUND_EXECUTOR, paramCancellationToken);
  }
  
  public static <TResult> Task<TResult> cancelled()
  {
    return TASK_CANCELLED;
  }
  
  private static <TContinuationResult, TResult> void completeAfterTask(final TaskCompletionSource<TContinuationResult> paramTaskCompletionSource, final Continuation<TResult, Task<TContinuationResult>> paramContinuation, final Task<TResult> paramTask, Executor paramExecutor, CancellationToken paramCancellationToken)
  {
    paramExecutor.execute(new Runnable()
    {
      public void run()
      {
        if ((this.val$ct != null) && (this.val$ct.isCancellationRequested()))
        {
          paramTaskCompletionSource.setCancelled();
          return;
        }
        try
        {
          Task localTask = (Task)paramContinuation.then(paramTask);
          if (localTask == null)
          {
            paramTaskCompletionSource.setResult(null);
            return;
          }
        }
        catch (CancellationException localCancellationException)
        {
          paramTaskCompletionSource.setCancelled();
          return;
          localCancellationException.continueWith(new Continuation()
          {
            public Void then(Task<TContinuationResult> paramAnonymous2Task)
            {
              if ((Task.15.this.val$ct != null) && (Task.15.this.val$ct.isCancellationRequested()))
              {
                Task.15.this.val$tcs.setCancelled();
                return null;
              }
              if (paramAnonymous2Task.isCancelled())
              {
                Task.15.this.val$tcs.setCancelled();
                return null;
              }
              if (paramAnonymous2Task.isFaulted())
              {
                Task.15.this.val$tcs.setError(paramAnonymous2Task.getError());
                return null;
              }
              Task.15.this.val$tcs.setResult(paramAnonymous2Task.getResult());
              return null;
            }
          });
          return;
        }
        catch (Exception localException)
        {
          paramTaskCompletionSource.setError(localException);
        }
      }
    });
  }
  
  private static <TContinuationResult, TResult> void completeImmediately(final TaskCompletionSource<TContinuationResult> paramTaskCompletionSource, final Continuation<TResult, TContinuationResult> paramContinuation, final Task<TResult> paramTask, Executor paramExecutor, CancellationToken paramCancellationToken)
  {
    paramExecutor.execute(new Runnable()
    {
      public void run()
      {
        if ((this.val$ct != null) && (this.val$ct.isCancellationRequested()))
        {
          paramTaskCompletionSource.setCancelled();
          return;
        }
        try
        {
          Object localObject = paramContinuation.then(paramTask);
          paramTaskCompletionSource.setResult(localObject);
          return;
        }
        catch (CancellationException localCancellationException)
        {
          paramTaskCompletionSource.setCancelled();
          return;
        }
        catch (Exception localException)
        {
          paramTaskCompletionSource.setError(localException);
        }
      }
    });
  }
  
  public static <TResult> Task<TResult>.TaskCompletionSource create()
  {
    Task localTask = new Task();
    localTask.getClass();
    return new TaskCompletionSource();
  }
  
  public static Task<Void> delay(long paramLong)
  {
    return delay(paramLong, BoltsExecutors.scheduled(), null);
  }
  
  public static Task<Void> delay(long paramLong, CancellationToken paramCancellationToken)
  {
    return delay(paramLong, BoltsExecutors.scheduled(), paramCancellationToken);
  }
  
  static Task<Void> delay(long paramLong, ScheduledExecutorService paramScheduledExecutorService, CancellationToken paramCancellationToken)
  {
    if ((paramCancellationToken != null) && (paramCancellationToken.isCancellationRequested())) {
      return cancelled();
    }
    if (paramLong <= 0L) {
      return forResult(null);
    }
    final TaskCompletionSource localTaskCompletionSource = new TaskCompletionSource();
    paramScheduledExecutorService = paramScheduledExecutorService.schedule(new Runnable()
    {
      public void run()
      {
        this.val$tcs.trySetResult(null);
      }
    }, paramLong, TimeUnit.MILLISECONDS);
    if (paramCancellationToken != null) {
      paramCancellationToken.register(new Runnable()
      {
        public void run()
        {
          this.val$scheduled.cancel(true);
          localTaskCompletionSource.trySetCancelled();
        }
      });
    }
    return localTaskCompletionSource.getTask();
  }
  
  public static <TResult> Task<TResult> forError(Exception paramException)
  {
    TaskCompletionSource localTaskCompletionSource = new TaskCompletionSource();
    localTaskCompletionSource.setError(paramException);
    return localTaskCompletionSource.getTask();
  }
  
  public static <TResult> Task<TResult> forResult(TResult paramTResult)
  {
    if (paramTResult == null) {
      return TASK_NULL;
    }
    if ((paramTResult instanceof Boolean))
    {
      if (((Boolean)paramTResult).booleanValue()) {
        return TASK_TRUE;
      }
      return TASK_FALSE;
    }
    TaskCompletionSource localTaskCompletionSource = new TaskCompletionSource();
    localTaskCompletionSource.setResult(paramTResult);
    return localTaskCompletionSource.getTask();
  }
  
  private void runContinuations()
  {
    for (;;)
    {
      Continuation localContinuation;
      synchronized (this.lock)
      {
        Iterator localIterator = this.continuations.iterator();
        if (!localIterator.hasNext()) {
          break;
        }
        localContinuation = (Continuation)localIterator.next();
      }
      try
      {
        localContinuation.then(this);
      }
      catch (RuntimeException localRuntimeException)
      {
        throw localRuntimeException;
        localObject2 = finally;
        throw ((Throwable)localObject2);
      }
      catch (Exception localException)
      {
        throw new RuntimeException(localException);
      }
    }
    this.continuations = null;
  }
  
  public static Task<Void> whenAll(Collection<? extends Task<?>> paramCollection)
  {
    if (paramCollection.size() == 0) {
      return forResult(null);
    }
    final TaskCompletionSource localTaskCompletionSource = new TaskCompletionSource();
    final ArrayList localArrayList = new ArrayList();
    Object localObject = new Object();
    final AtomicInteger localAtomicInteger = new AtomicInteger(paramCollection.size());
    final AtomicBoolean localAtomicBoolean = new AtomicBoolean(false);
    paramCollection = paramCollection.iterator();
    while (paramCollection.hasNext()) {
      ((Task)paramCollection.next()).continueWith(new Continuation()
      {
        public Void then(Task<Object> paramAnonymousTask)
        {
          if (paramAnonymousTask.isFaulted()) {}
          synchronized (this.val$errorLock)
          {
            localArrayList.add(paramAnonymousTask.getError());
            if (paramAnonymousTask.isCancelled()) {
              localAtomicBoolean.set(true);
            }
            if (localAtomicInteger.decrementAndGet() == 0)
            {
              if (localArrayList.size() == 0) {
                break label143;
              }
              if (localArrayList.size() == 1) {
                localTaskCompletionSource.setError((Exception)localArrayList.get(0));
              }
            }
            else
            {
              return null;
            }
          }
          paramAnonymousTask = new AggregateException(String.format("There were %d exceptions.", new Object[] { Integer.valueOf(localArrayList.size()) }), localArrayList);
          localTaskCompletionSource.setError(paramAnonymousTask);
          return null;
          label143:
          if (localAtomicBoolean.get())
          {
            localTaskCompletionSource.setCancelled();
            return null;
          }
          localTaskCompletionSource.setResult(null);
          return null;
        }
      });
    }
    return localTaskCompletionSource.getTask();
  }
  
  public static <TResult> Task<List<TResult>> whenAllResult(Collection<? extends Task<TResult>> paramCollection)
  {
    whenAll(paramCollection).onSuccess(new Continuation()
    {
      public List<TResult> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        if (this.val$tasks.size() == 0)
        {
          paramAnonymousTask = Collections.emptyList();
          return paramAnonymousTask;
        }
        ArrayList localArrayList = new ArrayList();
        Iterator localIterator = this.val$tasks.iterator();
        for (;;)
        {
          paramAnonymousTask = localArrayList;
          if (!localIterator.hasNext()) {
            break;
          }
          localArrayList.add(((Task)localIterator.next()).getResult());
        }
      }
    });
  }
  
  public static Task<Task<?>> whenAny(Collection<? extends Task<?>> paramCollection)
  {
    if (paramCollection.size() == 0) {
      return forResult(null);
    }
    final TaskCompletionSource localTaskCompletionSource = new TaskCompletionSource();
    AtomicBoolean localAtomicBoolean = new AtomicBoolean(false);
    paramCollection = paramCollection.iterator();
    while (paramCollection.hasNext()) {
      ((Task)paramCollection.next()).continueWith(new Continuation()
      {
        public Void then(Task<Object> paramAnonymousTask)
        {
          if (this.val$isAnyTaskComplete.compareAndSet(false, true)) {
            localTaskCompletionSource.setResult(paramAnonymousTask);
          }
          return null;
        }
      });
    }
    return localTaskCompletionSource.getTask();
  }
  
  public static <TResult> Task<Task<TResult>> whenAnyResult(Collection<? extends Task<TResult>> paramCollection)
  {
    if (paramCollection.size() == 0) {
      return forResult(null);
    }
    final TaskCompletionSource localTaskCompletionSource = new TaskCompletionSource();
    AtomicBoolean localAtomicBoolean = new AtomicBoolean(false);
    paramCollection = paramCollection.iterator();
    while (paramCollection.hasNext()) {
      ((Task)paramCollection.next()).continueWith(new Continuation()
      {
        public Void then(Task<TResult> paramAnonymousTask)
        {
          if (this.val$isAnyTaskComplete.compareAndSet(false, true)) {
            localTaskCompletionSource.setResult(paramAnonymousTask);
          }
          return null;
        }
      });
    }
    return localTaskCompletionSource.getTask();
  }
  
  public <TOut> Task<TOut> cast()
  {
    return this;
  }
  
  public Task<Void> continueWhile(Callable<Boolean> paramCallable, Continuation<Void, Task<Void>> paramContinuation)
  {
    return continueWhile(paramCallable, paramContinuation, IMMEDIATE_EXECUTOR, null);
  }
  
  public Task<Void> continueWhile(Callable<Boolean> paramCallable, Continuation<Void, Task<Void>> paramContinuation, CancellationToken paramCancellationToken)
  {
    return continueWhile(paramCallable, paramContinuation, IMMEDIATE_EXECUTOR, paramCancellationToken);
  }
  
  public Task<Void> continueWhile(Callable<Boolean> paramCallable, Continuation<Void, Task<Void>> paramContinuation, Executor paramExecutor)
  {
    return continueWhile(paramCallable, paramContinuation, paramExecutor, null);
  }
  
  public Task<Void> continueWhile(final Callable<Boolean> paramCallable, final Continuation<Void, Task<Void>> paramContinuation, final Executor paramExecutor, final CancellationToken paramCancellationToken)
  {
    final Capture localCapture = new Capture();
    localCapture.set(new Continuation()
    {
      public Task<Void> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        if ((paramCancellationToken != null) && (paramCancellationToken.isCancellationRequested())) {
          return Task.cancelled();
        }
        if (((Boolean)paramCallable.call()).booleanValue()) {
          return Task.forResult(null).onSuccessTask(paramContinuation, paramExecutor).onSuccessTask((Continuation)localCapture.get(), paramExecutor);
        }
        return Task.forResult(null);
      }
    });
    return makeVoid().continueWithTask((Continuation)localCapture.get(), paramExecutor);
  }
  
  public <TContinuationResult> Task<TContinuationResult> continueWith(Continuation<TResult, TContinuationResult> paramContinuation)
  {
    return continueWith(paramContinuation, IMMEDIATE_EXECUTOR, null);
  }
  
  public <TContinuationResult> Task<TContinuationResult> continueWith(Continuation<TResult, TContinuationResult> paramContinuation, CancellationToken paramCancellationToken)
  {
    return continueWith(paramContinuation, IMMEDIATE_EXECUTOR, paramCancellationToken);
  }
  
  public <TContinuationResult> Task<TContinuationResult> continueWith(Continuation<TResult, TContinuationResult> paramContinuation, Executor paramExecutor)
  {
    return continueWith(paramContinuation, paramExecutor, null);
  }
  
  public <TContinuationResult> Task<TContinuationResult> continueWith(final Continuation<TResult, TContinuationResult> paramContinuation, final Executor paramExecutor, final CancellationToken paramCancellationToken)
  {
    final TaskCompletionSource localTaskCompletionSource = new TaskCompletionSource();
    synchronized (this.lock)
    {
      boolean bool = isCompleted();
      if (!bool) {
        this.continuations.add(new Continuation()
        {
          public Void then(Task<TResult> paramAnonymousTask)
          {
            Task.completeImmediately(localTaskCompletionSource, paramContinuation, paramAnonymousTask, paramExecutor, paramCancellationToken);
            return null;
          }
        });
      }
      if (bool) {
        completeImmediately(localTaskCompletionSource, paramContinuation, this, paramExecutor, paramCancellationToken);
      }
      return localTaskCompletionSource.getTask();
    }
  }
  
  public <TContinuationResult> Task<TContinuationResult> continueWithTask(Continuation<TResult, Task<TContinuationResult>> paramContinuation)
  {
    return continueWithTask(paramContinuation, IMMEDIATE_EXECUTOR, null);
  }
  
  public <TContinuationResult> Task<TContinuationResult> continueWithTask(Continuation<TResult, Task<TContinuationResult>> paramContinuation, CancellationToken paramCancellationToken)
  {
    return continueWithTask(paramContinuation, IMMEDIATE_EXECUTOR, paramCancellationToken);
  }
  
  public <TContinuationResult> Task<TContinuationResult> continueWithTask(Continuation<TResult, Task<TContinuationResult>> paramContinuation, Executor paramExecutor)
  {
    return continueWithTask(paramContinuation, paramExecutor, null);
  }
  
  public <TContinuationResult> Task<TContinuationResult> continueWithTask(final Continuation<TResult, Task<TContinuationResult>> paramContinuation, final Executor paramExecutor, final CancellationToken paramCancellationToken)
  {
    final TaskCompletionSource localTaskCompletionSource = new TaskCompletionSource();
    synchronized (this.lock)
    {
      boolean bool = isCompleted();
      if (!bool) {
        this.continuations.add(new Continuation()
        {
          public Void then(Task<TResult> paramAnonymousTask)
          {
            Task.completeAfterTask(localTaskCompletionSource, paramContinuation, paramAnonymousTask, paramExecutor, paramCancellationToken);
            return null;
          }
        });
      }
      if (bool) {
        completeAfterTask(localTaskCompletionSource, paramContinuation, this, paramExecutor, paramCancellationToken);
      }
      return localTaskCompletionSource.getTask();
    }
  }
  
  public Exception getError()
  {
    synchronized (this.lock)
    {
      Exception localException = this.error;
      return localException;
    }
  }
  
  public TResult getResult()
  {
    synchronized (this.lock)
    {
      Object localObject2 = this.result;
      return (TResult)localObject2;
    }
  }
  
  public boolean isCancelled()
  {
    synchronized (this.lock)
    {
      boolean bool = this.cancelled;
      return bool;
    }
  }
  
  public boolean isCompleted()
  {
    synchronized (this.lock)
    {
      boolean bool = this.complete;
      return bool;
    }
  }
  
  public boolean isFaulted()
  {
    for (;;)
    {
      synchronized (this.lock)
      {
        if (this.error != null)
        {
          bool = true;
          return bool;
        }
      }
      boolean bool = false;
    }
  }
  
  public Task<Void> makeVoid()
  {
    continueWithTask(new Continuation()
    {
      public Task<Void> then(Task<TResult> paramAnonymousTask)
        throws Exception
      {
        if (paramAnonymousTask.isCancelled()) {
          return Task.cancelled();
        }
        if (paramAnonymousTask.isFaulted()) {
          return Task.forError(paramAnonymousTask.getError());
        }
        return Task.forResult(null);
      }
    });
  }
  
  public <TContinuationResult> Task<TContinuationResult> onSuccess(Continuation<TResult, TContinuationResult> paramContinuation)
  {
    return onSuccess(paramContinuation, IMMEDIATE_EXECUTOR, null);
  }
  
  public <TContinuationResult> Task<TContinuationResult> onSuccess(Continuation<TResult, TContinuationResult> paramContinuation, CancellationToken paramCancellationToken)
  {
    return onSuccess(paramContinuation, IMMEDIATE_EXECUTOR, paramCancellationToken);
  }
  
  public <TContinuationResult> Task<TContinuationResult> onSuccess(Continuation<TResult, TContinuationResult> paramContinuation, Executor paramExecutor)
  {
    return onSuccess(paramContinuation, paramExecutor, null);
  }
  
  public <TContinuationResult> Task<TContinuationResult> onSuccess(final Continuation<TResult, TContinuationResult> paramContinuation, Executor paramExecutor, final CancellationToken paramCancellationToken)
  {
    continueWithTask(new Continuation()
    {
      public Task<TContinuationResult> then(Task<TResult> paramAnonymousTask)
      {
        if ((paramCancellationToken != null) && (paramCancellationToken.isCancellationRequested())) {
          return Task.cancelled();
        }
        if (paramAnonymousTask.isFaulted()) {
          return Task.forError(paramAnonymousTask.getError());
        }
        if (paramAnonymousTask.isCancelled()) {
          return Task.cancelled();
        }
        return paramAnonymousTask.continueWith(paramContinuation);
      }
    }, paramExecutor);
  }
  
  public <TContinuationResult> Task<TContinuationResult> onSuccessTask(Continuation<TResult, Task<TContinuationResult>> paramContinuation)
  {
    return onSuccessTask(paramContinuation, IMMEDIATE_EXECUTOR);
  }
  
  public <TContinuationResult> Task<TContinuationResult> onSuccessTask(Continuation<TResult, Task<TContinuationResult>> paramContinuation, CancellationToken paramCancellationToken)
  {
    return onSuccessTask(paramContinuation, IMMEDIATE_EXECUTOR, paramCancellationToken);
  }
  
  public <TContinuationResult> Task<TContinuationResult> onSuccessTask(Continuation<TResult, Task<TContinuationResult>> paramContinuation, Executor paramExecutor)
  {
    return onSuccessTask(paramContinuation, paramExecutor, null);
  }
  
  public <TContinuationResult> Task<TContinuationResult> onSuccessTask(final Continuation<TResult, Task<TContinuationResult>> paramContinuation, Executor paramExecutor, final CancellationToken paramCancellationToken)
  {
    continueWithTask(new Continuation()
    {
      public Task<TContinuationResult> then(Task<TResult> paramAnonymousTask)
      {
        if ((paramCancellationToken != null) && (paramCancellationToken.isCancellationRequested())) {
          return Task.cancelled();
        }
        if (paramAnonymousTask.isFaulted()) {
          return Task.forError(paramAnonymousTask.getError());
        }
        if (paramAnonymousTask.isCancelled()) {
          return Task.cancelled();
        }
        return paramAnonymousTask.continueWithTask(paramContinuation);
      }
    }, paramExecutor);
  }
  
  boolean trySetCancelled()
  {
    synchronized (this.lock)
    {
      if (this.complete) {
        return false;
      }
      this.complete = true;
      this.cancelled = true;
      this.lock.notifyAll();
      runContinuations();
      return true;
    }
  }
  
  boolean trySetError(Exception paramException)
  {
    synchronized (this.lock)
    {
      if (this.complete) {
        return false;
      }
      this.complete = true;
      this.error = paramException;
      this.lock.notifyAll();
      runContinuations();
      return true;
    }
  }
  
  boolean trySetResult(TResult paramTResult)
  {
    synchronized (this.lock)
    {
      if (this.complete) {
        return false;
      }
      this.complete = true;
      this.result = paramTResult;
      this.lock.notifyAll();
      runContinuations();
      return true;
    }
  }
  
  public void waitForCompletion()
    throws InterruptedException
  {
    synchronized (this.lock)
    {
      if (!isCompleted()) {
        this.lock.wait();
      }
      return;
    }
  }
  
  public class TaskCompletionSource
    extends TaskCompletionSource<TResult>
  {
    TaskCompletionSource() {}
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/bolts/Task.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */