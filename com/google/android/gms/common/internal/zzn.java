package com.google.android.gms.common.internal;

import android.content.Context;
import android.util.Log;
import com.google.android.gms.internal.zzoy;

public final class zzn
{
  public static final int zzaaX = 23 - " PII_LOG".length();
  private static final String zzaaY = null;
  private final String zzaaZ;
  private final String zzaba;
  
  public zzn(String paramString)
  {
    this(paramString, zzaaY);
  }
  
  public zzn(String paramString1, String paramString2)
  {
    zzu.zzb(paramString1, "log tag cannot be null");
    if (paramString1.length() <= 23) {}
    for (boolean bool = true;; bool = false)
    {
      zzu.zzb(bool, "tag \"%s\" is longer than the %d character maximum", new Object[] { paramString1, Integer.valueOf(23) });
      this.zzaaZ = paramString1;
      if ((paramString2 != null) && (paramString2.length() > 0)) {
        break;
      }
      this.zzaba = zzaaY;
      return;
    }
    this.zzaba = paramString2;
  }
  
  private String zzch(String paramString)
  {
    if (this.zzaba == null) {
      return paramString;
    }
    return this.zzaba.concat(paramString);
  }
  
  public void zza(Context paramContext, String paramString1, String paramString2, Throwable paramThrowable)
  {
    StackTraceElement[] arrayOfStackTraceElement = paramThrowable.getStackTrace();
    StringBuilder localStringBuilder = new StringBuilder();
    int i = 0;
    while ((i < arrayOfStackTraceElement.length) && (i < 2))
    {
      localStringBuilder.append(arrayOfStackTraceElement[i].toString());
      localStringBuilder.append("\n");
      i += 1;
    }
    paramContext = new zzoy(paramContext, 10);
    paramContext.zza("GMS_WTF", null, new String[] { "GMS_WTF", localStringBuilder.toString() });
    paramContext.send();
    if (zzbv(7))
    {
      Log.e(paramString1, zzch(paramString2), paramThrowable);
      Log.wtf(paramString1, zzch(paramString2), paramThrowable);
    }
  }
  
  public void zza(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if (zzbv(4)) {
      Log.i(paramString1, zzch(paramString2), paramThrowable);
    }
  }
  
  public void zzb(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if (zzbv(5)) {
      Log.w(paramString1, zzch(paramString2), paramThrowable);
    }
  }
  
  public boolean zzbv(int paramInt)
  {
    return Log.isLoggable(this.zzaaZ, paramInt);
  }
  
  public void zzc(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if (zzbv(6)) {
      Log.e(paramString1, zzch(paramString2), paramThrowable);
    }
  }
  
  public void zzt(String paramString1, String paramString2)
  {
    if (zzbv(3)) {
      Log.d(paramString1, zzch(paramString2));
    }
  }
  
  public void zzu(String paramString1, String paramString2)
  {
    if (zzbv(5)) {
      Log.w(paramString1, zzch(paramString2));
    }
  }
  
  public void zzv(String paramString1, String paramString2)
  {
    if (zzbv(6)) {
      Log.e(paramString1, zzch(paramString2));
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/internal/zzn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */