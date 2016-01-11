package com.ad4screen.sdk.service.modules.push;

import android.content.Context;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.tasks.c;
import com.ad4screen.sdk.systems.b;
import com.ad4screen.sdk.systems.d;
import com.ad4screen.sdk.systems.d.b;
import org.json.JSONException;
import org.json.JSONObject;

public class f
  extends c
{
  private final String c = "token";
  private final String d = "releaseMode";
  private final String e = "timezone";
  private final String f = "fresh";
  private final String g = "com.ad4screen.sdk.service.modules.push.SendRegistrationTokenTask";
  private final String h = "content";
  private final String i = "newToken";
  private final String j = "token";
  private final String k = "tokenType";
  private final Context l;
  private b m;
  private boolean n;
  private String o;
  private a p = a.b;
  private String q;
  
  public f(Context paramContext, String paramString, a parama, boolean paramBoolean)
  {
    super(paramContext);
    this.l = paramContext;
    this.m = b.a(this.l);
    this.n = paramBoolean;
    this.o = paramString;
    if (parama != null) {
      this.p = parama;
    }
  }
  
  protected void a(String paramString)
  {
    Log.debug("The following GCM registration token has been successfully sent : " + this.o);
    d.a(this.l).e(d.b.x);
    com.ad4screen.sdk.systems.f.a().a(new d.d());
  }
  
  protected void a(Throwable paramThrowable)
  {
    Log.debug("Failed to send GCM registration token to server");
    com.ad4screen.sdk.systems.f.a().a(new d.c());
  }
  
  protected boolean a()
  {
    h();
    i();
    if (this.m.f == null)
    {
      Log.warn("Push|No SharedId, not sending token");
      return false;
    }
    if (!d.a(this.l).c(d.b.x))
    {
      Log.debug("Service interruption on SendRegistrationTokenTask");
      return false;
    }
    try
    {
      JSONObject localJSONObject = new JSONObject();
      localJSONObject.put("token", this.o);
      localJSONObject.put("releaseMode", this.p);
      localJSONObject.put("timezone", this.m.q);
      localJSONObject.put("fresh", this.n);
      this.q = localJSONObject.toString();
      return true;
    }
    catch (JSONException localJSONException)
    {
      Log.error("Push|Could not build message to send to server", localJSONException);
      com.ad4screen.sdk.systems.f.a().a(new d.c());
    }
    return false;
  }
  
  public c b(c paramc)
  {
    return paramc;
  }
  
  protected String c()
  {
    return d.b.x.toString();
  }
  
  public c d(String paramString)
    throws JSONException
  {
    super.d(paramString);
    paramString = new JSONObject(paramString).getJSONObject("com.ad4screen.sdk.service.modules.push.SendRegistrationTokenTask");
    if (!paramString.isNull("content")) {
      this.q = paramString.getString("content");
    }
    if (!paramString.isNull("newToken")) {
      this.n = paramString.getBoolean("newToken");
    }
    if (!paramString.isNull("token")) {
      this.o = paramString.getString("token");
    }
    if (!paramString.isNull("tokenType")) {
      this.p = a.valueOf(paramString.getString("tokenType"));
    }
    return this;
  }
  
  protected String d()
  {
    return this.q;
  }
  
  protected String e()
  {
    return d.a(this.l).a(d.b.x);
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.push.SendRegistrationTokenTask";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = super.toJSON();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("content", this.q);
    localJSONObject2.put("newToken", this.n);
    localJSONObject2.put("token", this.o);
    localJSONObject2.put("tokenType", this.p.toString());
    localJSONObject1.put("com.ad4screen.sdk.service.modules.push.SendRegistrationTokenTask", localJSONObject2);
    return localJSONObject1;
  }
  
  public static enum a
  {
    private a() {}
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/push/f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */