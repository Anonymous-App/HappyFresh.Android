package com.ad4screen.sdk.plugins.model;

import com.ad4screen.sdk.common.annotations.API;
import com.ad4screen.sdk.common.persistence.c;
import com.ad4screen.sdk.common.persistence.d;
import org.json.JSONException;
import org.json.JSONObject;

@API
public class Geofence
  implements c<Geofence>, d, Comparable<Geofence>
{
  private static final String CLASS_KEY = "com.ad4screen.sdk.plugins.model.Geofence";
  public int distance;
  public String id;
  public double latitude;
  public double longitude;
  public float radius;
  public boolean toRemove = false;
  
  public Geofence(String paramString)
  {
    this.id = paramString;
    this.toRemove = true;
  }
  
  public Geofence(String paramString, double paramDouble1, double paramDouble2, float paramFloat)
  {
    this.id = paramString;
    this.latitude = paramDouble1;
    this.longitude = paramDouble2;
    this.radius = paramFloat;
  }
  
  public int compareTo(Geofence paramGeofence)
  {
    return this.distance - paramGeofence.distance;
  }
  
  public Geofence fromJSON(String paramString)
    throws JSONException
  {
    paramString = new JSONObject(paramString);
    Geofence localGeofence = new Geofence(paramString.getString("id"));
    localGeofence.latitude = paramString.getDouble("latitude");
    localGeofence.longitude = paramString.getDouble("longitude");
    localGeofence.radius = ((float)paramString.getDouble("radius"));
    localGeofence.toRemove = paramString.getBoolean("toRemove");
    localGeofence.distance = paramString.getInt("distance");
    return localGeofence;
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.plugins.model.Geofence";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put("id", this.id);
    localJSONObject.put("latitude", this.latitude);
    localJSONObject.put("longitude", this.longitude);
    localJSONObject.put("radius", this.radius);
    localJSONObject.put("toRemove", this.toRemove);
    localJSONObject.put("distance", this.distance);
    return localJSONObject;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/plugins/model/Geofence.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */