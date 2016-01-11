package com.google.android.gms.internal;

import java.io.IOException;

public abstract interface zzpx
{
  public static final class zza
    extends zzrh<zza>
  {
    public long zzaOZ;
    public zzaf.zzj zzaPa;
    public zzaf.zzf zziO;
    
    public zza()
    {
      zzzY();
    }
    
    public static zza zzs(byte[] paramArrayOfByte)
      throws zzrm
    {
      return (zza)zzrn.zza(new zza(), paramArrayOfByte);
    }
    
    public boolean equals(Object paramObject)
    {
      boolean bool2 = false;
      boolean bool1;
      if (paramObject == this) {
        bool1 = true;
      }
      do
      {
        do
        {
          do
          {
            do
            {
              return bool1;
              bool1 = bool2;
            } while (!(paramObject instanceof zza));
            paramObject = (zza)paramObject;
            bool1 = bool2;
          } while (this.zzaOZ != ((zza)paramObject).zzaOZ);
          if (this.zziO != null) {
            break;
          }
          bool1 = bool2;
        } while (((zza)paramObject).zziO != null);
        if (this.zzaPa != null) {
          break label93;
        }
        bool1 = bool2;
      } while (((zza)paramObject).zzaPa != null);
      label93:
      while (this.zzaPa.equals(((zza)paramObject).zzaPa))
      {
        return zza((zzrh)paramObject);
        if (this.zziO.equals(((zza)paramObject).zziO)) {
          break;
        }
        return false;
      }
      return false;
    }
    
    public int hashCode()
    {
      int j = 0;
      int k = (int)(this.zzaOZ ^ this.zzaOZ >>> 32);
      int i;
      if (this.zziO == null)
      {
        i = 0;
        if (this.zzaPa != null) {
          break label67;
        }
      }
      for (;;)
      {
        return ((i + (k + 527) * 31) * 31 + j) * 31 + zzBI();
        i = this.zziO.hashCode();
        break;
        label67:
        j = this.zzaPa.hashCode();
      }
    }
    
    protected int zzB()
    {
      int j = super.zzB() + zzrg.zzd(1, this.zzaOZ);
      int i = j;
      if (this.zziO != null) {
        i = j + zzrg.zzc(2, this.zziO);
      }
      j = i;
      if (this.zzaPa != null) {
        j = i + zzrg.zzc(3, this.zzaPa);
      }
      return j;
    }
    
    public void zza(zzrg paramzzrg)
      throws IOException
    {
      paramzzrg.zzb(1, this.zzaOZ);
      if (this.zziO != null) {
        paramzzrg.zza(2, this.zziO);
      }
      if (this.zzaPa != null) {
        paramzzrg.zza(3, this.zzaPa);
      }
      super.zza(paramzzrg);
    }
    
    public zza zzu(zzrf paramzzrf)
      throws IOException
    {
      for (;;)
      {
        int i = paramzzrf.zzBr();
        switch (i)
        {
        default: 
          if (zza(paramzzrf, i)) {}
          break;
        case 0: 
          return this;
        case 8: 
          this.zzaOZ = paramzzrf.zzBt();
          break;
        case 18: 
          if (this.zziO == null) {
            this.zziO = new zzaf.zzf();
          }
          paramzzrf.zza(this.zziO);
          break;
        case 26: 
          if (this.zzaPa == null) {
            this.zzaPa = new zzaf.zzj();
          }
          paramzzrf.zza(this.zzaPa);
        }
      }
    }
    
    public zza zzzY()
    {
      this.zzaOZ = 0L;
      this.zziO = null;
      this.zzaPa = null;
      this.zzaVU = null;
      this.zzaWf = -1;
      return this;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzpx.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */