package org.jcodec;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtil
{
  public static final long MOV_TIME_OFFSET;
  
  static
  {
    Calendar localCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
    localCalendar.set(1904, 0, 1, 0, 0, 0);
    localCalendar.set(14, 0);
    MOV_TIME_OFFSET = localCalendar.getTimeInMillis();
  }
  
  public static long fromMovTime(int paramInt)
  {
    return paramInt * 1000L + MOV_TIME_OFFSET;
  }
  
  public static Date macTimeToDate(int paramInt)
  {
    return new Date(fromMovTime(paramInt));
  }
  
  public static int toMovTime(long paramLong)
  {
    return (int)((paramLong - MOV_TIME_OFFSET) / 1000L);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/org/jcodec/TimeUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */