package com.ad4screen.sdk.service.modules.inapp.parsing;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.service.modules.inapp.model.c;
import com.ad4screen.sdk.service.modules.inapp.model.h;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

public class d
{
  public List<c> a(JSONArray paramJSONArray)
    throws JSONException
  {
    if ((paramJSONArray == null) || (paramJSONArray.length() == 0)) {
      return null;
    }
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    for (;;)
    {
      if (i < paramJSONArray.length())
      {
        c localc = new c();
        localc.b(h.b("id", paramJSONArray.getJSONObject(i)));
        String str = h.b("transition", paramJSONArray.getJSONObject(i));
        try
        {
          localc.a(com.ad4screen.sdk.service.modules.inapp.model.d.a(str));
          localArrayList.add(localc);
          i += 1;
        }
        catch (IllegalArgumentException localIllegalArgumentException)
        {
          for (;;)
          {
            Log.internal("InAppRuleGeofenceParser|Impossible to parse Geofence transition : " + str);
          }
        }
      }
    }
    return localArrayList;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/parsing/d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */