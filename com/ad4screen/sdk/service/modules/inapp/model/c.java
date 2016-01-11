package com.ad4screen.sdk.service.modules.inapp.model;

import org.json.JSONException;
import org.json.JSONObject;

public class c
  implements com.ad4screen.sdk.common.persistence.c<c>, com.ad4screen.sdk.common.persistence.d
{
  private String a;
  private d b;
  
  public c a(String paramString)
    throws JSONException
  {
    paramString = new JSONObject(paramString).getJSONObject(getClassKey());
    this.a = paramString.getString("id");
    if (!paramString.isNull("transition")) {
      paramString = paramString.getString("transition");
    }
    try
    {
      this.b = d.a(paramString);
      return this;
    }
    catch (IllegalArgumentException paramString)
    {
      this.b = null;
    }
    return this;
  }
  
  public String a()
  {
    return this.a;
  }
  
  public void a(d paramd)
  {
    this.b = paramd;
  }
  
  public d b()
  {
    return this.b;
  }
  
  public void b(String paramString)
  {
    this.a = paramString;
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.inapp.model.GeofenceRule";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = new JSONObject();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("id", this.a);
    if (this.b == null) {}
    for (String str = "";; str = this.b.a())
    {
      localJSONObject2.put("transition", str);
      localJSONObject1.put(getClassKey(), localJSONObject2);
      return localJSONObject1;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/model/c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */