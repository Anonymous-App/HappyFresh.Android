package com.ad4screen.sdk.service.modules.inapp.rules;

import android.content.Context;

public class c
  implements g
{
  public String a()
  {
    return "ClickCappingCheck";
  }
  
  public void a(Context paramContext, com.ad4screen.sdk.service.modules.inapp.i parami) {}
  
  public void a(com.ad4screen.sdk.service.modules.inapp.model.i parami, com.ad4screen.sdk.service.modules.inapp.model.g paramg) {}
  
  public boolean b(com.ad4screen.sdk.service.modules.inapp.model.i parami, com.ad4screen.sdk.service.modules.inapp.model.g paramg)
  {
    return (parami.e() == null) || (parami.e().intValue() > paramg.d());
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/rules/c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */