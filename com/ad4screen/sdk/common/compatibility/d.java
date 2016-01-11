package com.ad4screen.sdk.common.compatibility;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.res.Resources;
import android.os.Binder;
import android.webkit.WebView;
import com.ad4screen.sdk.A4SService;
import com.ad4screen.sdk.common.i;
import java.lang.reflect.Method;

@TargetApi(19)
public final class d
{
  public static int a(Context paramContext, String paramString)
  {
    int j = -1;
    int i;
    if (paramString != null)
    {
      i = paramContext.getResources().getIdentifier(paramString, "drawable", paramContext.getPackageName());
      if (i <= 0) {
        break label68;
      }
    }
    for (;;)
    {
      return i;
      paramString = i.a(paramContext, "com.ad4screen.notifications.icon", A4SService.class);
      i = j;
      if (paramString != null)
      {
        int k = paramContext.getResources().getIdentifier(paramString, "drawable", paramContext.getPackageName());
        i = j;
        if (k > 0)
        {
          return k;
          label68:
          i = -1;
        }
      }
    }
  }
  
  public static void a()
  {
    WebView.setWebContentsDebuggingEnabled(true);
  }
  
  public static void a(AlarmManager paramAlarmManager, int paramInt, long paramLong, PendingIntent paramPendingIntent)
  {
    paramAlarmManager.setExact(paramInt, paramLong, paramPendingIntent);
  }
  
  public static boolean a(Context paramContext)
  {
    try
    {
      Object localObject = paramContext.getSystemService("appops");
      int i = ((Integer)localObject.getClass().getMethod("checkOpNoThrow", new Class[] { Integer.TYPE, Integer.TYPE, String.class }).invoke(localObject, new Object[] { Integer.valueOf(11), Integer.valueOf(Binder.getCallingUid()), paramContext.getPackageName() })).intValue();
      return i != 0;
    }
    catch (Error paramContext)
    {
      return false;
    }
    catch (Exception paramContext)
    {
      for (;;) {}
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/compatibility/d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */