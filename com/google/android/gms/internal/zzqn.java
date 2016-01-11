package com.google.android.gms.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.tagmanager.zzbg;
import java.util.List;

public abstract class zzqn
{
  private zzqd zzaPU;
  private zzqb zzaPV;
  private zzlb zzpw;
  
  public zzqn(zzqd paramzzqd, zzqb paramzzqb)
  {
    this(paramzzqd, paramzzqb, zzld.zzoQ());
  }
  
  public zzqn(zzqd paramzzqd, zzqb paramzzqb, zzlb paramzzlb)
  {
    if (paramzzqd.zzAf().size() == 1) {}
    for (;;)
    {
      zzu.zzV(bool);
      this.zzaPU = paramzzqd;
      this.zzaPV = paramzzqb;
      this.zzpw = paramzzlb;
      return;
      bool = false;
    }
  }
  
  protected abstract zzb zza(zzpy paramzzpy);
  
  protected abstract void zza(zzqe paramzzqe);
  
  public void zza(zza paramzza)
  {
    zzbg.zzaz("ResourceManager: Failed to download a resource: " + paramzza.name());
    paramzza = (zzpy)this.zzaPU.zzAf().get(0);
    zzb localzzb = zza(paramzza);
    if ((localzzb != null) && ((localzzb.zzAH() instanceof zzqf.zzc))) {}
    for (paramzza = new zzqe.zza(Status.zzXP, paramzza, null, (zzqf.zzc)localzzb.zzAH(), localzzb.zzAh(), localzzb.zzAl());; paramzza = new zzqe.zza(Status.zzXR, paramzza, zzqe.zza.zza.zzaPu))
    {
      zza(new zzqe(paramzza));
      return;
    }
  }
  
  public void zzu(byte[] paramArrayOfByte)
  {
    zzbg.zzaB("ResourceManager: Resource downloaded from Network: " + this.zzaPU.getId());
    localzzpy = (zzpy)this.zzaPU.zzAf().get(0);
    zzqe.zza.zza localzza2 = zzqe.zza.zza.zzaPu;
    localObject1 = null;
    long l2 = 0L;
    long l1 = l2;
    localzza1 = localzza2;
    try
    {
      Object localObject2 = this.zzaPV.zzt(paramArrayOfByte);
      l1 = l2;
      localObject1 = localObject2;
      localzza1 = localzza2;
      l2 = this.zzpw.currentTimeMillis();
      l1 = l2;
      localObject1 = localObject2;
      localzza1 = localzza2;
      if (localObject2 == null)
      {
        l1 = l2;
        localObject1 = localObject2;
        localzza1 = localzza2;
        zzbg.zzaA("Parsed resource from network is null");
        l1 = l2;
        localObject1 = localObject2;
        localzza1 = localzza2;
        zzb localzzb2 = zza(localzzpy);
        l1 = l2;
        localObject1 = localObject2;
        localzza1 = localzza2;
        if (localzzb2 != null)
        {
          l1 = l2;
          localObject1 = localObject2;
          localzza1 = localzza2;
          localObject2 = localzzb2.zzAH();
          l1 = l2;
          localObject1 = localObject2;
          localzza1 = localzza2;
          localzza2 = localzzb2.zzAh();
          l1 = l2;
          localObject1 = localObject2;
          localzza1 = localzza2;
          l2 = localzzb2.zzAl();
          l1 = l2;
          localzza1 = localzza2;
          localObject1 = localObject2;
        }
      }
    }
    catch (zzqf.zzg localzzg)
    {
      for (;;)
      {
        zzbg.zzaA("Resource from network is corrupted");
        zzb localzzb1 = zza(localzzpy);
        if (localzzb1 != null)
        {
          localObject1 = localzzb1.zzAH();
          localzza1 = localzzb1.zzAh();
          continue;
          paramArrayOfByte = new zzqe.zza(Status.zzXR, localzzpy, zzqe.zza.zza.zzaPu);
        }
      }
    }
    if (localObject1 != null)
    {
      paramArrayOfByte = new zzqe.zza(Status.zzXP, localzzpy, paramArrayOfByte, (zzqf.zzc)localObject1, localzza1, l1);
      zza(new zzqe(paramArrayOfByte));
      return;
    }
  }
  
  public static enum zza
  {
    private zza() {}
  }
  
  public class zzb
  {
    private final zzqe.zza.zza zzaPp;
    private final long zzaPr;
    private final Object zzaQa;
    
    public Object zzAH()
    {
      return this.zzaQa;
    }
    
    public zzqe.zza.zza zzAh()
    {
      return this.zzaPp;
    }
    
    public long zzAl()
    {
      return this.zzaPr;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzqn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */