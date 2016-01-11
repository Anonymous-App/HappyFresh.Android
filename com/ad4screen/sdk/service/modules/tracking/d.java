package com.ad4screen.sdk.service.modules.tracking;

import android.content.Context;
import com.ad4screen.sdk.common.persistence.b;
import com.ad4screen.sdk.plugins.model.Geofence;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class d
  extends b
{
  public d(Context paramContext)
  {
    super(paramContext, "com.ad4screen.sdk.Geofences");
  }
  
  public List<JSONObject> a()
  {
    return (List)b("geofences", new ArrayList());
  }
  
  public void a(ArrayList<Geofence> paramArrayList)
  {
    a("geofences", paramArrayList);
  }
  
  public boolean a(int paramInt, JSONObject paramJSONObject)
  {
    return false;
  }
  
  public int b()
  {
    return 4;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/tracking/d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */