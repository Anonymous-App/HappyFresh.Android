package com.ad4screen.sdk.service.modules.tracking.events;

import android.content.Context;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.common.a;
import com.ad4screen.sdk.common.g;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.common.persistence.e;
import com.ad4screen.sdk.common.tasks.c;
import com.ad4screen.sdk.systems.d;
import com.ad4screen.sdk.systems.d.b;
import org.json.JSONException;
import org.json.JSONObject;

public class b
  extends c
{
  private final String c = "com.ad4screen.sdk.service.modules.tracking.EventLeadTrackingTask";
  private final String d = "content";
  private final Context e;
  private final com.ad4screen.sdk.systems.b f;
  private String g;
  private Lead h;
  
  public b(Context paramContext, com.ad4screen.sdk.systems.b paramb, Lead paramLead)
  {
    super(paramContext);
    this.e = paramContext;
    this.f = paramb;
    this.h = paramLead;
  }
  
  private b(Context paramContext, com.ad4screen.sdk.systems.b paramb, String paramString)
    throws JSONException
  {
    super(paramContext);
    this.e = paramContext;
    this.f = paramb;
    super.d(paramString);
    paramContext = new JSONObject(paramString).getJSONObject("com.ad4screen.sdk.service.modules.tracking.EventLeadTrackingTask");
    if (!paramContext.isNull("content")) {
      this.g = paramContext.getString("content");
    }
  }
  
  protected void a(String paramString)
  {
    Log.debug("EventLeadTrackingTask|Successfully sent lead events to server");
    d.a(this.e).e(d.b.u);
  }
  
  protected void a(Throwable paramThrowable)
  {
    Log.error("EventLeadTrackingTask|Failed to send lead events to server");
  }
  
  protected boolean a()
  {
    h();
    i();
    if (this.h == null)
    {
      Log.debug("Lead is null, cannot send event");
      return false;
    }
    if (!d.a(this.e).c(d.b.u))
    {
      Log.debug("Service interruption on EventTrackingTask");
      return false;
    }
    if (this.f.f == null)
    {
      Log.warn("EventLeadTrackingTask|SharedId is undefined, cannot send event");
      return false;
    }
    try
    {
      JSONObject localJSONObject1 = new e().a(this.h);
      if (this.f.G != null)
      {
        JSONObject localJSONObject2 = new JSONObject();
        localJSONObject2.put("value", this.f.G);
        localJSONObject2.put("date", this.f.H);
        localJSONObject1.put("source", localJSONObject2);
      }
      localJSONObject1.put("date", h.a());
      this.g = localJSONObject1.toString();
      return true;
    }
    catch (Exception localException)
    {
      Log.error("EventLeadTrackingTask|Could not build message to send to server", localException);
    }
    return false;
  }
  
  public c b(c paramc)
  {
    return paramc;
  }
  
  protected String c()
  {
    return d.b.u.toString() + "/" + g.e().a() + "/" + (int)(Math.random() * 10000.0D);
  }
  
  public c d(String paramString)
    throws JSONException
  {
    return new b(this.e, this.f, paramString);
  }
  
  protected String d()
  {
    return this.g;
  }
  
  protected String e()
  {
    return d.a(this.e).a(d.b.u);
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.tracking.EventLeadTrackingTask";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = super.toJSON();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("content", this.g);
    localJSONObject1.put("com.ad4screen.sdk.service.modules.tracking.EventLeadTrackingTask", localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/tracking/events/b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */