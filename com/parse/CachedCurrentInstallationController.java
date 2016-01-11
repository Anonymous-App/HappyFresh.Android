package com.parse;

import bolts.Continuation;
import bolts.Task;

class CachedCurrentInstallationController
  implements ParseCurrentInstallationController
{
  static final String TAG = "com.parse.CachedCurrentInstallationController";
  ParseInstallation currentInstallation;
  private final InstallationId installationId;
  private final Object mutex = new Object();
  private final ParseObjectStore<ParseInstallation> store;
  private final TaskQueue taskQueue = new TaskQueue();
  
  public CachedCurrentInstallationController(ParseObjectStore<ParseInstallation> paramParseObjectStore, InstallationId paramInstallationId)
  {
    this.store = paramParseObjectStore;
    this.installationId = paramInstallationId;
  }
  
  public void clearFromDisk()
  {
    synchronized (this.mutex)
    {
      this.currentInstallation = null;
    }
    try
    {
      this.installationId.clear();
      ParseTaskUtils.wait(this.store.deleteAsync());
      return;
    }
    catch (ParseException localParseException) {}
    localObject2 = finally;
    throw ((Throwable)localObject2);
  }
  
  public void clearFromMemory()
  {
    synchronized (this.mutex)
    {
      this.currentInstallation = null;
      return;
    }
  }
  
  public Task<Boolean> existsAsync()
  {
    synchronized (this.mutex)
    {
      if (this.currentInstallation != null)
      {
        Task localTask = Task.forResult(Boolean.valueOf(true));
        return localTask;
      }
      this.taskQueue.enqueue(new Continuation()
      {
        public Task<Boolean> then(Task<Void> paramAnonymousTask)
          throws Exception
        {
          paramAnonymousTask.continueWithTask(new Continuation()
          {
            public Task<Boolean> then(Task<Void> paramAnonymous2Task)
              throws Exception
            {
              return CachedCurrentInstallationController.this.store.existsAsync();
            }
          });
        }
      });
    }
  }
  
  public Task<ParseInstallation> getAsync()
  {
    synchronized (this.mutex)
    {
      ParseInstallation localParseInstallation = this.currentInstallation;
      if (localParseInstallation != null) {
        return Task.forResult(localParseInstallation);
      }
    }
    this.taskQueue.enqueue(new Continuation()
    {
      public Task<ParseInstallation> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask.continueWithTask(new Continuation()
        {
          public Task<ParseInstallation> then(Task<Void> paramAnonymous2Task)
            throws Exception
          {
            return CachedCurrentInstallationController.this.store.getAsync();
          }
        }).continueWith(new Continuation()
        {
          public ParseInstallation then(Task<ParseInstallation> paramAnonymous2Task)
            throws Exception
          {
            paramAnonymous2Task = (ParseInstallation)paramAnonymous2Task.getResult();
            if (paramAnonymous2Task == null)
            {
              paramAnonymous2Task = (ParseInstallation)ParseObject.create(ParseInstallation.class);
              paramAnonymous2Task.updateDeviceInfo(CachedCurrentInstallationController.this.installationId);
            }
            synchronized (CachedCurrentInstallationController.this.mutex)
            {
              CachedCurrentInstallationController.this.currentInstallation = paramAnonymous2Task;
              return paramAnonymous2Task;
              CachedCurrentInstallationController.this.installationId.set(paramAnonymous2Task.getInstallationId());
              PLog.v("com.parse.CachedCurrentInstallationController", "Successfully deserialized Installation object");
            }
          }
        }, ParseExecutors.io());
      }
    });
  }
  
  public boolean isCurrent(ParseInstallation paramParseInstallation)
  {
    for (;;)
    {
      synchronized (this.mutex)
      {
        if (this.currentInstallation == paramParseInstallation)
        {
          bool = true;
          return bool;
        }
      }
      boolean bool = false;
    }
  }
  
  public Task<Void> setAsync(final ParseInstallation paramParseInstallation)
  {
    if (!isCurrent(paramParseInstallation)) {
      return Task.forResult(null);
    }
    this.taskQueue.enqueue(new Continuation()
    {
      public Task<Void> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask.continueWithTask(new Continuation()
        {
          public Task<Void> then(Task<Void> paramAnonymous2Task)
            throws Exception
          {
            return CachedCurrentInstallationController.this.store.setAsync(CachedCurrentInstallationController.1.this.val$installation);
          }
        }).continueWithTask(new Continuation()
        {
          public Task<Void> then(Task<Void> paramAnonymous2Task)
            throws Exception
          {
            CachedCurrentInstallationController.this.installationId.set(CachedCurrentInstallationController.1.this.val$installation.getInstallationId());
            return paramAnonymous2Task;
          }
        }, ParseExecutors.io());
      }
    });
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/CachedCurrentInstallationController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */