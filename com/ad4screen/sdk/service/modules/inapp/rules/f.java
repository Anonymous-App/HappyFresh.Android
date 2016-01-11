package com.ad4screen.sdk.service.modules.inapp.rules;

import android.content.Context;
import com.ad4screen.sdk.common.a;

public class f
  implements g
{
  private long a;
  
  public String a()
  {
    return "DelayCheck";
  }
  
  public void a(Context paramContext, com.ad4screen.sdk.service.modules.inapp.i parami)
  {
    this.a = com.ad4screen.sdk.common.g.e().a();
  }
  
  public void a(com.ad4screen.sdk.service.modules.inapp.model.i parami, com.ad4screen.sdk.service.modules.inapp.model.g paramg)
  {
    paramg.c(com.ad4screen.sdk.common.g.e().a());
  }
  
  public boolean b(com.ad4screen.sdk.service.modules.inapp.model.i parami, com.ad4screen.sdk.service.modules.inapp.model.g paramg)
  {
    return (parami.g() == null) || ((this.a - paramg.h()) / 1000L >= parami.g().longValue());
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/rules/f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */