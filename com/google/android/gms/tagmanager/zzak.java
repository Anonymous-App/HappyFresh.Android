package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag.zza;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

abstract class zzak
{
  private final Set<String> zzaLI;
  private final String zzaLJ;
  
  public zzak(String paramString, String... paramVarArgs)
  {
    this.zzaLJ = paramString;
    this.zzaLI = new HashSet(paramVarArgs.length);
    int j = paramVarArgs.length;
    int i = 0;
    while (i < j)
    {
      paramString = paramVarArgs[i];
      this.zzaLI.add(paramString);
      i += 1;
    }
  }
  
  public abstract zzag.zza zzE(Map<String, zzag.zza> paramMap);
  
  boolean zzg(Set<String> paramSet)
  {
    return paramSet.containsAll(this.zzaLI);
  }
  
  public String zzyM()
  {
    return this.zzaLJ;
  }
  
  public Set<String> zzyN()
  {
    return this.zzaLI;
  }
  
  public abstract boolean zzyh();
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzak.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */