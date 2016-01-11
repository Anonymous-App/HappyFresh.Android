package com.parse;

import android.app.Service;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

class PPNSUtil
{
  static String CLASS_PPNS_SERVICE = "com.parse.PPNSService";
  
  public static boolean isPPNSAvailable()
  {
    try
    {
      Class.forName(CLASS_PPNS_SERVICE);
      return true;
    }
    catch (ClassNotFoundException localClassNotFoundException) {}
    return false;
  }
  
  public static ProxyService newPPNSService(Service paramService)
  {
    try
    {
      paramService = (ProxyService)Class.forName(CLASS_PPNS_SERVICE).getDeclaredConstructor(new Class[] { Service.class }).newInstance(new Object[] { paramService });
      return paramService;
    }
    catch (ClassNotFoundException paramService)
    {
      throw new RuntimeException(paramService);
    }
    catch (NoSuchMethodException paramService)
    {
      throw new RuntimeException(paramService);
    }
    catch (InvocationTargetException paramService)
    {
      throw new RuntimeException(paramService);
    }
    catch (InstantiationException paramService)
    {
      throw new RuntimeException(paramService);
    }
    catch (IllegalAccessException paramService)
    {
      throw new RuntimeException(paramService);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/PPNSUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */