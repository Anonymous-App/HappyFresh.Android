package com.ad4screen.sdk.service.modules.inapp.model.events;

import android.text.TextUtils;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class b
{
  private boolean a(List<com.ad4screen.sdk.service.modules.inapp.model.events.regular.b> paramList, HashMap<Long, Integer> paramHashMap, boolean paramBoolean, com.ad4screen.sdk.service.modules.inapp.model.events.regular.b paramb, Long paramLong)
  {
    if (paramHashMap != null)
    {
      paramList = Integer.valueOf(Collections.frequency(paramList, paramb));
      paramb = (Integer)paramHashMap.get(paramLong);
      if ((paramb == null) || (paramb.intValue() < paramList.intValue()))
      {
        if (paramBoolean) {
          paramHashMap.put(paramLong, paramList);
        }
        return true;
      }
      return false;
    }
    return true;
  }
  
  public boolean a(a parama, List<com.ad4screen.sdk.service.modules.inapp.model.events.regular.b> paramList, HashMap<Long, Integer> paramHashMap, boolean paramBoolean)
  {
    if ((parama instanceof com.ad4screen.sdk.service.modules.inapp.model.events.regular.b)) {
      return a((com.ad4screen.sdk.service.modules.inapp.model.events.regular.b)parama, paramList, paramHashMap, paramBoolean);
    }
    if ((parama instanceof com.ad4screen.sdk.service.modules.inapp.model.events.regular.c)) {
      return a((com.ad4screen.sdk.service.modules.inapp.model.events.regular.c)parama, paramList, paramHashMap, paramBoolean);
    }
    if ((parama instanceof com.ad4screen.sdk.service.modules.inapp.model.events.composite.b)) {
      return a((com.ad4screen.sdk.service.modules.inapp.model.events.composite.b)parama, paramList, paramHashMap, paramBoolean);
    }
    if ((parama instanceof com.ad4screen.sdk.service.modules.inapp.model.events.composite.c)) {
      return a((com.ad4screen.sdk.service.modules.inapp.model.events.composite.c)parama, paramList, paramHashMap, paramBoolean);
    }
    return false;
  }
  
  protected boolean a(com.ad4screen.sdk.service.modules.inapp.model.events.composite.b paramb, List<com.ad4screen.sdk.service.modules.inapp.model.events.regular.b> paramList, HashMap<Long, Integer> paramHashMap, boolean paramBoolean)
  {
    if ((paramb.c() == null) || (paramb.c().isEmpty()) || (paramList == null)) {
      return false;
    }
    paramb = paramb.c().iterator();
    while (paramb.hasNext()) {
      if (!a((a)paramb.next(), paramList, paramHashMap, paramBoolean)) {
        return false;
      }
    }
    return true;
  }
  
  protected boolean a(com.ad4screen.sdk.service.modules.inapp.model.events.composite.c paramc, List<com.ad4screen.sdk.service.modules.inapp.model.events.regular.b> paramList, HashMap<Long, Integer> paramHashMap, boolean paramBoolean)
  {
    if (paramc.c() != null)
    {
      paramc = paramc.c().iterator();
      while (paramc.hasNext()) {
        if (a((a)paramc.next(), paramList, paramHashMap, paramBoolean)) {
          return true;
        }
      }
    }
    return false;
  }
  
  protected boolean a(com.ad4screen.sdk.service.modules.inapp.model.events.regular.b paramb, List<com.ad4screen.sdk.service.modules.inapp.model.events.regular.b> paramList, HashMap<Long, Integer> paramHashMap, boolean paramBoolean)
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    com.ad4screen.sdk.service.modules.inapp.model.events.regular.b localb;
    Long localLong;
    if (paramList != null)
    {
      Object localObject = paramList.iterator();
      do
      {
        bool1 = bool2;
        if (!((Iterator)localObject).hasNext()) {
          break;
        }
        localb = (com.ad4screen.sdk.service.modules.inapp.model.events.regular.b)((Iterator)localObject).next();
        localLong = localb.a();
      } while ((localLong == null) || (!localLong.equals(paramb.a())));
      if (paramb.b() == null) {
        break label116;
      }
      localObject = localb.b();
      bool1 = bool2;
      if (paramb.b().equals(localObject)) {
        bool1 = a(paramList, paramHashMap, paramBoolean, localb, localLong);
      }
    }
    return bool1;
    label116:
    return a(paramList, paramHashMap, paramBoolean, localb, localLong);
  }
  
  protected boolean a(com.ad4screen.sdk.service.modules.inapp.model.events.regular.c paramc, List<com.ad4screen.sdk.service.modules.inapp.model.events.regular.b> paramList, HashMap<Long, Integer> paramHashMap, boolean paramBoolean)
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    com.ad4screen.sdk.service.modules.inapp.model.events.regular.b localb;
    Long localLong;
    if (paramList != null)
    {
      Object localObject = paramList.iterator();
      do
      {
        bool1 = bool2;
        if (!((Iterator)localObject).hasNext()) {
          break;
        }
        localb = (com.ad4screen.sdk.service.modules.inapp.model.events.regular.b)((Iterator)localObject).next();
        localLong = localb.a();
      } while ((localLong == null) || (!localLong.equals(paramc.a())));
      if (TextUtils.isEmpty(paramc.b())) {
        break label122;
      }
      localObject = localb.b();
      bool1 = bool2;
      if (paramc.c().matcher((CharSequence)localObject).matches()) {
        bool1 = a(paramList, paramHashMap, paramBoolean, localb, localLong);
      }
    }
    return bool1;
    label122:
    return a(paramList, paramHashMap, paramBoolean, localb, localLong);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/model/events/b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */