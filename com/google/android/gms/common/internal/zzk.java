package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;

public abstract class zzk
{
  private static final Object zzaaJ = new Object();
  private static zzk zzaaK;
  
  public static zzk zzah(Context paramContext)
  {
    synchronized (zzaaJ)
    {
      if (zzaaK == null) {
        zzaaK = new zzl(paramContext.getApplicationContext());
      }
      return zzaaK;
    }
  }
  
  public abstract boolean zza(ComponentName paramComponentName, ServiceConnection paramServiceConnection, String paramString);
  
  public abstract boolean zza(String paramString1, ServiceConnection paramServiceConnection, String paramString2);
  
  public abstract void zzb(ComponentName paramComponentName, ServiceConnection paramServiceConnection, String paramString);
  
  public abstract void zzb(String paramString1, ServiceConnection paramServiceConnection, String paramString2);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/internal/zzk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */