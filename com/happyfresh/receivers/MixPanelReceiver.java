package com.happyfresh.receivers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.mixpanel.android.mpmetrics.GCMReceiver;
import org.json.JSONException;
import org.json.JSONObject;

public class MixPanelReceiver
  extends GCMReceiver
{
  private String MIX_PANEL_KEY = "mp_message";
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if ("com.google.android.c2dm.intent.REGISTRATION".equals(paramIntent.getAction()))
    {
      super.onReceive(paramContext, paramIntent);
      setResultCode(-1);
      return;
    }
    if (paramIntent.getExtras().containsKey(this.MIX_PANEL_KEY))
    {
      String str = paramIntent.getExtras().getString(this.MIX_PANEL_KEY);
      paramIntent = new Bundle();
      Object localObject = new JSONObject();
      try
      {
        ((JSONObject)localObject).put("alert", str);
        paramIntent.putString("com.parse.Data", ((JSONObject)localObject).toString());
        localObject = new Intent();
        ((Intent)localObject).setAction("com.parse.push.intent.RECEIVE");
        ((Intent)localObject).putExtras(paramIntent);
        paramContext.sendBroadcast((Intent)localObject);
        setResultCode(0);
        return;
      }
      catch (JSONException localJSONException)
      {
        for (;;)
        {
          localJSONException.printStackTrace();
        }
      }
    }
    setResultCode(-1);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/receivers/MixPanelReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */