package com.ad4screen.sdk.service.modules.common;

import android.content.Context;
import android.text.TextUtils;
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
  private final String c = "com.ad4screen.sdk.service.modules.common.TrackInAppTask";
  private final String d = "content";
  private Context e;
  private String f;
  private String g;
  private a h;
  private String i;
  
  public d(Context paramContext, String paramString, a parama)
  {
    super(paramContext);
    this.e = paramContext;
    this.g = paramString;
    this.i = null;
    this.h = parama;
  }
  
  public d(Context paramContext, String paramString1, String paramString2, a parama)
  {
    super(paramContext);
    this.e = paramContext;
    this.g = paramString1;
    this.i = paramString2;
    this.h = parama;
  }
  
  protected void a(String paramString)
  {
    Log.debug("TrackInAppTask|InApp Tracking successfully sent");
    com.ad4screen.sdk.systems.d.a(this.e).e(d.b.r);
  }
  
  protected void a(Throwable paramThrowable)
  {
    Log.error("TrackInAppTask|InApp Tracking failed, will be retried later..");
  }
  
  protected boolean a()
  {
    h();
    i();
    if ((TextUtils.isEmpty(this.g)) || (this.h == null))
    {
      Log.debug("TrackInAppTask|NotifId or Type is null, cannot send track in-app");
      return false;
    }
    b localb = b.a(this.e);
    if (localb.f == null)
    {
      Log.warn("TrackInAppTask|No SharedId, not tracking in-app");
      return false;
    }
    if (!com.ad4screen.sdk.systems.d.a(this.e).c(d.b.r))
    {
      Log.debug("Service interruption on TrackInAppTask");
      return false;
    }
    try
    {
      JSONObject localJSONObject1 = new JSONObject();
      JSONObject localJSONObject2 = new JSONObject();
      JSONArray localJSONArray = new JSONArray();
      localJSONObject2.put("date", h.a());
      localJSONObject2.put("type", this.h.toString());
      localJSONObject2.put("notifId", this.g);
      Log.debug("TrackInAppTask", localJSONObject2);
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
      Log.error("TrackInAppTask|Could not build message to send to Ad4Screen", localException);
    }
    return false;
  }
  
  public c b(c paramc)
  {
    Object localObject = (d)paramc;
    try
    {
      paramc = new JSONObject(d());
      localObject = new JSONObject(((d)localObject).d()).getJSONArray("notifs");
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
    return d.b.r.toString();
  }
  
  public c d(String paramString)
    throws JSONException
  {
    super.d(paramString);
    paramString = new JSONObject(paramString).getJSONObject("com.ad4screen.sdk.service.modules.common.TrackInAppTask");
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
    return com.ad4screen.sdk.systems.d.a(this.e).a(d.b.r);
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.common.TrackInAppTask";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = super.toJSON();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("content", this.f);
    localJSONObject1.put("com.ad4screen.sdk.service.modules.common.TrackInAppTask", localJSONObject2);
    return localJSONObject1;
  }
  
  public static enum a
  {
    private a() {}
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/common/d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */