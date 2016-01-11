package com.appsee;

import android.view.Menu;
import android.view.View;
import android.widget.AbsListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class kk
{
  public static kk i()
  {
    try
    {
      if (G == null) {
        G = new kk();
      }
      kk localkk = G;
      return localkk;
    }
    finally {}
  }
  
  public void i()
    throws Exception
  {
    i(null, null);
  }
  
  public void l()
    throws Exception
  {
    if (!i()) {}
    Object localObject2;
    Object localObject3;
    Object localObject1;
    do
    {
      return;
      localObject2 = ub.i().i();
      localObject3 = ((n)localObject2).i();
      localObject1 = localObject3;
      if (localObject3 == null) {
        localObject1 = localObject2;
      }
      localObject3 = i(localObject1);
      switch (dg.G[localObject3.ordinal()])
      {
      default: 
        localObject2 = i((n)localObject1);
      }
    } while (!i(localObject1, (String)localObject2));
    sc.i().i((String)localObject2, (vc)localObject3, true);
    mc.l((String)localObject2, 1);
    return;
    if ((localObject1 instanceof Menu))
    {
      i((n)localObject2, (Menu)localObject1);
      return;
    }
    l((n)localObject1, 0);
    return;
    i((n)localObject1, 0);
  }
  
  public void l(n paramn, int paramInt)
    throws Exception
  {
    Object localObject2 = pb.i(paramn.i(), AbsListView.class);
    AbsListView localAbsListView = (AbsListView)pb.i((List)localObject2);
    if (localAbsListView == null) {}
    Object localObject1;
    do
    {
      return;
      localObject1 = new ArrayList();
      localObject2 = ((List)localObject2).iterator();
      while (((Iterator)localObject2).hasNext())
      {
        Object localObject3 = (View)((Iterator)localObject2).next();
        if ((((View)localObject3).isShown()) && (((View)localObject3).getWidth() > 0) && (((View)localObject3).getHeight() > 0))
        {
          localObject3 = (AbsListView)localObject3;
          paramInt = 0;
          for (int j = 0; paramInt < ((AbsListView)localObject3).getChildCount(); j = paramInt)
          {
            Iterator localIterator = pb.i(((AbsListView)localObject3).getChildAt(j), TextView.class).iterator();
            while (localIterator.hasNext()) {
              ((List)localObject1).add(((TextView)localIterator.next()).getText().toString());
            }
            paramInt = j + 1;
          }
        }
      }
      localObject2 = new StringBuilder(((List)localObject1).size() * 50);
      ((StringBuilder)localObject2).append(((List)localObject1).size());
      localObject1 = ((List)localObject1).iterator();
      while (((Iterator)localObject1).hasNext()) {
        ((StringBuilder)localObject2).append((String)((Iterator)localObject1).next());
      }
      localObject1 = lb.l(((StringBuilder)localObject2).toString());
    } while (!i(paramn, (String)localObject1));
    if (pb.a(localAbsListView)) {}
    for (paramn = vc.b;; paramn = vc.G)
    {
      sc.i().i((String)localObject1, vc.G, true);
      return;
    }
  }
  
  public boolean l()
  {
    return this.i;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/kk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */