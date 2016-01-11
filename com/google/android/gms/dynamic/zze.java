package com.google.android.gms.dynamic;

import java.lang.reflect.Field;

public final class zze<T>
  extends zzd.zza
{
  private final T mWrappedObject;
  
  private zze(T paramT)
  {
    this.mWrappedObject = paramT;
  }
  
  public static <T> T zzn(zzd paramzzd)
  {
    if ((paramzzd instanceof zze)) {
      return (T)((zze)paramzzd).mWrappedObject;
    }
    paramzzd = paramzzd.asBinder();
    Object localObject = paramzzd.getClass().getDeclaredFields();
    if (localObject.length == 1)
    {
      localObject = localObject[0];
      if (!((Field)localObject).isAccessible())
      {
        ((Field)localObject).setAccessible(true);
        try
        {
          paramzzd = ((Field)localObject).get(paramzzd);
          return paramzzd;
        }
        catch (NullPointerException paramzzd)
        {
          throw new IllegalArgumentException("Binder object is null.", paramzzd);
        }
        catch (IllegalArgumentException paramzzd)
        {
          throw new IllegalArgumentException("remoteBinder is the wrong class.", paramzzd);
        }
        catch (IllegalAccessException paramzzd)
        {
          throw new IllegalArgumentException("Could not access the field in remoteBinder.", paramzzd);
        }
      }
      throw new IllegalArgumentException("The concrete class implementing IObjectWrapper must have exactly one declared *private* field for the wrapped object. Preferably, this is an instance of the ObjectWrapper<T> class.");
    }
    throw new IllegalArgumentException("The concrete class implementing IObjectWrapper must have exactly *one* declared private field for the wrapped object.  Preferably, this is an instance of the ObjectWrapper<T> class.");
  }
  
  public static <T> zzd zzw(T paramT)
  {
    return new zze(paramT);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/dynamic/zze.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */