package com.ad4screen.sdk.service.modules.tracking;

import android.content.Context;
import android.os.Bundle;
import com.ad4screen.sdk.common.persistence.b;
import org.json.JSONObject;

public class g
  extends b
{
  public g(Context paramContext)
  {
    super(paramContext, "com.ad4screen.sdk.service.modules.tracking.Tracker");
  }
  
  public long a()
  {
    return a("lastBeaconsUpdateTimestamp", 0L);
  }
  
  public void a(long paramLong)
  {
    a("lastUpdateTimestamp", Long.valueOf(paramLong));
  }
  
  public void a(Bundle paramBundle)
  {
    a("userPreferences", paramBundle);
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
    a("lastBeaconsUpdateTimestamp", Long.valueOf(paramLong));
  }
  
  public long e()
  {
    return a("lastUpdateTimestamp", 0L);
  }
  
  public Bundle f()
  {
    return (Bundle)b("userPreferences", new Bundle());
  }
  
  public boolean g()
  {
    return a("stopped", false);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/tracking/g.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */