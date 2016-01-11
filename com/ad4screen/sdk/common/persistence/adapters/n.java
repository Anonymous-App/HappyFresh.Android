package com.ad4screen.sdk.common.persistence.adapters;

import android.location.Location;
import com.ad4screen.sdk.common.persistence.adapters.model.b;
import org.json.JSONException;
import org.json.JSONObject;

public class n
  extends b<Location>
{
  private final String a = "android.location.Location";
  private final String b = "latitude";
  private final String c = "longitude";
  private final String d = "provider";
  
  public JSONObject a(Location paramLocation)
    throws JSONException
  {
    JSONObject localJSONObject1 = new JSONObject();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("latitude", paramLocation.getLatitude());
    localJSONObject2.put("longitude", paramLocation.getLongitude());
    localJSONObject2.put("provider", paramLocation.getProvider());
    localJSONObject1.put("type", "android.location.Location");
    localJSONObject1.put("android.location.Location", localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/persistence/adapters/n.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */