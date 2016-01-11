package com.google.android.gms.analytics.internal;

import android.util.Log;
import com.google.android.gms.analytics.Logger;

@Deprecated
public class zzae
{
  private static volatile Logger zzMk;
  
  static
  {
    setLogger(new zzs());
  }
  
  public static Logger getLogger()
  {
    return zzMk;
  }
  
  public static void setLogger(Logger paramLogger)
  {
    zzMk = paramLogger;
  }
  
  public static boolean zzL(int paramInt)
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (getLogger() != null)
    {
      bool1 = bool2;
      if (getLogger().getLogLevel() <= paramInt) {
        bool1 = true;
      }
    }
    return bool1;
  }
  
  public static void zzaA(String paramString)
  {
    Object localObject = zzaf.zzkc();
    if (localObject != null) {
      ((zzaf)localObject).zzaV(paramString);
    }
    for (;;)
    {
      localObject = zzMk;
      if (localObject != null) {
        ((Logger)localObject).info(paramString);
      }
      return;
      if (zzL(1)) {
        Log.i((String)zzy.zzLb.get(), paramString);
      }
    }
  }
  
  public static void zzaB(String paramString)
  {
    Object localObject = zzaf.zzkc();
    if (localObject != null) {
      ((zzaf)localObject).zzaT(paramString);
    }
    for (;;)
    {
      localObject = zzMk;
      if (localObject != null) {
        ((Logger)localObject).verbose(paramString);
      }
      return;
      if (zzL(0)) {
        Log.v((String)zzy.zzLb.get(), paramString);
      }
    }
  }
  
  public static void zzaC(String paramString)
  {
    Object localObject = zzaf.zzkc();
    if (localObject != null) {
      ((zzaf)localObject).zzaW(paramString);
    }
    for (;;)
    {
      localObject = zzMk;
      if (localObject != null) {
        ((Logger)localObject).warn(paramString);
      }
      return;
      if (zzL(2)) {
        Log.w((String)zzy.zzLb.get(), paramString);
      }
    }
  }
  
  public static void zzf(String paramString, Object paramObject)
  {
    zzaf localzzaf = zzaf.zzkc();
    if (localzzaf != null) {
      localzzaf.zze(paramString, paramObject);
    }
    while (!zzL(3))
    {
      paramObject = zzMk;
      if (paramObject != null) {
        ((Logger)paramObject).error(paramString);
      }
      return;
    }
    if (paramObject != null) {}
    for (paramObject = paramString + ":" + paramObject;; paramObject = paramString)
    {
      Log.e((String)zzy.zzLb.get(), (String)paramObject);
      break;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/analytics/internal/zzae.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */