package com.ad4screen.sdk.service.modules.tracking.services.facebook;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri.Builder;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.e;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.systems.d.b;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

public class c
  extends com.ad4screen.sdk.common.tasks.b
{
  private static final Object c = new Object();
  private static volatile String d = "";
  private static volatile boolean e = false;
  
  public c(Context paramContext)
  {
    super(paramContext);
  }
  
  private static boolean b(String paramString)
  {
    boolean bool2 = false;
    boolean bool1;
    Object localObject2;
    synchronized (c)
    {
      if (paramString.equals(d))
      {
        bool1 = e;
        return bool1;
      }
      localObject2 = new e[3];
      localObject2[0] = new e("sdk", "android");
      localObject2[1] = new e("format", "json");
      localObject2[2] = new e("fields", "supports_attribution");
      Uri.Builder localBuilder = new Uri.Builder().encodedPath("https://graph.facebook.com/" + paramString);
      int j = localObject2.length;
      int i = 0;
      while (i < j)
      {
        Object localObject3 = localObject2[i];
        localBuilder.appendQueryParameter(((e)localObject3).a, ((e)localObject3).b);
        i += 1;
      }
      localObject2 = localBuilder.toString();
      Log.internal("Facebook|Facebook GET " + (String)localObject2);
      localObject2 = com.ad4screen.sdk.common.d.a((String)localObject2);
      Log.internal("Facebook|Facebook attribution support response : " + (String)localObject2);
      bool1 = bool2;
      if (localObject2 == null) {}
    }
    try
    {
      bool1 = new JSONObject((String)localObject2).optBoolean("supports_attribution", false);
      d = paramString;
      e = bool1;
      bool1 = e;
      return bool1;
      paramString = finally;
      throw paramString;
    }
    catch (JSONException localJSONException)
    {
      for (;;)
      {
        Log.error("Facebook|Failed to obtain Facebook app attribution support status");
        bool1 = bool2;
      }
    }
  }
  
  public boolean a()
  {
    Object localObject2 = com.ad4screen.sdk.systems.b.a(this.b);
    if (!com.ad4screen.sdk.systems.d.a(this.b).c(d.b.E))
    {
      Log.internal("Facebook|Facebook Tracking interrupted. Not sending facebook open/installation tracking");
      return true;
    }
    try
    {
      if (((com.ad4screen.sdk.systems.b)localObject2).E == null)
      {
        Log.info("Facebook|No Facebook AppId found, not publishing facebook installation.");
        return true;
      }
      Object localObject3 = com.ad4screen.sdk.common.c.a(this.b);
      Object localObject1 = this.b.getSharedPreferences("com.facebook.sdk.attributionTracking", 0);
      String str = ((com.ad4screen.sdk.systems.b)localObject2).E + "ping";
      if (((SharedPreferences)localObject1).getLong(str, 0L) != 0L)
      {
        Log.debug("Facebook|Facebook installation was already published, skipping...");
        return a.a(this.b);
      }
      Log.info("Facebook|Querying Facebook Attribution for appId : " + ((com.ad4screen.sdk.systems.b)localObject2).E);
      if (!b(((com.ad4screen.sdk.systems.b)localObject2).E))
      {
        Log.info("Facebook|Server reported Facebook Attribution is not supported by appId : " + ((com.ad4screen.sdk.systems.b)localObject2).E);
        return a.a(this.b);
      }
      Log.info("Facebook|Sending app installation attribution");
      Object localObject4 = new ArrayList();
      ((ArrayList)localObject4).add(new e("event", "MOBILE_APP_INSTALL"));
      ((ArrayList)localObject4).add(new e("sdk", "android"));
      ((ArrayList)localObject4).add(new e("format", "json"));
      ((ArrayList)localObject4).add(new e("migration_bundle", "fbsdk:20131203"));
      ((ArrayList)localObject4).add(new e("application_tracking_enabled", "1"));
      ((ArrayList)localObject4).add(new e("auto_publish", "1"));
      ((ArrayList)localObject4).add(new e("application_package_name", ((com.ad4screen.sdk.systems.b)localObject2).i));
      if (localObject3 != null)
      {
        ((ArrayList)localObject4).add(new e("attribution", (String)localObject3));
        Log.info("Facebook|Attribution Id found");
        localObject4 = (e[])((ArrayList)localObject4).toArray(new e[((ArrayList)localObject4).size()]);
        localObject3 = new String[localObject4.length];
        int i = 0;
        while (i < localObject4.length)
        {
          localObject3[i] = (localObject4[i].a + "=" + localObject4[i].b);
          i += 1;
        }
      }
      Log.info("Facebook|No Attribution Id returned from Facebook Application");
      return a.a(this.b);
      localObject2 = String.format("%s/activities", new Object[] { ((com.ad4screen.sdk.systems.b)localObject2).E });
      localObject2 = "https://graph.facebook.com/" + (String)localObject2;
      localObject3 = h.a("&", (String[])localObject3);
      Log.internal("Facebook|Posting app attribution data @ " + (String)localObject2 + ": " + (String)localObject3);
      localObject2 = com.ad4screen.sdk.common.d.a((String)localObject2, ((String)localObject3).getBytes());
      Log.internal("Facebook|Facebook app installation attribution response : " + (String)localObject2);
      if (localObject2 == null)
      {
        Log.info("Facebook|Could not post app attribution, trying again later");
        return false;
      }
      com.ad4screen.sdk.systems.d.a(this.b).e(d.b.E);
      Log.debug("Facebook|Facebook tracking succeeded !");
      a.a(this.b);
      localObject1 = ((SharedPreferences)localObject1).edit();
      ((SharedPreferences.Editor)localObject1).putLong(str, System.currentTimeMillis());
      ((SharedPreferences.Editor)localObject1).commit();
      return true;
    }
    catch (Exception localException)
    {
      Log.error("Facebook|Error while publishing Facebook app attribution", localException);
    }
    return false;
  }
  
  public String getClassKey()
  {
    return "com.facebook.sdk.tracking";
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/tracking/services/facebook/c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */