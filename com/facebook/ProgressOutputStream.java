package com.facebook;

import android.os.Handler;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class ProgressOutputStream
  extends FilterOutputStream
  implements RequestOutputStream
{
  private long batchProgress;
  private RequestProgress currentRequestProgress;
  private long lastReportedProgress;
  private long maxProgress;
  private final Map<GraphRequest, RequestProgress> progressMap;
  private final GraphRequestBatch requests;
  private final long threshold;
  
  ProgressOutputStream(OutputStream paramOutputStream, GraphRequestBatch paramGraphRequestBatch, Map<GraphRequest, RequestProgress> paramMap, long paramLong)
  {
    super(paramOutputStream);
    this.requests = paramGraphRequestBatch;
    this.progressMap = paramMap;
    this.maxProgress = paramLong;
    this.threshold = FacebookSdk.getOnProgressThreshold();
  }
  
  private void addProgress(long paramLong)
  {
    if (this.currentRequestProgress != null) {
      this.currentRequestProgress.addProgress(paramLong);
    }
    this.batchProgress += paramLong;
    if ((this.batchProgress >= this.lastReportedProgress + this.threshold) || (this.batchProgress >= this.maxProgress)) {
      reportBatchProgress();
    }
  }
  
  private void reportBatchProgress()
  {
    if (this.batchProgress > this.lastReportedProgress)
    {
      Iterator localIterator = this.requests.getCallbacks().iterator();
      while (localIterator.hasNext())
      {
        final Object localObject = (GraphRequestBatch.Callback)localIterator.next();
        if ((localObject instanceof GraphRequestBatch.OnProgressCallback))
        {
          Handler localHandler = this.requests.getCallbackHandler();
          localObject = (GraphRequestBatch.OnProgressCallback)localObject;
          if (localHandler == null) {
            ((GraphRequestBatch.OnProgressCallback)localObject).onBatchProgress(this.requests, this.batchProgress, this.maxProgress);
          } else {
            localHandler.post(new Runnable()
            {
              public void run()
              {
                localObject.onBatchProgress(ProgressOutputStream.this.requests, ProgressOutputStream.this.batchProgress, ProgressOutputStream.this.maxProgress);
              }
            });
          }
        }
      }
      this.lastReportedProgress = this.batchProgress;
    }
  }
  
  public void close()
    throws IOException
  {
    super.close();
    Iterator localIterator = this.progressMap.values().iterator();
    while (localIterator.hasNext()) {
      ((RequestProgress)localIterator.next()).reportProgress();
    }
    reportBatchProgress();
  }
  
  long getBatchProgress()
  {
    return this.batchProgress;
  }
  
  long getMaxProgress()
  {
    return this.maxProgress;
  }
  
  public void setCurrentRequest(GraphRequest paramGraphRequest)
  {
    if (paramGraphRequest != null) {}
    for (paramGraphRequest = (RequestProgress)this.progressMap.get(paramGraphRequest);; paramGraphRequest = null)
    {
      this.currentRequestProgress = paramGraphRequest;
      return;
    }
  }
  
  public void write(int paramInt)
    throws IOException
  {
    this.out.write(paramInt);
    addProgress(1L);
  }
  
  public void write(byte[] paramArrayOfByte)
    throws IOException
  {
    this.out.write(paramArrayOfByte);
    addProgress(paramArrayOfByte.length);
  }
  
  public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    this.out.write(paramArrayOfByte, paramInt1, paramInt2);
    addProgress(paramInt2);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/ProgressOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */