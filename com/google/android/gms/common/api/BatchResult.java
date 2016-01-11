package com.google.android.gms.common.api;

import com.google.android.gms.common.internal.zzu;
import java.util.concurrent.TimeUnit;

public final class BatchResult
  implements Result
{
  private final Status zzOt;
  private final PendingResult<?>[] zzWq;
  
  BatchResult(Status paramStatus, PendingResult<?>[] paramArrayOfPendingResult)
  {
    this.zzOt = paramStatus;
    this.zzWq = paramArrayOfPendingResult;
  }
  
  public Status getStatus()
  {
    return this.zzOt;
  }
  
  public <R extends Result> R take(BatchResultToken<R> paramBatchResultToken)
  {
    if (paramBatchResultToken.mId < this.zzWq.length) {}
    for (boolean bool = true;; bool = false)
    {
      zzu.zzb(bool, "The result token does not belong to this batch");
      return this.zzWq[paramBatchResultToken.mId].await(0L, TimeUnit.MILLISECONDS);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/api/BatchResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */