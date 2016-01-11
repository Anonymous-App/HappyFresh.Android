package com.activeandroid.serializer;

import java.util.Calendar;

public final class CalendarSerializer
  extends TypeSerializer
{
  public Calendar deserialize(Object paramObject)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(((Long)paramObject).longValue());
    return localCalendar;
  }
  
  public Class<?> getDeserializedType()
  {
    return Calendar.class;
  }
  
  public Class<?> getSerializedType()
  {
    return Long.TYPE;
  }
  
  public Long serialize(Object paramObject)
  {
    return Long.valueOf(((Calendar)paramObject).getTimeInMillis());
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/activeandroid/serializer/CalendarSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */