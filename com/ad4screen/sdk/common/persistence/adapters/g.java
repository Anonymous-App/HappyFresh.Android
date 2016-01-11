package com.ad4screen.sdk.common.persistence.adapters;

import com.ad4screen.sdk.common.persistence.adapters.model.a;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class g<T, V>
  extends a<HashMap<T, V>>
{
  private final String a = "java.util.HashMap";
  private final String b = "key";
  private final String c = "value";
  
  public String a()
  {
    return "java.util.HashMap";
  }
  
  public HashMap<T, V> a(String paramString)
    throws JSONException
  {
    Object localObject = new JSONObject(paramString);
    paramString = new HashMap();
    localObject = ((JSONObject)localObject).getJSONArray("java.util.HashMap");
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


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/persistence/adapters/g.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */