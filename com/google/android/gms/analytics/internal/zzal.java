package com.google.android.gms.analytics.internal;

import android.app.Activity;
import java.util.HashMap;
import java.util.Map;

public class zzal
  implements zzp
{
  public double zzME = -1.0D;
  public int zzMF = -1;
  public int zzMG = -1;
  public int zzMH = -1;
  public int zzMI = -1;
  public Map<String, String> zzMJ = new HashMap();
  public String zztd;
  
  public int getSessionTimeout()
  {
    return this.zzMF;
  }
  
  public String getTrackingId()
  {
    return this.zztd;
  }
  
  public String zzbh(String paramString)
  {
    String str = (String)this.zzMJ.get(paramString);
    if (str != null) {
      return str;
    }
    return paramString;
  }
  
  public double zzkA()
  {
    return this.zzME;
  }
  
  public boolean zzkB()
  {
    return this.zzMF >= 0;
  }
  
  public boolean zzkC()
  {
    return this.zzMG != -1;
  }
  
  public boolean zzkD()
  {
    return this.zzMG == 1;
  }
  
  public boolean zzkE()
  {
    return this.zzMH != -1;
  }
  
  public boolean zzkF()
  {
    return this.zzMH == 1;
  }
  
  public boolean zzkG()
  {
    return this.zzMI == 1;
  }
  
  public boolean zzky()
  {
    return this.zztd != null;
  }
  
  public boolean zzkz()
  {
    return this.zzME >= 0.0D;
  }
  
  public String zzq(Activity paramActivity)
  {
    return zzbh(paramActivity.getClass().getCanonicalName());
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/analytics/internal/zzal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */