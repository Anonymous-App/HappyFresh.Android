package com.ad4screen.sdk;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.location.Location;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.widget.FrameLayout.LayoutParams;
import com.ad4screen.sdk.analytics.Cart;
import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.analytics.Purchase;
import com.ad4screen.sdk.common.annotations.API;
import com.ad4screen.sdk.common.i;
import com.ad4screen.sdk.plugins.model.Beacon;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@API
public abstract class A4S
{
  private static boolean a = false;
  private static Context b;
  private static Runnable c = new Runnable()
  {
    public void run()
    {
      A4S.a(A4S.a(A4S.d()));
    }
  };
  private static Handler d = new Handler(Looper.getMainLooper());
  private static final Object e = new Object();
  private static A4S f;
  
  private static A4S b(Context paramContext)
  {
    Object localObject;
    if (i.a(paramContext, "com.ad4screen.idsprovider", A4SService.class) != null)
    {
      localObject = com.ad4screen.sdk.systems.b.a(paramContext, true);
      if (((com.ad4screen.sdk.systems.b)localObject).d == null)
      {
        a = false;
        Log.setEnabled(true);
        Log.error("A4S|PartnerID needs to be properly setup in your AndroidManifest.xml file or by your provider");
        Log.error("A4S|Accengage SDK will retry to start in 10 sec");
        if (d != null) {
          d.postDelayed(c, 10000L);
        }
        return new b();
      }
      if (((com.ad4screen.sdk.systems.b)localObject).e == null)
      {
        a = false;
        Log.setEnabled(true);
        Log.error("A4S|PrivateKey needs to be properly setup in your AndroidManifest.xml file or by your provider");
        Log.error("A4S|Accengage SDK will retry to start in 10 sec");
        if (d != null) {
          d.postDelayed(c, 10000L);
        }
        return new b();
      }
      c = null;
      d = null;
    }
    a = true;
    if (!isDoNotTrackEnabled(paramContext)) {}
    try
    {
      if (Build.VERSION.class.getDeclaredField("SDK_INT").getInt(null) >= 4)
      {
        localObject = new a(paramContext, true);
        return (A4S)localObject;
      }
    }
    catch (NoSuchFieldException localNoSuchFieldException)
    {
      Log.error("A4S|Could not check android Build.VERSION.SDK_INT field, cannot start A4SSDK");
      new a(paramContext, false);
      return new b();
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      for (;;)
      {
        Log.error("A4S|Could not check android Build.VERSION.SDK_INT field, cannot start A4SSDK");
      }
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      for (;;)
      {
        Log.error("A4S|Could not check android Build.VERSION.SDK_INT field, cannot start A4SSDK");
      }
    }
  }
  
  private static void b(Context paramContext, boolean paramBoolean1, boolean paramBoolean2)
  {
    synchronized (e)
    {
      Object localObject2 = paramContext.getSharedPreferences("com.ad4screen.sdk.A4S", 0);
      if (((SharedPreferences)localObject2).getBoolean("com.ad4screen.sdk.A4S.doNotTrack", false) == paramBoolean1) {
        return;
      }
      localObject2 = ((SharedPreferences)localObject2).edit();
      ((SharedPreferences.Editor)localObject2).putBoolean("com.ad4screen.sdk.A4S.doNotTrack", paramBoolean1);
      ((SharedPreferences.Editor)localObject2).commit();
      if (f != null)
      {
        f.a(paramContext, paramBoolean1, paramBoolean2);
        f = b(b);
        f.a(paramContext, paramBoolean1, paramBoolean2);
      }
      return;
    }
  }
  
  public static void disableTracking(Context paramContext, boolean paramBoolean)
  {
    b(paramContext, true, paramBoolean);
  }
  
  public static void enableTracking(Context paramContext)
  {
    b(paramContext, false, false);
  }
  
  public static A4S get(Context paramContext)
  {
    try
    {
      b = paramContext;
      if ((f == null) || (!a))
      {
        if (f != null) {
          d.removeCallbacks(c);
        }
        f = b(paramContext);
      }
      paramContext = f;
      return paramContext;
    }
    finally {}
  }
  
  @Deprecated
  public static boolean isDoNotTrackEnabled(Context paramContext)
  {
    return !isTrackingEnabled(paramContext);
  }
  
  public static boolean isInA4SProcess(Context paramContext)
  {
    try
    {
      Object localObject1 = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 4);
      if (((PackageInfo)localObject1).services != null)
      {
        Object localObject2 = ((ActivityManager)paramContext.getSystemService("activity")).getRunningAppProcesses();
        if (localObject2 == null) {
          return false;
        }
        ServiceInfo[] arrayOfServiceInfo = ((PackageInfo)localObject1).services;
        int j = arrayOfServiceInfo.length;
        int i = 0;
        while (i < j)
        {
          paramContext = arrayOfServiceInfo[i];
          if ("com.ad4screen.sdk.A4SService".equals(paramContext.name))
          {
            if ((paramContext.processName == null) || (paramContext.processName.equals(((PackageInfo)localObject1).applicationInfo.processName))) {
              break label182;
            }
            localObject1 = ((List)localObject2).iterator();
            while (((Iterator)localObject1).hasNext())
            {
              localObject2 = (ActivityManager.RunningAppProcessInfo)((Iterator)localObject1).next();
              if (((ActivityManager.RunningAppProcessInfo)localObject2).pid == Process.myPid())
              {
                boolean bool = ((ActivityManager.RunningAppProcessInfo)localObject2).processName.equals(paramContext.processName);
                return bool;
              }
            }
            return false;
          }
          i += 1;
        }
      }
      return false;
    }
    catch (Exception paramContext)
    {
      Log.error("A4S|Error while checking if current process is dedicated to A4SSDK", paramContext);
    }
    label182:
    return false;
  }
  
  public static boolean isTrackingEnabled(Context paramContext)
  {
    boolean bool = false;
    synchronized (e)
    {
      if (!paramContext.getSharedPreferences("com.ad4screen.sdk.A4S", 0).getBoolean("com.ad4screen.sdk.A4S.doNotTrack", false)) {
        bool = true;
      }
      return bool;
    }
  }
  
  @Deprecated
  public static void setDoNotTrackEnabled(Context paramContext, boolean paramBoolean)
  {
    b(paramContext, paramBoolean, false);
  }
  
  protected abstract void a();
  
  protected abstract void a(int paramInt, Callback<Inbox> paramCallback, boolean paramBoolean);
  
  protected abstract void a(Context paramContext, boolean paramBoolean1, boolean paramBoolean2);
  
  protected abstract void a(String paramString);
  
  protected abstract void a(String paramString, Callback<Message> paramCallback);
  
  protected abstract void a(String paramString1, String paramString2, String[] paramArrayOfString);
  
  protected abstract void b();
  
  protected abstract void b(String paramString);
  
  protected abstract void c();
  
  protected abstract void c(String paramString);
  
  protected abstract void d(String paramString);
  
  public abstract void displayInApp(String paramString);
  
  public abstract void getA4SId(Callback<String> paramCallback);
  
  public abstract String getAndroidId();
  
  @API
  protected abstract ArrayList<Beacon> getBeacons();
  
  public abstract void getIDFV(Callback<String> paramCallback);
  
  public abstract void getInbox(Callback<Inbox> paramCallback);
  
  @API
  protected abstract void handleGeofencingMessage(Bundle paramBundle);
  
  @API
  protected abstract void handlePushMessage(Bundle paramBundle);
  
  @Deprecated
  public abstract void isGCMEnabled(Callback<Boolean> paramCallback);
  
  public abstract void isInAppDisplayLocked(Callback<Boolean> paramCallback);
  
  public abstract void isPushEnabled(Callback<Boolean> paramCallback);
  
  public abstract boolean isPushNotificationLocked();
  
  public abstract void isRestrictedConnection(Callback<Boolean> paramCallback);
  
  public abstract void putState(String paramString1, String paramString2);
  
  public abstract void resetOverlayPosition();
  
  @Deprecated
  public abstract void sendGCMToken(String paramString);
  
  public abstract void sendPushToken(String paramString);
  
  @Deprecated
  public abstract void setGCMEnabled(boolean paramBoolean);
  
  public abstract void setInAppClickedCallback(Callback<InApp> paramCallback, int... paramVarArgs);
  
  public abstract void setInAppClosedCallback(Callback<InApp> paramCallback, int... paramVarArgs);
  
  public abstract void setInAppDisplayLocked(boolean paramBoolean);
  
  public abstract void setInAppDisplayedCallback(Callback<InApp> paramCallback, int... paramVarArgs);
  
  public abstract void setInAppReadyCallback(boolean paramBoolean, Callback<InApp> paramCallback, int... paramVarArgs);
  
  public abstract void setIntent(Intent paramIntent);
  
  public abstract void setOverlayPosition(FrameLayout.LayoutParams paramLayoutParams);
  
  public abstract void setPushEnabled(boolean paramBoolean);
  
  public abstract void setPushNotificationLocked(boolean paramBoolean);
  
  public abstract void setRestrictedConnection(boolean paramBoolean);
  
  public abstract void setView(String paramString);
  
  public abstract void startActivity(Activity paramActivity);
  
  public abstract void stopActivity(Activity paramActivity);
  
  public abstract void trackAddToCart(Cart paramCart);
  
  public abstract void trackEvent(long paramLong, String... paramVarArgs);
  
  public abstract void trackLead(Lead paramLead);
  
  public abstract void trackPurchase(Purchase paramPurchase);
  
  @API
  protected abstract void triggerBeacons(Bundle paramBundle);
  
  public abstract void updateDeviceInfo(Bundle paramBundle);
  
  public abstract void updateGeolocation(Location paramLocation);
  
  public abstract void updateMessages(Inbox paramInbox);
  
  @API
  protected abstract void updatePushRegistration(Bundle paramBundle);
  
  @API
  public static abstract interface Callback<T>
  {
    public abstract void onError(int paramInt, String paramString);
    
    public abstract void onResult(T paramT);
  }
  
  @API
  public static abstract interface MessageCallback
  {
    public abstract void onError(int paramInt, String paramString);
    
    public abstract void onResult(Message paramMessage, int paramInt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/A4S.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */