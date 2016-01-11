package com.ad4screen.sdk.service.modules.inapp.rules.exclusions;

import com.ad4screen.sdk.service.modules.inapp.model.daterange.c;
import com.ad4screen.sdk.service.modules.inapp.model.g;
import com.ad4screen.sdk.service.modules.inapp.model.i;

public class d
  extends com.ad4screen.sdk.service.modules.inapp.rules.a
{
  public d(com.ad4screen.sdk.common.a parama)
  {
    super(parama);
  }
  
  public String a()
  {
    return "InAppDateRangeExclusionCheck";
  }
  
  public boolean b(i parami, g paramg)
  {
    if (a(paramg.a())) {}
    while ((!c(parami)) || (!c.a(a(parami), this.a.c()))) {
      return true;
    }
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/rules/exclusions/d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */