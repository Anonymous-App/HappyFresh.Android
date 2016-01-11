package com.ad4screen.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class c
  extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    String str1 = com.ad4screen.sdk.common.c.b(paramContext);
    String str2 = com.ad4screen.sdk.common.c.c(paramContext);
    if (paramIntent == null) {
      Log.internal("FacebookReceiver|Trying to retrieve a Facebook Token of a previous opened session");
    }
    if (str1 == null)
    {
      Log.internal("FacebookReceiver|Can't retrieve Facebook App Id");
      return;
    }
    if (str2 == null)
    {
      Log.internal("FacebookReceiver|Can't retrieve Facebook Token");
      return;
    }
    Log.internal("FacebookReceiver|New Facebook session opened, uploading facebook profile");
    A4S.get(paramContext).a(str1, str2, com.ad4screen.sdk.common.c.d(paramContext));
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */