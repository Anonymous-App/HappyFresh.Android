package com.google.android.gms.common.api;

import android.os.Looper;
import com.google.android.gms.common.internal.zzu;

public final class PendingResults
{
  public static PendingResult<Status> canceledPendingResult()
  {
    zzl localzzl = new zzl(Looper.getMainLooper());
    localzzl.cancel();
    return localzzl;
  }
  
  public static <R extends Result> PendingResult<R> canceledPendingResult(R paramR)
  {
    zzu.zzb(paramR, "Result must not be null");
    if (paramR.getStatus().getStatusCode() == 16) {}
    for (boolean bool = true;; bool = false)
    {
      zzu.zzb(bool, "Status code must be CommonStatusCodes.CANCELED");
      paramR = new zza(paramR);
      paramR.cancel();
      return paramR;
    }
  }
  
  public static <R extends Result> PendingResult<R> immediatePendingResult(R paramR)
  {
    zzu.zzb(paramR, "Result must not be null");
    zzb localzzb = new zzb();
    localzzb.setResult(paramR);
    return localzzb;
  }
  
  public static PendingResult<Status> immediatePendingResult(Status paramStatus)
  {
    zzu.zzb(paramStatus, "Result must not be null");
    zzl localzzl = new zzl(Looper.getMainLooper());
    localzzl.setResult(paramStatus);
    return localzzl;
  }
  
  private static final class zza<R extends Result>
    extends AbstractPendingResult<R>
  {
    private final R zzXN;
    
    public zza(R paramR)
    {
      super();
      this.zzXN = paramR;
    }
    
    protected R createFailedResult(Status paramStatus)
    {
      if (paramStatus.getStatusCode() != this.zzXN.getStatus().getStatusCode()) {
        throw new UnsupportedOperationException("Creating failed results is not supported");
      }
      return this.zzXN;
    }
  }
  
  private static final class zzb<R extends Result>
    extends AbstractPendingResult<R>
  {
    public zzb()
    {
      super();
    }
    
    protected R createFailedResult(Status paramStatus)
    {
      throw new UnsupportedOperationException("Creating failed results is not supported");
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/api/PendingResults.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */