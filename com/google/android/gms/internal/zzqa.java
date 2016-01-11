package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.tagmanager.zzbg;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class zzqa
{
  private final Context mContext;
  private String zzaLc = null;
  private final zzqh zzaPe;
  Map<String, zzc<zzqf.zzc>> zzaPf = new HashMap();
  private final Map<String, zzqp> zzaPg;
  private final zzlb zzpw;
  
  public zzqa(Context paramContext)
  {
    this(paramContext, new HashMap(), new zzqh(paramContext), zzld.zzoQ());
  }
  
  zzqa(Context paramContext, Map<String, zzqp> paramMap, zzqh paramzzqh, zzlb paramzzlb)
  {
    this.mContext = paramContext;
    this.zzpw = paramzzlb;
    this.zzaPe = paramzzqh;
    this.zzaPg = paramMap;
  }
  
  private void zza(zzqd paramzzqd, zza paramzza)
  {
    boolean bool = true;
    paramzzqd = paramzzqd.zzAf();
    if (paramzzqd.size() == 1) {}
    for (;;)
    {
      zzu.zzV(bool);
      zza((zzpy)paramzzqd.get(0), paramzza);
      return;
      bool = false;
    }
  }
  
  void zza(final zzpy paramzzpy, final zza paramzza)
  {
    this.zzaPe.zza(paramzzpy.zzAb(), paramzzpy.zzzZ(), zzqc.zzaPm, new zzqg()
    {
      public void zza(Status paramAnonymousStatus, Object paramAnonymousObject, Integer paramAnonymousInteger, long paramAnonymousLong)
      {
        if (paramAnonymousStatus.isSuccess()) {
          if (paramAnonymousInteger == zzqh.zzaPM) {
            paramAnonymousStatus = zzqe.zza.zza.zzaPw;
          }
        }
        for (paramAnonymousStatus = new zzqe.zza(Status.zzXP, paramzzpy, null, (zzqf.zzc)paramAnonymousObject, paramAnonymousStatus, paramAnonymousLong);; paramAnonymousStatus = new zzqe.zza(new Status(16, "There is no valid resource for the container: " + paramzzpy.getContainerId()), null, zzqe.zza.zza.zzaPv))
        {
          paramzza.zza(new zzqe(paramAnonymousStatus));
          return;
          paramAnonymousStatus = zzqe.zza.zza.zzaPv;
          break;
        }
      }
    });
  }
  
  void zza(zzqd paramzzqd, zza paramzza, zzqn paramzzqn)
  {
    Iterator localIterator = paramzzqd.zzAf().iterator();
    int i = 0;
    zzpy localzzpy;
    long l;
    if (localIterator.hasNext())
    {
      localzzpy = (zzpy)localIterator.next();
      zzc localzzc = (zzc)this.zzaPf.get(localzzpy.getContainerId());
      if (localzzc != null)
      {
        l = localzzc.zzAe();
        label67:
        if (l + 900000L >= this.zzpw.currentTimeMillis()) {
          break label202;
        }
        i = 1;
      }
    }
    label199:
    label202:
    for (;;)
    {
      break;
      l = this.zzaPe.zzfa(localzzpy.getContainerId());
      break label67;
      if (i != 0)
      {
        paramzza = (zzqp)this.zzaPg.get(paramzzqd.getId());
        if (paramzza != null) {
          break label199;
        }
        if (this.zzaLc == null)
        {
          paramzza = new zzqp();
          this.zzaPg.put(paramzzqd.getId(), paramzza);
        }
      }
      for (;;)
      {
        paramzza.zza(this.mContext, paramzzqd, 0L, paramzzqn);
        return;
        paramzza = new zzqp(this.zzaLc);
        break;
        zza(paramzzqd, paramzza);
        return;
      }
    }
  }
  
  void zza(zzqe.zza paramzza)
  {
    Object localObject = paramzza.zzAj().getContainerId();
    Status localStatus = paramzza.getStatus();
    paramzza = paramzza.zzAk();
    if (this.zzaPf.containsKey(localObject))
    {
      localObject = (zzc)this.zzaPf.get(localObject);
      ((zzc)localObject).zzU(this.zzpw.currentTimeMillis());
      if (localStatus == Status.zzXP)
      {
        ((zzc)localObject).zzaV(localStatus);
        ((zzc)localObject).zzO(paramzza);
      }
      return;
    }
    this.zzaPf.put(localObject, new zzc(localStatus, paramzza, this.zzpw.currentTimeMillis()));
  }
  
  public void zza(String paramString1, Integer paramInteger, String paramString2, zza paramzza)
  {
    paramString1 = new zzqd().zzb(new zzpy(paramString1, paramInteger, paramString2, false));
    zza(paramString1, paramzza, new zzb(paramString1, zzqc.zzaPm, paramzza));
  }
  
  public void zzeU(String paramString)
  {
    this.zzaLc = paramString;
  }
  
  public static abstract interface zza
  {
    public abstract void zza(zzqe paramzzqe);
  }
  
  class zzb
    extends zzqn
  {
    private final zzqa.zza zzaPk;
    
    zzb(zzqd paramzzqd, zzqb paramzzqb, zzqa.zza paramzza)
    {
      super(paramzzqb);
      this.zzaPk = paramzza;
    }
    
    protected zzqn.zzb zza(zzpy paramzzpy)
    {
      return null;
    }
    
    protected void zza(zzqe paramzzqe)
    {
      zzqe.zza localzza = paramzzqe.zzAg();
      zzqa.this.zza(localzza);
      if ((localzza.getStatus() == Status.zzXP) && (localzza.zzAh() == zzqe.zza.zza.zzaPu) && (localzza.zzAi() != null) && (localzza.zzAi().length > 0))
      {
        zzqa.zza(zzqa.this).zze(localzza.zzAj().zzAb(), localzza.zzAi());
        zzbg.zzaB("Resource successfully load from Network.");
        this.zzaPk.zza(paramzzqe);
        return;
      }
      StringBuilder localStringBuilder = new StringBuilder().append("Response status: ");
      if (localzza.getStatus().isSuccess()) {}
      for (paramzzqe = "SUCCESS";; paramzzqe = "FAILURE")
      {
        zzbg.zzaB(paramzzqe);
        if (localzza.getStatus().isSuccess())
        {
          zzbg.zzaB("Response source: " + localzza.zzAh().toString());
          zzbg.zzaB("Response size: " + localzza.zzAi().length);
        }
        zzqa.this.zza(localzza.zzAj(), this.zzaPk);
        return;
      }
    }
  }
  
  static class zzc<T>
  {
    private T mData;
    private Status zzOt;
    private long zzaPl;
    
    public zzc(Status paramStatus, T paramT, long paramLong)
    {
      this.zzOt = paramStatus;
      this.mData = paramT;
      this.zzaPl = paramLong;
    }
    
    public long zzAe()
    {
      return this.zzaPl;
    }
    
    public void zzO(T paramT)
    {
      this.mData = paramT;
    }
    
    public void zzU(long paramLong)
    {
      this.zzaPl = paramLong;
    }
    
    public void zzaV(Status paramStatus)
    {
      this.zzOt = paramStatus;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzqa.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */