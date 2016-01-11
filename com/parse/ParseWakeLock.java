package com.parse;

import android.content.Context;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

class ParseWakeLock
{
  private static final String TAG = "com.parse.ParseWakeLock";
  private static volatile boolean hasWakeLockPermission = true;
  private final PowerManager.WakeLock wakeLock;
  
  private ParseWakeLock(PowerManager.WakeLock paramWakeLock)
  {
    this.wakeLock = paramWakeLock;
  }
  
  public static ParseWakeLock acquireNewWakeLock(Context paramContext, int paramInt, String paramString, long paramLong)
  {
    Object localObject2 = null;
    localObject1 = localObject2;
    if (hasWakeLockPermission) {}
    for (;;)
    {
      try
      {
        paramContext = (PowerManager)paramContext.getApplicationContext().getSystemService("power");
        localObject1 = localObject2;
        if (paramContext != null)
        {
          paramContext = paramContext.newWakeLock(paramInt, paramString);
          localObject1 = paramContext;
          if (paramContext != null)
          {
            paramContext.setReferenceCounted(false);
            if (paramLong != 0L) {
              continue;
            }
            paramContext.acquire();
            localObject1 = paramContext;
          }
        }
      }
      catch (SecurityException paramContext)
      {
        PLog.e("com.parse.ParseWakeLock", "Failed to acquire a PowerManager.WakeLock. This isnecessary for reliable handling of pushes. Please add this to your Manifest.xml: <uses-permission android:name=\"android.permission.WAKE_LOCK\" /> ");
        hasWakeLockPermission = false;
        localObject1 = null;
        continue;
      }
      return new ParseWakeLock((PowerManager.WakeLock)localObject1);
      paramContext.acquire(paramLong);
      localObject1 = paramContext;
    }
  }
  
  public void release()
  {
    if (this.wakeLock != null) {
      this.wakeLock.release();
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseWakeLock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */