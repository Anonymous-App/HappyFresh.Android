package com.ad4screen.sdk.common.persistence.adapters;

import com.ad4screen.sdk.common.persistence.adapters.model.b;
import com.ad4screen.sdk.common.persistence.d;
import com.ad4screen.sdk.common.persistence.e;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class f<T, V>
  extends b<ConcurrentHashMap<T, V>>
{
  private final String a = "java.util.concurrent.ConcurrentHashMap";
  private final String b = "key";
  private final String c = "value";
  
  public JSONObject a(ConcurrentHashMap<T, V> paramConcurrentHashMap)
    throws JSONException
  {
    JSONObject localJSONObject1 = new JSONObject();
    JSONArray localJSONArray = new JSONArray();
    Iterator localIterator = paramConcurrentHashMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      JSONObject localJSONObject2 = new JSONObject();
      if ((localObject instanceof d))
      {
        JSONObject localJSONObject3 = new e().a(localObject);
        if (localJSONObject3 == null) {
          continue;
        }
        localJSONObject2.put("key", localJSONObject3);
        label92:
        if (!(paramConcurrentHashMap.get(localObject) instanceof d)) {
          break label160;
        }
        localObject = new e().a(paramConcurrentHashMap.get(localObject));
        if (localObject == null) {
          continue;
        }
        localJSONObject2.put("value", localObject);
      }
      for (;;)
      {
        localJSONArray.put(localJSONObject2);
        break;
        localJSONObject2.put("key", localObject);
        break label92;
        label160:
        localObject = paramConcurrentHashMap.get(localObject);
        if (localObject == null) {
          break;
        }
        localJSONObject2.put("value", localObject);
      }
    }
    localJSONObject1.put("type", "java.util.concurrent.ConcurrentHashMap");
    localJSONObject1.put("java.util.concurrent.ConcurrentHashMap", localJSONArray);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/persistence/adapters/f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */