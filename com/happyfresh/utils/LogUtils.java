package com.happyfresh.utils;

import android.util.Log;

public class LogUtils
{
  private static final String LOG_PREFIX = "HappyFresh:";
  private static final int LOG_PREFIX_LENGTH = "HappyFresh:".length();
  private static final int MAX_LOG_TAG_LENGTH = 23;
  
  public static void LOG(String paramString)
  {
    String str = tagLogger(new Throwable().getStackTrace()[1].getClassName());
    if (paramString != null) {
      Log.d(str, paramString);
    }
  }
  
  public static String tagLogger(String paramString)
  {
    if (paramString.length() > 23 - LOG_PREFIX_LENGTH) {
      return "HappyFresh:" + paramString.substring(23 - LOG_PREFIX_LENGTH);
    }
    return "HappyFresh:" + paramString;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/utils/LogUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */