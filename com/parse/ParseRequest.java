package com.parse;

import android.os.Build.VERSION;
import bolts.Continuation;
import bolts.Task;
import bolts.Task.TaskCompletionSource;
import com.parse.http.ParseHttpBody;
import com.parse.http.ParseHttpRequest;
import com.parse.http.ParseHttpRequest.Builder;
import com.parse.http.ParseHttpRequest.Method;
import com.parse.http.ParseHttpResponse;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

abstract class ParseRequest<Response>
{
  private static final int CORE_POOL_SIZE;
  private static final int CPU_COUNT;
  static final long DEFAULT_INITIAL_RETRY_DELAY = 1000L;
  protected static final int DEFAULT_MAX_RETRIES = 4;
  private static final long KEEP_ALIVE_TIME = 1L;
  private static final int MAX_POOL_SIZE;
  private static final int MAX_QUEUE_SIZE = 128;
  static final ExecutorService NETWORK_EXECUTOR;
  private static ParseHttpClient defaultClient = null;
  private static long defaultInitialRetryDelay;
  private static final ThreadFactory sThreadFactory = new ThreadFactory()
  {
    private final AtomicInteger mCount = new AtomicInteger(1);
    
    public Thread newThread(Runnable paramAnonymousRunnable)
    {
      return new Thread(paramAnonymousRunnable, "ParseRequest.NETWORK_EXECUTOR-thread-" + this.mCount.getAndIncrement());
    }
  };
  private int maxRetries = 4;
  ParseHttpRequest.Method method;
  String url;
  
  static
  {
    CPU_COUNT = Runtime.getRuntime().availableProcessors();
    CORE_POOL_SIZE = CPU_COUNT * 2 + 1;
    MAX_POOL_SIZE = CPU_COUNT * 2 * 2 + 1;
    NETWORK_EXECUTOR = newThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue(128), sThreadFactory);
    defaultInitialRetryDelay = 1000L;
  }
  
  public ParseRequest(ParseHttpRequest.Method paramMethod, String paramString)
  {
    this.method = paramMethod;
    this.url = paramString;
  }
  
  public ParseRequest(String paramString)
  {
    this(ParseHttpRequest.Method.GET, paramString);
  }
  
  private Task<Response> executeAsync(ParseHttpClient paramParseHttpClient, final ParseHttpRequest paramParseHttpRequest, final int paramInt, final long paramLong, final ProgressCallback paramProgressCallback, final Task<Void> paramTask)
  {
    if ((paramTask != null) && (paramTask.isCancelled())) {
      return Task.cancelled();
    }
    sendOneRequestAsync(paramParseHttpClient, paramParseHttpRequest, paramProgressCallback).continueWithTask(new Continuation()
    {
      public Task<Response> then(final Task<Response> paramAnonymousTask)
        throws Exception
      {
        Exception localException = paramAnonymousTask.getError();
        Object localObject = paramAnonymousTask;
        if (paramAnonymousTask.isFaulted())
        {
          localObject = paramAnonymousTask;
          if ((localException instanceof ParseException))
          {
            if ((paramTask == null) || (!paramTask.isCancelled())) {
              break label46;
            }
            localObject = Task.cancelled();
          }
        }
        label46:
        do
        {
          do
          {
            return (Task<Response>)localObject;
            if (!(localException instanceof ParseRequest.ParseRequestException)) {
              break;
            }
            localObject = paramAnonymousTask;
          } while (((ParseRequest.ParseRequestException)localException).isPermanentFailure);
          localObject = paramAnonymousTask;
        } while (paramInt >= ParseRequest.this.maxRetries);
        PLog.i("com.parse.ParseRequest", "Request failed. Waiting " + paramLong + " milliseconds before attempt #" + (paramInt + 1));
        paramAnonymousTask = Task.create();
        ParseExecutors.scheduled().schedule(new Runnable()
        {
          public void run()
          {
            ParseRequest.this.executeAsync(ParseRequest.4.this.val$client, ParseRequest.4.this.val$request, ParseRequest.4.this.val$attemptsMade + 1, ParseRequest.4.this.val$delay * 2L, ParseRequest.4.this.val$downloadProgressCallback, ParseRequest.4.this.val$cancellationToken).continueWithTask(new Continuation()
            {
              public Task<Void> then(Task<Response> paramAnonymous3Task)
                throws Exception
              {
                if (paramAnonymous3Task.isCancelled()) {
                  ParseRequest.4.1.this.val$retryTask.setCancelled();
                }
                for (;;)
                {
                  return null;
                  if (paramAnonymous3Task.isFaulted()) {
                    ParseRequest.4.1.this.val$retryTask.setError(paramAnonymous3Task.getError());
                  } else {
                    ParseRequest.4.1.this.val$retryTask.setResult(paramAnonymous3Task.getResult());
                  }
                }
              }
            });
          }
        }, paramLong, TimeUnit.MILLISECONDS);
        return paramAnonymousTask.getTask();
      }
    });
  }
  
  private Task<Response> executeAsync(ParseHttpClient paramParseHttpClient, ParseHttpRequest paramParseHttpRequest, ProgressCallback paramProgressCallback, Task<Void> paramTask)
  {
    return executeAsync(paramParseHttpClient, paramParseHttpRequest, 0, defaultInitialRetryDelay + (defaultInitialRetryDelay * Math.random()), paramProgressCallback, paramTask);
  }
  
  @Deprecated
  public static ParseHttpClient getDefaultClient()
  {
    if (defaultClient == null) {
      throw new IllegalStateException("Can't send Parse HTTPS request before Parse.initialize()");
    }
    return defaultClient;
  }
  
  public static long getDefaultInitialRetryDelay()
  {
    return defaultInitialRetryDelay;
  }
  
  private static ThreadPoolExecutor newThreadPoolExecutor(int paramInt1, int paramInt2, long paramLong, TimeUnit paramTimeUnit, BlockingQueue<Runnable> paramBlockingQueue, ThreadFactory paramThreadFactory)
  {
    paramTimeUnit = new ThreadPoolExecutor(paramInt1, paramInt2, paramLong, paramTimeUnit, paramBlockingQueue, paramThreadFactory);
    if (Build.VERSION.SDK_INT >= 9) {
      paramTimeUnit.allowCoreThreadTimeOut(true);
    }
    return paramTimeUnit;
  }
  
  private Task<Response> sendOneRequestAsync(final ParseHttpClient paramParseHttpClient, final ParseHttpRequest paramParseHttpRequest, final ProgressCallback paramProgressCallback)
  {
    Task.forResult(null).onSuccessTask(new Continuation()
    {
      public Task<Response> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = paramParseHttpClient.execute(paramParseHttpRequest);
        return ParseRequest.this.onResponseAsync(paramAnonymousTask, paramProgressCallback);
      }
    }, NETWORK_EXECUTOR).continueWithTask(new Continuation()
    {
      public Task<Response> then(Task<Response> paramAnonymousTask)
        throws Exception
      {
        Object localObject = paramAnonymousTask;
        if (paramAnonymousTask.isFaulted())
        {
          Exception localException = paramAnonymousTask.getError();
          localObject = paramAnonymousTask;
          if ((localException instanceof IOException)) {
            localObject = Task.forError(ParseRequest.this.newTemporaryException("i/o failure", localException));
          }
        }
        return (Task<Response>)localObject;
      }
    }, Task.BACKGROUND_EXECUTOR);
  }
  
  @Deprecated
  public static void setDefaultClient(ParseHttpClient paramParseHttpClient)
  {
    defaultClient = paramParseHttpClient;
  }
  
  public static void setDefaultInitialRetryDelay(long paramLong)
  {
    defaultInitialRetryDelay = paramLong;
  }
  
  @Deprecated
  public Task<Response> executeAsync()
  {
    return executeAsync(getDefaultClient());
  }
  
  public Task<Response> executeAsync(ParseHttpClient paramParseHttpClient)
  {
    return executeAsync(paramParseHttpClient, (ProgressCallback)null, null, null);
  }
  
  public Task<Response> executeAsync(ParseHttpClient paramParseHttpClient, Task<Void> paramTask)
  {
    return executeAsync(paramParseHttpClient, (ProgressCallback)null, null, paramTask);
  }
  
  public Task<Response> executeAsync(ParseHttpClient paramParseHttpClient, ProgressCallback paramProgressCallback1, ProgressCallback paramProgressCallback2)
  {
    return executeAsync(paramParseHttpClient, paramProgressCallback1, paramProgressCallback2, null);
  }
  
  public Task<Response> executeAsync(ParseHttpClient paramParseHttpClient, ProgressCallback paramProgressCallback1, ProgressCallback paramProgressCallback2, Task<Void> paramTask)
  {
    return executeAsync(paramParseHttpClient, newRequest(this.method, this.url, paramProgressCallback1), paramProgressCallback2, paramTask);
  }
  
  @Deprecated
  public Task<Response> executeAsync(ProgressCallback paramProgressCallback1, ProgressCallback paramProgressCallback2, Task<Void> paramTask)
  {
    return executeAsync(getDefaultClient(), paramProgressCallback1, paramProgressCallback2, paramTask);
  }
  
  protected ParseHttpBody newBody(ProgressCallback paramProgressCallback)
  {
    return null;
  }
  
  protected ParseException newPermanentException(int paramInt, String paramString)
  {
    paramString = new ParseRequestException(paramInt, paramString);
    paramString.isPermanentFailure = true;
    return paramString;
  }
  
  protected ParseHttpRequest newRequest(ParseHttpRequest.Method paramMethod, String paramString, ProgressCallback paramProgressCallback)
  {
    paramString = new ParseHttpRequest.Builder().setMethod(paramMethod).setUrl(paramString);
    switch (paramMethod)
    {
    default: 
      throw new IllegalStateException("Invalid method " + paramMethod);
    case ???: 
    case ???: 
      paramString.setBody(newBody(paramProgressCallback));
    }
    return paramString.build();
  }
  
  protected ParseException newTemporaryException(int paramInt, String paramString)
  {
    paramString = new ParseRequestException(paramInt, paramString);
    paramString.isPermanentFailure = false;
    return paramString;
  }
  
  protected ParseException newTemporaryException(String paramString, Throwable paramThrowable)
  {
    paramString = new ParseRequestException(100, paramString, paramThrowable);
    paramString.isPermanentFailure = false;
    return paramString;
  }
  
  protected abstract Task<Response> onResponseAsync(ParseHttpResponse paramParseHttpResponse, ProgressCallback paramProgressCallback);
  
  public void setMaxRetries(int paramInt)
  {
    this.maxRetries = paramInt;
  }
  
  private static class ParseRequestException
    extends ParseException
  {
    boolean isPermanentFailure = false;
    
    public ParseRequestException(int paramInt, String paramString)
    {
      super(paramString);
    }
    
    public ParseRequestException(int paramInt, String paramString, Throwable paramThrowable)
    {
      super(paramString, paramThrowable);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */