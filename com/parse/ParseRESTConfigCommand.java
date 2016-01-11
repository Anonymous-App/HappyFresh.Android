package com.parse;

import com.parse.http.ParseHttpRequest.Method;
import java.util.HashMap;
import java.util.Map;

class ParseRESTConfigCommand
  extends ParseRESTCommand
{
  public ParseRESTConfigCommand(String paramString1, ParseHttpRequest.Method paramMethod, Map<String, ?> paramMap, String paramString2)
  {
    super(paramString1, paramMethod, paramMap, paramString2);
  }
  
  public static ParseRESTConfigCommand fetchConfigCommand(String paramString)
  {
    return new ParseRESTConfigCommand("config", ParseHttpRequest.Method.GET, null, paramString);
  }
  
  public static ParseRESTConfigCommand updateConfigCommand(Map<String, ?> paramMap, String paramString)
  {
    HashMap localHashMap = null;
    if (paramMap != null)
    {
      localHashMap = new HashMap();
      localHashMap.put("params", paramMap);
    }
    return new ParseRESTConfigCommand("config", ParseHttpRequest.Method.PUT, localHashMap, paramString);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseRESTConfigCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */