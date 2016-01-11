package com.appsee;

import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

class ub
  implements p
{
  public static ub i()
  {
    try
    {
      if (i == null) {
        i = new ub();
      }
      ub localub = i;
      return localub;
    }
    finally {}
  }
  
  public void F()
    throws Exception
  {
    try
    {
      if (this.e == null) {
        k();
      }
      this.k = false;
      this.A = 0;
      this.G = rj.i();
      this.g = new WeakReference(null);
      this.a = null;
      this.K = 0;
      this.h = 0;
      this.J = new rb(new kb(this), 200);
      this.J.l();
      return;
    }
    catch (Exception localException)
    {
      vd.l(localException, AppseeBackgroundUploader.i("\t[(P(\037v\021>[4V\036<(V \025>Rg\0212\0338C!N#^zR;Qp\023/]q\030\030~3K$\\<P1\007=I*O!\n(L.Z9Kx\033$\001s\026"));
      throw localException;
    }
  }
  
  public void c()
  {
    ol.i(new jb(this));
  }
  
  public void f()
  {
    this.C.l();
    if (this.J != null) {
      this.J.i();
    }
    j();
  }
  
  public n i()
  {
    if (this.a != null) {
      return (n)this.a.get();
    }
    return null;
  }
  
  public n i(View paramView)
  {
    Iterator localIterator = i().iterator();
    while (localIterator.hasNext())
    {
      n localn = (n)localIterator.next();
      if (localn.i() == paramView) {
        return localn;
      }
    }
    return null;
  }
  
  public List<n> i()
  {
    return Collections.unmodifiableList(this.d);
  }
  
  public void i(n paramn, List<View> paramList)
  {
    if (this.I != null) {
      this.I.i(paramn, paramList);
    }
  }
  
  public void i(q paramq)
  {
    this.I = paramq;
  }
  
  public void i(String paramString)
    throws Exception
  {
    int n = 0;
    Object localObject1 = "";
    int j = 0;
    while (j < this.d.size())
    {
      Object localObject2 = (n)this.d.get(n);
      WindowManager.LayoutParams localLayoutParams = ((n)localObject2).i().getAttributes();
      localObject1 = new StringBuilder().insert(0, (String)localObject1).append(i((n)localObject2)).append(AppseeBackgroundUploader.i("\020")).append(((n)localObject2).i().isShown()).append(AppseeBackgroundUploader.i("\002")).append(localLayoutParams.type);
      localObject2 = AppseeBackgroundUploader.i("t\024");
      j = n + 1;
      localObject1 = (String)localObject2;
      n = j;
    }
    mc.l(AppseeBackgroundUploader.i("zh\023qU;\035|g\030") + (String)localObject1, 1);
  }
  
  public Object[] i()
    throws Exception
  {
    Object localObject = bc.i(this.e, AppseeBackgroundUploader.i("|\"#J*K"));
    if ((localObject instanceof Object[])) {
      return (Object[])localObject;
    }
    if ((localObject instanceof List)) {
      return ((List)localObject).toArray();
    }
    this.A += 1;
    if (this.A > 4) {
      throw new Exception(AppseeBackgroundUploader.i("\007Yp\0262^o^ \n+L.\037(P~\000jY4]!@|X\"G3N1\022,T\"\n8C\"M5HzRp\032+H8J"));
    }
    return null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/ub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */