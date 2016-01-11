package com.ad4screen.sdk.service.modules.inapp.model.states.composites;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class a
  extends c
{
  private final String b = "com.ad4screen.sdk.service.modules.inapp.model.states.composites.AllState";
  
  public boolean a(Map<String, com.ad4screen.sdk.service.modules.inapp.model.states.c> paramMap)
  {
    Iterator localIterator = this.a.iterator();
    while (localIterator.hasNext()) {
      if (!((com.ad4screen.sdk.service.modules.inapp.model.states.a)localIterator.next()).a(paramMap)) {
        return false;
      }
    }
    return true;
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.inapp.model.states.composites.AllState";
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/model/states/composites/a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */