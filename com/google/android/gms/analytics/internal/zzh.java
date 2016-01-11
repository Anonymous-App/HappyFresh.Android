package com.google.android.gms.analytics.internal;

import com.google.android.gms.common.internal.zzu;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class zzh
{
  private final long zzJS;
  private final String zzJT;
  private final boolean zzJU;
  private long zzJV;
  private final String zzJd;
  private final Map<String, String> zzyn;
  
  public zzh(long paramLong1, String paramString1, String paramString2, boolean paramBoolean, long paramLong2, Map<String, String> paramMap)
  {
    zzu.zzcj(paramString1);
    zzu.zzcj(paramString2);
    this.zzJS = paramLong1;
    this.zzJd = paramString1;
    this.zzJT = paramString2;
    this.zzJU = paramBoolean;
    this.zzJV = paramLong2;
    if (paramMap != null)
    {
      this.zzyn = new HashMap(paramMap);
      return;
    }
    this.zzyn = Collections.emptyMap();
  }
  
  public String getClientId()
  {
    return this.zzJd;
  }
  
  public long zzii()
  {
    return this.zzJS;
  }
  
  public String zzij()
  {
    return this.zzJT;
  }
  
  public boolean zzik()
  {
    return this.zzJU;
  }
  
  public long zzil()
  {
    return this.zzJV;
  }
  
  public Map<String, String> zzn()
  {
    return this.zzyn;
  }
  
  public void zzn(long paramLong)
  {
    this.zzJV = paramLong;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/analytics/internal/zzh.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */