package com.ad4screen.sdk.common.persistence.adapters;

import com.ad4screen.sdk.common.persistence.d;
import com.ad4screen.sdk.common.persistence.e;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class b<T>
  extends com.ad4screen.sdk.common.persistence.adapters.model.b<ArrayList<T>>
{
  private final String a = "java.util.ArrayList";
  private final String b = "item";
  
  public JSONObject a(ArrayList<T> paramArrayList)
    throws JSONException
  {
    JSONObject localJSONObject1 = new JSONObject();
    JSONArray localJSONArray = new JSONArray();
    paramArrayList = paramArrayList.iterator();
    if (paramArrayList.hasNext())
    {
      Object localObject = paramArrayList.next();
      JSONObject localJSONObject2 = new JSONObject();
      if ((localObject instanceof d)) {
        localJSONObject2.put("item", new e().a(localObject));
      }
      for (;;)
      {
        localJSONArray.put(localJSONObject2);
        break;
        localJSONObject2.put("item", localObject);
      }
    }
    localJSONObject1.put("type", "java.util.ArrayList");
    localJSONObject1.put("java.util.ArrayList", localJSONArray);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/persistence/adapters/b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */