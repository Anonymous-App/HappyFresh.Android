package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzu;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class zzno
{
  private final zznr zzaDU;
  private boolean zzaDV;
  private long zzaDW;
  private long zzaDX;
  private long zzaDY;
  private long zzaDZ;
  private long zzaEa;
  private boolean zzaEb;
  private final Map<Class<? extends zznq>, zznq> zzaEc;
  private final List<zznu> zzaEd;
  private final zzlb zzpw;
  
  zzno(zzno paramzzno)
  {
    this.zzaDU = paramzzno.zzaDU;
    this.zzpw = paramzzno.zzpw;
    this.zzaDW = paramzzno.zzaDW;
    this.zzaDX = paramzzno.zzaDX;
    this.zzaDY = paramzzno.zzaDY;
    this.zzaDZ = paramzzno.zzaDZ;
    this.zzaEa = paramzzno.zzaEa;
    this.zzaEd = new ArrayList(paramzzno.zzaEd);
    this.zzaEc = new HashMap(paramzzno.zzaEc.size());
    paramzzno = paramzzno.zzaEc.entrySet().iterator();
    while (paramzzno.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)paramzzno.next();
      zznq localzznq = zzf((Class)localEntry.getKey());
      ((zznq)localEntry.getValue()).zza(localzznq);
      this.zzaEc.put(localEntry.getKey(), localzznq);
    }
  }
  
  zzno(zznr paramzznr, zzlb paramzzlb)
  {
    zzu.zzu(paramzznr);
    zzu.zzu(paramzzlb);
    this.zzaDU = paramzznr;
    this.zzpw = paramzzlb;
    this.zzaDZ = 1800000L;
    this.zzaEa = 3024000000L;
    this.zzaEc = new HashMap();
    this.zzaEd = new ArrayList();
  }
  
  private static <T extends zznq> T zzf(Class<T> paramClass)
  {
    try
    {
      paramClass = (zznq)paramClass.newInstance();
      return paramClass;
    }
    catch (InstantiationException paramClass)
    {
      throw new IllegalArgumentException("dataType doesn't have default constructor", paramClass);
    }
    catch (IllegalAccessException paramClass)
    {
      throw new IllegalArgumentException("dataType default constructor is not accessible", paramClass);
    }
  }
  
  public void zzL(long paramLong)
  {
    this.zzaDX = paramLong;
  }
  
  public void zzb(zznq paramzznq)
  {
    zzu.zzu(paramzznq);
    Class localClass = paramzznq.getClass();
    if (localClass.getSuperclass() != zznq.class) {
      throw new IllegalArgumentException();
    }
    paramzznq.zza(zze(localClass));
  }
  
  public <T extends zznq> T zzd(Class<T> paramClass)
  {
    return (zznq)this.zzaEc.get(paramClass);
  }
  
  public <T extends zznq> T zze(Class<T> paramClass)
  {
    zznq localzznq2 = (zznq)this.zzaEc.get(paramClass);
    zznq localzznq1 = localzznq2;
    if (localzznq2 == null)
    {
      localzznq1 = zzf(paramClass);
      this.zzaEc.put(paramClass, localzznq1);
    }
    return localzznq1;
  }
  
  public zzno zzvP()
  {
    return new zzno(this);
  }
  
  public Collection<zznq> zzvQ()
  {
    return this.zzaEc.values();
  }
  
  public List<zznu> zzvR()
  {
    return this.zzaEd;
  }
  
  public long zzvS()
  {
    return this.zzaDW;
  }
  
  public void zzvT()
  {
    zzvX().zze(this);
  }
  
  public boolean zzvU()
  {
    return this.zzaDV;
  }
  
  void zzvV()
  {
    this.zzaDY = this.zzpw.elapsedRealtime();
    if (this.zzaDX != 0L) {}
    for (this.zzaDW = this.zzaDX;; this.zzaDW = this.zzpw.currentTimeMillis())
    {
      this.zzaDV = true;
      return;
    }
  }
  
  zznr zzvW()
  {
    return this.zzaDU;
  }
  
  zzns zzvX()
  {
    return this.zzaDU.zzvX();
  }
  
  boolean zzvY()
  {
    return this.zzaEb;
  }
  
  void zzvZ()
  {
    this.zzaEb = true;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzno.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */