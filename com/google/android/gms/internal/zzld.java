package com.google.android.gms.internal;

import android.os.SystemClock;

public final class zzld
  implements zzlb
{
  private static zzld zzacK;
  
  public static zzlb zzoQ()
  {
    try
    {
      if (zzacK == null) {
        zzacK = new zzld();
      }
      zzld localzzld = zzacK;
      return localzzld;
    }
    finally {}
  }
  
  public long currentTimeMillis()
  {
    return System.currentTimeMillis();
  }
  
  public long elapsedRealtime()
  {
    return SystemClock.elapsedRealtime();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzld.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */