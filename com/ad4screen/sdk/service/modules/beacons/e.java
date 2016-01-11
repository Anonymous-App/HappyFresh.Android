package com.ad4screen.sdk.service.modules.beacons;

import android.content.Context;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.common.h.a;
import com.ad4screen.sdk.systems.b;
import com.ad4screen.sdk.systems.d;
import com.ad4screen.sdk.systems.d.b;
import com.ad4screen.sdk.systems.f;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

public class e
  extends com.ad4screen.sdk.common.tasks.c
{
  private final String c = "com.ad4screen.sdk.service.modules.beacons.LoadBeaconConfigurationTask";
  private final String d = "lastUpdate";
  private final Context e;
  private Long f;
  
  public e(Context paramContext, long paramLong)
  {
    super(paramContext);
    this.e = paramContext;
    this.f = Long.valueOf(paramLong);
  }
  
  protected void a(String paramString)
  {
    try
    {
      Log.internal("Beacons Configuration|Beacons start parsing");
      paramString = new JSONObject(paramString);
      c localc = new c();
      localc.a(paramString);
      if ((localc.a == null) || (localc.b == null))
      {
        Log.error("Beacons Configuration|Beacons parsing failed");
        f.a().a(new a.a());
        return;
      }
      Log.debug("Beacons Configuration|Received " + localc.a.length + " Beacons");
      d.a(this.e).e(d.b.B);
      f.a().a(new a.b(localc.d, localc.c, localc.a, localc.b));
      return;
    }
    catch (JSONException paramString)
    {
      Log.internal("Beacons Configuration|Response JSON Parsing error!", paramString);
      f.a().a(new a.a());
    }
  }
  
  protected void a(Throwable paramThrowable)
  {
    Log.error("Beacons Configuration|Failed to retrieve beacons configuration");
    f.a().a(new a.a());
  }
  
  protected boolean a()
  {
    h();
    i();
    if (b.a(this.e).f == null)
    {
      Log.warn("Beacon Configuration|No sharedId, skipping reception of beacons");
      f.a().a(new a.a());
      return false;
    }
    if (!d.a(this.e).c(d.b.B))
    {
      Log.debug("Service interruption on BeaconConfigurationTask");
      return false;
    }
    return true;
  }
  
  public com.ad4screen.sdk.common.tasks.c b(com.ad4screen.sdk.common.tasks.c paramc)
  {
    return paramc;
  }
  
  protected String c()
  {
    return d.b.B.toString();
  }
  
  public com.ad4screen.sdk.common.tasks.c d(String paramString)
    throws JSONException
  {
    super.d(paramString);
    paramString = new JSONObject(paramString).getJSONObject("com.ad4screen.sdk.service.modules.beacons.LoadBeaconConfigurationTask");
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
      return d.a(this.e).a(d.b.B) + "?lastUpdate=" + h.a(localDate, h.a.b);
    }
    return d.a(this.e).a(d.b.B);
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.beacons.LoadBeaconConfigurationTask";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = super.toJSON();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("lastUpdate", this.f);
    localJSONObject1.put("com.ad4screen.sdk.service.modules.beacons.LoadBeaconConfigurationTask", localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/beacons/e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */