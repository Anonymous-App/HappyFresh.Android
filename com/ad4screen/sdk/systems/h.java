package com.ad4screen.sdk.systems;

import android.content.Context;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.compatibility.k.c;
import com.ad4screen.sdk.common.g;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public final class h
{
  private static h e;
  private com.ad4screen.sdk.service.modules.authentication.model.a a;
  private final i b;
  private final j c;
  private final Context d;
  
  private h(Context paramContext)
  {
    this.b = new i(paramContext);
    this.c = new j(paramContext);
    this.d = paramContext;
  }
  
  public static h a(Context paramContext)
  {
    try
    {
      if (e == null) {
        e = new h(paramContext.getApplicationContext());
      }
      paramContext = e;
      return paramContext;
    }
    finally {}
  }
  
  private void i()
  {
    this.b.c();
    this.c.c();
    if (this.a != null) {
      a(this.a);
    }
  }
  
  private void j()
  {
    this.c.c();
    f.a().a(new j());
  }
  
  public void a()
  {
    int k = 1;
    int m = 0;
    int n = this.b.e();
    int i = m;
    int j = n;
    if (n > 0)
    {
      i = m;
      j = n;
      if (!f())
      {
        j = 0;
        i = 1;
      }
    }
    if (j > 0)
    {
      this.b.a(j - 1);
      if (j - 1 <= 0) {
        i = k;
      }
    }
    for (;;)
    {
      this.b.a(g.e().a());
      Log.debug("Session|LaunchActivity stopped : displayed #" + (j - 1));
      if (i != 0)
      {
        Log.debug("Session|Entered background");
        f.a().a(new c());
      }
      f.a().a(new b());
      return;
    }
  }
  
  public void a(long paramLong)
  {
    this.b.b(paramLong);
  }
  
  public void a(com.ad4screen.sdk.service.modules.authentication.model.a parama)
  {
    SecretKey localSecretKey = com.ad4screen.sdk.common.i.a();
    Cipher localCipher = com.ad4screen.sdk.common.i.a(localSecretKey);
    this.b.a("t", com.ad4screen.sdk.common.i.a(localCipher, parama.a));
    this.b.a("u", com.ad4screen.sdk.common.i.a(localCipher, parama.b));
    this.b.a("v", k.c.a(localSecretKey.getEncoded(), 0));
    this.b.a("w", k.c.a(localCipher.getIV(), 0));
    this.b.d();
    this.a = parama;
  }
  
  public void a(String paramString1, String paramString2, String paramString3)
  {
    int m = 0;
    long l2 = this.b.a();
    int i = this.b.e();
    if ((i > 0) && (!f())) {
      i = 0;
    }
    for (;;)
    {
      int j;
      long l1;
      int k;
      StringBuilder localStringBuilder;
      if (i == 0)
      {
        j = 1;
        l1 = g.e().a();
        l2 = l1 - l2;
        k = m;
        if (l2 > 300000L)
        {
          k = m;
          if (i == 0) {
            k = 1;
          }
        }
        localStringBuilder = new StringBuilder().append("Session|Session is ");
        if (k == 0) {
          break label258;
        }
      }
      label258:
      for (String str = "restarting";; str = "in progress")
      {
        Log.debug(str + ". UI count : " + i + ", Last session activity : " + l2 / 1000L + '/' + 300L);
        Log.debug("Session|LaunchActivity started : displayed #" + (i + 1));
        if (j != 0)
        {
          Log.debug("Session|Entered foreground");
          f.a().a(new d());
        }
        if (k != 0)
        {
          i();
          j();
        }
        this.b.a(l1);
        this.b.a(1);
        f.a().a(new a(paramString1, paramString2, paramString3));
        return;
        j = 0;
        break;
      }
    }
  }
  
  public void b()
  {
    this.b.c();
    this.c.c();
    this.a = null;
  }
  
  public void c()
  {
    this.b.a(true);
  }
  
  public void d()
  {
    this.b.a(false);
  }
  
  public j e()
  {
    return this.c;
  }
  
  public boolean f()
  {
    if (this.b.e() > 0) {}
    for (boolean bool1 = true;; bool1 = false)
    {
      boolean bool2 = bool1;
      if (bool1)
      {
        bool1 = com.ad4screen.sdk.common.i.a(this.d);
        bool2 = bool1;
        if (!bool1)
        {
          this.b.a(0);
          this.b.a(g.e().a());
          Log.debug("Session|Entered background");
          f.a().a(new c());
          f.a().a(new b());
          bool2 = bool1;
        }
      }
      return bool2;
    }
  }
  
  public com.ad4screen.sdk.service.modules.authentication.model.a g()
  {
    Object localObject2 = null;
    if (this.a != null) {
      localObject1 = this.a;
    }
    String str1;
    String str2;
    String str4;
    String str3;
    do
    {
      do
      {
        do
        {
          return (com.ad4screen.sdk.service.modules.authentication.model.a)localObject1;
          str1 = this.b.a("t", null);
          str2 = this.b.a("u", null);
          str4 = this.b.a("v", null);
          str3 = this.b.a("w", null);
          localObject1 = localObject2;
        } while (str4 == null);
        localObject1 = localObject2;
      } while (str1 == null);
      localObject1 = localObject2;
    } while (str2 == null);
    Object localObject1 = new SecretKeySpec(k.c.a(str4, 0), com.ad4screen.sdk.common.i.b());
    return new com.ad4screen.sdk.service.modules.authentication.model.a(com.ad4screen.sdk.common.i.a(k.c.a(str3, 0), str1, (SecretKey)localObject1), com.ad4screen.sdk.common.i.a(k.c.a(str3, 0), str2, (SecretKey)localObject1));
  }
  
  public boolean h()
  {
    return this.b.f();
  }
  
  public static final class a
    implements f.a<h.e>
  {
    private final String a;
    private final String b;
    private final String c;
    
    public a(String paramString1, String paramString2, String paramString3)
    {
      this.b = paramString2;
      this.c = paramString3;
      this.a = paramString1;
    }
    
    public void a(h.e parame)
    {
      parame.a(this.a, this.b, this.c);
    }
  }
  
  public static final class b
    implements f.a<h.f>
  {
    public void a(h.f paramf)
    {
      paramf.a();
    }
  }
  
  public static final class c
    implements f.a<h.g>
  {
    public void a(h.g paramg)
    {
      paramg.a();
    }
  }
  
  public static final class d
    implements f.a<h.h>
  {
    public void a(h.h paramh)
    {
      paramh.a();
    }
  }
  
  public static abstract interface e
  {
    public abstract void a(String paramString1, String paramString2, String paramString3);
  }
  
  public static abstract interface f
  {
    public abstract void a();
  }
  
  public static abstract interface g
  {
    public abstract void a();
  }
  
  public static abstract interface h
  {
    public abstract void a();
  }
  
  public static abstract interface i
  {
    public abstract void a();
  }
  
  public static final class j
    implements f.a<h.i>
  {
    public void a(h.i parami)
    {
      parami.a();
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/systems/h.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */