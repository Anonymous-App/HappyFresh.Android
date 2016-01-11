package com.parse;

import com.parse.http.ParseHttpRequest.Method;
import org.json.JSONObject;

class ParseRESTSessionCommand
  extends ParseRESTCommand
{
  private ParseRESTSessionCommand(String paramString1, ParseHttpRequest.Method paramMethod, JSONObject paramJSONObject, String paramString2)
  {
    super(paramString1, paramMethod, paramJSONObject, paramString2);
  }
  
  public static ParseRESTSessionCommand getCurrentSessionCommand(String paramString)
  {
    return new ParseRESTSessionCommand("sessions/me", ParseHttpRequest.Method.GET, null, paramString);
  }
  
  public static ParseRESTSessionCommand revoke(String paramString)
  {
    return new ParseRESTSessionCommand("logout", ParseHttpRequest.Method.POST, new JSONObject(), paramString);
  }
  
  public static ParseRESTSessionCommand upgradeToRevocableSessionCommand(String paramString)
  {
    return new ParseRESTSessionCommand("upgradeToRevocableSession", ParseHttpRequest.Method.POST, new JSONObject(), paramString);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseRESTSessionCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */