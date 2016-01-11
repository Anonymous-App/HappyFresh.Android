package com.mixpanel.android.util;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtils
{
  public static String optionalStringKey(JSONObject paramJSONObject, String paramString)
    throws JSONException
  {
    if ((paramJSONObject.has(paramString)) && (!paramJSONObject.isNull(paramString))) {
      return paramJSONObject.getString(paramString);
    }
    return null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/util/JSONUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */