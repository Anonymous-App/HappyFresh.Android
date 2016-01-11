package com.ad4screen.sdk.service.modules.push;

import android.content.Context;
import android.os.Build.VERSION;
import com.ad4screen.sdk.A4SService.a;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.plugins.ADMPlugin;
import com.ad4screen.sdk.service.modules.push.providers.c;

public class e
{
  public static a a(A4SService.a parama, e parame)
  {
    parame = a(parama.a());
    switch (1.a[parame.ordinal()])
    {
    default: 
      Log.debug("Push|Cannot use GCM with android version < 8");
      return new com.ad4screen.sdk.service.modules.push.providers.b();
    case 1: 
      Log.info("PushFactory|Use ADM");
      return new com.ad4screen.sdk.service.modules.push.providers.a(parama);
    }
    Log.info("PushFactory|Use GCM");
    return new c(parama);
  }
  
  public static f.a a(Context paramContext)
  {
    ADMPlugin localADMPlugin = com.ad4screen.sdk.common.plugins.b.b();
    if ((localADMPlugin != null) && (localADMPlugin.isSupported(paramContext))) {
      return f.a.c;
    }
    if (Build.VERSION.SDK_INT >= 8) {
      return f.a.b;
    }
    return f.a.a;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/push/e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */