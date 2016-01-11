package com.ad4screen.sdk.service.modules.tracking;

import android.content.Context;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.compatibility.k;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.common.tasks.c;
import com.ad4screen.sdk.service.modules.push.g;
import com.ad4screen.sdk.systems.b;
import com.ad4screen.sdk.systems.b.b;
import com.ad4screen.sdk.systems.d;
import com.ad4screen.sdk.systems.d.b;
import org.json.JSONException;
import org.json.JSONObject;

public class i
  extends c
{
  b c;
  private final String d = "com.ad4screen.sdk.service.modules.tracking.TrackingTask";
  private final String e = "content";
  private final Context f;
  private String g;
  
  public i(Context paramContext)
  {
    super(paramContext);
    this.f = paramContext;
    this.c = b.a(this.f);
  }
  
  protected void a(String paramString)
  {
    Log.debug("TrackingTask|Tracking succeed");
    Log.internal("Tracking|Response : " + paramString);
    d.a(this.f).e(d.b.h);
    this.c.b();
    if (((d.a(this.f).d(d.b.H)) && (d.a(this.f).c(d.b.H))) || (this.c.I == b.b.a)) {
      d.a(this.f).e(d.b.H);
    }
    try
    {
      paramString = new JSONObject(paramString);
      if (!paramString.isNull("source"))
      {
        paramString = paramString.getString("source");
        this.c.f(paramString);
        Log.debug("Tracking|New source : " + paramString);
      }
      return;
    }
    catch (JSONException paramString)
    {
      Log.internal("Accengage|Could not parse server response", paramString);
    }
  }
  
  protected void a(Throwable paramThrowable)
  {
    Log.error("Tracking|Tracking failed", paramThrowable);
  }
  
  protected boolean a()
  {
    b(6);
    h();
    i();
    if (!d.a(this.f).c(d.b.h))
    {
      Log.debug("Service interruption on TrackingTask");
      return false;
    }
    if (this.c.f == null)
    {
      Log.warn("TrackingTask|No sharedId, skipping configuration");
      return false;
    }
    try
    {
      JSONObject localJSONObject = new JSONObject();
      localJSONObject.put("date", h.a());
      localJSONObject.put("install", this.c.p);
      Object localObject = new JSONObject();
      ((JSONObject)localObject).put("open", this.c.r);
      ((JSONObject)localObject).put("tracking", this.c.s);
      localJSONObject.put("count", localObject);
      boolean bool;
      if (((d.a(this.f).d(d.b.H)) && (d.a(this.f).c(d.b.H))) || (this.c.I == b.b.a))
      {
        if (com.ad4screen.sdk.common.i.h(this.f))
        {
          localObject = "wifi";
          localJSONObject.put("connection", localObject);
        }
      }
      else
      {
        localJSONObject.put("ua", k.a(this.f));
        if (!g.b(this.f)) {
          break label321;
        }
        if (com.ad4screen.sdk.common.i.i(this.f)) {
          break label316;
        }
        bool = true;
      }
      for (;;)
      {
        localJSONObject.put("notificationsEnabled", bool);
        if (this.c.G != null)
        {
          localObject = new JSONObject();
          ((JSONObject)localObject).put("value", this.c.G);
          ((JSONObject)localObject).put("date", this.c.H);
          localJSONObject.put("source", localObject);
        }
        Log.debug("TrackingTask", localJSONObject);
        this.g = localJSONObject.toString();
        return true;
        localObject = "cell";
        break;
        label316:
        bool = false;
        continue;
        label321:
        bool = false;
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
    return d.b.h.toString();
  }
  
  public c d(String paramString)
    throws JSONException
  {
    super.d(paramString);
    paramString = new JSONObject(paramString).getJSONObject("com.ad4screen.sdk.service.modules.tracking.TrackingTask");
    if (!paramString.isNull("content")) {
      this.g = paramString.getString("content");
    }
    return this;
  }
  
  protected String d()
  {
    return this.g;
  }
  
  protected String e()
  {
    return d.a(this.f).a(d.b.h);
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.tracking.TrackingTask";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = super.toJSON();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("content", this.g);
    localJSONObject1.put("com.ad4screen.sdk.service.modules.tracking.TrackingTask", localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/tracking/i.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */