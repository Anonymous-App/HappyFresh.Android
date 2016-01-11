package com.google.android.gms.internal;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class zzri<M extends zzrh<M>, T>
{
  public final int tag;
  protected final int type;
  protected final Class<T> zzaVV;
  protected final boolean zzaVW;
  
  private zzri(int paramInt1, Class<T> paramClass, int paramInt2, boolean paramBoolean)
  {
    this.type = paramInt1;
    this.zzaVV = paramClass;
    this.tag = paramInt2;
    this.zzaVW = paramBoolean;
  }
  
  @Deprecated
  public static <M extends zzrh<M>, T extends zzrn> zzri<M, T> zza(int paramInt1, Class<T> paramClass, int paramInt2)
  {
    return new zzri(paramInt1, paramClass, paramInt2, false);
  }
  
  private T zzy(List<zzrp> paramList)
  {
    int j = 0;
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    while (i < paramList.size())
    {
      localObject = (zzrp)paramList.get(i);
      if (((zzrp)localObject).zzaWg.length != 0) {
        zza((zzrp)localObject, localArrayList);
      }
      i += 1;
    }
    int k = localArrayList.size();
    if (k == 0)
    {
      paramList = null;
      return paramList;
    }
    Object localObject = this.zzaVV.cast(Array.newInstance(this.zzaVV.getComponentType(), k));
    i = j;
    for (;;)
    {
      paramList = (List<zzrp>)localObject;
      if (i >= k) {
        break;
      }
      Array.set(localObject, i, localArrayList.get(i));
      i += 1;
    }
  }
  
  private T zzz(List<zzrp> paramList)
  {
    if (paramList.isEmpty()) {
      return null;
    }
    paramList = (zzrp)paramList.get(paramList.size() - 1);
    return (T)this.zzaVV.cast(zzA(zzrf.zzz(paramList.zzaWg)));
  }
  
  protected Object zzA(zzrf paramzzrf)
  {
    Class localClass;
    if (this.zzaVW) {
      localClass = this.zzaVV.getComponentType();
    }
    for (;;)
    {
      try
      {
        switch (this.type)
        {
        case 10: 
          throw new IllegalArgumentException("Unknown type " + this.type);
        }
      }
      catch (InstantiationException paramzzrf)
      {
        throw new IllegalArgumentException("Error creating instance of class " + localClass, paramzzrf);
        localClass = this.zzaVV;
        continue;
        zzrn localzzrn = (zzrn)localClass.newInstance();
        paramzzrf.zza(localzzrn, zzrq.zzkV(this.tag));
        return localzzrn;
        localzzrn = (zzrn)localClass.newInstance();
        paramzzrf.zza(localzzrn);
        return localzzrn;
      }
      catch (IllegalAccessException paramzzrf)
      {
        throw new IllegalArgumentException("Error creating instance of class " + localClass, paramzzrf);
      }
      catch (IOException paramzzrf)
      {
        throw new IllegalArgumentException("Error reading extension field", paramzzrf);
      }
    }
  }
  
  int zzQ(Object paramObject)
  {
    if (this.zzaVW) {
      return zzR(paramObject);
    }
    return zzS(paramObject);
  }
  
  protected int zzR(Object paramObject)
  {
    int j = 0;
    int m = Array.getLength(paramObject);
    int i = 0;
    while (i < m)
    {
      int k = j;
      if (Array.get(paramObject, i) != null) {
        k = j + zzS(Array.get(paramObject, i));
      }
      i += 1;
      j = k;
    }
    return j;
  }
  
  protected int zzS(Object paramObject)
  {
    int i = zzrq.zzkV(this.tag);
    switch (this.type)
    {
    default: 
      throw new IllegalArgumentException("Unknown type " + this.type);
    case 10: 
      return zzrg.zzb(i, (zzrn)paramObject);
    }
    return zzrg.zzc(i, (zzrn)paramObject);
  }
  
  protected void zza(zzrp paramzzrp, List<Object> paramList)
  {
    paramList.add(zzA(zzrf.zzz(paramzzrp.zzaWg)));
  }
  
  void zza(Object paramObject, zzrg paramzzrg)
    throws IOException
  {
    if (this.zzaVW)
    {
      zzc(paramObject, paramzzrg);
      return;
    }
    zzb(paramObject, paramzzrg);
  }
  
  protected void zzb(Object paramObject, zzrg paramzzrg)
  {
    for (;;)
    {
      try
      {
        paramzzrg.zzkN(this.tag);
        switch (this.type)
        {
        case 10: 
          throw new IllegalArgumentException("Unknown type " + this.type);
        }
      }
      catch (IOException paramObject)
      {
        throw new IllegalStateException((Throwable)paramObject);
      }
      paramObject = (zzrn)paramObject;
      int i = zzrq.zzkV(this.tag);
      paramzzrg.zzb((zzrn)paramObject);
      paramzzrg.zzC(i, 4);
      return;
      paramzzrg.zzc((zzrn)paramObject);
      return;
    }
  }
  
  protected void zzc(Object paramObject, zzrg paramzzrg)
  {
    int j = Array.getLength(paramObject);
    int i = 0;
    while (i < j)
    {
      Object localObject = Array.get(paramObject, i);
      if (localObject != null) {
        zzb(localObject, paramzzrg);
      }
      i += 1;
    }
  }
  
  final T zzx(List<zzrp> paramList)
  {
    if (paramList == null) {
      return null;
    }
    if (this.zzaVW) {
      return (T)zzy(paramList);
    }
    return (T)zzz(paramList);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzri.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */