package com.ad4screen.sdk.systems;

import android.content.Context;
import com.ad4screen.sdk.common.persistence.b;
import org.json.JSONObject;

public class c
  extends b
{
  public c(Context paramContext)
  {
    super(paramContext, "com.ad4screen.sdk.common.DeviceInfo");
  }
  
  public String a()
  {
    return a("sharedId", null);
  }
  
  public void a(int paramInt)
  {
    a("sessionCount", Integer.valueOf(paramInt));
  }
  
  public void a(boolean paramBoolean)
  {
    a("logging", Boolean.valueOf(paramBoolean));
  }
  
  public boolean a(int paramInt, JSONObject paramJSONObject)
  {
    return false;
  }
  
  public int b()
  {
    return 4;
  }
  
  public void b(int paramInt)
  {
    a("trackingCount", Integer.valueOf(paramInt));
  }
  
  public void b(String paramString)
  {
    a("sharedId", paramString);
  }
  
  public void c(String paramString)
  {
    a("idfv", paramString);
  }
  
  public void d(String paramString)
  {
    a("referrer", paramString);
  }
  
  public String e()
  {
    return a("idfv", null);
  }
  
  public void e(String paramString)
  {
    a("fb", paramString);
  }
  
  public int f()
  {
    return a("sessionCount", 0);
  }
  
  public void f(String paramString)
  {
    a("ua", paramString);
  }
  
  public int g()
  {
    return a("trackingCount", 1);
  }
  
  public void g(String paramString)
  {
    a("source", paramString);
  }
  
  public String h()
  {
    return a("referrer", null);
  }
  
  public void h(String paramString)
  {
    a("sourceTimestamp", paramString);
  }
  
  public String i()
  {
    return a("fb", null);
  }
  
  public void i(String paramString)
  {
    a("nextReloadWebservices", paramString);
  }
  
  public String j()
  {
    return a("ua", null);
  }
  
  public void j(String paramString)
  {
    a("lastReloadWebservices", paramString);
  }
  
  public String k()
  {
    return a("source", null);
  }
  
  public String l()
  {
    return a("sourceTimestamp", null);
  }
  
  public boolean m()
  {
    return a("logging", false);
  }
  
  public String n()
  {
    return a("nextReloadWebservices", null);
  }
  
  public String o()
  {
    return a("lastReloadWebservices", null);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/systems/c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */