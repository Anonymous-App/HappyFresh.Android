package com.ad4screen.sdk.common.persistence.adapters;

import android.location.Location;
import com.ad4screen.sdk.common.persistence.adapters.model.a;
import org.json.JSONException;
import org.json.JSONObject;

public class m
  extends a<Location>
{
  private final String a = "android.location.Location";
  private final String b = "latitude";
  private final String c = "longitude";
  private final String d = "provider";
  
  public Location a(String paramString)
    throws JSONException
  {
    paramString = new JSONObject(paramString).getJSONObject("android.location.Location");
    Location localLocation = new Location(paramString.getString("provider"));
    localLocation.setLatitude(paramString.getDouble("latitude"));
    localLocation.setLongitude(paramString.getDouble("longitude"));
    return localLocation;
  }
  
  public String a()
  {
    return "android.location.Location";
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/persistence/adapters/m.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */