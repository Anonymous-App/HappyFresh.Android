package io.fabric.sdk.android.services.concurrency;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class DependencyPriorityBlockingQueue<E extends Dependency,  extends Task,  extends PriorityProvider>
  extends PriorityBlockingQueue<E>
{
  static final int PEEK = 1;
  static final int POLL = 2;
  static final int POLL_WITH_TIMEOUT = 3;
  static final int TAKE = 0;
  final Queue<E> blockedQueue = new LinkedList();
  private final ReentrantLock lock = new ReentrantLock();
  
  boolean canProcess(E paramE)
  {
    return paramE.areDependenciesMet();
  }
  
  public void clear()
  {
    try
    {
      this.lock.lock();
      this.blockedQueue.clear();
      super.clear();
      return;
    }
    finally
    {
      this.lock.unlock();
    }
  }
  
  <T> T[] concatenate(T[] paramArrayOfT1, T[] paramArrayOfT2)
  {
    int i = paramArrayOfT1.length;
    int j = paramArrayOfT2.length;
    Object[] arrayOfObject = (Object[])Array.newInstance(paramArrayOfT1.getClass().getComponentType(), i + j);
    System.arraycopy(paramArrayOfT1, 0, arrayOfObject, 0, i);
    System.arraycopy(paramArrayOfT2, 0, arrayOfObject, i, j);
    return arrayOfObject;
  }
  
  /* Error */
  public boolean contains(Object paramObject)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 33	io/fabric/sdk/android/services/concurrency/DependencyPriorityBlockingQueue:lock	Ljava/util/concurrent/locks/ReentrantLock;
    //   4: invokevirtual 47	java/util/concurrent/locks/ReentrantLock:lock	()V
    //   7: aload_0
    //   8: aload_1
    //   9: invokespecial 87	java/util/concurrent/PriorityBlockingQueue:contains	(Ljava/lang/Object;)Z
    //   12: ifne +18 -> 30
    //   15: aload_0
    //   16: getfield 28	io/fabric/sdk/android/services/concurrency/DependencyPriorityBlockingQueue:blockedQueue	Ljava/util/Queue;
    //   19: aload_1
    //   20: invokeinterface 88 2 0
    //   25: istore_2
    //   26: iload_2
    //   27: ifeq +14 -> 41
    //   30: iconst_1
    //   31: istore_2
    //   32: aload_0
    //   33: getfield 33	io/fabric/sdk/android/services/concurrency/DependencyPriorityBlockingQueue:lock	Ljava/util/concurrent/locks/ReentrantLock;
    //   36: invokevirtual 55	java/util/concurrent/locks/ReentrantLock:unlock	()V
    //   39: iload_2
    //   40: ireturn
    //   41: iconst_0
    //   42: istore_2
    //   43: goto -11 -> 32
    //   46: astore_1
    //   47: aload_0
    //   48: getfield 33	io/fabric/sdk/android/services/concurrency/DependencyPriorityBlockingQueue:lock	Ljava/util/concurrent/locks/ReentrantLock;
    //   51: invokevirtual 55	java/util/concurrent/locks/ReentrantLock:unlock	()V
    //   54: aload_1
    //   55: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	56	0	this	DependencyPriorityBlockingQueue
    //   0	56	1	paramObject	Object
    //   25	18	2	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   0	26	46	finally
  }
  
  public int drainTo(Collection<? super E> paramCollection)
  {
    int i;
    int j;
    try
    {
      this.lock.lock();
      i = super.drainTo(paramCollection);
      j = this.blockedQueue.size();
      while (!this.blockedQueue.isEmpty()) {
        paramCollection.add(this.blockedQueue.poll());
      }
    }
    finally
    {
      this.lock.unlock();
    }
    return i + j;
  }
  
  public int drainTo(Collection<? super E> paramCollection, int paramInt)
  {
    try
    {
      this.lock.lock();
      int i = super.drainTo(paramCollection, paramInt);
      while ((!this.blockedQueue.isEmpty()) && (i <= paramInt))
      {
        paramCollection.add(this.blockedQueue.poll());
        i += 1;
      }
      return i;
    }
    finally
    {
      this.lock.unlock();
    }
  }
  
  E get(int paramInt, Long paramLong, TimeUnit paramTimeUnit)
    throws InterruptedException
  {
    for (;;)
    {
      Dependency localDependency = performOperation(paramInt, paramLong, paramTimeUnit);
      if ((localDependency == null) || (canProcess(localDependency))) {
        return localDependency;
      }
      offerBlockedResult(paramInt, localDependency);
    }
  }
  
  boolean offerBlockedResult(int paramInt, E paramE)
  {
    try
    {
      this.lock.lock();
      if (paramInt == 1) {
        super.remove(paramE);
      }
      boolean bool = this.blockedQueue.offer(paramE);
      return bool;
    }
    finally
    {
      this.lock.unlock();
    }
  }
  
  public E peek()
  {
    try
    {
      Dependency localDependency = get(1, null, null);
      return localDependency;
    }
    catch (InterruptedException localInterruptedException) {}
    return null;
  }
  
  E performOperation(int paramInt, Long paramLong, TimeUnit paramTimeUnit)
    throws InterruptedException
  {
    switch (paramInt)
    {
    default: 
      return null;
    case 0: 
      return (Dependency)super.take();
    case 1: 
      return (Dependency)super.peek();
    case 2: 
      return (Dependency)super.poll();
    }
    return (Dependency)super.poll(paramLong.longValue(), paramTimeUnit);
  }
  
  public E poll()
  {
    try
    {
      Dependency localDependency = get(2, null, null);
      return localDependency;
    }
    catch (InterruptedException localInterruptedException) {}
    return null;
  }
  
  public E poll(long paramLong, TimeUnit paramTimeUnit)
    throws InterruptedException
  {
    return get(3, Long.valueOf(paramLong), paramTimeUnit);
  }
  
  public void recycleBlockedQueue()
  {
    try
    {
      this.lock.lock();
      Iterator localIterator = this.blockedQueue.iterator();
      while (localIterator.hasNext())
      {
        Dependency localDependency = (Dependency)localIterator.next();
        if (canProcess(localDependency))
        {
          super.offer(localDependency);
          localIterator.remove();
        }
      }
    }
    finally
    {
      this.lock.unlock();
    }
  }
  
  /* Error */
  public boolean remove(Object paramObject)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 33	io/fabric/sdk/android/services/concurrency/DependencyPriorityBlockingQueue:lock	Ljava/util/concurrent/locks/ReentrantLock;
    //   4: invokevirtual 47	java/util/concurrent/locks/ReentrantLock:lock	()V
    //   7: aload_0
    //   8: aload_1
    //   9: invokespecial 131	java/util/concurrent/PriorityBlockingQueue:remove	(Ljava/lang/Object;)Z
    //   12: ifne +18 -> 30
    //   15: aload_0
    //   16: getfield 28	io/fabric/sdk/android/services/concurrency/DependencyPriorityBlockingQueue:blockedQueue	Ljava/util/Queue;
    //   19: aload_1
    //   20: invokeinterface 184 2 0
    //   25: istore_2
    //   26: iload_2
    //   27: ifeq +14 -> 41
    //   30: iconst_1
    //   31: istore_2
    //   32: aload_0
    //   33: getfield 33	io/fabric/sdk/android/services/concurrency/DependencyPriorityBlockingQueue:lock	Ljava/util/concurrent/locks/ReentrantLock;
    //   36: invokevirtual 55	java/util/concurrent/locks/ReentrantLock:unlock	()V
    //   39: iload_2
    //   40: ireturn
    //   41: iconst_0
    //   42: istore_2
    //   43: goto -11 -> 32
    //   46: astore_1
    //   47: aload_0
    //   48: getfield 33	io/fabric/sdk/android/services/concurrency/DependencyPriorityBlockingQueue:lock	Ljava/util/concurrent/locks/ReentrantLock;
    //   51: invokevirtual 55	java/util/concurrent/locks/ReentrantLock:unlock	()V
    //   54: aload_1
    //   55: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	56	0	this	DependencyPriorityBlockingQueue
    //   0	56	1	paramObject	Object
    //   25	18	2	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   0	26	46	finally
  }
  
  public boolean removeAll(Collection<?> paramCollection)
  {
    try
    {
      this.lock.lock();
      boolean bool1 = super.removeAll(paramCollection);
      boolean bool2 = this.blockedQueue.removeAll(paramCollection);
      return bool1 | bool2;
    }
    finally
    {
      this.lock.unlock();
    }
  }
  
  public int size()
  {
    try
    {
      this.lock.lock();
      int i = this.blockedQueue.size();
      int j = super.size();
      return i + j;
    }
    finally
    {
      this.lock.unlock();
    }
  }
  
  public E take()
    throws InterruptedException
  {
    return get(0, null, null);
  }
  
  public Object[] toArray()
  {
    try
    {
      this.lock.lock();
      Object[] arrayOfObject = concatenate(super.toArray(), this.blockedQueue.toArray());
      return arrayOfObject;
    }
    finally
    {
      this.lock.unlock();
    }
  }
  
  public <T> T[] toArray(T[] paramArrayOfT)
  {
    try
    {
      this.lock.lock();
      paramArrayOfT = concatenate(super.toArray(paramArrayOfT), this.blockedQueue.toArray(paramArrayOfT));
      return paramArrayOfT;
    }
    finally
    {
      this.lock.unlock();
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/concurrency/DependencyPriorityBlockingQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */