package com.parse;

import bolts.Continuation;
import bolts.Task;
import org.json.JSONObject;

class NetworkSessionController
  implements ParseSessionController
{
  private final ParseHttpClient client;
  private final ParseObjectCoder coder;
  
  public NetworkSessionController(ParseHttpClient paramParseHttpClient)
  {
    this.client = paramParseHttpClient;
    this.coder = ParseObjectCoder.get();
  }
  
  public Task<ParseObject.State> getSessionAsync(String paramString)
  {
    ParseRESTSessionCommand.getCurrentSessionCommand(paramString).executeAsync(this.client).onSuccess(new Continuation()
    {
      public ParseObject.State then(Task<JSONObject> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = (JSONObject)paramAnonymousTask.getResult();
        return ((ParseObject.State.Builder)((ParseObject.State.Builder)NetworkSessionController.this.coder.decode(new ParseObject.State.Builder("_Session"), paramAnonymousTask, ParseDecoder.get())).isComplete(true)).build();
      }
    });
  }
  
  public Task<Void> revokeAsync(String paramString)
  {
    return ParseRESTSessionCommand.revoke(paramString).executeAsync(this.client).makeVoid();
  }
  
  public Task<ParseObject.State> upgradeToRevocable(String paramString)
  {
    ParseRESTSessionCommand.upgradeToRevocableSessionCommand(paramString).executeAsync(this.client).onSuccess(new Continuation()
    {
      public ParseObject.State then(Task<JSONObject> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = (JSONObject)paramAnonymousTask.getResult();
        return ((ParseObject.State.Builder)((ParseObject.State.Builder)NetworkSessionController.this.coder.decode(new ParseObject.State.Builder("_Session"), paramAnonymousTask, ParseDecoder.get())).isComplete(true)).build();
      }
    });
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/NetworkSessionController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */