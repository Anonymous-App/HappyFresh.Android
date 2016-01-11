package com.ad4screen.sdk.service.modules.inapp.rules;

import android.content.Context;
import com.ad4screen.sdk.A4SService.a;

public class i
  implements g
{
  private A4SService.a a;
  private com.ad4screen.sdk.common.a b;
  
  public i(com.ad4screen.sdk.common.a parama, A4SService.a parama1)
  {
    this.a = parama1;
    this.b = parama;
  }
  
  public String a()
  {
    return "SessionTimerCheck";
  }
  
  public void a(Context paramContext, com.ad4screen.sdk.service.modules.inapp.i parami) {}
  
  public void a(com.ad4screen.sdk.service.modules.inapp.model.i parami, com.ad4screen.sdk.service.modules.inapp.model.g paramg) {}
  
  public boolean b(com.ad4screen.sdk.service.modules.inapp.model.i parami, com.ad4screen.sdk.service.modules.inapp.model.g paramg)
  {
    parami = parami.l();
    long l = this.b.a();
    if (parami != null)
    {
      if (paramg.g() == 0L)
      {
        paramg.b(l);
        this.a.d().g();
      }
      while (l - paramg.g() < parami.longValue()) {
        return false;
      }
      return true;
    }
    return true;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/rules/i.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */