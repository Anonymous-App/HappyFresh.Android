package com.ad4screen.sdk.service.modules.inapp;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.common.h.a;
import com.ad4screen.sdk.common.i;
import com.ad4screen.sdk.plugins.beacons.BeaconUtils;
import com.ad4screen.sdk.plugins.geofences.GeofenceUtils;
import com.ad4screen.sdk.service.modules.inapp.model.e;
import com.ad4screen.sdk.systems.a;
import com.ad4screen.sdk.systems.b;
import com.ad4screen.sdk.systems.b.a;
import com.ad4screen.sdk.systems.b.b;
import com.ad4screen.sdk.systems.d.b;
import com.ad4screen.sdk.systems.f;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public class g
  extends com.ad4screen.sdk.common.tasks.c
{
  private final String c = "com.ad4screen.sdk.service.modules.inapp.LoadInAppConfigTask";
  private final String d = "content";
  private final String e = "fromGeofence";
  private final Context f;
  private String g;
  private Bundle h;
  private boolean i;
  
  public g(Context paramContext, Bundle paramBundle, boolean paramBoolean)
  {
    super(paramContext);
    this.f = paramContext;
    this.h = paramBundle;
    this.i = paramBoolean;
  }
  
  protected void a(String paramString)
  {
    Log.debug("InApp|New Configuration received");
    com.ad4screen.sdk.systems.d.a(this.f).e(d.b.G);
    com.ad4screen.sdk.systems.d.a(this.f).e(d.b.H);
    try
    {
      Log.internal("InApp|Configuration start parsing");
      d locald = new d(this.f);
      locald.a(new JSONObject(paramString));
      if (locald.a == null)
      {
        Log.error("InApp|Configuration parsing failed");
        return;
      }
      Log.internal("InApp|Configuration parsing success");
      Log.debug("InApp|Received " + locald.a.b.size() + " inapps");
      com.ad4screen.sdk.systems.d.a(this.f).e(d.b.e);
      f.a().a(new c.e(locald.a, this.i));
      return;
    }
    catch (JSONException paramString)
    {
      Log.internal("InApp|InApp config Parsing error!", paramString);
    }
  }
  
  protected void a(Throwable paramThrowable)
  {
    Log.error("InApp|Failed to retrieve configuration from server");
  }
  
  protected boolean a()
  {
    c("application/json;charset=utf-8");
    b(2);
    h();
    Object localObject1 = b.a(this.f);
    com.ad4screen.sdk.service.modules.member.c.a(this.f);
    if (((b)localObject1).f == null)
    {
      Log.warn("InApp|No sharedId, skipping configuration");
      return false;
    }
    if (!com.ad4screen.sdk.systems.d.a(this.f).c(d.b.e))
    {
      Log.debug("Service interruption on LoadInAppConfigTask");
      return false;
    }
    for (;;)
    {
      try
      {
        JSONObject localJSONObject1 = new JSONObject();
        localJSONObject1.put("screenSize", ((b)localObject1).u + "," + ((b)localObject1).v.toString());
        localJSONObject1.put("version", "A3.2.1");
        localJSONObject1.put("deviceModel", ((b)localObject1).g);
        localJSONObject1.put("sharedId", ((b)localObject1).f);
        localJSONObject1.put("appLang", ((b)localObject1).m);
        localJSONObject1.put("source", ((b)localObject1).G);
        localJSONObject1.put("sourceDate", ((b)localObject1).H);
        localJSONObject1.put("deviceSystemVersion", ((b)localObject1).h);
        localJSONObject1.put("partnerId", ((b)localObject1).d);
        localJSONObject1.put("openCount", ((b)localObject1).r);
        if (((com.ad4screen.sdk.systems.d.a(this.f).d(d.b.G)) && (com.ad4screen.sdk.systems.d.a(this.f).c(d.b.G))) || (((b)localObject1).I == b.b.a)) {
          localJSONObject1.put("localDate", h.a());
        }
        localJSONObject1.put("deviceCountry", ((b)localObject1).l);
        localJSONObject1.put("installTime", h.a(h.a(((b)localObject1).p, h.a.b), h.a.b));
        if (!com.ad4screen.sdk.service.modules.push.g.b(this.f)) {
          break label655;
        }
        if (!i.i(this.f))
        {
          bool = true;
          localJSONObject1.put("notificationsEnabled", bool);
          localJSONObject1.put("bundleVersion", ((b)localObject1).n);
          if (((com.ad4screen.sdk.systems.d.a(this.f).d(d.b.H)) && (com.ad4screen.sdk.systems.d.a(this.f).c(d.b.H))) || (((b)localObject1).I == b.b.a))
          {
            if (i.h(this.f))
            {
              localObject1 = "wifi";
              localJSONObject1.put("connectionType", localObject1);
            }
          }
          else
          {
            localObject1 = new JSONObject();
            if (this.h != null)
            {
              if (GeofenceUtils.parseGeofences(this.h) != null) {
                ((JSONObject)localObject1).put("geofences", GeofenceUtils.parseGeofences(this.h));
              }
              localObject2 = BeaconUtils.parseBeacons(this.h);
              if (localObject2 != null) {
                ((JSONObject)localObject1).put("beacons", localObject2);
              }
            }
            Object localObject2 = a.a(this.f).d();
            if (localObject2 != null)
            {
              JSONObject localJSONObject2 = new JSONObject();
              Calendar localCalendar = Calendar.getInstance(Locale.US);
              localCalendar.setTimeInMillis(((Location)localObject2).getTime());
              localJSONObject2.put("date", h.a(localCalendar.getTime(), h.a.b));
              localJSONObject2.put("lat", ((Location)localObject2).getLatitude());
              localJSONObject2.put("lon", ((Location)localObject2).getLongitude());
              localJSONObject2.put("alt", ((Location)localObject2).getAltitude());
              localJSONObject2.put("acc", ((Location)localObject2).getAccuracy());
              ((JSONObject)localObject1).put("geoloc", localJSONObject2);
            }
            if ((this.h != null) || (localObject2 != null)) {
              localJSONObject1.put("geoloc", localObject1);
            }
            this.g = localJSONObject1.toString();
            return true;
          }
          localObject1 = "cell";
          continue;
        }
        bool = false;
      }
      catch (JSONException localJSONException)
      {
        Log.error("InApp|Could not build message to send to Ad4Screen", localJSONException);
        return false;
      }
      continue;
      label655:
      boolean bool = false;
    }
  }
  
  public com.ad4screen.sdk.common.tasks.c b(com.ad4screen.sdk.common.tasks.c paramc)
  {
    return paramc;
  }
  
  protected String c()
  {
    return d.b.e.toString();
  }
  
  public com.ad4screen.sdk.common.tasks.c d(String paramString)
    throws JSONException
  {
    super.d(paramString);
    paramString = new JSONObject(paramString).getJSONObject("com.ad4screen.sdk.service.modules.inapp.LoadInAppConfigTask");
    if (!paramString.isNull("content")) {
      this.g = paramString.getString("content");
    }
    if (!paramString.isNull("fromGeofence")) {
      this.i = paramString.getBoolean("fromGeofence");
    }
    return this;
  }
  
  protected String d()
  {
    return this.g;
  }
  
  protected String e()
  {
    return com.ad4screen.sdk.systems.d.a(this.f).a(d.b.e);
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.inapp.LoadInAppConfigTask";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = super.toJSON();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("content", this.g);
    localJSONObject2.put("fromGeofence", this.i);
    localJSONObject1.put("com.ad4screen.sdk.service.modules.inapp.LoadInAppConfigTask", localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/g.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */