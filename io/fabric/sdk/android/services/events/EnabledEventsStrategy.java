package io.fabric.sdk.android.services.events;

import android.content.Context;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public abstract class EnabledEventsStrategy<T>
  implements EventsStrategy<T>
{
  static final int UNDEFINED_ROLLOVER_INTERVAL_SECONDS = -1;
  protected final Context context;
  final ScheduledExecutorService executorService;
  protected final EventsFilesManager<T> filesManager;
  volatile int rolloverIntervalSeconds = -1;
  final AtomicReference<ScheduledFuture<?>> scheduledRolloverFutureRef;
  
  public EnabledEventsStrategy(Context paramContext, ScheduledExecutorService paramScheduledExecutorService, EventsFilesManager<T> paramEventsFilesManager)
  {
    this.context = paramContext;
    this.executorService = paramScheduledExecutorService;
    this.filesManager = paramEventsFilesManager;
    this.scheduledRolloverFutureRef = new AtomicReference();
  }
  
  public void cancelTimeBasedFileRollOver()
  {
    if (this.scheduledRolloverFutureRef.get() != null)
    {
      CommonUtils.logControlled(this.context, "Cancelling time-based rollover because no events are currently being generated.");
      ((ScheduledFuture)this.scheduledRolloverFutureRef.get()).cancel(false);
      this.scheduledRolloverFutureRef.set(null);
    }
  }
  
  protected void configureRollover(int paramInt)
  {
    this.rolloverIntervalSeconds = paramInt;
    scheduleTimeBasedFileRollOver(0L, this.rolloverIntervalSeconds);
  }
  
  public void deleteAllEvents()
  {
    this.filesManager.deleteAllEventsFiles();
  }
  
  public int getRollover()
  {
    return this.rolloverIntervalSeconds;
  }
  
  public void recordEvent(T paramT)
  {
    CommonUtils.logControlled(this.context, paramT.toString());
    try
    {
      this.filesManager.writeEvent(paramT);
      scheduleTimeBasedRollOverIfNeeded();
      return;
    }
    catch (IOException paramT)
    {
      for (;;)
      {
        CommonUtils.logControlledError(this.context, "Failed to write event.", paramT);
      }
    }
  }
  
  public boolean rollFileOver()
  {
    try
    {
      boolean bool = this.filesManager.rollFileOver();
      return bool;
    }
    catch (IOException localIOException)
    {
      CommonUtils.logControlledError(this.context, "Failed to roll file over.", localIOException);
    }
    return false;
  }
  
  void scheduleTimeBasedFileRollOver(long paramLong1, long paramLong2)
  {
    if (this.scheduledRolloverFutureRef.get() == null) {}
    for (int i = 1;; i = 0)
    {
      TimeBasedFileRollOverRunnable localTimeBasedFileRollOverRunnable;
      if (i != 0)
      {
        localTimeBasedFileRollOverRunnable = new TimeBasedFileRollOverRunnable(this.context, this);
        CommonUtils.logControlled(this.context, "Scheduling time based file roll over every " + paramLong2 + " seconds");
      }
      try
      {
        this.scheduledRolloverFutureRef.set(this.executorService.scheduleAtFixedRate(localTimeBasedFileRollOverRunnable, paramLong1, paramLong2, TimeUnit.SECONDS));
        return;
      }
      catch (RejectedExecutionException localRejectedExecutionException)
      {
        CommonUtils.logControlledError(this.context, "Failed to schedule time based file roll over", localRejectedExecutionException);
      }
    }
  }
  
  public void scheduleTimeBasedRollOverIfNeeded()
  {
    if (this.rolloverIntervalSeconds != -1) {}
    for (int i = 1;; i = 0)
    {
      if (i != 0) {
        scheduleTimeBasedFileRollOver(this.rolloverIntervalSeconds, this.rolloverIntervalSeconds);
      }
      return;
    }
  }
  
  void sendAndCleanUpIfSuccess()
  {
    FilesSender localFilesSender = getFilesSender();
    if (localFilesSender == null)
    {
      CommonUtils.logControlled(this.context, "skipping files send because we don't yet know the target endpoint");
      return;
    }
    CommonUtils.logControlled(this.context, "Sending all files");
    int j = 0;
    List localList = this.filesManager.getBatchOfFilesToSend();
    for (;;)
    {
      k = j;
      i = j;
      try
      {
        if (localList.size() > 0)
        {
          i = j;
          CommonUtils.logControlled(this.context, String.format(Locale.US, "attempt to send batch of %d files", new Object[] { Integer.valueOf(localList.size()) }));
          i = j;
          boolean bool = localFilesSender.send(localList);
          k = j;
          if (bool)
          {
            i = j;
            k = j + localList.size();
            i = k;
            this.filesManager.deleteSentFiles(localList);
          }
          if (bool) {
            break label149;
          }
        }
      }
      catch (Exception localException)
      {
        for (;;)
        {
          CommonUtils.logControlledError(this.context, "Failed to send batch of analytics files to server: " + localException.getMessage(), localException);
          k = i;
        }
      }
      if (k != 0) {
        break;
      }
      this.filesManager.deleteOldestInRollOverIfOverMax();
      return;
      label149:
      i = k;
      localList = this.filesManager.getBatchOfFilesToSend();
      j = k;
    }
  }
  
  public void sendEvents()
  {
    sendAndCleanUpIfSuccess();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/events/EnabledEventsStrategy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */