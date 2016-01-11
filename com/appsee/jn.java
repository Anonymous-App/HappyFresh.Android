package com.appsee;

import android.app.Application;
import android.app.Instrumentation;
import android.os.Build.VERSION;
import java.lang.reflect.Method;

class jn
{
  public static final String k = "APPSEE_NO_CONTEXT";
  
  public static Application i()
  {
    if (G == null) {
      throw new NullPointerException("APPSEE_NO_CONTEXT");
    }
    return G;
  }
  
  public static boolean i()
  {
    try
    {
      Object localObject = bc.i(Class.forName(AppseeBackgroundUploader.i("=W4G=^uZ?K?\004\016I8@,V.FE\034'U0P")).getMethod(AppseeBackgroundUploader.i("Td\006,^!^\016I8@,V.FE\034'U0P"), new Class[0]).invoke(null, (Object[])null), AppseeBackgroundUploader.i("\"c!Y8[/R?Qe\025!Y>Z"));
      if ((localObject != null) && (!localObject.getClass().equals(Instrumentation.class)))
      {
        boolean bool = localObject.getClass().equals(wd.class);
        if (!bool) {
          return true;
        }
      }
      return false;
    }
    catch (Exception localException) {}
    return false;
  }
  
  public static boolean l()
  {
    for (;;)
    {
      Object localObject1;
      try
      {
        localObject1 = G;
        if (localObject1 == null) {}
        try
        {
          localObject1 = Class.forName(AppseeBackgroundUploader.i("=W4G=^uZ?K?\004\016I8@,V.FE\034'U0P"));
          if (Build.VERSION.SDK_INT < 9) {
            continue;
          }
          G = (Application)((Class)localObject1).getMethod(AppseeBackgroundUploader.i("=N=X*D8h*O6Vr\025!Y>Z"), new Class[0]).invoke(null, (Object[])null);
          localObject1 = G;
          if (localObject1 == null) {
            continue;
          }
          bool = true;
        }
        catch (Exception localException)
        {
          Object localObject3;
          boolean bool = false;
          continue;
          bool = false;
          continue;
        }
        return bool;
      }
      finally {}
      localObject3 = ((Class)localObject1).getMethod(AppseeBackgroundUploader.i("Td\006,^!^\016I8@,V.FE\034'U0P"), new Class[0]).invoke(null, (Object[])null);
      G = (Application)((Class)localObject1).getMethod(AppseeBackgroundUploader.i("(O8h*O6Vr\025!Y>Z"), new Class[0]).invoke(localObject3, new Object[0]);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/jn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */