package com.ad4screen.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.ad4screen.sdk.common.annotations.API;

@API
public class GCMHandler
  extends BroadcastReceiver
{
  @API
  protected final boolean isA4SPush(Context paramContext, Intent paramIntent)
  {
    if (paramIntent != null) {
      return paramIntent.hasExtra("a4sid");
    }
    return false;
  }
  
  @API
  protected final void onMessage(Context paramContext, Intent paramIntent)
  {
    Log.debug("Push|Received GCM message");
    processPush(paramContext, paramIntent);
  }
  
  @API
  protected void onPushReceive(Context paramContext, Intent paramIntent) {}
  
  public final void onReceive(Context paramContext, Intent paramIntent)
  {
    Log.debug("Push|GCM Registration Receiver received a broadcast");
    String str = paramIntent.getAction();
    if ("com.google.android.c2dm.intent.REGISTRATION".equals(str)) {
      onRegistered(paramContext, paramIntent);
    }
    while (!"com.google.android.c2dm.intent.RECEIVE".equals(str)) {
      return;
    }
    onMessage(paramContext, paramIntent);
  }
  
  @API
  protected final void onRegistered(Context paramContext, Intent paramIntent)
  {
    Log.debug("Push|Received GCM registration status");
    A4S.get(paramContext).updatePushRegistration(paramIntent.getExtras());
    onPushReceive(paramContext, paramIntent);
  }
  
  @API
  protected final void processA4SPush(Context paramContext, Intent paramIntent)
  {
    A4S.get(paramContext).handlePushMessage(paramIntent.getExtras());
  }
  
  @API
  protected void processPush(Context paramContext, Intent paramIntent)
  {
    if (isA4SPush(paramContext, paramIntent))
    {
      processA4SPush(paramContext, paramIntent);
      return;
    }
    onPushReceive(paramContext, paramIntent);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/GCMHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */