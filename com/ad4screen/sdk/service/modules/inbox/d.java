package com.ad4screen.sdk.service.modules.inbox;

import android.content.Context;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.systems.b;
import com.ad4screen.sdk.systems.d.b;
import com.ad4screen.sdk.systems.f;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class d
  extends com.ad4screen.sdk.common.tasks.c
{
  private final String c = "com.ad4screen.sdk.service.modules.inbox.LoadMessagesTask";
  private final String d = "content";
  private final Context e;
  private String f;
  private String[] g;
  
  public d(Context paramContext)
  {
    super(paramContext);
    this.e = paramContext;
  }
  
  public d(String[] paramArrayOfString, Context paramContext)
  {
    super(paramContext);
    this.g = paramArrayOfString;
    this.e = paramContext;
  }
  
  protected void a(String paramString)
  {
    for (;;)
    {
      c localc;
      try
      {
        Log.debug("Inbox|Successfully loaded messages");
        Log.internal("Inbox|Messages start parsing");
        paramString = new JSONObject(paramString);
        localc = new c();
        localc.a(paramString);
        if (localc.a == null)
        {
          Log.error("Inbox|Messages parsing failed");
          f.a().a(new a.a());
          return;
        }
        Log.internal("Inbox|Messages parsing success");
        if (this.g != null)
        {
          com.ad4screen.sdk.systems.d.a(this.e).e(d.b.o);
          f.a().a(new a.b(localc.a));
          return;
        }
      }
      catch (JSONException paramString)
      {
        Log.internal("Inbox|Response JSON Parsing error!", paramString);
        f.a().a(new a.a());
        return;
      }
      Log.debug("Inbox|" + localc.a.length + " inbox messages loaded");
      com.ad4screen.sdk.systems.d.a(this.e).e(d.b.n);
    }
  }
  
  protected void a(Throwable paramThrowable)
  {
    Log.debug("Inbox|Failed to load inbox messages");
    f.a().a(new a.a());
  }
  
  protected boolean a()
  {
    c("application/json;charset=utf-8");
    a(4);
    Object localObject = b.a(this.e);
    if (((b)localObject).f == null)
    {
      Log.warn("Inbox|No sharedId, skipping configuration");
      f.a().a(new a.a());
      return false;
    }
    if ((this.g == null) && (!com.ad4screen.sdk.systems.d.a(this.e).c(d.b.n)))
    {
      Log.debug("Service interruption on LoadMessagesTask (List)");
      f.a().a(new a.a());
      return false;
    }
    if ((this.g != null) && (!com.ad4screen.sdk.systems.d.a(this.e).c(d.b.o)))
    {
      Log.debug("Service interruption on LoadMessageTask (Detail)");
      f.a().a(new a.a());
      return false;
    }
    try
    {
      JSONObject localJSONObject = new JSONObject();
      localJSONObject.put("partnerId", ((b)localObject).d);
      localJSONObject.put("sharedId", ((b)localObject).f);
      if (this.g != null)
      {
        localObject = new JSONArray();
        int i = 0;
        while (i < this.g.length)
        {
          ((JSONArray)localObject).put(this.g[i]);
          i += 1;
        }
        localJSONObject.put("messageId", localObject);
      }
      this.f = localJSONObject.toString();
      return true;
    }
    catch (JSONException localJSONException)
    {
      Log.error("Inbox|Could not build message to send to Ad4Screen", localJSONException);
      f.a().a(new a.a());
    }
    return false;
  }
  
  public com.ad4screen.sdk.common.tasks.c b(com.ad4screen.sdk.common.tasks.c paramc)
  {
    return paramc;
  }
  
  protected String c()
  {
    return d.b.n.toString();
  }
  
  public com.ad4screen.sdk.common.tasks.c d(String paramString)
    throws JSONException
  {
    super.d(paramString);
    paramString = new JSONObject(paramString).getJSONObject("com.ad4screen.sdk.service.modules.inbox.LoadMessagesTask");
    if (!paramString.isNull("content")) {
      this.f = paramString.getString("content");
    }
    return this;
  }
  
  protected String d()
  {
    return this.f;
  }
  
  protected String e()
  {
    if (this.g != null) {
      return com.ad4screen.sdk.systems.d.a(this.e).a(d.b.o);
    }
    return com.ad4screen.sdk.systems.d.a(this.e).a(d.b.n);
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.inbox.LoadMessagesTask";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = super.toJSON();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("content", this.f);
    localJSONObject1.put("com.ad4screen.sdk.service.modules.inbox.LoadMessagesTask", localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inbox/d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */