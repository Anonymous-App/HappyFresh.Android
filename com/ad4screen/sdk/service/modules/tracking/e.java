package com.ad4screen.sdk.service.modules.tracking;

import android.content.Context;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.common.tasks.c;
import com.ad4screen.sdk.systems.b;
import com.ad4screen.sdk.systems.d;
import com.ad4screen.sdk.systems.d.b;
import org.json.JSONException;
import org.json.JSONObject;

public class e
  extends c
{
  private final String c = "com.ad4screen.sdk.service.modules.tracking.ReferrerTrackingTask";
  private final String d = "content";
  private final Context e;
  private final b f;
  private String g;
  
  public e(Context paramContext)
  {
    super(paramContext);
    this.e = paramContext;
    this.f = b.a(this.e);
  }
  
  protected void a(String paramString)
  {
    d.a(this.e).e(d.b.j);
  }
  
  protected void a(Throwable paramThrowable) {}
  
  protected boolean a()
  {
    h();
    i();
    if (!d.a(this.e).c(d.b.j))
    {
      Log.debug("Service interruption on ReferrerTrackingTask");
      return false;
    }
    if (this.f.f == null)
    {
      Log.warn("Accengage|SharedId is undefined, cannot send event");
      return false;
    }
    try
    {
      JSONObject localJSONObject = new JSONObject();
      localJSONObject.put("date", h.a());
      localJSONObject.put("referrer", this.f.t);
      this.g = localJSONObject.toString();
      return true;
    }
    catch (Exception localException)
    {
      Log.error("Accengage|Could not build message to send to server", localException);
    }
    return false;
  }
  
  public c b(c paramc)
  {
    return paramc;
  }
  
  protected String c()
  {
    return d.b.j.toString();
  }
  
  public c d(String paramString)
    throws JSONException
  {
    super.d(paramString);
    paramString = new JSONObject(paramString).getJSONObject("com.ad4screen.sdk.service.modules.tracking.ReferrerTrackingTask");
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
    return d.a(this.e).a(d.b.j);
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.tracking.ReferrerTrackingTask";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = super.toJSON();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("content", this.g);
    localJSONObject1.put("com.ad4screen.sdk.service.modules.tracking.ReferrerTrackingTask", localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/tracking/e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */