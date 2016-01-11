package com.parse;

import android.net.Uri;
import com.parse.http.ParseHttpRequest.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

class ParseRESTAnalyticsCommand
  extends ParseRESTCommand
{
  static final String EVENT_APP_OPENED = "AppOpened";
  
  public ParseRESTAnalyticsCommand(String paramString1, ParseHttpRequest.Method paramMethod, Map<String, ?> paramMap, String paramString2)
  {
    super(paramString1, paramMethod, paramMap, paramString2);
  }
  
  public static ParseRESTAnalyticsCommand trackAppOpenedCommand(String paramString1, String paramString2)
  {
    HashMap localHashMap = null;
    if (paramString1 != null)
    {
      localHashMap = new HashMap();
      localHashMap.put("push_hash", paramString1);
    }
    return trackEventCommand("AppOpened", localHashMap, paramString2);
  }
  
  static ParseRESTAnalyticsCommand trackEventCommand(String paramString1, Map<String, ?> paramMap, String paramString2)
  {
    paramString1 = String.format("events/%s", new Object[] { Uri.encode(paramString1) });
    HashMap localHashMap = new HashMap();
    if (paramMap != null) {
      localHashMap.putAll(paramMap);
    }
    localHashMap.put("at", NoObjectsEncoder.get().encode(new Date()));
    return new ParseRESTAnalyticsCommand(paramString1, ParseHttpRequest.Method.POST, localHashMap, paramString2);
  }
  
  public static ParseRESTAnalyticsCommand trackEventCommand(String paramString1, JSONObject paramJSONObject, String paramString2)
  {
    HashMap localHashMap = null;
    if (paramJSONObject != null)
    {
      localHashMap = new HashMap();
      localHashMap.put("dimensions", paramJSONObject);
    }
    return trackEventCommand(paramString1, localHashMap, paramString2);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseRESTAnalyticsCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */