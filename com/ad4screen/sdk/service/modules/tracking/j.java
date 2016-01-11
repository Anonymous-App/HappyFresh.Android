package com.ad4screen.sdk.service.modules.tracking;

import android.content.Context;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.i;
import com.ad4screen.sdk.common.tasks.c;
import com.ad4screen.sdk.systems.b;
import com.ad4screen.sdk.systems.b.b;
import com.ad4screen.sdk.systems.d;
import com.ad4screen.sdk.systems.d.b;
import org.json.JSONObject;

public class j
  extends c
{
  private final String c = "com.ad4screen.sdk.service.modules.tracking.VersionTrackingTask";
  private final Context d;
  private String e;
  private b f;
  
  public j(Context paramContext)
  {
    super(paramContext);
    this.d = paramContext;
    this.f = b.a(this.d);
  }
  
  protected void a(String paramString)
  {
    Log.internal("VersionTrackingTask|Tracking succeed");
    d.a(this.d).e(d.b.g);
    if (((d.a(this.d).d(d.b.I)) && (d.a(this.d).c(d.b.I))) || ((this.f.I == b.b.a) && (i.f(this.d) != null))) {
      d.a(this.d).e(d.b.I);
    }
  }
  
  protected void a(Throwable paramThrowable)
  {
    Log.internal("VersionTrackingTask|Tracking failed");
  }
  
  protected boolean a()
  {
    a(4);
    h();
    i();
    if (this.f.f == null)
    {
      Log.warn("VersionTrackingTask|No sharedId, skipping configuration");
      return false;
    }
    if (!d.a(this.d).c(d.b.g))
    {
      Log.debug("Service interruption on VersionTrackingTask");
      return false;
    }
    try
    {
      JSONObject localJSONObject1 = new JSONObject();
      JSONObject localJSONObject2 = new JSONObject();
      localJSONObject2.put("version", this.f.h);
      localJSONObject2.put("machine", this.f.g);
      localJSONObject2.put("capacity", 0);
      localJSONObject2.put("language", this.f.m);
      localJSONObject2.put("countryCode", this.f.l);
      if (((d.a(this.d).d(d.b.I)) && (d.a(this.d).c(d.b.I))) || (this.f.I == b.b.a))
      {
        String str = i.f(this.d);
        if (str != null) {
          localJSONObject2.put("carrierName", str);
        }
      }
      if (this.f.D) {
        if (this.f.c(this.d)) {
          break label331;
        }
      }
      label331:
      for (boolean bool = true;; bool = false)
      {
        localJSONObject2.put("idfaEnabled", bool);
        localJSONObject1.put("device", localJSONObject2);
        localJSONObject2 = new JSONObject();
        localJSONObject2.put("version", this.f.n);
        localJSONObject2.put("name", this.f.i);
        localJSONObject2.put("display", this.f.o);
        localJSONObject1.put("bundle", localJSONObject2);
        localJSONObject1.put("sdk", this.f.a);
        Log.debug("VersionTrackingTask", localJSONObject1);
        this.e = localJSONObject1.toString();
        return true;
      }
      return false;
    }
    catch (Exception localException)
    {
      Log.error("Accengage|Could not build message to send to server", localException);
    }
  }
  
  public c b(c paramc)
  {
    return paramc;
  }
  
  protected String c()
  {
    return d.b.g.toString();
  }
  
  protected String d()
  {
    return this.e;
  }
  
  protected String e()
  {
    return d.a(this.d).a(d.b.g);
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.tracking.VersionTrackingTask";
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/tracking/j.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */