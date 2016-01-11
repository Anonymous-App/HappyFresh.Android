package com.ad4screen.sdk.service.modules.inapp.model;

import com.ad4screen.sdk.common.persistence.c;
import com.ad4screen.sdk.common.persistence.d;
import org.json.JSONException;
import org.json.JSONObject;

public class b
  implements c<b>, d
{
  private String a;
  private String b;
  private String c;
  
  public String a()
  {
    return this.a;
  }
  
  public void a(String paramString)
  {
    this.a = paramString;
  }
  
  public String b()
  {
    return this.b;
  }
  
  public void b(String paramString)
  {
    this.b = paramString;
  }
  
  public String c()
  {
    return this.c;
  }
  
  public void c(String paramString)
  {
    this.c = paramString;
  }
  
  public b d(String paramString)
    throws JSONException
  {
    paramString = new JSONObject(paramString).getJSONObject(getClassKey());
    this.a = paramString.getString("id");
    this.b = paramString.getString("transition");
    if (!paramString.isNull("accuracy")) {
      this.c = paramString.getString("accuracy");
    }
    return this;
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.inapp.model.BeaconRule";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = new JSONObject();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("id", this.a);
    localJSONObject2.put("transition", this.b);
    localJSONObject2.put("accuracy", this.c);
    localJSONObject1.put(getClassKey(), localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/model/b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */