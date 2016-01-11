package com.ad4screen.sdk.common;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ServiceInfo;
import com.ad4screen.sdk.A4SApplication;
import com.ad4screen.sdk.A4SIDFVHandler;
import com.ad4screen.sdk.A4SInterstitial;
import com.ad4screen.sdk.A4SPopup;
import com.ad4screen.sdk.A4SService;
import com.ad4screen.sdk.GCMHandler;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.R.style;
import com.ad4screen.sdk.systems.b;
import java.util.List;

public final class f
{
  public static void a(Context paramContext)
  {
    c(paramContext);
    if (b(paramContext)) {
      d(paramContext);
    }
    Log.info("ManifestChecker|Manifest configuration seems to be OK");
  }
  
  private static void a(Context paramContext, Class<?> paramClass, String paramString, String[] paramArrayOfString1, String[] paramArrayOfString2, boolean paramBoolean)
  {
    PackageManager localPackageManager = paramContext.getPackageManager();
    for (;;)
    {
      try
      {
        ActivityInfo localActivityInfo = localPackageManager.getReceiverInfo(new ComponentName(paramContext, paramClass), 0);
        if (localActivityInfo.exported != paramBoolean)
        {
          StringBuilder localStringBuilder = new StringBuilder().append("ManifestChecker|Receiver '").append(paramClass.getName()).append("' must ");
          if (!paramBoolean) {
            continue;
          }
          str = "";
          Log.error(str + "be exported");
        }
        if ((paramString != null) && (!paramString.equals(localActivityInfo.permission))) {
          Log.error("ManifestChecker|Receiver '" + paramClass.getName() + "' must require permission '" + paramString + "' for security reasons");
        }
      }
      catch (PackageManager.NameNotFoundException paramString)
      {
        String str;
        Log.error("ManifestChecker|Receiver '" + paramClass.getName() + "' must be declared in your AndroidManifest.xml file");
        continue;
      }
      catch (RuntimeException paramString)
      {
        if (!(paramString instanceof a)) {
          continue;
        }
        throw paramString;
      }
      paramString = new Intent();
      j = paramArrayOfString2.length;
      i = 0;
      if (i >= j) {
        break;
      }
      paramString.addCategory(paramArrayOfString2[i]);
      i += 1;
      continue;
      str = "not ";
    }
    paramString.setPackage(paramContext.getPackageName());
    int j = paramArrayOfString1.length;
    int i = 0;
    while (i < j)
    {
      paramContext = paramArrayOfString1[i];
      paramString.setAction(paramContext);
      if (localPackageManager.queryBroadcastReceivers(paramString, 32).isEmpty()) {
        Log.error("ManifestChecker|Receiver '" + paramClass.getName() + "' must be declared with an intent filter { action = '" + paramContext + "', categories = ['" + h.a("', '", paramArrayOfString2) + "'] } in your AndroidManifest.xml file");
      }
      i += 1;
    }
  }
  
  private static boolean b(Context paramContext)
  {
    return b.a(paramContext).w != null;
  }
  
  private static void c(Context paramContext)
  {
    Object localObject = b.a(paramContext);
    PackageManager localPackageManager = paramContext.getPackageManager();
    try
    {
      ApplicationInfo localApplicationInfo = localPackageManager.getApplicationInfo(paramContext.getPackageName(), 0);
      if ((localApplicationInfo.className != null) && (localApplicationInfo.className.length() > 0) && (!A4SApplication.class.isAssignableFrom(Class.forName(localApplicationInfo.className)))) {
        Log.warn("ManifestChecker|Your Application class must extend A4SApplication");
      }
      if (((b)localObject).d == null) {
        throw new a("ManifestChecker|PartnerID needs to be properly setup in your AndroidManifest.xml file");
      }
    }
    catch (RuntimeException localRuntimeException4)
    {
      while (!(localRuntimeException4 instanceof a)) {}
      throw localRuntimeException4;
      if (((b)localObject).e == null) {
        throw new a("ManifestChecker|PrivateKey needs to be properly setup in your AndroidManifest.xml file");
      }
      if (!i.a(paramContext, "android.permission.INTERNET")) {
        throw new a("ManifestChecker|Permission 'android.permission.INTERNET' is required in your AndroidManifest.xml file");
      }
      if (!i.a(paramContext, "android.permission.ACCESS_NETWORK_STATE")) {
        throw new a("ManifestChecker|Permission 'android.permission.ACCESS_NETWORK_STATE' is required in your AndroidManifest.xml file");
      }
      try
      {
        localObject = localPackageManager.getServiceInfo(new ComponentName(paramContext, A4SService.class), 0);
        if (!((ServiceInfo)localObject).processName.equals(((ServiceInfo)localObject).packageName + ":A4SService")) {
          throw new a("ManifestChecker|android:process=\":A4SService\" is required into " + A4SService.class.getCanonicalName() + " tag in your AndroidManifest.xml file");
        }
      }
      catch (PackageManager.NameNotFoundException paramContext)
      {
        throw new a("ManifestChecker|'" + A4SService.class.getCanonicalName() + "' service is required in your AndroidManifest.xml file");
      }
      catch (RuntimeException localRuntimeException2)
      {
        if ((localRuntimeException2 instanceof a)) {
          throw localRuntimeException2;
        }
        try
        {
          ActivityInfo localActivityInfo = localPackageManager.getActivityInfo(new ComponentName(paramContext, A4SPopup.class), 0);
          if (localActivityInfo.theme != R.style.com_ad4screen_sdk_theme_popup) {
            Log.warn("ManifestChecker|You should use style/a4s_popup_theme for Popup theme in your AndroidManifest.xml file");
          }
          if (localActivityInfo.taskAffinity != null) {
            throw new a("ManifestChecker|A4SPopup must have taskAffinity set to \"\" (empty)");
          }
        }
        catch (PackageManager.NameNotFoundException paramContext)
        {
          throw new a("ManifestChecker|LaunchActivity '" + A4SPopup.class.getCanonicalName() + "' is required in your AndroidManifest.xml file");
        }
        catch (RuntimeException localRuntimeException3)
        {
          try
          {
            if (localPackageManager.getActivityInfo(new ComponentName(paramContext, A4SInterstitial.class), 0).theme != R.style.com_ad4screen_sdk_theme_interstitial) {
              Log.warn("ManifestChecker|You should use style/a4s_richpush_theme for LandingPage theme in your AndroidManifest.xml file");
            }
            a(paramContext, A4SIDFVHandler.class, null, new String[] { "com.ad4screen.sdk.intent.action.QUERY" }, new String[] { "com.ad4screen.sdk.intent.category.ANONYM_ID" }, true);
            return;
          }
          catch (PackageManager.NameNotFoundException paramContext)
          {
            throw new a("ManifestChecker|LaunchActivity '" + A4SInterstitial.class.getCanonicalName() + "' is required in your AndroidManifest.xml file");
          }
          catch (RuntimeException localRuntimeException1)
          {
            for (;;) {}
          }
        }
      }
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      for (;;) {}
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      for (;;) {}
    }
  }
  
  private static void d(Context paramContext)
  {
    Object localObject = paramContext.getPackageManager();
    String str2;
    String[] arrayOfString;
    try
    {
      ((PackageManager)localObject).getPackageInfo("com.google.android.gsf", 0);
      str2 = paramContext.getPackageName();
      arrayOfString = new String[3];
      arrayOfString[0] = (str2 + ".permission.C2D_MESSAGE");
      arrayOfString[1] = "com.google.android.c2dm.permission.RECEIVE";
      arrayOfString[2] = "android.permission.INTERNET";
      localObject = new String[1];
      localObject[0] = "android.permission.VIBRATE";
      j = arrayOfString.length;
      i = 0;
      while (i < j)
      {
        String str3 = arrayOfString[i];
        if (!i.a(paramContext, str3)) {
          Log.error("ManifestChecker|Permission '" + str3 + "' is required in your AndroidManifest.xml file");
        }
        i += 1;
      }
    }
    catch (PackageManager.NameNotFoundException paramContext)
    {
      Log.info("ManifestChecker|Device does not have package 'com.google.android.gsf', notification integration will not be validated");
      return;
    }
    catch (RuntimeException localRuntimeException)
    {
      while (!(localRuntimeException instanceof a)) {}
      throw localRuntimeException;
    }
    int j = localRuntimeException.length;
    int i = 0;
    while (i < j)
    {
      arrayOfString = localRuntimeException[i];
      if (!i.a(paramContext, arrayOfString)) {
        Log.warn("ManifestChecker|Permission '" + arrayOfString + "' should be used in your AndroidManifest.xml file");
      }
      i += 1;
    }
    String str1 = "com.google.android.c2dm.permission.SEND";
    if (b.a(paramContext).B) {
      str1 = null;
    }
    a(paramContext, GCMHandler.class, str1, new String[] { "com.google.android.c2dm.intent.RECEIVE", "com.google.android.c2dm.intent.REGISTRATION" }, new String[] { str2 }, true);
  }
  
  private static class a
    extends RuntimeException
  {
    public a(String paramString)
    {
      super();
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */