package com.google.android.gms.gcm;

import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Base64;

public class GcmReceiver
  extends WakefulBroadcastReceiver
{
  private static String zzavK = "google.com/iid";
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    paramIntent.setComponent(null);
    paramIntent.setPackage(paramContext.getPackageName());
    if (Build.VERSION.SDK_INT <= 18) {
      paramIntent.removeCategory(paramContext.getPackageName());
    }
    if (("com.google.android.c2dm.intent.REGISTRATION".equals(paramIntent.getAction())) || (zzavK.equals(paramIntent.getStringExtra("from")))) {
      paramIntent.setAction("com.google.android.gms.iid.InstanceID");
    }
    String str = paramIntent.getStringExtra("gcm.rawData64");
    if (str != null)
    {
      paramIntent.putExtra("rawData", Base64.decode(str, 0));
      paramIntent.removeExtra("gcm.rawData64");
    }
    if ("com.google.android.c2dm.intent.RECEIVE".equals(paramIntent.getAction())) {
      zzc(paramContext, paramIntent);
    }
    for (;;)
    {
      if (isOrderedBroadcast()) {
        setResultCode(-1);
      }
      return;
      startWakefulService(paramContext, paramIntent);
    }
  }
  
  public void zzc(Context paramContext, Intent paramIntent)
  {
    startWakefulService(paramContext, paramIntent);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/gcm/GcmReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */