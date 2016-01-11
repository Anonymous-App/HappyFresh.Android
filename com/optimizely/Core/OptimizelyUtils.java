package com.optimizely.Core;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.Display;
import android.view.WindowManager;
import com.google.gson.TypeAdapter;
import com.optimizely.JSON.OptimizelyJSON;
import com.optimizely.Optimizely;
import com.optimizely.crashreporting.OptimizelyUncaughtExceptionHandler;
import com.optimizely.utils.OptimizelyThreadPoolExecutor;
import com.optimizely.utils.VersionResolver;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class OptimizelyUtils
{
  private static boolean FORCE_APP_STORE = false;
  private static final String LOG_TAG = "OptimizelyUtils";
  private static final float SCALING_HIGH_DENSITY = 0.5F;
  private static final float SCALING_LOW_DENSITY = 1.0F;
  private static final int TABLET_CUTOFF = 6;
  public static Boolean sIsAppStoreApp;
  
  static
  {
    if (!OptimizelyUtils.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      FORCE_APP_STORE = false;
      return;
    }
  }
  
  public static String androidVersion()
  {
    if (Build.VERSION.CODENAME.equals("REL")) {
      return Integer.toString(Build.VERSION.SDK_INT);
    }
    return Build.VERSION.CODENAME;
  }
  
  public static String applicationName(@Nullable Context paramContext)
  {
    if (paramContext != null)
    {
      paramContext = paramContext.getApplicationInfo();
      if ((paramContext != null) && (paramContext.packageName != null)) {
        return paramContext.packageName;
      }
    }
    return "com.UnknownApp";
  }
  
  @NonNull
  public static String applicationVersion(@NonNull Optimizely paramOptimizely)
  {
    Object localObject = paramOptimizely.getCurrentContext().getApplicationContext();
    if (localObject != null) {
      try
      {
        localObject = ((Context)localObject).getPackageManager().getPackageInfo(applicationName((Context)localObject), 0);
        if ((localObject != null) && (((PackageInfo)localObject).versionName != null))
        {
          localObject = ((PackageInfo)localObject).versionName;
          return (String)localObject;
        }
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        paramOptimizely.verboseLog("OptimizelyUtils", "Failed to get packaging info", new Object[0]);
      }
    }
    return "0.0.0";
  }
  
  public static int convertDPtoPX(@NonNull Context paramContext, int paramInt)
  {
    float f = paramContext.getResources().getDisplayMetrics().density;
    return (int)(paramInt * f + 0.5F);
  }
  
  public static int convertPXtoDP(@NonNull Context paramContext, int paramInt)
  {
    float f = paramContext.getResources().getDisplayMetrics().density;
    return (int)(paramInt / f + 0.5F);
  }
  
  @NonNull
  public static String deviceId()
  {
    return Build.DISPLAY.replaceAll(" ", "_");
  }
  
  public static String deviceModel()
  {
    return Build.MODEL;
  }
  
  @NonNull
  public static String deviceName()
  {
    return String.format("%s %s", new Object[] { Build.MANUFACTURER.toUpperCase(), Build.MODEL });
  }
  
  public static void disableOptimizely(Optimizely arg0, VersionResolver paramVersionResolver)
  {
    ???.getUserDefaults(???.getCurrentContext().getApplicationContext()).edit().putBoolean("com.optimizely.disable", true).putString("com.optimizely.disable.sdk_version", paramVersionResolver.getSdkVersion()).putString("com.optimizely.disable.app_version", paramVersionResolver.getAppVersion()).commit();
    ???.verboseLog(true, OptimizelyUncaughtExceptionHandler.EXCEPTION_HANDLER_COMPONENT, "Optimizely has crashed and will be disabled", new Object[0]);
    synchronized (OptimizelyUncaughtExceptionHandler.monitor)
    {
      OptimizelyUncaughtExceptionHandler.monitor.notifyAll();
      return;
    }
  }
  
  public static float getDensity(@NonNull Context paramContext)
  {
    return paramContext.getResources().getDisplayMetrics().density;
  }
  
  public static int getDeviceDPI(@NonNull Context paramContext)
  {
    return paramContext.getResources().getDisplayMetrics().densityDpi;
  }
  
  private static Pair<Integer, Integer> getDeviceSizeInPixels(WindowManager paramWindowManager, DisplayMetrics paramDisplayMetrics)
  {
    paramWindowManager = paramWindowManager.getDefaultDisplay();
    paramWindowManager.getMetrics(paramDisplayMetrics);
    int k = paramDisplayMetrics.widthPixels;
    m = paramDisplayMetrics.heightPixels;
    j = m;
    int i = k;
    if (Build.VERSION.SDK_INT >= 14)
    {
      j = m;
      i = k;
      if (Build.VERSION.SDK_INT < 17) {
        i = k;
      }
    }
    try
    {
      j = ((Integer)Display.class.getMethod("getRawWidth", new Class[0]).invoke(paramWindowManager, new Object[0])).intValue();
      i = j;
      k = ((Integer)Display.class.getMethod("getRawHeight", new Class[0]).invoke(paramWindowManager, new Object[0])).intValue();
      i = j;
      j = k;
    }
    catch (Exception paramDisplayMetrics)
    {
      for (;;)
      {
        j = m;
      }
    }
    m = j;
    k = i;
    if (Build.VERSION.SDK_INT >= 17) {
      k = i;
    }
    try
    {
      paramDisplayMetrics = new Point();
      k = i;
      Display.class.getMethod("getRealSize", new Class[] { Point.class }).invoke(paramWindowManager, new Object[] { paramDisplayMetrics });
      k = i;
      i = paramDisplayMetrics.x;
      k = i;
      m = paramDisplayMetrics.y;
      k = i;
    }
    catch (Exception paramWindowManager)
    {
      for (;;)
      {
        m = j;
      }
    }
    return new Pair(Integer.valueOf(k), Integer.valueOf(m));
  }
  
  public static String getLanguage()
  {
    return Locale.getDefault().getLanguage();
  }
  
  public static String getLocale()
  {
    return Locale.getDefault().toString();
  }
  
  public static String getLocaleTag()
  {
    return Locale.getDefault().toString();
  }
  
  @NonNull
  public static Map<String, Integer> getScaledScreenSizeMap(@NonNull Context paramContext)
  {
    Map localMap = getScreenSizeMap(paramContext);
    double d = getScreenshotScaling(paramContext);
    localMap.put("height", Integer.valueOf((int)(((Integer)localMap.get("height")).intValue() * d)));
    localMap.put("width", Integer.valueOf((int)(((Integer)localMap.get("width")).intValue() * d)));
    return localMap;
  }
  
  public static long getScreenSize(@NonNull Context paramContext)
  {
    return paramContext.getResources().getConfiguration().screenLayout & 0xF;
  }
  
  public static Double getScreenSizeInInches(@NonNull Optimizely paramOptimizely)
  {
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    paramOptimizely = getWindowManager(paramOptimizely);
    paramOptimizely.getDefaultDisplay().getMetrics(localDisplayMetrics);
    paramOptimizely = getDeviceSizeInPixels(paramOptimizely, localDisplayMetrics);
    return Double.valueOf(Math.sqrt(Math.pow(((Integer)paramOptimizely.first).intValue() / localDisplayMetrics.xdpi, 2.0D) + Math.pow(((Integer)paramOptimizely.second).intValue() / localDisplayMetrics.ydpi, 2.0D)));
  }
  
  @NonNull
  public static Map<String, Integer> getScreenSizeMap(@NonNull Context paramContext)
  {
    DisplayMetrics localDisplayMetrics = paramContext.getResources().getDisplayMetrics();
    int i = paramContext.getResources().getConfiguration().orientation;
    paramContext = new HashMap();
    switch (i)
    {
    default: 
      return paramContext;
    case 1: 
      paramContext.put("height", Integer.valueOf(localDisplayMetrics.heightPixels));
      paramContext.put("width", Integer.valueOf(localDisplayMetrics.widthPixels));
      return paramContext;
    }
    paramContext.put("height", Integer.valueOf(localDisplayMetrics.widthPixels));
    paramContext.put("width", Integer.valueOf(localDisplayMetrics.heightPixels));
    return paramContext;
  }
  
  public static float getScreenshotScaling(@NonNull Context paramContext)
  {
    paramContext = paramContext.getResources().getDisplayMetrics();
    assert (paramContext != null);
    if (paramContext.densityDpi >= 240) {
      return 0.5F;
    }
    return 1.0F;
  }
  
  @NonNull
  public static Rect getScreenshotSize(@NonNull Context paramContext)
  {
    paramContext = paramContext.getResources().getDisplayMetrics();
    assert (paramContext != null);
    return new Rect(0, 0, paramContext.widthPixels, paramContext.heightPixels);
  }
  
  public static WindowManager getWindowManager(@NonNull Optimizely paramOptimizely)
  {
    return (WindowManager)paramOptimizely.getApplication().getSystemService("window");
  }
  
  public static boolean isAppStoreApp(@NonNull Context paramContext)
  {
    if (sIsAppStoreApp == null)
    {
      paramContext = paramContext.getPackageManager().getInstallerPackageName(paramContext.getPackageName());
      if ((!FORCE_APP_STORE) && ((paramContext == null) || (paramContext.isEmpty()))) {
        break label51;
      }
    }
    label51:
    for (boolean bool = true;; bool = false)
    {
      sIsAppStoreApp = Boolean.valueOf(bool);
      return sIsAppStoreApp.booleanValue();
    }
  }
  
  public static boolean isDisabled(Optimizely paramOptimizely, VersionResolver paramVersionResolver)
  {
    boolean bool2 = false;
    paramOptimizely = paramOptimizely.getUserDefaults(paramOptimizely.getCurrentContext());
    boolean bool1 = bool2;
    if (paramOptimizely.contains("com.optimizely.disable"))
    {
      bool1 = bool2;
      if (paramOptimizely.getBoolean("com.optimizely.disable", false))
      {
        if ((!paramOptimizely.getString("com.optimizely.disable.app_version", "").equals(paramVersionResolver.getAppVersion())) || (!paramOptimizely.getString("com.optimizely.disable.sdk_version", "").equals(paramVersionResolver.getSdkVersion()))) {
          break label84;
        }
        bool1 = true;
      }
    }
    return bool1;
    label84:
    paramOptimizely.edit().remove("com.optimizely.disable").remove("com.optimizely.disable.app_version").remove("com.optimizely.disable.sdk_version").commit();
    return false;
  }
  
  public static boolean isTablet(@NonNull Context paramContext)
  {
    paramContext = paramContext.getResources().getDisplayMetrics();
    assert (paramContext != null);
    float f1 = paramContext.heightPixels / paramContext.ydpi;
    float f2 = paramContext.widthPixels / paramContext.xdpi;
    return Math.sqrt(f2 * f2 + f1 * f1) >= 6.0D;
  }
  
  @TargetApi(11)
  @Nullable
  public static OptimizelyJSON marshallDataFile(final Optimizely paramOptimizely, TypeAdapter<OptimizelyJSON> paramTypeAdapter, String paramString)
  {
    paramTypeAdapter = new AsyncTask()
    {
      protected OptimizelyJSON doInBackground(String... paramAnonymousVarArgs)
      {
        try
        {
          OptimizelyJSON localOptimizelyJSON = (OptimizelyJSON)this.val$gsonAdapter.fromJson(paramAnonymousVarArgs[0]);
          if (localOptimizelyJSON == null) {
            paramOptimizely.verboseLog(true, "OptimizelyData", "Parsing JSON config file returned null", new Object[0]);
          }
          return localOptimizelyJSON;
        }
        catch (IOException localIOException)
        {
          if (!paramAnonymousVarArgs[0].contains("archive")) {
            break label66;
          }
        }
        paramOptimizely.verboseLog(true, "OptimizelyData", "This project token refers to a project that has been archived and is no longer available. Please change your call to startOptimizely to use yournew project's token.", new Object[0]);
        for (;;)
        {
          return null;
          label66:
          paramOptimizely.verboseLog(true, "OptimizelyData", "Parsing JSON data failed: %1$s", new Object[] { localIOException.getLocalizedMessage() });
        }
      }
    };
    paramTypeAdapter.executeOnExecutor(OptimizelyThreadPoolExecutor.instance(), new String[] { paramString });
    try
    {
      paramTypeAdapter = (OptimizelyJSON)paramTypeAdapter.get();
      return paramTypeAdapter;
    }
    catch (InterruptedException paramTypeAdapter)
    {
      paramOptimizely.verboseLog(true, "OptimizelyUtils", "Error running async task marshalDataFile. This means Optimizely won't start. Error: " + paramTypeAdapter.getLocalizedMessage(), new Object[0]);
      return null;
    }
    catch (ExecutionException paramTypeAdapter)
    {
      for (;;) {}
    }
  }
  
  @TargetApi(11)
  @Nullable
  public static String readDataFile(@NonNull File paramFile, @NonNull final Optimizely paramOptimizely)
  {
    paramFile = new AsyncTask()
    {
      protected String doInBackground(Object... paramAnonymousVarArgs)
      {
        if ((!this.val$dataFile.exists()) || (!this.val$dataFile.canRead())) {
          return null;
        }
        try
        {
          paramAnonymousVarArgs = new FileInputStream(this.val$dataFile);
          byte[] arrayOfByte = new byte[(int)this.val$dataFile.length()];
          paramAnonymousVarArgs.read(arrayOfByte);
          paramAnonymousVarArgs.close();
          paramAnonymousVarArgs = new String(arrayOfByte, "UTF-8");
          return paramAnonymousVarArgs;
        }
        catch (FileNotFoundException paramAnonymousVarArgs)
        {
          paramOptimizely.verboseLog(true, "OptimizelyUtils", "Reading data file (%1$s) failed: %2$s", new Object[] { this.val$dataFile.getPath(), paramAnonymousVarArgs.getLocalizedMessage() });
          return null;
        }
        catch (IOException paramAnonymousVarArgs)
        {
          paramOptimizely.verboseLog(true, "OptimizelyUtils", "Reading data file (%1$s) failed: %2$s", new Object[] { this.val$dataFile.getPath(), paramAnonymousVarArgs.getLocalizedMessage() });
        }
        return null;
      }
    };
    paramFile.executeOnExecutor(OptimizelyThreadPoolExecutor.instance(), new Object[0]);
    try
    {
      paramFile = (String)paramFile.get();
      return paramFile;
    }
    catch (Exception paramFile)
    {
      paramOptimizely.verboseLog(true, "OptimizelyUtils", "Error running async task readDataFile. This means we wont get into edit mode. Error: " + paramFile.getLocalizedMessage(), new Object[0]);
    }
    return null;
  }
  
  public static String readInputStream(@NonNull InputStream paramInputStream)
    throws IOException
  {
    BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramInputStream));
    StringBuilder localStringBuilder = new StringBuilder();
    try
    {
      for (;;)
      {
        String str = localBufferedReader.readLine();
        if (str == null) {
          break;
        }
        localStringBuilder.append(str);
      }
    }
    finally
    {
      paramInputStream.close();
    }
    return localStringBuilder.toString();
  }
  
  public static void reset(Optimizely paramOptimizely)
  {
    paramOptimizely.getUserDefaults(paramOptimizely.getCurrentContext()).edit().remove("com.optimizely.disable").remove("com.optimizely.disable.app_version").remove("com.optimizely.disable.sdk_version").commit();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Core/OptimizelyUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */