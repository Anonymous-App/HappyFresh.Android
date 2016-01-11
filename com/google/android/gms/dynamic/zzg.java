package com.google.android.gms.dynamic;

import android.content.Context;
import android.os.IBinder;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.zzu;

public abstract class zzg<T>
{
  private final String zzaju;
  private T zzajv;
  
  protected zzg(String paramString)
  {
    this.zzaju = paramString;
  }
  
  protected final T zzak(Context paramContext)
    throws zzg.zza
  {
    if (this.zzajv == null)
    {
      zzu.zzu(paramContext);
      paramContext = GooglePlayServicesUtil.getRemoteContext(paramContext);
      if (paramContext == null) {
        throw new zza("Could not get remote context.");
      }
      paramContext = paramContext.getClassLoader();
    }
    try
    {
      this.zzajv = zzd((IBinder)paramContext.loadClass(this.zzaju).newInstance());
      return (T)this.zzajv;
    }
    catch (ClassNotFoundException paramContext)
    {
      throw new zza("Could not load creator class.", paramContext);
    }
    catch (InstantiationException paramContext)
    {
      throw new zza("Could not instantiate creator.", paramContext);
    }
    catch (IllegalAccessException paramContext)
    {
      throw new zza("Could not access creator.", paramContext);
    }
  }
  
  protected abstract T zzd(IBinder paramIBinder);
  
  public static class zza
    extends Exception
  {
    public zza(String paramString)
    {
      super();
    }
    
    public zza(String paramString, Throwable paramThrowable)
    {
      super(paramThrowable);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/dynamic/zzg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */