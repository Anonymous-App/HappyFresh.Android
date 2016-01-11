package com.ad4screen.sdk.common;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class g
  implements a
{
  public static a e()
  {
    return new g();
  }
  
  public long a()
  {
    return Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTimeInMillis();
  }
  
  public long b()
  {
    return System.currentTimeMillis();
  }
  
  public Date c()
  {
    return Calendar.getInstance().getTime();
  }
  
  public Calendar d()
  {
    return Calendar.getInstance();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/g.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */