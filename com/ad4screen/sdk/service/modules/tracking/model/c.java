package com.ad4screen.sdk.service.modules.tracking.model;

import com.ad4screen.sdk.common.persistence.d;
import com.ad4screen.sdk.common.persistence.e;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class c
  implements com.ad4screen.sdk.common.persistence.c<c>, d
{
  public b[] a;
  private final String b = "com.ad4screen.sdk.service.modules.tracking.model.EventDispatch";
  private final String c = "events";
  private e d = new e();
  
  public int a(long paramLong)
  {
    int j;
    if (this.a == null)
    {
      j = -1;
      return j;
    }
    int i = 0;
    for (;;)
    {
      if (i >= this.a.length) {
        break label53;
      }
      j = i;
      if (this.a[i].a.equals(String.valueOf(paramLong))) {
        break;
      }
      i += 1;
    }
    label53:
    return -1;
  }
  
  public c a(String paramString)
    throws JSONException
  {
    paramString = new JSONObject(paramString).getJSONObject("com.ad4screen.sdk.service.modules.tracking.model.EventDispatch").getJSONArray("events");
    this.a = new b[paramString.length()];
    int i = 0;
    while (i < paramString.length())
    {
      this.a[i] = ((b)this.d.a(paramString.getString(i), new b()));
      i += 1;
    }
    return this;
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.tracking.model.EventDispatch";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = new JSONObject();
    JSONArray localJSONArray = new JSONArray();
    int i = 0;
    while (i < this.a.length)
    {
      localJSONArray.put(this.d.a(this.a[i]));
      i += 1;
    }
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("events", localJSONArray);
    localJSONObject1.put("com.ad4screen.sdk.service.modules.tracking.model.EventDispatch", localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/tracking/model/c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */