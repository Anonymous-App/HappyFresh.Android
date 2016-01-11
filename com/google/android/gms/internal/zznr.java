package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzu;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class zznr<T extends zznr>
{
  private final zzns zzaEe;
  protected final zzno zzaEf;
  private final List<zznp> zzaEg;
  
  protected zznr(zzns paramzzns, zzlb paramzzlb)
  {
    zzu.zzu(paramzzns);
    this.zzaEe = paramzzns;
    this.zzaEg = new ArrayList();
    paramzzns = new zzno(this, paramzzlb);
    paramzzns.zzvZ();
    this.zzaEf = paramzzns;
  }
  
  protected void zza(zzno paramzzno) {}
  
  protected void zzd(zzno paramzzno)
  {
    Iterator localIterator = this.zzaEg.iterator();
    while (localIterator.hasNext()) {
      ((zznp)localIterator.next()).zza(this, paramzzno);
    }
  }
  
  public zzno zzhc()
  {
    zzno localzzno = this.zzaEf.zzvP();
    zzd(localzzno);
    return localzzno;
  }
  
  protected zzns zzvX()
  {
    return this.zzaEe;
  }
  
  public zzno zzwa()
  {
    return this.zzaEf;
  }
  
  public List<zznu> zzwb()
  {
    return this.zzaEf.zzvR();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zznr.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */