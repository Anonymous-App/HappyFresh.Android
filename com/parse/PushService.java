package com.parse;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import java.util.ArrayList;
import java.util.List;

public final class PushService
  extends Service
{
  static final String ACTION_START_IF_REQUIRED = "com.parse.PushService.startIfRequired";
  private static final String TAG = "com.parse.PushService";
  private static boolean loggedStartError = false;
  private static List<ServiceLifecycleCallbacks> serviceLifecycleCallbacks = null;
  private ProxyService proxy;
  
  private static Object[] collectServiceLifecycleCallbacks()
  {
    Object[] arrayOfObject = null;
    try
    {
      if (serviceLifecycleCallbacks == null) {
        return null;
      }
      if (serviceLifecycleCallbacks.size() > 0) {
        arrayOfObject = serviceLifecycleCallbacks.toArray();
      }
      return arrayOfObject;
    }
    finally {}
  }
  
  private static void dispatchOnServiceCreated(Service paramService)
  {
    Object[] arrayOfObject = collectServiceLifecycleCallbacks();
    if (arrayOfObject != null)
    {
      int j = arrayOfObject.length;
      int i = 0;
      while (i < j)
      {
        ((ServiceLifecycleCallbacks)arrayOfObject[i]).onServiceCreated(paramService);
        i += 1;
      }
    }
  }
  
  private static void dispatchOnServiceDestroyed(Service paramService)
  {
    Object[] arrayOfObject = collectServiceLifecycleCallbacks();
    if (arrayOfObject != null)
    {
      int j = arrayOfObject.length;
      int i = 0;
      while (i < j)
      {
        ((ServiceLifecycleCallbacks)arrayOfObject[i]).onServiceDestroyed(paramService);
        i += 1;
      }
    }
  }
  
  static void registerServiceLifecycleCallbacks(ServiceLifecycleCallbacks paramServiceLifecycleCallbacks)
  {
    try
    {
      if (serviceLifecycleCallbacks == null) {
        serviceLifecycleCallbacks = new ArrayList();
      }
      serviceLifecycleCallbacks.add(paramServiceLifecycleCallbacks);
      return;
    }
    finally {}
  }
  
  static void startServiceIfRequired(Context paramContext)
  {
    switch (ManifestInfo.getPushType())
    {
    default: 
      if (!loggedStartError)
      {
        PLog.e("com.parse.PushService", "Tried to use push, but this app is not configured for push due to: " + ManifestInfo.getNonePushTypeLogMessage());
        loggedStartError = true;
      }
      return;
    case ???: 
      ParseInstallation localParseInstallation = ParseInstallation.getCurrentInstallation();
      if (localParseInstallation.getPushType() == PushType.GCM)
      {
        PLog.w("com.parse.PushService", "Detected a client that used to use GCM and is now using PPNS.");
        localParseInstallation.removePushType();
        localParseInstallation.removeDeviceToken();
        localParseInstallation.saveEventually();
      }
      ServiceUtils.runIntentInService(paramContext, new Intent("com.parse.PushService.startIfRequired"), PushService.class);
      return;
    }
    GcmRegistrar.getInstance().registerAsync();
  }
  
  static void stopServiceIfRequired(Context paramContext)
  {
    switch (ManifestInfo.getPushType())
    {
    default: 
      return;
    }
    paramContext.stopService(new Intent(paramContext, PushService.class));
  }
  
  static void unregisterServiceLifecycleCallbacks(ServiceLifecycleCallbacks paramServiceLifecycleCallbacks)
  {
    try
    {
      serviceLifecycleCallbacks.remove(paramServiceLifecycleCallbacks);
      if (serviceLifecycleCallbacks.size() <= 0) {
        serviceLifecycleCallbacks = null;
      }
      return;
    }
    finally {}
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    throw new IllegalArgumentException("You cannot bind directly to the PushService. Use PushService.subscribe instead.");
  }
  
  public void onCreate()
  {
    super.onCreate();
    if (ParsePlugins.Android.get().applicationContext() == null)
    {
      PLog.e("com.parse.PushService", "The Parse push service cannot start because Parse.initialize has not yet been called. If you call Parse.initialize from an Activity's onCreate, that call should instead be in the Application.onCreate. Be sure your Application class is registered in your AndroidManifest.xml with the android:name property of your <application> tag.");
      stopSelf();
      return;
    }
    switch (ManifestInfo.getPushType())
    {
    default: 
      PLog.e("com.parse.PushService", "PushService somehow started even though this device doesn't support push.");
    }
    for (;;)
    {
      if (this.proxy != null) {
        this.proxy.onCreate();
      }
      dispatchOnServiceCreated(this);
      return;
      this.proxy = PPNSUtil.newPPNSService(this);
      continue;
      this.proxy = new GCMService(this);
    }
  }
  
  public void onDestroy()
  {
    if (this.proxy != null) {
      this.proxy.onDestroy();
    }
    dispatchOnServiceDestroyed(this);
    super.onDestroy();
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    switch (ManifestInfo.getPushType())
    {
    default: 
      PLog.e("com.parse.PushService", "Started push service even though no push service is enabled: " + paramIntent);
      ServiceUtils.completeWakefulIntent(paramIntent);
      return 2;
    }
    return this.proxy.onStartCommand(paramIntent, paramInt1, paramInt2);
  }
  
  static abstract interface ServiceLifecycleCallbacks
  {
    public abstract void onServiceCreated(Service paramService);
    
    public abstract void onServiceDestroyed(Service paramService);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/PushService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */