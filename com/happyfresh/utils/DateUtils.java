package com.happyfresh.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils
{
  public static String showUtcInLocal(Date paramDate1, Date paramDate2)
  {
    SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat("MMM dd, hha");
    SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat("hha");
    return String.format("%s - %s", new Object[] { localSimpleDateFormat1.format(paramDate1), localSimpleDateFormat2.format(paramDate2) });
  }
  
  public static String showUtcInLocalForDeliverySlot(Date paramDate1, Date paramDate2, String paramString1, String paramString2)
  {
    SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat("EEEE, MMM dd, hha");
    SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat("hha");
    SimpleDateFormat localSimpleDateFormat3 = new SimpleDateFormat("hha");
    Calendar localCalendar1 = Calendar.getInstance();
    localCalendar1.setTime(paramDate1);
    Calendar localCalendar2 = Calendar.getInstance();
    Calendar localCalendar3 = Calendar.getInstance();
    localCalendar3.add(5, 1);
    if ((localCalendar1.get(1) == localCalendar2.get(1)) && (localCalendar1.get(6) == localCalendar2.get(6))) {
      return String.format("%s %s - %s", new Object[] { paramString1, localSimpleDateFormat2.format(paramDate1), localSimpleDateFormat3.format(paramDate2) });
    }
    if ((localCalendar1.get(1) == localCalendar3.get(1)) && (localCalendar1.get(6) == localCalendar3.get(6))) {
      return String.format("%s %s - %s", new Object[] { paramString2, localSimpleDateFormat2.format(paramDate1), localSimpleDateFormat3.format(paramDate2) });
    }
    return String.format("%s - %s", new Object[] { localSimpleDateFormat1.format(paramDate1), localSimpleDateFormat3.format(paramDate2) });
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/utils/DateUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */