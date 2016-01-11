package com.squareup.okhttp.internal.allocations;

import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.internal.Internal;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public final class Connection
{
  private int allocationLimit = 1;
  private final List<StreamAllocationReference> allocations = new ArrayList();
  long idleAt = Long.MAX_VALUE;
  private boolean noNewAllocations;
  private final ConnectionPool pool;
  
  public Connection(ConnectionPool paramConnectionPool)
  {
    this.pool = paramConnectionPool;
  }
  
  private void remove(StreamAllocation paramStreamAllocation)
  {
    int i = 0;
    int j = this.allocations.size();
    while (i < j)
    {
      if (((StreamAllocationReference)this.allocations.get(i)).get() == paramStreamAllocation)
      {
        this.allocations.remove(i);
        if (this.allocations.isEmpty()) {
          this.idleAt = System.nanoTime();
        }
        return;
      }
      i += 1;
    }
    throw new IllegalArgumentException("unexpected allocation: " + paramStreamAllocation);
  }
  
  public void noNewStreams()
  {
    synchronized (this.pool)
    {
      this.noNewAllocations = true;
      int i = 0;
      while (i < this.allocations.size())
      {
        ((StreamAllocationReference)this.allocations.get(i)).rescind();
        i += 1;
      }
      return;
    }
  }
  
  public void pruneLeakedAllocations()
  {
    synchronized (this.pool)
    {
      Iterator localIterator = this.allocations.iterator();
      while (localIterator.hasNext())
      {
        StreamAllocationReference localStreamAllocationReference = (StreamAllocationReference)localIterator.next();
        if (localStreamAllocationReference.get() == null)
        {
          Internal.logger.warning("Call " + localStreamAllocationReference.name + " leaked a connection. Did you forget to close a response body?");
          this.noNewAllocations = true;
          localIterator.remove();
          if (this.allocations.isEmpty()) {
            this.idleAt = System.nanoTime();
          }
        }
      }
    }
  }
  
  public void release(StreamAllocation paramStreamAllocation)
  {
    synchronized (this.pool)
    {
      if (paramStreamAllocation.released) {
        throw new IllegalStateException("already released");
      }
    }
    StreamAllocation.access$102(paramStreamAllocation, true);
    if (paramStreamAllocation.stream == null) {
      remove(paramStreamAllocation);
    }
  }
  
  public StreamAllocation reserve(String paramString)
  {
    synchronized (this.pool)
    {
      if ((this.noNewAllocations) || (this.allocations.size() >= this.allocationLimit)) {
        return null;
      }
      StreamAllocation localStreamAllocation = new StreamAllocation(null);
      this.allocations.add(new StreamAllocationReference(localStreamAllocation, paramString));
      return localStreamAllocation;
    }
  }
  
  public void setAllocationLimit(int paramInt)
  {
    ConnectionPool localConnectionPool = this.pool;
    if (paramInt < 0) {
      try
      {
        throw new IllegalArgumentException();
      }
      finally {}
    }
    this.allocationLimit = paramInt;
    while (paramInt < this.allocations.size())
    {
      ((StreamAllocationReference)this.allocations.get(paramInt)).rescind();
      paramInt += 1;
    }
  }
  
  int size()
  {
    synchronized (this.pool)
    {
      int i = this.allocations.size();
      return i;
    }
  }
  
  public static class Stream
  {
    public final String name;
    
    public Stream(String paramString)
    {
      this.name = paramString;
    }
    
    public String toString()
    {
      return this.name;
    }
  }
  
  public final class StreamAllocation
  {
    private boolean released;
    private boolean rescinded;
    private Connection.Stream stream;
    
    private StreamAllocation() {}
    
    public Connection.Stream newStream(String paramString)
    {
      synchronized (Connection.this.pool)
      {
        if ((this.stream != null) || (this.released)) {
          throw new IllegalStateException();
        }
      }
      return paramString;
    }
    
    public void streamComplete(Connection.Stream paramStream)
    {
      ConnectionPool localConnectionPool = Connection.this.pool;
      if (paramStream != null) {}
      try
      {
        if (paramStream != this.stream) {
          throw new IllegalArgumentException();
        }
      }
      finally
      {
        throw paramStream;
        this.stream = null;
        if (!this.released) {}
      }
    }
  }
  
  private static final class StreamAllocationReference
    extends WeakReference<Connection.StreamAllocation>
  {
    private final String name;
    
    public StreamAllocationReference(Connection.StreamAllocation paramStreamAllocation, String paramString)
    {
      super();
      this.name = paramString;
    }
    
    public void rescind()
    {
      Connection.StreamAllocation localStreamAllocation = (Connection.StreamAllocation)get();
      if (localStreamAllocation != null) {
        Connection.StreamAllocation.access$602(localStreamAllocation, true);
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/squareup/okhttp/internal/allocations/Connection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */