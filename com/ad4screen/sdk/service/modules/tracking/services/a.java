package com.ad4screen.sdk.service.modules.tracking.services;

import android.os.Bundle;
import com.ad4screen.sdk.A4SService.a;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.analytics.Cart;
import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.analytics.Purchase;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.common.persistence.e;
import com.ad4screen.sdk.service.modules.authentication.a.b;
import com.ad4screen.sdk.service.modules.authentication.a.c;
import com.ad4screen.sdk.service.modules.tracking.events.d;
import com.ad4screen.sdk.service.modules.tracking.h.a;
import com.ad4screen.sdk.service.modules.tracking.h.b;
import com.ad4screen.sdk.service.modules.tracking.h.e;
import com.ad4screen.sdk.service.modules.tracking.h.g;
import com.ad4screen.sdk.service.modules.tracking.i;
import org.json.JSONException;
import org.json.JSONObject;

public class a
  implements com.ad4screen.sdk.service.modules.tracking.interfaces.a
{
  private A4SService.a a;
  private final a.c b = new a.c()
  {
    public void a() {}
    
    public void a(com.ad4screen.sdk.service.modules.authentication.model.a paramAnonymousa, boolean paramAnonymousBoolean)
    {
      Log.debug("Ad4screen|Authentication succeeded");
      a.a(a.this).b().a(paramAnonymousBoolean);
    }
  };
  private h.g c = new h.g()
  {
    public void a(com.ad4screen.sdk.service.modules.tracking.model.c paramAnonymousc)
    {
      a.a(a.this).b().a(paramAnonymousc);
    }
    
    public void a(String paramAnonymousString)
    {
      new com.ad4screen.sdk.service.modules.tracking.services.facebook.a(a.a(a.this)).c();
    }
  };
  
  public a(A4SService.a parama)
  {
    this.a = parama;
    com.ad4screen.sdk.systems.f.a().a(a.b.class, this.b);
    com.ad4screen.sdk.systems.f.a().a(h.a.class, this.c);
    com.ad4screen.sdk.systems.f.a().a(h.b.class, this.c);
  }
  
  public String a()
  {
    return "Ad4Screen";
  }
  
  public void a(long paramLong, Bundle paramBundle, String... paramVarArgs)
  {
    Log.debug("Ad4Screen|Tracking event #" + paramLong + " : [ '" + h.a("', '", paramVarArgs) + "' ]");
    com.ad4screen.sdk.systems.f.a().a(new h.e(paramLong, paramVarArgs));
    paramBundle = com.ad4screen.sdk.systems.b.a(this.a.a());
    int j = paramVarArgs.length;
    int i = 0;
    while (i < j)
    {
      String str = paramVarArgs[i];
      new d(this.a.a(), paramBundle, Long.valueOf(paramLong), str).run();
      i += 1;
    }
  }
  
  public void a(Cart paramCart, Bundle paramBundle)
  {
    try
    {
      paramBundle = new e().a(paramCart).toString();
      Log.debug("Ad4Screen|Tracking event #30 : [ '" + paramBundle + "' ]");
      com.ad4screen.sdk.systems.f.a().a(new h.e(30L, new String[] { paramBundle }));
      paramBundle = com.ad4screen.sdk.systems.b.a(this.a.a());
      new com.ad4screen.sdk.service.modules.tracking.events.a(this.a.a(), paramBundle, paramCart).run();
      return;
    }
    catch (JSONException paramCart)
    {
      Log.internal("Ad4Screen|Error while serializing Cart to JSON", paramCart);
    }
  }
  
  public void a(Lead paramLead, Bundle paramBundle)
  {
    try
    {
      paramBundle = new e().a(paramLead).toString();
      Log.debug("Ad4Screen|Tracking event #10 : [ '" + paramBundle + "' ]");
      com.ad4screen.sdk.systems.f.a().a(new h.e(10L, new String[] { paramBundle }));
      paramBundle = com.ad4screen.sdk.systems.b.a(this.a.a());
      new com.ad4screen.sdk.service.modules.tracking.events.b(this.a.a(), paramBundle, paramLead).run();
      return;
    }
    catch (JSONException paramLead)
    {
      Log.internal("Ad4Screen|Error while serializing Lead to JSON", paramLead);
    }
  }
  
  public void a(Purchase paramPurchase, Bundle paramBundle)
  {
    try
    {
      paramBundle = new e().a(paramPurchase).toString();
      Log.debug("Ad4Screen|Tracking event #50 : [ '" + paramBundle + "' ]");
      com.ad4screen.sdk.systems.f.a().a(new h.e(50L, new String[] { paramBundle }));
      paramBundle = com.ad4screen.sdk.systems.b.a(this.a.a());
      new com.ad4screen.sdk.service.modules.tracking.events.c(this.a.a(), paramBundle, paramPurchase).run();
      return;
    }
    catch (JSONException paramPurchase)
    {
      Log.internal("Ad4Screen|Error while serializing Purchase to JSON", paramPurchase);
    }
  }
  
  public int b()
  {
    return 1;
  }
  
  public void c()
  {
    new i(this.a.a()).run();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/tracking/services/a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */