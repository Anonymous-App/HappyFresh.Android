package com.google.android.gms.internal;

import java.io.IOException;

public abstract class zzrh<M extends zzrh<M>>
  extends zzrn
{
  protected zzrj zzaVU;
  
  protected int zzB()
  {
    int j = 0;
    if (this.zzaVU != null)
    {
      int i = 0;
      for (;;)
      {
        k = i;
        if (j >= this.zzaVU.size()) {
          break;
        }
        i += this.zzaVU.zzkS(j).zzB();
        j += 1;
      }
    }
    int k = 0;
    return k;
  }
  
  protected final int zzBI()
  {
    if ((this.zzaVU == null) || (this.zzaVU.isEmpty())) {
      return 0;
    }
    return this.zzaVU.hashCode();
  }
  
  public M zzBJ()
    throws CloneNotSupportedException
  {
    zzrh localzzrh = (zzrh)super.zzBK();
    zzrl.zza(this, localzzrh);
    return localzzrh;
  }
  
  public final <T> T zza(zzri<M, T> paramzzri)
  {
    if (this.zzaVU == null) {}
    zzrk localzzrk;
    do
    {
      return null;
      localzzrk = this.zzaVU.zzkR(zzrq.zzkV(paramzzri.tag));
    } while (localzzrk == null);
    return (T)localzzrk.zzb(paramzzri);
  }
  
  public void zza(zzrg paramzzrg)
    throws IOException
  {
    if (this.zzaVU == null) {}
    for (;;)
    {
      return;
      int i = 0;
      while (i < this.zzaVU.size())
      {
        this.zzaVU.zzkS(i).zza(paramzzrg);
        i += 1;
      }
    }
  }
  
  protected final boolean zza(zzrf paramzzrf, int paramInt)
    throws IOException
  {
    int i = paramzzrf.getPosition();
    if (!paramzzrf.zzkA(paramInt)) {
      return false;
    }
    int j = zzrq.zzkV(paramInt);
    zzrp localzzrp = new zzrp(paramInt, paramzzrf.zzx(i, paramzzrf.getPosition() - i));
    paramzzrf = null;
    if (this.zzaVU == null) {
      this.zzaVU = new zzrj();
    }
    for (;;)
    {
      Object localObject = paramzzrf;
      if (paramzzrf == null)
      {
        localObject = new zzrk();
        this.zzaVU.zza(j, (zzrk)localObject);
      }
      ((zzrk)localObject).zza(localzzrp);
      return true;
      paramzzrf = this.zzaVU.zzkR(j);
    }
  }
  
  protected final boolean zza(M paramM)
  {
    if ((this.zzaVU == null) || (this.zzaVU.isEmpty())) {
      return (paramM.zzaVU == null) || (paramM.zzaVU.isEmpty());
    }
    return this.zzaVU.equals(paramM.zzaVU);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzrh.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */