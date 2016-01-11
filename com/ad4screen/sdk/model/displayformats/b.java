package com.ad4screen.sdk.model.displayformats;

import com.ad4screen.sdk.common.persistence.c;
import com.ad4screen.sdk.common.persistence.d;
import org.json.JSONException;
import org.json.JSONObject;

public class b
  implements c<b>, d
{
  public String a;
  public String b;
  public String c;
  public String d;
  public String e;
  public String f;
  private final String g = "com.ad4screen.sdk.model.displayformats.Display";
  private final String h = "title";
  private final String i = "body";
  private final String j = "url";
  private final String k = "template";
  private final String l = "inAnimation";
  private final String m = "outAnimation";
  
  public b a(String paramString)
    throws JSONException
  {
    paramString = new JSONObject(paramString).getJSONObject("com.ad4screen.sdk.model.displayformats.Display");
    if (!paramString.isNull("title")) {
      this.a = paramString.getString("title");
    }
    if (!paramString.isNull("body")) {
      this.b = paramString.getString("body");
    }
    if (!paramString.isNull("url")) {
      this.c = paramString.getString("url");
    }
    if (!paramString.isNull("template")) {
      this.d = paramString.getString("template");
    }
    if (!paramString.isNull("inAnimation")) {
      this.e = paramString.getString("inAnimation");
    }
    if (!paramString.isNull("outAnimation")) {
      this.f = paramString.getString("outAnimation");
    }
    return this;
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.model.displayformats.Display";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = new JSONObject();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("title", this.a);
    localJSONObject2.put("body", this.b);
    localJSONObject2.put("url", this.c);
    localJSONObject2.put("template", this.d);
    localJSONObject2.put("inAnimation", this.e);
    localJSONObject2.put("outAnimation", this.f);
    localJSONObject1.put("com.ad4screen.sdk.model.displayformats.Display", localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/model/displayformats/b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */