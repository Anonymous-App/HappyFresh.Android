package com.ad4screen.sdk.common.cache.model;

import android.content.Context;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.persistence.b;
import com.ad4screen.sdk.common.persistence.e;
import com.ad4screen.sdk.common.tasks.c;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class a
  extends b
{
  public a(Context paramContext)
  {
    super(paramContext, "com.ad4screen.sdk.common.cache.RequestManager");
  }
  
  private boolean a(JSONObject paramJSONObject)
  {
    try
    {
      boolean bool = com.ad4screen.sdk.common.persistence.helpers.a.a(paramJSONObject, "savedQueue");
      return bool;
    }
    catch (JSONException paramJSONObject)
    {
      Log.internal("Error during upgrade file from v4 to v5", paramJSONObject);
    }
    return false;
  }
  
  private boolean b(JSONObject paramJSONObject)
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add("com.ad4screen.sdk.service.modules.tracking.ReferrerTrackingTask");
    localArrayList.add("com.ad4screen.sdk.service.modules.location.LocationUpdateTask");
    localArrayList.add("com.ad4screen.sdk.service.modules.geofencing.LoadGeofencingConfigurationTask");
    localArrayList.add("com.ad4screen.sdk.service.modules.authentication.GeofencingUpdateTask");
    localArrayList.add("com.ad4screen.sdk.service.modules.tracking.EventTrackingTask");
    localArrayList.add("com.ad4screen.sdk.service.modules.tracking.EventLeadTrackingTask");
    localArrayList.add("com.ad4screen.sdk.service.modules.tracking.EventCartTrackingTask");
    localArrayList.add("com.ad4screen.sdk.service.modules.tracking.EventPurchaseTrackingTask");
    localArrayList.add("com.ad4screen.sdk.service.modules.profile.UpdateMemberInfoTask");
    localArrayList.add("com.ad4screen.sdk.service.modules.profile.UpdateDeviceInfoTask");
    localArrayList.add("com.ad4screen.sdk.service.modules.common.TrackPushTask");
    localArrayList.add("com.ad4screen.sdk.service.modules.common.TrackInboxTask");
    localArrayList.add("com.ad4screen.sdk.service.modules.common.TrackInAppTask");
    try
    {
      Object localObject = paramJSONObject.getString("savedQueue");
      localObject = (ConcurrentHashMap)new e().a((String)localObject, new ConcurrentHashMap());
      paramJSONObject.remove("savedQueue");
      if ((localObject != null) && (!((ConcurrentHashMap)localObject).isEmpty()))
      {
        Iterator localIterator = ((ConcurrentHashMap)localObject).values().iterator();
        while (localIterator.hasNext())
        {
          JSONObject localJSONObject1 = (JSONObject)localIterator.next();
          try
          {
            if ((localArrayList.contains(localJSONObject1.getString("type"))) && (localJSONObject1.has("com.ad4screen.sdk.common.tasks.URLConnectionTask")))
            {
              JSONObject localJSONObject2 = localJSONObject1.getJSONObject("com.ad4screen.sdk.common.tasks.URLConnectionTask");
              localJSONObject2.put("isSecure", true);
              localJSONObject1.put("com.ad4screen.sdk.common.tasks.URLConnectionTask", localJSONObject2);
            }
          }
          catch (JSONException localJSONException) {}
        }
        paramJSONObject.put("savedQueue", new e().a(localObject));
      }
      return true;
    }
    catch (JSONException paramJSONObject)
    {
      Log.internal("Error during upgrade file from v5 to v6", paramJSONObject);
    }
    return false;
  }
  
  public ConcurrentHashMap<String, JSONObject> a()
  {
    return (ConcurrentHashMap)b("savedQueue", new ConcurrentHashMap());
  }
  
  public void a(ConcurrentHashMap<String, c> paramConcurrentHashMap)
  {
    a("savedQueue", paramConcurrentHashMap);
  }
  
  public boolean a(int paramInt, JSONObject paramJSONObject)
  {
    boolean bool = false;
    switch (paramInt)
    {
    default: 
      return false;
    case 4: 
      bool = false & a(paramJSONObject);
    }
    return bool & b(paramJSONObject);
  }
  
  public int b()
  {
    return 6;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/cache/model/a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */