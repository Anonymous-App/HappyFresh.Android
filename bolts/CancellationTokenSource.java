package bolts;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class CancellationTokenSource
  implements Closeable
{
  private boolean cancellationRequested;
  private boolean closed;
  private final ScheduledExecutorService executor = BoltsExecutors.scheduled();
  private final Object lock = new Object();
  private final List<CancellationTokenRegistration> registrations = new ArrayList();
  private ScheduledFuture<?> scheduledCancellation;
  
  private void cancelAfter(long paramLong, TimeUnit paramTimeUnit)
  {
    if (paramLong < -1L) {
      throw new IllegalArgumentException("Delay must be >= -1");
    }
    if (paramLong == 0L)
    {
      cancel();
      return;
    }
    synchronized (this.lock)
    {
      if (this.cancellationRequested) {
        return;
      }
    }
    cancelScheduledCancellation();
    if (paramLong != -1L) {
      this.scheduledCancellation = this.executor.schedule(new Runnable()
      {
        public void run()
        {
          synchronized (CancellationTokenSource.this.lock)
          {
            CancellationTokenSource.access$102(CancellationTokenSource.this, null);
            CancellationTokenSource.this.cancel();
            return;
          }
        }
      }, paramLong, paramTimeUnit);
    }
  }
  
  private void cancelScheduledCancellation()
  {
    if (this.scheduledCancellation != null)
    {
      this.scheduledCancellation.cancel(true);
      this.scheduledCancellation = null;
    }
  }
  
  private void notifyListeners(List<CancellationTokenRegistration> paramList)
  {
    paramList = paramList.iterator();
    while (paramList.hasNext()) {
      ((CancellationTokenRegistration)paramList.next()).runAction();
    }
  }
  
  private void throwIfClosed()
  {
    if (this.closed) {
      throw new IllegalStateException("Object already closed");
    }
  }
  
  public void cancel()
  {
    synchronized (this.lock)
    {
      throwIfClosed();
      if (this.cancellationRequested) {
        return;
      }
      cancelScheduledCancellation();
      this.cancellationRequested = true;
      ArrayList localArrayList = new ArrayList(this.registrations);
      notifyListeners(localArrayList);
      return;
    }
  }
  
  public void cancelAfter(long paramLong)
  {
    cancelAfter(paramLong, TimeUnit.MILLISECONDS);
  }
  
  public void close()
  {
    synchronized (this.lock)
    {
      if (this.closed) {
        return;
      }
      cancelScheduledCancellation();
      Iterator localIterator = this.registrations.iterator();
      if (localIterator.hasNext()) {
        ((CancellationTokenRegistration)localIterator.next()).close();
      }
    }
    this.registrations.clear();
    this.closed = true;
  }
  
  public CancellationToken getToken()
  {
    synchronized (this.lock)
    {
      throwIfClosed();
      CancellationToken localCancellationToken = new CancellationToken(this);
      return localCancellationToken;
    }
  }
  
  public boolean isCancellationRequested()
  {
    synchronized (this.lock)
    {
      throwIfClosed();
      boolean bool = this.cancellationRequested;
      return bool;
    }
  }
  
  CancellationTokenRegistration register(Runnable paramRunnable)
  {
    synchronized (this.lock)
    {
      throwIfClosed();
      paramRunnable = new CancellationTokenRegistration(this, paramRunnable);
      if (this.cancellationRequested)
      {
        paramRunnable.runAction();
        return paramRunnable;
      }
      this.registrations.add(paramRunnable);
    }
  }
  
  void throwIfCancellationRequested()
    throws CancellationException
  {
    synchronized (this.lock)
    {
      throwIfClosed();
      if (this.cancellationRequested) {
        throw new CancellationException();
      }
    }
  }
  
  public String toString()
  {
    return String.format(Locale.US, "%s@%s[cancellationRequested=%s]", new Object[] { getClass().getName(), Integer.toHexString(hashCode()), Boolean.toString(isCancellationRequested()) });
  }
  
  void unregister(CancellationTokenRegistration paramCancellationTokenRegistration)
  {
    synchronized (this.lock)
    {
      throwIfClosed();
      this.registrations.remove(paramCancellationTokenRegistration);
      return;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/bolts/CancellationTokenSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */