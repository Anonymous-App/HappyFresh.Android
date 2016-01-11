package com.ad4screen.sdk.common.cache;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import com.ad4screen.sdk.A4S.Callback;
import com.ad4screen.sdk.A4SService;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.compatibility.k.h;
import com.ad4screen.sdk.common.tasks.d.a;
import com.ad4screen.sdk.common.tasks.d.c;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.json.JSONException;
import org.json.JSONObject;

public final class a
{
  private static a u;
  private static com.ad4screen.sdk.common.tasks.a v;
  Context a;
  private int b;
  private boolean c = true;
  private boolean d = false;
  private boolean e = false;
  private boolean f = false;
  private boolean g = false;
  private boolean h = false;
  private int i = -1;
  private int j = -1;
  private HandlerThread k;
  private Handler l;
  private A4S.Callback<Boolean> m;
  private Runnable n = a();
  private com.ad4screen.sdk.common.cache.model.a o;
  private CopyOnWriteArrayList<com.ad4screen.sdk.common.tasks.b> p;
  private HashMap<String, com.ad4screen.sdk.common.tasks.c> q;
  private ConcurrentHashMap<String, com.ad4screen.sdk.common.tasks.c> r;
  private List<com.ad4screen.sdk.systems.d.b> s;
  private List<String> t;
  private d.c w = new d.c()
  {
    public void a(com.ad4screen.sdk.common.tasks.c paramAnonymousc, String paramAnonymousString)
    {
      Log.debug("RequestManager|Request to " + paramAnonymousString + " succeeded");
      if ((paramAnonymousc.l()) && (!paramAnonymousc.m())) {
        a.d(a.this);
      }
      if ((paramAnonymousc instanceof com.ad4screen.sdk.common.tasks.a)) {
        a.d(a.this);
      }
    }
    
    public void b(com.ad4screen.sdk.common.tasks.c paramAnonymousc, String paramAnonymousString)
    {
      int j = 320000;
      a.a(a.this, true);
      Log.debug("RequestManager|Request to " + paramAnonymousString + " failed");
      int i;
      if (paramAnonymousc.l())
      {
        if (paramAnonymousc.j() != 0) {
          break label124;
        }
        i = a.e(a.this);
        if (i <= 320000) {
          break label134;
        }
        i = j;
      }
      label124:
      label134:
      for (;;)
      {
        paramAnonymousc.c(i);
        if (!a.f(a.this)) {
          a.this.a(paramAnonymousc, paramAnonymousString);
        }
        if (!paramAnonymousc.m()) {
          a.d(a.this);
        }
        if ((paramAnonymousc instanceof com.ad4screen.sdk.common.tasks.a)) {
          a.d(a.this);
        }
        return;
        i = paramAnonymousc.j() * 2;
        break;
      }
    }
  };
  
  private a(Context paramContext)
  {
    this.a = paramContext;
    this.r = new ConcurrentHashMap();
    this.p = new CopyOnWriteArrayList();
    this.o = new com.ad4screen.sdk.common.cache.model.a(this.a);
    v = new com.ad4screen.sdk.common.tasks.a(this.a);
    paramContext = new com.ad4screen.sdk.service.modules.profile.b(this.a, new Bundle());
    com.ad4screen.sdk.service.modules.geofencing.c localc = new com.ad4screen.sdk.service.modules.geofencing.c(this.a, new Bundle());
    com.ad4screen.sdk.service.modules.geofencing.d locald = new com.ad4screen.sdk.service.modules.geofencing.d(this.a, 0L);
    com.ad4screen.sdk.service.modules.beacons.e locale = new com.ad4screen.sdk.service.modules.beacons.e(this.a, 0L);
    com.ad4screen.sdk.service.modules.beacons.d locald1 = new com.ad4screen.sdk.service.modules.beacons.d(this.a, new Bundle());
    com.ad4screen.sdk.service.modules.inapp.g localg = new com.ad4screen.sdk.service.modules.inapp.g(this.a, new Bundle(), false);
    com.ad4screen.sdk.service.modules.member.a locala = new com.ad4screen.sdk.service.modules.member.a(null, this.a);
    com.ad4screen.sdk.service.modules.member.f localf = new com.ad4screen.sdk.service.modules.member.f(null, this.a);
    com.ad4screen.sdk.service.modules.location.c localc1 = new com.ad4screen.sdk.service.modules.location.c(this.a, null);
    com.ad4screen.sdk.service.modules.push.f localf1 = new com.ad4screen.sdk.service.modules.push.f(this.a, null, null, false);
    com.ad4screen.sdk.service.modules.tracking.events.d locald2 = new com.ad4screen.sdk.service.modules.tracking.events.d(this.a, com.ad4screen.sdk.systems.b.a(this.a), null, null);
    com.ad4screen.sdk.service.modules.tracking.events.b localb = new com.ad4screen.sdk.service.modules.tracking.events.b(this.a, com.ad4screen.sdk.systems.b.a(this.a), null);
    com.ad4screen.sdk.service.modules.tracking.events.a locala1 = new com.ad4screen.sdk.service.modules.tracking.events.a(this.a, com.ad4screen.sdk.systems.b.a(this.a), null);
    com.ad4screen.sdk.service.modules.tracking.events.c localc2 = new com.ad4screen.sdk.service.modules.tracking.events.c(this.a, com.ad4screen.sdk.systems.b.a(this.a), null);
    com.ad4screen.sdk.service.modules.tracking.c localc3 = new com.ad4screen.sdk.service.modules.tracking.c(this.a, null, null, null);
    com.ad4screen.sdk.service.modules.tracking.i locali = new com.ad4screen.sdk.service.modules.tracking.i(this.a);
    com.ad4screen.sdk.service.modules.common.g localg1 = new com.ad4screen.sdk.service.modules.common.g(this.a, null, new com.ad4screen.sdk.common.e[0]);
    com.ad4screen.sdk.service.modules.common.f localf2 = new com.ad4screen.sdk.service.modules.common.f(this.a, null);
    com.ad4screen.sdk.service.modules.common.d locald3 = new com.ad4screen.sdk.service.modules.common.d(this.a, null, null, null);
    com.ad4screen.sdk.service.modules.common.e locale1 = new com.ad4screen.sdk.service.modules.common.e(this.a, null, null);
    com.ad4screen.sdk.service.modules.tracking.services.facebook.c localc4 = new com.ad4screen.sdk.service.modules.tracking.services.facebook.c(this.a);
    com.ad4screen.sdk.service.modules.tracking.services.facebook.b localb1 = new com.ad4screen.sdk.service.modules.tracking.services.facebook.b(this.a, null, null);
    a(new com.ad4screen.sdk.common.tasks.c[] { paramContext, localc, locald, locale, locald1, localg, locala, localf, localc1, localf1, locald2, localb, locala1, localc2, localc3, locali, localg1, localf2, locald3, locale1 });
    a(new com.ad4screen.sdk.common.tasks.b[] { localc4, localb1 });
    this.k = new HandlerThread("com.ad4screen.sdk.A4SService.network");
    this.k.start();
    this.l = new Handler(this.k.getLooper());
    com.ad4screen.sdk.systems.f.a().a(com.ad4screen.sdk.common.tasks.d.b.class, this.w);
    com.ad4screen.sdk.systems.f.a().a(d.a.class, this.w);
    b();
  }
  
  public static a a(Context paramContext)
  {
    try
    {
      if ((u == null) || (u.l == null) || (u.k == null) || (!u.k.isAlive())) {
        u = new a(paramContext);
      }
      paramContext = u;
      return paramContext;
    }
    finally {}
  }
  
  private void a(com.ad4screen.sdk.common.tasks.b[] paramArrayOfb)
  {
    HashMap localHashMap = (HashMap)this.o.b("externalSavedQueue", new HashMap());
    String str1;
    if (localHashMap.size() > 0)
    {
      Iterator localIterator = localHashMap.keySet().iterator();
      if (localIterator.hasNext()) {
        str1 = (String)localIterator.next();
      }
    }
    for (;;)
    {
      int i1;
      try
      {
        String str2 = ((JSONObject)localHashMap.get(str1)).getString("type");
        i1 = 0;
        if (i1 >= paramArrayOfb.length) {
          break;
        }
        if (!str2.equals(paramArrayOfb[i1].getClassKey())) {
          break label296;
        }
        com.ad4screen.sdk.common.tasks.b localb = (com.ad4screen.sdk.common.tasks.b)new com.ad4screen.sdk.common.persistence.e().a(((JSONObject)localHashMap.get(str1)).toString(), paramArrayOfb[i1]);
        if (com.ad4screen.sdk.common.g.e().a() - localb.a > 432000000L)
        {
          Log.internal("RequestManager|" + str1 + " is too old. External Task will not be restored");
          break label296;
        }
        Log.internal("RequestManager|" + str1 + " external task restored from saved cache");
        a(localb);
      }
      catch (JSONException localJSONException)
      {
        Log.internal("RequestManager|Cannot deserialize " + str1 + " from file", localJSONException);
      }
      break;
      Log.internal("RequestManager|" + this.p.size() + " external task(s) restored from saved cache");
      return;
      label296:
      i1 += 1;
    }
  }
  
  private void a(com.ad4screen.sdk.common.tasks.c[] paramArrayOfc)
  {
    ConcurrentHashMap localConcurrentHashMap = this.o.a();
    String str1;
    if (localConcurrentHashMap.size() > 0)
    {
      Iterator localIterator = localConcurrentHashMap.keySet().iterator();
      if (localIterator.hasNext()) {
        str1 = (String)localIterator.next();
      }
    }
    for (;;)
    {
      int i1;
      try
      {
        String str2 = ((JSONObject)localConcurrentHashMap.get(str1)).getString("type");
        i1 = 0;
        if (i1 >= paramArrayOfc.length) {
          break;
        }
        if (!str2.equals(paramArrayOfc[i1].getClassKey())) {
          break label285;
        }
        com.ad4screen.sdk.common.tasks.c localc = (com.ad4screen.sdk.common.tasks.c)new com.ad4screen.sdk.common.persistence.e().a(((JSONObject)localConcurrentHashMap.get(str1)).toString(), paramArrayOfc[i1]);
        if (com.ad4screen.sdk.common.g.e().a() - localc.b > 432000000L)
        {
          Log.internal("RequestManager|" + str1 + " is too old. Task will not be restored");
          break label285;
        }
        Log.internal("RequestManager|" + str1 + " task restored from saved cache");
        a(localc, str1);
      }
      catch (JSONException localJSONException)
      {
        Log.internal("RequestManager|Cannot deserialize " + str1 + " from file", localJSONException);
      }
      break;
      Log.internal("RequestManager|" + this.r.size() + " task(s) restored from saved cache");
      return;
      label285:
      i1 += 1;
    }
  }
  
  private void h()
  {
    for (;;)
    {
      try
      {
        Handler localHandler = this.l;
        if (localHandler == null) {
          return;
        }
        if (this.n == null) {
          this.n = a();
        }
        this.l.removeCallbacks(this.n);
        Log.internal("RequestManager|Flushing cache...");
        if (((this.q == null) || (this.q.size() == 0)) && ((this.r == null) || (this.r.size() == 0)))
        {
          Log.internal("RequestManager|No requests currently cached");
          Log.debug("RequestManager|Cache will be flushed again in " + j() / 1000 + " secs");
          this.l.postDelayed(this.n, j());
          if (this.m == null) {
            continue;
          }
          this.m.onResult(Boolean.valueOf(this.d));
          continue;
        }
        if (!this.c) {
          break label213;
        }
      }
      finally {}
      if (!this.e)
      {
        Log.internal("RequestManager|Manager is stopped. Flush cancelled");
        if (this.m != null) {
          this.m.onResult(Boolean.valueOf(true));
        }
      }
      else
      {
        label213:
        if ((!this.e) && (!this.c))
        {
          Object localObject2;
          Object localObject3;
          if ((this.i != -1) && (this.i >= this.q.size()))
          {
            localObject2 = new ArrayList(this.p);
            this.p.clear();
            this.o.a("externalSavedQueue", this.p);
            localObject2 = ((ArrayList)localObject2).iterator();
            while (((Iterator)localObject2).hasNext())
            {
              localObject3 = (com.ad4screen.sdk.common.tasks.b)((Iterator)localObject2).next();
              Log.internal("RequestManager|Flushing task : " + localObject3.getClass().getName());
              ((com.ad4screen.sdk.common.tasks.b)localObject3).run();
            }
            this.f = false;
            Log.internal("RequestManager|Cache flush is done");
            this.i = -1;
            this.j = -1;
            this.q.clear();
            Log.debug("RequestManager|Cache will be flushed again in " + j() / 1000 + " secs");
            this.l.postDelayed(this.n, j());
            if (this.m != null) {
              this.m.onResult(Boolean.valueOf(this.d));
            }
            this.h = false;
            if (this.b >= 3)
            {
              this.g = false;
              this.b = 0;
              Log.internal("RequestManager|Too many flush done at once, aborting any new needed immediate flush..");
            }
            else if (this.g)
            {
              this.b += 1;
              this.g = false;
              Log.internal("RequestManager|A new request needs immediate flush. Flushing again..");
              h();
            }
          }
          else
          {
            this.e = true;
            if (this.i == -1)
            {
              this.f = true;
              this.d = false;
              this.i = 0;
              localObject2 = new ConcurrentHashMap(this.r);
              this.q = new HashMap();
              localObject3 = this.r.keySet().iterator();
              while (((Iterator)localObject3).hasNext())
              {
                String str = (String)((Iterator)localObject3).next();
                com.ad4screen.sdk.common.tasks.c localc = (com.ad4screen.sdk.common.tasks.c)this.r.get(str);
                if (localc.o())
                {
                  if (localc.m())
                  {
                    if ((localc.k()) || (this.m != null))
                    {
                      v.a(localc);
                      ((ConcurrentHashMap)localObject2).remove(str);
                    }
                    else if (localc.p() >= localc.q())
                    {
                      Log.debug("RequestManager|" + localc.p() + " of " + localc.q() + " request to " + str + " failed." + " This service will be flushed at next launch");
                    }
                    else
                    {
                      Log.debug("RequestManager|Previous request to " + str + " failed, this service will be flushed later. " + "Delay : " + localc.j() / 1000 + " secs");
                    }
                  }
                  else if ((localc.k()) || (this.m != null))
                  {
                    this.q.put(str, localc);
                    ((ConcurrentHashMap)localObject2).remove(str);
                  }
                  else if (localc.p() >= localc.q())
                  {
                    Log.debug("RequestManager|" + localc.p() + " of " + localc.q() + " request to " + str + " failed." + " This service will be flushed at next launch");
                  }
                  else
                  {
                    Log.debug("RequestManager|Previous request to " + str + " failed, this service will be flushed later. " + "Delay : " + localc.j() / 1000 + " secs");
                  }
                }
                else {
                  Log.debug("RequestManager|No url for " + str + " . Task skipped and will be flushed as soon as a valid url is available");
                }
              }
              this.r = ((ConcurrentHashMap)localObject2);
              this.o.a(this.r);
              this.s = new ArrayList();
              this.t = new ArrayList();
              localObject2 = this.q.keySet().iterator();
              while (((Iterator)localObject2).hasNext())
              {
                localObject3 = (String)((Iterator)localObject2).next();
                try
                {
                  this.s.add(com.ad4screen.sdk.systems.d.b.valueOf((String)localObject3));
                }
                catch (IllegalArgumentException localIllegalArgumentException)
                {
                  this.t.add(localObject3);
                }
              }
              if (!v.b())
              {
                this.s.add(com.ad4screen.sdk.systems.d.b.f);
                this.q.put(com.ad4screen.sdk.systems.d.b.f.toString(), v);
              }
              Collections.sort(this.s);
              if (this.s.size() == 0) {
                this.j = 0;
              }
            }
            localObject2 = null;
            if ((this.i >= this.s.size()) && (this.j < this.t.size()))
            {
              localObject2 = (com.ad4screen.sdk.common.tasks.c)this.q.get(this.t.get(this.j));
              Log.internal("RequestManager|Flushing URL : " + (String)this.t.get(this.j));
            }
            for (;;)
            {
              if (localObject2 == null) {
                break label1362;
              }
              this.l.post((Runnable)localObject2);
              break;
              if (this.i < this.s.size())
              {
                localObject2 = (com.ad4screen.sdk.common.tasks.c)this.q.get(((com.ad4screen.sdk.systems.d.b)this.s.get(this.i)).toString());
                Log.internal("RequestManager|Flushing Service : " + ((com.ad4screen.sdk.systems.d.b)this.s.get(this.i)).toString());
              }
            }
            label1362:
            i();
          }
        }
      }
    }
  }
  
  private void i()
  {
    this.e = false;
    if ((this.q == null) || (this.s == null))
    {
      Log.internal("RequestManager|Can't flush next tasks, is RequestManager stopped?");
      return;
    }
    this.i += 1;
    if (this.i >= this.s.size()) {
      this.j += 1;
    }
    h();
  }
  
  private int j()
  {
    String str = com.ad4screen.sdk.common.i.a(this.a, "com.ad4screen.cache.delay", A4SService.class);
    if ((str != null) && (Integer.valueOf(str).intValue() * 1000 >= 5000) && (Integer.valueOf(str).intValue() * 1000 <= 320000)) {
      return Integer.valueOf(str).intValue() * 1000;
    }
    return 10000;
  }
  
  public Runnable a()
  {
    new Runnable()
    {
      public void run()
      {
        if ((!a.a(a.this)) && (!a.b(a.this))) {
          a.c(a.this);
        }
      }
    };
  }
  
  public void a(A4S.Callback<Boolean> paramCallback)
  {
    Log.internal("RequestManager|App is stopped, flushing all");
    this.m = paramCallback;
    h();
  }
  
  public void a(com.ad4screen.sdk.common.tasks.b paramb)
  {
    Log.internal("RequestManager|Task : " + paramb.getClass().getName() + " will be flushed with cache");
    if (this.p != null)
    {
      this.p.add(paramb);
      this.o.a("externalSavedQueue", this.p);
    }
  }
  
  public void a(com.ad4screen.sdk.common.tasks.c paramc, String paramString)
  {
    if (this.r == null) {
      return;
    }
    if ((paramString == null) || (paramString.length() == 0))
    {
      Log.internal("RequestManager|Can't cache a request with null or empty service");
      return;
    }
    if (this.r.containsKey(paramString))
    {
      Log.internal("RequestManager|Request to " + paramString + " merged and added to queue");
      this.r.put(paramString, b.a((com.ad4screen.sdk.common.tasks.c)this.r.get(paramString), paramc));
      return;
    }
    this.r.put(paramString, paramc);
    this.o.a(this.r);
    Log.internal("RequestManager|Request to " + paramString + " added to queue");
  }
  
  public void a(Runnable paramRunnable)
  {
    Log.internal("RequestManager|Flushing task immediately : " + paramRunnable.getClass().getName());
    if (this.l != null)
    {
      this.l.post(paramRunnable);
      f();
    }
  }
  
  public void b()
  {
    if (!this.c) {}
    do
    {
      return;
      if (this.n == null) {
        a();
      }
      this.c = false;
    } while (this.l == null);
    Log.debug("RequestManager|Cache will be flushed in " + j() / 1000 + " secs");
    this.l.postDelayed(this.n, j());
  }
  
  public void c()
  {
    if (this.c) {
      return;
    }
    if (this.n == null) {
      a();
    }
    this.c = true;
    if (this.l != null) {
      this.l.removeCallbacks(this.n);
    }
    Log.debug("RequestManager|Request Manager is now stopped");
  }
  
  public boolean d()
  {
    return this.c;
  }
  
  public void e()
  {
    com.ad4screen.sdk.systems.f.a().b(com.ad4screen.sdk.common.tasks.d.b.class, this.w);
    com.ad4screen.sdk.systems.f.a().b(d.a.class, this.w);
    this.m = null;
    c();
    k.h.a(this.k);
  }
  
  public void f()
  {
    if (!this.f)
    {
      h();
      return;
    }
    this.g = true;
  }
  
  public void g()
  {
    if (this.f) {
      this.h = true;
    }
    this.r.clear();
    this.o.a(this.r);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/cache/a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */