package com.parse;

import bolts.Continuation;
import bolts.Task;
import java.util.Map;
import org.json.JSONObject;

class ParseCloudCodeController
{
  final ParseHttpClient restClient;
  
  public ParseCloudCodeController(ParseHttpClient paramParseHttpClient)
  {
    this.restClient = paramParseHttpClient;
  }
  
  public <T> Task<T> callFunctionInBackground(String paramString1, Map<String, ?> paramMap, String paramString2)
  {
    ParseRESTCloudCommand.callFunctionCommand(paramString1, paramMap, paramString2).executeAsync(this.restClient).onSuccess(new Continuation()
    {
      public T then(Task<JSONObject> paramAnonymousTask)
        throws Exception
      {
        return (T)ParseCloudCodeController.this.convertCloudResponse(paramAnonymousTask.getResult());
      }
    });
  }
  
  Object convertCloudResponse(Object paramObject)
  {
    Object localObject = paramObject;
    if ((paramObject instanceof JSONObject)) {
      localObject = ((JSONObject)paramObject).opt("result");
    }
    paramObject = ParseDecoder.get().decode(localObject);
    if (paramObject != null) {
      return paramObject;
    }
    return localObject;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseCloudCodeController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */