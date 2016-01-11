package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzu;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class zzqd
{
  private final List<zzpy> zzaPn = new ArrayList();
  
  public String getId()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    Iterator localIterator = this.zzaPn.iterator();
    int i = 1;
    if (localIterator.hasNext())
    {
      zzpy localzzpy = (zzpy)localIterator.next();
      if (i != 0) {
        i = 0;
      }
      for (;;)
      {
        localStringBuilder.append(localzzpy.getContainerId());
        break;
        localStringBuilder.append("#");
      }
    }
    return localStringBuilder.toString();
  }
  
  public List<zzpy> zzAf()
  {
    return this.zzaPn;
  }
  
  public zzqd zzb(zzpy paramzzpy)
    throws IllegalArgumentException
  {
    zzu.zzu(paramzzpy);
    Iterator localIterator = this.zzaPn.iterator();
    while (localIterator.hasNext()) {
      if (((zzpy)localIterator.next()).getContainerId().equals(paramzzpy.getContainerId())) {
        throw new IllegalArgumentException("The container is already being requested. " + paramzzpy.getContainerId());
      }
    }
    this.zzaPn.add(paramzzpy);
    return this;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzqd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */