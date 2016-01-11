package com.adjust.sdk;

import android.content.Context;
import com.adjust.sdk.plugin.Plugin;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Reflection
{
  public static Object createDefaultInstance(Class paramClass)
  {
    try
    {
      paramClass = paramClass.newInstance();
      return paramClass;
    }
    catch (Throwable paramClass) {}
    return null;
  }
  
  public static Object createDefaultInstance(String paramString)
  {
    return createDefaultInstance(forName(paramString));
  }
  
  public static Object createInstance(String paramString, Class[] paramArrayOfClass, Object... paramVarArgs)
  {
    try
    {
      paramString = Class.forName(paramString).getConstructor(paramArrayOfClass).newInstance(paramVarArgs);
      return paramString;
    }
    catch (Throwable paramString) {}
    return null;
  }
  
  public static Class forName(String paramString)
  {
    try
    {
      paramString = Class.forName(paramString);
      return paramString;
    }
    catch (Throwable paramString) {}
    return null;
  }
  
  private static Object getAdvertisingInfoObject(Context paramContext)
    throws Exception
  {
    return invokeStaticMethod("com.google.android.gms.ads.identifier.AdvertisingIdClient", "getAdvertisingIdInfo", new Class[] { Context.class }, new Object[] { paramContext });
  }
  
  public static String getAndroidId(Context paramContext)
  {
    try
    {
      paramContext = (String)invokeStaticMethod("com.adjust.sdk.plugin.AndroidIdUtil", "getAndroidId", new Class[] { Context.class }, new Object[] { paramContext });
      return paramContext;
    }
    catch (Throwable paramContext) {}
    return null;
  }
  
  public static String getMacAddress(Context paramContext)
  {
    try
    {
      paramContext = (String)invokeStaticMethod("com.adjust.sdk.plugin.MacAddressUtil", "getMacAddress", new Class[] { Context.class }, new Object[] { paramContext });
      return paramContext;
    }
    catch (Throwable paramContext) {}
    return null;
  }
  
  public static String getPlayAdId(Context paramContext)
  {
    try
    {
      paramContext = (String)invokeInstanceMethod(getAdvertisingInfoObject(paramContext), "getId", null, new Object[0]);
      return paramContext;
    }
    catch (Throwable paramContext) {}
    return null;
  }
  
  public static Map<String, String> getPluginKeys(Context paramContext)
  {
    HashMap localHashMap = new HashMap();
    Iterator localIterator = getPlugins().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = ((Plugin)localIterator.next()).getParameter(paramContext);
      if (localEntry != null) {
        localHashMap.put(localEntry.getKey(), localEntry.getValue());
      }
    }
    paramContext = localHashMap;
    if (localHashMap.size() == 0) {
      paramContext = null;
    }
    return paramContext;
  }
  
  private static List<Plugin> getPlugins()
  {
    ArrayList localArrayList = new ArrayList(Constants.PLUGINS.size());
    Iterator localIterator = Constants.PLUGINS.iterator();
    while (localIterator.hasNext())
    {
      Object localObject = createDefaultInstance((String)localIterator.next());
      if ((localObject != null) && ((localObject instanceof Plugin))) {
        localArrayList.add((Plugin)localObject);
      }
    }
    return localArrayList;
  }
  
  public static Object invokeInstanceMethod(Object paramObject, String paramString, Class[] paramArrayOfClass, Object... paramVarArgs)
    throws Exception
  {
    return invokeMethod(paramObject.getClass(), paramString, paramObject, paramArrayOfClass, paramVarArgs);
  }
  
  public static Object invokeMethod(Class paramClass, String paramString, Object paramObject, Class[] paramArrayOfClass, Object... paramVarArgs)
    throws Exception
  {
    return paramClass.getMethod(paramString, paramArrayOfClass).invoke(paramObject, paramVarArgs);
  }
  
  public static Object invokeStaticMethod(String paramString1, String paramString2, Class[] paramArrayOfClass, Object... paramVarArgs)
    throws Exception
  {
    return invokeMethod(Class.forName(paramString1), paramString2, null, paramArrayOfClass, paramVarArgs);
  }
  
  private static boolean isConnectionResultSuccess(Integer paramInteger)
  {
    if (paramInteger == null) {}
    for (;;)
    {
      return false;
      try
      {
        int i = Class.forName("com.google.android.gms.common.ConnectionResult").getField("SUCCESS").getInt(null);
        int j = paramInteger.intValue();
        if (i == j) {
          return true;
        }
      }
      catch (Throwable paramInteger) {}
    }
    return false;
  }
  
  public static boolean isGooglePlayServicesAvailable(Context paramContext)
  {
    try
    {
      boolean bool = Boolean.valueOf(isConnectionResultSuccess((Integer)invokeStaticMethod("com.google.android.gms.common.GooglePlayServicesUtil", "isGooglePlayServicesAvailable", new Class[] { Context.class }, new Object[] { paramContext }))).booleanValue();
      return bool;
    }
    catch (Throwable paramContext) {}
    return false;
  }
  
  public static Boolean isPlayTrackingEnabled(Context paramContext)
  {
    boolean bool = false;
    try
    {
      if (!((Boolean)invokeInstanceMethod(getAdvertisingInfoObject(paramContext), "isLimitAdTrackingEnabled", null, new Object[0])).booleanValue()) {
        bool = true;
      }
      return Boolean.valueOf(bool);
    }
    catch (Throwable paramContext) {}
    return null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/adjust/sdk/Reflection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */