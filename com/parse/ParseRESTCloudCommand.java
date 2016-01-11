package com.parse;

import com.parse.http.ParseHttpRequest.Method;
import java.util.Map;

class ParseRESTCloudCommand
  extends ParseRESTCommand
{
  private ParseRESTCloudCommand(String paramString1, ParseHttpRequest.Method paramMethod, Map<String, ?> paramMap, String paramString2)
  {
    super(paramString1, paramMethod, paramMap, paramString2);
  }
  
  public static ParseRESTCloudCommand callFunctionCommand(String paramString1, Map<String, ?> paramMap, String paramString2)
  {
    return new ParseRESTCloudCommand(String.format("functions/%s", new Object[] { paramString1 }), ParseHttpRequest.Method.POST, paramMap, paramString2);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseRESTCloudCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */