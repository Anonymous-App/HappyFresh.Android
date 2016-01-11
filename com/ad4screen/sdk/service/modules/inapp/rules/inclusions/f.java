package com.ad4screen.sdk.service.modules.inapp.rules.inclusions;

import android.content.Context;
import com.ad4screen.sdk.service.modules.inapp.model.j;
import com.ad4screen.sdk.service.modules.inapp.model.states.a;
import com.ad4screen.sdk.service.modules.inapp.model.states.c;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class f
  implements com.ad4screen.sdk.service.modules.inapp.rules.g
{
  HashMap<String, c> a;
  
  private boolean a(HashMap<String, c> paramHashMap, List<a> paramList)
  {
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      a locala = (a)paramList.next();
      if ((locala != null) && (locala.a(paramHashMap))) {
        return true;
      }
    }
    return false;
  }
  
  private boolean a(List<a> paramList)
  {
    return (paramList != null) && (!paramList.isEmpty());
  }
  
  public String a()
  {
    return "StateInclusionCheck";
  }
  
  public void a(Context paramContext, com.ad4screen.sdk.service.modules.inapp.i parami)
  {
    this.a = parami.h();
  }
  
  public void a(com.ad4screen.sdk.service.modules.inapp.model.i parami, com.ad4screen.sdk.service.modules.inapp.model.g paramg) {}
  
  public boolean b(com.ad4screen.sdk.service.modules.inapp.model.i parami, com.ad4screen.sdk.service.modules.inapp.model.g paramg)
  {
    parami = parami.m().d();
    if (!a(parami)) {}
    while (a(this.a, parami)) {
      return true;
    }
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/rules/inclusions/f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */