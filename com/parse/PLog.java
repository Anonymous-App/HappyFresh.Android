package com.parse;

import android.util.Log;

class PLog
{
  public static final int LOG_LEVEL_NONE = Integer.MAX_VALUE;
  private static int logLevel = Integer.MAX_VALUE;
  
  static void d(String paramString1, String paramString2)
  {
    d(paramString1, paramString2, null);
  }
  
  static void d(String paramString1, String paramString2, Throwable paramThrowable)
  {
    log(3, paramString1, paramString2, paramThrowable);
  }
  
  static void e(String paramString1, String paramString2)
  {
    e(paramString1, paramString2, null);
  }
  
  static void e(String paramString1, String paramString2, Throwable paramThrowable)
  {
    log(6, paramString1, paramString2, paramThrowable);
  }
  
  public static int getLogLevel()
  {
    return logLevel;
  }
  
  static void i(String paramString1, String paramString2)
  {
    i(paramString1, paramString2, null);
  }
  
  static void i(String paramString1, String paramString2, Throwable paramThrowable)
  {
    log(4, paramString1, paramString2, paramThrowable);
  }
  
  private static void log(int paramInt, String paramString1, String paramString2, Throwable paramThrowable)
  {
    if (paramInt >= logLevel)
    {
      if (paramThrowable == null) {
        Log.println(logLevel, paramString1, paramString2);
      }
    }
    else {
      return;
    }
    Log.println(logLevel, paramString1, paramString2 + '\n' + Log.getStackTraceString(paramThrowable));
  }
  
  public static void setLogLevel(int paramInt)
  {
    logLevel = paramInt;
  }
  
  static void v(String paramString1, String paramString2)
  {
    v(paramString1, paramString2, null);
  }
  
  static void v(String paramString1, String paramString2, Throwable paramThrowable)
  {
    log(2, paramString1, paramString2, paramThrowable);
  }
  
  static void w(String paramString1, String paramString2)
  {
    w(paramString1, paramString2, null);
  }
  
  static void w(String paramString1, String paramString2, Throwable paramThrowable)
  {
    log(5, paramString1, paramString2, paramThrowable);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/PLog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */