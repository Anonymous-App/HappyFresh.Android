package com.google.android.gms.internal;

import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzu;
import java.util.Set;

public final class zzln
{
  public static String[] zza(Scope[] paramArrayOfScope)
  {
    zzu.zzb(paramArrayOfScope, "scopes can't be null.");
    String[] arrayOfString = new String[paramArrayOfScope.length];
    int i = 0;
    while (i < paramArrayOfScope.length)
    {
      arrayOfString[i] = paramArrayOfScope[i].zzmS();
      i += 1;
    }
    return arrayOfString;
  }
  
  public static String[] zzc(Set<Scope> paramSet)
  {
    zzu.zzb(paramSet, "scopes can't be null.");
    return zza((Scope[])paramSet.toArray(new Scope[paramSet.size()]));
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzln.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */