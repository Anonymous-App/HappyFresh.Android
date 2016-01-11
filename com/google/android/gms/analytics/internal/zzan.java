package com.google.android.gms.analytics.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

public class zzan
  extends zzd
{
  protected boolean zzIx;
  protected int zzKR;
  protected String zzLU;
  protected String zzLV;
  protected int zzLX;
  protected boolean zzML;
  protected boolean zzMM;
  protected boolean zzMN;
  
  public zzan(zzf paramzzf)
  {
    super(paramzzf);
  }
  
  private static int zzbo(String paramString)
  {
    paramString = paramString.toLowerCase();
    if ("verbose".equals(paramString)) {
      return 0;
    }
    if ("info".equals(paramString)) {
      return 1;
    }
    if ("warning".equals(paramString)) {
      return 2;
    }
    if ("error".equals(paramString)) {
      return 3;
    }
    return -1;
  }
  
  public int getLogLevel()
  {
    zzia();
    return this.zzKR;
  }
  
  void zza(zzaa paramzzaa)
  {
    zzaT("Loading global XML config values");
    String str;
    if (paramzzaa.zzjK())
    {
      str = paramzzaa.zzjL();
      this.zzLU = str;
      zzb("XML config - app name", str);
    }
    if (paramzzaa.zzjM())
    {
      str = paramzzaa.zzjN();
      this.zzLV = str;
      zzb("XML config - app version", str);
    }
    int i;
    if (paramzzaa.zzjO())
    {
      i = zzbo(paramzzaa.zzjP());
      if (i >= 0)
      {
        this.zzKR = i;
        zza("XML config - log level", Integer.valueOf(i));
      }
    }
    if (paramzzaa.zzjQ())
    {
      i = paramzzaa.zzjR();
      this.zzLX = i;
      this.zzMM = true;
      zzb("XML config - dispatch period (sec)", Integer.valueOf(i));
    }
    if (paramzzaa.zzjS())
    {
      boolean bool = paramzzaa.zzjT();
      this.zzIx = bool;
      this.zzMN = true;
      zzb("XML config - dry run", Boolean.valueOf(bool));
    }
  }
  
  protected void zzhn()
  {
    zzkI();
  }
  
  public String zzjL()
  {
    zzia();
    return this.zzLU;
  }
  
  public String zzjN()
  {
    zzia();
    return this.zzLV;
  }
  
  public boolean zzjO()
  {
    zzia();
    return this.zzML;
  }
  
  public boolean zzjQ()
  {
    zzia();
    return this.zzMM;
  }
  
  public boolean zzjS()
  {
    zzia();
    return this.zzMN;
  }
  
  public boolean zzjT()
  {
    zzia();
    return this.zzIx;
  }
  
  public int zzkH()
  {
    zzia();
    return this.zzLX;
  }
  
  protected void zzkI()
  {
    Object localObject1 = getContext();
    try
    {
      localObject1 = ((Context)localObject1).getPackageManager().getApplicationInfo(((Context)localObject1).getPackageName(), 129);
      if (localObject1 == null)
      {
        zzaW("Couldn't get ApplicationInfo to load global config");
        return;
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      Object localObject2;
      do
      {
        int i;
        do
        {
          do
          {
            for (;;)
            {
              zzd("PackageManager doesn't know about the app package", localNameNotFoundException);
              localObject2 = null;
            }
            localObject2 = ((ApplicationInfo)localObject2).metaData;
          } while (localObject2 == null);
          i = ((Bundle)localObject2).getInt("com.google.android.gms.analytics.globalConfigResource");
        } while (i <= 0);
        localObject2 = (zzaa)new zzz(zzhM()).zzab(i);
      } while (localObject2 == null);
      zza((zzaa)localObject2);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/analytics/internal/zzan.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */