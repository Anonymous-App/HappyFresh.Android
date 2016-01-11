package com.ad4screen.sdk.model.displayformats;

import org.json.JSONException;
import org.json.JSONObject;

public class e
  extends d
{
  public b a = new b();
  public a b = a.a;
  private final String c = "com.ad4screen.sdk.model.displayformats.LandingPage";
  private final String d = "openType";
  private final String e = "display";
  private com.ad4screen.sdk.common.persistence.e f = new com.ad4screen.sdk.common.persistence.e();
  
  public e a(String paramString)
    throws JSONException
  {
    paramString = new JSONObject(paramString);
    if (!paramString.isNull("display")) {
      this.a = ((b)this.f.a(paramString.getString("display"), new b()));
    }
    if (!paramString.isNull("openType")) {
      this.b = a.valueOf(paramString.getString("openType"));
    }
    return this;
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.model.displayformats.LandingPage";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject = super.toJSON();
    localJSONObject.put("type", "com.ad4screen.sdk.model.displayformats.LandingPage");
    localJSONObject.put("openType", this.b.toString());
    if (this.a != null) {
      localJSONObject.put("display", this.f.a(this.a));
    }
    return localJSONObject;
  }
  
  public static enum a
  {
    private a() {}
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/model/displayformats/e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */