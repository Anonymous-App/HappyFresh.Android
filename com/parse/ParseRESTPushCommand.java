package com.parse;

import com.parse.http.ParseHttpRequest.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class ParseRESTPushCommand
  extends ParseRESTCommand
{
  static final String KEY_CHANNELS = "channels";
  static final String KEY_DATA = "data";
  static final String KEY_DEVICE_TYPE = "deviceType";
  static final String KEY_EXPIRATION_INTERVAL = "expiration_interval";
  static final String KEY_EXPIRATION_TIME = "expiration_time";
  static final String KEY_WHERE = "where";
  
  public ParseRESTPushCommand(String paramString1, ParseHttpRequest.Method paramMethod, Map<String, ?> paramMap, String paramString2)
  {
    super(paramString1, paramMethod, paramMap, paramString2);
  }
  
  public static ParseRESTPushCommand sendPushCommand(ParseQuery.State<ParseInstallation> paramState, Set<String> paramSet, String paramString1, Long paramLong1, Long paramLong2, JSONObject paramJSONObject, String paramString2)
  {
    HashMap localHashMap = new HashMap();
    if (paramSet != null)
    {
      localHashMap.put("channels", new JSONArray(paramSet));
      if (paramLong1 == null) {
        break label183;
      }
      localHashMap.put("expiration_time", paramLong1);
    }
    for (;;)
    {
      if (paramJSONObject != null) {
        localHashMap.put("data", paramJSONObject);
      }
      return new ParseRESTPushCommand("push", ParseHttpRequest.Method.POST, localHashMap, paramString2);
      if (paramState != null)
      {
        paramState = paramState.constraints();
        localHashMap.put("where", (JSONObject)PointerEncoder.get().encode(paramState));
      }
      if (paramString1 != null) {
        paramState = new JSONObject();
      }
      try
      {
        paramState.put("deviceType", paramString1);
        localHashMap.put("where", paramState);
        if (localHashMap.size() != 0) {
          break;
        }
        localHashMap.put("where", new JSONObject());
      }
      catch (JSONException paramState)
      {
        throw new RuntimeException(paramState.getMessage());
      }
      label183:
      if (paramLong2 != null) {
        localHashMap.put("expiration_interval", paramLong2);
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseRESTPushCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */