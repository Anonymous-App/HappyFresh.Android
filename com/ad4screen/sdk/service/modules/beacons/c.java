package com.ad4screen.sdk.service.modules.beacons;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.common.h.a;
import com.ad4screen.sdk.plugins.model.Beacon;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class c
{
  public Beacon[] a;
  public Date b;
  public boolean c = false;
  public boolean d = false;
  
  private void a(JSONArray paramJSONArray)
    throws JSONException
  {
    this.a = new Beacon[paramJSONArray.length()];
    int i = 0;
    while (i < paramJSONArray.length())
    {
      this.a[i] = b(paramJSONArray.getJSONObject(i));
      i += 1;
    }
  }
  
  private Beacon b(JSONObject paramJSONObject)
    throws JSONException
  {
    Beacon localBeacon = new Beacon(paramJSONObject.getString("id"));
    localBeacon.toRemove = paramJSONObject.getString("action").equalsIgnoreCase("remove");
    if (!paramJSONObject.isNull("uuid")) {
      localBeacon.uuid = paramJSONObject.getString("uuid");
    }
    if (!paramJSONObject.isNull("major")) {
      localBeacon.major = paramJSONObject.getInt("major");
    }
    if (!paramJSONObject.isNull("minor")) {
      localBeacon.minor = paramJSONObject.getInt("minor");
    }
    return localBeacon;
  }
  
  public void a(JSONObject paramJSONObject)
  {
    try
    {
      paramJSONObject = paramJSONObject.getJSONObject("beacons");
      this.b = h.a(paramJSONObject.getString("date"), h.a.b);
      this.c = paramJSONObject.getBoolean("nearestCalculated");
      this.d = paramJSONObject.getBoolean("differentialUpdate");
      a(paramJSONObject.getJSONArray("points"));
      return;
    }
    catch (JSONException paramJSONObject)
    {
      Log.internal("Beacons Configuration|Error while parsing beacon !", paramJSONObject);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/beacons/c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */