package com.ad4screen.sdk.service.modules.inapp.rules;

import android.content.Context;
import com.ad4screen.sdk.service.modules.alarm.model.c;
import java.util.Date;

public class e
  implements g
{
  Date a;
  
  public String a()
  {
    return "DateIntervalCheck";
  }
  
  public void a(Context paramContext, com.ad4screen.sdk.service.modules.inapp.i parami)
  {
    this.a = new Date();
  }
  
  public void a(com.ad4screen.sdk.service.modules.inapp.model.i parami, com.ad4screen.sdk.service.modules.inapp.model.g paramg) {}
  
  public boolean b(com.ad4screen.sdk.service.modules.inapp.model.i parami, com.ad4screen.sdk.service.modules.inapp.model.g paramg)
  {
    if ((paramg.a() instanceof c)) {
      this.a = ((c)paramg.a()).c();
    }
    if ((parami.b() != null) && (parami.b().after(this.a))) {
      return false;
    }
    return (parami.c() == null) || (!parami.c().before(this.a));
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/rules/e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */