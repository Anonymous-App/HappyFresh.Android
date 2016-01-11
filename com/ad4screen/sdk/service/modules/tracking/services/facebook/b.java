package com.ad4screen.sdk.service.modules.tracking.services.facebook;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.c;
import com.ad4screen.sdk.common.persistence.e;
import com.ad4screen.sdk.systems.d;
import com.ad4screen.sdk.systems.d.b;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class b
  extends com.ad4screen.sdk.common.tasks.b
{
  private JSONArray c;
  private Bundle d;
  
  public b(Context paramContext, JSONArray paramJSONArray, Bundle paramBundle)
  {
    super(paramContext);
    this.c = paramJSONArray;
    this.d = paramBundle;
  }
  
  public com.ad4screen.sdk.common.tasks.b a(String paramString)
    throws JSONException
  {
    paramString = new JSONObject(paramString);
    if (!paramString.isNull("array")) {
      this.c = paramString.getJSONArray("array");
    }
    if (!paramString.isNull("bundle")) {
      this.d = ((Bundle)new e().a(paramString.getString("bundle"), new Bundle()));
    }
    return this;
  }
  
  public boolean a()
  {
    com.ad4screen.sdk.systems.b localb = com.ad4screen.sdk.systems.b.a(this.b);
    String str2 = localb.E;
    if (str2 == null)
    {
      Log.info("Facebook|No Facebook AppId found, not sending facebook event.");
      return true;
    }
    Object localObject2 = c.a(this.b);
    Object localObject3 = c.a(this.b, c.b(this.b));
    if (!d.a(this.b).c(d.b.E))
    {
      Log.internal("Facebook|Facebook Tracking interrupted. Not sending facebook event");
      return true;
    }
    Object localObject1 = this.d.getString("FBToken");
    if (localObject1 == null) {
      localObject1 = "";
    }
    label565:
    label656:
    for (;;)
    {
      int i;
      try
      {
        Log.info("Facebook|No AccessToken found, not linking event with user.");
        localObject1 = "https://graph.facebook.com/" + str2 + "/activities?" + "format" + "=" + "json" + "&" + "sdk" + "=" + "android" + "&" + "migration_bundle" + "=" + Uri.encode("fbsdk:20131203") + "&" + "application_package_name" + "=" + Uri.encode(localb.i) + "&" + "application_tracking_enabled" + "=1&" + "advertiser_tracking_enabled" + "=1" + "&access_token=" + (String)localObject1 + "&event=CUSTOM_APP_EVENTS";
        if (localObject2 != null)
        {
          localObject1 = (String)localObject1 + "&attribution=" + (String)localObject2;
          localObject3 = new URL((String)localObject1);
          localObject2 = "custom_events=" + Uri.encode(this.c.toString());
          localObject3 = (HttpURLConnection)((URL)localObject3).openConnection();
          ((HttpURLConnection)localObject3).setDoInput(true);
          ((HttpURLConnection)localObject3).setUseCaches(false);
          ((HttpURLConnection)localObject3).setRequestProperty("Content-Type", "application/json");
          ((HttpURLConnection)localObject3).setRequestProperty("User-Agent", "FBAndroidSDK.3.6.0");
          ((HttpURLConnection)localObject3).setDoOutput(true);
          ((HttpURLConnection)localObject3).setRequestMethod("POST");
          Log.internal("Facebook|Sending query to : " + (String)localObject1 + " with content : " + (String)localObject2);
          localObject1 = new BufferedOutputStream(((HttpURLConnection)localObject3).getOutputStream());
          ((BufferedOutputStream)localObject1).write(((String)localObject2).getBytes());
          ((BufferedOutputStream)localObject1).close();
          Log.internal("Facebook|Query sent");
          i = ((HttpURLConnection)localObject3).getResponseCode();
          localObject2 = new BufferedReader(new InputStreamReader(((HttpURLConnection)localObject3).getInputStream()));
          localObject3 = new StringBuilder(2048);
          localObject1 = ((BufferedReader)localObject2).readLine();
          if (localObject1 == null) {
            break label565;
          }
          ((StringBuilder)localObject3).append((String)localObject1);
          localObject1 = ((BufferedReader)localObject2).readLine();
          continue;
          Log.info("Facebook|AccessToken Found, this event is now linked with logged Facebook User");
          Log.internal("Facebook|Token :" + (String)localObject1);
          continue;
        }
        if (localObject3 == null) {
          break label656;
        }
      }
      catch (Exception localException)
      {
        Log.error("Facebook|Error while sending Facebook event", localException);
        return false;
      }
      String str1 = localException + "&advertiser_id=" + (String)localObject3;
      continue;
      if (i == 200)
      {
        Log.debug("Facebook|Send Facebook event success : " + this.c.toString());
        d.a(this.b).e(d.b.E);
        return true;
      }
      Log.error("Facebook|Send Facebook event failed : " + i + "  " + ((StringBuilder)localObject3).toString());
    }
  }
  
  public String getClassKey()
  {
    return "com.facebook.sdk.event";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject = super.toJSON();
    if (this.c != null) {
      localJSONObject.put("array", this.c);
    }
    if (this.d != null) {
      localJSONObject.put("bundle", new e().a(this.d));
    }
    return localJSONObject;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/tracking/services/facebook/b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */