package com.ad4screen.sdk.service.modules.inapp.model;

import com.ad4screen.sdk.common.persistence.c;
import com.ad4screen.sdk.common.persistence.d;
import org.json.JSONException;
import org.json.JSONObject;

public class a
  implements c<a>, d
{
  public String a;
  public String b;
  public String c;
  public long d;
  
  public a() {}
  
  public a(String paramString1, String paramString2, String paramString3)
  {
    this.d = System.currentTimeMillis();
    this.a = paramString1;
    this.b = paramString2;
    this.c = paramString3;
  }
  
  public a a(String paramString)
    throws JSONException
  {
    paramString = new JSONObject(paramString).getJSONObject("com.ad4screen.sdk.service.modules.inapp.model.Beacon");
    a locala = new a(paramString.getString("id"), paramString.getString("accuracy"), paramString.getString("transition"));
    locala.d = paramString.getLong("timestamp");
    return locala;
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.inapp.model.Beacon";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = new JSONObject();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("id", this.a);
    localJSONObject2.put("transition", this.c);
    localJSONObject2.put("accuracy", this.b);
    localJSONObject2.put("timestamp", this.d);
    localJSONObject1.put("com.ad4screen.sdk.service.modules.inapp.model.Beacon", localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/model/a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */