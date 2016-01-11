package com.google.android.gms.analytics.internal;

import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.internal.zzlb;

class zzaj
{
  private long zzMC;
  private final zzlb zzpw;
  
  public zzaj(zzlb paramzzlb)
  {
    zzu.zzu(paramzzlb);
    this.zzpw = paramzzlb;
  }
  
  public zzaj(zzlb paramzzlb, long paramLong)
  {
    zzu.zzu(paramzzlb);
    this.zzpw = paramzzlb;
    this.zzMC = paramLong;
  }
  
  public void clear()
  {
    this.zzMC = 0L;
  }
  
  public void start()
  {
    this.zzMC = this.zzpw.elapsedRealtime();
  }
  
  public boolean zzv(long paramLong)
  {
    if (this.zzMC == 0L) {}
    while (this.zzpw.elapsedRealtime() - this.zzMC > paramLong) {
      return true;
    }
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/analytics/internal/zzaj.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */