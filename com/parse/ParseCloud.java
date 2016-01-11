package com.parse;

import bolts.Continuation;
import bolts.Task;
import java.util.Map;

public final class ParseCloud
{
  public static <T> T callFunction(String paramString, Map<String, ?> paramMap)
    throws ParseException
  {
    return (T)ParseTaskUtils.wait(callFunctionInBackground(paramString, paramMap));
  }
  
  public static <T> Task<T> callFunctionInBackground(String paramString, final Map<String, ?> paramMap)
  {
    ParseUser.getCurrentSessionTokenAsync().onSuccessTask(new Continuation()
    {
      public Task<T> then(Task<String> paramAnonymousTask)
        throws Exception
      {
        paramAnonymousTask = (String)paramAnonymousTask.getResult();
        return ParseCloud.getCloudCodeController().callFunctionInBackground(this.val$name, paramMap, paramAnonymousTask);
      }
    });
  }
  
  public static <T> void callFunctionInBackground(String paramString, Map<String, ?> paramMap, FunctionCallback<T> paramFunctionCallback)
  {
    ParseTaskUtils.callbackOnMainThreadAsync(callFunctionInBackground(paramString, paramMap), paramFunctionCallback);
  }
  
  static ParseCloudCodeController getCloudCodeController()
  {
    return ParseCorePlugins.getInstance().getCloudCodeController();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseCloud.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */