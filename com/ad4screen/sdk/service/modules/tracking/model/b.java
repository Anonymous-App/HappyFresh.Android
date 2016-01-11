package com.ad4screen.sdk.service.modules.tracking.model;

import com.ad4screen.sdk.common.persistence.c;
import com.ad4screen.sdk.common.persistence.d;
import org.json.JSONException;
import org.json.JSONObject;

public class b
  implements c<b>, d
{
  public String a;
  public int b;
  private final String c = "com.ad4screen.sdk.service.modules.tracking.model.Event";
  private final String d = "id";
  private final String e = "dispatch";
  
  public b a(String paramString)
    throws JSONException
  {
    paramString = new JSONObject(paramString).getJSONObject("com.ad4screen.sdk.service.modules.tracking.model.Event");
    if (!paramString.isNull("id")) {
      this.a = paramString.getString("id");
    }
    this.b = paramString.getInt("dispatch");
    return this;
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.tracking.model.Event";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = new JSONObject();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("dispatch", this.b);
    localJSONObject2.put("id", this.a);
    localJSONObject1.put("com.ad4screen.sdk.service.modules.tracking.model.Event", localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/tracking/model/b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */