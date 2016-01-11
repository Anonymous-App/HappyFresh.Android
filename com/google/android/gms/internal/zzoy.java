package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.util.Log;

@Deprecated
public class zzoy
  implements zzoz.zza
{
  private final zzoz zzaGB;
  private boolean zzaGC;
  
  public zzoy(Context paramContext, int paramInt)
  {
    this(paramContext, paramInt, null);
  }
  
  public zzoy(Context paramContext, int paramInt, String paramString)
  {
    this(paramContext, paramInt, paramString, null, true);
  }
  
  public zzoy(Context paramContext, int paramInt, String paramString1, String paramString2, boolean paramBoolean)
  {
    if (paramContext != paramContext.getApplicationContext()) {}
    for (String str = paramContext.getClass().getName();; str = "OneTimePlayLogger")
    {
      this.zzaGB = new zzoz(paramContext, paramInt, paramString1, paramString2, this, paramBoolean, str);
      this.zzaGC = true;
      return;
    }
  }
  
  private void zzxk()
  {
    if (!this.zzaGC) {
      throw new IllegalStateException("Cannot reuse one-time logger after sending.");
    }
  }
  
  public void send()
  {
    zzxk();
    this.zzaGB.start();
    this.zzaGC = false;
  }
  
  public void zza(String paramString, byte[] paramArrayOfByte, String... paramVarArgs)
  {
    zzxk();
    this.zzaGB.zzb(paramString, paramArrayOfByte, paramVarArgs);
  }
  
  public void zzf(PendingIntent paramPendingIntent)
  {
    Log.w("OneTimePlayLogger", "logger connection failed: " + paramPendingIntent);
  }
  
  public void zzxl()
  {
    this.zzaGB.stop();
  }
  
  public void zzxm()
  {
    Log.w("OneTimePlayLogger", "logger connection failed");
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzoy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */