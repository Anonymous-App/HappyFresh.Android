package com.optimizely.LogAndEvent;

import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.os.MessageQueue.IdleHandler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.optimizely.Optimizely;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DatabaseRunner
  extends Thread
  implements MessageQueue.IdleHandler, Handler.Callback
{
  @NonNull
  public static final String COMPONENT = DatabaseRunner.class.getSimpleName();
  public static final int MESSAGE_POLL_QUEUE = 1;
  public static final int MESSAGE_QUIT = 2;
  @Nullable
  private Handler handler;
  private volatile boolean isFirstIdle = true;
  private volatile boolean isIdle = false;
  private volatile boolean isQuit = false;
  public final Object monitor = new Object();
  @NonNull
  private final Optimizely optimizely;
  private final Queue<Runnable> queue = new ConcurrentLinkedQueue();
  @NonNull
  private final SQLiteOpenHelper sqLiteOpenHelper;
  private volatile int tasksHandled = 0;
  
  public DatabaseRunner(@NonNull Optimizely paramOptimizely, @NonNull SQLiteOpenHelper paramSQLiteOpenHelper)
  {
    super("OptimizelyDbThread");
    this.optimizely = paramOptimizely;
    this.sqLiteOpenHelper = paramSQLiteOpenHelper;
  }
  
  private void replayQueue()
  {
    while (!this.queue.isEmpty())
    {
      ((Runnable)this.queue.poll()).run();
      this.tasksHandled += 1;
    }
  }
  
  private void sendPollQueueMsg(@NonNull Handler paramHandler)
  {
    try
    {
      paramHandler.sendEmptyMessage(1);
      return;
    }
    catch (RuntimeException paramHandler)
    {
      this.optimizely.verboseLog(COMPONENT, "Polling queue on dead database thread", new Object[0]);
    }
  }
  
  public Queue<Runnable> getQueue()
  {
    return this.queue;
  }
  
  public int getTasksHandled()
  {
    return this.tasksHandled;
  }
  
  public boolean handleMessage(Message paramMessage)
  {
    switch (paramMessage.what)
    {
    }
    do
    {
      return true;
      replayQueue();
      return true;
      replayQueue();
      paramMessage = Looper.myLooper();
    } while (paramMessage == null);
    paramMessage.quit();
    this.isQuit = true;
    return true;
  }
  
  public boolean isIdle()
  {
    return this.isIdle;
  }
  
  public boolean isQuit()
  {
    return this.isQuit;
  }
  
  public boolean isReady()
  {
    return this.handler != null;
  }
  
  public boolean post(Runnable paramRunnable)
  {
    this.queue.add(paramRunnable);
    if (this.handler != null) {
      sendPollQueueMsg(this.handler);
    }
    return true;
  }
  
  public boolean queueIdle()
  {
    if (this.isFirstIdle)
    {
      this.isFirstIdle = false;
      return true;
    }
    this.isIdle = true;
    return true;
  }
  
  public void quit()
  {
    if (this.handler != null) {}
    try
    {
      this.handler.sendEmptyMessage(2);
      return;
    }
    catch (RuntimeException localRuntimeException)
    {
      this.optimizely.verboseLog(COMPONENT, "Trying to quit an already dead database thread", new Object[0]);
    }
  }
  
  public void run()
  {
    Looper.prepare();
    this.handler = new Handler(this);
    synchronized (this.monitor)
    {
      this.monitor.notifyAll();
      Looper.myQueue().addIdleHandler(this);
      sendPollQueueMsg(this.handler);
      Looper.loop();
      return;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/LogAndEvent/DatabaseRunner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */