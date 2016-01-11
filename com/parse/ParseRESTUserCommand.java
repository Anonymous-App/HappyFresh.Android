package com.parse;

import bolts.Task;
import com.parse.http.ParseHttpRequest.Builder;
import com.parse.http.ParseHttpRequest.Method;
import com.parse.http.ParseHttpResponse;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

class ParseRESTUserCommand
  extends ParseRESTCommand
{
  private static final String HEADER_REVOCABLE_SESSION = "X-Parse-Revocable-Session";
  private static final String HEADER_TRUE = "1";
  private boolean isRevocableSessionEnabled;
  private int statusCode;
  
  private ParseRESTUserCommand(String paramString1, ParseHttpRequest.Method paramMethod, Map<String, ?> paramMap, String paramString2)
  {
    this(paramString1, paramMethod, paramMap, paramString2, false);
  }
  
  private ParseRESTUserCommand(String paramString1, ParseHttpRequest.Method paramMethod, Map<String, ?> paramMap, String paramString2, boolean paramBoolean)
  {
    super(paramString1, paramMethod, paramMap, paramString2);
    this.isRevocableSessionEnabled = paramBoolean;
  }
  
  private ParseRESTUserCommand(String paramString1, ParseHttpRequest.Method paramMethod, JSONObject paramJSONObject, String paramString2, boolean paramBoolean)
  {
    super(paramString1, paramMethod, paramJSONObject, paramString2);
    this.isRevocableSessionEnabled = paramBoolean;
  }
  
  public static ParseRESTUserCommand getCurrentUserCommand(String paramString)
  {
    return new ParseRESTUserCommand("users/me", ParseHttpRequest.Method.GET, null, paramString);
  }
  
  public static ParseRESTUserCommand logInUserCommand(String paramString1, String paramString2, boolean paramBoolean)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("username", paramString1);
    localHashMap.put("password", paramString2);
    return new ParseRESTUserCommand("login", ParseHttpRequest.Method.GET, localHashMap, null, paramBoolean);
  }
  
  public static ParseRESTUserCommand resetPasswordResetCommand(String paramString)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("email", paramString);
    return new ParseRESTUserCommand("requestPasswordReset", ParseHttpRequest.Method.POST, localHashMap, null);
  }
  
  public static ParseRESTUserCommand serviceLogInUserCommand(String paramString, Map<String, String> paramMap, boolean paramBoolean)
  {
    try
    {
      JSONObject localJSONObject = new JSONObject();
      localJSONObject.put(paramString, PointerEncoder.get().encode(paramMap));
      paramString = new JSONObject();
      paramString.put("authData", localJSONObject);
      return serviceLogInUserCommand(paramString, null, paramBoolean);
    }
    catch (JSONException paramString)
    {
      throw new RuntimeException("could not serialize object to JSON");
    }
  }
  
  public static ParseRESTUserCommand serviceLogInUserCommand(JSONObject paramJSONObject, String paramString, boolean paramBoolean)
  {
    return new ParseRESTUserCommand("users", ParseHttpRequest.Method.POST, paramJSONObject, paramString, paramBoolean);
  }
  
  public static ParseRESTUserCommand signUpUserCommand(JSONObject paramJSONObject, String paramString, boolean paramBoolean)
  {
    return new ParseRESTUserCommand("classes/_User", ParseHttpRequest.Method.POST, paramJSONObject, paramString, paramBoolean);
  }
  
  protected void addAdditionalHeaders(ParseHttpRequest.Builder paramBuilder)
  {
    super.addAdditionalHeaders(paramBuilder);
    if (this.isRevocableSessionEnabled) {
      paramBuilder.addHeader("X-Parse-Revocable-Session", "1");
    }
  }
  
  public int getStatusCode()
  {
    return this.statusCode;
  }
  
  protected Task<JSONObject> onResponseAsync(ParseHttpResponse paramParseHttpResponse, ProgressCallback paramProgressCallback)
  {
    this.statusCode = paramParseHttpResponse.getStatusCode();
    return super.onResponseAsync(paramParseHttpResponse, paramProgressCallback);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseRESTUserCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */