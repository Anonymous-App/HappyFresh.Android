package com.ad4screen.sdk.service.modules.push;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.text.TextUtils;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.R.string;
import com.ad4screen.sdk.common.compatibility.k.k;
import com.ad4screen.sdk.model.displayformats.d;
import com.ad4screen.sdk.model.displayformats.e;
import com.ad4screen.sdk.model.displayformats.g.a;
import com.ad4screen.sdk.service.modules.push.model.a;
import com.ad4screen.sdk.systems.h;
import java.net.URISyntaxException;

public final class g
{
  public static Intent a(Context paramContext)
    throws ClassNotFoundException, URISyntaxException
  {
    com.ad4screen.sdk.service.modules.inapp.i locali = com.ad4screen.sdk.service.modules.inapp.i.a(h.a(paramContext));
    if (locali.d() != null)
    {
      Log.debug("Push|Resuming activity : " + locali.d());
      paramContext = new Intent(paramContext, Class.forName(locali.d()));
      paramContext.addFlags(805306368);
      return paramContext;
    }
    paramContext = Intent.parseUri(com.ad4screen.sdk.common.i.c(paramContext), 1);
    paramContext.addFlags(402653184);
    return paramContext;
  }
  
  public static e a(Context paramContext, a parama)
  {
    if (parama.d != null)
    {
      e locale = new e();
      locale.i = parama.b;
      locale.b = parama.c;
      locale.a = new com.ad4screen.sdk.model.displayformats.b();
      locale.a.c = parama.d;
      locale.a.d = "com_ad4screen_sdk_template_interstitial";
      if (parama.e != null) {
        if (paramContext.getResources().getIdentifier(parama.e, "layout", paramContext.getPackageName()) <= 0) {
          break label127;
        }
      }
      label127:
      for (int i = 1; i == 0; i = 0)
      {
        Log.warn("PushUtil|Wrong LandingPage template provided : " + parama.e + " using default");
        return locale;
      }
      locale.a.d = parama.e;
      return locale;
    }
    return null;
  }
  
  public static com.ad4screen.sdk.model.displayformats.g a(Context paramContext, a parama, d paramd)
  {
    com.ad4screen.sdk.model.displayformats.g localg = new com.ad4screen.sdk.model.displayformats.g();
    localg.i = parama.b;
    localg.b = parama.n;
    if ((h.a(paramContext).f()) && (!TextUtils.isEmpty(parama.m))) {
      localg.a = parama.m;
    }
    localg.c = new g.a[2];
    localg.c[0] = new g.a();
    localg.c[0].a = "0";
    if (parama.u == null)
    {
      localg.c[0].b = paramContext.getString(R.string.a4s_popup_dismiss);
      localg.c[1] = new g.a();
      localg.c[1].a = "1";
      if (parama.t != null) {
        break label182;
      }
    }
    label182:
    for (localg.c[1].b = paramContext.getString(R.string.a4s_popup_open);; localg.c[1].b = parama.t)
    {
      localg.c[1].d = paramd;
      return localg;
      localg.c[0].b = parama.u;
      break;
    }
  }
  
  public static void a(Context paramContext, PendingIntent paramPendingIntent, a parama, d.b paramb)
  {
    k.k.a(paramContext, paramPendingIntent, parama, paramb);
  }
  
  public static boolean b(Context paramContext)
  {
    return new b(paramContext).a();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/push/g.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */