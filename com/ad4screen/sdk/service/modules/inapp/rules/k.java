package com.ad4screen.sdk.service.modules.inapp.rules;

import android.content.Context;
import com.ad4screen.sdk.A4SService.a;

public class k
  implements g
{
  private com.ad4screen.sdk.common.a a;
  private A4SService.a b;
  
  public k(com.ad4screen.sdk.common.a parama, A4SService.a parama1)
  {
    this.a = parama;
    this.b = parama1;
  }
  
  public String a()
  {
    return "TimerCheck";
  }
  
  public void a(Context paramContext, com.ad4screen.sdk.service.modules.inapp.i parami) {}
  
  public void a(com.ad4screen.sdk.service.modules.inapp.model.i parami, com.ad4screen.sdk.service.modules.inapp.model.g paramg) {}
  
  public boolean b(com.ad4screen.sdk.service.modules.inapp.model.i parami, com.ad4screen.sdk.service.modules.inapp.model.g paramg)
  {
    parami = parami.k();
    long l = this.a.a();
    if (parami != null)
    {
      if (paramg.f() == 0L)
      {
        paramg.a(l);
        this.b.d().g();
      }
      while (l - paramg.f() < parami.longValue()) {
        return false;
      }
      return true;
    }
    return true;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/rules/k.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */