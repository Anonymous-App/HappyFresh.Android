package com.ad4screen.sdk.service.modules.common;

import android.content.Context;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.cache.a;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.service.modules.tracking.h.b;
import com.ad4screen.sdk.systems.d.b;
import com.ad4screen.sdk.systems.f;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class b
  extends com.ad4screen.sdk.common.tasks.c
{
  private final String c = "com.ad4screen.sdk.service.modules.common.ConfigurationTask";
  private final Context d;
  private com.ad4screen.sdk.systems.b e;
  
  public b(Context paramContext)
  {
    super(paramContext);
    this.d = paramContext;
    this.e = com.ad4screen.sdk.systems.b.a(this.d);
  }
  
  protected void a(String paramString)
  {
    int j = 0;
    Log.internal("ConfigurationTask|Configuration request succeed with response : " + paramString);
    com.ad4screen.sdk.systems.d.a(this.d).e(d.b.i);
    for (;;)
    {
      Object localObject1;
      Object localObject2;
      int i;
      try
      {
        paramString = new JSONObject(paramString);
        if (!paramString.isNull("facebookId"))
        {
          localObject1 = this.e.E;
          localObject2 = String.valueOf(paramString.getInt("facebookId"));
          this.e.d((String)localObject2);
          if (!((String)localObject1).equals(localObject2)) {
            f.a().a(new h.b((String)localObject2));
          }
          Log.debug("ConfigurationTask|Facebook App Id Updated to " + (String)localObject2);
        }
        if (!paramString.isNull("gaid"))
        {
          this.e.e(paramString.getString("gaid"));
          Log.debug("ConfigurationTask|Google Analytics UA Updated to " + paramString.getString("gaid"));
        }
        com.ad4screen.sdk.systems.d.a(this.d).c();
        com.ad4screen.sdk.systems.d.a(this.d).d();
        if (!paramString.isNull("interruptions"))
        {
          localObject1 = paramString.getJSONArray("interruptions");
          i = 0;
          if (i < ((JSONArray)localObject1).length())
          {
            localObject2 = new com.ad4screen.sdk.service.modules.tracking.model.d();
            ((com.ad4screen.sdk.service.modules.tracking.model.d)localObject2).a = ((JSONArray)localObject1).getJSONObject(i).getString("serviceName");
            ((com.ad4screen.sdk.service.modules.tracking.model.d)localObject2).b = ((JSONArray)localObject1).getJSONObject(i).getInt("frequency");
            ((com.ad4screen.sdk.service.modules.tracking.model.d)localObject2).c = h.a(((JSONArray)localObject1).getJSONObject(i).getString("nextTime"), com.ad4screen.sdk.common.h.a.b).getTime();
            d.b localb = d.b.a(((com.ad4screen.sdk.service.modules.tracking.model.d)localObject2).a);
            if (localb == null)
            {
              Log.internal("ConfigurationTask|Could not find service with name '" + ((com.ad4screen.sdk.service.modules.tracking.model.d)localObject2).a + "'");
            }
            else
            {
              com.ad4screen.sdk.systems.d.a(this.d).a(localb, ((com.ad4screen.sdk.service.modules.tracking.model.d)localObject2).b);
              com.ad4screen.sdk.systems.d.a(this.d).a(localb, ((com.ad4screen.sdk.service.modules.tracking.model.d)localObject2).c);
              Log.warn("ConfigurationTask|Updated '" + ((com.ad4screen.sdk.service.modules.tracking.model.d)localObject2).a + "' interruption date to " + h.a(new Date(((com.ad4screen.sdk.service.modules.tracking.model.d)localObject2).c), com.ad4screen.sdk.common.h.a.b) + " and frequency " + ((com.ad4screen.sdk.service.modules.tracking.model.d)localObject2).b);
            }
          }
        }
      }
      catch (JSONException paramString)
      {
        Log.internal("Accengage|Could not parse server response", paramString);
      }
      for (;;)
      {
        if ((this.e.d() != null) && (this.e.c() != null) && (this.e.d().before(this.e.K)))
        {
          Log.debug("ConfigurationTask|We need to refresh routes, refreshing now...");
          a.a(this.d).a(new c(this.d));
        }
        return;
        Log.debug("ConfigurationTask|Service interruptions updated");
        if (!paramString.isNull("events"))
        {
          localObject1 = paramString.getJSONArray("events");
          localObject2 = new com.ad4screen.sdk.service.modules.tracking.model.c();
          ((com.ad4screen.sdk.service.modules.tracking.model.c)localObject2).a = new com.ad4screen.sdk.service.modules.tracking.model.b[((JSONArray)localObject1).length()];
          i = j;
          while (i < ((JSONArray)localObject1).length())
          {
            ((com.ad4screen.sdk.service.modules.tracking.model.c)localObject2).a[i] = new com.ad4screen.sdk.service.modules.tracking.model.b();
            localObject2.a[i].a = String.valueOf(((JSONArray)localObject1).getJSONObject(i).getInt("name"));
            localObject2.a[i].b = ((JSONArray)localObject1).getJSONObject(i).getInt("value");
            Log.debug("ConfigurationTask|Updated Event " + localObject2.a[i].a + " dispatch to " + localObject2.a[i].b);
            i += 1;
          }
          Log.debug("ConfigurationTask|Event dispatch updated");
          f.a().a(new com.ad4screen.sdk.service.modules.tracking.h.a((com.ad4screen.sdk.service.modules.tracking.model.c)localObject2));
        }
        if (!paramString.isNull("lastReloadRoutes"))
        {
          this.e.a(h.a(paramString.getString("lastReloadRoutes"), com.ad4screen.sdk.common.h.a.b));
          Log.debug("ConfigurationTask|Last reload routes date updated to " + h.a(paramString.getString("lastReloadRoutes"), com.ad4screen.sdk.common.h.a.b));
        }
      }
      i += 1;
    }
  }
  
  protected void a(Throwable paramThrowable)
  {
    Log.internal("ConfigurationTask|Tracking failed", paramThrowable);
  }
  
  protected boolean a()
  {
    a(4);
    h();
    i();
    if (!com.ad4screen.sdk.systems.d.a(this.d).b(d.b.i))
    {
      Log.debug("Service interruption on ConfigurationTask");
      return false;
    }
    if (this.e.f == null)
    {
      Log.warn("ConfigurationTask|No sharedId, skipping configuration");
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
    return d.b.i.toString();
  }
  
  protected String d()
  {
    return null;
  }
  
  protected String e()
  {
    return com.ad4screen.sdk.systems.d.a(this.d).a(d.b.i);
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.common.ConfigurationTask";
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/common/b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */