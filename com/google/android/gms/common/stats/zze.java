package com.google.android.gms.common.stats;

import android.os.SystemClock;
import android.support.v4.util.SimpleArrayMap;
import android.util.Log;

public class zze
{
  private final long zzacG;
  private final int zzacH;
  private final SimpleArrayMap<String, Long> zzacI;
  
  public zze()
  {
    this.zzacG = 60000L;
    this.zzacH = 10;
    this.zzacI = new SimpleArrayMap(10);
  }
  
  public zze(int paramInt, long paramLong)
  {
    this.zzacG = paramLong;
    this.zzacH = paramInt;
    this.zzacI = new SimpleArrayMap();
  }
  
  private void zzc(long paramLong1, long paramLong2)
  {
    int i = this.zzacI.size() - 1;
    while (i >= 0)
    {
      if (paramLong2 - ((Long)this.zzacI.valueAt(i)).longValue() > paramLong1) {
        this.zzacI.removeAt(i);
      }
      i -= 1;
    }
  }
  
  public Long zzcp(String paramString)
  {
    long l2 = SystemClock.elapsedRealtime();
    long l1 = this.zzacG;
    try
    {
      while (this.zzacI.size() >= this.zzacH)
      {
        zzc(l1, l2);
        l1 /= 2L;
        Log.w("ConnectionTracker", "The max capacity " + this.zzacH + " is not enough. Current durationThreshold is: " + l1);
      }
      paramString = (Long)this.zzacI.put(paramString, Long.valueOf(l2));
    }
    finally {}
    return paramString;
  }
  
  public boolean zzcq(String paramString)
  {
    for (;;)
    {
      try
      {
        if (this.zzacI.remove(paramString) != null)
        {
          bool = true;
          return bool;
        }
      }
      finally {}
      boolean bool = false;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/stats/zze.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */