package com.ad4screen.sdk.model.displayformats;

import com.ad4screen.sdk.service.modules.alarm.model.b;
import org.json.JSONException;
import org.json.JSONObject;

public class d
  implements com.ad4screen.sdk.common.persistence.c<d>, com.ad4screen.sdk.common.persistence.d
{
  private final String a = "com.ad4screen.sdk.model.displayformats";
  private final String b = "id";
  private final String c = "displayTrackingUrl";
  private final String d = "clickTrackingUrl";
  private final String e = "closeTrackingUrl";
  public final String h = "type";
  public String i;
  public String j;
  public String k;
  public String l;
  public com.ad4screen.sdk.common.persistence.e m = new com.ad4screen.sdk.common.persistence.e();
  
  public d b(String paramString)
    throws JSONException
  {
    int n = 0;
    Object localObject = new d[8];
    localObject[0] = new b();
    localObject[1] = new com.ad4screen.sdk.service.modules.alarm.model.c();
    localObject[2] = new a();
    localObject[3] = new c();
    localObject[4] = new e();
    localObject[5] = new f();
    localObject[6] = new g();
    localObject[7] = new h();
    JSONObject localJSONObject = new JSONObject(paramString);
    String str = localJSONObject.getString("type");
    if (n < localObject.length) {
      if (!str.equals(localObject[n].getClassKey())) {}
    }
    for (paramString = (d)this.m.a(paramString, localObject[n]);; paramString = null)
    {
      localObject = paramString;
      if (paramString == null) {
        localObject = new d();
      }
      if (!localJSONObject.isNull("id")) {
        ((d)localObject).i = localJSONObject.getString("id");
      }
      if (!localJSONObject.isNull("displayTrackingUrl")) {
        ((d)localObject).j = localJSONObject.getString("displayTrackingUrl");
      }
      if (!localJSONObject.isNull("clickTrackingUrl")) {
        ((d)localObject).k = localJSONObject.getString("clickTrackingUrl");
      }
      if (!localJSONObject.isNull("closeTrackingUrl")) {
        ((d)localObject).l = localJSONObject.getString("closeTrackingUrl");
      }
      return (d)localObject;
      n += 1;
      break;
    }
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.model.displayformats";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put("id", this.i);
    localJSONObject.put("clickTrackingUrl", this.k);
    localJSONObject.put("displayTrackingUrl", this.j);
    localJSONObject.put("closeTrackingUrl", this.l);
    return localJSONObject;
  }
  
  public static enum a
  {
    private a() {}
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/model/displayformats/d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */