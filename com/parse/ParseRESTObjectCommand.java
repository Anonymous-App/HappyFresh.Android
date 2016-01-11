package com.parse;

import android.net.Uri;
import com.parse.http.ParseHttpRequest.Method;
import org.json.JSONObject;

class ParseRESTObjectCommand
  extends ParseRESTCommand
{
  public ParseRESTObjectCommand(String paramString1, ParseHttpRequest.Method paramMethod, JSONObject paramJSONObject, String paramString2)
  {
    super(paramString1, paramMethod, paramJSONObject, paramString2);
  }
  
  private static ParseRESTObjectCommand createObjectCommand(String paramString1, JSONObject paramJSONObject, String paramString2)
  {
    return new ParseRESTObjectCommand(String.format("classes/%s", new Object[] { Uri.encode(paramString1) }), ParseHttpRequest.Method.POST, paramJSONObject, paramString2);
  }
  
  public static ParseRESTObjectCommand deleteObjectCommand(ParseObject.State paramState, String paramString)
  {
    String str1 = String.format("classes/%s", new Object[] { Uri.encode(paramState.className()) });
    String str2 = paramState.objectId();
    paramState = str1;
    if (str2 != null) {
      paramState = str1 + String.format("/%s", new Object[] { Uri.encode(str2) });
    }
    return new ParseRESTObjectCommand(paramState, ParseHttpRequest.Method.DELETE, null, paramString);
  }
  
  public static ParseRESTObjectCommand getObjectCommand(String paramString1, String paramString2, String paramString3)
  {
    return new ParseRESTObjectCommand(String.format("classes/%s/%s", new Object[] { Uri.encode(paramString2), Uri.encode(paramString1) }), ParseHttpRequest.Method.GET, null, paramString3);
  }
  
  public static ParseRESTObjectCommand saveObjectCommand(ParseObject.State paramState, JSONObject paramJSONObject, String paramString)
  {
    if (paramState.objectId() == null) {
      return createObjectCommand(paramState.className(), paramJSONObject, paramString);
    }
    return updateObjectCommand(paramState.objectId(), paramState.className(), paramJSONObject, paramString);
  }
  
  private static ParseRESTObjectCommand updateObjectCommand(String paramString1, String paramString2, JSONObject paramJSONObject, String paramString3)
  {
    return new ParseRESTObjectCommand(String.format("classes/%s/%s", new Object[] { Uri.encode(paramString2), Uri.encode(paramString1) }), ParseHttpRequest.Method.PUT, paramJSONObject, paramString3);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseRESTObjectCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */