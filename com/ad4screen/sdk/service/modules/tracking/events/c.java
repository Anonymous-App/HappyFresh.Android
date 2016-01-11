package com.ad4screen.sdk.service.modules.tracking.events;

import android.content.Context;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.analytics.Purchase;
import com.ad4screen.sdk.common.g;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.common.persistence.e;
import com.ad4screen.sdk.service.modules.tracking.model.a.a;
import com.ad4screen.sdk.systems.b;
import com.ad4screen.sdk.systems.d;
import com.ad4screen.sdk.systems.d.b;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public class c
  extends com.ad4screen.sdk.common.tasks.c
{
  private final String c = "com.ad4screen.sdk.service.modules.tracking.EventPurchaseTrackingTask";
  private final String d = "content";
  private final Context e;
  private final b f;
  private String g;
  private Purchase h;
  
  public c(Context paramContext, b paramb, Purchase paramPurchase)
  {
    super(paramContext);
    this.e = paramContext;
    this.f = paramb;
    this.h = paramPurchase;
  }
  
  private c(Context paramContext, b paramb, String paramString)
    throws JSONException
  {
    super(paramContext);
    this.e = paramContext;
    this.f = paramb;
    super.d(paramString);
    paramContext = new JSONObject(paramString).getJSONObject("com.ad4screen.sdk.service.modules.tracking.EventPurchaseTrackingTask");
    if (!paramContext.isNull("content")) {
      this.g = paramContext.getString("content");
    }
  }
  
  protected void a(String paramString)
  {
    Log.debug("EventPurchaseTrackingTask|Successfully sent purchase events to server");
    d.a(this.e).e(d.b.w);
  }
  
  protected void a(Throwable paramThrowable)
  {
    Log.error("EventPurchaseTrackingTask|Failed to send purchase events to server");
  }
  
  protected boolean a()
  {
    h();
    i();
    if (this.h == null)
    {
      Log.debug("Purchase is null, cannot send event");
      return false;
    }
    if (!d.a(this.e).c(d.b.t))
    {
      Log.debug("Service interruption on EventTrackingTask");
      return false;
    }
    if (this.f.f == null)
    {
      Log.warn("EventPurchaseTrackingTask|SharedId is undefined, cannot send event");
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
      Log.error("EventPurchaseTrackingTask|Could not build message to send to server", localException);
    }
    return false;
  }
  
  protected boolean a(int paramInt, String paramString)
  {
    if ((paramInt == 500) && (paramString != null))
    {
      Log.debug("EventPurchaseTrackingTask|Request succeeded but parameters are invalid, server returned :" + paramString);
      try
      {
        Iterator localIterator = new com.ad4screen.sdk.service.modules.tracking.model.a().a(paramString).a().iterator();
        while (localIterator.hasNext())
        {
          a.a locala = (a.a)localIterator.next();
          if (locala.a().toLowerCase(Locale.US).contains("value"))
          {
            Log.error("EventPurchaseTrackingTask|Error with this purchase : " + locala.b());
            return true;
          }
          if (locala.a().toLowerCase(Locale.US).contains("currency"))
          {
            Log.error("EventPurchaseTrackingTask|Error with this purchase : " + locala.b());
            return true;
          }
        }
      }
      catch (JSONException localJSONException)
      {
        Log.internal("EventPurchaseTrackingTask|Error Parsing failed : " + localJSONException.getMessage(), localJSONException);
      }
    }
    return super.a(paramInt, paramString);
  }
  
  public com.ad4screen.sdk.common.tasks.c b(com.ad4screen.sdk.common.tasks.c paramc)
  {
    return paramc;
  }
  
  protected String c()
  {
    return d.b.w.toString() + "/" + g.e().a() + "/" + (int)(Math.random() * 10000.0D);
  }
  
  public com.ad4screen.sdk.common.tasks.c d(String paramString)
    throws JSONException
  {
    return new c(this.e, this.f, paramString);
  }
  
  protected String d()
  {
    return this.g;
  }
  
  protected String e()
  {
    return d.a(this.e).a(d.b.w);
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.tracking.EventPurchaseTrackingTask";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = super.toJSON();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("content", this.g);
    localJSONObject1.put("com.ad4screen.sdk.service.modules.tracking.EventPurchaseTrackingTask", localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/tracking/events/c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */