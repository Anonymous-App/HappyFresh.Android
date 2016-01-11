package com.ad4screen.sdk.service.modules.beacons;

import android.content.Context;
import android.os.Bundle;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.tasks.c;
import com.ad4screen.sdk.plugins.beacons.BeaconUtils;
import com.ad4screen.sdk.systems.b;
import com.ad4screen.sdk.systems.d.b;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class d
  extends c
{
  private final String c = "com.ad4screen.sdk.service.modules.beacons.BeaconUpdateTask";
  private final String d = "content";
  private String e;
  private final Context f;
  private Bundle g;
  
  public d(Context paramContext, Bundle paramBundle)
  {
    super(paramContext);
    this.f = paramContext;
    this.g = paramBundle;
  }
  
  protected void a(String paramString)
  {
    Log.debug("BeaconUpdateTask|Successfully sent Beacons update");
    com.ad4screen.sdk.systems.d.a(this.f).e(d.b.C);
  }
  
  protected void a(Throwable paramThrowable)
  {
    Log.error("BeaconUpdateTask|Failed to send Beacons update");
  }
  
  protected boolean a()
  {
    h();
    i();
    if (b.a(this.f).f == null)
    {
      Log.warn("BeaconUpdateTask|No SharedId, not updating beacons");
      return false;
    }
    if (!com.ad4screen.sdk.systems.d.a(this.f).c(d.b.C))
    {
      Log.debug("Service interruption on BeaconUpdateTask");
      return false;
    }
    try
    {
      JSONObject localJSONObject = new JSONObject();
      if (this.g != null)
      {
        JSONArray localJSONArray = BeaconUtils.parseBeacons(this.g);
        if (localJSONArray != null) {
          localJSONObject.put("beacons", localJSONArray);
        }
      }
      this.e = localJSONObject.toString();
      return true;
    }
    catch (Exception localException)
    {
      Log.error("BeaconUpdateTask|Could not build message to send to Ad4Screen", localException);
    }
    return false;
  }
  
  public c b(c paramc)
  {
    Object localObject = (d)paramc;
    try
    {
      paramc = new JSONObject(d());
      localObject = new JSONObject(((d)localObject).d()).getJSONArray("beacons");
      JSONArray localJSONArray = paramc.getJSONArray("beacons");
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
    return d.b.C.toString();
  }
  
  public c d(String paramString)
    throws JSONException
  {
    super.d(paramString);
    paramString = new JSONObject(paramString).getJSONObject("com.ad4screen.sdk.service.modules.beacons.BeaconUpdateTask");
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
    return com.ad4screen.sdk.systems.d.a(this.f).a(d.b.C);
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.beacons.BeaconUpdateTask";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = super.toJSON();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("content", this.e);
    localJSONObject1.put("com.ad4screen.sdk.service.modules.beacons.BeaconUpdateTask", localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/beacons/d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */