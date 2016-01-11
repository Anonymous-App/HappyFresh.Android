package io.fabric.sdk.android.services.events;

import android.content.Context;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

public abstract class EventsHandler<T>
  implements EventsStorageListener
{
  protected final Context context;
  protected final ScheduledExecutorService executor;
  protected EventsStrategy<T> strategy;
  
  public EventsHandler(Context paramContext, EventsStrategy<T> paramEventsStrategy, EventsFilesManager paramEventsFilesManager, ScheduledExecutorService paramScheduledExecutorService)
  {
    this.context = paramContext.getApplicationContext();
    this.executor = paramScheduledExecutorService;
    this.strategy = paramEventsStrategy;
    paramEventsFilesManager.registerRollOverListener(this);
  }
  
  public void disable()
  {
    executeAsync(new Runnable()
    {
      public void run()
      {
        try
        {
          EventsStrategy localEventsStrategy = EventsHandler.this.strategy;
          EventsHandler.this.strategy = EventsHandler.this.getDisabledEventsStrategy();
          localEventsStrategy.deleteAllEvents();
          return;
        }
        catch (Exception localException)
        {
          CommonUtils.logControlledError(EventsHandler.this.context, "Failed to disable events.", localException);
        }
      }
    });
  }
  
  protected void executeAsync(Runnable paramRunnable)
  {
    try
    {
      this.executor.submit(paramRunnable);
      return;
    }
    catch (Exception paramRunnable)
    {
      CommonUtils.logControlledError(this.context, "Failed to submit events task", paramRunnable);
    }
  }
  
  protected void executeSync(Runnable paramRunnable)
  {
    try
    {
      this.executor.submit(paramRunnable).get();
      return;
    }
    catch (Exception paramRunnable)
    {
      CommonUtils.logControlledError(this.context, "Failed to run events task", paramRunnable);
    }
  }
  
  protected abstract EventsStrategy<T> getDisabledEventsStrategy();
  
  public void onRollOver(String paramString)
  {
    executeAsync(new Runnable()
    {
      public void run()
      {
        try
        {
          EventsHandler.this.strategy.sendEvents();
          return;
        }
        catch (Exception localException)
        {
          CommonUtils.logControlledError(EventsHandler.this.context, "Failed to send events files.", localException);
        }
      }
    });
  }
  
  public void recordEventAsync(final T paramT, final boolean paramBoolean)
  {
    executeAsync(new Runnable()
    {
      public void run()
      {
        try
        {
          EventsHandler.this.strategy.recordEvent(paramT);
          if (paramBoolean) {
            EventsHandler.this.strategy.rollFileOver();
          }
          return;
        }
        catch (Exception localException)
        {
          CommonUtils.logControlledError(EventsHandler.this.context, "Failed to record event.", localException);
        }
      }
    });
  }
  
  public void recordEventSync(final T paramT)
  {
    executeSync(new Runnable()
    {
      public void run()
      {
        try
        {
          EventsHandler.this.strategy.recordEvent(paramT);
          return;
        }
        catch (Exception localException)
        {
          CommonUtils.logControlledError(EventsHandler.this.context, "Crashlytics failed to record event", localException);
        }
      }
    });
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/events/EventsHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */