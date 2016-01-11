package com.google.android.gms.maps.internal;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.maps.model.RuntimeRemoteException;

public class zzy
{
  private static Context zzaCM;
  private static zzc zzaCN;
  
  private static Context getRemoteContext(Context paramContext)
  {
    if (zzaCM == null) {
      if (!zzvE()) {
        break label23;
      }
    }
    label23:
    for (zzaCM = paramContext.getApplicationContext();; zzaCM = GooglePlayServicesUtil.getRemoteContext(paramContext)) {
      return zzaCM;
    }
  }
  
  private static <T> T zza(ClassLoader paramClassLoader, String paramString)
  {
    try
    {
      paramClassLoader = zzc(((ClassLoader)zzu.zzu(paramClassLoader)).loadClass(paramString));
      return paramClassLoader;
    }
    catch (ClassNotFoundException paramClassLoader)
    {
      throw new IllegalStateException("Unable to find dynamic class " + paramString);
    }
  }
  
  private static zzc zzaA(Context paramContext)
  {
    if (zzvE())
    {
      Log.i(zzy.class.getSimpleName(), "Making Creator statically");
      return (zzc)zzc(zzvF());
    }
    Log.i(zzy.class.getSimpleName(), "Making Creator dynamically");
    return zzc.zza.zzcg((IBinder)zza(getRemoteContext(paramContext).getClassLoader(), "com.google.android.gms.maps.internal.CreatorImpl"));
  }
  
  public static zzc zzay(Context paramContext)
    throws GooglePlayServicesNotAvailableException
  {
    zzu.zzu(paramContext);
    if (zzaCN != null) {
      return zzaCN;
    }
    zzaz(paramContext);
    zzaCN = zzaA(paramContext);
    try
    {
      zzaCN.zzd(zze.zzw(getRemoteContext(paramContext).getResources()), GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE);
      return zzaCN;
    }
    catch (RemoteException paramContext)
    {
      throw new RuntimeRemoteException(paramContext);
    }
  }
  
  private static void zzaz(Context paramContext)
    throws GooglePlayServicesNotAvailableException
  {
    int i = GooglePlayServicesUtil.isGooglePlayServicesAvailable(paramContext);
    switch (i)
    {
    default: 
      throw new GooglePlayServicesNotAvailableException(i);
    }
  }
  
  private static <T> T zzc(Class<?> paramClass)
  {
    try
    {
      Object localObject = paramClass.newInstance();
      return (T)localObject;
    }
    catch (InstantiationException localInstantiationException)
    {
      throw new IllegalStateException("Unable to instantiate the dynamic class " + paramClass.getName());
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      throw new IllegalStateException("Unable to call the default constructor of " + paramClass.getName());
    }
  }
  
  public static boolean zzvE()
  {
    return false;
  }
  
  private static Class<?> zzvF()
  {
    try
    {
      if (Build.VERSION.SDK_INT < 15) {
        return Class.forName("com.google.android.gms.maps.internal.CreatorImplGmm6");
      }
      Class localClass = Class.forName("com.google.android.gms.maps.internal.CreatorImpl");
      return localClass;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      throw new RuntimeException(localClassNotFoundException);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/maps/internal/zzy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */