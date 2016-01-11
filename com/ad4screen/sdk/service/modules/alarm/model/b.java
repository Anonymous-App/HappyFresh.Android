package com.ad4screen.sdk.service.modules.alarm.model;

import com.ad4screen.sdk.model.displayformats.d;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class b
  extends d
{
  public String[] a;
  private final String b = "com.ad4screen.sdk.model.displayformats.CancelAlarm";
  private final String c = "ids";
  
  public b a(String paramString)
    throws JSONException
  {
    paramString = new JSONObject(paramString);
    if (!paramString.isNull("ids"))
    {
      paramString = paramString.getJSONArray("ids");
      this.a = new String[paramString.length()];
      int i = 0;
      while (i < paramString.length())
      {
        this.a[i] = paramString.getString(i);
        i += 1;
      }
    }
    return this;
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.model.displayformats.CancelAlarm";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject = super.toJSON();
    localJSONObject.put("type", "com.ad4screen.sdk.model.displayformats.CancelAlarm");
    if (this.a != null)
    {
      JSONArray localJSONArray = new JSONArray();
      int i = 0;
      while (i < this.a.length)
      {
        localJSONArray.put(this.a[i]);
        i += 1;
      }
      localJSONObject.put("ids", localJSONArray);
    }
    return localJSONObject;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/alarm/model/b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */