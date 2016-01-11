package com.ad4screen.sdk.plugins.geofences;

import android.os.Bundle;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.common.h.a;
import java.util.Calendar;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class GeofenceUtils
{
  public static JSONArray parseGeofences(Bundle paramBundle)
    throws JSONException
  {
    Object localObject = paramBundle.getBundle("com.ad4screen.sdk.extra.GEOFENCE_PAYLOAD");
    if (localObject != null)
    {
      paramBundle = new JSONArray();
      int j = ((Bundle)localObject).getInt("transition");
      localObject = ((Bundle)localObject).getStringArray("ids");
      int i = 0;
      if (i < localObject.length)
      {
        JSONObject localJSONObject = new JSONObject();
        localJSONObject.put("date", h.a(Calendar.getInstance(Locale.US).getTime(), h.a.b));
        localJSONObject.put("id", localObject[i]);
        if (j == 1) {
          localJSONObject.put("transition", "enter");
        }
        for (;;)
        {
          paramBundle.put(localJSONObject);
          i += 1;
          break;
          localJSONObject.put("transition", "exit");
        }
      }
      return paramBundle;
    }
    return null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/plugins/geofences/GeofenceUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */