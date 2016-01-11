package com.google.android.gms.common.api;

import android.os.Looper;
import java.util.ArrayList;
import java.util.List;

public final class Batch
  extends AbstractPendingResult<BatchResult>
{
  private int zzWn;
  private boolean zzWo;
  private boolean zzWp;
  private final PendingResult<?>[] zzWq;
  private final Object zzqt = new Object();
  
  private Batch(List<PendingResult<?>> paramList, Looper paramLooper)
  {
    super(new AbstractPendingResult.CallbackHandler(paramLooper));
    this.zzWn = paramList.size();
    this.zzWq = new PendingResult[this.zzWn];
    int i = 0;
    while (i < paramList.size())
    {
      paramLooper = (PendingResult)paramList.get(i);
      this.zzWq[i] = paramLooper;
      paramLooper.addBatchCallback(new PendingResult.BatchCallback()
      {
        public void zzs(Status paramAnonymousStatus)
        {
          for (;;)
          {
            synchronized (Batch.zza(Batch.this))
            {
              if (Batch.this.isCanceled()) {
                return;
              }
              if (paramAnonymousStatus.isCanceled())
              {
                Batch.zza(Batch.this, true);
                Batch.zzb(Batch.this);
                if (Batch.zzc(Batch.this) == 0)
                {
                  if (!Batch.zzd(Batch.this)) {
                    break;
                  }
                  Batch.zze(Batch.this);
                }
                return;
              }
            }
            if (!paramAnonymousStatus.isSuccess()) {
              Batch.zzb(Batch.this, true);
            }
          }
          if (Batch.zzf(Batch.this)) {}
          for (paramAnonymousStatus = new Status(13);; paramAnonymousStatus = Status.zzXP)
          {
            Batch.this.setResult(new BatchResult(paramAnonymousStatus, Batch.zzg(Batch.this)));
            break;
          }
        }
      });
      i += 1;
    }
  }
  
  public void cancel()
  {
    super.cancel();
    PendingResult[] arrayOfPendingResult = this.zzWq;
    int j = arrayOfPendingResult.length;
    int i = 0;
    while (i < j)
    {
      arrayOfPendingResult[i].cancel();
      i += 1;
    }
  }
  
  public BatchResult createFailedResult(Status paramStatus)
  {
    return new BatchResult(paramStatus, this.zzWq);
  }
  
  public static final class Builder
  {
    private List<PendingResult<?>> zzWs = new ArrayList();
    private Looper zzWt;
    
    public Builder(GoogleApiClient paramGoogleApiClient)
    {
      this.zzWt = paramGoogleApiClient.getLooper();
    }
    
    public <R extends Result> BatchResultToken<R> add(PendingResult<R> paramPendingResult)
    {
      BatchResultToken localBatchResultToken = new BatchResultToken(this.zzWs.size());
      this.zzWs.add(paramPendingResult);
      return localBatchResultToken;
    }
    
    public Batch build()
    {
      return new Batch(this.zzWs, this.zzWt, null);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/api/Batch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */