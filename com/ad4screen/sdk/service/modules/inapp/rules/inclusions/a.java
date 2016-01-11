package com.ad4screen.sdk.service.modules.inapp.rules.inclusions;

import android.content.Context;
import com.ad4screen.sdk.service.modules.inapp.model.b;
import com.ad4screen.sdk.service.modules.inapp.model.j;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class a
  implements com.ad4screen.sdk.service.modules.inapp.rules.g
{
  private HashMap<String, com.ad4screen.sdk.service.modules.inapp.model.a> a;
  
  private boolean a(com.ad4screen.sdk.service.modules.inapp.model.i parami)
  {
    parami = parami.m().g();
    return (parami != null) && (!parami.isEmpty());
  }
  
  public String a()
  {
    return "BeaconInclusionCheck";
  }
  
  public void a(Context paramContext, com.ad4screen.sdk.service.modules.inapp.i parami)
  {
    this.a = parami.a();
  }
  
  public void a(com.ad4screen.sdk.service.modules.inapp.model.i parami, com.ad4screen.sdk.service.modules.inapp.model.g paramg) {}
  
  public boolean b(com.ad4screen.sdk.service.modules.inapp.model.i parami, com.ad4screen.sdk.service.modules.inapp.model.g paramg)
  {
    if (!a(parami)) {
      return true;
    }
    parami = parami.m().g().iterator();
    while (parami.hasNext())
    {
      Object localObject = (b)parami.next();
      if ((this.a.containsKey(((b)localObject).a())) && (((com.ad4screen.sdk.service.modules.inapp.model.a)this.a.get(((b)localObject).a())).c.equalsIgnoreCase(((b)localObject).b())))
      {
        if (((b)localObject).b().equalsIgnoreCase("exit")) {
          return true;
        }
        paramg = ((com.ad4screen.sdk.service.modules.inapp.model.a)this.a.get(((b)localObject).a())).b;
        localObject = ((b)localObject).c();
        if ((localObject == null) || (((String)localObject).equalsIgnoreCase("unknown"))) {
          return true;
        }
        if ((((String)localObject).equalsIgnoreCase("far")) && (!paramg.equalsIgnoreCase("unknown"))) {
          return true;
        }
        if ((((String)localObject).equalsIgnoreCase("near")) && ((paramg.equalsIgnoreCase("near")) || (paramg.equalsIgnoreCase("immediate")))) {
          return true;
        }
        if ((((String)localObject).equalsIgnoreCase("immediate")) && (paramg.equalsIgnoreCase("immediate"))) {
          return true;
        }
      }
    }
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/rules/inclusions/a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */