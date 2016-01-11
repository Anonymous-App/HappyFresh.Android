package com.ad4screen.sdk.service.modules.inapp.model.daterange;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class c
{
  public static Date a(List<a> paramList1, List<a> paramList2, Date paramDate)
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(paramDate);
    localArrayList.addAll(a(paramDate, paramList2));
    localArrayList.addAll(b(paramDate, paramList1));
    return b(a(localArrayList, paramList2), paramList1);
  }
  
  public static List<Date> a(Date paramDate, List<a> paramList)
  {
    ArrayList localArrayList = new ArrayList();
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      Date localDate = ((a)paramList.next()).b();
      if ((localDate != null) && (localDate.after(paramDate))) {
        localArrayList.add(new Date(localDate.getTime() + 1000L));
      }
    }
    return localArrayList;
  }
  
  public static List<Date> a(List<Date> paramList, List<a> paramList1)
  {
    ArrayList localArrayList = new ArrayList();
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      Date localDate = (Date)paramList.next();
      if ((paramList1 == null) || (paramList1.isEmpty()) || (!a(paramList1, localDate))) {
        localArrayList.add(localDate);
      }
    }
    return localArrayList;
  }
  
  public static boolean a(Date paramDate1, Date paramDate2)
  {
    return (paramDate1 != null) && (paramDate2 != null) && (paramDate2.before(paramDate1));
  }
  
  public static boolean a(Date paramDate1, Date paramDate2, Date paramDate3)
  {
    return (paramDate3 != null) && (paramDate1 != null) && (paramDate2 != null) && (paramDate3.after(paramDate1)) && (paramDate3.before(paramDate2));
  }
  
  public static boolean a(List<a> paramList, Date paramDate)
  {
    return a(paramList, paramDate, true);
  }
  
  public static boolean a(List<a> paramList, Date paramDate, boolean paramBoolean)
  {
    if ((paramList == null) || (paramList.isEmpty())) {
      return true;
    }
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      Object localObject = (a)paramList.next();
      Date localDate = ((a)localObject).a();
      localObject = ((a)localObject).b();
      if ((localDate != null) && (localObject != null) && ((a(localDate, (Date)localObject, paramDate)) || ((paramBoolean) && ((localDate.equals(paramDate)) || (((Date)localObject).equals(paramDate)))))) {
        return true;
      }
      if ((localObject == null) && ((b(localDate, paramDate)) || ((paramBoolean) && (paramDate.equals(localDate))))) {
        return true;
      }
      if ((localDate == null) && ((a((Date)localObject, paramDate)) || ((paramBoolean) && (paramDate.equals(localObject))))) {
        return true;
      }
    }
    return false;
  }
  
  public static Date b(List<Date> paramList, List<a> paramList1)
  {
    Collections.sort(paramList);
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      Date localDate = (Date)paramList.next();
      if ((paramList1 == null) || (paramList1.isEmpty()) || (a(paramList1, localDate))) {
        return localDate;
      }
    }
    return null;
  }
  
  private static List<Date> b(Date paramDate, List<a> paramList)
  {
    ArrayList localArrayList = new ArrayList();
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      Date localDate = ((a)paramList.next()).a();
      if ((localDate != null) && (localDate.after(paramDate))) {
        localArrayList.add(localDate);
      }
    }
    return localArrayList;
  }
  
  public static boolean b(Date paramDate1, Date paramDate2)
  {
    return (paramDate1 != null) && (paramDate2 != null) && (paramDate2.after(paramDate1));
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/model/daterange/c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */