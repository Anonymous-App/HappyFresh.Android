package com.google.android.gms.common.api;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.common.internal.ICancelToken;
import com.google.android.gms.common.internal.zzu;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public abstract class AbstractPendingResult<R extends Result>
  implements PendingResult<R>
{
  protected final CallbackHandler<R> mHandler;
  private boolean zzL;
  private final Object zzWb = new Object();
  private final ArrayList<PendingResult.BatchCallback> zzWc = new ArrayList();
  private ResultCallback<R> zzWd;
  private volatile R zzWe;
  private volatile boolean zzWf;
  private boolean zzWg;
  private ICancelToken zzWh;
  private final CountDownLatch zzoD = new CountDownLatch(1);
  
  protected AbstractPendingResult(Looper paramLooper)
  {
    this.mHandler = new CallbackHandler(paramLooper);
  }
  
  protected AbstractPendingResult(CallbackHandler<R> paramCallbackHandler)
  {
    this.mHandler = ((CallbackHandler)zzu.zzb(paramCallbackHandler, "CallbackHandler must not be null"));
  }
  
  private void zza(R paramR)
  {
    this.zzWe = paramR;
    this.zzWh = null;
    this.zzoD.countDown();
    paramR = this.zzWe.getStatus();
    if (this.zzWd != null)
    {
      this.mHandler.removeTimeoutMessages();
      if (!this.zzL) {
        this.mHandler.sendResultCallback(this.zzWd, zzmo());
      }
    }
    Iterator localIterator = this.zzWc.iterator();
    while (localIterator.hasNext()) {
      ((PendingResult.BatchCallback)localIterator.next()).zzs(paramR);
    }
    this.zzWc.clear();
  }
  
  static void zzb(Result paramResult)
  {
    if ((paramResult instanceof Releasable)) {}
    try
    {
      ((Releasable)paramResult).release();
      return;
    }
    catch (RuntimeException localRuntimeException)
    {
      Log.w("AbstractPendingResult", "Unable to release " + paramResult, localRuntimeException);
    }
  }
  
  private R zzmo()
  {
    boolean bool = true;
    synchronized (this.zzWb)
    {
      if (!this.zzWf)
      {
        zzu.zza(bool, "Result has already been consumed.");
        zzu.zza(isReady(), "Result is not ready.");
        Result localResult = this.zzWe;
        this.zzWe = null;
        this.zzWd = null;
        this.zzWf = true;
        onResultConsumed();
        return localResult;
      }
      bool = false;
    }
  }
  
  public final void addBatchCallback(PendingResult.BatchCallback paramBatchCallback)
  {
    if (!this.zzWf) {}
    for (boolean bool = true;; bool = false)
    {
      zzu.zza(bool, "Result has already been consumed.");
      synchronized (this.zzWb)
      {
        if (isReady())
        {
          paramBatchCallback.zzs(this.zzWe.getStatus());
          return;
        }
        this.zzWc.add(paramBatchCallback);
      }
    }
  }
  
  public final R await()
  {
    boolean bool2 = true;
    boolean bool1;
    if (Looper.myLooper() != Looper.getMainLooper()) {
      bool1 = true;
    }
    for (;;)
    {
      zzu.zza(bool1, "await must not be called on the UI thread");
      if (!this.zzWf)
      {
        bool1 = bool2;
        zzu.zza(bool1, "Result has already been consumed");
      }
      try
      {
        this.zzoD.await();
        zzu.zza(isReady(), "Result is not ready.");
        return zzmo();
        bool1 = false;
        continue;
        bool1 = false;
      }
      catch (InterruptedException localInterruptedException)
      {
        for (;;)
        {
          forceFailureUnlessReady(Status.zzXQ);
        }
      }
    }
  }
  
  public final R await(long paramLong, TimeUnit paramTimeUnit)
  {
    boolean bool2 = true;
    boolean bool1;
    if ((paramLong <= 0L) || (Looper.myLooper() != Looper.getMainLooper())) {
      bool1 = true;
    }
    for (;;)
    {
      zzu.zza(bool1, "await must not be called on the UI thread when time is greater than zero.");
      if (!this.zzWf)
      {
        bool1 = bool2;
        zzu.zza(bool1, "Result has already been consumed.");
      }
      try
      {
        if (!this.zzoD.await(paramLong, paramTimeUnit)) {
          forceFailureUnlessReady(Status.zzXS);
        }
        zzu.zza(isReady(), "Result is not ready.");
        return zzmo();
        bool1 = false;
        continue;
        bool1 = false;
      }
      catch (InterruptedException paramTimeUnit)
      {
        for (;;)
        {
          forceFailureUnlessReady(Status.zzXQ);
        }
      }
    }
  }
  
  public void cancel()
  {
    synchronized (this.zzWb)
    {
      if ((this.zzL) || (this.zzWf)) {
        return;
      }
      ICancelToken localICancelToken = this.zzWh;
      if (localICancelToken == null) {}
    }
    try
    {
      this.zzWh.cancel();
      zzb(this.zzWe);
      this.zzWd = null;
      this.zzL = true;
      zza(createFailedResult(Status.zzXT));
      return;
      localObject2 = finally;
      throw ((Throwable)localObject2);
    }
    catch (RemoteException localRemoteException)
    {
      for (;;) {}
    }
  }
  
  protected abstract R createFailedResult(Status paramStatus);
  
  public final void forceFailureUnlessReady(Status paramStatus)
  {
    synchronized (this.zzWb)
    {
      if (!isReady())
      {
        setResult(createFailedResult(paramStatus));
        this.zzWg = true;
      }
      return;
    }
  }
  
  public boolean isCanceled()
  {
    synchronized (this.zzWb)
    {
      boolean bool = this.zzL;
      return bool;
    }
  }
  
  public final boolean isReady()
  {
    return this.zzoD.getCount() == 0L;
  }
  
  protected void onResultConsumed() {}
  
  protected final void setCancelToken(ICancelToken paramICancelToken)
  {
    synchronized (this.zzWb)
    {
      this.zzWh = paramICancelToken;
      return;
    }
  }
  
  public final void setResult(R paramR)
  {
    boolean bool2 = true;
    for (;;)
    {
      synchronized (this.zzWb)
      {
        if ((this.zzWg) || (this.zzL))
        {
          zzb(paramR);
          return;
        }
        if (!isReady())
        {
          bool1 = true;
          zzu.zza(bool1, "Results have already been set");
          if (this.zzWf) {
            break label83;
          }
          bool1 = bool2;
          zzu.zza(bool1, "Result has already been consumed");
          zza(paramR);
          return;
        }
      }
      boolean bool1 = false;
      continue;
      label83:
      bool1 = false;
    }
  }
  
  public final void setResultCallback(ResultCallback<R> paramResultCallback)
  {
    if (!this.zzWf) {}
    for (boolean bool = true;; bool = false)
    {
      zzu.zza(bool, "Result has already been consumed.");
      for (;;)
      {
        synchronized (this.zzWb)
        {
          if (isCanceled()) {
            return;
          }
          if (isReady())
          {
            this.mHandler.sendResultCallback(paramResultCallback, zzmo());
            return;
          }
        }
        this.zzWd = paramResultCallback;
      }
    }
  }
  
  public final void setResultCallback(ResultCallback<R> paramResultCallback, long paramLong, TimeUnit paramTimeUnit)
  {
    if (!this.zzWf) {}
    for (boolean bool = true;; bool = false)
    {
      zzu.zza(bool, "Result has already been consumed.");
      for (;;)
      {
        synchronized (this.zzWb)
        {
          if (isCanceled()) {
            return;
          }
          if (isReady())
          {
            this.mHandler.sendResultCallback(paramResultCallback, zzmo());
            return;
          }
        }
        this.zzWd = paramResultCallback;
        this.mHandler.sendTimeoutResultCallback(this, paramTimeUnit.toMillis(paramLong));
      }
    }
  }
  
  public static class CallbackHandler<R extends Result>
    extends Handler
  {
    public static final int CALLBACK_ON_COMPLETE = 1;
    public static final int CALLBACK_ON_TIMEOUT = 2;
    
    public CallbackHandler()
    {
      this(Looper.getMainLooper());
    }
    
    public CallbackHandler(Looper paramLooper)
    {
      super();
    }
    
    protected void deliverResultCallback(ResultCallback<R> paramResultCallback, R paramR)
    {
      try
      {
        paramResultCallback.onResult(paramR);
        return;
      }
      catch (RuntimeException paramResultCallback)
      {
        AbstractPendingResult.zzb(paramR);
        throw paramResultCallback;
      }
    }
    
    public void handleMessage(Message paramMessage)
    {
      switch (paramMessage.what)
      {
      default: 
        Log.wtf("AbstractPendingResult", "Don't know how to handle this message.");
        return;
      case 1: 
        paramMessage = (Pair)paramMessage.obj;
        deliverResultCallback((ResultCallback)paramMessage.first, (Result)paramMessage.second);
        return;
      }
      ((AbstractPendingResult)paramMessage.obj).forceFailureUnlessReady(Status.zzXS);
    }
    
    public void removeTimeoutMessages()
    {
      removeMessages(2);
    }
    
    public void sendResultCallback(ResultCallback<R> paramResultCallback, R paramR)
    {
      sendMessage(obtainMessage(1, new Pair(paramResultCallback, paramR)));
    }
    
    public void sendTimeoutResultCallback(AbstractPendingResult<R> paramAbstractPendingResult, long paramLong)
    {
      sendMessageDelayed(obtainMessage(2, paramAbstractPendingResult), paramLong);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/api/AbstractPendingResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */