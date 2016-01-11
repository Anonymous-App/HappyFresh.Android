package com.parse;

import bolts.Continuation;
import bolts.Task;
import org.json.JSONObject;

class ParseConfigController
{
  private ParseCurrentConfigController currentConfigController;
  private final ParseHttpClient restClient;
  
  public ParseConfigController(ParseHttpClient paramParseHttpClient, ParseCurrentConfigController paramParseCurrentConfigController)
  {
    this.restClient = paramParseHttpClient;
    this.currentConfigController = paramParseCurrentConfigController;
  }
  
  public Task<ParseConfig> getAsync(String paramString)
  {
    paramString = ParseRESTConfigCommand.fetchConfigCommand(paramString);
    paramString.enableRetrying();
    paramString.executeAsync(this.restClient).onSuccessTask(new Continuation()
    {
      public Task<ParseConfig> then(final Task<JSONObject> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = new ParseConfig((JSONObject)paramAnonymousTask.getResult(), ParseDecoder.get());
        ParseConfigController.this.currentConfigController.setCurrentConfigAsync(paramAnonymousTask).continueWith(new Continuation()
        {
          public ParseConfig then(Task<Void> paramAnonymous2Task)
            throws Exception
          {
            return paramAnonymousTask;
          }
        });
      }
    });
  }
  
  ParseCurrentConfigController getCurrentConfigController()
  {
    return this.currentConfigController;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseConfigController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */