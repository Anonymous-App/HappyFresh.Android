package com.appsee;

import java.io.File;
import java.util.List;
import java.util.concurrent.CountDownLatch;

class m
{
  public static m i()
  {
    try
    {
      if (y == null) {
        y = new m();
      }
      m localm = y;
      return localm;
    }
    finally {}
  }
  
  public void F()
  {
    this.D = false;
  }
  
  public boolean F()
  {
    return this.E;
  }
  
  public void K()
  {
    this.E = false;
    this.G = 0L;
    this.j = 0L;
    this.t = 0;
    this.S = true;
    if (this.e != null) {
      this.e.i();
    }
    this.M.i();
    this.V.i();
    this.I.i();
    this.L.i();
    if (this.k != null) {
      this.k.l();
    }
    if (this.k != null)
    {
      this.k.i();
      this.b = null;
    }
  }
  
  public void a()
    throws Exception
  {
    int n = 100;
    int i2 = 0;
    int i3 = 0;
    int i1 = 0;
    try
    {
      String str1;
      if (!this.E)
      {
        vd.i(AppseeBackgroundUploader.i("\035Z#F*NlO3Q3Ly&7T1I8P>Rr@y\0210\033!E;\n>L9P([x\0325\031"));
        if (this.e != null)
        {
          this.e.i();
          this.e = null;
        }
        this.D = true;
        this.E = false;
        mc.l(AppseeBackgroundUploader.i("x3Ez\021:\033=O,E>M3Q=\037,T\034x"), 1);
        i2 = n;
        for (;;)
        {
          if ((this.A) || (i1 >= i2)) {
            break label141;
          }
          str1 = AppseeBackgroundUploader.i("#3^*R2^pS=E1\0027_*Eo^#\t8ZzMt\0256N");
          i3 = i1 + 1;
          mc.l(str1, 1);
          Thread.sleep(100L);
          n = i2;
          i1 = i3;
          if (this.J == null) {
            break;
          }
          n = i2;
          i1 = i3;
          if (!this.J.i()) {
            break;
          }
          i2 = 300;
          i1 = i3;
        }
        label141:
        F();
        K();
        if (i1 >= i2)
        {
          this.m.interrupt();
          L();
          vd.i(new Exception(), AppseeBackgroundUploader.i("\033C!L5J.\037f\025;C7U;\0316Z \027g\035:^ \n;ElK?\037(Zp\020+\031"));
        }
        return;
      }
      this.M.l();
      this.V.l();
      this.I.l();
      this.L.l();
      return;
    }
    finally
    {
      if (this.e != null)
      {
        this.e.i();
        this.e = null;
      }
      this.D = true;
      this.E = false;
      mc.l(AppseeBackgroundUploader.i("x3Ez\021:\033=O,E>M3Q=\037,T\034x"), 1);
      i1 = i3;
      i2 = n;
      for (;;)
      {
        if ((this.A) || (i1 >= i2)) {
          break label488;
        }
        String str2 = AppseeBackgroundUploader.i("#3^*R2^pS=E1\0027_*Eo^#\t8ZzMt\0256N");
        i3 = i1 + 1;
        mc.l(str2, 1);
        Thread.sleep(100L);
        n = i2;
        i1 = i3;
        if (this.J == null) {
          break;
        }
        n = i2;
        i1 = i3;
        if (!this.J.i()) {
          break;
        }
        i2 = 300;
        i1 = i3;
      }
      label488:
      F();
      K();
      if (i1 >= i2)
      {
        this.m.interrupt();
        L();
        vd.i(new Exception(), AppseeBackgroundUploader.i("\033C!L5J.\037f\025;C7U;\0316Z \027g\035:^ \n;ElK?\037(Zp\020+\031"));
      }
    }
  }
  
  public boolean a()
  {
    return this.D;
  }
  
  public void f()
    throws Exception
  {
    if (this.E)
    {
      vd.i(AppseeBackgroundUploader.i("\021V5P9]pE Ra\025,^\016D+y8H(K\bZr\033 S0R;\031']7Y1\0252I*K+Sl[?\\5Mu\035<P"));
      return;
    }
    this.l = new Dimension(ge.i().i(), ge.i().l());
    this.B = (ge.i().F() * 1024);
    this.f = ((int)ge.i().l());
    K();
    this.k = new fd(2, this.l.getWidth(), this.l.getHeight());
    synchronized (this.c)
    {
      this.c.clear();
      this.d = hd.i(AppseeBackgroundUploader.i("\031") + "mp4").getAbsolutePath();
      mc.l(this.d, 1);
      ??? = new CountDownLatch(1);
      this.m = new Thread(new y(this, (CountDownLatch)???), AppseeBackgroundUploader.i("\023Ga\007;^\031C+O#y(P9Zb\007=E"));
      this.E = true;
      this.A = false;
      this.m.start();
      ((CountDownLatch)???).await();
      if (this.C != null) {
        throw this.C;
      }
    }
    this.e = new rb(new t(this), 1000 / this.f);
    this.e.l();
  }
  
  public long i()
  {
    return this.G;
  }
  
  public xd i()
  {
    if (this.b == null) {}
    for (;;)
    {
      try
      {
        this.b = this.k.i();
        return this.b;
      }
      catch (Exception localException)
      {
        this.b = null;
        continue;
      }
      mc.i(AppseeBackgroundUploader.i("E\006'R!Mo^#\t*J6S1\000%^5V|~\034\0250Bw\022;IoL XlG5\037(Zp\007=Y"), 1);
    }
  }
  
  public void i(boolean paramBoolean)
  {
    this.S = paramBoolean;
  }
  
  public boolean i()
  {
    return this.K;
  }
  
  public void l(boolean paramBoolean)
  {
    this.K = paramBoolean;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/m.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */