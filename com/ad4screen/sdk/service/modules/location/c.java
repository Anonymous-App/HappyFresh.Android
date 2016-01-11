package com.ad4screen.sdk.service.modules.location;

import android.content.Context;
import android.location.Location;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.common.h.a;
import com.ad4screen.sdk.common.persistence.e;
import com.ad4screen.sdk.systems.b;
import com.ad4screen.sdk.systems.d;
import com.ad4screen.sdk.systems.d.b;
import java.util.Calendar;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class c
  extends com.ad4screen.sdk.common.tasks.c
{
  private final String c = "com.ad4screen.sdk.service.modules.location.LocationUpdateTask";
  private final String d = "content";
  private final String e = "location";
  private String f;
  private final Context g;
  private Location h;
  
  public c(Context paramContext, Location paramLocation)
  {
    super(paramContext);
    this.g = paramContext;
    this.h = paramLocation;
  }
  
  protected void a(String paramString)
  {
    if (this.h == null) {
      return;
    }
    Log.debug("LocationUpdateTask|Successfully updated location to " + this.h.getLatitude() + "," + this.h.getLongitude() + " (accuracy :" + this.h.getAccuracy() + ")");
    d.a(this.g).e(d.b.y);
  }
  
  protected void a(Throwable paramThrowable)
  {
    Log.error("LocationUpdateTask|Location update failed");
  }
  
  protected boolean a()
  {
    h();
    i();
    b localb = b.a(this.g);
    if (localb.f == null) {
      Log.warn("LocationUpdateTask|No SharedId, not updating location");
    }
    for (;;)
    {
      return false;
      if (!d.a(this.g).c(d.b.y))
      {
        Log.debug("Service interruption on LocationUpdateTask");
        return false;
      }
      try
      {
        JSONObject localJSONObject1 = new JSONObject();
        JSONObject localJSONObject2 = new JSONObject();
        JSONArray localJSONArray = new JSONArray();
        if (this.h != null)
        {
          Calendar localCalendar = Calendar.getInstance(Locale.US);
          localCalendar.setTimeInMillis(this.h.getTime());
          localJSONObject2.put("date", h.a(localCalendar.getTime(), h.a.b));
          localJSONObject2.put("lat", this.h.getLatitude());
          localJSONObject2.put("lon", this.h.getLongitude());
          localJSONObject2.put("alt", this.h.getAltitude());
          localJSONObject2.put("acc", this.h.getAccuracy());
          localJSONObject2.put("timezone", localb.q);
          localJSONArray.put(localJSONObject2);
          localJSONObject1.put("geolocs", localJSONArray);
          this.f = localJSONObject1.toString();
          return true;
        }
      }
      catch (Exception localException)
      {
        Log.error("LocationUpdateTask|Could not build message to send to Ad4Screen", localException);
      }
    }
    return false;
  }
  
  public com.ad4screen.sdk.common.tasks.c b(com.ad4screen.sdk.common.tasks.c paramc)
  {
    Object localObject = (c)paramc;
    try
    {
      paramc = new JSONObject(d());
      localObject = new JSONObject(((c)localObject).d()).getJSONArray("geolocs");
      JSONArray localJSONArray = paramc.getJSONArray("geolocs");
      int i = 0;
      while (i < ((JSONArray)localObject).length())
      {
        localJSONArray.put(((JSONArray)localObject).get(i));
        i += 1;
      }
      this.f = paramc.toString();
      return this;
    }
    catch (JSONException paramc)
    {
      Log.internal("Failed to merge " + c(), paramc);
      return this;
    }
    catch (NullPointerException paramc)
    {
      Log.internal("Failed to merge " + c(), paramc);
    }
    return this;
  }
  
  protected String c()
  {
    return d.b.y.toString();
  }
  
  public com.ad4screen.sdk.common.tasks.c d(String paramString)
    throws JSONException
  {
    super.d(paramString);
    paramString = new JSONObject(paramString).getJSONObject("com.ad4screen.sdk.service.modules.location.LocationUpdateTask");
    if (!paramString.isNull("content")) {
      this.f = paramString.getString("content");
    }
    if (!paramString.isNull("location")) {
      this.h = ((Location)new e().a(paramString.getString("location"), new Location("")));
    }
    h();
    return this;
  }
  
  protected String d()
  {
    return this.f;
  }
  
  protected String e()
  {
    return d.a(this.g).a(d.b.y);
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.location.LocationUpdateTask";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = super.toJSON();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("content", this.f);
    localJSONObject1.put("com.ad4screen.sdk.service.modules.location.LocationUpdateTask", localJSONObject2);
    if (this.h != null) {
      localJSONObject1.put("location", new e().a(this.h));
    }
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/location/c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */