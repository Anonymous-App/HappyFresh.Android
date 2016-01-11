package com.ad4screen.sdk.service.modules.alarm.model;

import com.ad4screen.sdk.common.persistence.e;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class a
  implements com.ad4screen.sdk.common.persistence.c<a>, com.ad4screen.sdk.common.persistence.d
{
  public ArrayList<c> a = new ArrayList();
  private final String b = "com.ad4screen.sdk.service.modules.inapp.model.AlarmConfig";
  private final String c = "alarms";
  private e d = new e();
  
  public a a(String paramString)
    throws JSONException
  {
    paramString = new JSONObject(paramString).getJSONObject("com.ad4screen.sdk.service.modules.inapp.model.AlarmConfig").getJSONArray("alarms");
    this.a = new ArrayList();
    int i = 0;
    while (i < paramString.length())
    {
      this.a.add((c)this.d.a(paramString.getString(i), new com.ad4screen.sdk.model.displayformats.d()));
      i += 1;
    }
    return this;
  }
  
  public c b(String paramString)
  {
    if (this.a == null) {
      return null;
    }
    int i = 0;
    while (i < this.a.size())
    {
      if ((((c)this.a.get(i)).i != null) && (((c)this.a.get(i)).i.equals(paramString))) {
        return (c)this.a.get(i);
      }
      i += 1;
    }
    return null;
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.inapp.model.AlarmConfig";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = new JSONObject();
    JSONObject localJSONObject2 = new JSONObject();
    JSONArray localJSONArray = new JSONArray();
    int i = 0;
    while (i < this.a.size())
    {
      localJSONArray.put(this.d.a(this.a.get(i)));
      i += 1;
    }
    localJSONObject2.put("alarms", localJSONArray);
    localJSONObject1.put("com.ad4screen.sdk.service.modules.inapp.model.AlarmConfig", localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/alarm/model/a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */