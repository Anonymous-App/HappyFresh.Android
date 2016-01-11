package com.ad4screen.sdk.systems;

import android.content.Context;
import com.ad4screen.sdk.common.persistence.b;
import org.json.JSONObject;

public class i
  extends b
{
  public i(Context paramContext)
  {
    super(paramContext, "com.ad4screen.sdk.common.Session");
  }
  
  public long a()
  {
    return a("Session.lastDisplayTime", 0L);
  }
  
  public void a(int paramInt)
  {
    a("Session.displayedActivities", Integer.valueOf(paramInt));
  }
  
  public void a(long paramLong)
  {
    a("Session.lastDisplayTime", Long.valueOf(paramLong));
  }
  
  public void a(boolean paramBoolean)
  {
    a("Session.isInterstitialDisplayed", Boolean.valueOf(paramBoolean));
  }
  
  public boolean a(int paramInt, JSONObject paramJSONObject)
  {
    return false;
  }
  
  public int b()
  {
    return 4;
  }
  
  public void b(long paramLong)
  {
    a("Session.lastInAppConfigUpdateTime", Long.valueOf(paramLong));
  }
  
  public int e()
  {
    return a("Session.displayedActivities", 0);
  }
  
  public boolean f()
  {
    return a("Session.isInterstitialDisplayed", false);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/systems/i.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */