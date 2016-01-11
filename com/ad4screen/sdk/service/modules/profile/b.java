package com.ad4screen.sdk.service.modules.profile;

import android.content.Context;
import android.os.Bundle;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.tasks.c;
import com.ad4screen.sdk.service.modules.tracking.model.a;
import com.ad4screen.sdk.service.modules.tracking.model.a.a;
import com.ad4screen.sdk.systems.d;
import com.ad4screen.sdk.systems.d.b;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class b
  extends c
{
  private final String c = "com.ad4screen.sdk.service.modules.profile.UpdateDeviceInfoTask";
  private final String d = "content";
  private final Context e;
  private Bundle f;
  private String g;
  
  public b(Context paramContext, Bundle paramBundle)
  {
    super(paramContext);
    this.e = paramContext;
    this.f = paramBundle;
  }
  
  protected void a(String paramString)
  {
    Log.debug("UpdateDeviceInfoTask|Profile is successfully updated");
    d.a(this.e).e(d.b.c);
  }
  
  protected void a(Throwable paramThrowable)
  {
    Log.error("UpdateDeviceInfoTask|Profile update failed");
  }
  
  protected boolean a()
  {
    if (com.ad4screen.sdk.systems.b.a(this.e).f == null)
    {
      Log.warn("UpdateDeviceInfoTask|No sharedId, skipping user info update");
      return false;
    }
    if (!d.a(this.e).c(d.b.c))
    {
      Log.debug("Service interruption on UpdateUserPreferencesTask");
      return false;
    }
    h();
    i();
    try
    {
      JSONObject localJSONObject = new JSONObject();
      Iterator localIterator = this.f.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        localJSONObject.put(str, this.f.get(str).toString());
      }
      this.g = localException.toString();
    }
    catch (Exception localException)
    {
      Log.error("UpdateDeviceInfoTask|Could not build message to send to Ad4Screen", localException);
      return false;
    }
    Log.debug("UpdateDeviceInfoTask", localException);
    return true;
  }
  
  protected boolean a(int paramInt, String paramString)
  {
    if ((paramInt == 500) && (paramString != null))
    {
      Log.error("UpdateDeviceInfoTask|Request succeeded but parameters are invalid, server returned :" + paramString);
      try
      {
        Iterator localIterator = new a().a(paramString).a().iterator();
        while (localIterator.hasNext())
        {
          a.a locala = (a.a)localIterator.next();
          if (locala.b().toLowerCase(Locale.US).contains("unknown fields"))
          {
            Log.error("UpdateDeviceInfoTask|Some fields do not exist : " + locala.b());
            return true;
          }
        }
      }
      catch (JSONException localJSONException)
      {
        Log.internal("UpdateDeviceInfoTask|Error Parsing failed : " + localJSONException.getMessage(), localJSONException);
      }
    }
    return super.a(paramInt, paramString);
  }
  
  public c b(c paramc)
  {
    paramc = (b)paramc;
    try
    {
      paramc = new JSONObject(paramc.d());
      JSONObject localJSONObject = new JSONObject(d());
      JSONArray localJSONArray = paramc.names();
      int i = 0;
      while (i < localJSONArray.length())
      {
        String str = localJSONArray.getString(i);
        localJSONObject.put(str, String.valueOf(paramc.get(str)));
        i += 1;
      }
      this.g = localJSONObject.toString();
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
    return d.b.c.toString();
  }
  
  public c d(String paramString)
    throws JSONException
  {
    super.d(paramString);
    paramString = new JSONObject(paramString).getJSONObject("com.ad4screen.sdk.service.modules.profile.UpdateDeviceInfoTask");
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
    return d.a(this.e).a(d.b.c);
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.profile.UpdateDeviceInfoTask";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = super.toJSON();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("content", this.g);
    localJSONObject1.put("com.ad4screen.sdk.service.modules.profile.UpdateDeviceInfoTask", localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/profile/b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */