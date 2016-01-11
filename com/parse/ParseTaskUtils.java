package com.parse;

import bolts.AggregateException;
import bolts.Continuation;
import bolts.Task;
import bolts.Task.TaskCompletionSource;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;

class ParseTaskUtils
{
  static Task<Void> callbackOnMainThreadAsync(Task<Void> paramTask, ParseCallback1<ParseException> paramParseCallback1)
  {
    return callbackOnMainThreadAsync(paramTask, paramParseCallback1, false);
  }
  
  static Task<Void> callbackOnMainThreadAsync(Task<Void> paramTask, ParseCallback1<ParseException> paramParseCallback1, boolean paramBoolean)
  {
    if (paramParseCallback1 == null) {
      return paramTask;
    }
    callbackOnMainThreadAsync(paramTask, new ParseCallback2()
    {
      public void done(Void paramAnonymousVoid, ParseException paramAnonymousParseException)
      {
        this.val$callback.done(paramAnonymousParseException);
      }
    }, paramBoolean);
  }
  
  static <T> Task<T> callbackOnMainThreadAsync(Task<T> paramTask, ParseCallback2<T, ParseException> paramParseCallback2)
  {
    return callbackOnMainThreadAsync(paramTask, paramParseCallback2, false);
  }
  
  static <T> Task<T> callbackOnMainThreadAsync(Task<T> paramTask, final ParseCallback2<T, ParseException> paramParseCallback2, boolean paramBoolean)
  {
    if (paramParseCallback2 == null) {
      return paramTask;
    }
    final Task.TaskCompletionSource localTaskCompletionSource = Task.create();
    paramTask.continueWith(new Continuation()
    {
      public Void then(final Task<T> paramAnonymousTask)
        throws Exception
      {
        if ((paramAnonymousTask.isCancelled()) && (!this.val$reportCancellation))
        {
          localTaskCompletionSource.setCancelled();
          return null;
        }
        ParseExecutors.main().execute(new Runnable()
        {
          public void run()
          {
            try
            {
              Exception localException = paramAnonymousTask.getError();
              Object localObject1 = localException;
              if (localException != null)
              {
                localObject1 = localException;
                if (!(localException instanceof ParseException)) {
                  localObject1 = new ParseException(localException);
                }
              }
              ParseTaskUtils.2.this.val$callback.done(paramAnonymousTask.getResult(), (ParseException)localObject1);
              if (paramAnonymousTask.isCancelled())
              {
                ParseTaskUtils.2.this.val$tcs.setCancelled();
                return;
              }
              if (paramAnonymousTask.isFaulted())
              {
                ParseTaskUtils.2.this.val$tcs.setError(paramAnonymousTask.getError());
                return;
              }
              ParseTaskUtils.2.this.val$tcs.setResult(paramAnonymousTask.getResult());
              return;
            }
            finally
            {
              if (!paramAnonymousTask.isCancelled()) {
                break label145;
              }
            }
            ParseTaskUtils.2.this.val$tcs.setCancelled();
            for (;;)
            {
              throw ((Throwable)localObject2);
              label145:
              if (paramAnonymousTask.isFaulted()) {
                ParseTaskUtils.2.this.val$tcs.setError(paramAnonymousTask.getError());
              } else {
                ParseTaskUtils.2.this.val$tcs.setResult(paramAnonymousTask.getResult());
              }
            }
          }
        });
        return null;
      }
    });
    return localTaskCompletionSource.getTask();
  }
  
  static <T> T wait(Task<T> paramTask)
    throws ParseException
  {
    try
    {
      paramTask.waitForCompletion();
      if (!paramTask.isFaulted()) {
        break label75;
      }
      paramTask = paramTask.getError();
      if ((paramTask instanceof ParseException)) {
        throw ((ParseException)paramTask);
      }
    }
    catch (InterruptedException paramTask)
    {
      throw new RuntimeException(paramTask);
    }
    if ((paramTask instanceof AggregateException)) {
      throw new ParseException(paramTask);
    }
    if ((paramTask instanceof RuntimeException)) {
      throw ((RuntimeException)paramTask);
    }
    throw new RuntimeException(paramTask);
    label75:
    if (paramTask.isCancelled()) {
      throw new RuntimeException(new CancellationException());
    }
    paramTask = paramTask.getResult();
    return paramTask;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseTaskUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */