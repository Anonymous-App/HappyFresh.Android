package com.ad4screen.sdk.service.modules.geofencing;

import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.common.h.a;
import com.ad4screen.sdk.plugins.model.Geofence;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class b
{
  public Geofence[] a;
  public Date b;
  public boolean c = false;
  public boolean d = false;
  
  private void a(JSONArray paramJSONArray)
    throws JSONException
  {
    this.a = new Geofence[paramJSONArray.length()];
    int i = 0;
    while (i < paramJSONArray.length())
    {
      this.a[i] = b(paramJSONArray.getJSONObject(i));
      i += 1;
    }
  }
  
  private Geofence b(JSONObject paramJSONObject)
    throws JSONException
  {
    if (paramJSONObject.getString("action").equals("delete")) {
      return new Geofence(paramJSONObject.getString("id"));
    }
    return new Geofence(paramJSONObject.getString("id"), paramJSONObject.getDouble("lat"), paramJSONObject.getDouble("lon"), paramJSONObject.getInt("radius"));
  }
  
  public void a(JSONObject paramJSONObject)
  {
    try
    {
      paramJSONObject = paramJSONObject.getJSONObject("geofences");
      this.b = h.a(paramJSONObject.getString("date"), h.a.b);
      this.c = paramJSONObject.getBoolean("nearestCalculated");
      this.d = paramJSONObject.getBoolean("differentialUpdate");
      a(paramJSONObject.getJSONArray("points"));
      return;
    }
    catch (JSONException paramJSONObject)
    {
      paramJSONObject.printStackTrace();
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/geofencing/b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */