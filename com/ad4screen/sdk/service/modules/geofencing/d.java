package com.ad4screen.sdk.service.modules.geofencing;

import android.content.Context;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.common.h.a;
import com.ad4screen.sdk.common.tasks.c;
import com.ad4screen.sdk.systems.d.b;
import com.ad4screen.sdk.systems.f;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

public class d
  extends c
{
  private final String c = "com.ad4screen.sdk.service.modules.geofencing.LoadGeofencingConfigurationTask";
  private final String d = "lastUpdate";
  private final Context e;
  private Long f;
  
  public d(Context paramContext, long paramLong)
  {
    super(paramContext);
    this.e = paramContext;
    this.f = Long.valueOf(paramLong);
  }
  
  protected void a(String paramString)
  {
    try
    {
      Log.internal("Geofencing Configuration|Geofencing start parsing");
      paramString = new JSONObject(paramString);
      b localb = new b();
      localb.a(paramString);
      if ((localb.a == null) || (localb.b == null))
      {
        Log.error("Geofencing Configuration|Geofencing parsing failed");
        f.a().a(new a.a());
        return;
      }
      Log.debug("Geofencing Configuration|Received " + localb.a.length + " Geofences");
      com.ad4screen.sdk.systems.d.a(this.e).e(d.b.A);
      f.a().a(new a.b(localb.d, localb.c, localb.a, localb.b));
      return;
    }
    catch (JSONException paramString)
    {
      Log.internal("Geofencing Configuration|Response JSON Parsing error!", paramString);
      f.a().a(new a.a());
    }
  }
  
  protected void a(Throwable paramThrowable)
  {
    Log.error("Geofencing Configuration|Failed to retrieve geofences configuration");
    f.a().a(new a.a());
  }
  
  protected boolean a()
  {
    h();
    i();
    if (com.ad4screen.sdk.systems.b.a(this.e).f == null)
    {
      Log.warn("Geofencing Configuration|No sharedId, skipping reception of geofences");
      f.a().a(new a.a());
      return false;
    }
    if (!com.ad4screen.sdk.systems.d.a(this.e).c(d.b.A))
    {
      Log.debug("Service interruption on GeofencingConfigurationTask");
      return false;
    }
    return true;
  }
  
  public c b(c paramc)
  {
    return paramc;
  }
  
  protected String c()
  {
    return d.b.A.toString();
  }
  
  public c d(String paramString)
    throws JSONException
  {
    super.d(paramString);
    paramString = new JSONObject(paramString).getJSONObject("com.ad4screen.sdk.service.modules.geofencing.LoadGeofencingConfigurationTask");
    if (!paramString.isNull("lastUpdate")) {
      this.f = Long.valueOf(paramString.getLong("lastUpdate"));
    }
    return this;
  }
  
  protected String d()
  {
    return null;
  }
  
  protected String e()
  {
    if (this.f.longValue() != 0L)
    {
      Date localDate = new Date(this.f.longValue());
      return com.ad4screen.sdk.systems.d.a(this.e).a(d.b.A) + "?lastUpdate=" + h.a(localDate, h.a.b);
    }
    return com.ad4screen.sdk.systems.d.a(this.e).a(d.b.A);
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.geofencing.LoadGeofencingConfigurationTask";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = super.toJSON();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("lastUpdate", this.f);
    localJSONObject1.put("com.ad4screen.sdk.service.modules.geofencing.LoadGeofencingConfigurationTask", localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/geofencing/d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */