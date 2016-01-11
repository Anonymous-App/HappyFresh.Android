package com.squareup.okhttp;

import com.squareup.okhttp.internal.Platform;
import com.squareup.okhttp.internal.Util;
import java.net.SocketException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class ConnectionPool
{
  private static final long DEFAULT_KEEP_ALIVE_DURATION_MS = 300000L;
  private static final ConnectionPool systemDefault;
  private final Deque<Connection> connections = new ArrayDeque();
  private final Runnable connectionsCleanupRunnable = new Runnable()
  {
    public void run()
    {
      ConnectionPool.this.runCleanupUntilPoolIsEmpty();
    }
  };
  private Executor executor = new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory("OkHttp ConnectionPool", true));
  private final long keepAliveDurationNs;
  private final int maxIdleConnections;
  
  static
  {
    String str1 = System.getProperty("http.keepAlive");
    String str2 = System.getProperty("http.keepAliveDuration");
    String str3 = System.getProperty("http.maxConnections");
    if (str2 != null) {}
    for (long l = Long.parseLong(str2); (str1 != null) && (!Boolean.parseBoolean(str1)); l = 300000L)
    {
      systemDefault = new ConnectionPool(0, l);
      return;
    }
    if (str3 != null)
    {
      systemDefault = new ConnectionPool(Integer.parseInt(str3), l);
      return;
    }
    systemDefault = new ConnectionPool(5, l);
  }
  
  public ConnectionPool(int paramInt, long paramLong)
  {
    this.maxIdleConnections = paramInt;
    this.keepAliveDurationNs = (paramLong * 1000L * 1000L);
  }
  
  private void addConnection(Connection paramConnection)
  {
    boolean bool = this.connections.isEmpty();
    this.connections.addFirst(paramConnection);
    if (bool)
    {
      this.executor.execute(this.connectionsCleanupRunnable);
      return;
    }
    notifyAll();
  }
  
  public static ConnectionPool getDefault()
  {
    return systemDefault;
  }
  
  private void runCleanupUntilPoolIsEmpty()
  {
    while (performCleanup()) {}
  }
  
  public void evictAll()
  {
    try
    {
      ArrayList localArrayList = new ArrayList(this.connections);
      this.connections.clear();
      notifyAll();
      int i = 0;
      int j = localArrayList.size();
      while (i < j)
      {
        Util.closeQuietly(((Connection)localArrayList.get(i)).getSocket());
        i += 1;
      }
      return;
    }
    finally {}
  }
  
  /* Error */
  public Connection get(Address paramAddress)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aconst_null
    //   3: astore 4
    //   5: aload_0
    //   6: getfield 68	com/squareup/okhttp/ConnectionPool:connections	Ljava/util/Deque;
    //   9: invokeinterface 171 1 0
    //   14: astore 5
    //   16: aload 4
    //   18: astore_3
    //   19: aload 5
    //   21: invokeinterface 176 1 0
    //   26: ifeq +77 -> 103
    //   29: aload 5
    //   31: invokeinterface 180 1 0
    //   36: checkcast 156	com/squareup/okhttp/Connection
    //   39: astore_3
    //   40: aload_3
    //   41: invokevirtual 184	com/squareup/okhttp/Connection:getRoute	()Lcom/squareup/okhttp/Route;
    //   44: invokevirtual 190	com/squareup/okhttp/Route:getAddress	()Lcom/squareup/okhttp/Address;
    //   47: aload_1
    //   48: invokevirtual 196	com/squareup/okhttp/Address:equals	(Ljava/lang/Object;)Z
    //   51: ifeq -35 -> 16
    //   54: aload_3
    //   55: invokevirtual 199	com/squareup/okhttp/Connection:isAlive	()Z
    //   58: ifeq -42 -> 16
    //   61: invokestatic 203	java/lang/System:nanoTime	()J
    //   64: aload_3
    //   65: invokevirtual 206	com/squareup/okhttp/Connection:getIdleStartTimeNs	()J
    //   68: lsub
    //   69: aload_0
    //   70: getfield 105	com/squareup/okhttp/ConnectionPool:keepAliveDurationNs	J
    //   73: lcmp
    //   74: ifge -58 -> 16
    //   77: aload 5
    //   79: invokeinterface 209 1 0
    //   84: aload_3
    //   85: invokevirtual 212	com/squareup/okhttp/Connection:isFramed	()Z
    //   88: istore_2
    //   89: iload_2
    //   90: ifne +13 -> 103
    //   93: invokestatic 217	com/squareup/okhttp/internal/Platform:get	()Lcom/squareup/okhttp/internal/Platform;
    //   96: aload_3
    //   97: invokevirtual 160	com/squareup/okhttp/Connection:getSocket	()Ljava/net/Socket;
    //   100: invokevirtual 220	com/squareup/okhttp/internal/Platform:tagSocket	(Ljava/net/Socket;)V
    //   103: aload_3
    //   104: ifnull +20 -> 124
    //   107: aload_3
    //   108: invokevirtual 212	com/squareup/okhttp/Connection:isFramed	()Z
    //   111: ifeq +13 -> 124
    //   114: aload_0
    //   115: getfield 68	com/squareup/okhttp/ConnectionPool:connections	Ljava/util/Deque;
    //   118: aload_3
    //   119: invokeinterface 121 2 0
    //   124: aload_0
    //   125: monitorexit
    //   126: aload_3
    //   127: areturn
    //   128: astore 6
    //   130: aload_3
    //   131: invokevirtual 160	com/squareup/okhttp/Connection:getSocket	()Ljava/net/Socket;
    //   134: invokestatic 164	com/squareup/okhttp/internal/Util:closeQuietly	(Ljava/net/Socket;)V
    //   137: invokestatic 217	com/squareup/okhttp/internal/Platform:get	()Lcom/squareup/okhttp/internal/Platform;
    //   140: new 222	java/lang/StringBuilder
    //   143: dup
    //   144: invokespecial 223	java/lang/StringBuilder:<init>	()V
    //   147: ldc -31
    //   149: invokevirtual 229	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   152: aload 6
    //   154: invokevirtual 232	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   157: invokevirtual 236	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   160: invokevirtual 240	com/squareup/okhttp/internal/Platform:logW	(Ljava/lang/String;)V
    //   163: goto -147 -> 16
    //   166: astore_1
    //   167: aload_0
    //   168: monitorexit
    //   169: aload_1
    //   170: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	171	0	this	ConnectionPool
    //   0	171	1	paramAddress	Address
    //   88	2	2	bool	boolean
    //   18	113	3	localObject1	Object
    //   3	14	4	localObject2	Object
    //   14	64	5	localIterator	Iterator
    //   128	25	6	localSocketException	SocketException
    // Exception table:
    //   from	to	target	type
    //   93	103	128	java/net/SocketException
    //   5	16	166	finally
    //   19	89	166	finally
    //   93	103	166	finally
    //   107	124	166	finally
    //   130	163	166	finally
  }
  
  public int getConnectionCount()
  {
    try
    {
      int i = this.connections.size();
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  List<Connection> getConnections()
  {
    try
    {
      ArrayList localArrayList = new ArrayList(this.connections);
      return localArrayList;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public int getHttpConnectionCount()
  {
    try
    {
      int i = this.connections.size();
      int j = getMultiplexedConnectionCount();
      return i - j;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public int getMultiplexedConnectionCount()
  {
    int i = 0;
    try
    {
      Iterator localIterator = this.connections.iterator();
      while (localIterator.hasNext())
      {
        boolean bool = ((Connection)localIterator.next()).isFramed();
        if (bool) {
          i += 1;
        }
      }
      return i;
    }
    finally {}
  }
  
  @Deprecated
  public int getSpdyConnectionCount()
  {
    try
    {
      int i = getMultiplexedConnectionCount();
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  boolean performCleanup()
  {
    long l2;
    long l1;
    Connection localConnection;
    for (;;)
    {
      long l3;
      try
      {
        if (this.connections.isEmpty()) {
          return false;
        }
        ArrayList localArrayList = new ArrayList();
        i = 0;
        l2 = System.nanoTime();
        l1 = this.keepAliveDurationNs;
        localIterator = this.connections.descendingIterator();
        if (!localIterator.hasNext()) {
          break;
        }
        localConnection = (Connection)localIterator.next();
        l3 = localConnection.getIdleStartTimeNs() + this.keepAliveDurationNs - l2;
        if ((l3 <= 0L) || (!localConnection.isAlive()))
        {
          localIterator.remove();
          localArrayList.add(localConnection);
          continue;
        }
        if (!localConnection.isIdle()) {
          continue;
        }
      }
      finally {}
      i += 1;
      l1 = Math.min(l1, l3);
    }
    Iterator localIterator = this.connections.descendingIterator();
    while ((localIterator.hasNext()) && (i > this.maxIdleConnections))
    {
      localConnection = (Connection)localIterator.next();
      if (localConnection.isIdle())
      {
        ((List)localObject).add(localConnection);
        localIterator.remove();
        i -= 1;
      }
    }
    boolean bool = ((List)localObject).isEmpty();
    if (bool) {}
    try
    {
      l2 = l1 / 1000000L;
      wait(l2, (int)(l1 - 1000000L * l2));
      return true;
    }
    catch (InterruptedException localInterruptedException)
    {
      int j;
      for (;;) {}
    }
    int i = 0;
    j = ((List)localObject).size();
    while (i < j)
    {
      Util.closeQuietly(((Connection)((List)localObject).get(i)).getSocket());
      i += 1;
    }
    return true;
  }
  
  void recycle(Connection paramConnection)
  {
    if (paramConnection.isFramed()) {}
    while (!paramConnection.clearOwner()) {
      return;
    }
    if (!paramConnection.isAlive())
    {
      Util.closeQuietly(paramConnection.getSocket());
      return;
    }
    try
    {
      Platform.get().untagSocket(paramConnection.getSocket());
      try
      {
        addConnection(paramConnection);
        paramConnection.incrementRecycleCount();
        paramConnection.resetIdleStartTime();
        return;
      }
      finally {}
      return;
    }
    catch (SocketException localSocketException)
    {
      Platform.get().logW("Unable to untagSocket(): " + localSocketException);
      Util.closeQuietly(paramConnection.getSocket());
    }
  }
  
  void replaceCleanupExecutorForTests(Executor paramExecutor)
  {
    this.executor = paramExecutor;
  }
  
  void share(Connection paramConnection)
  {
    if (!paramConnection.isFramed()) {
      throw new IllegalArgumentException();
    }
    if (!paramConnection.isAlive()) {
      return;
    }
    try
    {
      addConnection(paramConnection);
      return;
    }
    finally {}
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/squareup/okhttp/ConnectionPool.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */