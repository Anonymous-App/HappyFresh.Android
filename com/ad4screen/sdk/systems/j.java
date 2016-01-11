package com.ad4screen.sdk.systems;

import android.content.Context;
import com.ad4screen.sdk.common.persistence.b;
import com.ad4screen.sdk.service.modules.inapp.i;
import com.ad4screen.sdk.service.modules.tracking.model.c;
import org.json.JSONObject;

public class j
  extends b
{
  public j(Context paramContext)
  {
    super(paramContext, "com.ad4screen.sdk.common.Session.Storage");
  }
  
  public i a()
  {
    return (i)b("InApp.SessionData", new i());
  }
  
  public void a(i parami)
  {
    a("InApp.SessionData", parami);
  }
  
  public void a(c paramc)
  {
    a("com.ad4screen.sdk.service.modules.tracking.eventDispatchs", paramc);
  }
  
  public boolean a(int paramInt, JSONObject paramJSONObject)
  {
    return false;
  }
  
  public int b()
  {
    return 4;
  }
  
  public c e()
  {
    return (c)b("com.ad4screen.sdk.service.modules.tracking.eventDispatchs", new c());
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/systems/j.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */