package com.ad4screen.sdk.service.modules.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.ad4screen.sdk.A4SService;
import com.ad4screen.sdk.A4SService.a;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.compatibility.k.a;
import com.ad4screen.sdk.service.modules.alarm.model.c;
import com.ad4screen.sdk.service.modules.common.d.a;
import com.ad4screen.sdk.service.modules.common.h;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class b
{
  private static b a;
  private final A4SService.a b;
  private final a c;
  private final com.ad4screen.sdk.service.modules.alarm.model.a d;
  private final AlarmManager e;
  
  private b(A4SService.a parama)
  {
    this.b = parama;
    this.c = new a(this.b.a());
    this.d = this.c.a();
    this.e = ((AlarmManager)this.b.a().getSystemService("alarm"));
  }
  
  public static b a(A4SService.a parama)
  {
    try
    {
      if (a == null) {
        a = new b(parama);
      }
      return a;
    }
    finally {}
  }
  
  private void b()
  {
    this.c.a(this.d);
  }
  
  private void d(String paramString)
  {
    Object localObject = e(paramString);
    this.e.cancel((PendingIntent)localObject);
    Log.debug("Alarm|Alarm #" + paramString + " cancelled");
    localObject = b(paramString);
    if (localObject == null)
    {
      Log.internal("Alarm|Can't find alarm #" + paramString + " in configuration. Already removed or triggered?");
      return;
    }
    h.a(this.b, paramString, d.a.d);
    this.d.a.remove(localObject);
  }
  
  private PendingIntent e(String paramString)
  {
    Intent localIntent = new Intent(this.b.a(), A4SService.class);
    localIntent.setData(Uri.parse("a4slocalnotif:" + paramString));
    return PendingIntent.getService(this.b.a(), 0, localIntent, 0);
  }
  
  public void a()
  {
    Log.debug("Alarm|Cancelling all alarms");
    ArrayList localArrayList = new ArrayList(this.d.a);
    int i = 0;
    while (i < localArrayList.size())
    {
      d(((c)localArrayList.get(i)).i);
      i += 1;
    }
    b();
  }
  
  public void a(com.ad4screen.sdk.service.modules.alarm.model.b paramb)
  {
    int i = 0;
    while (i < paramb.a.length)
    {
      String str = paramb.a[i];
      if ("*".equals(str))
      {
        a();
        return;
      }
      d(str);
      i += 1;
    }
    b();
  }
  
  public void a(c paramc)
  {
    if (b(paramc.i) == null)
    {
      this.d.a.add(paramc);
      b();
    }
    PendingIntent localPendingIntent = e(paramc.i);
    Log.debug("Alarm|Alarm #" + paramc.i + " set to " + DateFormat.getDateTimeInstance().format(paramc.c()));
    paramc.a(paramc.c());
    k.a.a(this.e, 0, paramc.c().getTime(), localPendingIntent);
    h.a(this.b, paramc.i, d.a.a);
  }
  
  public void a(String paramString)
  {
    d(paramString);
    b();
  }
  
  public c b(String paramString)
  {
    return this.d.b(paramString);
  }
  
  public void c(String paramString)
  {
    this.d.a.remove(this.d.b(paramString));
    b();
    Log.debug("Alarm|Alarm #" + paramString + " deleted");
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/alarm/b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */