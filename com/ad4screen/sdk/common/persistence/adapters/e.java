package com.ad4screen.sdk.common.persistence.adapters;

import com.ad4screen.sdk.common.persistence.adapters.model.a;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class e<T, V>
  extends a<ConcurrentHashMap<T, V>>
{
  private final String a = "java.util.concurrent.ConcurrentHashMap";
  private final String b = "key";
  private final String c = "value";
  
  public String a()
  {
    return "java.util.concurrent.ConcurrentHashMap";
  }
  
  public ConcurrentHashMap<T, V> a(String paramString)
    throws JSONException
  {
    Object localObject = new JSONObject(paramString);
    paramString = new ConcurrentHashMap();
    localObject = ((JSONObject)localObject).getJSONArray("java.util.concurrent.ConcurrentHashMap");
    int i = 0;
    while (i < ((JSONArray)localObject).length())
    {
      JSONObject localJSONObject = ((JSONArray)localObject).getJSONObject(i);
      paramString.put(localJSONObject.get("key"), localJSONObject.get("value"));
      i += 1;
    }
    return paramString;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/persistence/adapters/e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */