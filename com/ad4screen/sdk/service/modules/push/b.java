package com.ad4screen.sdk.service.modules.push;

import android.content.Context;
import org.json.JSONObject;

public class b
  extends com.ad4screen.sdk.common.persistence.b
{
  public b(Context paramContext)
  {
    super(paramContext, "com.ad4screen.sdk.service.modules.push.PushNotification");
  }
  
  public void a(int paramInt)
  {
    a("appVersion", Integer.valueOf(paramInt));
  }
  
  public void a(boolean paramBoolean)
  {
    a("isEnabled", Boolean.valueOf(paramBoolean));
  }
  
  public boolean a()
  {
    return a("isEnabled", true);
  }
  
  public boolean a(int paramInt, JSONObject paramJSONObject)
  {
    return false;
  }
  
  public int b()
  {
    return 4;
  }
  
  public void b(String paramString)
  {
    a("token", paramString);
  }
  
  public void c(String paramString)
  {
    a("senderID", paramString);
  }
  
  public String e()
  {
    return a("token", null);
  }
  
  public void f()
  {
    a("token");
  }
  
  public String g()
  {
    return a("senderID", null);
  }
  
  public int h()
  {
    return a("appVersion", -1);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/push/b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */