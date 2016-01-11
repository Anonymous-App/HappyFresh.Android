package com.ad4screen.sdk.plugins.beacons;

import android.os.Bundle;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.common.h.a;
import java.sql.Date;
import java.util.Iterator;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class BeaconUtils
{
  public static JSONArray parseBeacons(Bundle paramBundle)
    throws JSONException
  {
    if (paramBundle != null)
    {
      Bundle localBundle1 = paramBundle.getBundle("com.ad4screen.sdk.extra.BEACON_PAYLOAD");
      if (localBundle1 != null)
      {
        JSONArray localJSONArray = new JSONArray();
        Iterator localIterator = localBundle1.keySet().iterator();
        if (localIterator.hasNext())
        {
          paramBundle = (String)localIterator.next();
          JSONObject localJSONObject = new JSONObject();
          Bundle localBundle2 = localBundle1.getBundle(paramBundle);
          localJSONObject.put("id", localBundle2.getString("id"));
          if (localBundle2.getInt("transition") == 1) {}
          for (paramBundle = "enter";; paramBundle = "exit")
          {
            localJSONObject.put("transition", paramBundle);
            localJSONObject.put("uuid", localBundle2.getString("uuid"));
            localJSONObject.put("maj", localBundle2.getInt("maj"));
            localJSONObject.put("min", localBundle2.getInt("min"));
            localJSONObject.put("power", localBundle2.getInt("power"));
            localJSONObject.put("dist", localBundle2.getDouble("dist"));
            localJSONObject.put("acc", localBundle2.getString("acc"));
            localJSONObject.put("rssi", localBundle2.getInt("rssi"));
            localJSONObject.put("date", h.a(new Date(localBundle2.getLong("date")), h.a.b));
            localJSONArray.put(localJSONObject);
            break;
          }
        }
        Log.debug("BeaconUtils|Detected beacons : " + localJSONArray.toString());
        return localJSONArray;
      }
    }
    return null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/plugins/beacons/BeaconUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */