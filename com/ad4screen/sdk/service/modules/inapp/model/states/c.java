package com.ad4screen.sdk.service.modules.inapp.model.states;

import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public final class c
  extends a
{
  public String a;
  public String b;
  
  public c() {}
  
  public c(String paramString1, String paramString2)
  {
    this.a = paramString1;
    this.b = paramString2;
  }
  
  public boolean a(Map<String, c> paramMap)
  {
    paramMap = (c)paramMap.get(this.a);
    if (paramMap == null) {
      return false;
    }
    if ((this.a == null) || (this.b == null)) {
      return false;
    }
    if (!this.a.equals(paramMap.a)) {
      return false;
    }
    return this.b.equalsIgnoreCase(paramMap.b);
  }
  
  public c b(String paramString)
    throws JSONException
  {
    paramString = new JSONObject(paramString).getJSONObject("com.ad4screen.sdk.service.modules.inapp.model.states.State");
    this.a = paramString.getString("name");
    this.b = paramString.getString("id");
    return this;
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.inapp.model.states.State";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = new JSONObject();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("name", this.a);
    localJSONObject2.put("id", this.b);
    localJSONObject1.put("com.ad4screen.sdk.service.modules.inapp.model.states.State", localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/model/states/c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */