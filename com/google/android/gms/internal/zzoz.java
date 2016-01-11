package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import com.google.android.gms.common.internal.zze;
import com.google.android.gms.playlog.internal.LogEvent;
import com.google.android.gms.playlog.internal.PlayLoggerContext;
import com.google.android.gms.playlog.internal.zzd;
import com.google.android.gms.playlog.internal.zzf;

@Deprecated
public class zzoz
{
  private final zzf zzaGD;
  private PlayLoggerContext zzaGE;
  
  public zzoz(Context paramContext, int paramInt, String paramString1, String paramString2, zza paramzza, boolean paramBoolean, String paramString3)
  {
    String str = paramContext.getPackageName();
    int i = 0;
    try
    {
      int j = paramContext.getPackageManager().getPackageInfo(str, 0).versionCode;
      i = j;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      for (;;)
      {
        Log.wtf("PlayLogger", "This can't happen.");
      }
    }
    this.zzaGE = new PlayLoggerContext(str, i, paramInt, paramString1, paramString2, paramBoolean);
    this.zzaGD = new zzf(paramContext, paramContext.getMainLooper(), new zzd(paramzza), new zze(null, null, null, 49, null, str, paramString3, null));
  }
  
  public void start()
  {
    this.zzaGD.start();
  }
  
  public void stop()
  {
    this.zzaGD.stop();
  }
  
  public void zza(long paramLong, String paramString, byte[] paramArrayOfByte, String... paramVarArgs)
  {
    this.zzaGD.zzb(this.zzaGE, new LogEvent(paramLong, paramString, paramArrayOfByte, paramVarArgs));
  }
  
  public void zzb(String paramString, byte[] paramArrayOfByte, String... paramVarArgs)
  {
    zza(System.currentTimeMillis(), paramString, paramArrayOfByte, paramVarArgs);
  }
  
  public static abstract interface zza
  {
    public abstract void zzf(PendingIntent paramPendingIntent);
    
    public abstract void zzxl();
    
    public abstract void zzxm();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzoz.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */