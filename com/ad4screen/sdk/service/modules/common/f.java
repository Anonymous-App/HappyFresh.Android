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

public class f
  extends c
{
  private final String c = "com.ad4screen.sdk.service.modules.common.TrackPushTask";
  private final String d = "content";
  private Context e;
  private String f;
  private String g;
  
  public f(Context paramContext, String paramString)
  {
    super(paramContext);
    this.e = paramContext;
    this.f = paramString;
  }
  
  protected void a(String paramString)
  {
    Log.debug("TrackPushTask|Successfully tracked push");
    d.a(this.e).e(d.b.q);
  }
  
  protected void a(Throwable paramThrowable)
  {
    Log.error("TrackPushTask|Push Tracking failed, will be retried later..");
  }
  
  protected boolean a()
  {
    h();
    i();
    if (TextUtils.isEmpty(this.f))
    {
      Log.debug("TrackId is null, cannot send track push");
      return false;
    }
    if (b.a(this.e).f == null)
    {
      Log.warn("TrackPushTask|No SharedId, not tracking push");
      return false;
    }
    if (!d.a(this.e).c(d.b.q))
    {
      Log.debug("Service interruption on TrackPushTask");
      return false;
    }
    try
    {
      JSONObject localJSONObject1 = new JSONObject();
      JSONObject localJSONObject2 = new JSONObject();
      JSONArray localJSONArray = new JSONArray();
      if (this.f.contains("#"))
      {
        localJSONObject2.put("bid", this.f.split("#")[1]);
        this.f = this.f.split("#")[0];
      }
      localJSONObject2.put("trackId", this.f);
      localJSONObject2.put("date", h.a());
      Log.debug("TrackPushTask", localJSONObject2);
      localJSONArray.put(localJSONObject2);
      localJSONObject1.put("pushes", localJSONArray);
      this.g = localJSONObject1.toString();
      return true;
    }
    catch (Exception localException)
    {
      Log.error("Push|Could not build message to send to Ad4Screen", localException);
    }
    return false;
  }
  
  public c b(c paramc)
  {
    Object localObject = (f)paramc;
    try
    {
      paramc = new JSONObject(d());
      localObject = new JSONObject(((f)localObject).d()).getJSONArray("pushes");
      JSONArray localJSONArray = paramc.getJSONArray("pushes");
      int i = 0;
      while (i < ((JSONArray)localObject).length())
      {
        localJSONArray.put(((JSONArray)localObject).get(i));
        i += 1;
      }
      this.g = paramc.toString();
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
    return d.b.q.toString();
  }
  
  public c d(String paramString)
    throws JSONException
  {
    super.d(paramString);
    paramString = new JSONObject(paramString).getJSONObject("com.ad4screen.sdk.service.modules.common.TrackPushTask");
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
    return d.a(this.e).a(d.b.q);
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.common.TrackPushTask";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = super.toJSON();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("content", this.g);
    localJSONObject1.put("com.ad4screen.sdk.service.modules.common.TrackPushTask", localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/common/f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */