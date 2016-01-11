package com.ad4screen.sdk.common.compatibility;

import android.annotation.TargetApi;
import android.os.HandlerThread;

@TargetApi(5)
public final class g
{
  public static boolean a(HandlerThread paramHandlerThread)
  {
    return paramHandlerThread.quit();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/compatibility/g.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */