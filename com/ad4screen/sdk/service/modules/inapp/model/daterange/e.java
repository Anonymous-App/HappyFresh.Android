package com.ad4screen.sdk.service.modules.inapp.model.daterange;

import java.util.Calendar;

public enum e
{
  private e() {}
  
  public static e a(Calendar paramCalendar)
  {
    switch (paramCalendar.get(7))
    {
    default: 
      return a;
    case 2: 
      return b;
    case 3: 
      return c;
    case 4: 
      return d;
    case 5: 
      return e;
    case 6: 
      return f;
    case 7: 
      return g;
    }
    return h;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/model/daterange/e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */