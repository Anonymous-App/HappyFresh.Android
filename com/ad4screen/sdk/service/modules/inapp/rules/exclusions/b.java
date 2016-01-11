package com.ad4screen.sdk.service.modules.inapp.rules.exclusions;

import android.content.Context;
import com.ad4screen.sdk.service.modules.inapp.model.events.a;
import com.ad4screen.sdk.service.modules.inapp.model.j;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class b
  implements com.ad4screen.sdk.service.modules.inapp.rules.g
{
  private List<Long> a;
  
  private boolean a(a parama, List<Long> paramList, boolean paramBoolean1, HashMap<Long, Integer> paramHashMap, boolean paramBoolean2)
  {
    ArrayList localArrayList = new ArrayList();
    paramList = paramList.iterator();
    while (paramList.hasNext()) {
      localArrayList.add(new com.ad4screen.sdk.service.modules.inapp.model.events.regular.b((Long)paramList.next(), null));
    }
    paramList = new com.ad4screen.sdk.service.modules.inapp.model.events.b();
    if (paramBoolean1) {
      return paramList.a(parama, localArrayList, paramHashMap, paramBoolean2);
    }
    return paramList.a(parama, localArrayList, null, paramBoolean2);
  }
  
  private boolean a(com.ad4screen.sdk.service.modules.inapp.model.i parami)
  {
    parami = parami.n().e();
    return (parami != null) && (!parami.isEmpty());
  }
  
  private boolean a(com.ad4screen.sdk.service.modules.inapp.model.i parami, com.ad4screen.sdk.service.modules.inapp.model.g paramg, boolean paramBoolean)
  {
    if (a(parami))
    {
      List localList = parami.n().e();
      boolean bool = parami.j();
      parami = localList.iterator();
      while (parami.hasNext()) {
        if (a((a)parami.next(), this.a, bool, paramg.j(), paramBoolean)) {
          return false;
        }
      }
    }
    return true;
  }
  
  public String a()
  {
    return "EventExclusionCheck";
  }
  
  public void a(Context paramContext, com.ad4screen.sdk.service.modules.inapp.i parami)
  {
    this.a = parami.g();
  }
  
  public void a(com.ad4screen.sdk.service.modules.inapp.model.i parami, com.ad4screen.sdk.service.modules.inapp.model.g paramg)
  {
    if (parami.j()) {
      a(parami, paramg, true);
    }
  }
  
  public boolean b(com.ad4screen.sdk.service.modules.inapp.model.i parami, com.ad4screen.sdk.service.modules.inapp.model.g paramg)
  {
    return a(parami, paramg, false);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/rules/exclusions/b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */