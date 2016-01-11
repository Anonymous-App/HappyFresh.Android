package com.ad4screen.sdk.model.displayformats;

import com.ad4screen.sdk.common.persistence.e;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class a
  extends d
{
  public Integer a;
  public boolean b;
  public b c = new b();
  public d d;
  public HashMap<String, String> e = new HashMap();
  public HashMap<String, String> f = new HashMap();
  public boolean g = true;
  private final String n = "com.ad4screen.sdk.model.displayformats.Banner";
  private final String o = "autoClose";
  private final String p = "isFullScreen";
  private final String q = "display";
  private final String r = "target";
  private final String s = "overlay";
  private final String t = "displayCustomParams";
  private final String u = "clickCustomParams";
  
  public a a(String paramString)
    throws JSONException
  {
    paramString = new JSONObject(paramString);
    if (!paramString.isNull("autoClose")) {
      this.a = Integer.valueOf(paramString.getInt("autoClose"));
    }
    if (!paramString.isNull("isFullScreen")) {
      this.b = paramString.getBoolean("isFullScreen");
    }
    if (!paramString.isNull("overlay")) {
      this.g = paramString.getBoolean("overlay");
    }
    if (!paramString.isNull("display")) {
      this.c = ((b)this.m.a(paramString.getString("display"), this.c));
    }
    if (!paramString.isNull("target")) {
      this.d = ((d)this.m.a(paramString.getString("target"), new d()));
    }
    JSONObject localJSONObject = paramString.getJSONObject("displayCustomParams");
    this.e = ((HashMap)this.m.a(localJSONObject.toString(), new HashMap()));
    paramString = paramString.getJSONObject("clickCustomParams");
    this.f = ((HashMap)this.m.a(paramString.toString(), new HashMap()));
    return this;
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.model.displayformats.Banner";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject = super.toJSON();
    localJSONObject.put("type", "com.ad4screen.sdk.model.displayformats.Banner");
    localJSONObject.put("autoClose", this.a);
    localJSONObject.put("isFullScreen", this.b);
    localJSONObject.put("overlay", this.g);
    if (this.c != null) {
      localJSONObject.put("display", this.m.a(this.c));
    }
    if (this.d != null) {
      localJSONObject.put("target", this.m.a(this.d));
    }
    localJSONObject.put("displayCustomParams", this.m.a(this.e));
    localJSONObject.put("clickCustomParams", this.m.a(this.f));
    return localJSONObject;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/model/displayformats/a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */