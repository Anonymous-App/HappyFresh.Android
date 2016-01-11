package com.ad4screen.sdk.common.compatibility;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.util.Base64;
import com.ad4screen.sdk.common.a;
import com.ad4screen.sdk.common.g;
import java.io.File;

@TargetApi(8)
public final class i
{
  public static Notification a(Context paramContext, int paramInt, String paramString, PendingIntent paramPendingIntent)
  {
    Notification localNotification = new Notification(paramInt, paramString, g.e().b());
    localNotification.contentIntent = paramPendingIntent;
    localNotification.audioStreamType = -1;
    if (com.ad4screen.sdk.common.i.a(paramContext, "android.permission.VIBRATE")) {}
    for (localNotification.defaults = 3;; localNotification.defaults = 1)
    {
      localNotification.flags = 17;
      localNotification.number = 0;
      localNotification.tickerText = paramString;
      localNotification.ledARGB = -1;
      localNotification.ledOffMS = 3000;
      localNotification.ledOnMS = 1000;
      return localNotification;
    }
  }
  
  public static File a(Context paramContext)
  {
    return paramContext.getExternalFilesDir(null);
  }
  
  public static String a(byte[] paramArrayOfByte, int paramInt)
  {
    return Base64.encodeToString(paramArrayOfByte, paramInt);
  }
  
  public static byte[] a(String paramString, int paramInt)
  {
    return Base64.decode(paramString, paramInt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/compatibility/i.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */