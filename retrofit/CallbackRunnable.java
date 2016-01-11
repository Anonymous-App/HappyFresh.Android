package retrofit;

import java.util.concurrent.Executor;

abstract class CallbackRunnable<T>
  implements Runnable
{
  private final Callback<T> callback;
  private final Executor callbackExecutor;
  private final ErrorHandler errorHandler;
  
  CallbackRunnable(Callback<T> paramCallback, Executor paramExecutor, ErrorHandler paramErrorHandler)
  {
    this.callback = paramCallback;
    this.callbackExecutor = paramExecutor;
    this.errorHandler = paramErrorHandler;
  }
  
  public abstract ResponseWrapper obtainResponse();
  
  public final void run()
  {
    Throwable localThrowable;
    try
    {
      final ResponseWrapper localResponseWrapper = obtainResponse();
      this.callbackExecutor.execute(new Runnable()
      {
        public void run()
        {
          CallbackRunnable.this.callback.success(localResponseWrapper.responseBody, localResponseWrapper.response);
        }
      });
      return;
    }
    catch (RetrofitError localRetrofitError1)
    {
      localThrowable = this.errorHandler.handleError(localRetrofitError1);
      if (localThrowable != localRetrofitError1) {}
    }
    for (;;)
    {
      this.callbackExecutor.execute(new Runnable()
      {
        public void run()
        {
          CallbackRunnable.this.callback.failure(localRetrofitError1);
        }
      });
      return;
      RetrofitError localRetrofitError2 = RetrofitError.unexpectedError(localRetrofitError1.getUrl(), localThrowable);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/retrofit/CallbackRunnable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */