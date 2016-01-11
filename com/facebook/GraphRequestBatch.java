package com.facebook;

import android.os.Handler;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GraphRequestBatch
  extends AbstractList<GraphRequest>
{
  private static AtomicInteger idGenerator = new AtomicInteger();
  private String batchApplicationId;
  private Handler callbackHandler;
  private List<Callback> callbacks = new ArrayList();
  private final String id = Integer.valueOf(idGenerator.incrementAndGet()).toString();
  private List<GraphRequest> requests = new ArrayList();
  private int timeoutInMilliseconds = 0;
  
  public GraphRequestBatch()
  {
    this.requests = new ArrayList();
  }
  
  public GraphRequestBatch(GraphRequestBatch paramGraphRequestBatch)
  {
    this.requests = new ArrayList(paramGraphRequestBatch);
    this.callbackHandler = paramGraphRequestBatch.callbackHandler;
    this.timeoutInMilliseconds = paramGraphRequestBatch.timeoutInMilliseconds;
    this.callbacks = new ArrayList(paramGraphRequestBatch.callbacks);
  }
  
  public GraphRequestBatch(Collection<GraphRequest> paramCollection)
  {
    this.requests = new ArrayList(paramCollection);
  }
  
  public GraphRequestBatch(GraphRequest... paramVarArgs)
  {
    this.requests = Arrays.asList(paramVarArgs);
  }
  
  public final void add(int paramInt, GraphRequest paramGraphRequest)
  {
    this.requests.add(paramInt, paramGraphRequest);
  }
  
  public final boolean add(GraphRequest paramGraphRequest)
  {
    return this.requests.add(paramGraphRequest);
  }
  
  public void addCallback(Callback paramCallback)
  {
    if (!this.callbacks.contains(paramCallback)) {
      this.callbacks.add(paramCallback);
    }
  }
  
  public final void clear()
  {
    this.requests.clear();
  }
  
  public final List<GraphResponse> executeAndWait()
  {
    return executeAndWaitImpl();
  }
  
  List<GraphResponse> executeAndWaitImpl()
  {
    return GraphRequest.executeBatchAndWait(this);
  }
  
  public final GraphRequestAsyncTask executeAsync()
  {
    return executeAsyncImpl();
  }
  
  GraphRequestAsyncTask executeAsyncImpl()
  {
    return GraphRequest.executeBatchAsync(this);
  }
  
  public final GraphRequest get(int paramInt)
  {
    return (GraphRequest)this.requests.get(paramInt);
  }
  
  public final String getBatchApplicationId()
  {
    return this.batchApplicationId;
  }
  
  final Handler getCallbackHandler()
  {
    return this.callbackHandler;
  }
  
  final List<Callback> getCallbacks()
  {
    return this.callbacks;
  }
  
  final String getId()
  {
    return this.id;
  }
  
  final List<GraphRequest> getRequests()
  {
    return this.requests;
  }
  
  public int getTimeout()
  {
    return this.timeoutInMilliseconds;
  }
  
  public final GraphRequest remove(int paramInt)
  {
    return (GraphRequest)this.requests.remove(paramInt);
  }
  
  public void removeCallback(Callback paramCallback)
  {
    this.callbacks.remove(paramCallback);
  }
  
  public final GraphRequest set(int paramInt, GraphRequest paramGraphRequest)
  {
    return (GraphRequest)this.requests.set(paramInt, paramGraphRequest);
  }
  
  public final void setBatchApplicationId(String paramString)
  {
    this.batchApplicationId = paramString;
  }
  
  final void setCallbackHandler(Handler paramHandler)
  {
    this.callbackHandler = paramHandler;
  }
  
  public void setTimeout(int paramInt)
  {
    if (paramInt < 0) {
      throw new IllegalArgumentException("Argument timeoutInMilliseconds must be >= 0.");
    }
    this.timeoutInMilliseconds = paramInt;
  }
  
  public final int size()
  {
    return this.requests.size();
  }
  
  public static abstract interface Callback
  {
    public abstract void onBatchCompleted(GraphRequestBatch paramGraphRequestBatch);
  }
  
  public static abstract interface OnProgressCallback
    extends GraphRequestBatch.Callback
  {
    public abstract void onBatchProgress(GraphRequestBatch paramGraphRequestBatch, long paramLong1, long paramLong2);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/GraphRequestBatch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */