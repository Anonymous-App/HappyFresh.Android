package com.parse;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.WeakHashMap;
import java.util.concurrent.locks.Lock;

class LockSet
{
  private static long nextStableId = 0L;
  private static WeakHashMap<Lock, Long> stableIds = new WeakHashMap();
  private final Set<Lock> locks = new TreeSet(new Comparator()
  {
    public int compare(Lock paramAnonymousLock1, Lock paramAnonymousLock2)
    {
      return LockSet.getStableId(paramAnonymousLock1).compareTo(LockSet.getStableId(paramAnonymousLock2));
    }
  });
  
  public LockSet(Collection<Lock> paramCollection)
  {
    this.locks.addAll(paramCollection);
  }
  
  private static Long getStableId(Lock paramLock)
  {
    synchronized (stableIds)
    {
      if (stableIds.containsKey(paramLock))
      {
        paramLock = (Long)stableIds.get(paramLock);
        return paramLock;
      }
      long l = nextStableId;
      nextStableId = 1L + l;
      stableIds.put(paramLock, Long.valueOf(l));
      return Long.valueOf(l);
    }
  }
  
  public void lock()
  {
    Iterator localIterator = this.locks.iterator();
    while (localIterator.hasNext()) {
      ((Lock)localIterator.next()).lock();
    }
  }
  
  public void unlock()
  {
    Iterator localIterator = this.locks.iterator();
    while (localIterator.hasNext()) {
      ((Lock)localIterator.next()).unlock();
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/LockSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */