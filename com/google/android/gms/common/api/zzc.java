package com.google.android.gms.common.api;

import com.google.android.gms.common.data.DataHolder;

public abstract class zzc
  implements Releasable, Result
{
  protected final Status zzOt;
  protected final DataHolder zzWu;
  
  protected zzc(DataHolder paramDataHolder)
  {
    this(paramDataHolder, new Status(paramDataHolder.getStatusCode()));
  }
  
  protected zzc(DataHolder paramDataHolder, Status paramStatus)
  {
    this.zzOt = paramStatus;
    this.zzWu = paramDataHolder;
  }
  
  public Status getStatus()
  {
    return this.zzOt;
  }
  
  public void release()
  {
    if (this.zzWu != null) {
      this.zzWu.close();
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/api/zzc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */