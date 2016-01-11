package com.ad4screen.sdk.common.persistence.adapters;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class a<T>
  extends com.ad4screen.sdk.common.persistence.adapters.model.a<ArrayList<T>>
{
  private final String a = "java.util.ArrayList";
  private final String b = "item";
  
  public String a()
  {
    return "java.util.ArrayList";
  }
  
  public ArrayList<T> a(String paramString)
    throws JSONException
  {
    Object localObject = new JSONObject(paramString);
    paramString = new ArrayList();
    localObject = ((JSONObject)localObject).getJSONArray("java.util.ArrayList");
    int i = 0;
    while (i < ((JSONArray)localObject).length())
    {
      paramString.add(((JSONArray)localObject).getJSONObject(i).get("item"));
      i += 1;
    }
    return paramString;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/persistence/adapters/a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */