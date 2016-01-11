package com.google.android.gms.playlog.internal;

import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.internal.zzrr.zzd;
import java.util.ArrayList;

public class zzb
{
  private final ArrayList<zza> zzaGI = new ArrayList();
  private int zzaGJ;
  
  public zzb()
  {
    this(100);
  }
  
  public zzb(int paramInt)
  {
    this.zzaGJ = paramInt;
  }
  
  private void zzxo()
  {
    while (getSize() > getCapacity()) {
      this.zzaGI.remove(0);
    }
  }
  
  public void clear()
  {
    this.zzaGI.clear();
  }
  
  public int getCapacity()
  {
    return this.zzaGJ;
  }
  
  public int getSize()
  {
    return this.zzaGI.size();
  }
  
  public boolean isEmpty()
  {
    return this.zzaGI.isEmpty();
  }
  
  public void zza(PlayLoggerContext paramPlayLoggerContext, LogEvent paramLogEvent)
  {
    this.zzaGI.add(new zza(paramPlayLoggerContext, paramLogEvent, null));
    zzxo();
  }
  
  public ArrayList<zza> zzxn()
  {
    return this.zzaGI;
  }
  
  public static class zza
  {
    public final PlayLoggerContext zzaGK;
    public final LogEvent zzaGL;
    public final zzrr.zzd zzaGM;
    
    private zza(PlayLoggerContext paramPlayLoggerContext, LogEvent paramLogEvent)
    {
      this.zzaGK = ((PlayLoggerContext)zzu.zzu(paramPlayLoggerContext));
      this.zzaGL = ((LogEvent)zzu.zzu(paramLogEvent));
      this.zzaGM = null;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/playlog/internal/zzb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */