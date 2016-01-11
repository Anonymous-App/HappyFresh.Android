package com.adjust.sdk.plugin;

import android.content.Context;
import android.provider.Settings.Secure;

public class AndroidIdUtil
{
  public static String getAndroidId(Context paramContext)
  {
    return Settings.Secure.getString(paramContext.getContentResolver(), "android_id");
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/adjust/sdk/plugin/AndroidIdUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */