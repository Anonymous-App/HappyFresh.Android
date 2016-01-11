package com.ad4screen.sdk.service.modules.common;

import android.content.Context;
import android.text.TextUtils;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.common.tasks.c;
import com.ad4screen.sdk.systems.b;
import com.ad4screen.sdk.systems.d;
import com.ad4screen.sdk.systems.d.b;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class e
  extends c
{
  private final String c = "com.ad4screen.sdk.service.modules.common.TrackInboxTask";
  private final String d = "content";
  private Context e;
  private String f;
  private String g;
  private a h;
  private String i;
  
  public e(Context paramContext, String paramString, a parama)
  {
    super(paramContext);
    this.e = paramContext;
    this.g = paramString;
    this.i = null;
    this.h = parama;
  }
  
  public e(Context paramContext, String paramString1, String paramString2, a parama)
  {
    super(paramContext);
    this.e = paramContext;
    this.g = paramString1;
    this.i = paramString2;
    this.h = parama;
  }
  
  protected void a(String paramString)
  {
    Log.debug("TrackInboxTask|Inbox Tracking successfully sent");
    d.a(this.e).e(d.b.s);
  }
  
  protected void a(Throwable paramThrowable)
  {
    Log.debug("TrackInAppTask|Inbox Tracking failed, will be retried later..");
  }
  
  protected boolean a()
  {
    h();
    i();
    if (this.h == null)
    {
      Log.debug("TrackInboxTask|TrackType is null, cannot send track inbox");
      return false;
    }
    if (TextUtils.isEmpty(this.g))
    {
      Log.debug("TrackInboxTask|InboxId is null, cannot send track inbox");
      return false;
    }
    b localb = b.a(this.e);
    if (localb.f == null)
    {
      Log.warn("TrackInboxTask|No SharedId, not tracking inbox");
      return false;
    }
    if (!d.a(this.e).c(d.b.s))
    {
      Log.debug("Service interruption on TrackInboxTask");
      return false;
    }
    try
    {
      JSONObject localJSONObject1 = new JSONObject();
      JSONObject localJSONObject2 = new JSONObject();
      JSONArray localJSONArray = new JSONArray();
      localJSONObject2.put("date", h.a());
      localJSONObject2.put("notifId", this.g);
      localJSONObject2.put("type", this.h);
      Log.debug("TrackInboxTask", localJSONObject2);
      if (this.i != null) {
        localJSONObject2.put("bid", this.i);
      }
      localJSONArray.put(localJSONObject2);
      localJSONObject1.put("notifs", localJSONArray);
      localJSONObject1.put("sdk", localb.a);
      this.f = localJSONObject1.toString();
      return true;
    }
    catch (Exception localException)
    {
      Log.error("TrackInboxTask|Could not build message to send to Ad4Screen", localException);
    }
    return false;
  }
  
  public c b(c paramc)
  {
    Object localObject = (e)paramc;
    try
    {
      paramc = new JSONObject(d());
      localObject = new JSONObject(((e)localObject).d()).getJSONArray("notifs");
      JSONArray localJSONArray = paramc.getJSONArray("notifs");
      int j = 0;
      while (j < ((JSONArray)localObject).length())
      {
        localJSONArray.put(((JSONArray)localObject).get(j));
        j += 1;
      }
      this.f = paramc.toString();
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
    return d.b.s.toString();
  }
  
  public c d(String paramString)
    throws JSONException
  {
    super.d(paramString);
    paramString = new JSONObject(paramString).getJSONObject("com.ad4screen.sdk.service.modules.common.TrackInboxTask");
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
    return d.a(this.e).a(d.b.s);
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.common.TrackInboxTask";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = super.toJSON();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("content", this.f);
    localJSONObject1.put("com.ad4screen.sdk.service.modules.common.TrackInboxTask", localJSONObject2);
    return localJSONObject1;
  }
  
  public static enum a
  {
    private a() {}
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/common/e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */