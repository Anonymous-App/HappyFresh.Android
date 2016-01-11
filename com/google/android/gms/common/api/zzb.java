package com.google.android.gms.common.api;

import com.google.android.gms.common.data.DataHolder;

public abstract class zzb<L>
  implements zzi.zzb<L>
{
  private final DataHolder zzWu;
  
  protected zzb(DataHolder paramDataHolder)
  {
    this.zzWu = paramDataHolder;
  }
  
  protected abstract void zza(L paramL, DataHolder paramDataHolder);
  
  public void zzmw()
  {
    if (this.zzWu != null) {
      this.zzWu.close();
    }
  }
  
  public final void zzn(L paramL)
  {
    zza(paramL, this.zzWu);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/api/zzb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */