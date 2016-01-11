package com.ad4screen.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.ad4screen.sdk.common.annotations.API;
import com.ad4screen.sdk.common.i;
import com.ad4screen.sdk.systems.b;
import java.util.ArrayList;

@API
public class A4SIDFVHandler
  extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if (paramIntent.getExtras() != null)
    {
      paramIntent = paramIntent.getExtras();
      if (paramIntent.containsKey("action")) {
        A4S.get(paramContext).c(paramIntent.getString("action"));
      }
      if (paramIntent.containsKey("confirmation")) {
        A4S.get(paramContext).b(paramIntent.getString("confirmation"));
      }
    }
    do
    {
      b localb;
      do
      {
        return;
        localb = b.a(paramContext, true);
      } while (localb.c == null);
      Bundle localBundle = getResultExtras(true);
      paramIntent = localBundle.getStringArrayList("anonymId");
      paramContext = paramIntent;
      if (paramIntent == null) {
        paramContext = new ArrayList();
      }
      paramContext.add(localb.c + "|" + i.a(new StringBuilder().append(localb.c).append("y^X*4]6k],:!e%$&n{@[#!|S2[yH#/I1]Qd;^{+'J1rAkp8!%&&)OV0)\"").append("H$#V2{\"O<+v^6k=q}74;1}=6X3-:G~&F!$]f_L6C>@M").toString()));
      localBundle.putStringArrayList("anonymId", paramContext);
      setResultExtras(localBundle);
    } while (paramContext.size() <= 5);
    abortBroadcast();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/A4SIDFVHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */