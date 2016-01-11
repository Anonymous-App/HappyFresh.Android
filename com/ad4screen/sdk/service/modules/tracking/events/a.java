package com.ad4screen.sdk.service.modules.tracking.events;

import android.content.Context;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.analytics.Cart;
import com.ad4screen.sdk.common.g;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.common.persistence.e;
import com.ad4screen.sdk.common.tasks.c;
import com.ad4screen.sdk.service.modules.tracking.model.a.a;
import com.ad4screen.sdk.systems.b;
import com.ad4screen.sdk.systems.d;
import com.ad4screen.sdk.systems.d.b;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public class a
  extends c
{
  private final String c = "com.ad4screen.sdk.service.modules.tracking.EventCartTrackingTask";
  private final String d = "content";
  private final Context e;
  private final b f;
  private String g;
  private Cart h;
  
  public a(Context paramContext, b paramb, Cart paramCart)
  {
    super(paramContext);
    this.e = paramContext;
    this.f = paramb;
    this.h = paramCart;
  }
  
  private a(Context paramContext, b paramb, String paramString)
    throws JSONException
  {
    super(paramContext);
    this.e = paramContext;
    this.f = paramb;
    super.d(paramString);
    paramContext = new JSONObject(paramString).getJSONObject("com.ad4screen.sdk.service.modules.tracking.EventCartTrackingTask");
    if (!paramContext.isNull("content")) {
      this.g = paramContext.getString("content");
    }
  }
  
  protected void a(String paramString)
  {
    Log.debug("EventCartTrackingTask|Successfully sent cart events to server");
    d.a(this.e).e(d.b.v);
  }
  
  protected void a(Throwable paramThrowable)
  {
    Log.error("EventCartTrackingTask|Failed to send cart events to server");
  }
  
  protected boolean a()
  {
    h();
    i();
    if (this.h == null)
    {
      Log.debug("Cart is null, cannot send event");
      return false;
    }
    if (!d.a(this.e).c(d.b.t))
    {
      Log.debug("Service interruption on EventTrackingTask");
      return false;
    }
    if (this.f.f == null)
    {
      Log.warn("EventCartTrackingTask|SharedId is undefined, cannot send event");
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
      Log.error("EventCartTrackingTask|Could not build message to send to server", localException);
    }
    return false;
  }
  
  protected boolean a(int paramInt, String paramString)
  {
    if ((paramInt == 500) && (paramString != null))
    {
      Log.debug("EventCartTrackingTask|Request succeed but parameters are invalid, server returned :" + paramString);
      try
      {
        Iterator localIterator = new com.ad4screen.sdk.service.modules.tracking.model.a().a(paramString).a().iterator();
        while (localIterator.hasNext())
        {
          a.a locala = (a.a)localIterator.next();
          if (locala.a().toLowerCase(Locale.US).contains("currency"))
          {
            Log.error("EventCartTrackingTask|Error with this cart : " + locala.b());
            return true;
          }
        }
      }
      catch (JSONException localJSONException)
      {
        Log.internal("EventCartTrackingTask|Error Parsing failed : " + localJSONException.getMessage(), localJSONException);
      }
    }
    return super.a(paramInt, paramString);
  }
  
  public c b(c paramc)
  {
    return paramc;
  }
  
  protected String c()
  {
    return d.b.v.toString() + "/" + g.e().a() + "/" + (int)(Math.random() * 10000.0D);
  }
  
  public c d(String paramString)
    throws JSONException
  {
    return new a(this.e, this.f, paramString);
  }
  
  protected String d()
  {
    return this.g;
  }
  
  protected String e()
  {
    return d.a(this.e).a(d.b.v);
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.tracking.EventCartTrackingTask";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = super.toJSON();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("content", this.g);
    localJSONObject1.put("com.ad4screen.sdk.service.modules.tracking.EventCartTrackingTask", localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/tracking/events/a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */