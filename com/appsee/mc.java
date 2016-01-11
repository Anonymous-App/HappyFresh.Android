package com.appsee;

import android.util.Log;
import java.util.EnumSet;

class mc
{
  public static final int G = 1000;
  public static final int K = 3;
  public static final String d = "appsee";
  public static final int k = 1;
  public static final int m = 2;
  
  public static int i()
  {
    return b;
  }
  
  public static void i()
  {
    if (!ge.i().i().contains(vi.b)) {
      return;
    }
    try
    {
      l();
      return;
    }
    catch (Exception localException)
    {
      vd.l(localException, AppseeBackgroundUploader.i("z7]2V$\0255ReT2T(I.^lO(P7\037u\021$^?\\"));
    }
  }
  
  public static void i(int paramInt)
  {
    b = paramInt;
  }
  
  public static void i(String paramString)
  {
    Log.i("appsee", paramString);
  }
  
  public static void i(String paramString, int paramInt)
  {
    i(paramString, null, paramInt, true);
  }
  
  public static void i(String paramString, Exception paramException, int paramInt)
  {
    i(paramString, paramException, paramInt, false);
  }
  
  public static void i(String paramString, Exception paramException, int paramInt, boolean paramBoolean)
  {
    if (paramInt >= b)
    {
      String str = paramString;
      if (paramString == null) {
        str = "";
      }
      paramString = str;
      if (paramException != null) {
        paramString = AppseeBackgroundUploader.i("o\007ll\"\\?Oe\035=Yf\031") + paramException + AppseeBackgroundUploader.i("r3") + Log.getStackTraceString(paramException);
      }
      paramString = Thread.currentThread().getId() + AppseeBackgroundUploader.i("\003") + paramString;
      if ((!paramBoolean) && (paramInt < 3)) {
        break label137;
      }
      Log.e("appsee", paramString);
    }
    for (;;)
    {
      if (i) {
        hd.i(paramString);
      }
      return;
      label137:
      Log.d("appsee", paramString);
    }
  }
  
  public static void i(boolean paramBoolean)
  {
    i = paramBoolean;
  }
  
  public static void l(String paramString, int paramInt)
  {
    i(paramString, null, paramInt, false);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/mc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */