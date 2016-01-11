package com.parse;

import bolts.Continuation;
import bolts.Task;
import java.util.Arrays;
import java.util.List;

class OfflineObjectStore<T extends ParseObject>
  implements ParseObjectStore<T>
{
  private final String className;
  private final ParseObjectStore<T> legacy;
  private final String pinName;
  
  public OfflineObjectStore(Class<T> paramClass, String paramString, ParseObjectStore<T> paramParseObjectStore)
  {
    this(ParseObject.getClassName(paramClass), paramString, paramParseObjectStore);
  }
  
  public OfflineObjectStore(String paramString1, String paramString2, ParseObjectStore<T> paramParseObjectStore)
  {
    this.className = paramString1;
    this.pinName = paramString2;
    this.legacy = paramParseObjectStore;
  }
  
  private static <T extends ParseObject> Task<T> migrate(ParseObjectStore<T> paramParseObjectStore1, final ParseObjectStore<T> paramParseObjectStore2)
  {
    paramParseObjectStore1.getAsync().onSuccessTask(new Continuation()
    {
      public Task<T> then(Task<T> paramAnonymousTask)
        throws Exception
      {
        final ParseObject localParseObject = (ParseObject)paramAnonymousTask.getResult();
        if (localParseObject == null) {
          return paramAnonymousTask;
        }
        Task.whenAll(Arrays.asList(new Task[] { this.val$from.deleteAsync(), paramParseObjectStore2.setAsync(localParseObject) })).continueWith(new Continuation()
        {
          public T then(Task<Void> paramAnonymous2Task)
            throws Exception
          {
            return localParseObject;
          }
        });
      }
    });
  }
  
  public Task<Void> deleteAsync()
  {
    final Task localTask = ParseObject.unpinAllInBackground(this.pinName);
    Task.whenAll(Arrays.asList(new Task[] { this.legacy.deleteAsync(), localTask })).continueWithTask(new Continuation()
    {
      public Task<Void> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        return localTask;
      }
    });
  }
  
  public Task<Boolean> existsAsync()
  {
    ParseQuery.getQuery(this.className).fromPin(this.pinName).ignoreACLs().countInBackground().onSuccessTask(new Continuation()
    {
      public Task<Boolean> then(Task<Integer> paramAnonymousTask)
        throws Exception
      {
        if (((Integer)paramAnonymousTask.getResult()).intValue() == 1) {}
        for (int i = 1; i != 0; i = 0) {
          return Task.forResult(Boolean.valueOf(true));
        }
        return OfflineObjectStore.this.legacy.existsAsync();
      }
    });
  }
  
  public Task<T> getAsync()
  {
    ParseQuery.getQuery(this.className).fromPin(this.pinName).ignoreACLs().findInBackground().onSuccessTask(new Continuation()
    {
      public Task<T> then(Task<List<T>> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = (List)paramAnonymousTask.getResult();
        if (paramAnonymousTask != null)
        {
          if (paramAnonymousTask.size() == 1) {
            return Task.forResult(paramAnonymousTask.get(0));
          }
          return ParseObject.unpinAllInBackground(OfflineObjectStore.this.pinName).cast();
        }
        return Task.forResult(null);
      }
    }).onSuccessTask(new Continuation()
    {
      public Task<T> then(Task<T> paramAnonymousTask)
        throws Exception
      {
        if ((ParseObject)paramAnonymousTask.getResult() != null) {
          return paramAnonymousTask;
        }
        return OfflineObjectStore.migrate(OfflineObjectStore.this.legacy, OfflineObjectStore.this).cast();
      }
    });
  }
  
  public Task<Void> setAsync(final T paramT)
  {
    ParseObject.unpinAllInBackground(this.pinName).continueWithTask(new Continuation()
    {
      public Task<Void> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        return paramT.pinInBackground(OfflineObjectStore.this.pinName, false);
      }
    });
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/OfflineObjectStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */