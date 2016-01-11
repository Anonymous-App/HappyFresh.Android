package com.ad4screen.sdk.service.modules.inapp.rules;

import android.content.Context;
import com.ad4screen.sdk.service.modules.alarm.model.c;
import com.ad4screen.sdk.service.modules.inapp.h;
import com.ad4screen.sdk.service.modules.inapp.model.daterange.b;
import com.ad4screen.sdk.service.modules.inapp.model.j;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

public abstract class a
  implements g
{
  protected com.ad4screen.sdk.common.a a;
  
  public a(com.ad4screen.sdk.common.a parama)
  {
    this.a = parama;
  }
  
  protected List<com.ad4screen.sdk.service.modules.inapp.model.daterange.a> a(com.ad4screen.sdk.service.modules.inapp.model.i parami)
  {
    return a(parami.b(), parami.c(), parami.n().b());
  }
  
  protected List<com.ad4screen.sdk.service.modules.inapp.model.daterange.a> a(Date paramDate1, Date paramDate2, List<b> paramList)
  {
    ArrayList localArrayList = new ArrayList();
    h localh = new h(this.a);
    if (paramList != null)
    {
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        b localb = (b)localIterator.next();
        com.ad4screen.sdk.service.modules.inapp.model.daterange.d locald = localb.c();
        if (locald != null)
        {
          Object localObject = localb.a();
          Date localDate = localb.b();
          paramList = (List<b>)localObject;
          if (localObject == null) {
            paramList = paramDate1;
          }
          localObject = localDate;
          if (localDate == null) {
            localObject = paramDate2;
          }
          localArrayList.addAll(localh.a(locald, paramList, (Date)localObject, localb.d()));
        }
        else if (localb.d())
        {
          paramList = this.a.d();
          long l = paramList.getTimeZone().getOffset(paramList.getTimeInMillis());
          localArrayList.add(new com.ad4screen.sdk.service.modules.inapp.model.daterange.a(new Date(localb.a().getTime() - l), new Date(localb.b().getTime() - l)));
        }
        else
        {
          localArrayList.add(new com.ad4screen.sdk.service.modules.inapp.model.daterange.a(localb.a(), localb.b()));
        }
      }
    }
    return localArrayList;
  }
  
  public void a(Context paramContext, com.ad4screen.sdk.service.modules.inapp.i parami) {}
  
  public void a(com.ad4screen.sdk.service.modules.inapp.model.i parami, com.ad4screen.sdk.service.modules.inapp.model.g paramg) {}
  
  protected boolean a(com.ad4screen.sdk.model.displayformats.d paramd)
  {
    return paramd instanceof c;
  }
  
  protected List<com.ad4screen.sdk.service.modules.inapp.model.daterange.a> b(com.ad4screen.sdk.service.modules.inapp.model.i parami)
  {
    return a(parami.b(), parami.c(), parami.m().b());
  }
  
  protected boolean c(com.ad4screen.sdk.service.modules.inapp.model.i parami)
  {
    return (parami.n() != null) && (parami.n().b() != null) && (!parami.n().b().isEmpty());
  }
  
  protected boolean d(com.ad4screen.sdk.service.modules.inapp.model.i parami)
  {
    return (parami.m() != null) && (parami.m().b() != null) && (!parami.m().b().isEmpty());
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/rules/a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */