package com.facebook.internal;

import com.facebook.FacebookException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GraphUtil
{
  private static final String[] dateFormats = { "yyyy-MM-dd'T'HH:mm:ssZ", "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd" };
  
  public static JSONObject createOpenGraphActionForPost(String paramString)
  {
    JSONObject localJSONObject = new JSONObject();
    if (paramString != null) {}
    try
    {
      localJSONObject.put("type", paramString);
      return localJSONObject;
    }
    catch (JSONException paramString)
    {
      throw new FacebookException("An error occurred while setting up the open graph action", paramString);
    }
  }
  
  public static JSONObject createOpenGraphObjectForPost(String paramString)
  {
    return createOpenGraphObjectForPost(paramString, null, null, null, null, null, null);
  }
  
  public static JSONObject createOpenGraphObjectForPost(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, JSONObject paramJSONObject, String paramString6)
  {
    JSONObject localJSONObject = new JSONObject();
    if (paramString1 != null) {}
    try
    {
      localJSONObject.put("type", paramString1);
      localJSONObject.put("title", paramString2);
      if (paramString3 != null)
      {
        paramString1 = new JSONObject();
        paramString1.put("url", paramString3);
        paramString2 = new JSONArray();
        paramString2.put(paramString1);
        localJSONObject.put("image", paramString2);
      }
      localJSONObject.put("url", paramString4);
      localJSONObject.put("description", paramString5);
      localJSONObject.put("fbsdk:create_object", true);
      if (paramJSONObject != null) {
        localJSONObject.put("data", paramJSONObject);
      }
      if (paramString6 != null) {
        localJSONObject.put("id", paramString6);
      }
      return localJSONObject;
    }
    catch (JSONException paramString1)
    {
      throw new FacebookException("An error occurred while setting up the graph object", paramString1);
    }
  }
  
  public static boolean isOpenGraphObjectForPost(JSONObject paramJSONObject)
  {
    if (paramJSONObject != null) {
      return paramJSONObject.optBoolean("fbsdk:create_object");
    }
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/internal/GraphUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */