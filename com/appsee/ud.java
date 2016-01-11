package com.appsee;

import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import java.util.Iterator;
import java.util.List;

class ud
{
  public static final long b = 1000L;
  
  public static ud i()
  {
    try
    {
      if (C == null) {
        C = new ud();
      }
      ud localud = C;
      return localud;
    }
    finally {}
  }
  
  public void i()
    throws Exception
  {
    if ((!jc.i().i()) || (!ge.i().M()) || (!ge.i().A())) {}
    do
    {
      do
      {
        return;
        i(AppseeBackgroundUploader.i("X GaK\"M(P3[?\035$[>L8R0\027&\\7@?=\027U?_;g)]2P>rp\032+H>L"), AppseeBackgroundUploader.i("7lt\006<F8["));
        i(AppseeBackgroundUploader.i("X GaK\"M(P3[?\035$[>L8R0\027&\\7@?=\027U?_;g)]2P>lt\0079F4P"), AppseeBackgroundUploader.i("7|/M\\\021>G4Z"));
      } while (this.e);
      localObject1 = ub.i().i();
    } while ((localObject1 == null) || (((List)localObject1).isEmpty()));
    Object localObject1 = ((List)localObject1).iterator();
    long l1 = 0L;
    boolean bool2 = false;
    label324:
    for (;;)
    {
      if (((Iterator)localObject1).hasNext())
      {
        Object localObject2 = (n)((Iterator)localObject1).next();
        if (((n)localObject2).i() == null) {
          break;
        }
        localObject2 = ((n)localObject2).i();
        if (localObject2 == null) {
          break;
        }
        this.m.getDefaultDisplay().getMetrics(this.K);
        ((View)localObject2).getWindowVisibleDisplayFrame(this.d);
        if (this.d.width() != this.K.widthPixels) {
          continue;
        }
        if (this.d.bottom != this.K.heightPixels) {}
        for (boolean bool1 = true;; bool1 = false)
        {
          bool2 |= bool1;
          if (this.d.bottom == this.K.heightPixels) {
            break label324;
          }
          l1 = Math.abs(this.K.heightPixels - this.d.bottom);
          break;
        }
      }
      long l2 = rj.i();
      long l3 = this.a;
      if ((i() == bool2) || (l2 - l3 <= 500L)) {
        break;
      }
      i(bool2, l1);
      return;
    }
  }
  
  public void i(i parami)
  {
    this.I = parami;
  }
  
  public void i(ki paramki)
  {
    if ((paramki == ki.b) || (paramki == ki.G)) {}
    for (boolean bool = true;; bool = false)
    {
      i(bool);
      return;
    }
  }
  
  public boolean i()
  {
    synchronized (this.k)
    {
      boolean bool = this.G;
      return bool;
    }
  }
  
  public boolean l()
  {
    synchronized (this.k)
    {
      boolean bool = this.e;
      return bool;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/ud.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */