package com.ad4screen.sdk.service.modules.inapp;

import android.content.Intent;
import android.os.Bundle;
import com.ad4screen.sdk.A4SService.a;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.model.displayformats.g.a;
import com.ad4screen.sdk.service.modules.authentication.a.c;
import com.ad4screen.sdk.service.modules.common.d.a;
import com.ad4screen.sdk.service.modules.inapp.rules.j;
import com.ad4screen.sdk.service.modules.inapp.rules.k;
import com.ad4screen.sdk.systems.a.a;
import com.ad4screen.sdk.systems.h.a;
import com.ad4screen.sdk.systems.h.b;
import com.ad4screen.sdk.systems.h.d;
import com.ad4screen.sdk.systems.h.f;
import com.ad4screen.sdk.systems.h.g;
import com.ad4screen.sdk.systems.h.h;
import com.ad4screen.sdk.systems.h.i;
import com.ad4screen.sdk.systems.h.j;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class a
{
  private final A4SService.a a;
  private final b b;
  private com.ad4screen.sdk.service.modules.inapp.model.e c;
  private i d;
  private f e;
  private com.ad4screen.sdk.service.modules.alarm.b f;
  private List<com.ad4screen.sdk.service.modules.inapp.rules.g> g;
  private List<com.ad4screen.sdk.service.modules.inapp.rules.g> h;
  private com.ad4screen.sdk.service.modules.inapp.rules.g i;
  private boolean j;
  private boolean k;
  private int[] l;
  private ArrayList<String> m = new ArrayList();
  private final c.l n = new c.l()
  {
    public void a(final com.ad4screen.sdk.service.modules.inapp.model.e paramAnonymouse, final boolean paramAnonymousBoolean)
    {
      a.a(a.this).a(new Runnable()
      {
        public void run()
        {
          a.a(a.this, paramAnonymouse);
          if (paramAnonymousBoolean) {
            a.a(a.this, paramAnonymousBoolean);
          }
          if (com.ad4screen.sdk.systems.h.a(a.a(a.this).a()).f()) {
            a.b(a.this).b(500L);
          }
        }
      });
    }
  };
  private final com.ad4screen.sdk.service.modules.tracking.h.c o = new com.ad4screen.sdk.service.modules.tracking.h.c()
  {
    public void a(long paramAnonymousLong, String[] paramAnonymousArrayOfString)
    {
      if ((a.c(a.this) == null) || (a.c(a.this).g() == null) || (a.a(a.this) == null)) {
        return;
      }
      a.c(a.this).g().add(Long.valueOf(paramAnonymousLong));
      a.c(a.this).b(com.ad4screen.sdk.systems.h.a(a.a(a.this).a()));
      a.d(a.this);
    }
  };
  private final c.i p = new c.i()
  {
    public void a()
    {
      try
      {
        a.a(a.this).a(new Runnable()
        {
          public void run()
          {
            Log.debug("InApp|Autocheck rules event raised");
            a.e(a.this);
          }
        });
        return;
      }
      catch (NullPointerException localNullPointerException)
      {
        Log.error("InApp|Autocheck rules failed", localNullPointerException);
      }
    }
  };
  private final c.h q = new c.h()
  {
    public void a(final String paramAnonymousString)
    {
      a.a(a.this).a(new Runnable()
      {
        public void run()
        {
          Log.debug("InApp|Autoclose message was raised, closing inapp #" + paramAnonymousString);
          a.this.a(paramAnonymousString);
        }
      });
    }
  };
  private final com.ad4screen.sdk.systems.a.b r = new com.ad4screen.sdk.systems.a.b()
  {
    public void a()
    {
      a.d(a.this);
    }
  };
  private final a.c s = new a.c()
  {
    public void a() {}
    
    public void a(com.ad4screen.sdk.service.modules.authentication.model.a paramAnonymousa, boolean paramAnonymousBoolean)
    {
      Log.debug("InApp|Received sharedId");
      a.b(a.this, paramAnonymousBoolean);
    }
  };
  private final com.ad4screen.sdk.systems.h.e t = new com.ad4screen.sdk.systems.h.e()
  {
    public void a(String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3)
    {
      a.c(a.this).b(paramAnonymousString1);
      a.c(a.this).c(paramAnonymousString2);
      Log.debug("InApp|View is now set to : " + paramAnonymousString2);
      a.c(a.this).d(paramAnonymousString3);
      a.c(a.this).b(com.ad4screen.sdk.systems.h.a(a.a(a.this).a()));
      a.f(a.this).clear();
    }
  };
  private final h.f u = new h.f()
  {
    public void a()
    {
      if (a.c(a.this).c().size() > 0)
      {
        int i = 0;
        while (i < a.c(a.this).c().size())
        {
          a.this.b((String)a.c(a.this).c().get(i));
          i += 1;
        }
      }
    }
  };
  private final h.h v = new h.h()
  {
    public void a()
    {
      a.b(a.this).b(500L);
    }
  };
  private final h.g w = new h.g()
  {
    public void a()
    {
      a.b(a.this).c();
    }
  };
  private final h.i x = new h.i()
  {
    public void a()
    {
      a.b(a.this, true);
    }
  };
  
  public a(A4SService.a parama)
  {
    this.a = parama;
    this.b = new b(this.a.a());
    this.j = this.b.a();
    this.c = this.b.e();
    this.d = i.a(com.ad4screen.sdk.systems.h.a(this.a.a()));
    com.ad4screen.sdk.systems.f.a().a(com.ad4screen.sdk.service.modules.tracking.h.e.class, this.o);
    com.ad4screen.sdk.systems.f.a().a(c.b.class, this.p);
    com.ad4screen.sdk.systems.f.a().a(c.a.class, this.q);
    com.ad4screen.sdk.systems.f.a().a(a.a.class, this.r);
    com.ad4screen.sdk.systems.f.a().a(h.a.class, this.t);
    com.ad4screen.sdk.systems.f.a().a(h.b.class, this.u);
    com.ad4screen.sdk.systems.f.a().a(com.ad4screen.sdk.systems.h.c.class, this.w);
    com.ad4screen.sdk.systems.f.a().a(h.d.class, this.v);
    com.ad4screen.sdk.systems.f.a().a(com.ad4screen.sdk.service.modules.authentication.a.b.class, this.s);
    com.ad4screen.sdk.systems.f.a().a(h.j.class, this.x);
    com.ad4screen.sdk.systems.f.a().a(c.e.class, this.n);
    this.g = new ArrayList(Arrays.asList(new com.ad4screen.sdk.service.modules.inapp.rules.g[] { new com.ad4screen.sdk.service.modules.inapp.rules.b(), new com.ad4screen.sdk.service.modules.inapp.rules.c(), new com.ad4screen.sdk.service.modules.inapp.rules.d(), new com.ad4screen.sdk.service.modules.inapp.rules.h(), new com.ad4screen.sdk.service.modules.inapp.rules.f(), new com.ad4screen.sdk.service.modules.inapp.rules.inclusions.e(com.ad4screen.sdk.systems.a.a(this.a.a())), new com.ad4screen.sdk.service.modules.inapp.rules.exclusions.e(com.ad4screen.sdk.systems.a.a(this.a.a())), new com.ad4screen.sdk.service.modules.inapp.rules.inclusions.g(), new com.ad4screen.sdk.service.modules.inapp.rules.exclusions.g(), new com.ad4screen.sdk.service.modules.inapp.rules.inclusions.f(), new com.ad4screen.sdk.service.modules.inapp.rules.exclusions.f(), new com.ad4screen.sdk.service.modules.inapp.rules.inclusions.b(), new com.ad4screen.sdk.service.modules.inapp.rules.exclusions.b(), new com.ad4screen.sdk.service.modules.inapp.rules.inclusions.c(this.a.a(), com.ad4screen.sdk.systems.a.a(this.a.a())), new com.ad4screen.sdk.service.modules.inapp.rules.exclusions.c(this.a.a(), com.ad4screen.sdk.systems.a.a(this.a.a())), new com.ad4screen.sdk.service.modules.inapp.rules.inclusions.a(), new com.ad4screen.sdk.service.modules.inapp.rules.exclusions.a(), new com.ad4screen.sdk.service.modules.inapp.rules.inclusions.d(com.ad4screen.sdk.common.g.e()), new com.ad4screen.sdk.service.modules.inapp.rules.exclusions.d(com.ad4screen.sdk.common.g.e()), new j(com.ad4screen.sdk.common.g.e()), new com.ad4screen.sdk.service.modules.inapp.rules.e(), new com.ad4screen.sdk.service.modules.inapp.rules.i(com.ad4screen.sdk.common.g.e(), this.a), new k(com.ad4screen.sdk.common.g.e(), this.a) }));
    this.h = new ArrayList(Arrays.asList(new com.ad4screen.sdk.service.modules.inapp.rules.g[] { new com.ad4screen.sdk.service.modules.inapp.rules.exclusions.e(com.ad4screen.sdk.systems.a.a(this.a.a())), new com.ad4screen.sdk.service.modules.inapp.rules.exclusions.g(), new com.ad4screen.sdk.service.modules.inapp.rules.exclusions.f(), new com.ad4screen.sdk.service.modules.inapp.rules.exclusions.b(), new com.ad4screen.sdk.service.modules.inapp.rules.exclusions.c(this.a.a(), com.ad4screen.sdk.systems.a.a(this.a.a())), new com.ad4screen.sdk.service.modules.inapp.rules.exclusions.a(), new j(com.ad4screen.sdk.common.g.e()) }));
    this.f = com.ad4screen.sdk.service.modules.alarm.b.a(this.a);
    this.e = new f();
    a();
  }
  
  private void a(com.ad4screen.sdk.model.displayformats.d paramd)
  {
    if (!(paramd instanceof com.ad4screen.sdk.model.displayformats.e))
    {
      paramd = this.c.a(paramd.i);
      paramd.c(com.ad4screen.sdk.common.g.e().a());
      paramd.a(paramd.b() + 1);
      paramd.b(paramd.c() + 1);
      e.a(this.c);
      g();
    }
  }
  
  private void a(com.ad4screen.sdk.model.displayformats.d paramd, String paramString)
  {
    a(paramd, paramString, null);
  }
  
  private void a(com.ad4screen.sdk.model.displayformats.d paramd, String paramString1, String paramString2)
  {
    if ((paramString1 == null) || (paramd == null)) {
      return;
    }
    Intent localIntent = new Intent(paramString1);
    localIntent.addCategory("com.ad4screen.sdk.intent.category.INAPP_NOTIFICATIONS");
    if ((paramd instanceof com.ad4screen.sdk.model.displayformats.a))
    {
      paramString2 = (com.ad4screen.sdk.model.displayformats.a)paramd;
      if ("com.ad4screen.sdk.intent.action.DISPLAYED".equals(paramString1)) {
        e.a(localIntent, paramString2.e);
      }
      if ("com.ad4screen.sdk.intent.action.CLICKED".equals(paramString1)) {
        e.a(localIntent, paramString2.f);
      }
    }
    for (;;)
    {
      a(paramd.i, paramString1, localIntent);
      com.ad4screen.sdk.common.i.a(this.a.a(), localIntent);
      return;
      Object localObject1;
      if ((paramd instanceof com.ad4screen.sdk.model.displayformats.g))
      {
        localObject1 = (com.ad4screen.sdk.model.displayformats.g)paramd;
        if ("com.ad4screen.sdk.intent.action.DISPLAYED".equals(paramString1)) {
          e.a(localIntent, ((com.ad4screen.sdk.model.displayformats.g)localObject1).d);
        }
        if (("com.ad4screen.sdk.intent.action.CLICKED".equals(paramString1)) && (paramString2 != null))
        {
          localObject1 = ((com.ad4screen.sdk.model.displayformats.g)localObject1).c;
          int i2 = localObject1.length;
          int i1 = 0;
          for (;;)
          {
            if (i1 >= i2) {
              break label212;
            }
            Object localObject2 = localObject1[i1];
            if (paramString2.equals(((g.a)localObject2).a))
            {
              e.a(localIntent, ((g.a)localObject2).e);
              break;
            }
            i1 += 1;
          }
        }
      }
      else
      {
        label212:
        if ((paramd instanceof com.ad4screen.sdk.model.displayformats.c))
        {
          paramString2 = (com.ad4screen.sdk.model.displayformats.c)paramd;
          if ("com.ad4screen.sdk.intent.action.DISPLAYED".equals(paramString1))
          {
            localObject1 = new HashMap();
            ((HashMap)localObject1).put("com.accengage.sdk.extra.file.content", paramString2.a);
            e.a(localIntent, (HashMap)localObject1);
          }
        }
      }
    }
  }
  
  private void a(com.ad4screen.sdk.service.modules.inapp.model.e parame)
  {
    if ((this.c == null) || (!this.c.equals(parame)))
    {
      if (this.c != null) {
        break label97;
      }
      this.c = parame;
    }
    for (;;)
    {
      e.a(this.c);
      g();
      Log.debug("InApp|Configuration was updated");
      this.j = true;
      this.b.a(this.j);
      com.ad4screen.sdk.systems.h.a(this.a.a()).a(com.ad4screen.sdk.common.g.e().a());
      this.e.b(500L);
      return;
      label97:
      this.c.a(parame);
    }
  }
  
  private void a(String paramString1, String paramString2, Intent paramIntent)
  {
    paramIntent = paramIntent.getExtras();
    if (paramIntent != null)
    {
      Object localObject = paramIntent.keySet();
      if (localObject != null)
      {
        Log.debug("Send Customs Params for InApp " + paramString1 + " with action " + paramString2);
        paramString1 = ((Set)localObject).iterator();
        while (paramString1.hasNext())
        {
          paramString2 = (String)paramString1.next();
          localObject = paramIntent.getString(paramString2);
          Log.debug(paramString2 + " -> " + (String)localObject);
        }
      }
    }
  }
  
  private boolean a(com.ad4screen.sdk.model.displayformats.d paramd, int paramInt, boolean paramBoolean)
  {
    if (paramd == null) {
      return false;
    }
    if (this.d.c().contains(paramd.i))
    {
      Log.internal("InApp|InApp #" + paramd.i + " is currently displayed, aborting new display calls");
      return false;
    }
    if (!paramBoolean)
    {
      if (((paramd instanceof com.ad4screen.sdk.model.displayformats.a)) && (this.k)) {
        if ((this.l == null) || (this.l.length == 0))
        {
          if (paramInt > -1)
          {
            this.a.f().a(paramd, paramInt);
            this.m.add(paramd.i);
            return false;
          }
        }
        else if (paramInt > -1)
        {
          int i1 = 0;
          while (i1 < this.l.length)
          {
            if (this.l[i1] == paramInt)
            {
              this.a.f().a(paramd, paramInt);
              this.m.add(paramd.i);
              return false;
            }
            i1 += 1;
          }
        }
      }
    }
    else
    {
      if (!this.m.contains(paramd.i)) {
        break label241;
      }
      this.m.remove(paramd.i);
    }
    if (((paramd instanceof com.ad4screen.sdk.service.modules.alarm.model.c)) || ((paramd instanceof com.ad4screen.sdk.service.modules.alarm.model.b)))
    {
      d(paramd.i);
      return true;
      label241:
      Log.error("InApp|InApp #" + paramd.i + " is not waiting for manual display (already displayed?) " + "or not allowed to be displayed manually.");
      return false;
    }
    return this.a.f().a(paramd, this.d.f());
  }
  
  private void b(boolean paramBoolean)
  {
    boolean bool = this.d.b();
    HashMap localHashMap = this.d.a();
    this.d = this.d.c(com.ad4screen.sdk.systems.h.a(this.a.a()));
    this.d.a(bool);
    this.d.a(localHashMap);
    localHashMap = this.c.b;
    Iterator localIterator = localHashMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      ((com.ad4screen.sdk.service.modules.inapp.model.g)localHashMap.get(str)).i().clear();
      ((com.ad4screen.sdk.service.modules.inapp.model.g)localHashMap.get(str)).j().clear();
      ((com.ad4screen.sdk.service.modules.inapp.model.g)localHashMap.get(str)).d(0);
      ((com.ad4screen.sdk.service.modules.inapp.model.g)localHashMap.get(str)).b(0);
      ((com.ad4screen.sdk.service.modules.inapp.model.g)localHashMap.get(str)).b(0L);
    }
    e.a(this.c);
    g();
    if (paramBoolean)
    {
      this.j = false;
      this.b.a(this.j);
      this.e.c();
      e();
    }
  }
  
  private void c(boolean paramBoolean)
  {
    Log.debug("InApp|Started rules analysis");
    if (com.ad4screen.sdk.systems.b.a(this.a.a()).f == null) {
      Log.debug("InApp|No Shared id, skipping rules analysis");
    }
    label653:
    do
    {
      return;
      if (this.c == null)
      {
        Log.debug("InApp|No configuration provided, skipping rules analysis");
        return;
      }
      if ((!paramBoolean) && (!com.ad4screen.sdk.systems.h.a(this.a.a()).f()))
      {
        Log.debug("InApp|Cannot display inapp in background");
        return;
      }
      if (!this.j)
      {
        Log.debug("InApp|Configuration is not up to date, skipping rules analysis");
        return;
      }
      if (this.d.b())
      {
        Log.debug("InApp|User locked InApp display. Rules checking skipped");
        return;
      }
      e.a(this.c);
      Object localObject1 = this.g.iterator();
      while (((Iterator)localObject1).hasNext()) {
        ((com.ad4screen.sdk.service.modules.inapp.rules.g)((Iterator)localObject1).next()).a(this.a.a(), this.d);
      }
      localObject1 = this.h.iterator();
      while (((Iterator)localObject1).hasNext()) {
        ((com.ad4screen.sdk.service.modules.inapp.rules.g)((Iterator)localObject1).next()).a(this.a.a(), this.d);
      }
      localObject1 = this.c.a;
      int i2 = localObject1.length;
      int i1 = 0;
      long l1 = 10000L;
      if (i1 < i2)
      {
        Object localObject2 = localObject1[i1];
        if ((localObject2 == null) || (((com.ad4screen.sdk.service.modules.inapp.model.i)localObject2).a() == null)) {
          Log.error("InApp|Current rule is null or does not have any id because of deserialization failure. Current rule skipped");
        }
        for (;;)
        {
          i1 += 1;
          break;
          com.ad4screen.sdk.service.modules.inapp.model.g localg = this.c.a(((com.ad4screen.sdk.service.modules.inapp.model.i)localObject2).a());
          if (localg == null)
          {
            Log.warn("InApp|InApp #" + ((com.ad4screen.sdk.service.modules.inapp.model.i)localObject2).a() + " has no associated message. Rule check skipped");
          }
          else if (this.d.c().contains(localg.a().i))
          {
            Log.debug("InApp|InApp #" + localg.a().i + " was already displayed. Rules checking skipped for this in-app");
          }
          else if ((!paramBoolean) || ((localg.a() instanceof com.ad4screen.sdk.service.modules.alarm.model.c)) || ((localg.a() instanceof com.ad4screen.sdk.service.modules.alarm.model.b)))
          {
            if ((localg.a() instanceof com.ad4screen.sdk.service.modules.alarm.model.c))
            {
              com.ad4screen.sdk.service.modules.alarm.model.c localc = (com.ad4screen.sdk.service.modules.alarm.model.c)localg.a();
              localc = this.f.b(localc.i);
              if (localc != null)
              {
                if (a(this.h, (com.ad4screen.sdk.service.modules.inapp.model.i)localObject2, localg)) {
                  break label653;
                }
                this.f.a(localc.i);
                continue;
              }
            }
            if ((!this.k) || (!this.m.contains(localg.a().i))) {
              if (!a(this.g, (com.ad4screen.sdk.service.modules.inapp.model.i)localObject2, localg))
              {
                localObject2 = a(localg, (com.ad4screen.sdk.service.modules.inapp.model.i)localObject2);
                if ((localObject2 != null) && (((Long)localObject2).longValue() < l1)) {
                  l1 = ((Long)localObject2).longValue();
                }
              }
              else
              {
                Log.debug("InApp|Found a matching message (#" + ((com.ad4screen.sdk.service.modules.inapp.model.i)localObject2).a() + ')');
                if (((localg.a() instanceof com.ad4screen.sdk.service.modules.alarm.model.c)) || ((localg.a() instanceof com.ad4screen.sdk.service.modules.alarm.model.b))) {
                  a(localg.a().i, -1);
                } else {
                  this.a.f().a(localg.a());
                }
              }
            }
          }
        }
      }
      if (l1 < 10000L)
      {
        Log.debug("InApp|New delay before checking rules again : " + l1 / 1000L + "s");
        this.e.a(l1);
      }
    } while (this.c.a.length != 0);
    Log.debug("InApp|No message found");
  }
  
  private void h()
  {
    c(false);
  }
  
  private void i()
  {
    this.e.c();
    this.e.b(500L);
  }
  
  public Long a(com.ad4screen.sdk.service.modules.inapp.model.g paramg, com.ad4screen.sdk.service.modules.inapp.model.i parami)
  {
    long l1 = com.ad4screen.sdk.common.g.e().a();
    if ((this.i != null) && (!(this.i instanceof k)) && (!(this.i instanceof com.ad4screen.sdk.service.modules.inapp.rules.i)) && (!(this.i instanceof com.ad4screen.sdk.service.modules.inapp.rules.f)))
    {
      paramg.a(0L);
      paramg.b(0L);
      g();
    }
    long l2;
    if (paramg.f() > 0L)
    {
      l2 = parami.k().longValue() - (l1 - paramg.f());
      if (l2 > 0L) {
        return Long.valueOf(l2);
      }
    }
    if (paramg.g() > 0L)
    {
      l2 = parami.l().longValue() - (l1 - paramg.g());
      if (l2 > 0L) {
        return Long.valueOf(l2);
      }
    }
    if (parami.g() != null)
    {
      l1 = parami.g().intValue() - (l1 - paramg.h());
      if (l1 > 0L) {
        return Long.valueOf(l1);
      }
    }
    return null;
  }
  
  public void a()
  {
    this.e.b();
  }
  
  public void a(Bundle paramBundle, boolean paramBoolean)
  {
    this.e.c();
    new g(this.a.a(), paramBundle, paramBoolean).run();
  }
  
  public void a(String paramString)
  {
    if (!this.d.c().contains(paramString)) {
      return;
    }
    Log.debug("InApp|Service closing inapp #" + paramString);
    this.a.f().a(paramString, this.d.f());
    this.e.a(paramString);
  }
  
  public void a(String paramString, int paramInt)
  {
    com.ad4screen.sdk.service.modules.inapp.model.g localg = this.c.a(paramString);
    if (a(localg.a(), paramInt, false))
    {
      paramInt = 0;
      while (paramInt < this.c.a.length)
      {
        if (this.c.a[paramInt].a().equalsIgnoreCase(paramString))
        {
          paramString = this.g.iterator();
          while (paramString.hasNext()) {
            ((com.ad4screen.sdk.service.modules.inapp.rules.g)paramString.next()).a(this.c.a[paramInt], localg);
          }
        }
        paramInt += 1;
      }
    }
    this.b.a(this.c);
  }
  
  public void a(String paramString1, String paramString2)
  {
    if (paramString1 == null)
    {
      Log.error("InApp|Client reported click on null inapp");
      return;
    }
    if (!this.d.c().contains(paramString1)) {
      Log.warn("InApp|Client reported click on inapp #" + paramString1 + " but inapp seems to not be displayed");
    }
    com.ad4screen.sdk.model.displayformats.d locald = this.c.b(paramString1);
    if (locald == null)
    {
      Log.error("InApp|Could not find format for clicked inapp #" + paramString1);
      return;
    }
    Log.debug("InApp|InApp #" + paramString1 + " was clicked");
    Object localObject2;
    Object localObject1;
    if ((locald instanceof com.ad4screen.sdk.model.displayformats.a))
    {
      localObject2 = ((com.ad4screen.sdk.model.displayformats.a)locald).d;
      localObject1 = "com.ad4screen.sdk.intent.action.CLICKED";
    }
    for (;;)
    {
      if ((localObject2 != null) || (((locald instanceof com.ad4screen.sdk.model.displayformats.g)) && (paramString2 != null)))
      {
        localObject3 = this.c.a(paramString1);
        ((com.ad4screen.sdk.service.modules.inapp.model.g)localObject3).c(((com.ad4screen.sdk.service.modules.inapp.model.g)localObject3).d() + 1);
        ((com.ad4screen.sdk.service.modules.inapp.model.g)localObject3).d(((com.ad4screen.sdk.service.modules.inapp.model.g)localObject3).e() + 1);
        g();
        a(locald.k, locald.i, paramString2, d.a.b, new com.ad4screen.sdk.common.e[0]);
      }
      for (;;)
      {
        b(paramString1);
        a(locald, (String)localObject1, paramString2);
        if (localObject2 == null) {
          break;
        }
        a((com.ad4screen.sdk.model.displayformats.d)localObject2, -1, false);
        return;
        if ((!(locald instanceof com.ad4screen.sdk.model.displayformats.g)) || (paramString2 == null)) {
          break label400;
        }
        localObject1 = ((com.ad4screen.sdk.model.displayformats.g)locald).c;
        int i2 = localObject1.length;
        int i1 = 0;
        for (;;)
        {
          if (i1 >= i2) {
            break label400;
          }
          localObject2 = localObject1[i1];
          if (paramString2.equals(((g.a)localObject2).a))
          {
            localObject1 = ((g.a)localObject2).d;
            if (((g.a)localObject2).d != null) {
              break label384;
            }
            a(locald, "com.ad4screen.sdk.intent.action.CLICKED", paramString2);
            localObject3 = "com.ad4screen.sdk.intent.action.CLOSED";
            localObject2 = localObject1;
            localObject1 = localObject3;
            break;
          }
          i1 += 1;
        }
        Log.debug("InApp|InApp #" + paramString1 + " click tracking will not be sent because target is null");
      }
      label384:
      Object localObject3 = "com.ad4screen.sdk.intent.action.CLICKED";
      localObject2 = localObject1;
      localObject1 = localObject3;
      continue;
      label400:
      localObject1 = "com.ad4screen.sdk.intent.action.CLICKED";
      localObject2 = null;
    }
  }
  
  public void a(String paramString1, String paramString2, d.a parama, com.ad4screen.sdk.common.e... paramVarArgs)
  {
    a(paramString1, paramString2, null, parama, paramVarArgs);
  }
  
  public void a(String paramString1, String paramString2, String paramString3, d.a parama, com.ad4screen.sdk.common.e... paramVarArgs)
  {
    if ((paramString1 == null) || (paramString2 == null)) {
      return;
    }
    com.ad4screen.sdk.service.modules.common.h.a(this.a, paramString2, paramString3, parama);
  }
  
  public void a(boolean paramBoolean)
  {
    this.d.a(paramBoolean);
    this.d.b(com.ad4screen.sdk.systems.h.a(this.a.a()));
    StringBuilder localStringBuilder = new StringBuilder().append("InApp|InApp display is now ");
    if (paramBoolean) {}
    for (String str = "";; str = "un")
    {
      Log.debug(str + "locked");
      if (paramBoolean) {
        break;
      }
      this.e.b(500L);
      return;
    }
    this.e.c();
  }
  
  public void a(boolean paramBoolean, int[] paramArrayOfInt)
  {
    this.l = paramArrayOfInt;
    this.k = paramBoolean;
  }
  
  public boolean a(List<com.ad4screen.sdk.service.modules.inapp.rules.g> paramList, com.ad4screen.sdk.service.modules.inapp.model.i parami, com.ad4screen.sdk.service.modules.inapp.model.g paramg)
  {
    if (paramg.a() == null)
    {
      Log.warn("InApp|InApp #" + parami.a() + " has no format to display.");
      return false;
    }
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      com.ad4screen.sdk.service.modules.inapp.rules.g localg = (com.ad4screen.sdk.service.modules.inapp.rules.g)paramList.next();
      if (!localg.b(parami, paramg))
      {
        this.i = localg;
        Log.verbose("InApp|Message #" + parami.a() + " do not match '" + localg.a() + "'");
        return false;
      }
    }
    this.i = null;
    return true;
  }
  
  public void b()
  {
    this.e.a();
  }
  
  public void b(String paramString)
  {
    if (paramString == null)
    {
      Log.error("InApp|Client reported null inapp was closed");
      return;
    }
    if (!this.d.c().contains(paramString)) {
      Log.error("InApp|Client reported inapp #" + paramString + " was closed but it can not be found in current opened inapp");
    }
    for (;;)
    {
      this.e.a(paramString);
      this.d.c().remove(paramString);
      this.d.b(com.ad4screen.sdk.systems.h.a(this.a.a()));
      i();
      return;
      Log.debug("InApp|InApp #" + paramString + " was closed");
    }
  }
  
  public void b(String paramString1, String paramString2)
  {
    if (paramString1 == null)
    {
      Log.debug("InApp|Cannot put state with null name");
      return;
    }
    if (paramString2 == null)
    {
      this.d.f(paramString1);
      Log.debug("InApp|State '" + paramString1 + "' removed");
    }
    for (;;)
    {
      this.d.b(com.ad4screen.sdk.systems.h.a(this.a.a()));
      i();
      return;
      com.ad4screen.sdk.service.modules.inapp.model.states.c localc2 = this.d.e(paramString1);
      com.ad4screen.sdk.service.modules.inapp.model.states.c localc1 = localc2;
      if (localc2 == null)
      {
        localc1 = new com.ad4screen.sdk.service.modules.inapp.model.states.c();
        localc1.a = paramString1;
        this.d.a(paramString1, localc1);
      }
      localc1.b = paramString2;
      Log.debug("InApp|State '" + paramString1 + "' is now set to '" + paramString2 + "'");
    }
  }
  
  public void c(String paramString)
  {
    if (paramString == null)
    {
      Log.error("InApp|Client reported null inapp was closed by click");
      return;
    }
    if (!this.d.c().contains(paramString))
    {
      Log.error("InApp|Client reported inapp #" + paramString + " was closed by click but it can not be found in current opened inapp");
      return;
    }
    Log.debug("InApp|Client reported inapp #" + paramString + " was closed by click");
    com.ad4screen.sdk.model.displayformats.d locald = this.c.b(paramString);
    if (locald == null)
    {
      Log.warn("InApp|Could not find inapp with id #" + paramString + " which was closed by user");
      b(paramString);
      return;
    }
    a(locald.l, locald.i, d.a.c, new com.ad4screen.sdk.common.e[0]);
    a(locald, "com.ad4screen.sdk.intent.action.CLOSED");
    b(paramString);
  }
  
  public boolean c()
  {
    return this.d.b();
  }
  
  public void d()
  {
    if (this.d.c().size() == 0) {}
    for (;;)
    {
      return;
      int i1 = 0;
      while (i1 < this.d.c().size())
      {
        Log.debug("InApp|Service closing inapp #" + (String)this.d.c().get(i1));
        this.a.f().a((String)this.d.c().get(i1), this.d.f());
        this.e.a((String)this.d.c().get(i1));
        i1 += 1;
      }
    }
  }
  
  public void d(String paramString)
  {
    if (paramString == null)
    {
      Log.error("InApp|Client reported null inapp was displayed");
      return;
    }
    if ((this.d.c().size() > 0) && (this.d.c().contains(paramString))) {
      Log.warn("InApp|Client reported inapp was displayed but inapp #" + paramString + " was already displayed");
    }
    com.ad4screen.sdk.model.displayformats.d locald = this.c.b(paramString);
    if (locald == null)
    {
      Log.error("InApp|Could not find format for displayed inapp #" + paramString);
      return;
    }
    if ((locald instanceof com.ad4screen.sdk.model.displayformats.h))
    {
      paramString = (com.ad4screen.sdk.model.displayformats.h)locald;
      com.ad4screen.sdk.service.modules.common.h.a(this.a.a(), paramString.a, new com.ad4screen.sdk.common.e[0]);
      a(locald);
      return;
    }
    if ((locald instanceof com.ad4screen.sdk.model.displayformats.c))
    {
      paramString = (com.ad4screen.sdk.model.displayformats.c)locald;
      a(paramString.j, paramString.i, d.a.a, new com.ad4screen.sdk.common.e[0]);
      a(locald, "com.ad4screen.sdk.intent.action.DISPLAYED");
      a(locald);
      return;
    }
    if ((locald instanceof com.ad4screen.sdk.service.modules.alarm.model.c))
    {
      paramString = this.f.b(locald.i);
      if (paramString == null)
      {
        this.f.a((com.ad4screen.sdk.service.modules.alarm.model.c)locald);
        a(locald);
        return;
      }
      Log.verbose("InApp|Alarm #" + locald.i + " is already set.");
      if (paramString.b() != null) {
        Log.verbose("InApp|Alarm #" + locald.i + " will be displayed at " + DateFormat.getDateTimeInstance().format(paramString.b()));
      }
      g();
      return;
    }
    if ((locald instanceof com.ad4screen.sdk.service.modules.alarm.model.b))
    {
      this.f.a((com.ad4screen.sdk.service.modules.alarm.model.b)locald);
      a(locald);
      return;
    }
    a(locald);
    a(locald, "com.ad4screen.sdk.intent.action.DISPLAYED");
    this.e.a(locald);
    if ((com.ad4screen.sdk.systems.h.a(this.a.a()).f()) || ((locald instanceof com.ad4screen.sdk.model.displayformats.g)))
    {
      Log.debug("InApp|InApp #" + paramString + " was displayed");
      this.d.c().add(paramString);
      this.d.b(com.ad4screen.sdk.systems.h.a(this.a.a()));
    }
    for (;;)
    {
      a(locald.j, locald.i, d.a.a, new com.ad4screen.sdk.common.e[0]);
      return;
      Log.debug("InApp|InApp #" + paramString + " not displayed because application is in background");
    }
  }
  
  public void e()
  {
    a(null, false);
  }
  
  public void e(String paramString)
  {
    if (paramString == null)
    {
      Log.debug("InApp|Cannot set view with null name");
      return;
    }
    this.d.c(paramString);
    this.d.b(com.ad4screen.sdk.systems.h.a(this.a.a()));
    Log.debug("InApp|View is now set to : " + paramString);
    d();
    i();
  }
  
  public com.ad4screen.sdk.service.modules.inapp.model.e f()
  {
    return this.c;
  }
  
  public void f(String paramString)
  {
    com.ad4screen.sdk.service.modules.inapp.model.g localg = this.c.a(paramString);
    int i1 = 0;
    for (;;)
    {
      if (i1 < this.c.a.length)
      {
        if (this.c.a[i1].a().equalsIgnoreCase(paramString))
        {
          paramString = this.g.iterator();
          while (paramString.hasNext()) {
            ((com.ad4screen.sdk.service.modules.inapp.rules.g)paramString.next()).a(this.a.a(), this.d);
          }
          a(this.g, this.c.a[i1], localg);
          paramString = this.g.iterator();
          while (paramString.hasNext()) {
            ((com.ad4screen.sdk.service.modules.inapp.rules.g)paramString.next()).a(this.c.a[i1], localg);
          }
          a(localg.a(), -1, true);
        }
      }
      else {
        return;
      }
      i1 += 1;
    }
  }
  
  public void g()
  {
    this.b.a(this.c);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */