package com.ad4screen.sdk;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class A4SGeofencingService
  extends IntentService
{
  public A4SGeofencingService()
  {
    super("ReceiveTransitionsIntentService");
  }
  
  protected void onHandleIntent(Intent paramIntent)
  {
    Log.debug("Geofence Plugin|Received Geofence Intent");
    Object localObject1 = GeofencingEvent.fromIntent(paramIntent);
    if (((GeofencingEvent)localObject1).hasError())
    {
      i = ((GeofencingEvent)localObject1).getErrorCode();
      Log.error("Geofence Plugin|Location client returned an error : " + i);
    }
    int j;
    do
    {
      return;
      j = ((GeofencingEvent)localObject1).getGeofenceTransition();
      Log.debug("Geofence Plugin|Received Geofence Transition : " + j);
    } while ((j != 1) && (j != 2));
    paramIntent = ((GeofencingEvent)localObject1).getTriggeringGeofences();
    Object localObject2 = new String[paramIntent.size()];
    int i = 0;
    for (;;)
    {
      Bundle localBundle;
      if (i >= paramIntent.size())
      {
        paramIntent = TextUtils.join(",", (Object[])localObject2);
        Log.debug("Geofence Plugin|Triggered geofences : " + paramIntent);
        paramIntent = new Intent();
        paramIntent.addCategory("com.ad4screen.sdk.intent.category.GEOFENCE_NOTIFICATIONS");
        paramIntent.setAction("com.ad4screen.sdk.intent.action.TRIGGER");
        localBundle = new Bundle();
        localBundle.putStringArray("ids", (String[])localObject2);
        localBundle.putInt("transition", j);
        if (((GeofencingEvent)localObject1).getTriggeringLocation() == null) {}
      }
      try
      {
        localObject1 = ((GeofencingEvent)localObject1).getTriggeringLocation();
        localObject2 = new JSONObject();
        ((JSONObject)localObject2).put("latitude", ((Location)localObject1).getLatitude());
        ((JSONObject)localObject2).put("longitude", ((Location)localObject1).getLongitude());
        ((JSONObject)localObject2).put("altitude", ((Location)localObject1).getAltitude());
        ((JSONObject)localObject2).put("accuracy", ((Location)localObject1).getAccuracy());
        ((JSONObject)localObject2).put("bearing", ((Location)localObject1).getBearing());
        ((JSONObject)localObject2).put("provider", ((Location)localObject1).getProvider());
        ((JSONObject)localObject2).put("speed", ((Location)localObject1).getSpeed());
        ((JSONObject)localObject2).put("time", ((Location)localObject1).getTime());
        localBundle.putString("triggeringLocation", ((JSONObject)localObject2).toString());
        paramIntent.putExtra("com.ad4screen.sdk.extra.GEOFENCE_PAYLOAD", localBundle);
        A4S.get(getApplicationContext()).handleGeofencingMessage(paramIntent.getExtras());
        paramIntent.setPackage(getApplicationContext().getPackageName());
        getApplicationContext().sendBroadcast(paramIntent, getApplicationContext().getPackageName() + ".permission.A4S_SEND");
        return;
        localObject2[i] = ((Geofence)paramIntent.get(i)).getRequestId();
        i += 1;
      }
      catch (JSONException localJSONException)
      {
        for (;;)
        {
          Log.error("Geofence Plugin|Error while parsing trigeringLocation", localJSONException);
        }
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/A4SGeofencingService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */