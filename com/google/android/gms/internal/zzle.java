package com.google.android.gms.internal;

import android.content.res.Configuration;
import android.content.res.Resources;

public final class zzle
{
  public static boolean zzb(Resources paramResources)
  {
    if (paramResources == null) {}
    for (;;)
    {
      return false;
      if ((paramResources.getConfiguration().screenLayout & 0xF) > 3) {}
      for (int i = 1; ((zzlk.zzoR()) && (i != 0)) || (zzc(paramResources)); i = 0) {
        return true;
      }
    }
  }
  
  private static boolean zzc(Resources paramResources)
  {
    boolean bool2 = false;
    paramResources = paramResources.getConfiguration();
    boolean bool1 = bool2;
    if (zzlk.zzoT())
    {
      bool1 = bool2;
      if ((paramResources.screenLayout & 0xF) <= 3)
      {
        bool1 = bool2;
        if (paramResources.smallestScreenWidthDp >= 600) {
          bool1 = true;
        }
      }
    }
    return bool1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */