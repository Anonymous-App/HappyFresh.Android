package com.ad4screen.sdk.service.modules.location;

import android.location.Location;
import android.os.Bundle;
import com.ad4screen.sdk.A4SService.a;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.g;
import com.ad4screen.sdk.common.i;
import com.ad4screen.sdk.common.persistence.e;
import com.ad4screen.sdk.service.modules.authentication.a.c;
import com.ad4screen.sdk.systems.a.a;
import com.ad4screen.sdk.systems.f;
import org.json.JSONException;
import org.json.JSONObject;

public class b
{
  private final a a;
  private final A4SService.a b;
  private Bundle c;
  private final com.ad4screen.sdk.systems.a.b d = new com.ad4screen.sdk.systems.a.b()
  {
    public void a()
    {
      Log.debug("GeolocationManager|Geolocation changed, updating ad4push geolocation");
      b.this.a(false);
    }
  };
  private final a.c e = new a.c()
  {
    public void a() {}
    
    public void a(com.ad4screen.sdk.service.modules.authentication.model.a paramAnonymousa, boolean paramAnonymousBoolean)
    {
      Log.debug("GeolocationManager|Received sharedId, starting session");
      b.a(b.this);
    }
  };
  
  public b(A4SService.a parama)
  {
    this.b = parama;
    this.a = new a(parama.a());
    f.a().a(a.a.class, this.d);
    f.a().a(com.ad4screen.sdk.service.modules.authentication.a.b.class, this.e);
  }
  
  private void a()
  {
    b();
    if (!com.ad4screen.sdk.systems.b.a(this.b.a()).z)
    {
      Log.debug("GeolocationManager|Starting session, updating ad4push geolocation");
      a(true);
    }
  }
  
  private boolean a(long paramLong1, long paramLong2, boolean paramBoolean, Location paramLocation1, Location paramLocation2)
  {
    if (paramLocation1 == null) {
      return false;
    }
    if (paramBoolean) {
      return true;
    }
    return (paramLong1 - paramLong2 > 300000L) && (i.a(paramLocation2.getLatitude(), paramLocation2.getLongitude(), paramLocation1.getLatitude(), paramLocation1.getLongitude()) >= 100);
  }
  
  private void b()
  {
    this.a.a(0L);
  }
  
  public void a(Location paramLocation)
  {
    com.ad4screen.sdk.systems.a.a(this.b.a()).a(paramLocation);
    a(false);
  }
  
  public void a(Bundle paramBundle)
  {
    this.c = paramBundle;
    if (this.c == null)
    {
      Log.error("GeolocationManager|No Geofence information found, aborting...");
      return;
    }
    paramBundle = this.c.getBundle("com.ad4screen.sdk.extra.GEOFENCE_PAYLOAD");
    if ((paramBundle != null) && (paramBundle.containsKey("triggeringLocation"))) {
      try
      {
        paramBundle = new JSONObject(paramBundle.getString("triggeringLocation"));
        Location localLocation = new Location("fused");
        if (!paramBundle.isNull("provider")) {
          localLocation.setProvider(paramBundle.getString("provider"));
        }
        if (!paramBundle.isNull("latitude")) {
          localLocation.setLatitude(paramBundle.getDouble("latitude"));
        }
        if (!paramBundle.isNull("longitude")) {
          localLocation.setLongitude(paramBundle.getDouble("longitude"));
        }
        if (!paramBundle.isNull("altitude")) {
          localLocation.setAltitude(paramBundle.getDouble("altitude"));
        }
        if (!paramBundle.isNull("accuracy")) {
          localLocation.setAccuracy((float)paramBundle.getDouble("accuracy"));
        }
        if (!paramBundle.isNull("bearing")) {
          localLocation.setBearing((float)paramBundle.getDouble("bearing"));
        }
        if (!paramBundle.isNull("speed")) {
          localLocation.setSpeed((float)paramBundle.getDouble("speed"));
        }
        if (!paramBundle.isNull("time")) {
          localLocation.setTime(paramBundle.getLong("time"));
        }
        com.ad4screen.sdk.systems.a.a(this.b.a()).a(localLocation);
        a(true);
        this.b.d().a(this.c, true);
        return;
      }
      catch (JSONException paramBundle)
      {
        Log.error("GeolocationManager|Error while parsing triggeringLocation information, aborting...");
        return;
      }
    }
    Log.error("GeolocationManager|No triggeringLocation information found, aborting...");
  }
  
  public void a(boolean paramBoolean)
  {
    Location localLocation1 = com.ad4screen.sdk.systems.a.a(this.b.a()).d();
    Location localLocation2 = this.a.a();
    if (localLocation1 == null) {
      Log.debug("GeolocationManager|No Geolocation Found");
    }
    for (;;)
    {
      return;
      long l1 = this.a.e();
      long l2 = g.e().a();
      if (a(l2, l1, paramBoolean, localLocation1, localLocation2))
      {
        if (com.ad4screen.sdk.systems.b.a(this.b.a()).f == null) {
          continue;
        }
        new c(this.b.a(), localLocation1).run();
        this.a.a(l2);
        try
        {
          this.a.a(new e().a(localLocation1));
          if (this.c != null)
          {
            new com.ad4screen.sdk.service.modules.geofencing.c(this.b.a(), this.c).run();
            this.c = null;
            return;
          }
        }
        catch (JSONException localJSONException)
        {
          for (;;)
          {
            Log.internal("GeolocationManager|Can't store last location sent", localJSONException);
          }
        }
      }
    }
    Log.debug("GeolocationManager|Cancelled ad4push geolocation update (cannot be sent more than once every 5 minutes and new location must be 100 meters away from last sent one)");
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/location/b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */