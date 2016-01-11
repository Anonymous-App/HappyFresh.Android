package com.parse;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.util.SparseArray;

final class ServiceUtils
{
  private static final String TAG = "com.parse.ServiceUtils";
  private static final String WAKE_LOCK_EXTRA = "parseWakeLockId";
  private static int wakeLockId = 0;
  private static final SparseArray<ParseWakeLock> wakeLocks = new SparseArray();
  
  public static void completeWakefulIntent(Intent arg0)
  {
    int i;
    if ((??? != null) && (???.hasExtra("parseWakeLockId"))) {
      i = ???.getIntExtra("parseWakeLockId", -1);
    }
    synchronized (wakeLocks)
    {
      ParseWakeLock localParseWakeLock = (ParseWakeLock)wakeLocks.get(i);
      wakeLocks.remove(i);
      if (localParseWakeLock == null)
      {
        PLog.e("com.parse.ServiceUtils", "Got wake lock id of " + i + " in intent, but no such lock found in " + "global map. Was completeWakefulIntent called twice for the same intent?");
        return;
      }
    }
    ((ParseWakeLock)localObject).release();
  }
  
  public static boolean runIntentInService(Context paramContext, Intent paramIntent, Class<? extends Service> paramClass)
  {
    boolean bool2 = false;
    if (paramIntent != null)
    {
      if (paramClass != null) {
        paramIntent.setClass(paramContext, paramClass);
      }
      if (paramContext.startService(paramIntent) == null) {
        break label75;
      }
    }
    label75:
    for (boolean bool1 = true;; bool1 = false)
    {
      bool2 = bool1;
      if (!bool1)
      {
        PLog.e("com.parse.ServiceUtils", "Could not start the service. Make sure that the XML tag <service android:name=\"" + paramClass + "\" /> is in your " + "AndroidManifest.xml as a child of the <application> element.");
        bool2 = bool1;
      }
      return bool2;
    }
  }
  
  public static boolean runWakefulIntentInService(Context paramContext, Intent paramIntent, Class<? extends Service> paramClass)
  {
    boolean bool1 = false;
    ParseWakeLock localParseWakeLock;
    if (paramIntent != null) {
      localParseWakeLock = ParseWakeLock.acquireNewWakeLock(paramContext, 1, paramIntent.toString(), 0L);
    }
    synchronized (wakeLocks)
    {
      paramIntent.putExtra("parseWakeLockId", wakeLockId);
      wakeLocks.append(wakeLockId, localParseWakeLock);
      wakeLockId += 1;
      boolean bool2 = runIntentInService(paramContext, paramIntent, paramClass);
      bool1 = bool2;
      if (!bool2)
      {
        completeWakefulIntent(paramIntent);
        bool1 = bool2;
      }
      return bool1;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ServiceUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */