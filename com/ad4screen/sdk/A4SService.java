package com.ad4screen.sdk;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Debug;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import com.ad4screen.sdk.common.annotations.API;
import com.ad4screen.sdk.common.compatibility.k.h;
import com.ad4screen.sdk.service.modules.inapp.rules.j;
import com.ad4screen.sdk.systems.d;
import com.ad4screen.sdk.systems.h;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

@API
public class A4SService
  extends Service
{
  private com.ad4screen.sdk.service.modules.tracking.f a;
  private com.ad4screen.sdk.service.modules.push.a b;
  private com.ad4screen.sdk.service.modules.inapp.a c;
  private com.ad4screen.sdk.service.modules.inbox.b d;
  private com.ad4screen.sdk.service.modules.location.b e;
  private com.ad4screen.sdk.service.modules.beacons.b f;
  private com.ad4screen.sdk.service.modules.common.a g;
  private com.ad4screen.sdk.service.a h;
  private HandlerThread i;
  private Handler j;
  private long k;
  private final Object l = new Object();
  private final Runnable m = new Runnable()
  {
    public void run()
    {
      com.ad4screen.sdk.common.cache.a.a(A4SService.this).a(new A4S.Callback()
      {
        public void a(Boolean paramAnonymous2Boolean)
        {
          if (paramAnonymous2Boolean.booleanValue()) {
            Log.debug("RequestManager|Some requests failed, will retry again at next launch");
          }
          if (A4SService.a(A4SService.this) != null) {
            A4SService.a(A4SService.this).postDelayed(A4SService.b(A4SService.this), 5000L);
          }
        }
        
        public void onError(int paramAnonymous2Int, String paramAnonymous2String)
        {
          Log.debug("RequestManager|Unrecoverable error : " + paramAnonymous2Int + " - " + paramAnonymous2String);
          if (A4SService.a(A4SService.this) != null) {
            A4SService.a(A4SService.this).postDelayed(A4SService.b(A4SService.this), 5000L);
          }
        }
      });
    }
  };
  private final Runnable n = new Runnable()
  {
    public void run()
    {
      if ((h.a(A4SService.this).f()) || (A4SService.c(A4SService.this) > 0L)) {
        return;
      }
      com.ad4screen.sdk.common.cache.a.a(A4SService.this).e();
      if (A4SService.d(A4SService.this) != null) {
        A4SService.d(A4SService.this).b();
      }
      if (A4SService.e(A4SService.this) != null) {
        A4SService.e(A4SService.this).a();
      }
      A4SService.this.stopSelf();
    }
  };
  private final transient a o = new a()
  {
    public Context a()
    {
      return A4SService.this;
    }
    
    public void a(Runnable paramAnonymousRunnable)
    {
      synchronized (A4SService.f(A4SService.this))
      {
        A4SService.i(A4SService.this);
        if (A4SService.a(A4SService.this) != null)
        {
          A4SService.a(A4SService.this).removeCallbacks(A4SService.h(A4SService.this));
          A4SService.a(A4SService.this).removeCallbacks(A4SService.b(A4SService.this));
          A4SService.a(A4SService.this).post(new A4SService.b(A4SService.this, paramAnonymousRunnable));
        }
        return;
      }
    }
    
    public void a(Runnable paramAnonymousRunnable, long paramAnonymousLong)
    {
      synchronized (A4SService.f(A4SService.this))
      {
        A4SService.i(A4SService.this);
        if (A4SService.a(A4SService.this) != null)
        {
          A4SService.a(A4SService.this).removeCallbacks(A4SService.h(A4SService.this));
          A4SService.a(A4SService.this).removeCallbacks(A4SService.b(A4SService.this));
          A4SService.a(A4SService.this).postDelayed(new A4SService.b(A4SService.this, paramAnonymousRunnable), paramAnonymousLong);
        }
        return;
      }
    }
    
    public com.ad4screen.sdk.service.modules.tracking.f b()
    {
      return A4SService.j(A4SService.this);
    }
    
    public com.ad4screen.sdk.service.modules.push.a c()
    {
      return A4SService.k(A4SService.this);
    }
    
    public com.ad4screen.sdk.service.modules.inapp.a d()
    {
      return A4SService.d(A4SService.this);
    }
    
    public com.ad4screen.sdk.service.modules.inbox.b e()
    {
      return A4SService.l(A4SService.this);
    }
    
    public com.ad4screen.sdk.service.modules.common.a f()
    {
      return A4SService.m(A4SService.this);
    }
    
    public com.ad4screen.sdk.service.modules.location.b g()
    {
      return A4SService.n(A4SService.this);
    }
    
    public com.ad4screen.sdk.service.modules.beacons.b h()
    {
      return A4SService.e(A4SService.this);
    }
  };
  
  private void a(Intent paramIntent)
  {
    if (paramIntent == null) {}
    do
    {
      do
      {
        return;
        localObject = paramIntent.getData();
      } while (localObject == null);
      localObject = ((Uri)localObject).getScheme();
    } while ((localObject == null) || (!((String)localObject).contains("a4slocalnotif")));
    Log.debug("Alarm|New alarm has been triggered");
    Object localObject = com.ad4screen.sdk.service.modules.alarm.b.a(this.o);
    paramIntent = paramIntent.getData().getSchemeSpecificPart();
    com.ad4screen.sdk.service.modules.alarm.model.c localc = ((com.ad4screen.sdk.service.modules.alarm.b)localObject).b(paramIntent);
    if (localc == null)
    {
      Log.debug("Alarm|An error occurred when trying to launch alarm #" + paramIntent + ". Is this alarm already cancelled? Aborting display..");
      return;
    }
    com.ad4screen.sdk.service.modules.push.model.a locala = com.ad4screen.sdk.service.modules.push.model.a.a(localc.a());
    if (locala == null)
    {
      Log.debug("Alarm|An error occurred when parsing alarm #" + paramIntent + " push payload. Aborting display..");
      ((com.ad4screen.sdk.service.modules.alarm.b)localObject).c(paramIntent);
      return;
    }
    h localh = h.a(this);
    if ((localh.f()) || (localh.h())) {
      if (localc.e())
      {
        if (a(localc, localh)) {
          ((com.ad4screen.sdk.service.modules.alarm.b)localObject).a(localc);
        }
      }
      else if (!locala.w)
      {
        Log.debug("Alarm|Alarm #" + paramIntent + " is ready to display, but app is in foreground. Alarm will not be displayed...");
        ((com.ad4screen.sdk.service.modules.alarm.b)localObject).c(paramIntent);
        return;
      }
    }
    Log.debug("Alarm|Launching alarm #" + paramIntent);
    this.o.c().handleMessage(localc.a());
    ((com.ad4screen.sdk.service.modules.alarm.b)localObject).c(paramIntent);
  }
  
  private boolean a(com.ad4screen.sdk.service.modules.alarm.model.c paramc, h paramh)
  {
    Log.debug("Alarm|Alarm #" + paramc.i + " is ready to display, but app is in foreground. Shifting alarm..");
    paramc.c().setTime(com.ad4screen.sdk.common.g.e().b() + paramc.d());
    com.ad4screen.sdk.service.modules.inapp.a locala = this.o.d();
    Object localObject = locala.f();
    ArrayList localArrayList = new ArrayList(Arrays.asList(new com.ad4screen.sdk.service.modules.inapp.rules.g[] { new j(com.ad4screen.sdk.common.g.e()), new com.ad4screen.sdk.service.modules.inapp.rules.e(), new com.ad4screen.sdk.service.modules.inapp.rules.inclusions.a(), new com.ad4screen.sdk.service.modules.inapp.rules.exclusions.a(), new com.ad4screen.sdk.service.modules.inapp.rules.inclusions.c(this.o.a(), com.ad4screen.sdk.systems.a.a(this.o.a())), new com.ad4screen.sdk.service.modules.inapp.rules.exclusions.c(this.o.a(), com.ad4screen.sdk.systems.a.a(this.o.a())) }));
    Iterator localIterator = localArrayList.iterator();
    while (localIterator.hasNext()) {
      ((com.ad4screen.sdk.service.modules.inapp.rules.g)localIterator.next()).a(this, com.ad4screen.sdk.service.modules.inapp.i.a(paramh));
    }
    paramh = ((com.ad4screen.sdk.service.modules.inapp.model.e)localObject).a(paramc.i);
    localObject = ((com.ad4screen.sdk.service.modules.inapp.model.e)localObject).a;
    int i2 = localObject.length;
    int i1 = 0;
    if (i1 < i2)
    {
      localIterator = localObject[i1];
      if (!localIterator.a().equals(paramc.i)) {}
      while (locala.a(localArrayList, localIterator, paramh))
      {
        i1 += 1;
        break;
      }
      return false;
    }
    return true;
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    startService(new Intent(this, A4SService.class));
    return this.h;
  }
  
  public void onCreate()
  {
    super.onCreate();
    com.ad4screen.sdk.systems.b localb = com.ad4screen.sdk.systems.b.a(this);
    if (localb.x) {
      Debug.waitForDebugger();
    }
    Log.setEnabled(localb.y);
    Log.debug("---------------------------------------- A4S - START ----------------------------------------");
    this.i = new HandlerThread("com.ad4screen.sdk.A4SService.worker");
    this.i.start();
    this.j = new Handler(this.i.getLooper());
    this.g = new com.ad4screen.sdk.service.modules.common.a();
    if (localb.z) {
      Log.debug("A4SService|Geolocation is disabled in your AndroidManifest.xml. Manually update geolocation with updateGeolocation method if you want geolocated In-App or Push to be displayed.");
    }
    com.ad4screen.sdk.common.cache.a.a(this).b();
    this.a = new com.ad4screen.sdk.service.modules.tracking.f(this.o);
    this.b = com.ad4screen.sdk.service.modules.push.e.a(this.o, new com.ad4screen.sdk.service.modules.push.e());
    this.e = new com.ad4screen.sdk.service.modules.location.b(this.o);
    this.c = new com.ad4screen.sdk.service.modules.inapp.a(this.o);
    this.d = new com.ad4screen.sdk.service.modules.inbox.b(this.o);
    this.f = new com.ad4screen.sdk.service.modules.beacons.b(this.o);
    this.h = new com.ad4screen.sdk.service.a(this.o);
    if (d.a(this).b())
    {
      Log.debug("A4SService|Refreshing webservices URLs");
      com.ad4screen.sdk.common.cache.a.a(this).a(new com.ad4screen.sdk.service.modules.common.c(this));
    }
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    com.ad4screen.sdk.systems.f.a().b();
    k.h.a(this.i);
    this.j = null;
    com.ad4screen.sdk.common.cache.a.a(this).e();
    Log.debug("---------------------------------------- A4S - STOP ----------------------------------------");
  }
  
  public void onStart(Intent paramIntent, int paramInt)
  {
    if (this.j != null)
    {
      this.j.removeCallbacks(this.m);
      this.j.removeCallbacks(this.n);
    }
    a(paramIntent);
  }
  
  @SuppressLint({"InlinedApi"})
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    onStart(paramIntent, paramInt2);
    return 2;
  }
  
  @TargetApi(14)
  public void onTaskRemoved(Intent paramIntent)
  {
    if (this.j != null)
    {
      this.j.removeCallbacks(this.m);
      this.j.removeCallbacks(this.n);
    }
    onDestroy();
    super.onTaskRemoved(paramIntent);
  }
  
  public static abstract interface a
  {
    public abstract Context a();
    
    public abstract void a(Runnable paramRunnable);
    
    public abstract void a(Runnable paramRunnable, long paramLong);
    
    public abstract com.ad4screen.sdk.service.modules.tracking.f b();
    
    public abstract com.ad4screen.sdk.service.modules.push.a c();
    
    public abstract com.ad4screen.sdk.service.modules.inapp.a d();
    
    public abstract com.ad4screen.sdk.service.modules.inbox.b e();
    
    public abstract com.ad4screen.sdk.service.modules.common.a f();
    
    public abstract com.ad4screen.sdk.service.modules.location.b g();
    
    public abstract com.ad4screen.sdk.service.modules.beacons.b h();
  }
  
  private final class b
    implements Runnable
  {
    private final transient Runnable b;
    
    public b(Runnable paramRunnable)
    {
      this.b = paramRunnable;
    }
    
    public void run()
    {
      this.b.run();
      synchronized (A4SService.f(A4SService.this))
      {
        A4SService.g(A4SService.this);
        if ((!com.ad4screen.sdk.systems.b.a(A4SService.this).z) && (!com.ad4screen.sdk.systems.a.a(A4SService.this).a()) && (h.a(A4SService.this).f())) {
          com.ad4screen.sdk.systems.a.a(A4SService.this).b();
        }
        if (A4SService.c(A4SService.this) <= 0L)
        {
          A4SService.a(A4SService.this, 0L);
          if ((!h.a(A4SService.this).f()) && (A4SService.a(A4SService.this) != null))
          {
            Log.debug("A4SService|No more tasks to process and no activity is visible, trying to stop A4SService in 5s");
            if (com.ad4screen.sdk.systems.a.a(A4SService.this).a()) {
              com.ad4screen.sdk.systems.a.a(A4SService.this).c();
            }
            if (A4SService.a(A4SService.this) != null) {
              A4SService.a(A4SService.this).postDelayed(A4SService.h(A4SService.this), 5000L);
            }
          }
        }
        return;
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/A4SService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */