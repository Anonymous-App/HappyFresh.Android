package com.ad4screen.sdk.model.displayformats;

import org.json.JSONException;
import org.json.JSONObject;

public class h
  extends d
{
  public String a;
  private final String b = "com.ad4screen.sdk.model.displayformats.Webservice";
  private final String c = "url";
  
  public h a(String paramString)
    throws JSONException
  {
    paramString = new JSONObject(paramString);
    if (!paramString.isNull("url")) {
      this.a = paramString.getString("url");
    }
    return this;
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.model.displayformats.Webservice";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject = super.toJSON();
    localJSONObject.put("type", "com.ad4screen.sdk.model.displayformats.Webservice");
    localJSONObject.put("url", this.a);
    return localJSONObject;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/model/displayformats/h.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */