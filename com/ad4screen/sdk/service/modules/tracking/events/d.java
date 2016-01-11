package com.ad4screen.sdk.service.modules.tracking.events;

import android.content.Context;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.common.tasks.c;
import com.ad4screen.sdk.systems.b;
import com.ad4screen.sdk.systems.d.b;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class d
  extends c
{
  private final String c = "com.ad4screen.sdk.service.modules.tracking.EventTrackingTask";
  private final String d = "content";
  private final Context e;
  private final String f;
  private final b g;
  private String h;
  private Long i;
  
  public d(Context paramContext, b paramb, Long paramLong, String paramString)
  {
    super(paramContext);
    this.e = paramContext;
    this.g = paramb;
    this.i = paramLong;
    this.f = paramString;
  }
  
  protected void a(String paramString)
  {
    Log.debug("EventTrackingTask|Successfully sent events to server");
    com.ad4screen.sdk.systems.d.a(this.e).e(d.b.t);
  }
  
  protected void a(Throwable paramThrowable)
  {
    Log.error("EventTrackingTask|Failed to send events to server");
  }
  
  protected boolean a()
  {
    h();
    i();
    if ((this.i == null) || (this.f == null))
    {
      Log.debug("Event type or value is null, cannot send event");
      return false;
    }
    if (!com.ad4screen.sdk.systems.d.a(this.e).c(d.b.t))
    {
      Log.debug("Service interruption on EventTrackingTask");
      return false;
    }
    if (this.g.f == null)
    {
      Log.warn("EventTrackingTask|SharedId is undefined, cannot send event");
      return false;
    }
    try
    {
      JSONObject localJSONObject1 = new JSONObject();
      JSONArray localJSONArray = new JSONArray();
      JSONObject localJSONObject2 = new JSONObject();
      localJSONObject2.put("type", this.i);
      localJSONObject2.put("date", h.a());
      localJSONObject2.put("value", this.f);
      if (this.g.G != null)
      {
        JSONObject localJSONObject3 = new JSONObject();
        localJSONObject3.put("value", this.g.G);
        localJSONObject3.put("date", this.g.H);
        localJSONObject2.put("source", localJSONObject3);
      }
      localJSONArray.put(localJSONObject2);
      localJSONObject1.put("events", localJSONArray);
      this.h = localJSONObject1.toString();
      return true;
    }
    catch (Exception localException)
    {
      Log.error("EventTrackingTask|Could not build message to send to server", localException);
    }
    return false;
  }
  
  public c b(c paramc)
  {
    Object localObject = (d)paramc;
    try
    {
      paramc = new JSONObject(d());
      localObject = new JSONObject(((d)localObject).d()).getJSONArray("events");
      JSONArray localJSONArray = paramc.getJSONArray("events");
      int j = 0;
      while (j < ((JSONArray)localObject).length())
      {
        localJSONArray.put(((JSONArray)localObject).get(j));
        j += 1;
      }
      this.h = paramc.toString();
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
    return d.b.t.toString();
  }
  
  public c d(String paramString)
    throws JSONException
  {
    super.d(paramString);
    paramString = new JSONObject(paramString).getJSONObject("com.ad4screen.sdk.service.modules.tracking.EventTrackingTask");
    if (!paramString.isNull("content")) {
      this.h = paramString.getString("content");
    }
    return this;
  }
  
  protected String d()
  {
    return this.h;
  }
  
  protected String e()
  {
    return com.ad4screen.sdk.systems.d.a(this.e).a(d.b.t);
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.tracking.EventTrackingTask";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = super.toJSON();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("content", this.h);
    localJSONObject2.put("type", this.i);
    localJSONObject1.put("com.ad4screen.sdk.service.modules.tracking.EventTrackingTask", localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/tracking/events/d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */