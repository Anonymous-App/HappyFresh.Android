package com.google.android.gms.common.internal;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.lang.reflect.Field;

public abstract class DowngradeableSafeParcel
  implements SafeParcelable
{
  private static final Object zzZZ = new Object();
  private static ClassLoader zzaaa = null;
  private static Integer zzaab = null;
  private boolean zzaac = false;
  
  private static boolean zza(Class<?> paramClass)
  {
    try
    {
      boolean bool = "SAFE_PARCELABLE_NULL_STRING".equals(paramClass.getField("NULL").get(null));
      return bool;
    }
    catch (IllegalAccessException paramClass)
    {
      return false;
    }
    catch (NoSuchFieldException paramClass) {}
    return false;
  }
  
  protected static boolean zzca(String paramString)
  {
    ClassLoader localClassLoader = zznD();
    if (localClassLoader == null) {
      return true;
    }
    try
    {
      boolean bool = zza(localClassLoader.loadClass(paramString));
      return bool;
    }
    catch (Exception paramString) {}
    return false;
  }
  
  protected static ClassLoader zznD()
  {
    synchronized (zzZZ)
    {
      ClassLoader localClassLoader = zzaaa;
      return localClassLoader;
    }
  }
  
  protected static Integer zznE()
  {
    synchronized (zzZZ)
    {
      Integer localInteger = zzaab;
      return localInteger;
    }
  }
  
  protected boolean zznF()
  {
    return this.zzaac;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/internal/DowngradeableSafeParcel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */