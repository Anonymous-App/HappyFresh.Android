package com.ad4screen.sdk.service.modules.inapp.rules;

import com.ad4screen.sdk.service.modules.inapp.model.g;
import com.ad4screen.sdk.service.modules.inapp.model.i;
import java.util.Date;
import java.util.List;

public class j
  extends a
{
  public j(com.ad4screen.sdk.common.a parama)
  {
    super(parama);
  }
  
  private Date a(Date paramDate, long paramLong)
  {
    Date localDate;
    if (paramDate != null)
    {
      localDate = paramDate;
      if (paramDate.getTime() > this.a.b() + paramLong) {}
    }
    else
    {
      localDate = new Date();
      localDate.setTime(this.a.b() + paramLong);
    }
    return localDate;
  }
  
  public String a()
  {
    return "SetAlarmDateRangeCheck";
  }
  
  public boolean b(i parami, g paramg)
  {
    if (!a(paramg.a())) {
      return true;
    }
    paramg = (com.ad4screen.sdk.service.modules.alarm.model.c)paramg.a();
    paramg.b(a(paramg.c(), paramg.d()));
    if ((c(parami)) || (d(parami)))
    {
      List localList = a(parami);
      parami = b(parami);
      if ((com.ad4screen.sdk.service.modules.inapp.model.daterange.c.a(localList, paramg.c())) || (!com.ad4screen.sdk.service.modules.inapp.model.daterange.c.a(parami, paramg.c())))
      {
        parami = com.ad4screen.sdk.service.modules.inapp.model.daterange.c.a(parami, localList, paramg.c());
        if (parami != null)
        {
          paramg.b(parami);
          return true;
        }
        return false;
      }
      return true;
    }
    return true;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/rules/j.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */