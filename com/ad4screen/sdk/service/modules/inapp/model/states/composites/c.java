package com.ad4screen.sdk.service.modules.inapp.model.states.composites;

import com.ad4screen.sdk.common.persistence.e;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class c
  extends com.ad4screen.sdk.service.modules.inapp.model.states.a
{
  public List<com.ad4screen.sdk.service.modules.inapp.model.states.a> a = new ArrayList();
  private e b = new e();
  
  public com.ad4screen.sdk.service.modules.inapp.model.states.a a(String paramString)
    throws JSONException
  {
    c[] arrayOfc = new c[2];
    arrayOfc[0] = new a();
    arrayOfc[1] = new b();
    paramString = new JSONObject(paramString);
    int i = 0;
    while (i < arrayOfc.length)
    {
      if (!paramString.isNull(arrayOfc[i].getClassKey()))
      {
        JSONArray localJSONArray = paramString.getJSONArray(arrayOfc[i].getClassKey());
        int j = 0;
        while (j < localJSONArray.length())
        {
          JSONObject localJSONObject = localJSONArray.getJSONObject(j);
          this.a.add(this.b.a(localJSONObject.toString(), new com.ad4screen.sdk.service.modules.inapp.model.states.a()));
          j += 1;
        }
      }
      i += 1;
    }
    return this;
  }
  
  public boolean a(Map<String, com.ad4screen.sdk.service.modules.inapp.model.states.c> paramMap)
  {
    return false;
  }
  
  public String getClassKey()
  {
    return null;
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject = new JSONObject();
    JSONArray localJSONArray = new JSONArray();
    Iterator localIterator = this.a.iterator();
    while (localIterator.hasNext())
    {
      com.ad4screen.sdk.service.modules.inapp.model.states.a locala = (com.ad4screen.sdk.service.modules.inapp.model.states.a)localIterator.next();
      localJSONArray.put(this.b.a(locala));
    }
    localJSONObject.put(getClassKey(), localJSONArray);
    return localJSONObject;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/model/states/composites/c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */