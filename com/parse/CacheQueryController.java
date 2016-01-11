package com.parse;

import bolts.Continuation;
import bolts.Task;
import java.util.List;
import java.util.concurrent.Callable;
import org.json.JSONException;
import org.json.JSONObject;

class CacheQueryController
  extends AbstractQueryController
{
  private final NetworkQueryController networkController;
  
  public CacheQueryController(NetworkQueryController paramNetworkQueryController)
  {
    this.networkController = paramNetworkQueryController;
  }
  
  private <T extends ParseObject> Task<Integer> countFromCacheAsync(final ParseQuery.State<T> paramState, String paramString)
  {
    Task.call(new Callable()
    {
      public Integer call()
        throws Exception
      {
        JSONObject localJSONObject = ParseKeyValueCache.jsonFromKeyValueCache(this.val$cacheKey, paramState.maxCacheAge());
        if (localJSONObject == null) {
          throw new ParseException(120, "results not cached");
        }
        try
        {
          int i = localJSONObject.getInt("count");
          return Integer.valueOf(i);
        }
        catch (JSONException localJSONException)
        {
          throw new ParseException(120, "the cache contains corrupted json");
        }
      }
    }, Task.BACKGROUND_EXECUTOR);
  }
  
  private <T extends ParseObject> Task<List<T>> findFromCacheAsync(final ParseQuery.State<T> paramState, String paramString)
  {
    Task.call(new Callable()
    {
      public List<T> call()
        throws Exception
      {
        Object localObject = ParseKeyValueCache.jsonFromKeyValueCache(this.val$cacheKey, paramState.maxCacheAge());
        if (localObject == null) {
          throw new ParseException(120, "results not cached");
        }
        try
        {
          localObject = CacheQueryController.this.networkController.convertFindResponse(paramState, (JSONObject)localObject);
          return (List<T>)localObject;
        }
        catch (JSONException localJSONException)
        {
          throw new ParseException(120, "the cache contains corrupted json");
        }
      }
    }, Task.BACKGROUND_EXECUTOR);
  }
  
  private <TResult> Task<TResult> runCommandWithPolicyAsync(final CommandDelegate<TResult> paramCommandDelegate, ParseQuery.CachePolicy paramCachePolicy)
  {
    switch (paramCachePolicy)
    {
    default: 
      throw new RuntimeException("Unknown cache policy: " + paramCachePolicy);
    case ???: 
    case ???: 
      return paramCommandDelegate.runOnNetworkAsync(true);
    case ???: 
      return paramCommandDelegate.runFromCacheAsync();
    case ???: 
      paramCommandDelegate.runFromCacheAsync().continueWithTask(new Continuation()
      {
        public Task<TResult> then(Task<TResult> paramAnonymousTask)
          throws Exception
        {
          Object localObject = paramAnonymousTask;
          if ((paramAnonymousTask.getError() instanceof ParseException)) {
            localObject = paramCommandDelegate.runOnNetworkAsync(true);
          }
          return (Task<TResult>)localObject;
        }
      });
    case ???: 
      paramCommandDelegate.runOnNetworkAsync(false).continueWithTask(new Continuation()
      {
        public Task<TResult> then(Task<TResult> paramAnonymousTask)
          throws Exception
        {
          Exception localException = paramAnonymousTask.getError();
          Object localObject = paramAnonymousTask;
          if ((localException instanceof ParseException))
          {
            localObject = paramAnonymousTask;
            if (((ParseException)localException).getCode() == 100) {
              localObject = paramCommandDelegate.runFromCacheAsync();
            }
          }
          return (Task<TResult>)localObject;
        }
      });
    }
    throw new RuntimeException("You cannot use the cache policy CACHE_THEN_NETWORK with find()");
  }
  
  public <T extends ParseObject> Task<Integer> countAsync(final ParseQuery.State<T> paramState, final ParseUser paramParseUser, final Task<Void> paramTask)
  {
    if (paramParseUser != null) {}
    for (paramParseUser = paramParseUser.getSessionToken();; paramParseUser = null) {
      runCommandWithPolicyAsync(new CommandDelegate()
      {
        public Task<Integer> runFromCacheAsync()
        {
          return CacheQueryController.this.countFromCacheAsync(paramState, paramParseUser);
        }
        
        public Task<Integer> runOnNetworkAsync(boolean paramAnonymousBoolean)
        {
          return CacheQueryController.this.networkController.countAsync(paramState, paramParseUser, paramAnonymousBoolean, paramTask);
        }
      }, paramState.cachePolicy());
    }
  }
  
  public <T extends ParseObject> Task<List<T>> findAsync(final ParseQuery.State<T> paramState, final ParseUser paramParseUser, final Task<Void> paramTask)
  {
    if (paramParseUser != null) {}
    for (paramParseUser = paramParseUser.getSessionToken();; paramParseUser = null) {
      runCommandWithPolicyAsync(new CommandDelegate()
      {
        public Task<List<T>> runFromCacheAsync()
        {
          return CacheQueryController.this.findFromCacheAsync(paramState, paramParseUser);
        }
        
        public Task<List<T>> runOnNetworkAsync(boolean paramAnonymousBoolean)
        {
          return CacheQueryController.this.networkController.findAsync(paramState, paramParseUser, paramAnonymousBoolean, paramTask);
        }
      }, paramState.cachePolicy());
    }
  }
  
  private static abstract interface CommandDelegate<T>
  {
    public abstract Task<T> runFromCacheAsync();
    
    public abstract Task<T> runOnNetworkAsync(boolean paramBoolean);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/CacheQueryController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */