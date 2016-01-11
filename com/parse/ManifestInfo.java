package com.parse;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Build.VERSION;
import android.os.Bundle;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

class ManifestInfo
{
  private static final int NUMBER_OF_PUSH_INTENTS = 3;
  private static final String TAG = "com.parse.ManifestInfo";
  private static String displayName = null;
  private static int iconId;
  private static long lastModified;
  private static final Object lock = new Object();
  private static PushType pushType;
  static int versionCode;
  static String versionName;
  
  static
  {
    lastModified = -1L;
    versionCode = -1;
    versionName = null;
    iconId = 0;
  }
  
  private static boolean checkReceiver(Class<? extends BroadcastReceiver> paramClass, String paramString, Intent[] paramArrayOfIntent)
  {
    ActivityInfo localActivityInfo = getReceiverInfo(paramClass);
    if (localActivityInfo == null) {}
    while ((paramString != null) && (!paramString.equals(localActivityInfo.permission))) {
      return false;
    }
    int j = paramArrayOfIntent.length;
    int i = 0;
    for (;;)
    {
      if (i >= j) {
        break label78;
      }
      paramString = paramArrayOfIntent[i];
      paramString = getPackageManager().queryBroadcastReceivers(paramString, 0);
      if ((paramString.isEmpty()) || (!checkResolveInfo(paramClass, paramString))) {
        break;
      }
      i += 1;
    }
    label78:
    return true;
  }
  
  private static boolean checkResolveInfo(Class<? extends BroadcastReceiver> paramClass, List<ResolveInfo> paramList)
  {
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      ResolveInfo localResolveInfo = (ResolveInfo)paramList.next();
      if ((localResolveInfo.activityInfo != null) && (paramClass.getCanonicalName().equals(localResolveInfo.activityInfo.name))) {
        return true;
      }
    }
    return false;
  }
  
  private static ManifestCheckResult gcmSupportLevel()
  {
    Context localContext = getContext();
    if (getServiceInfo(PushService.class) == null) {
      return ManifestCheckResult.MISSING_REQUIRED_DECLARATIONS;
    }
    if (!hasRequestedPermissions(localContext, new String[] { "android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE", "android.permission.WAKE_LOCK", "android.permission.GET_ACCOUNTS", "com.google.android.c2dm.permission.RECEIVE", localContext.getPackageName() + ".permission.C2D_MESSAGE" })) {
      return ManifestCheckResult.MISSING_REQUIRED_DECLARATIONS;
    }
    String str = localContext.getPackageName();
    if (!checkReceiver(GcmBroadcastReceiver.class, "com.google.android.c2dm.permission.SEND", new Intent[] { new Intent("com.google.android.c2dm.intent.RECEIVE").setPackage(str).addCategory(str), new Intent("com.google.android.c2dm.intent.REGISTRATION").setPackage(str).addCategory(str) })) {
      return ManifestCheckResult.MISSING_REQUIRED_DECLARATIONS;
    }
    if (!hasGrantedPermissions(localContext, new String[] { "android.permission.VIBRATE" })) {
      return ManifestCheckResult.MISSING_OPTIONAL_DECLARATIONS;
    }
    return ManifestCheckResult.HAS_ALL_DECLARATIONS;
  }
  
  private static ApplicationInfo getApplicationInfo(Context paramContext, int paramInt)
  {
    try
    {
      paramContext = paramContext.getPackageManager().getApplicationInfo(paramContext.getPackageName(), paramInt);
      return paramContext;
    }
    catch (PackageManager.NameNotFoundException paramContext) {}
    return null;
  }
  
  public static Bundle getApplicationMetadata(Context paramContext)
  {
    paramContext = getApplicationInfo(paramContext, 128);
    if (paramContext != null) {
      return paramContext.metaData;
    }
    return null;
  }
  
  private static Context getContext()
  {
    return Parse.getApplicationContext();
  }
  
  public static String getDisplayName(Context paramContext)
  {
    synchronized (lock)
    {
      if (displayName == null)
      {
        ApplicationInfo localApplicationInfo = paramContext.getApplicationInfo();
        displayName = paramContext.getPackageManager().getApplicationLabel(localApplicationInfo).toString();
      }
      return displayName;
    }
  }
  
  private static String getGcmManifestMessage()
  {
    String str1 = getContext().getPackageName();
    String str2 = str1 + ".permission.C2D_MESSAGE";
    return "make sure that these permissions are declared as children of the root <manifest> element:\n\n<uses-permission android:name=\"android.permission.INTERNET\" />\n<uses-permission android:name=\"android.permission.ACCESS_NETWORK_STATE\" />\n<uses-permission android:name=\"android.permission.VIBRATE\" />\n<uses-permission android:name=\"android.permission.WAKE_LOCK\" />\n<uses-permission android:name=\"android.permission.GET_ACCOUNTS\" />\n<uses-permission android:name=\"com.google.android.c2dm.permission.RECEIVE\" />\n<permission android:name=\"" + str2 + "\" " + "android:protectionLevel=\"signature\" />\n" + "<uses-permission android:name=\"" + str2 + "\" />\n" + "\n" + "Also, please make sure that these services and broadcast receivers are declared as " + "children of the <application> element:\n" + "\n" + "<service android:name=\"com.parse.PushService\" />\n" + "<receiver android:name=\"com.parse.GcmBroadcastReceiver\" " + "android:permission=\"com.google.android.c2dm.permission.SEND\">\n" + "  <intent-filter>\n" + "    <action android:name=\"com.google.android.c2dm.intent.RECEIVE\" />\n" + "    <action android:name=\"com.google.android.c2dm.intent.REGISTRATION\" />\n" + "    <category android:name=\"" + str1 + "\" />\n" + "  </intent-filter>\n" + "</receiver>\n" + "<receiver android:name=\"com.parse.ParsePushBroadcastReceiver\"" + " android:exported=false>\n" + "  <intent-filter>\n" + "    <action android:name=\"com.parse.push.intent.RECEIVE\" />\n" + "    <action android:name=\"com.parse.push.intent.OPEN\" />\n" + "    <action android:name=\"com.parse.push.intent.DELETE\" />\n" + "  </intent-filter>\n" + "</receiver>";
  }
  
  public static int getIconId()
  {
    synchronized (lock)
    {
      if (iconId == 0) {
        iconId = getContext().getApplicationInfo().icon;
      }
      return iconId;
    }
  }
  
  static List<ResolveInfo> getIntentReceivers(String... paramVarArgs)
  {
    Context localContext = getContext();
    String str1 = localContext.getPackageName();
    ArrayList localArrayList = new ArrayList();
    int j = paramVarArgs.length;
    int i = 0;
    while (i < j)
    {
      String str2 = paramVarArgs[i];
      localArrayList.addAll(localContext.getPackageManager().queryBroadcastReceivers(new Intent(str2), 32));
      i += 1;
    }
    i = localArrayList.size() - 1;
    while (i >= 0)
    {
      if (!((ResolveInfo)localArrayList.get(i)).activityInfo.packageName.equals(str1)) {
        localArrayList.remove(i);
      }
      i -= 1;
    }
    return localArrayList;
  }
  
  public static long getLastModified()
  {
    synchronized (lock)
    {
      if (lastModified == -1L) {
        lastModified = new File(getContext().getApplicationInfo().sourceDir).lastModified();
      }
      return lastModified;
    }
  }
  
  public static String getNonePushTypeLogMessage()
  {
    return "Push is not configured for this app because the app manifest is missing required declarations. Please add the following declarations to your app manifest to use GCM for push: " + getGcmManifestMessage();
  }
  
  private static PackageInfo getPackageInfo(String paramString)
  {
    try
    {
      paramString = getPackageManager().getPackageInfo(paramString, 0);
      return paramString;
    }
    catch (PackageManager.NameNotFoundException paramString) {}
    return null;
  }
  
  private static PackageManager getPackageManager()
  {
    return getContext().getPackageManager();
  }
  
  private static String getPpnsManifestMessage()
  {
    return "make sure that these permissions are declared as children of the root <manifest> element:\n\n<uses-permission android:name=\"android.permission.INTERNET\" />\n<uses-permission android:name=\"android.permission.ACCESS_NETWORK_STATE\" />\n<uses-permission android:name=\"android.permission.RECEIVE_BOOT_COMPLETED\" />\n<uses-permission android:name=\"android.permission.VIBRATE\" />\n<uses-permission android:name=\"android.permission.WAKE_LOCK\" />\n\nAlso, please make sure that these services and broadcast receivers are declared as children of the <application> element:\n\n<service android:name=\"com.parse.PushService\" />\n<receiver android:name=\"com.parse.ParseBroadcastReceiver\">\n  <intent-filter>\n    <action android:name=\"android.intent.action.BOOT_COMPLETED\" />\n    <action android:name=\"android.intent.action.USER_PRESENT\" />\n  </intent-filter>\n</receiver>\n<receiver android:name=\"com.parse.ParsePushBroadcastReceiver\" android:exported=false>\n  <intent-filter>\n    <action android:name=\"com.parse.push.intent.RECEIVE\" />\n    <action android:name=\"com.parse.push.intent.OPEN\" />\n    <action android:name=\"com.parse.push.intent.DELETE\" />\n  </intent-filter>\n</receiver>";
  }
  
  public static PushType getPushType()
  {
    int j = 1;
    boolean bool1;
    boolean bool2;
    ManifestCheckResult localManifestCheckResult2;
    boolean bool4;
    int i;
    for (;;)
    {
      boolean bool3;
      synchronized (lock)
      {
        if (pushType == null)
        {
          bool1 = isGooglePlayServicesAvailable();
          bool2 = PPNSUtil.isPPNSAvailable();
          bool3 = hasAnyGcmSpecificDeclaration();
          ManifestCheckResult localManifestCheckResult1 = gcmSupportLevel();
          localManifestCheckResult2 = ppnsSupportLevel();
          bool4 = usesPushBroadcastReceivers();
          if (localManifestCheckResult1 == ManifestCheckResult.MISSING_REQUIRED_DECLARATIONS) {
            continue;
          }
          i = 1;
          if (localManifestCheckResult2 == ManifestCheckResult.MISSING_REQUIRED_DECLARATIONS) {
            continue;
          }
          if ((!bool4) || (!bool1) || (i == 0)) {
            continue;
          }
          pushType = PushType.GCM;
          PLog.v("com.parse.ManifestInfo", "Using " + pushType + " for push.");
          if (Parse.getLogLevel() <= 5)
          {
            if ((pushType != PushType.GCM) || (localManifestCheckResult1 != ManifestCheckResult.MISSING_OPTIONAL_DECLARATIONS)) {
              break;
            }
            PLog.w("com.parse.ManifestInfo", "Using GCM for Parse Push, but the app manifest is missing some optional declarations that should be added for maximum reliability. Please " + getGcmManifestMessage());
          }
        }
        label165:
        return pushType;
        i = 0;
        continue;
        j = 0;
        continue;
        if ((bool4) && (bool2) && (j != 0) && ((!bool3) || (!bool1))) {
          pushType = PushType.PPNS;
        }
      }
      pushType = PushType.NONE;
      if ((!bool3) || (i != 0)) {
        break label391;
      }
      PLog.e("com.parse.ManifestInfo", "Cannot use GCM for push because the app manifest is missing some required declarations. Please " + getGcmManifestMessage());
    }
    for (;;)
    {
      label266:
      PLog.e("com.parse.ManifestInfo", "Push is currently disabled. This is probably because you migrated from an older version of Parse. This version of Parse requires your app to have a BroadcastReceiver that handles com.parse.push.intent.RECEIVE, com.parse.push.intent.OPEN, and com.parse.push.intent.DELETE. You can do this by adding these lines to your AndroidManifest.xml:\n\n <receiver android:name=\"com.parse.ParsePushBroadcastReceiver\"\n   android:exported=false>\n  <intent-filter>\n     <action android:name=\"com.parse.push.intent.RECEIVE\" />\n     <action android:name=\"com.parse.push.intent.OPEN\" />\n     <action android:name=\"com.parse.push.intent.DELETE\" />\n   </intent-filter>\n </receiver>");
      break;
      label391:
      do
      {
        do
        {
          if ((bool4) && (i != 0) && (!bool1))
          {
            PLog.e("com.parse.ManifestInfo", "Cannot use GCM for push on this device because Google Play Services is not installed. Install Google Play Service from the Play Store, or enable PPNS as a fallback push service.\nTo enable PPNS as a fallback push service on devices without Google Play Service support, please include PPNS.jar in your application and " + getPpnsManifestMessage());
            break;
          }
          if ((!bool4) || (j == 0) || (bool2)) {
            break;
          }
          PLog.e("com.parse.ManifestInfo", "Cannot use PPNS for push on this device because PPNS is not available. Include PPNS.jar in your application to use PPNS.");
          break;
          if ((pushType != PushType.PPNS) || (localManifestCheckResult2 != ManifestCheckResult.MISSING_OPTIONAL_DECLARATIONS)) {
            break label165;
          }
          PLog.w("com.parse.ManifestInfo", "Using PPNS for push, but the app manifest is missing some optional declarations that should be added for maximum reliability. Please " + getPpnsManifestMessage());
          break label165;
        } while (bool4);
        if (i != 0) {
          break label266;
        }
      } while (j == 0);
    }
  }
  
  private static ActivityInfo getReceiverInfo(Class<? extends BroadcastReceiver> paramClass)
  {
    try
    {
      paramClass = getPackageManager().getReceiverInfo(new ComponentName(getContext(), paramClass), 0);
      return paramClass;
    }
    catch (PackageManager.NameNotFoundException paramClass) {}
    return null;
  }
  
  private static ServiceInfo getServiceInfo(Class<? extends Service> paramClass)
  {
    try
    {
      paramClass = getPackageManager().getServiceInfo(new ComponentName(getContext(), paramClass), 0);
      return paramClass;
    }
    catch (PackageManager.NameNotFoundException paramClass) {}
    return null;
  }
  
  public static int getVersionCode()
  {
    synchronized (lock)
    {
      int i = versionCode;
      if (i == -1) {}
      try
      {
        versionCode = getPackageManager().getPackageInfo(getContext().getPackageName(), 0).versionCode;
        return versionCode;
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        for (;;)
        {
          PLog.e("com.parse.ManifestInfo", "Couldn't find info about own package", localNameNotFoundException);
        }
      }
    }
  }
  
  public static String getVersionName()
  {
    synchronized (lock)
    {
      String str = versionName;
      if (str == null) {}
      try
      {
        versionName = getPackageManager().getPackageInfo(getContext().getPackageName(), 0).versionName;
        return versionName;
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        for (;;)
        {
          PLog.e("com.parse.ManifestInfo", "Couldn't find info about own package", localNameNotFoundException);
        }
      }
    }
  }
  
  private static boolean hasAnyGcmSpecificDeclaration()
  {
    boolean bool = false;
    Context localContext = getContext();
    if (!hasRequestedPermissions(localContext, new String[] { "com.google.android.c2dm.permission.RECEIVE" }))
    {
      if ((!hasRequestedPermissions(localContext, new String[] { localContext.getPackageName() + ".permission.C2D_MESSAGE" })) && (getReceiverInfo(GcmBroadcastReceiver.class) == null)) {}
    }
    else {
      bool = true;
    }
    return bool;
  }
  
  private static boolean hasGrantedPermissions(Context paramContext, String... paramVarArgs)
  {
    String str = paramContext.getPackageName();
    paramContext = paramContext.getPackageManager();
    int j = paramVarArgs.length;
    int i = 0;
    while (i < j)
    {
      if (paramContext.checkPermission(paramVarArgs[i], str) != 0) {
        return false;
      }
      i += 1;
    }
    return true;
  }
  
  static boolean hasIntentReceiver(String paramString)
  {
    return !getIntentReceivers(new String[] { paramString }).isEmpty();
  }
  
  private static boolean hasRequestedPermissions(Context paramContext, String... paramVarArgs)
  {
    String str = paramContext.getPackageName();
    try
    {
      boolean bool = Arrays.asList(paramContext.getPackageManager().getPackageInfo(str, 4096).requestedPermissions).containsAll(Arrays.asList(paramVarArgs));
      return bool;
    }
    catch (PackageManager.NameNotFoundException paramContext)
    {
      PLog.e("com.parse.ManifestInfo", "Couldn't find info about own package", paramContext);
    }
    return false;
  }
  
  private static boolean isGooglePlayServicesAvailable()
  {
    return (Build.VERSION.SDK_INT >= 8) && (getPackageInfo("com.google.android.gsf") != null);
  }
  
  private static ManifestCheckResult ppnsSupportLevel()
  {
    Object localObject = getContext();
    if (getServiceInfo(PushService.class) == null) {
      return ManifestCheckResult.MISSING_REQUIRED_DECLARATIONS;
    }
    if (!hasGrantedPermissions((Context)localObject, new String[] { "android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE", "android.permission.VIBRATE", "android.permission.WAKE_LOCK", "android.permission.RECEIVE_BOOT_COMPLETED" })) {
      return ManifestCheckResult.MISSING_OPTIONAL_DECLARATIONS;
    }
    localObject = ((Context)localObject).getPackageName();
    if (!checkReceiver(ParseBroadcastReceiver.class, null, new Intent[] { new Intent("android.intent.action.BOOT_COMPLETED").setPackage((String)localObject), new Intent("android.intent.action.USER_PRESENT").setPackage((String)localObject) })) {
      return ManifestCheckResult.MISSING_OPTIONAL_DECLARATIONS;
    }
    return ManifestCheckResult.HAS_ALL_DECLARATIONS;
  }
  
  static void setPushType(PushType paramPushType)
  {
    synchronized (lock)
    {
      pushType = paramPushType;
      return;
    }
  }
  
  private static boolean usesPushBroadcastReceivers()
  {
    int j = 0;
    if (hasIntentReceiver("com.parse.push.intent.RECEIVE")) {
      j = 0 + 1;
    }
    int i = j;
    if (hasIntentReceiver("com.parse.push.intent.OPEN")) {
      i = j + 1;
    }
    j = i;
    if (hasIntentReceiver("com.parse.push.intent.DELETE")) {
      j = i + 1;
    }
    if ((j != 0) && (j != 3)) {
      throw new SecurityException("The Parse Push BroadcastReceiver must implement a filter for all of com.parse.push.intent.RECEIVE, com.parse.push.intent.OPEN, and com.parse.push.intent.DELETE");
    }
    return j == 3;
  }
  
  static enum ManifestCheckResult
  {
    HAS_ALL_DECLARATIONS,  MISSING_OPTIONAL_DECLARATIONS,  MISSING_REQUIRED_DECLARATIONS;
    
    private ManifestCheckResult() {}
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ManifestInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */