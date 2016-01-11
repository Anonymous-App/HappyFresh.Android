package com.appsee;

import android.util.Log;

class vd
{
  public static void i(String paramString)
  {
    mc.l(paramString, 2);
    if (ge.i().i() != yd.b) {
      hd.i(paramString);
    }
  }
  
  public static void i(Throwable paramThrowable, String paramString)
    throws Exception
  {
    l(paramThrowable, paramString);
    throw new Exception(paramThrowable);
  }
  
  public static void i(Throwable paramThrowable, String paramString, boolean paramBoolean)
  {
    String str = paramString;
    if (paramString == null) {
      str = "";
    }
    paramString = str;
    if (paramThrowable != null) {
      paramString = AppseeBackgroundUploader.i("o\007ll\"\\?Oe\035&Ba\036") + paramThrowable + AppseeBackgroundUploader.i("u4") + Log.getStackTraceString(paramThrowable);
    }
    mc.i(paramString, 2);
    if ((paramBoolean) && (ge.i().i() != yd.b)) {
      hd.i(paramString);
    }
  }
  
  public static void l(Throwable paramThrowable, String paramString)
  {
    i(paramThrowable, paramString, true);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/vd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */