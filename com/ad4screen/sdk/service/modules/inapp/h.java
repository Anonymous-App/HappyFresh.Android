package com.ad4screen.sdk.service.modules.inapp;

import com.ad4screen.sdk.service.modules.inapp.model.daterange.d;
import com.ad4screen.sdk.service.modules.inapp.model.daterange.e;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

public class h
{
  private com.ad4screen.sdk.common.a a;
  
  public h(com.ad4screen.sdk.common.a parama)
  {
    this.a = parama;
  }
  
  private Calendar a(Calendar paramCalendar)
  {
    paramCalendar = (Calendar)paramCalendar.clone();
    paramCalendar.setTimeZone(TimeZone.getTimeZone("GMT"));
    paramCalendar.set(11, 0);
    paramCalendar.set(12, 0);
    paramCalendar.set(13, 0);
    paramCalendar.set(14, 0);
    return paramCalendar;
  }
  
  private List<Date> a(d paramd, Calendar paramCalendar1, Calendar paramCalendar2)
  {
    ArrayList localArrayList = new ArrayList();
    List localList = paramd.f();
    Calendar localCalendar = a(paramCalendar1);
    localCalendar.set(11, paramCalendar1.get(11));
    int i = paramd.b();
    while (a(paramCalendar2, localCalendar))
    {
      localArrayList.addAll(a(localList, localCalendar));
      localCalendar.add(11, i);
    }
    return localArrayList;
  }
  
  private List<Date> a(HashMap<e, Integer> paramHashMap, List<Integer> paramList1, List<Integer> paramList2, Calendar paramCalendar)
  {
    ArrayList localArrayList = new ArrayList();
    if (a(paramHashMap))
    {
      e locale = e.a(paramCalendar);
      if (paramHashMap.containsKey(locale))
      {
        paramHashMap = (Integer)paramHashMap.get(locale);
        if ((paramHashMap == null) || (a(paramHashMap.intValue(), paramCalendar))) {
          localArrayList.addAll(a(paramList1, paramList2, paramCalendar));
        }
      }
      return localArrayList;
    }
    localArrayList.addAll(a(paramList1, paramList2, paramCalendar));
    return localArrayList;
  }
  
  private List<Date> a(List<Integer> paramList, Calendar paramCalendar)
  {
    ArrayList localArrayList = new ArrayList();
    if ((paramList != null) && (!paramList.isEmpty())) {
      paramList = paramList.iterator();
    }
    while (paramList.hasNext())
    {
      Integer localInteger = (Integer)paramList.next();
      if (localInteger != null)
      {
        paramCalendar.set(12, localInteger.intValue());
        localArrayList.add(paramCalendar.getTime());
        continue;
        localArrayList.add(paramCalendar.getTime());
      }
    }
    return localArrayList;
  }
  
  private List<Date> a(List<Integer> paramList1, HashMap<e, Integer> paramHashMap, List<Integer> paramList2, List<Integer> paramList3, Calendar paramCalendar)
  {
    ArrayList localArrayList = new ArrayList();
    if ((paramList1 != null) && (!paramList1.isEmpty()))
    {
      if (paramList1.contains(Integer.valueOf(paramCalendar.get(5)))) {
        localArrayList.addAll(a(paramHashMap, paramList2, paramList3, paramCalendar));
      }
      return localArrayList;
    }
    localArrayList.addAll(a(paramHashMap, paramList2, paramList3, paramCalendar));
    return localArrayList;
  }
  
  private List<Date> a(List<Integer> paramList1, List<Integer> paramList2, Calendar paramCalendar)
  {
    ArrayList localArrayList = new ArrayList();
    if ((paramList1 != null) && (!paramList1.isEmpty())) {
      paramList1 = paramList1.iterator();
    }
    while (paramList1.hasNext())
    {
      Integer localInteger = (Integer)paramList1.next();
      if (localInteger != null)
      {
        paramCalendar.set(11, localInteger.intValue());
        localArrayList.addAll(a(paramList2, paramCalendar));
        continue;
        localArrayList.addAll(a(paramList2, paramCalendar));
      }
    }
    return localArrayList;
  }
  
  protected static boolean a(int paramInt, Calendar paramCalendar)
  {
    int i = paramCalendar.get(5);
    int j = paramCalendar.getActualMaximum(5);
    if (paramInt > 0) {
      if ((i <= (paramInt - 1) * 7) || (i > paramInt * 7)) {}
    }
    do
    {
      return true;
      return false;
      if (paramInt >= 0) {
        break;
      }
      i = j - i + 1;
      paramInt = Math.abs(paramInt);
    } while ((i > (paramInt - 1) * 7) && (i <= paramInt * 7));
    return false;
    return false;
  }
  
  private boolean a(Calendar paramCalendar1, Calendar paramCalendar2)
  {
    Calendar localCalendar = (Calendar)this.a.d().clone();
    localCalendar.add(6, 1);
    return (paramCalendar2.before(localCalendar)) || (paramCalendar2.before(paramCalendar1));
  }
  
  private boolean a(HashMap<e, Integer> paramHashMap)
  {
    return (paramHashMap != null) && (!paramHashMap.isEmpty());
  }
  
  private List<Date> b(d paramd, Calendar paramCalendar1, Calendar paramCalendar2)
  {
    ArrayList localArrayList = new ArrayList();
    List localList1 = paramd.e();
    List localList2 = paramd.f();
    paramCalendar1 = a(paramCalendar1);
    int i = paramd.b();
    while (a(paramCalendar2, paramCalendar1))
    {
      localArrayList.addAll(a(localList1, localList2, paramCalendar1));
      paramCalendar1.add(6, i);
    }
    return localArrayList;
  }
  
  private List<Date> c(d paramd, Calendar paramCalendar1, Calendar paramCalendar2)
  {
    ArrayList localArrayList = new ArrayList();
    HashMap localHashMap = paramd.d();
    List localList1 = paramd.e();
    List localList2 = paramd.f();
    paramCalendar1 = a(paramCalendar1);
    int i = paramd.b();
    while (a(paramCalendar2, paramCalendar1))
    {
      localArrayList.addAll(a(localHashMap, localList1, localList2, paramCalendar1));
      if (paramCalendar1.get(7) == 1) {
        paramCalendar1.add(6, (i - 1) * 7 + 1);
      } else {
        paramCalendar1.add(6, 1);
      }
    }
    return localArrayList;
  }
  
  private List<Date> d(d paramd, Calendar paramCalendar1, Calendar paramCalendar2)
  {
    ArrayList localArrayList = new ArrayList();
    List localList1 = paramd.c();
    HashMap localHashMap = paramd.d();
    List localList2 = paramd.e();
    List localList3 = paramd.f();
    paramCalendar1 = a(paramCalendar1);
    int i = paramd.b();
    while (a(paramCalendar2, paramCalendar1))
    {
      localArrayList.addAll(a(localList1, localHashMap, localList2, localList3, paramCalendar1));
      if ((paramCalendar1.get(5) == paramCalendar1.getActualMaximum(5)) && (i > 1))
      {
        paramCalendar1.add(6, 1);
        paramCalendar1.add(2, i - 1);
      }
      else
      {
        paramCalendar1.add(6, 1);
      }
    }
    return localArrayList;
  }
  
  public List<Date> a(d paramd, Date paramDate1, Date paramDate2)
  {
    ArrayList localArrayList = new ArrayList();
    Calendar localCalendar = this.a.d();
    if (paramDate1 != null) {
      localCalendar.setTime(paramDate1);
    }
    paramDate1 = null;
    if (paramDate2 != null)
    {
      paramDate1 = this.a.d();
      paramDate1.setTime(paramDate2);
    }
    switch (1.a[paramd.a().ordinal()])
    {
    case 1: 
    case 2: 
    case 7: 
    default: 
      return localArrayList;
    case 3: 
      localArrayList.addAll(a(paramd, localCalendar, paramDate1));
      return localArrayList;
    case 4: 
      localCalendar.add(6, paramd.b() * -1);
      localArrayList.addAll(b(paramd, localCalendar, paramDate1));
      return localArrayList;
    case 5: 
      localCalendar.add(6, paramd.b() * -7);
      localArrayList.addAll(c(paramd, localCalendar, paramDate1));
      return localArrayList;
    }
    localCalendar.add(2, paramd.b() * -1);
    localArrayList.addAll(d(paramd, localCalendar, paramDate1));
    return localArrayList;
  }
  
  public List<com.ad4screen.sdk.service.modules.inapp.model.daterange.a> a(d paramd, Date paramDate1, Date paramDate2, boolean paramBoolean)
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = a(paramd, paramDate1, paramDate2).iterator();
    while (localIterator.hasNext())
    {
      Date localDate = (Date)localIterator.next();
      if (localDate != null)
      {
        Object localObject = localDate;
        if (paramBoolean)
        {
          localObject = this.a.d();
          long l = ((Calendar)localObject).getTimeZone().getOffset(((Calendar)localObject).getTimeInMillis());
          localObject = new Date(localDate.getTime() - l);
        }
        localObject = new com.ad4screen.sdk.service.modules.inapp.model.daterange.a((Date)localObject, new Date(((Date)localObject).getTime() + paramd.g()));
        if (paramDate1 != null)
        {
          if (((com.ad4screen.sdk.service.modules.inapp.model.daterange.a)localObject).a().before(paramDate1))
          {
            if (!((com.ad4screen.sdk.service.modules.inapp.model.daterange.a)localObject).b().after(paramDate1)) {
              continue;
            }
            ((com.ad4screen.sdk.service.modules.inapp.model.daterange.a)localObject).a(paramDate1);
          }
          if (paramDate2 != null)
          {
            if (((com.ad4screen.sdk.service.modules.inapp.model.daterange.a)localObject).a().after(paramDate2)) {
              continue;
            }
            if (((com.ad4screen.sdk.service.modules.inapp.model.daterange.a)localObject).b().after(paramDate2)) {
              ((com.ad4screen.sdk.service.modules.inapp.model.daterange.a)localObject).b(paramDate2);
            }
          }
        }
        localArrayList.add(localObject);
      }
    }
    return localArrayList;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/h.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */