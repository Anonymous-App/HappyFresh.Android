package com.appsee;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.view.Display;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import java.util.Locale;

class rj
{
  public static String E()
    throws PackageManager.NameNotFoundException
  {
    if (C == null)
    {
      Object localObject = jn.i();
      localObject = ((Context)localObject).getPackageManager().getPackageInfo(((Context)localObject).getPackageName(), 0);
      if (localObject == null) {
        throw new PackageManager.NameNotFoundException(AppseeBackgroundUploader.i("\026Q\024q3MpR7C1\004?X$K(Ol@4Y5M|\0259A:^"));
      }
      C = ((PackageInfo)localObject).versionName;
      if (lb.i(C)) {
        C = "0.0";
      }
    }
    return C;
  }
  
  public static String F()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    ol.l(new yi(localStringBuilder));
    return localStringBuilder.toString();
  }
  
  public static boolean F()
  {
    ki localki = i();
    return (localki == ki.b) || (localki == ki.G);
  }
  
  public static String H()
  {
    if (G == null) {
      G = Build.MANUFACTURER;
    }
    return G;
  }
  
  public static float a()
  {
    try
    {
      Runtime localRuntime = Runtime.getRuntime();
      long l1 = localRuntime.freeMemory();
      long l2 = localRuntime.totalMemory();
      return (1.0F - (float)l1 / (float)l2) * 100.0F;
    }
    catch (Exception localException) {}
    return -1.0F;
  }
  
  /* Error */
  public static boolean a()
  {
    // Byte code:
    //   0: iconst_1
    //   1: istore_0
    //   2: ldc 2
    //   4: monitorenter
    //   5: getstatic 138	com/appsee/rj:h	Z
    //   8: ifne +43 -> 51
    //   11: iconst_1
    //   12: putstatic 138	com/appsee/rj:h	Z
    //   15: invokestatic 141	com/appsee/rj:i	()Lcom/appsee/Dimension;
    //   18: astore_1
    //   19: aload_1
    //   20: invokevirtual 147	com/appsee/Dimension:getWidth	()I
    //   23: i2f
    //   24: invokestatic 150	com/appsee/rj:i	(F)F
    //   27: ldc -105
    //   29: fcmpl
    //   30: iflt +30 -> 60
    //   33: aload_1
    //   34: invokevirtual 154	com/appsee/Dimension:getHeight	()I
    //   37: i2f
    //   38: invokestatic 150	com/appsee/rj:i	(F)F
    //   41: ldc -105
    //   43: fcmpl
    //   44: iflt +16 -> 60
    //   47: iload_0
    //   48: putstatic 156	com/appsee/rj:K	Z
    //   51: getstatic 156	com/appsee/rj:K	Z
    //   54: istore_0
    //   55: ldc 2
    //   57: monitorexit
    //   58: iload_0
    //   59: ireturn
    //   60: iconst_0
    //   61: istore_0
    //   62: goto -15 -> 47
    //   65: astore_1
    //   66: ldc 2
    //   68: monitorexit
    //   69: aload_1
    //   70: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   1	61	0	bool	boolean
    //   18	16	1	localDimension	Dimension
    //   65	5	1	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   5	47	65	finally
    //   47	51	65	finally
    //   51	55	65	finally
  }
  
  public static String c()
  {
    if (i == null)
    {
      i = Build.VERSION.RELEASE;
      mc.l(Build.VERSION.SDK_INT, 1);
    }
    return i;
  }
  
  public static float i()
  {
    i();
    return I;
  }
  
  public static float i(float paramFloat)
  {
    i();
    return paramFloat / I;
  }
  
  public static int i()
  {
    return Build.VERSION.SDK_INT;
  }
  
  public static long i()
  {
    return SystemClock.elapsedRealtime();
  }
  
  public static Dimension i()
  {
    Display localDisplay = ((WindowManager)jn.i().getSystemService(AppseeBackgroundUploader.i("f\035#L:G"))).getDefaultDisplay();
    return i(localDisplay, i(localDisplay));
  }
  
  public static jg i()
  {
    Object localObject = jn.i();
    if (((Context)localObject).checkCallingOrSelfPermission(AppseeBackgroundUploader.i("H4[(Px\020cX0B;Z/J9Z<\031P7\035~\034y\020d\t}\rp\btN'\031i\001u")) != 0)
    {
      localObject = jg.k;
      return (jg)localObject;
    }
    jg localjg = jg.k;
    NetworkInfo localNetworkInfo = ((ConnectivityManager)((Context)localObject).getSystemService(AppseeBackgroundUploader.i("/F4Q?\\e\035;A!I"))).getActiveNetworkInfo();
    if (localNetworkInfo == null) {}
    for (localjg = jg.i;; localjg = jg.m)
    {
      localObject = localjg;
      if (localNetworkInfo == null) {
        break;
      }
      localObject = localjg;
      if (localNetworkInfo.isConnected()) {
        break;
      }
      return jg.i;
      if ((localNetworkInfo.getType() != 1) && (localNetworkInfo.getType() != 6) && (localNetworkInfo.getType() != 7) && ((Build.VERSION.SDK_INT <= 13) || (localNetworkInfo.getType() != 9))) {
        break label121;
      }
    }
    label121:
    if ((localNetworkInfo.getType() == 0) || (localNetworkInfo.getType() == 4) || (localNetworkInfo.getType() == 5)) {
      localjg = jg.b;
    }
    for (;;)
    {
      break;
    }
  }
  
  public static ki i()
  {
    return i(((WindowManager)jn.i().getSystemService(AppseeBackgroundUploader.i("f\035#L:G"))).getDefaultDisplay());
  }
  
  public static String i()
  {
    return Build.VERSION.INCREMENTAL;
  }
  
  public static boolean i()
  {
    return (Build.BRAND.toLowerCase().startsWith(AppseeBackgroundUploader.i("Xt\032(Z<S"))) || (Build.MANUFACTURER.toLowerCase().contains(AppseeBackgroundUploader.i("_7H;MtXmA;S")));
  }
  
  public static Dimension[] i()
  {
    Dimension[] arrayOfDimension = l();
    int j = 3;
    int n = 3;
    for (;;)
    {
      n -= 1;
      if ((j > 0) && ((arrayOfDimension[0].getRatio() > 1.0D) || (arrayOfDimension[1].getRatio() > 1.0D)))
      {
        mc.l(AppseeBackgroundUploader.i("k5A Nx\0329\033(O;^%G=\037)\\c\021(FuC?I9Jp\\<\027a\033,O=K&^lF(V?Qe\0259A:^"), 1);
        try
        {
          Thread.sleep(100L);
          arrayOfDimension = l();
          j = n;
        }
        catch (InterruptedException localInterruptedException)
        {
          for (;;) {}
        }
      }
    }
    return localInterruptedException;
  }
  
  public static String j()
  {
    if (i()) {
      return AppseeBackgroundUploader.i("\037Rd\030,\\:B");
    }
    return Build.MODEL;
  }
  
  public static String k()
  {
    if (b == null)
    {
      Locale localLocale = jn.i().getResources().getConfiguration().locale;
      b = AppseeBackgroundUploader.i("\035") + localLocale.getCountry();
    }
    return b;
  }
  
  /* Error */
  public static boolean k()
  {
    // Byte code:
    //   0: iconst_1
    //   1: istore_0
    //   2: ldc 2
    //   4: monitorenter
    //   5: getstatic 414	com/appsee/rj:m	Z
    //   8: ifne +27 -> 35
    //   11: iconst_1
    //   12: putstatic 414	com/appsee/rj:m	Z
    //   15: invokestatic 416	com/appsee/rj:i	()[Lcom/appsee/Dimension;
    //   18: astore_1
    //   19: aload_1
    //   20: iconst_0
    //   21: aaload
    //   22: aload_1
    //   23: iconst_1
    //   24: aaload
    //   25: invokevirtual 420	com/appsee/Dimension:equals	(Ljava/lang/Object;)Z
    //   28: ifne +16 -> 44
    //   31: iload_0
    //   32: putstatic 422	com/appsee/rj:a	Z
    //   35: getstatic 422	com/appsee/rj:a	Z
    //   38: istore_0
    //   39: ldc 2
    //   41: monitorexit
    //   42: iload_0
    //   43: ireturn
    //   44: iconst_0
    //   45: istore_0
    //   46: goto -15 -> 31
    //   49: astore_1
    //   50: ldc 2
    //   52: monitorexit
    //   53: aload_1
    //   54: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   1	45	0	bool	boolean
    //   18	5	1	arrayOfDimension	Dimension[]
    //   49	5	1	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   5	31	49	finally
    //   31	35	49	finally
    //   35	39	49	finally
  }
  
  public static float l()
  {
    return jn.i().getResources().getConfiguration().fontScale;
  }
  
  public static float l(float paramFloat)
  {
    i();
    return I * paramFloat;
  }
  
  public static int l()
  {
    i();
    return k;
  }
  
  public static String l()
  {
    if (d == null)
    {
      d = jn.i().getPackageName();
      if (lb.i(d)) {
        d = "";
      }
    }
    return d;
  }
  
  @TargetApi(14)
  public static boolean l()
  {
    if (Build.VERSION.SDK_INT >= 14) {
      return ViewConfiguration.get(jn.i()).hasPermanentMenuKey();
    }
    return true;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/rj.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */