package com.parse;

import bolts.Continuation;
import bolts.Task;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

class ParseAuthenticationManager
{
  private final Map<String, AuthenticationCallback> callbacks = new HashMap();
  private final ParseCurrentUserController controller;
  private final Object lock = new Object();
  
  public ParseAuthenticationManager(ParseCurrentUserController paramParseCurrentUserController)
  {
    this.controller = paramParseCurrentUserController;
  }
  
  public Task<Void> deauthenticateAsync(final String paramString)
  {
    synchronized (this.lock)
    {
      paramString = (AuthenticationCallback)this.callbacks.get(paramString);
      if (paramString != null) {
        Task.call(new Callable()
        {
          public Void call()
            throws Exception
          {
            paramString.onRestore(null);
            return null;
          }
        }, ParseExecutors.io());
      }
    }
    return Task.forResult(null);
  }
  
  public void register(final String paramString, AuthenticationCallback paramAuthenticationCallback)
  {
    if (paramString == null) {
      throw new IllegalArgumentException("Invalid authType: " + null);
    }
    synchronized (this.lock)
    {
      if (this.callbacks.containsKey(paramString)) {
        throw new IllegalStateException("Callback already registered for <" + paramString + ">: " + this.callbacks.get(paramString));
      }
    }
    this.callbacks.put(paramString, paramAuthenticationCallback);
    if ("anonymous".equals(paramString)) {
      return;
    }
    this.controller.getAsync(false).onSuccessTask(new Continuation()
    {
      public Task<Void> then(Task<ParseUser> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = (ParseUser)paramAnonymousTask.getResult();
        if (paramAnonymousTask != null) {
          return paramAnonymousTask.synchronizeAuthDataAsync(paramString);
        }
        return null;
      }
    });
  }
  
  public Task<Boolean> restoreAuthenticationAsync(final String paramString, final Map<String, String> paramMap)
  {
    synchronized (this.lock)
    {
      paramString = (AuthenticationCallback)this.callbacks.get(paramString);
      if (paramString == null) {
        return Task.forResult(Boolean.valueOf(true));
      }
    }
    Task.call(new Callable()
    {
      public Boolean call()
        throws Exception
      {
        return Boolean.valueOf(paramString.onRestore(paramMap));
      }
    }, ParseExecutors.io());
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseAuthenticationManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */