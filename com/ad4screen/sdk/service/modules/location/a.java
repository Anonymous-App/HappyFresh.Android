package com.ad4screen.sdk.service.modules.location;

import android.content.Context;
import android.location.Location;
import com.ad4screen.sdk.common.persistence.b;
import org.json.JSONObject;

public class a
  extends b
{
  public a(Context paramContext)
  {
    super(paramContext, "com.ad4screen.sdk.service.modules.location.GeolocationManager");
  }
  
  public Location a()
  {
    return (Location)b("lastLocation", new Location("fused"));
  }
  
  public void a(long paramLong)
  {
    a("lastGeolocationUpdate", Long.valueOf(paramLong));
  }
  
  public void a(JSONObject paramJSONObject)
  {
    a("lastLocation", paramJSONObject);
  }
  
  public boolean a(int paramInt, JSONObject paramJSONObject)
  {
    return false;
  }
  
  public int b()
  {
    return 1;
  }
  
  public long e()
  {
    return a("lastGeolocationUpdate", 0L);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/location/a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */