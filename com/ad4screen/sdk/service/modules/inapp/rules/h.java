package com.ad4screen.sdk.service.modules.inapp.rules;

import android.content.Context;

public class h
  implements g
{
  public String a()
  {
    return "SessionClickCappingCheck";
  }
  
  public void a(Context paramContext, com.ad4screen.sdk.service.modules.inapp.i parami) {}
  
  public void a(com.ad4screen.sdk.service.modules.inapp.model.i parami, com.ad4screen.sdk.service.modules.inapp.model.g paramg) {}
  
  public boolean b(com.ad4screen.sdk.service.modules.inapp.model.i parami, com.ad4screen.sdk.service.modules.inapp.model.g paramg)
  {
    return (parami.f() == null) || (parami.f().intValue() > paramg.e());
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/rules/h.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */