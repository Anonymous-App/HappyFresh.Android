package com.ad4screen.sdk.service.modules.tracking;

import android.content.Context;
import com.ad4screen.sdk.common.persistence.b;
import com.ad4screen.sdk.plugins.model.Beacon;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class a
  extends b
{
  public a(Context paramContext)
  {
    super(paramContext, "com.ad4screen.sdk.Beacons");
  }
  
  public List<JSONObject> a()
  {
    return (List)b("beacons", new ArrayList());
  }
  
  public void a(long paramLong)
  {
    a("startSessionTime", Long.valueOf(paramLong));
  }
  
  public void a(ArrayList<Beacon> paramArrayList)
  {
    a("beacons", paramArrayList);
  }
  
  public void a(List<String> paramList)
  {
    a("sessionSentBeacons", paramList);
  }
  
  public boolean a(int paramInt, JSONObject paramJSONObject)
  {
    return false;
  }
  
  public int b()
  {
    return 1;
  }
  
  public long e()
  {
    return a("startSessionTime", 0L);
  }
  
  public List<String> f()
  {
    return (List)b("sessionSentBeacons", new ArrayList());
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/tracking/a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */