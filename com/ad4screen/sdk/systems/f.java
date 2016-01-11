package com.ad4screen.sdk.systems;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public final class f
{
  private static f b;
  private final HashMap<Class, CopyOnWriteArrayList> a = new HashMap();
  
  public static f a()
  {
    if (b == null) {
      b = new f();
    }
    return b;
  }
  
  private <L> CopyOnWriteArrayList<L> a(Class<? extends a<L>> paramClass)
  {
    synchronized (this.a)
    {
      CopyOnWriteArrayList localCopyOnWriteArrayList = (CopyOnWriteArrayList)this.a.get(paramClass);
      if (localCopyOnWriteArrayList != null) {
        return localCopyOnWriteArrayList;
      }
      localCopyOnWriteArrayList = new CopyOnWriteArrayList();
      this.a.put(paramClass, localCopyOnWriteArrayList);
      return localCopyOnWriteArrayList;
    }
  }
  
  public <L> void a(a<L> parama)
  {
    Iterator localIterator = a(parama.getClass()).iterator();
    while (localIterator.hasNext()) {
      parama.a(localIterator.next());
    }
  }
  
  public <L> void a(Class<? extends a<L>> arg1, L paramL)
  {
    synchronized (a(???))
    {
      if (!???.contains(paramL)) {
        ???.add(paramL);
      }
      return;
    }
  }
  
  public void b()
  {
    this.a.clear();
  }
  
  public <L> void b(Class<? extends a<L>> arg1, L paramL)
  {
    synchronized (a(???))
    {
      ???.remove(paramL);
      return;
    }
  }
  
  public static abstract interface a<L>
  {
    public abstract void a(L paramL);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/systems/f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */