package com.parse;

import bolts.Continuation;
import bolts.Task;
import java.util.Map;
import org.json.JSONObject;

class NetworkUserController
  implements ParseUserController
{
  private static final int STATUS_CODE_CREATED = 201;
  private final ParseHttpClient client;
  private final ParseObjectCoder coder;
  private final boolean revocableSession;
  
  public NetworkUserController(ParseHttpClient paramParseHttpClient)
  {
    this(paramParseHttpClient, false);
  }
  
  public NetworkUserController(ParseHttpClient paramParseHttpClient, boolean paramBoolean)
  {
    this.client = paramParseHttpClient;
    this.coder = ParseObjectCoder.get();
    this.revocableSession = paramBoolean;
  }
  
  public Task<ParseUser.State> getUserAsync(String paramString)
  {
    ParseRESTUserCommand.getCurrentUserCommand(paramString).executeAsync(this.client).onSuccess(new Continuation()
    {
      public ParseUser.State then(Task<JSONObject> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = (JSONObject)paramAnonymousTask.getResult();
        return ((ParseUser.State.Builder)((ParseUser.State.Builder)NetworkUserController.this.coder.decode(new ParseUser.State.Builder(), paramAnonymousTask, ParseDecoder.get())).isComplete(true)).build();
      }
    });
  }
  
  public Task<ParseUser.State> logInAsync(final ParseUser.State paramState, ParseOperationSet paramParseOperationSet)
  {
    paramState = ParseRESTUserCommand.serviceLogInUserCommand(this.coder.encode(paramState, paramParseOperationSet, PointerEncoder.get()), paramState.sessionToken(), this.revocableSession);
    paramState.executeAsync(this.client).onSuccess(new Continuation()
    {
      public ParseUser.State then(Task<JSONObject> paramAnonymousTask)
        throws Exception
      {
        boolean bool2 = true;
        paramAnonymousTask = (JSONObject)paramAnonymousTask.getResult();
        boolean bool1;
        if (paramState.getStatusCode() == 201)
        {
          bool1 = true;
          if (bool1) {
            break label73;
          }
        }
        for (;;)
        {
          return ((ParseUser.State.Builder)((ParseUser.State.Builder)NetworkUserController.this.coder.decode(new ParseUser.State.Builder(), paramAnonymousTask, ParseDecoder.get())).isComplete(bool2)).isNew(bool1).build();
          bool1 = false;
          break;
          label73:
          bool2 = false;
        }
      }
    });
  }
  
  public Task<ParseUser.State> logInAsync(String paramString1, String paramString2)
  {
    ParseRESTUserCommand.logInUserCommand(paramString1, paramString2, this.revocableSession).executeAsync(this.client).onSuccess(new Continuation()
    {
      public ParseUser.State then(Task<JSONObject> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = (JSONObject)paramAnonymousTask.getResult();
        return ((ParseUser.State.Builder)((ParseUser.State.Builder)NetworkUserController.this.coder.decode(new ParseUser.State.Builder(), paramAnonymousTask, ParseDecoder.get())).isComplete(true)).build();
      }
    });
  }
  
  public Task<ParseUser.State> logInAsync(final String paramString, final Map<String, String> paramMap)
  {
    final ParseRESTUserCommand localParseRESTUserCommand = ParseRESTUserCommand.serviceLogInUserCommand(paramString, paramMap, this.revocableSession);
    localParseRESTUserCommand.executeAsync(this.client).onSuccess(new Continuation()
    {
      public ParseUser.State then(Task<JSONObject> paramAnonymousTask)
        throws Exception
      {
        boolean bool = true;
        paramAnonymousTask = (JSONObject)paramAnonymousTask.getResult();
        paramAnonymousTask = (ParseUser.State.Builder)((ParseUser.State.Builder)NetworkUserController.this.coder.decode(new ParseUser.State.Builder(), paramAnonymousTask, ParseDecoder.get())).isComplete(true);
        if (localParseRESTUserCommand.getStatusCode() == 201) {}
        for (;;)
        {
          return paramAnonymousTask.isNew(bool).putAuthData(paramString, paramMap).build();
          bool = false;
        }
      }
    });
  }
  
  public Task<Void> requestPasswordResetAsync(String paramString)
  {
    return ParseRESTUserCommand.resetPasswordResetCommand(paramString).executeAsync(this.client).makeVoid();
  }
  
  public Task<ParseUser.State> signUpAsync(ParseObject.State paramState, ParseOperationSet paramParseOperationSet, String paramString)
  {
    ParseRESTUserCommand.signUpUserCommand(this.coder.encode(paramState, paramParseOperationSet, PointerEncoder.get()), paramString, this.revocableSession).executeAsync(this.client).onSuccess(new Continuation()
    {
      public ParseUser.State then(Task<JSONObject> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = (JSONObject)paramAnonymousTask.getResult();
        return ((ParseUser.State.Builder)((ParseUser.State.Builder)NetworkUserController.this.coder.decode(new ParseUser.State.Builder(), paramAnonymousTask, ParseDecoder.get())).isComplete(false)).isNew(true).build();
      }
    });
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/NetworkUserController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */