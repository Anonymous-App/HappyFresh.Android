package com.ad4screen.sdk.service.modules.inapp;

import android.content.Context;
import com.ad4screen.sdk.service.modules.inapp.model.e;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class b
  extends com.ad4screen.sdk.common.persistence.b
{
  public b(Context paramContext)
  {
    super(paramContext, "com.ad4screen.sdk.service.modules.inapp.InAppNotification");
  }
  
  public void a(e parame)
  {
    a("configuration", parame);
  }
  
  public void a(boolean paramBoolean)
  {
    a("inAppConfigUpdated", Boolean.valueOf(paramBoolean));
  }
  
  public boolean a()
  {
    return a("inAppConfigUpdated", false);
  }
  
  public boolean a(int paramInt, JSONObject paramJSONObject)
  {
    switch (paramInt)
    {
    default: 
      return false;
    }
    try
    {
      paramJSONObject.getJSONObject("configuration").getJSONObject("com.ad4screen.sdk.service.modules.inapp.model.InAppConfig").put("rules", new JSONArray());
      return true;
    }
    catch (JSONException paramJSONObject) {}
    return false;
  }
  
  public int b()
  {
    return 5;
  }
  
  public e e()
  {
    return (e)b("configuration", new e());
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */