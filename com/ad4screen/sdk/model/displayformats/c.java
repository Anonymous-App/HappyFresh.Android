package com.ad4screen.sdk.model.displayformats;

import org.json.JSONException;
import org.json.JSONObject;

public class c
  extends d
{
  public transient String a;
  private final String b = "com.ad4screen.sdk.model.displayformats.File";
  private final String c = "content";
  
  public c a(String paramString)
    throws JSONException
  {
    paramString = new JSONObject(paramString);
    if (!paramString.isNull("content")) {
      this.a = paramString.getString("content");
    }
    return this;
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.model.displayformats.File";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject = super.toJSON();
    localJSONObject.put("type", "com.ad4screen.sdk.model.displayformats.File");
    localJSONObject.put("content", this.a);
    return localJSONObject;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/model/displayformats/c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */