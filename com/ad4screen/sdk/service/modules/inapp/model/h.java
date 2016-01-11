package com.ad4screen.sdk.service.modules.inapp.model;

import org.json.JSONException;
import org.json.JSONObject;

public class h
{
  public static Double a(String paramString, JSONObject paramJSONObject)
  {
    try
    {
      if (paramJSONObject.isNull(paramString)) {
        return null;
      }
      double d = paramJSONObject.getDouble(paramString);
      return Double.valueOf(d);
    }
    catch (JSONException paramString) {}
    return null;
  }
  
  public static String b(String paramString, JSONObject paramJSONObject)
  {
    try
    {
      paramString = paramJSONObject.getString(paramString);
      return paramString;
    }
    catch (JSONException paramString) {}
    return null;
  }
  
  public static Boolean c(String paramString, JSONObject paramJSONObject)
  {
    try
    {
      if (paramJSONObject.isNull(paramString)) {
        return Boolean.valueOf(false);
      }
      boolean bool = paramJSONObject.getBoolean(paramString);
      return Boolean.valueOf(bool);
    }
    catch (JSONException paramString) {}
    return Boolean.valueOf(false);
  }
  
  public static Integer d(String paramString, JSONObject paramJSONObject)
  {
    try
    {
      if (paramJSONObject.isNull(paramString)) {
        return null;
      }
      int i = paramJSONObject.getInt(paramString);
      return Integer.valueOf(i);
    }
    catch (JSONException paramString) {}
    return null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/model/h.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */