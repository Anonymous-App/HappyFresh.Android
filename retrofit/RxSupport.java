package retrofit;

import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.subscriptions.Subscriptions;

final class RxSupport
{
  private final ErrorHandler errorHandler;
  private final Executor executor;
  private final RequestInterceptor requestInterceptor;
  
  RxSupport(Executor paramExecutor, ErrorHandler paramErrorHandler, RequestInterceptor paramRequestInterceptor)
  {
    this.executor = paramExecutor;
    this.errorHandler = paramErrorHandler;
    this.requestInterceptor = paramRequestInterceptor;
  }
  
  private Runnable getRunnable(final Subscriber<? super Object> paramSubscriber, final Invoker paramInvoker, final RequestInterceptorTape paramRequestInterceptorTape)
  {
    new Runnable()
    {
      public void run()
      {
        try
        {
          if (paramSubscriber.isUnsubscribed()) {
            return;
          }
          ResponseWrapper localResponseWrapper = paramInvoker.invoke(paramRequestInterceptorTape);
          paramSubscriber.onNext(localResponseWrapper.responseBody);
          paramSubscriber.onCompleted();
          return;
        }
        catch (RetrofitError localRetrofitError)
        {
          paramSubscriber.onError(RxSupport.this.errorHandler.handleError(localRetrofitError));
        }
      }
    };
  }
  
  Observable createRequestObservable(final Invoker paramInvoker)
  {
    Observable.create(new Observable.OnSubscribe()
    {
      public void call(Subscriber<? super Object> paramAnonymousSubscriber)
      {
        Object localObject = new RequestInterceptorTape();
        RxSupport.this.requestInterceptor.intercept((RequestInterceptor.RequestFacade)localObject);
        localObject = new FutureTask(RxSupport.this.getRunnable(paramAnonymousSubscriber, paramInvoker, (RequestInterceptorTape)localObject), null);
        paramAnonymousSubscriber.add(Subscriptions.from((Future)localObject));
        RxSupport.this.executor.execute((Runnable)localObject);
      }
    });
  }
  
  static abstract interface Invoker
  {
    public abstract ResponseWrapper invoke(RequestInterceptor paramRequestInterceptor);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/retrofit/RxSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */