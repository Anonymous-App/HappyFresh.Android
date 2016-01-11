package com.facebook.internal;

import com.facebook.FacebookSdk;
import java.util.concurrent.Executor;

public class WorkQueue
{
  public static final int DEFAULT_MAX_CONCURRENT = 8;
  private final Executor executor;
  private final int maxConcurrent;
  private WorkNode pendingJobs;
  private int runningCount = 0;
  private WorkNode runningJobs = null;
  private final Object workLock = new Object();
  
  static
  {
    if (!WorkQueue.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public WorkQueue()
  {
    this(8);
  }
  
  public WorkQueue(int paramInt)
  {
    this(paramInt, FacebookSdk.getExecutor());
  }
  
  public WorkQueue(int paramInt, Executor paramExecutor)
  {
    this.maxConcurrent = paramInt;
    this.executor = paramExecutor;
  }
  
  private void execute(final WorkNode paramWorkNode)
  {
    this.executor.execute(new Runnable()
    {
      public void run()
      {
        try
        {
          paramWorkNode.getCallback().run();
          return;
        }
        finally
        {
          WorkQueue.this.finishItemAndStartNew(paramWorkNode);
        }
      }
    });
  }
  
  private void finishItemAndStartNew(WorkNode paramWorkNode)
  {
    WorkNode localWorkNode = null;
    Object localObject = this.workLock;
    if (paramWorkNode != null) {}
    try
    {
      this.runningJobs = paramWorkNode.removeFromList(this.runningJobs);
      this.runningCount -= 1;
      paramWorkNode = localWorkNode;
      if (this.runningCount < this.maxConcurrent)
      {
        localWorkNode = this.pendingJobs;
        paramWorkNode = localWorkNode;
        if (localWorkNode != null)
        {
          this.pendingJobs = localWorkNode.removeFromList(this.pendingJobs);
          this.runningJobs = localWorkNode.addToList(this.runningJobs, false);
          this.runningCount += 1;
          localWorkNode.setIsRunning(true);
          paramWorkNode = localWorkNode;
        }
      }
      if (paramWorkNode != null) {
        execute(paramWorkNode);
      }
      return;
    }
    finally {}
  }
  
  private void startItem()
  {
    finishItemAndStartNew(null);
  }
  
  public WorkItem addActiveWorkItem(Runnable paramRunnable)
  {
    return addActiveWorkItem(paramRunnable, true);
  }
  
  public WorkItem addActiveWorkItem(Runnable arg1, boolean paramBoolean)
  {
    WorkNode localWorkNode = new WorkNode(???);
    synchronized (this.workLock)
    {
      this.pendingJobs = localWorkNode.addToList(this.pendingJobs, paramBoolean);
      startItem();
      return localWorkNode;
    }
  }
  
  public void validate()
  {
    Object localObject3 = this.workLock;
    int j = 0;
    int i = 0;
    try
    {
      if (this.runningJobs != null)
      {
        Object localObject1 = this.runningJobs;
        WorkNode localWorkNode;
        do
        {
          ((WorkNode)localObject1).verify(true);
          j = i + 1;
          localWorkNode = ((WorkNode)localObject1).getNext();
          i = j;
          localObject1 = localWorkNode;
        } while (localWorkNode != this.runningJobs);
      }
      if ((!$assertionsDisabled) && (this.runningCount != j)) {
        throw new AssertionError();
      }
    }
    finally {}
  }
  
  public static abstract interface WorkItem
  {
    public abstract boolean cancel();
    
    public abstract boolean isRunning();
    
    public abstract void moveToFront();
  }
  
  private class WorkNode
    implements WorkQueue.WorkItem
  {
    private final Runnable callback;
    private boolean isRunning;
    private WorkNode next;
    private WorkNode prev;
    
    static
    {
      if (!WorkQueue.class.desiredAssertionStatus()) {}
      for (boolean bool = true;; bool = false)
      {
        $assertionsDisabled = bool;
        return;
      }
    }
    
    WorkNode(Runnable paramRunnable)
    {
      this.callback = paramRunnable;
    }
    
    WorkNode addToList(WorkNode paramWorkNode, boolean paramBoolean)
    {
      assert (this.next == null);
      assert (this.prev == null);
      if (paramWorkNode == null)
      {
        this.prev = this;
        this.next = this;
        paramWorkNode = this;
      }
      while (paramBoolean)
      {
        return this;
        this.next = paramWorkNode;
        this.prev = paramWorkNode.prev;
        WorkNode localWorkNode = this.next;
        this.prev.next = this;
        localWorkNode.prev = this;
      }
      return paramWorkNode;
    }
    
    public boolean cancel()
    {
      synchronized (WorkQueue.this.workLock)
      {
        if (!isRunning())
        {
          WorkQueue.access$202(WorkQueue.this, removeFromList(WorkQueue.this.pendingJobs));
          return true;
        }
        return false;
      }
    }
    
    Runnable getCallback()
    {
      return this.callback;
    }
    
    WorkNode getNext()
    {
      return this.next;
    }
    
    public boolean isRunning()
    {
      return this.isRunning;
    }
    
    public void moveToFront()
    {
      synchronized (WorkQueue.this.workLock)
      {
        if (!isRunning())
        {
          WorkQueue.access$202(WorkQueue.this, removeFromList(WorkQueue.this.pendingJobs));
          WorkQueue.access$202(WorkQueue.this, addToList(WorkQueue.this.pendingJobs, true));
        }
        return;
      }
    }
    
    WorkNode removeFromList(WorkNode paramWorkNode)
    {
      assert (this.next != null);
      assert (this.prev != null);
      WorkNode localWorkNode = paramWorkNode;
      if (paramWorkNode == this) {
        if (this.next != this) {
          break label93;
        }
      }
      label93:
      for (localWorkNode = null;; localWorkNode = this.next)
      {
        this.next.prev = this.prev;
        this.prev.next = this.next;
        this.prev = null;
        this.next = null;
        return localWorkNode;
      }
    }
    
    void setIsRunning(boolean paramBoolean)
    {
      this.isRunning = paramBoolean;
    }
    
    void verify(boolean paramBoolean)
    {
      assert (this.prev.next == this);
      assert (this.next.prev == this);
      assert (isRunning() == paramBoolean);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/internal/WorkQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */