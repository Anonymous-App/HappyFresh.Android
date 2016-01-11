package com.appsee;

import android.app.Activity;
import android.app.Application;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class bc
{
  public static <T> T i(Object paramObject, String paramString)
    throws Exception
  {
    paramString = i(paramObject.getClass(), paramString);
    if (paramString != null)
    {
      paramString.setAccessible(true);
      return (T)paramString.get(paramObject);
    }
    return null;
  }
  
  public static Object i(Object paramObject, String paramString, int paramInt, Object... paramVarArgs)
    throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException
  {
    Object[] arrayOfObject = new Object[paramInt];
    Class[] arrayOfClass = new Class[paramInt];
    int i = 0;
    int j = 0;
    if (i < paramInt)
    {
      arrayOfObject[j] = paramVarArgs[j];
      if (paramVarArgs[j] == null) {}
      for (Class localClass = Object.class;; localClass = i(paramVarArgs[j].getClass()))
      {
        arrayOfClass[j] = localClass;
        i = j + 1;
        j = i;
        break;
      }
    }
    return i(paramObject.getClass(), paramObject, paramString, arrayOfClass, paramVarArgs);
  }
  
  public static Object i(Object paramObject, String paramString, Class<?>[] paramArrayOfClass, Object... paramVarArgs)
    throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException
  {
    return i(paramObject.getClass(), paramObject, paramString, paramArrayOfClass, paramVarArgs);
  }
  
  public static String i(Class<?> paramClass)
  {
    Method[] arrayOfMethod = paramClass.getMethods();
    StringBuilder localStringBuilder = new StringBuilder();
    int m = arrayOfMethod.length;
    int j = 0;
    for (int i = 0; j < m; i = j)
    {
      Method localMethod = arrayOfMethod[i];
      Class[] arrayOfClass = localMethod.getParameterTypes();
      paramClass = "";
      int k = 0;
      for (j = 0; k < arrayOfClass.length; j = k)
      {
        localObject = paramClass;
        if (j > 0) {
          localObject = AppseeBackgroundUploader.i("p\031");
        }
        paramClass = new StringBuilder().insert(0, (String)localObject).append(arrayOfClass[j].getName()).append(AppseeBackgroundUploader.i("}Y.^"));
        k = j + 1;
        paramClass = j;
      }
      paramClass = new StringBuilder().insert(0, localMethod.getReturnType().getName()).append(AppseeBackgroundUploader.i("\031")).append(localMethod.getName()).append(AppseeBackgroundUploader.i("|\021")).append(paramClass);
      Object localObject = AppseeBackgroundUploader.i("u3");
      j = i + 1;
      localStringBuilder.append((String)localObject);
    }
    return localStringBuilder.toString();
  }
  
  public static void i(Object paramObject1, String paramString, Object paramObject2)
    throws Exception
  {
    paramString = i(paramObject1.getClass(), paramString);
    if (paramString != null)
    {
      paramString.setAccessible(true);
      paramString.set(paramObject1, paramObject2);
    }
  }
  
  public static boolean i()
  {
    if (i(Activity.class, AppseeBackgroundUploader.i("5QR\0068Y(\\"), 0) != null) {}
    while (i(Activity.class, AppseeBackgroundUploader.i("5QC\021.M1\\"), 0) != null) {
      return true;
    }
    return false;
  }
  
  public static boolean i(String paramString)
  {
    if (lb.i(paramString)) {}
    for (;;)
    {
      return false;
      StackTraceElement[] arrayOfStackTraceElement = Thread.currentThread().getStackTrace();
      int k = arrayOfStackTraceElement.length;
      int i = 0;
      for (int j = 0; i < k; j = i)
      {
        if (arrayOfStackTraceElement[j].getMethodName().equalsIgnoreCase(paramString)) {
          return true;
        }
        i = j + 1;
      }
    }
  }
  
  public static StackTraceElement l()
  {
    return i(Thread.class, AppseeBackgroundUploader.i("M)]\tK;\\z /Y?\\"), 3);
  }
  
  public static boolean l()
  {
    try
    {
      boolean bool = Application.class.isAssignableFrom(Class.forName(i().getClassName()));
      return bool;
    }
    catch (Exception localException) {}
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/bc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */