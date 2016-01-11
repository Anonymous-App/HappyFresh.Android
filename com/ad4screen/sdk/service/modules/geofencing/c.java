package com.ad4screen.sdk.service.modules.geofencing;

import android.content.Context;
import android.os.Bundle;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.plugins.geofences.GeofenceUtils;
import com.ad4screen.sdk.systems.b;
import com.ad4screen.sdk.systems.d;
import com.ad4screen.sdk.systems.d.b;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class c
  extends com.ad4screen.sdk.common.tasks.c
{
  private final String c = "com.ad4screen.sdk.service.modules.geofencing.GeofencingUpdateTask";
  private final String d = "content";
  private String e;
  private final Context f;
  private Bundle g;
  
  public c(Context paramContext, Bundle paramBundle)
  {
    super(paramContext);
    this.f = paramContext;
    this.g = paramBundle;
  }
  
  protected void a(String paramString)
  {
    Log.debug("GeofencingUpdateTask|Successfully sent Geofences update");
    d.a(this.f).e(d.b.z);
  }
  
  protected void a(Throwable paramThrowable)
  {
    Log.error("GeofencingUpdateTask|Failed to send Geofences update");
  }
  
  protected boolean a()
  {
    h();
    i();
    if (b.a(this.f).f == null)
    {
      Log.warn("GeofencingUpdateTask|No SharedId, not updating geofences");
      return false;
    }
    if (!d.a(this.f).c(d.b.A))
    {
      Log.debug("Service interruption on GeofencingUpdateTask");
      return false;
    }
    try
    {
      JSONObject localJSONObject = new JSONObject();
      if (this.g != null)
      {
        JSONArray localJSONArray = GeofenceUtils.parseGeofences(this.g);
        if (localJSONArray != null) {
          localJSONObject.put("geofences", localJSONArray);
        }
      }
      this.e = localJSONObject.toString();
      return true;
    }
    catch (Exception localException)
    {
      Log.error("GeofencingUpdateTask|Could not build message to send to Ad4Screen", localException);
    }
    return false;
  }
  
  public com.ad4screen.sdk.common.tasks.c b(com.ad4screen.sdk.common.tasks.c paramc)
  {
    Object localObject = (c)paramc;
    try
    {
      paramc = new JSONObject(d());
      localObject = new JSONObject(((c)localObject).d()).getJSONArray("geofences");
      JSONArray localJSONArray = paramc.getJSONArray("geofences");
      int i = 0;
      while (i < ((JSONArray)localObject).length())
      {
        localJSONArray.put(((JSONArray)localObject).get(i));
        i += 1;
      }
      this.e = paramc.toString();
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
    return d.b.z.toString();
  }
  
  public com.ad4screen.sdk.common.tasks.c d(String paramString)
    throws JSONException
  {
    super.d(paramString);
    paramString = new JSONObject(paramString).getJSONObject("com.ad4screen.sdk.service.modules.geofencing.GeofencingUpdateTask");
    if (!paramString.isNull("content")) {
      this.e = paramString.getString("content");
    }
    return this;
  }
  
  protected String d()
  {
    return this.e;
  }
  
  protected String e()
  {
    return d.a(this.f).a(d.b.z);
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.geofencing.GeofencingUpdateTask";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = super.toJSON();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("content", this.e);
    localJSONObject1.put("com.ad4screen.sdk.service.modules.geofencing.GeofencingUpdateTask", localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/geofencing/c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */