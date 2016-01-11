package com.parse;

import bolts.Continuation;
import bolts.Task;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ParseClassName("_Session")
public class ParseSession
  extends ParseObject
{
  private static final String KEY_CREATED_WITH = "createdWith";
  private static final String KEY_EXPIRES_AT = "expiresAt";
  private static final String KEY_INSTALLATION_ID = "installationId";
  private static final String KEY_RESTRICTED = "restricted";
  private static final String KEY_SESSION_TOKEN = "sessionToken";
  private static final String KEY_USER = "user";
  private static final List<String> READ_ONLY_KEYS = Collections.unmodifiableList(Arrays.asList(new String[] { "sessionToken", "createdWith", "restricted", "user", "expiresAt", "installationId" }));
  
  public static Task<ParseSession> getCurrentSessionInBackground()
  {
    ParseUser.getCurrentSessionTokenAsync().onSuccessTask(new Continuation()
    {
      public Task<ParseSession> then(Task<String> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = (String)paramAnonymousTask.getResult();
        if (paramAnonymousTask == null) {
          return Task.forResult(null);
        }
        ParseSession.access$000().getSessionAsync(paramAnonymousTask).onSuccess(new Continuation()
        {
          public ParseSession then(Task<ParseObject.State> paramAnonymous2Task)
            throws Exception
          {
            return (ParseSession)ParseObject.from((ParseObject.State)paramAnonymous2Task.getResult());
          }
        });
      }
    });
  }
  
  public static void getCurrentSessionInBackground(GetCallback<ParseSession> paramGetCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(getCurrentSessionInBackground(), paramGetCallback);
  }
  
  public static ParseQuery<ParseSession> getQuery()
  {
    return ParseQuery.getQuery(ParseSession.class);
  }
  
  private static ParseSessionController getSessionController()
  {
    return ParseCorePlugins.getInstance().getSessionController();
  }
  
  static boolean isRevocableSessionToken(String paramString)
  {
    return paramString.contains("r:");
  }
  
  static Task<Void> revokeAsync(String paramString)
  {
    if ((paramString == null) || (!isRevocableSessionToken(paramString))) {
      return Task.forResult(null);
    }
    return getSessionController().revokeAsync(paramString);
  }
  
  static Task<String> upgradeToRevocableSessionAsync(String paramString)
  {
    if ((paramString == null) || (isRevocableSessionToken(paramString))) {
      return Task.forResult(paramString);
    }
    getSessionController().upgradeToRevocable(paramString).onSuccess(new Continuation()
    {
      public String then(Task<ParseObject.State> paramAnonymousTask)
        throws Exception
      {
        return ((ParseSession)ParseObject.from((ParseObject.State)paramAnonymousTask.getResult())).getSessionToken();
      }
    });
  }
  
  public String getSessionToken()
  {
    return getString("sessionToken");
  }
  
  boolean isKeyMutable(String paramString)
  {
    return !READ_ONLY_KEYS.contains(paramString);
  }
  
  boolean needsDefaultACL()
  {
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseSession.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */