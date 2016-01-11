package com.google.android.gms.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

public class zzqe
  implements Result
{
  private final zza zzaPo;
  
  public zzqe(zza paramzza)
  {
    this.zzaPo = paramzza;
  }
  
  public Status getStatus()
  {
    return this.zzaPo.getStatus();
  }
  
  public zza zzAg()
  {
    return this.zzaPo;
  }
  
  public static class zza
  {
    private final Status zzOt;
    private final zza zzaPp;
    private final byte[] zzaPq;
    private final long zzaPr;
    private final zzpy zzaPs;
    private final zzqf.zzc zzaPt;
    
    public zza(Status paramStatus, zzpy paramzzpy, zza paramzza)
    {
      this(paramStatus, paramzzpy, null, null, paramzza, 0L);
    }
    
    public zza(Status paramStatus, zzpy paramzzpy, byte[] paramArrayOfByte, zzqf.zzc paramzzc, zza paramzza, long paramLong)
    {
      this.zzOt = paramStatus;
      this.zzaPs = paramzzpy;
      this.zzaPq = paramArrayOfByte;
      this.zzaPt = paramzzc;
      this.zzaPp = paramzza;
      this.zzaPr = paramLong;
    }
    
    public Status getStatus()
    {
      return this.zzOt;
    }
    
    public zza zzAh()
    {
      return this.zzaPp;
    }
    
    public byte[] zzAi()
    {
      return this.zzaPq;
    }
    
    public zzpy zzAj()
    {
      return this.zzaPs;
    }
    
    public zzqf.zzc zzAk()
    {
      return this.zzaPt;
    }
    
    public long zzAl()
    {
      return this.zzaPr;
    }
    
    public static enum zza
    {
      private zza() {}
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzqe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */