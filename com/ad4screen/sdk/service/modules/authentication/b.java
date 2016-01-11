package com.ad4screen.sdk.service.modules.authentication;

import android.content.Context;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.tasks.c;
import com.ad4screen.sdk.service.modules.authentication.model.a;
import com.ad4screen.sdk.systems.d;
import com.ad4screen.sdk.systems.d.b;
import com.ad4screen.sdk.systems.f;
import com.ad4screen.sdk.systems.h;
import org.json.JSONException;
import org.json.JSONObject;

public class b
  extends c
{
  private final String c = "com.ad4screen.sdk.service.modules.authentication.AuthenticationTask";
  private final Context d;
  private String e;
  private com.ad4screen.sdk.systems.b f;
  
  public b(Context paramContext)
  {
    super(paramContext);
    this.d = paramContext;
    this.f = com.ad4screen.sdk.systems.b.a(this.d);
  }
  
  protected void a(String paramString)
  {
    try
    {
      Log.internal("AuthenticationTask|Authentication response start parsing");
      Object localObject = new JSONObject(paramString);
      if ((((JSONObject)localObject).isNull("access_token")) || (((JSONObject)localObject).isNull("token_type")))
      {
        Log.error("AuthenticationTask|Response parsing failed");
        f.a().a(new a.a());
        return;
      }
      Log.internal("AuthenticationTask|Received Token : " + ((JSONObject)localObject).getString("access_token"));
      d.a(this.d).e(d.b.b);
      paramString = new a(((JSONObject)localObject).getString("access_token"), ((JSONObject)localObject).getString("token_type"));
      h.a(this.d).a(paramString);
      boolean bool2 = false;
      boolean bool1 = bool2;
      if (!((JSONObject)localObject).isNull("sharedId"))
      {
        localObject = ((JSONObject)localObject).getString("sharedId");
        if (this.f.f != null)
        {
          bool1 = bool2;
          if (this.f.f.equals(localObject)) {}
        }
        else
        {
          this.f.b((String)localObject);
          bool1 = true;
        }
      }
      Log.debug("AuthenticationTask|Authentication succeed. Shared Id : " + this.f.f);
      f.a().a(new a.b(paramString, bool1));
      return;
    }
    catch (JSONException paramString)
    {
      Log.debug("AuthenticationTask|Response JSON Parsing error!", paramString);
      f.a().a(new a.a());
    }
  }
  
  protected void a(Throwable paramThrowable)
  {
    Log.error("AuthenticationTask|Authentication failed.");
    f.a().a(new a.a());
  }
  
  protected boolean a()
  {
    if (this.f.f == null)
    {
      Log.warn("AuthenticationTask|No sharedId, skipping configuration");
      f.a().a(new a.a());
      return false;
    }
    if (!d.a(this.d).b(d.b.b))
    {
      Log.debug("Service interruption on AuthenticationTask");
      f.a().a(new a.a());
      return false;
    }
    try
    {
      JSONObject localJSONObject1 = new JSONObject();
      localJSONObject1.put("partnerId", this.f.d);
      JSONObject localJSONObject2 = new JSONObject();
      if (this.f.D)
      {
        String str = this.f.b(this.d);
        if (str == null) {
          break label207;
        }
        Log.debug("AdvertiserPlugin|Advertiser id returned from plugin : " + str);
        localJSONObject2.put("idfa", str);
      }
      for (;;)
      {
        localJSONObject2.put("idfv", this.f.c);
        localJSONObject1.put("deviceId", localJSONObject2);
        localJSONObject1.put("sharedId", this.f.f);
        this.e = localJSONObject1.toString();
        a(4);
        h();
        return true;
        label207:
        Log.debug("TrackingTask|No Advertiser id found, using android id : " + this.f.b);
        localJSONObject2.put("androidid", this.f.b);
      }
      return false;
    }
    catch (JSONException localJSONException)
    {
      Log.error("AuthenticationTask|Could not build message to send to Ad4Screen", localJSONException);
      f.a().a(new a.a());
    }
  }
  
  public c b(c paramc)
  {
    return paramc;
  }
  
  protected String c()
  {
    return d.b.b.toString();
  }
  
  protected String d()
  {
    return this.e;
  }
  
  protected String e()
  {
    return d.a(this.d).a(d.b.b);
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.authentication.AuthenticationTask";
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/authentication/b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */