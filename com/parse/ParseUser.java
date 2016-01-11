package com.parse;

import bolts.Continuation;
import bolts.Task;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONObject;

@ParseClassName("_User")
public class ParseUser
  extends ParseObject
{
  private static final String KEY_AUTH_DATA = "authData";
  private static final String KEY_EMAIL = "email";
  private static final String KEY_PASSWORD = "password";
  private static final String KEY_SESSION_TOKEN = "sessionToken";
  private static final String KEY_USERNAME = "username";
  private static final List<String> READ_ONLY_KEYS = Collections.unmodifiableList(Arrays.asList(new String[] { "sessionToken", "authData" }));
  private static boolean autoUserEnabled;
  private static final Object isAutoUserEnabledMutex = new Object();
  private boolean isCurrentUser = false;
  
  public static ParseUser become(String paramString)
    throws ParseException
  {
    return (ParseUser)ParseTaskUtils.wait(becomeInBackground(paramString));
  }
  
  public static Task<ParseUser> becomeInBackground(String paramString)
  {
    if (paramString == null) {
      throw new IllegalArgumentException("Must specify a sessionToken for the user to log in with");
    }
    getUserController().getUserAsync(paramString).onSuccessTask(new Continuation()
    {
      public Task<ParseUser> then(final Task<ParseUser.State> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = (ParseUser)ParseObject.from((ParseUser.State)paramAnonymousTask.getResult());
        ParseUser.saveCurrentUserAsync(paramAnonymousTask).onSuccess(new Continuation()
        {
          public ParseUser then(Task<Void> paramAnonymous2Task)
            throws Exception
          {
            return paramAnonymousTask;
          }
        });
      }
    });
  }
  
  public static void becomeInBackground(String paramString, LogInCallback paramLogInCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(becomeInBackground(paramString), paramLogInCallback);
  }
  
  static void disableAutomaticUser()
  {
    synchronized (isAutoUserEnabledMutex)
    {
      autoUserEnabled = false;
      return;
    }
  }
  
  public static void enableAutomaticUser()
  {
    synchronized (isAutoUserEnabledMutex)
    {
      autoUserEnabled = true;
      return;
    }
  }
  
  public static Task<Void> enableRevocableSessionInBackground()
  {
    ParseCorePlugins.getInstance().registerUserController(new NetworkUserController(ParsePlugins.get().restClient(), true));
    getCurrentUserController().getAsync(false).onSuccessTask(new Continuation()
    {
      public Task<Void> then(Task<ParseUser> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = (ParseUser)paramAnonymousTask.getResult();
        if (paramAnonymousTask == null) {
          return Task.forResult(null);
        }
        return paramAnonymousTask.upgradeToRevocableSessionAsync();
      }
    });
  }
  
  private Map<String, String> getAuthData(String paramString)
  {
    return (Map)getAuthData().get(paramString);
  }
  
  static ParseAuthenticationManager getAuthenticationManager()
  {
    return ParseCorePlugins.getInstance().getAuthenticationManager();
  }
  
  static String getCurrentSessionToken()
  {
    ParseUser localParseUser = getCurrentUser();
    if (localParseUser != null) {
      return localParseUser.getSessionToken();
    }
    return null;
  }
  
  static Task<String> getCurrentSessionTokenAsync()
  {
    return getCurrentUserController().getCurrentSessionTokenAsync();
  }
  
  public static ParseUser getCurrentUser()
  {
    return getCurrentUser(isAutomaticUserEnabled());
  }
  
  private static ParseUser getCurrentUser(boolean paramBoolean)
  {
    try
    {
      ParseUser localParseUser = (ParseUser)ParseTaskUtils.wait(getCurrentUserController().getAsync(paramBoolean));
      return localParseUser;
    }
    catch (ParseException localParseException) {}
    return null;
  }
  
  static Task<ParseUser> getCurrentUserAsync()
  {
    return getCurrentUserController().getAsync();
  }
  
  static ParseCurrentUserController getCurrentUserController()
  {
    return ParseCorePlugins.getInstance().getCurrentUserController();
  }
  
  public static ParseQuery<ParseUser> getQuery()
  {
    return ParseQuery.getQuery(ParseUser.class);
  }
  
  static ParseUserController getUserController()
  {
    return ParseCorePlugins.getInstance().getUserController();
  }
  
  static boolean isAutomaticUserEnabled()
  {
    synchronized (isAutoUserEnabledMutex)
    {
      boolean bool = autoUserEnabled;
      return bool;
    }
  }
  
  private Task<Void> linkWithAsync(final String paramString1, Map<String, String> paramMap, Task<Void> paramTask, String paramString2)
  {
    synchronized (this.mutex)
    {
      boolean bool = isLazy();
      final Map localMap = getAuthData("anonymous");
      stripAnonymity();
      putAuthData(paramString1, paramMap);
      paramString1 = saveAsync(paramString2, bool, paramTask).continueWithTask(new Continuation()
      {
        public Task<Void> then(Task<Void> paramAnonymousTask)
          throws Exception
        {
          synchronized (ParseUser.this.mutex)
          {
            if ((paramAnonymousTask.isFaulted()) || (paramAnonymousTask.isCancelled()))
            {
              ParseUser.this.restoreAnonymity(localMap);
              return paramAnonymousTask;
            }
            paramAnonymousTask = ParseUser.this.synchronizeAuthDataAsync(paramString1);
            return paramAnonymousTask;
          }
        }
      });
      return paramString1;
    }
  }
  
  private Task<Void> linkWithAsync(final String paramString1, final Map<String, String> paramMap, final String paramString2)
  {
    this.taskQueue.enqueue(new Continuation()
    {
      public Task<Void> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        return ParseUser.this.linkWithAsync(paramString1, paramMap, paramAnonymousTask, paramString2);
      }
    });
  }
  
  public static ParseUser logIn(String paramString1, String paramString2)
    throws ParseException
  {
    return (ParseUser)ParseTaskUtils.wait(logInInBackground(paramString1, paramString2));
  }
  
  public static Task<ParseUser> logInInBackground(String paramString1, String paramString2)
  {
    if (paramString1 == null) {
      throw new IllegalArgumentException("Must specify a username for the user to log in with");
    }
    if (paramString2 == null) {
      throw new IllegalArgumentException("Must specify a password for the user to log in with");
    }
    getUserController().logInAsync(paramString1, paramString2).onSuccessTask(new Continuation()
    {
      public Task<ParseUser> then(final Task<ParseUser.State> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = (ParseUser)ParseObject.from((ParseUser.State)paramAnonymousTask.getResult());
        ParseUser.saveCurrentUserAsync(paramAnonymousTask).onSuccess(new Continuation()
        {
          public ParseUser then(Task<Void> paramAnonymous2Task)
            throws Exception
          {
            return paramAnonymousTask;
          }
        });
      }
    });
  }
  
  public static void logInInBackground(String paramString1, String paramString2, LogInCallback paramLogInCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(logInInBackground(paramString1, paramString2), paramLogInCallback);
  }
  
  public static Task<ParseUser> logInWithInBackground(String paramString, final Map<String, String> paramMap)
  {
    if (paramString == null) {
      throw new IllegalArgumentException("Invalid authType: " + null);
    }
    final Continuation local11 = new Continuation()
    {
      public Task<ParseUser> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        ParseUser.getUserController().logInAsync(this.val$authType, paramMap).onSuccessTask(new Continuation()
        {
          public Task<ParseUser> then(final Task<ParseUser.State> paramAnonymous2Task)
            throws Exception
          {
            paramAnonymous2Task = (ParseUser)ParseObject.from((ParseUser.State)paramAnonymous2Task.getResult());
            ParseUser.saveCurrentUserAsync(paramAnonymous2Task).onSuccess(new Continuation()
            {
              public ParseUser then(Task<Void> paramAnonymous3Task)
                throws Exception
              {
                return paramAnonymous2Task;
              }
            });
          }
        });
      }
    };
    getCurrentUserAsync().onSuccessTask(new Continuation()
    {
      public Task<ParseUser> then(Task<ParseUser> arg1)
        throws Exception
      {
        final Object localObject1 = (ParseUser)???.getResult();
        if (localObject1 != null) {
          synchronized (((ParseUser)localObject1).mutex)
          {
            if (ParseAnonymousUtils.isLinked((ParseUser)localObject1))
            {
              if (((ParseUser)localObject1).isLazy())
              {
                final Map localMap = ((ParseUser)localObject1).getAuthData("anonymous");
                localObject1 = ((ParseUser)localObject1).taskQueue.enqueue(new Continuation()
                {
                  public Task<ParseUser> then(Task<Void> paramAnonymous2Task)
                    throws Exception
                  {
                    paramAnonymous2Task.continueWithTask(new Continuation()
                    {
                      public Task<Void> then(Task<Void> paramAnonymous3Task)
                        throws Exception
                      {
                        synchronized (ParseUser.12.1.this.val$user.mutex)
                        {
                          ParseUser.12.1.this.val$user.stripAnonymity();
                          ParseUser.12.1.this.val$user.putAuthData(ParseUser.12.this.val$authType, ParseUser.12.this.val$authData);
                          paramAnonymous3Task = ParseUser.12.1.this.val$user.resolveLazinessAsync(paramAnonymous3Task);
                          return paramAnonymous3Task;
                        }
                      }
                    }).continueWithTask(new Continuation()
                    {
                      public Task<ParseUser> then(Task<Void> paramAnonymous3Task)
                        throws Exception
                      {
                        synchronized (ParseUser.12.1.this.val$user.mutex)
                        {
                          if (paramAnonymous3Task.isFaulted())
                          {
                            ParseUser.12.1.this.val$user.removeAuthData(ParseUser.12.this.val$authType);
                            ParseUser.12.1.this.val$user.restoreAnonymity(ParseUser.12.1.this.val$oldAnonymousData);
                            paramAnonymous3Task = Task.forError(paramAnonymous3Task.getError());
                            return paramAnonymous3Task;
                          }
                          if (paramAnonymous3Task.isCancelled())
                          {
                            paramAnonymous3Task = Task.cancelled();
                            return paramAnonymous3Task;
                          }
                        }
                        paramAnonymous3Task = Task.forResult(ParseUser.12.1.this.val$user);
                        return paramAnonymous3Task;
                      }
                    });
                  }
                });
                return (Task<ParseUser>)localObject1;
              }
              localObject1 = ((ParseUser)localObject1).linkWithInBackground(this.val$authType, paramMap).continueWithTask(new Continuation()
              {
                public Task<ParseUser> then(Task<Void> paramAnonymous2Task)
                  throws Exception
                {
                  if (paramAnonymous2Task.isFaulted())
                  {
                    Exception localException = paramAnonymous2Task.getError();
                    if (((localException instanceof ParseException)) && (((ParseException)localException).getCode() == 208)) {
                      return Task.forResult(null).continueWithTask(ParseUser.12.this.val$logInWithTask);
                    }
                  }
                  if (paramAnonymous2Task.isCancelled()) {
                    return Task.cancelled();
                  }
                  return Task.forResult(localObject1);
                }
              });
              return (Task<ParseUser>)localObject1;
            }
          }
        }
        return Task.forResult(null).continueWithTask(local11);
      }
    });
  }
  
  public static void logOut()
  {
    try
    {
      ParseTaskUtils.wait(logOutInBackground());
      return;
    }
    catch (ParseException localParseException) {}
  }
  
  public static Task<Void> logOutInBackground()
  {
    return getCurrentUserController().logOutAsync();
  }
  
  public static void logOutInBackground(LogOutCallback paramLogOutCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(logOutInBackground(), paramLogOutCallback);
  }
  
  static Task<Void> pinCurrentUserIfNeededAsync(ParseUser paramParseUser)
  {
    if (!Parse.isLocalDatastoreEnabled()) {
      throw new IllegalStateException("Method requires Local Datastore. Please refer to `Parse#enableLocalDatastore(Context)`.");
    }
    return getCurrentUserController().setIfNeededAsync(paramParseUser);
  }
  
  public static void registerAuthenticationCallback(String paramString, AuthenticationCallback paramAuthenticationCallback)
  {
    getAuthenticationManager().register(paramString, paramAuthenticationCallback);
  }
  
  private void removeAuthData(String paramString)
  {
    synchronized (this.mutex)
    {
      Map localMap = getAuthData();
      localMap.remove(paramString);
      performPut("authData", localMap);
      return;
    }
  }
  
  public static void requestPasswordReset(String paramString)
    throws ParseException
  {
    ParseTaskUtils.wait(requestPasswordResetInBackground(paramString));
  }
  
  public static Task<Void> requestPasswordResetInBackground(String paramString)
  {
    return getUserController().requestPasswordResetAsync(paramString);
  }
  
  public static void requestPasswordResetInBackground(String paramString, RequestPasswordResetCallback paramRequestPasswordResetCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(requestPasswordResetInBackground(paramString), paramRequestPasswordResetCallback);
  }
  
  private void restoreAnonymity(Map<String, String> paramMap)
  {
    Object localObject = this.mutex;
    if (paramMap != null) {}
    try
    {
      putAuthData("anonymous", paramMap);
      return;
    }
    finally {}
  }
  
  private static Task<Void> saveCurrentUserAsync(ParseUser paramParseUser)
  {
    return getCurrentUserController().setAsync(paramParseUser);
  }
  
  private Task<Void> setSessionTokenInBackground(String paramString)
  {
    synchronized (this.mutex)
    {
      State localState = getState();
      if (paramString.equals(localState.sessionToken()))
      {
        paramString = Task.forResult(null);
        return paramString;
      }
      setState(localState.newBuilder().sessionToken(paramString).build());
      paramString = saveCurrentUserAsync(this);
      return paramString;
    }
  }
  
  private void stripAnonymity()
  {
    synchronized (this.mutex)
    {
      if (ParseAnonymousUtils.isLinked(this))
      {
        if (getObjectId() != null) {
          putAuthData("anonymous", null);
        }
      }
      else {
        return;
      }
      removeAuthData("anonymous");
    }
  }
  
  private Task<Void> synchronizeAuthDataAsync(ParseAuthenticationManager paramParseAuthenticationManager, final String paramString, Map<String, String> paramMap)
  {
    paramParseAuthenticationManager.restoreAuthenticationAsync(paramString, paramMap).continueWithTask(new Continuation()
    {
      public Task<Void> then(Task<Boolean> paramAnonymousTask)
        throws Exception
      {
        if ((!paramAnonymousTask.isFaulted()) && (((Boolean)paramAnonymousTask.getResult()).booleanValue())) {}
        for (int i = 1; i == 0; i = 0) {
          return ParseUser.this.unlinkFromInBackground(paramString);
        }
        return paramAnonymousTask.makeVoid();
      }
    });
  }
  
  private Task<Void> upgradeToRevocableSessionAsync(Task<Void> paramTask)
  {
    paramTask.continueWithTask(new Continuation()
    {
      public Task<String> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        return ParseSession.upgradeToRevocableSessionAsync(this.val$sessionToken);
      }
    }).onSuccessTask(new Continuation()
    {
      public Task<Void> then(Task<String> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = (String)paramAnonymousTask.getResult();
        return ParseUser.this.setSessionTokenInBackground(paramAnonymousTask);
      }
    });
  }
  
  Task<Void> cleanUpAuthDataAsync()
  {
    Object localObject1 = getAuthenticationManager();
    Map localMap;
    synchronized (this.mutex)
    {
      localMap = getState().authData();
      if (localMap.size() == 0)
      {
        localObject1 = Task.forResult(null);
        return (Task<Void>)localObject1;
      }
      ??? = new ArrayList();
      Iterator localIterator = localMap.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        if (localEntry.getValue() == null)
        {
          localIterator.remove();
          ((List)???).add(((ParseAuthenticationManager)localObject1).restoreAuthenticationAsync((String)localEntry.getKey(), null).makeVoid());
        }
      }
    }
    setState(getState().newBuilder().authData(localMap).build());
    return Task.whenAll((Collection)???);
  }
  
  public ParseUser fetch()
    throws ParseException
  {
    return (ParseUser)super.fetch();
  }
  
  <T extends ParseObject> Task<T> fetchAsync(String paramString, Task<Void> paramTask)
  {
    if (isLazy()) {
      paramString = Task.forResult(this);
    }
    do
    {
      return paramString;
      paramTask = super.fetchAsync(paramString, paramTask);
      paramString = paramTask;
    } while (!isCurrentUser());
    paramTask.onSuccessTask(new Continuation()
    {
      public Task<Void> then(Task<T> paramAnonymousTask)
        throws Exception
      {
        return ParseUser.this.cleanUpAuthDataAsync();
      }
    }).onSuccessTask(new Continuation()
    {
      public Task<Void> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        return ParseUser.saveCurrentUserAsync(ParseUser.this);
      }
    }).onSuccess(new Continuation()
    {
      public T then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        return ParseUser.this;
      }
    });
  }
  
  <T extends ParseObject> Task<T> fetchFromLocalDatastoreAsync()
  {
    if (isLazy()) {
      return Task.forResult(this);
    }
    return super.fetchFromLocalDatastoreAsync();
  }
  
  public ParseUser fetchIfNeeded()
    throws ParseException
  {
    return (ParseUser)super.fetchIfNeeded();
  }
  
  Map<String, Map<String, String>> getAuthData()
  {
    synchronized (this.mutex)
    {
      Map localMap = getMap("authData");
      Object localObject1 = localMap;
      if (localMap == null) {
        localObject1 = new HashMap();
      }
      return (Map<String, Map<String, String>>)localObject1;
    }
  }
  
  public String getEmail()
  {
    return getString("email");
  }
  
  String getPassword()
  {
    return getString("password");
  }
  
  public String getSessionToken()
  {
    return getState().sessionToken();
  }
  
  State getState()
  {
    return (State)super.getState();
  }
  
  public String getUsername()
  {
    return getString("username");
  }
  
  Task<Void> handleSaveResultAsync(ParseObject.State paramState, ParseOperationSet paramParseOperationSet)
  {
    if (paramState != null) {}
    for (int i = 1;; i = 0)
    {
      if (i != 0) {
        paramParseOperationSet.remove("password");
      }
      return super.handleSaveResultAsync(paramState, paramParseOperationSet);
    }
  }
  
  public boolean isAuthenticated()
  {
    for (;;)
    {
      synchronized (this.mutex)
      {
        ParseUser localParseUser = getCurrentUser();
        if (!isLazy())
        {
          if ((getState().sessionToken() == null) || (localParseUser == null) || (!getObjectId().equals(localParseUser.getObjectId()))) {
            break label63;
          }
          break label58;
          return bool;
        }
      }
      label58:
      boolean bool = true;
      continue;
      label63:
      bool = false;
    }
  }
  
  boolean isContainerObject(String paramString, Object paramObject)
  {
    if ("authData".equals(paramString)) {
      return false;
    }
    return super.isContainerObject(paramString, paramObject);
  }
  
  boolean isCurrentUser()
  {
    synchronized (this.mutex)
    {
      boolean bool = this.isCurrentUser;
      return bool;
    }
  }
  
  boolean isKeyMutable(String paramString)
  {
    return !READ_ONLY_KEYS.contains(paramString);
  }
  
  boolean isLazy()
  {
    for (;;)
    {
      synchronized (this.mutex)
      {
        if ((getObjectId() == null) && (ParseAnonymousUtils.isLinked(this)))
        {
          bool = true;
          return bool;
        }
      }
      boolean bool = false;
    }
  }
  
  public boolean isLinked(String paramString)
  {
    Map localMap = getAuthData();
    return (localMap.containsKey(paramString)) && (localMap.get(paramString) != null);
  }
  
  public boolean isNew()
  {
    return getState().isNew();
  }
  
  public Task<Void> linkWithInBackground(String paramString, Map<String, String> paramMap)
  {
    if (paramString == null) {
      throw new IllegalArgumentException("Invalid authType: " + null);
    }
    return linkWithAsync(paramString, paramMap, getSessionToken());
  }
  
  Task<Void> logOutAsync()
  {
    return logOutAsync(true);
  }
  
  Task<Void> logOutAsync(boolean paramBoolean)
  {
    Object localObject2 = getAuthenticationManager();
    ArrayList localArrayList = new ArrayList();
    String str;
    synchronized (this.mutex)
    {
      str = getState().sessionToken();
      Iterator localIterator = getAuthData().entrySet().iterator();
      if (localIterator.hasNext()) {
        localArrayList.add(((ParseAuthenticationManager)localObject2).deauthenticateAsync((String)((Map.Entry)localIterator.next()).getKey()));
      }
    }
    localObject2 = getState().newBuilder().sessionToken(null).isNew(false).build();
    this.isCurrentUser = false;
    setState((ParseObject.State)localObject2);
    if (paramBoolean) {
      localCollection.add(ParseSession.revokeAsync(str));
    }
    return Task.whenAll(localCollection);
  }
  
  boolean needsDefaultACL()
  {
    return false;
  }
  
  ParseUser.State.Builder newStateBuilder(String paramString)
  {
    return new ParseUser.State.Builder();
  }
  
  public void put(String paramString, Object paramObject)
  {
    synchronized (this.mutex)
    {
      if ("username".equals(paramString)) {
        stripAnonymity();
      }
      super.put(paramString, paramObject);
      return;
    }
  }
  
  void putAuthData(String paramString, Map<String, String> paramMap)
  {
    synchronized (this.mutex)
    {
      Map localMap = getAuthData();
      localMap.put(paramString, paramMap);
      performPut("authData", localMap);
      return;
    }
  }
  
  public void remove(String paramString)
  {
    if ("username".equals(paramString)) {
      throw new IllegalArgumentException("Can't remove the username key.");
    }
    super.remove(paramString);
  }
  
  Task<Void> resolveLazinessAsync(Task<Void> paramTask)
  {
    synchronized (this.mutex)
    {
      if (getAuthData().size() == 0)
      {
        paramTask = signUpAsync(paramTask);
        return paramTask;
      }
      paramTask = paramTask.onSuccessTask(new Continuation()
      {
        public Task<Void> then(Task<Void> paramAnonymousTask)
          throws Exception
        {
          ParseUser.getUserController().logInAsync(ParseUser.this.getState(), this.val$operations).onSuccessTask(new Continuation()
          {
            public Task<Void> then(final Task<ParseUser.State> paramAnonymous2Task)
              throws Exception
            {
              paramAnonymous2Task = (ParseUser.State)paramAnonymous2Task.getResult();
              if ((Parse.isLocalDatastoreEnabled()) && (!paramAnonymous2Task.isNew())) {}
              for (paramAnonymous2Task = Task.forResult(paramAnonymous2Task);; paramAnonymous2Task = ParseUser.this.handleSaveResultAsync(paramAnonymous2Task, ParseUser.16.this.val$operations).onSuccess(new Continuation()
                  {
                    public ParseUser.State then(Task<Void> paramAnonymous3Task)
                      throws Exception
                    {
                      return paramAnonymous2Task;
                    }
                  })) {
                paramAnonymous2Task.onSuccessTask(new Continuation()
                {
                  public Task<Void> then(Task<ParseUser.State> paramAnonymous3Task)
                    throws Exception
                  {
                    ParseUser.State localState = (ParseUser.State)paramAnonymous3Task.getResult();
                    if (!localState.isNew()) {
                      return ParseUser.saveCurrentUserAsync((ParseUser)ParseObject.from(localState));
                    }
                    return paramAnonymous3Task.makeVoid();
                  }
                });
              }
            }
          });
        }
      });
      return paramTask;
    }
  }
  
  Task<Void> saveAsync(String paramString, Task<Void> paramTask)
  {
    return saveAsync(paramString, isLazy(), paramTask);
  }
  
  Task<Void> saveAsync(String paramString, boolean paramBoolean, Task<Void> paramTask)
  {
    if (paramBoolean) {}
    for (paramString = resolveLazinessAsync(paramTask);; paramString = super.saveAsync(paramString, paramTask))
    {
      paramTask = paramString;
      if (isCurrentUser()) {
        paramTask = paramString.onSuccessTask(new Continuation()
        {
          public Task<Void> then(Task<Void> paramAnonymousTask)
            throws Exception
          {
            return ParseUser.this.cleanUpAuthDataAsync();
          }
        }).onSuccessTask(new Continuation()
        {
          public Task<Void> then(Task<Void> paramAnonymousTask)
            throws Exception
          {
            return ParseUser.saveCurrentUserAsync(ParseUser.this);
          }
        });
      }
      return paramTask;
    }
  }
  
  public void setEmail(String paramString)
  {
    put("email", paramString);
  }
  
  void setIsCurrentUser(boolean paramBoolean)
  {
    synchronized (this.mutex)
    {
      this.isCurrentUser = paramBoolean;
      return;
    }
  }
  
  public void setPassword(String paramString)
  {
    put("password", paramString);
  }
  
  void setState(ParseObject.State paramState)
  {
    ParseObject.State localState = paramState;
    if (isCurrentUser())
    {
      localState = paramState;
      if (getSessionToken() != null)
      {
        localState = paramState;
        if (paramState.get("sessionToken") == null) {
          localState = paramState.newBuilder().put("sessionToken", getSessionToken()).build();
        }
      }
    }
    super.setState(localState);
  }
  
  public void setUsername(String paramString)
  {
    put("username", paramString);
  }
  
  public void signUp()
    throws ParseException
  {
    ParseTaskUtils.wait(signUpInBackground());
  }
  
  Task<Void> signUpAsync(Task<Void> paramTask)
  {
    final Object localObject2 = getCurrentUser();
    Object localObject1 = this.mutex;
    if (localObject2 != null) {}
    for (;;)
    {
      try
      {
        str1 = ((ParseUser)localObject2).getSessionToken();
        if (ParseTextUtils.isEmpty(getUsername()))
        {
          paramTask = Task.forError(new IllegalArgumentException("Username cannot be missing or blank"));
          return paramTask;
        }
        if (ParseTextUtils.isEmpty(getPassword()))
        {
          paramTask = Task.forError(new IllegalArgumentException("Password cannot be missing or blank"));
          return paramTask;
        }
      }
      finally {}
      if (getObjectId() != null)
      {
        localObject2 = getAuthData();
        if ((((Map)localObject2).containsKey("anonymous")) && (((Map)localObject2).get("anonymous") == null))
        {
          paramTask = saveAsync(str1, paramTask);
          return paramTask;
        }
        paramTask = Task.forError(new IllegalArgumentException("Cannot sign up a user that has already signed up."));
        return paramTask;
      }
      if (this.operationSetQueue.size() > 1)
      {
        paramTask = Task.forError(new IllegalArgumentException("Cannot sign up a user that is already signing up."));
        return paramTask;
      }
      if ((localObject2 != null) && (ParseAnonymousUtils.isLinked((ParseUser)localObject2)))
      {
        if (this == localObject2)
        {
          paramTask = Task.forError(new IllegalArgumentException("Attempt to merge currentUser with itself."));
          return paramTask;
        }
        checkForChangesToMutableContainers();
        ((ParseUser)localObject2).checkForChangesToMutableContainers();
        boolean bool = ((ParseUser)localObject2).isLazy();
        final String str2 = ((ParseUser)localObject2).getUsername();
        final String str3 = ((ParseUser)localObject2).getPassword();
        final Map localMap = ((ParseUser)localObject2).getAuthData("anonymous");
        ((ParseUser)localObject2).copyChangesFrom(this);
        ((ParseUser)localObject2).setUsername(getUsername());
        ((ParseUser)localObject2).setPassword(getPassword());
        revert();
        paramTask = ((ParseUser)localObject2).saveAsync(str1, bool, paramTask).continueWithTask(new Continuation()
        {
          public Task<Void> then(Task<Void> paramAnonymousTask)
            throws Exception
          {
            if ((paramAnonymousTask.isCancelled()) || (paramAnonymousTask.isFaulted())) {
              for (;;)
              {
                synchronized (localObject2.mutex)
                {
                  if (str2 != null)
                  {
                    localObject2.setUsername(str2);
                    if (str3 != null)
                    {
                      localObject2.setPassword(str3);
                      localObject2.restoreAnonymity(localMap);
                      return paramAnonymousTask;
                    }
                  }
                  else
                  {
                    localObject2.revert("username");
                  }
                }
                localObject2.revert("password");
              }
            }
            localObject2.revert("password");
            ParseUser.this.revert("password");
            ParseUser.this.mergeFromObject(localObject2);
            return ParseUser.saveCurrentUserAsync(ParseUser.this);
          }
        });
        return paramTask;
      }
      paramTask = paramTask.onSuccessTask(new Continuation()
      {
        public Task<Void> then(Task<Void> paramAnonymousTask)
          throws Exception
        {
          ParseUser.getUserController().signUpAsync(ParseUser.this.getState(), this.val$operations, str1).continueWithTask(new Continuation()
          {
            public Task<Void> then(final Task<ParseUser.State> paramAnonymous2Task)
              throws Exception
            {
              ParseUser.State localState = (ParseUser.State)paramAnonymous2Task.getResult();
              ParseUser.this.handleSaveResultAsync(localState, ParseUser.8.this.val$operations).continueWithTask(new Continuation()
              {
                public Task<Void> then(Task<Void> paramAnonymous3Task)
                  throws Exception
                {
                  if ((!paramAnonymous2Task.isCancelled()) && (!paramAnonymous2Task.isFaulted())) {
                    return ParseUser.saveCurrentUserAsync(ParseUser.this);
                  }
                  return paramAnonymous2Task.makeVoid();
                }
              });
            }
          });
        }
      });
      return paramTask;
      final String str1 = null;
    }
  }
  
  public Task<Void> signUpInBackground()
  {
    this.taskQueue.enqueue(new Continuation()
    {
      public Task<Void> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        return ParseUser.this.signUpAsync(paramAnonymousTask);
      }
    });
  }
  
  public void signUpInBackground(SignUpCallback paramSignUpCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(signUpInBackground(), paramSignUpCallback);
  }
  
  Task<Void> synchronizeAllAuthDataAsync()
  {
    synchronized (this.mutex)
    {
      if (!isCurrentUser())
      {
        localObject2 = Task.forResult(null);
        return (Task<Void>)localObject2;
      }
      Object localObject2 = getAuthData();
      ??? = new ArrayList(((Map)localObject2).size());
      localObject2 = ((Map)localObject2).keySet().iterator();
      if (((Iterator)localObject2).hasNext()) {
        ((List)???).add(synchronizeAuthDataAsync((String)((Iterator)localObject2).next()));
      }
    }
    return Task.whenAll((Collection)???);
  }
  
  Task<Void> synchronizeAuthDataAsync(String paramString)
  {
    synchronized (this.mutex)
    {
      if (!isCurrentUser())
      {
        paramString = Task.forResult(null);
        return paramString;
      }
      Map localMap = getAuthData(paramString);
      return synchronizeAuthDataAsync(getAuthenticationManager(), paramString, localMap);
    }
  }
  
  JSONObject toRest(ParseObject.State paramState, List<ParseOperationSet> paramList, ParseEncoder paramParseEncoder)
  {
    Object localObject1 = paramList;
    int i = 0;
    while (i < paramList.size())
    {
      ParseOperationSet localParseOperationSet = (ParseOperationSet)paramList.get(i);
      Object localObject2 = localObject1;
      if (localParseOperationSet.containsKey("password"))
      {
        localObject2 = localObject1;
        if (localObject1 == paramList) {
          localObject2 = new LinkedList(paramList);
        }
        localObject1 = new ParseOperationSet(localParseOperationSet);
        ((ParseOperationSet)localObject1).remove("password");
        ((List)localObject2).set(i, localObject1);
      }
      i += 1;
      localObject1 = localObject2;
    }
    return super.toRest(paramState, (List)localObject1, paramParseEncoder);
  }
  
  public Task<Void> unlinkFromInBackground(String paramString)
  {
    if (paramString == null) {
      return Task.forResult(null);
    }
    synchronized (this.mutex)
    {
      if (!getAuthData().containsKey(paramString))
      {
        paramString = Task.forResult(null);
        return paramString;
      }
    }
    putAuthData(paramString, null);
    return saveInBackground();
  }
  
  Task<Void> upgradeToRevocableSessionAsync()
  {
    this.taskQueue.enqueue(new Continuation()
    {
      public Task<Void> then(Task<Void> paramAnonymousTask)
        throws Exception
      {
        return ParseUser.this.upgradeToRevocableSessionAsync(paramAnonymousTask);
      }
    });
  }
  
  void validateDelete()
  {
    synchronized (this.mutex)
    {
      super.validateDelete();
      if ((!isAuthenticated()) && (isDirty())) {
        throw new IllegalArgumentException("Cannot delete a ParseUser that is not authenticated.");
      }
    }
  }
  
  void validateSave()
  {
    synchronized (this.mutex)
    {
      if (getObjectId() == null) {
        throw new IllegalArgumentException("Cannot save a ParseUser until it has been signed up. Call signUp first.");
      }
    }
    if ((isAuthenticated()) || (!isDirty()) || (isCurrentUser())) {
      return;
    }
    if (!Parse.isLocalDatastoreEnabled())
    {
      ??? = getCurrentUser();
      if ((??? != null) && (getObjectId().equals(((ParseUser)???).getObjectId()))) {}
    }
    else
    {
      throw new IllegalArgumentException("Cannot save a ParseUser that is not authenticated.");
    }
  }
  
  void validateSaveEventually()
    throws ParseException
  {
    if (isDirty("password")) {
      throw new ParseException(-1, "Unable to saveEventually on a ParseUser with dirty password");
    }
  }
  
  static class State
    extends ParseObject.State
  {
    private final boolean isNew;
    
    private State(Builder paramBuilder)
    {
      super();
      this.isNew = paramBuilder.isNew;
    }
    
    public Map<String, Map<String, String>> authData()
    {
      Map localMap = (Map)get("authData");
      Object localObject = localMap;
      if (localMap == null) {
        localObject = new HashMap();
      }
      return (Map<String, Map<String, String>>)localObject;
    }
    
    public boolean isNew()
    {
      return this.isNew;
    }
    
    public Builder newBuilder()
    {
      return new Builder(this);
    }
    
    public String sessionToken()
    {
      return (String)get("sessionToken");
    }
    
    static class Builder
      extends ParseObject.State.Init<Builder>
    {
      private boolean isNew;
      
      public Builder()
      {
        super();
      }
      
      Builder(ParseUser.State paramState)
      {
        super();
        this.isNew = paramState.isNew();
      }
      
      public Builder apply(ParseObject.State paramState)
      {
        isNew(((ParseUser.State)paramState).isNew());
        return (Builder)super.apply(paramState);
      }
      
      public Builder authData(Map<String, Map<String, String>> paramMap)
      {
        return (Builder)put("authData", paramMap);
      }
      
      public ParseUser.State build()
      {
        return new ParseUser.State(this, null);
      }
      
      public Builder isNew(boolean paramBoolean)
      {
        this.isNew = paramBoolean;
        return this;
      }
      
      public Builder putAuthData(String paramString, Map<String, String> paramMap)
      {
        Map localMap = (Map)this.serverData.get("authData");
        Object localObject = localMap;
        if (localMap == null) {
          localObject = new HashMap();
        }
        ((Map)localObject).put(paramString, paramMap);
        this.serverData.put("authData", localObject);
        return this;
      }
      
      Builder self()
      {
        return this;
      }
      
      public Builder sessionToken(String paramString)
      {
        return (Builder)put("sessionToken", paramString);
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseUser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */