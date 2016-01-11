package com.ad4screen.sdk.service.modules.inapp.model.events.composite;

import com.ad4screen.sdk.common.persistence.e;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class a
  extends com.ad4screen.sdk.service.modules.inapp.model.events.a
{
  protected List<com.ad4screen.sdk.service.modules.inapp.model.events.a> a;
  
  public a() {}
  
  public a(List<com.ad4screen.sdk.service.modules.inapp.model.events.a> paramList)
  {
    this.a = paramList;
  }
  
  public Long a()
  {
    return null;
  }
  
  public a b(String paramString)
    throws JSONException
  {
    e locale = new e();
    a[] arrayOfa = new a[2];
    arrayOfa[0] = new b();
    arrayOfa[1] = new c();
    paramString = new JSONObject(paramString);
    this.a = new ArrayList();
    int i = 0;
    while (i < arrayOfa.length)
    {
      if (!paramString.isNull(arrayOfa[i].getClassKey()))
      {
        JSONArray localJSONArray = paramString.getJSONArray(arrayOfa[i].getClassKey());
        int j = 0;
        while (j < localJSONArray.length())
        {
          JSONObject localJSONObject = localJSONArray.getJSONObject(j);
          this.a.add(locale.a(localJSONObject.toString(), new com.ad4screen.sdk.service.modules.inapp.model.events.a()));
          j += 1;
        }
      }
      i += 1;
    }
    return this;
  }
  
  public String b()
  {
    return null;
  }
  
  public List<com.ad4screen.sdk.service.modules.inapp.model.events.a> c()
  {
    return this.a;
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    e locale = new e();
    JSONObject localJSONObject = new JSONObject();
    JSONArray localJSONArray = new JSONArray();
    Iterator localIterator = this.a.iterator();
    while (localIterator.hasNext()) {
      localJSONArray.put(locale.a((com.ad4screen.sdk.service.modules.inapp.model.events.a)localIterator.next()));
    }
    localJSONObject.put(getClassKey(), localJSONArray);
    return localJSONObject;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/model/events/composite/a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */