package com.ad4screen.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.ad4screen.sdk.common.annotations.API;

@API
public final class ReferrerHandler
  extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if ((paramIntent.getExtras() != null) && (paramIntent.hasExtra("referrer")))
    {
      paramIntent = paramIntent.getStringExtra("referrer");
      Log.debug("Referrer was found : " + paramIntent);
      A4S.get(paramContext).d(paramIntent);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/ReferrerHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */