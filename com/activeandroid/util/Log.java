package com.activeandroid.util;

public final class Log
{
  private static boolean sEnabled = false;
  private static String sTag = "ActiveAndroid";
  
  public static int d(String paramString)
  {
    if (sEnabled) {
      return android.util.Log.d(sTag, paramString);
    }
    return 0;
  }
  
  public static int d(String paramString1, String paramString2)
  {
    if (sEnabled) {
      return android.util.Log.d(paramString1, paramString2);
    }
    return 0;
  }
  
  public static int d(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if (sEnabled) {
      return android.util.Log.d(paramString1, paramString2, paramThrowable);
    }
    return 0;
  }
  
  public static int d(String paramString, Throwable paramThrowable)
  {
    if (sEnabled) {
      return android.util.Log.d(sTag, paramString, paramThrowable);
    }
    return 0;
  }
  
  public static int e(String paramString)
  {
    if (sEnabled) {
      return android.util.Log.e(sTag, paramString);
    }
    return 0;
  }
  
  public static int e(String paramString1, String paramString2)
  {
    if (sEnabled) {
      return android.util.Log.e(paramString1, paramString2);
    }
    return 0;
  }
  
  public static int e(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if (sEnabled) {
      return android.util.Log.e(paramString1, paramString2, paramThrowable);
    }
    return 0;
  }
  
  public static int e(String paramString, Throwable paramThrowable)
  {
    if (sEnabled) {
      return android.util.Log.e(sTag, paramString, paramThrowable);
    }
    return 0;
  }
  
  public static int i(String paramString)
  {
    if (sEnabled) {
      return android.util.Log.i(sTag, paramString);
    }
    return 0;
  }
  
  public static int i(String paramString1, String paramString2)
  {
    if (sEnabled) {
      return android.util.Log.i(paramString1, paramString2);
    }
    return 0;
  }
  
  public static int i(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if (sEnabled) {
      return android.util.Log.i(paramString1, paramString2, paramThrowable);
    }
    return 0;
  }
  
  public static int i(String paramString, Throwable paramThrowable)
  {
    if (sEnabled) {
      return android.util.Log.i(sTag, paramString, paramThrowable);
    }
    return 0;
  }
  
  public static boolean isEnabled()
  {
    return sEnabled;
  }
  
  public static boolean isLoggingEnabled()
  {
    return sEnabled;
  }
  
  public static void setEnabled(boolean paramBoolean)
  {
    sEnabled = paramBoolean;
  }
  
  public static int t(String paramString, Object... paramVarArgs)
  {
    if (sEnabled) {
      return android.util.Log.v("test", String.format(paramString, paramVarArgs));
    }
    return 0;
  }
  
  public static int v(String paramString)
  {
    if (sEnabled) {
      return android.util.Log.v(sTag, paramString);
    }
    return 0;
  }
  
  public static int v(String paramString1, String paramString2)
  {
    if (sEnabled) {
      return android.util.Log.v(paramString1, paramString2);
    }
    return 0;
  }
  
  public static int v(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if (sEnabled) {
      return android.util.Log.v(paramString1, paramString2, paramThrowable);
    }
    return 0;
  }
  
  public static int v(String paramString, Throwable paramThrowable)
  {
    if (sEnabled) {
      return android.util.Log.v(sTag, paramString, paramThrowable);
    }
    return 0;
  }
  
  public static int w(String paramString)
  {
    if (sEnabled) {
      return android.util.Log.w(sTag, paramString);
    }
    return 0;
  }
  
  public static int w(String paramString1, String paramString2)
  {
    if (sEnabled) {
      return android.util.Log.w(paramString1, paramString2);
    }
    return 0;
  }
  
  public static int w(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if (sEnabled) {
      return android.util.Log.w(paramString1, paramString2, paramThrowable);
    }
    return 0;
  }
  
  public static int w(String paramString, Throwable paramThrowable)
  {
    if (sEnabled) {
      return android.util.Log.w(sTag, paramString, paramThrowable);
    }
    return 0;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/activeandroid/util/Log.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */