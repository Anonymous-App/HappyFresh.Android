package com.parse;

import android.content.Context;
import android.content.Intent;
import bolts.Continuation;
import bolts.Task;
import bolts.Task.TaskCompletionSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.json.JSONObject;

class ParsePinningEventuallyQueue
  extends ParseEventuallyQueue
{
  private static final String TAG = "ParsePinningEventuallyQueue";
  private final Object connectionLock = new Object();
  private Task<Void>.TaskCompletionSource connectionTaskCompletionSource = Task.create();
  private ArrayList<String> eventuallyPinUUIDQueue = new ArrayList();
  private ConnectivityNotifier.ConnectivityListener listener = new ConnectivityNotifier.ConnectivityListener()
  {
    public void networkConnectivityStatusChanged(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      if (paramAnonymousIntent.getBooleanExtra("noConnectivity", false))
      {
        ParsePinningEventuallyQueue.this.setConnected(false);
        return;
      }
      ParsePinningEventuallyQueue.this.setConnected(ConnectivityNotifier.isConnected(paramAnonymousContext));
    }
  };
  private ConnectivityNotifier notifier;
  private TaskQueue operationSetTaskQueue = new TaskQueue();
  private HashMap<String, Task<JSONObject>.TaskCompletionSource> pendingEventuallyTasks = new HashMap();
  private HashMap<String, Task<JSONObject>.TaskCompletionSource> pendingOperationSetUUIDTasks = new HashMap();
  private TaskQueue taskQueue = new TaskQueue();
  private final Object taskQueueSyncLock = new Object();
  private HashMap<String, EventuallyPin> uuidToEventuallyPin = new HashMap();
  private HashMap<String, ParseOperationSet> uuidToOperationSet = new HashMap();
  
  public ParsePinningEventuallyQueue(Context paramContext)
  {
    setConnected(ConnectivityNotifier.isConnected(paramContext));
    this.notifier = ConnectivityNotifier.getNotifier(paramContext);
    this.notifier.addListener(this.listener);
    resume();
  }
  
  private Task<Void> enqueueEventuallyAsync(final ParseRESTCommand paramParseRESTCommand, final ParseObject paramParseObject, Task<Void> paramTask, final Task<JSONObject>.TaskCompletionSource paramTask1)
  {
    paramTask.continueWithTask(new Continuation()
    {
      public Task<Void> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        EventuallyPin.pinEventuallyCommand(paramParseObject, paramParseRESTCommand).continueWithTask(new Continuation()
        {
          public Task<Void> then(Task<EventuallyPin> paramAnonymous2Task)
            throws Exception
          {
            EventuallyPin localEventuallyPin = (EventuallyPin)paramAnonymous2Task.getResult();
            Exception localException = paramAnonymous2Task.getError();
            if (localException != null)
            {
              if (5 >= Parse.getLogLevel()) {
                PLog.w("ParsePinningEventuallyQueue", "Unable to save command for later.", localException);
              }
              ParsePinningEventuallyQueue.this.notifyTestHelper(4);
              return Task.forResult(null);
            }
            ParsePinningEventuallyQueue.this.pendingOperationSetUUIDTasks.put(localEventuallyPin.getUUID(), ParsePinningEventuallyQueue.5.this.val$tcs);
            ParsePinningEventuallyQueue.this.populateQueueAsync().continueWithTask(new Continuation()
            {
              public Task<Void> then(Task<Void> paramAnonymous3Task)
                throws Exception
              {
                ParsePinningEventuallyQueue.this.notifyTestHelper(3);
                return paramAnonymous3Task;
              }
            });
            return paramAnonymous2Task.makeVoid();
          }
        });
      }
    });
  }
  
  private Task<Void> populateQueueAsync()
  {
    this.taskQueue.enqueue(new Continuation()
    {
      public Task<Void> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        return ParsePinningEventuallyQueue.this.populateQueueAsync(paramAnonymousTask);
      }
    });
  }
  
  private Task<Void> populateQueueAsync(Task<Void> paramTask)
  {
    paramTask.continueWithTask(new Continuation()
    {
      public Task<List<EventuallyPin>> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        return EventuallyPin.findAllPinned(ParsePinningEventuallyQueue.this.eventuallyPinUUIDQueue);
      }
    }).onSuccessTask(new Continuation()
    {
      public Task<Void> then(Task<List<EventuallyPin>> paramAnonymousTask)
        throws Exception
      {
        Iterator localIterator = ((List)paramAnonymousTask.getResult()).iterator();
        while (localIterator.hasNext())
        {
          EventuallyPin localEventuallyPin = (EventuallyPin)localIterator.next();
          ParsePinningEventuallyQueue.this.runEventuallyAsync(localEventuallyPin);
        }
        return paramAnonymousTask.makeVoid();
      }
    });
  }
  
  private Task<JSONObject> process(final EventuallyPin paramEventuallyPin, final ParseOperationSet paramParseOperationSet)
  {
    waitForConnectionAsync().onSuccessTask(new Continuation()
    {
      public Task<JSONObject> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        final int i = paramEventuallyPin.getType();
        final ParseObject localParseObject = paramEventuallyPin.getObject();
        paramAnonymousTask = paramEventuallyPin.getSessionToken();
        if (i == 1) {
          paramAnonymousTask = localParseObject.saveAsync(paramParseOperationSet, paramAnonymousTask);
        }
        for (;;)
        {
          paramAnonymousTask.continueWithTask(new Continuation()
          {
            public Task<JSONObject> then(final Task<JSONObject> paramAnonymous2Task)
              throws Exception
            {
              Exception localException = paramAnonymous2Task.getError();
              if ((localException != null) && ((localException instanceof ParseException)) && (((ParseException)localException).getCode() == 100))
              {
                ParsePinningEventuallyQueue.this.setConnected(false);
                ParsePinningEventuallyQueue.this.notifyTestHelper(7);
                return ParsePinningEventuallyQueue.this.process(ParsePinningEventuallyQueue.13.this.val$eventuallyPin, ParsePinningEventuallyQueue.13.this.val$operationSet);
              }
              ParsePinningEventuallyQueue.13.this.val$eventuallyPin.unpinInBackground("_eventuallyPin").continueWithTask(new Continuation()
              {
                public Task<Void> then(Task<Void> paramAnonymous3Task)
                  throws Exception
                {
                  Object localObject = (JSONObject)paramAnonymous2Task.getResult();
                  if (ParsePinningEventuallyQueue.13.1.this.val$type == 1) {
                    localObject = ParsePinningEventuallyQueue.13.1.this.val$object.handleSaveEventuallyResultAsync((JSONObject)localObject, ParsePinningEventuallyQueue.13.this.val$operationSet);
                  }
                  do
                  {
                    do
                    {
                      return (Task<Void>)localObject;
                      localObject = paramAnonymous3Task;
                    } while (ParsePinningEventuallyQueue.13.1.this.val$type != 2);
                    localObject = paramAnonymous3Task;
                  } while (paramAnonymous2Task.isFaulted());
                  return ParsePinningEventuallyQueue.13.1.this.val$object.handleDeleteEventuallyResultAsync();
                }
              }).continueWithTask(new Continuation()
              {
                public Task<JSONObject> then(Task<Void> paramAnonymous3Task)
                  throws Exception
                {
                  return paramAnonymous2Task;
                }
              });
            }
          });
          if (i == 2)
          {
            paramAnonymousTask = localParseObject.deleteAsync(paramAnonymousTask).cast();
          }
          else
          {
            paramAnonymousTask = paramEventuallyPin.getCommand();
            if (paramAnonymousTask == null)
            {
              paramAnonymousTask = Task.forResult(null);
              ParsePinningEventuallyQueue.this.notifyTestHelper(8);
            }
            else
            {
              paramAnonymousTask = paramAnonymousTask.executeAsync();
            }
          }
        }
      }
    });
  }
  
  private Task<Void> runEventuallyAsync(final EventuallyPin paramEventuallyPin)
  {
    final String str = paramEventuallyPin.getUUID();
    if (this.eventuallyPinUUIDQueue.contains(str)) {
      return Task.forResult(null);
    }
    this.eventuallyPinUUIDQueue.add(str);
    this.operationSetTaskQueue.enqueue(new Continuation()
    {
      public Task<Void> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        ParsePinningEventuallyQueue.this.runEventuallyAsync(paramEventuallyPin, paramAnonymousTask).continueWithTask(new Continuation()
        {
          public Task<Void> then(Task<Void> paramAnonymous2Task)
            throws Exception
          {
            ParsePinningEventuallyQueue.this.eventuallyPinUUIDQueue.remove(ParsePinningEventuallyQueue.9.this.val$uuid);
            return paramAnonymous2Task;
          }
        });
      }
    });
    return Task.forResult(null);
  }
  
  private Task<Void> runEventuallyAsync(final EventuallyPin paramEventuallyPin, Task<Void> paramTask)
  {
    paramTask.continueWithTask(new Continuation()
    {
      public Task<Void> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        return ParsePinningEventuallyQueue.this.waitForConnectionAsync();
      }
    }).onSuccessTask(new Continuation()
    {
      public Task<Void> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        ParsePinningEventuallyQueue.this.waitForOperationSetAndEventuallyPin(null, paramEventuallyPin).continueWithTask(new Continuation()
        {
          public Task<Void> then(Task<JSONObject> paramAnonymous2Task)
            throws Exception
          {
            Exception localException = paramAnonymous2Task.getError();
            Task.TaskCompletionSource localTaskCompletionSource;
            if (localException != null)
            {
              if ((localException instanceof ParsePinningEventuallyQueue.PauseException)) {
                return paramAnonymous2Task.makeVoid();
              }
              if (6 >= Parse.getLogLevel()) {
                PLog.e("ParsePinningEventuallyQueue", "Failed to run command.", localException);
              }
              ParsePinningEventuallyQueue.this.notifyTestHelper(2, localException);
              localTaskCompletionSource = (Task.TaskCompletionSource)ParsePinningEventuallyQueue.this.pendingOperationSetUUIDTasks.remove(ParsePinningEventuallyQueue.10.this.val$eventuallyPin.getUUID());
              if (localTaskCompletionSource != null)
              {
                if (localException == null) {
                  break label108;
                }
                localTaskCompletionSource.setError(localException);
              }
            }
            for (;;)
            {
              return paramAnonymous2Task.makeVoid();
              ParsePinningEventuallyQueue.this.notifyTestHelper(1);
              break;
              label108:
              localTaskCompletionSource.setResult(paramAnonymous2Task.getResult());
            }
          }
        });
      }
    });
  }
  
  private Task<Void> waitForConnectionAsync()
  {
    synchronized (this.connectionLock)
    {
      Task localTask = this.connectionTaskCompletionSource.getTask();
      return localTask;
    }
  }
  
  private Task<Void> whenAll(Collection<TaskQueue> paramCollection)
  {
    ArrayList localArrayList = new ArrayList();
    paramCollection = paramCollection.iterator();
    while (paramCollection.hasNext()) {
      localArrayList.add(((TaskQueue)paramCollection.next()).enqueue(new Continuation()
      {
        public Task<Void> then(Task<Void> paramAnonymousTask)
          throws Exception
        {
          return paramAnonymousTask;
        }
      }));
    }
    return Task.whenAll(localArrayList);
  }
  
  public void clear()
  {
    pause();
    Task localTask = this.taskQueue.enqueue(new Continuation()
    {
      public Task<Void> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask.continueWithTask(new Continuation()
        {
          public Task<Void> then(Task<Void> paramAnonymous2Task)
            throws Exception
          {
            EventuallyPin.findAllPinned().onSuccessTask(new Continuation()
            {
              public Task<Void> then(Task<List<EventuallyPin>> paramAnonymous3Task)
                throws Exception
              {
                Object localObject = (List)paramAnonymous3Task.getResult();
                paramAnonymous3Task = new ArrayList();
                localObject = ((List)localObject).iterator();
                while (((Iterator)localObject).hasNext()) {
                  paramAnonymous3Task.add(((EventuallyPin)((Iterator)localObject).next()).unpinInBackground("_eventuallyPin"));
                }
                return Task.whenAll(paramAnonymous3Task);
              }
            });
          }
        });
      }
    });
    try
    {
      ParseTaskUtils.wait(localTask);
      simulateReboot();
      resume();
      return;
    }
    catch (ParseException localParseException)
    {
      throw new IllegalStateException(localParseException);
    }
  }
  
  public Task<JSONObject> enqueueEventuallyAsync(final ParseRESTCommand paramParseRESTCommand, final ParseObject paramParseObject)
  {
    Parse.requirePermission("android.permission.ACCESS_NETWORK_STATE");
    final Task.TaskCompletionSource localTaskCompletionSource = Task.create();
    this.taskQueue.enqueue(new Continuation()
    {
      public Task<Void> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        return ParsePinningEventuallyQueue.this.enqueueEventuallyAsync(paramParseRESTCommand, paramParseObject, paramAnonymousTask, localTaskCompletionSource);
      }
    });
    return localTaskCompletionSource.getTask();
  }
  
  public void onDestroy()
  {
    this.notifier.removeListener(this.listener);
  }
  
  public void pause()
  {
    synchronized (this.connectionLock)
    {
      this.connectionTaskCompletionSource.trySetError(new PauseException(null));
      this.connectionTaskCompletionSource = Task.create();
      this.connectionTaskCompletionSource.trySetError(new PauseException(null));
      synchronized (this.taskQueueSyncLock)
      {
        Iterator localIterator = this.pendingEventuallyTasks.keySet().iterator();
        if (localIterator.hasNext())
        {
          String str = (String)localIterator.next();
          ((Task.TaskCompletionSource)this.pendingEventuallyTasks.get(str)).trySetError(new PauseException(null));
        }
      }
    }
    this.pendingEventuallyTasks.clear();
    this.uuidToOperationSet.clear();
    this.uuidToEventuallyPin.clear();
    try
    {
      ParseTaskUtils.wait(whenAll(Arrays.asList(new TaskQueue[] { this.taskQueue, this.operationSetTaskQueue })));
      return;
    }
    catch (ParseException localParseException)
    {
      throw new IllegalStateException(localParseException);
    }
  }
  
  public int pendingCount()
  {
    try
    {
      int i = ((Integer)ParseTaskUtils.wait(pendingCountAsync())).intValue();
      return i;
    }
    catch (ParseException localParseException)
    {
      throw new IllegalStateException(localParseException);
    }
  }
  
  public Task<Integer> pendingCountAsync()
  {
    final Task.TaskCompletionSource localTaskCompletionSource = Task.create();
    this.taskQueue.enqueue(new Continuation()
    {
      public Task<Void> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        ParsePinningEventuallyQueue.this.pendingCountAsync(paramAnonymousTask).continueWithTask(new Continuation()
        {
          public Task<Void> then(Task<Integer> paramAnonymous2Task)
            throws Exception
          {
            int i = ((Integer)paramAnonymous2Task.getResult()).intValue();
            ParsePinningEventuallyQueue.2.this.val$tcs.setResult(Integer.valueOf(i));
            return Task.forResult(null);
          }
        });
      }
    });
    return localTaskCompletionSource.getTask();
  }
  
  public Task<Integer> pendingCountAsync(Task<Void> paramTask)
  {
    paramTask.continueWithTask(new Continuation()
    {
      public Task<Integer> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        EventuallyPin.findAllPinned().continueWithTask(new Continuation()
        {
          public Task<Integer> then(Task<List<EventuallyPin>> paramAnonymous2Task)
            throws Exception
          {
            return Task.forResult(Integer.valueOf(((List)paramAnonymous2Task.getResult()).size()));
          }
        });
      }
    });
  }
  
  public void resume()
  {
    if (isConnected())
    {
      this.connectionTaskCompletionSource.trySetResult(null);
      this.connectionTaskCompletionSource = Task.create();
      this.connectionTaskCompletionSource.trySetResult(null);
    }
    for (;;)
    {
      populateQueueAsync();
      return;
      this.connectionTaskCompletionSource = Task.create();
    }
  }
  
  public void setConnected(boolean paramBoolean)
  {
    synchronized (this.connectionLock)
    {
      if (isConnected() != paramBoolean)
      {
        super.setConnected(paramBoolean);
        if (paramBoolean)
        {
          this.connectionTaskCompletionSource.trySetResult(null);
          this.connectionTaskCompletionSource = Task.create();
          this.connectionTaskCompletionSource.trySetResult(null);
        }
      }
      else
      {
        return;
      }
      this.connectionTaskCompletionSource = Task.create();
    }
  }
  
  void simulateReboot()
  {
    pause();
    this.pendingOperationSetUUIDTasks.clear();
    this.pendingEventuallyTasks.clear();
    this.uuidToOperationSet.clear();
    this.uuidToEventuallyPin.clear();
    resume();
  }
  
  Task<JSONObject> waitForOperationSetAndEventuallyPin(final ParseOperationSet paramParseOperationSet, EventuallyPin paramEventuallyPin)
  {
    if ((paramEventuallyPin != null) && (paramEventuallyPin.getType() != 1)) {
      return process(paramEventuallyPin, null);
    }
    Object localObject = this.taskQueueSyncLock;
    if ((paramParseOperationSet != null) && (paramEventuallyPin == null)) {}
    ParseOperationSet localParseOperationSet;
    for (;;)
    {
      try
      {
        paramEventuallyPin = paramParseOperationSet.getUUID();
        this.uuidToOperationSet.put(paramEventuallyPin, paramParseOperationSet);
        paramParseOperationSet = paramEventuallyPin;
        paramEventuallyPin = (EventuallyPin)this.uuidToEventuallyPin.get(paramParseOperationSet);
        localParseOperationSet = (ParseOperationSet)this.uuidToOperationSet.get(paramParseOperationSet);
        if ((paramEventuallyPin != null) && (localParseOperationSet != null)) {
          break;
        }
        if (!this.pendingEventuallyTasks.containsKey(paramParseOperationSet)) {
          break label159;
        }
        paramParseOperationSet = (Task.TaskCompletionSource)this.pendingEventuallyTasks.get(paramParseOperationSet);
        paramParseOperationSet = paramParseOperationSet.getTask();
        return paramParseOperationSet;
      }
      finally {}
      if ((paramParseOperationSet == null) && (paramEventuallyPin != null))
      {
        paramParseOperationSet = paramEventuallyPin.getOperationSetUUID();
        this.uuidToEventuallyPin.put(paramParseOperationSet, paramEventuallyPin);
      }
      else
      {
        throw new IllegalStateException("Either operationSet or eventuallyPin must be set.");
        label159:
        paramEventuallyPin = Task.create();
        this.pendingEventuallyTasks.put(paramParseOperationSet, paramEventuallyPin);
        paramParseOperationSet = paramEventuallyPin;
      }
    }
    final Task.TaskCompletionSource localTaskCompletionSource = (Task.TaskCompletionSource)this.pendingEventuallyTasks.get(paramParseOperationSet);
    process(paramEventuallyPin, localParseOperationSet).continueWithTask(new Continuation()
    {
      public Task<JSONObject> then(Task<JSONObject> paramAnonymousTask)
        throws Exception
      {
        for (;;)
        {
          synchronized (ParsePinningEventuallyQueue.this.taskQueueSyncLock)
          {
            ParsePinningEventuallyQueue.this.pendingEventuallyTasks.remove(paramParseOperationSet);
            ParsePinningEventuallyQueue.this.uuidToOperationSet.remove(paramParseOperationSet);
            ParsePinningEventuallyQueue.this.uuidToEventuallyPin.remove(paramParseOperationSet);
            ??? = paramAnonymousTask.getError();
            if (??? != null)
            {
              localTaskCompletionSource.trySetError((Exception)???);
              return localTaskCompletionSource.getTask();
            }
          }
          if (paramAnonymousTask.isCancelled()) {
            localTaskCompletionSource.trySetCancelled();
          } else {
            localTaskCompletionSource.trySetResult(paramAnonymousTask.getResult());
          }
        }
      }
    });
  }
  
  private static class PauseException
    extends Exception
  {}
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParsePinningEventuallyQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */