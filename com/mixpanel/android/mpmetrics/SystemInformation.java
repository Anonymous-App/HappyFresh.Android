package com.mixpanel.android.mpmetrics;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class SystemInformation
{
  private static final String LOGTAG = "MixpanelAPI.SysInfo";
  private final Integer mAppVersionCode;
  private final String mAppVersionName;
  private final Context mContext;
  private final DisplayMetrics mDisplayMetrics;
  private final Boolean mHasNFC;
  private final Boolean mHasTelephony;
  
  public SystemInformation(Context paramContext)
  {
    this.mContext = paramContext;
    PackageManager localPackageManager = this.mContext.getPackageManager();
    Object localObject1 = null;
    localObject3 = null;
    paramContext = (Context)localObject1;
    try
    {
      localObject4 = localPackageManager.getPackageInfo(this.mContext.getPackageName(), 0);
      paramContext = (Context)localObject1;
      localObject1 = ((PackageInfo)localObject4).versionName;
      paramContext = (Context)localObject1;
      int i = ((PackageInfo)localObject4).versionCode;
      localObject3 = Integer.valueOf(i);
      paramContext = (Context)localObject1;
      localObject1 = localObject3;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      for (;;)
      {
        Object localObject4;
        Log.w("MixpanelAPI.SysInfo", "System information constructed with a context that apparently doesn't exist.");
        localObject2 = localObject3;
      }
    }
    this.mAppVersionName = paramContext;
    this.mAppVersionCode = ((Integer)localObject1);
    paramContext = localPackageManager.getClass();
    localObject4 = null;
    try
    {
      paramContext = paramContext.getMethod("hasSystemFeature", new Class[] { String.class });
      localObject4 = paramContext;
    }
    catch (NoSuchMethodException paramContext)
    {
      Object localObject6;
      Object localObject5;
      for (;;) {}
    }
    localObject6 = null;
    localObject1 = null;
    paramContext = null;
    localObject5 = null;
    localObject3 = localObject5;
    if (localObject4 != null)
    {
      paramContext = (Context)localObject6;
      localObject3 = localObject1;
    }
    try
    {
      localObject1 = (Boolean)((Method)localObject4).invoke(localPackageManager, new Object[] { "android.hardware.nfc" });
      paramContext = (Context)localObject1;
      localObject3 = localObject1;
      localObject4 = (Boolean)((Method)localObject4).invoke(localPackageManager, new Object[] { "android.hardware.telephony" });
      localObject3 = localObject4;
      paramContext = (Context)localObject1;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      for (;;)
      {
        Object localObject2;
        Log.w("MixpanelAPI.SysInfo", "System version appeared to support PackageManager.hasSystemFeature, but we were unable to call it.");
        localObject3 = localObject5;
      }
    }
    catch (IllegalAccessException paramContext)
    {
      for (;;)
      {
        Log.w("MixpanelAPI.SysInfo", "System version appeared to support PackageManager.hasSystemFeature, but we were unable to call it.");
        paramContext = (Context)localObject3;
        localObject3 = localObject5;
      }
    }
    this.mHasNFC = paramContext;
    this.mHasTelephony = ((Boolean)localObject3);
    this.mDisplayMetrics = new DisplayMetrics();
    ((WindowManager)this.mContext.getSystemService("window")).getDefaultDisplay().getMetrics(this.mDisplayMetrics);
  }
  
  public Integer getAppVersionCode()
  {
    return this.mAppVersionCode;
  }
  
  public String getAppVersionName()
  {
    return this.mAppVersionName;
  }
  
  public String getBluetoothVersion()
  {
    String str = null;
    if (Build.VERSION.SDK_INT >= 8)
    {
      str = "none";
      if ((Build.VERSION.SDK_INT < 18) || (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le"))) {
        break label41;
      }
      str = "ble";
    }
    label41:
    while (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.bluetooth")) {
      return str;
    }
    return "classic";
  }
  
  public String getCurrentNetworkOperator()
  {
    String str = null;
    TelephonyManager localTelephonyManager = (TelephonyManager)this.mContext.getSystemService("phone");
    if (localTelephonyManager != null) {
      str = localTelephonyManager.getNetworkOperatorName();
    }
    return str;
  }
  
  public DisplayMetrics getDisplayMetrics()
  {
    return this.mDisplayMetrics;
  }
  
  public String getPhoneRadioType()
  {
    TelephonyManager localTelephonyManager = (TelephonyManager)this.mContext.getSystemService("phone");
    if (localTelephonyManager != null) {}
    switch (localTelephonyManager.getPhoneType())
    {
    default: 
      return null;
    case 0: 
      return "none";
    case 1: 
      return "gsm";
    case 2: 
      return "cdma";
    }
    return "sip";
  }
  
  public boolean hasNFC()
  {
    return this.mHasNFC.booleanValue();
  }
  
  public boolean hasTelephony()
  {
    return this.mHasTelephony.booleanValue();
  }
  
  public Boolean isBluetoothEnabled()
  {
    Boolean localBoolean = null;
    try
    {
      BluetoothAdapter localBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
      if (localBluetoothAdapter != null)
      {
        boolean bool = localBluetoothAdapter.isEnabled();
        localBoolean = Boolean.valueOf(bool);
      }
      return localBoolean;
    }
    catch (SecurityException localSecurityException) {}
    return null;
  }
  
  public Boolean isWifiConnected()
  {
    Boolean localBoolean = null;
    if (this.mContext.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") == 0) {
      localBoolean = Boolean.valueOf(((ConnectivityManager)this.mContext.getSystemService("connectivity")).getNetworkInfo(1).isConnected());
    }
    return localBoolean;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/mpmetrics/SystemInformation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */