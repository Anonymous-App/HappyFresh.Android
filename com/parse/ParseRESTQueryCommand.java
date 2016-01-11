package com.parse;

import com.parse.http.ParseHttpRequest.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONObject;

class ParseRESTQueryCommand
  extends ParseRESTCommand
{
  private ParseRESTQueryCommand(String paramString1, ParseHttpRequest.Method paramMethod, Map<String, ?> paramMap, String paramString2)
  {
    super(paramString1, paramMethod, paramMap, paramString2);
  }
  
  public static <T extends ParseObject> ParseRESTQueryCommand countCommand(ParseQuery.State<T> paramState, String paramString)
  {
    String str = String.format("classes/%s", new Object[] { paramState.className() });
    paramState = encode(paramState, true);
    return new ParseRESTQueryCommand(str, ParseHttpRequest.Method.GET, paramState, paramString);
  }
  
  static <T extends ParseObject> Map<String, String> encode(ParseQuery.State<T> paramState, boolean paramBoolean)
  {
    PointerEncoder localPointerEncoder = PointerEncoder.get();
    HashMap localHashMap = new HashMap();
    Object localObject1 = paramState.order();
    if (!((List)localObject1).isEmpty()) {
      localHashMap.put("order", ParseTextUtils.join(",", (Iterable)localObject1));
    }
    localObject1 = paramState.constraints();
    if (!((ParseQuery.QueryConstraints)localObject1).isEmpty()) {
      localHashMap.put("where", ((JSONObject)localPointerEncoder.encode(localObject1)).toString());
    }
    localObject1 = paramState.selectedKeys();
    if (localObject1 != null) {
      localHashMap.put("keys", ParseTextUtils.join(",", (Iterable)localObject1));
    }
    localObject1 = paramState.includes();
    if (!((Set)localObject1).isEmpty()) {
      localHashMap.put("include", ParseTextUtils.join(",", (Iterable)localObject1));
    }
    if (paramBoolean) {
      localHashMap.put("count", Integer.toString(1));
    }
    for (;;)
    {
      localObject1 = paramState.extraOptions().entrySet().iterator();
      while (((Iterator)localObject1).hasNext())
      {
        Map.Entry localEntry = (Map.Entry)((Iterator)localObject1).next();
        Object localObject2 = localPointerEncoder.encode(localEntry.getValue());
        localHashMap.put(localEntry.getKey(), localObject2.toString());
      }
      int i = paramState.limit();
      if (i >= 0) {
        localHashMap.put("limit", Integer.toString(i));
      }
      i = paramState.skip();
      if (i > 0) {
        localHashMap.put("skip", Integer.toString(i));
      }
    }
    if (paramState.isTracingEnabled()) {
      localHashMap.put("trace", Integer.toString(1));
    }
    return localHashMap;
  }
  
  public static <T extends ParseObject> ParseRESTQueryCommand findCommand(ParseQuery.State<T> paramState, String paramString)
  {
    String str = String.format("classes/%s", new Object[] { paramState.className() });
    paramState = encode(paramState, false);
    return new ParseRESTQueryCommand(str, ParseHttpRequest.Method.GET, paramState, paramString);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseRESTQueryCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */