package com.parse;

import bolts.Continuation;
import bolts.Task;
import java.util.Arrays;
import java.util.Map;

class CachedCurrentUserController
  implements ParseCurrentUserController
{
  ParseUser currentUser;
  boolean currentUserMatchesDisk = false;
  private final Object mutex = new Object();
  private final ParseObjectStore<ParseUser> store;
  private final TaskQueue taskQueue = new TaskQueue();
  
  public CachedCurrentUserController(ParseObjectStore<ParseUser> paramParseObjectStore)
  {
    this.store = paramParseObjectStore;
  }
  
  private ParseUser lazyLogIn()
  {
    return lazyLogIn("anonymous", ParseAnonymousUtils.getAuthData());
  }
  
  public void clearFromDisk()
  {
    synchronized (this.mutex)
    {
      this.currentUser = null;
      this.currentUserMatchesDisk = false;
    }
  }
  
  public void clearFromMemory()
  {
    synchronized (this.mutex)
    {
      this.currentUser = null;
      this.currentUserMatchesDisk = false;
      return;
    }
  }
  
  public Task<Boolean> existsAsync()
  {
    synchronized (this.mutex)
    {
      if (this.currentUser != null)
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
              return CachedCurrentUserController.this.store.existsAsync();
            }
          });
        }
      });
    }
  }
  
  public Task<ParseUser> getAsync()
  {
    return getAsync(ParseUser.isAutomaticUserEnabled());
  }
  
  public Task<ParseUser> getAsync(final boolean paramBoolean)
  {
    synchronized (this.mutex)
    {
      if (this.currentUser != null)
      {
        Task localTask = Task.forResult(this.currentUser);
        return localTask;
      }
      this.taskQueue.enqueue(new Continuation()
      {
        public Task<ParseUser> then(Task<Void> paramAnonymousTask)
          throws Exception
        {
          paramAnonymousTask.continueWithTask(new Continuation()
          {
            public Task<ParseUser> then(Task<Void> arg1)
              throws Exception
            {
              boolean bool;
              synchronized (CachedCurrentUserController.this.mutex)
              {
                ParseUser localParseUser = CachedCurrentUserController.this.currentUser;
                bool = CachedCurrentUserController.this.currentUserMatchesDisk;
                if (localParseUser != null) {
                  return Task.forResult(localParseUser);
                }
              }
              if (bool)
              {
                if (CachedCurrentUserController.5.this.val$shouldAutoCreateUser) {
                  return Task.forResult(CachedCurrentUserController.this.lazyLogIn());
                }
                return null;
              }
              CachedCurrentUserController.this.store.getAsync().continueWith(new Continuation()
              {
                public ParseUser then(Task<ParseUser> arg1)
                  throws Exception
                {
                  boolean bool = true;
                  ParseUser localParseUser = (ParseUser)???.getResult();
                  if (!???.isFaulted()) {}
                  for (;;)
                  {
                    synchronized (CachedCurrentUserController.this.mutex)
                    {
                      CachedCurrentUserController.this.currentUser = localParseUser;
                      CachedCurrentUserController.this.currentUserMatchesDisk = bool;
                      if (localParseUser == null) {
                        break;
                      }
                    }
                    synchronized (localParseUser.mutex)
                    {
                      localParseUser.setIsCurrentUser(true);
                      return localParseUser;
                      bool = false;
                      continue;
                      localObject1 = finally;
                      throw ((Throwable)localObject1);
                    }
                  }
                  if (CachedCurrentUserController.5.this.val$shouldAutoCreateUser) {
                    return CachedCurrentUserController.this.lazyLogIn();
                  }
                  return null;
                }
              });
            }
          });
        }
      });
    }
  }
  
  public Task<String> getCurrentSessionTokenAsync()
  {
    getAsync(false).onSuccess(new Continuation()
    {
      public String then(Task<ParseUser> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = (ParseUser)paramAnonymousTask.getResult();
        if (paramAnonymousTask != null) {
          return paramAnonymousTask.getSessionToken();
        }
        return null;
      }
    });
  }
  
  public boolean isCurrent(ParseUser paramParseUser)
  {
    for (;;)
    {
      synchronized (this.mutex)
      {
        if (this.currentUser == paramParseUser)
        {
          bool = true;
          return bool;
        }
      }
      boolean bool = false;
    }
  }
  
  ParseUser lazyLogIn(String arg1, Map<String, String> paramMap)
  {
    ParseUser localParseUser = (ParseUser)ParseObject.create(ParseUser.class);
    synchronized (localParseUser.mutex)
    {
      localParseUser.setIsCurrentUser(true);
      localParseUser.putAuthData(???, paramMap);
    }
    synchronized (this.mutex)
    {
      this.currentUserMatchesDisk = false;
      this.currentUser = localParseUser;
      return localParseUser;
      ??? = finally;
      throw ???;
    }
  }
  
  public Task<Void> logOutAsync()
  {
    this.taskQueue.enqueue(new Continuation()
    {
      public Task<Void> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        final Task localTask = CachedCurrentUserController.this.getAsync(false);
        Task.whenAll(Arrays.asList(new Task[] { localTask, paramAnonymousTask })).continueWithTask(new Continuation()
        {
          public Task<Void> then(Task<Void> paramAnonymous2Task)
            throws Exception
          {
            Task.whenAll(Arrays.asList(new Task[] { localTask.onSuccessTask(new Continuation()
            {
              public Task<Void> then(Task<ParseUser> paramAnonymous3Task)
                throws Exception
              {
                ParseUser localParseUser = (ParseUser)paramAnonymous3Task.getResult();
                if (localParseUser == null) {
                  return paramAnonymous3Task.cast();
                }
                return localParseUser.logOutAsync();
              }
            }), CachedCurrentUserController.this.store.deleteAsync().continueWith(new Continuation()
            {
              public Void then(Task<Void> arg1)
                throws Exception
              {
                if (!???.isFaulted()) {}
                for (boolean bool = true;; bool = false) {
                  synchronized (CachedCurrentUserController.this.mutex)
                  {
                    CachedCurrentUserController.this.currentUserMatchesDisk = bool;
                    CachedCurrentUserController.this.currentUser = null;
                    return null;
                  }
                }
              }
            }) }));
          }
        });
      }
    });
  }
  
  public Task<Void> setAsync(final ParseUser paramParseUser)
  {
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
            synchronized (CachedCurrentUserController.this.mutex)
            {
              ParseUser localParseUser = CachedCurrentUserController.this.currentUser;
              ??? = paramAnonymous2Task;
              if (localParseUser != null)
              {
                ??? = paramAnonymous2Task;
                if (localParseUser != CachedCurrentUserController.1.this.val$user) {
                  ??? = localParseUser.logOutAsync(false).continueWith(new Continuation()
                  {
                    public Void then(Task<Void> paramAnonymous3Task)
                      throws Exception
                    {
                      return null;
                    }
                  });
                }
              }
              return (Task<Void>)???;
            }
          }
        }).onSuccessTask(new Continuation()
        {
          public Task<Void> then(Task<Void> paramAnonymous2Task)
            throws Exception
          {
            CachedCurrentUserController.1.this.val$user.setIsCurrentUser(true);
            return CachedCurrentUserController.1.this.val$user.synchronizeAllAuthDataAsync();
          }
        }).onSuccessTask(new Continuation()
        {
          public Task<Void> then(Task<Void> paramAnonymous2Task)
            throws Exception
          {
            CachedCurrentUserController.this.store.setAsync(CachedCurrentUserController.1.this.val$user).continueWith(new Continuation()
            {
              public Void then(Task<Void> paramAnonymous3Task)
                throws Exception
              {
                for (;;)
                {
                  synchronized (CachedCurrentUserController.this.mutex)
                  {
                    CachedCurrentUserController localCachedCurrentUserController = CachedCurrentUserController.this;
                    if (!paramAnonymous3Task.isFaulted())
                    {
                      bool = true;
                      localCachedCurrentUserController.currentUserMatchesDisk = bool;
                      CachedCurrentUserController.this.currentUser = CachedCurrentUserController.1.this.val$user;
                      return null;
                    }
                  }
                  boolean bool = false;
                }
              }
            });
          }
        });
      }
    });
  }
  
  public Task<Void> setIfNeededAsync(ParseUser paramParseUser)
  {
    synchronized (this.mutex)
    {
      if ((!paramParseUser.isCurrentUser()) || (this.currentUserMatchesDisk))
      {
        paramParseUser = Task.forResult(null);
        return paramParseUser;
      }
      return setAsync(paramParseUser);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/CachedCurrentUserController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */