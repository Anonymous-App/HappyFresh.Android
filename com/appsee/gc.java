package com.appsee;

import android.graphics.Rect;
import android.view.View;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

class gc
{
  public static gc i()
  {
    try
    {
      if (G == null) {
        G = new gc();
      }
      gc localgc = G;
      return localgc;
    }
    finally {}
  }
  
  public List<Rect> i()
  {
    return this.i;
  }
  
  public void i()
    throws Exception
  {
    List localList;
    int j;
    int m;
    do
    {
      do
      {
        synchronized (this.i)
        {
          this.i.clear();
          if (!jc.i().i()) {
            return;
          }
        }
        ??? = ub.i().i();
      } while ((??? == null) || (???.length == 0));
      localList = i((Object[])???);
      j = 0;
      m = 0;
    } while (j >= ???.length);
    View localView = (View)???[m];
    if (l(localView)) {}
    for (;;)
    {
      j = m + 1;
      m = j;
      break;
      if (localView.isShown()) {
        i(localView, localList);
      }
    }
  }
  
  public void i(View paramView)
  {
    ArrayList localArrayList;
    synchronized (this.k)
    {
      localArrayList = new ArrayList();
      Iterator localIterator = this.k.iterator();
      while (localIterator.hasNext())
      {
        WeakReference localWeakReference = (WeakReference)localIterator.next();
        if ((localWeakReference.get() == null) || (((View)localWeakReference.get()).equals(paramView))) {
          localArrayList.add(localWeakReference);
        }
      }
    }
    this.k.removeAll(localArrayList);
  }
  
  public boolean i()
  {
    Object localObject = ge.i().i();
    if ((localObject != null) && (!((List)localObject).isEmpty()))
    {
      localObject = ((List)localObject).iterator();
      while (((Iterator)localObject).hasNext())
      {
        String str = (String)((Iterator)localObject).next();
        if (str.startsWith(AppseeBackgroundUploader.i("b\027*X9W")))
        {
          str = str.split(AppseeBackgroundUploader.i("\003"))[1];
          wc localwc = sc.i().i();
          if ((localwc != null) && (localwc.i().equalsIgnoreCase(str))) {
            return true;
          }
        }
      }
    }
    return false;
  }
  
  public void l(View paramView)
  {
    synchronized (this.k)
    {
      this.k.add(new WeakReference(paramView));
      return;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/gc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */