package com.ad4screen.sdk.service.modules.inapp.rules;

import android.content.Context;
import com.ad4screen.sdk.service.modules.inapp.model.i.a;

public class d
  implements g
{
  private boolean a;
  private boolean b;
  
  public String a()
  {
    return "ConnectivityCheck";
  }
  
  public void a(Context paramContext, com.ad4screen.sdk.service.modules.inapp.i parami)
  {
    this.a = com.ad4screen.sdk.common.i.g(paramContext);
    this.b = com.ad4screen.sdk.common.i.h(paramContext);
  }
  
  public void a(com.ad4screen.sdk.service.modules.inapp.model.i parami, com.ad4screen.sdk.service.modules.inapp.model.g paramg) {}
  
  public boolean b(com.ad4screen.sdk.service.modules.inapp.model.i parami, com.ad4screen.sdk.service.modules.inapp.model.g paramg)
  {
    if (parami.h() != i.a.a)
    {
      if ((parami.h() == i.a.b) && (!this.a)) {}
      while ((parami.h() == i.a.c) && (!this.b)) {
        return false;
      }
    }
    return true;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/rules/d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */