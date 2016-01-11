package com.ad4screen.sdk.service.modules.inapp.model.states.composites;

import com.ad4screen.sdk.service.modules.inapp.model.states.a;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class b
  extends c
{
  private final String b = "com.ad4screen.sdk.service.modules.inapp.model.states.composites.AnyState";
  
  public boolean a(Map<String, com.ad4screen.sdk.service.modules.inapp.model.states.c> paramMap)
  {
    Iterator localIterator = this.a.iterator();
    while (localIterator.hasNext()) {
      if (((a)localIterator.next()).a(paramMap)) {
        return true;
      }
    }
    return false;
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.inapp.model.states.composites.AnyState";
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/model/states/composites/b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */