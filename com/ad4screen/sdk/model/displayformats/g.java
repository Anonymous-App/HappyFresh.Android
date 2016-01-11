package com.ad4screen.sdk.model.displayformats;

import com.ad4screen.sdk.common.persistence.adapters.h;
import com.ad4screen.sdk.common.persistence.e;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class g
  extends d
{
  public String a;
  public String b;
  public a[] c = new a[0];
  public HashMap<String, String> d = new HashMap();
  
  public g a(String paramString)
    throws JSONException
  {
    paramString = new JSONObject(paramString);
    if (!paramString.isNull("title")) {
      this.a = paramString.getString("title");
    }
    if (!paramString.isNull("body")) {
      this.b = paramString.getString("body");
    }
    JSONObject localJSONObject = paramString.getJSONObject("displayCustomParams");
    this.d = ((HashMap)this.m.a(localJSONObject.toString(), new HashMap()));
    paramString = paramString.getJSONArray("buttons");
    this.c = new a[paramString.length()];
    int i = 0;
    while (i < paramString.length())
    {
      localJSONObject = paramString.getJSONObject(i);
      this.c[i] = new a();
      if (!localJSONObject.isNull("id")) {
        this.c[i].a = localJSONObject.getString("id");
      }
      if (!localJSONObject.isNull("title")) {
        this.c[i].b = localJSONObject.getString("title");
      }
      if (!localJSONObject.isNull("icon")) {
        this.c[i].c = localJSONObject.getInt("icon");
      }
      if (!localJSONObject.isNull("target")) {
        this.c[i].d = ((d)this.m.a(localJSONObject.getString("target"), new d()));
      }
      localJSONObject = localJSONObject.getJSONObject("clickCustomParams");
      this.c[i].e = ((HashMap)this.m.a(localJSONObject.toString(), new HashMap()));
      i += 1;
    }
    return this;
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.model.displayformats.Popup";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    e locale = new e();
    JSONObject localJSONObject1 = super.toJSON();
    localJSONObject1.put("type", "com.ad4screen.sdk.model.displayformats.Popup");
    localJSONObject1.put("title", this.a);
    localJSONObject1.put("body", this.b);
    JSONArray localJSONArray = new JSONArray();
    int i = 0;
    while (i < this.c.length)
    {
      JSONObject localJSONObject2 = new JSONObject();
      localJSONObject2.put("id", this.c[i].a);
      localJSONObject2.put("title", this.c[i].b);
      localJSONObject2.put("icon", this.c[i].c);
      if (this.c[i].d != null) {
        localJSONObject2.put("target", locale.a(this.c[i].d));
      }
      localJSONObject2.put("clickCustomParams", new h().a(this.c[i].e));
      localJSONArray.put(localJSONObject2);
      i += 1;
    }
    localJSONObject1.put("buttons", localJSONArray);
    localJSONObject1.put("displayCustomParams", new h().a(this.d));
    return localJSONObject1;
  }
  
  public static class a
  {
    public String a;
    public String b;
    public int c;
    public d d;
    public HashMap<String, String> e = new HashMap();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/model/displayformats/g.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */