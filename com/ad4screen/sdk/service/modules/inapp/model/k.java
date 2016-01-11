package com.ad4screen.sdk.service.modules.inapp.model;

import com.ad4screen.sdk.common.persistence.c;
import com.ad4screen.sdk.common.persistence.d;
import org.json.JSONException;
import org.json.JSONObject;

public class k
  implements c<k>, d
{
  private String a;
  
  public k() {}
  
  public k(String paramString)
  {
    this.a = paramString;
  }
  
  public k a(String paramString)
    throws JSONException
  {
    this.a = new JSONObject(paramString).getJSONObject(getClassKey()).getString("name");
    return this;
  }
  
  public String a()
  {
    return this.a;
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.inapp.model.ViewRule";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = new JSONObject();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("name", this.a);
    localJSONObject1.put(getClassKey(), localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/model/k.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */