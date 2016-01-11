package com.mixpanel.android.viewcrawler;

import android.util.Log;
import android.view.View;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class Caller
{
  private static final String LOGTAG = "MixpanelABTest.Caller";
  private final Object[] mMethodArgs;
  private final String mMethodName;
  private final Class<?> mMethodResultType;
  private final Class<?> mTargetClass;
  private final Method mTargetMethod;
  
  public Caller(Class<?> paramClass1, String paramString, Object[] paramArrayOfObject, Class<?> paramClass2)
    throws NoSuchMethodException
  {
    this.mMethodName = paramString;
    this.mMethodArgs = paramArrayOfObject;
    this.mMethodResultType = paramClass2;
    this.mTargetMethod = pickMethod(paramClass1);
    if (this.mTargetMethod == null) {
      throw new NoSuchMethodException("Method " + paramClass1.getName() + "." + this.mMethodName + " doesn't exit");
    }
    this.mTargetClass = this.mTargetMethod.getDeclaringClass();
  }
  
  private static Class<?> assignableArgType(Class<?> paramClass)
  {
    Object localObject;
    if (paramClass == Byte.class) {
      localObject = Byte.TYPE;
    }
    do
    {
      return (Class<?>)localObject;
      if (paramClass == Short.class) {
        return Short.TYPE;
      }
      if (paramClass == Integer.class) {
        return Integer.TYPE;
      }
      if (paramClass == Long.class) {
        return Long.TYPE;
      }
      if (paramClass == Float.class) {
        return Float.TYPE;
      }
      if (paramClass == Double.class) {
        return Double.TYPE;
      }
      if (paramClass == Boolean.class) {
        return Boolean.TYPE;
      }
      localObject = paramClass;
    } while (paramClass != Character.class);
    return Character.TYPE;
  }
  
  private Method pickMethod(Class<?> paramClass)
  {
    Class[] arrayOfClass1 = new Class[this.mMethodArgs.length];
    int i = 0;
    while (i < this.mMethodArgs.length)
    {
      arrayOfClass1[i] = this.mMethodArgs[i].getClass();
      i += 1;
    }
    paramClass = paramClass.getMethods();
    int k = paramClass.length;
    i = 0;
    if (i < k)
    {
      Method localMethod = paramClass[i];
      Object localObject = localMethod.getName();
      Class[] arrayOfClass2 = localMethod.getParameterTypes();
      if ((!((String)localObject).equals(this.mMethodName)) || (arrayOfClass2.length != this.mMethodArgs.length)) {}
      boolean bool;
      do
      {
        do
        {
          i += 1;
          break;
        } while (!assignableArgType(this.mMethodResultType).isAssignableFrom(assignableArgType(localMethod.getReturnType())));
        bool = true;
        int j = 0;
        while ((j < arrayOfClass2.length) && (bool))
        {
          localObject = assignableArgType(arrayOfClass1[j]);
          bool = assignableArgType(arrayOfClass2[j]).isAssignableFrom((Class)localObject);
          j += 1;
        }
      } while (!bool);
      return localMethod;
    }
    return null;
  }
  
  public Object applyMethod(View paramView)
  {
    return applyMethodWithArguments(paramView, this.mMethodArgs);
  }
  
  public Object applyMethodWithArguments(View paramView, Object[] paramArrayOfObject)
  {
    Class localClass = paramView.getClass();
    if (this.mTargetClass.isAssignableFrom(localClass)) {}
    try
    {
      paramView = this.mTargetMethod.invoke(paramView, paramArrayOfObject);
      return paramView;
    }
    catch (IllegalAccessException paramView)
    {
      Log.e("MixpanelABTest.Caller", "Method " + this.mTargetMethod.getName() + " appears not to be public", paramView);
      return null;
    }
    catch (IllegalArgumentException paramView)
    {
      for (;;)
      {
        Log.e("MixpanelABTest.Caller", "Method " + this.mTargetMethod.getName() + " called with arguments of the wrong type", paramView);
      }
    }
    catch (InvocationTargetException paramView)
    {
      for (;;)
      {
        Log.e("MixpanelABTest.Caller", "Method " + this.mTargetMethod.getName() + " threw an exception", paramView);
      }
    }
  }
  
  public boolean argsAreApplicable(Object[] paramArrayOfObject)
  {
    Class[] arrayOfClass = this.mTargetMethod.getParameterTypes();
    if (paramArrayOfObject.length != arrayOfClass.length) {}
    int i;
    Class localClass;
    do
    {
      return false;
      i = 0;
      if (i >= paramArrayOfObject.length) {
        break label129;
      }
      localClass = assignableArgType(arrayOfClass[i]);
      if (paramArrayOfObject[i] != null) {
        break;
      }
    } while ((localClass == Byte.TYPE) || (localClass == Short.TYPE) || (localClass == Integer.TYPE) || (localClass == Long.TYPE) || (localClass == Float.TYPE) || (localClass == Double.TYPE) || (localClass == Boolean.TYPE) || (localClass == Character.TYPE));
    while (localClass.isAssignableFrom(assignableArgType(paramArrayOfObject[i].getClass())))
    {
      i += 1;
      break;
    }
    return false;
    label129:
    return true;
  }
  
  public Object[] getArgs()
  {
    return this.mMethodArgs;
  }
  
  public String toString()
  {
    return "[Caller " + this.mMethodName + "(" + this.mMethodArgs + ")" + "]";
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/viewcrawler/Caller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */