package com.adjust.sdk;

import android.content.Context;

public class AdjustConfig
{
  public static final String ENVIRONMENT_PRODUCTION = "production";
  public static final String ENVIRONMENT_SANDBOX = "sandbox";
  String appToken;
  Context context;
  String defaultTracker;
  String environment;
  Boolean eventBufferingEnabled;
  Boolean knownDevice;
  LogLevel logLevel;
  OnAttributionChangedListener onAttributionChangedListener;
  String referrer;
  long referrerClickTime;
  String sdkPrefix;
  
  public AdjustConfig(Context paramContext, String paramString1, String paramString2)
  {
    if (!isValid(paramContext, paramString1, paramString2)) {
      return;
    }
    this.context = paramContext.getApplicationContext();
    this.appToken = paramString1;
    this.environment = paramString2;
    this.logLevel = LogLevel.INFO;
    this.eventBufferingEnabled = Boolean.valueOf(false);
  }
  
  private static boolean checkAppToken(String paramString)
  {
    ILogger localILogger = AdjustFactory.getLogger();
    if (paramString == null)
    {
      localILogger.error("Missing App Token.", new Object[0]);
      return false;
    }
    if (paramString.length() != 12)
    {
      localILogger.error("Malformed App Token '%s'", new Object[] { paramString });
      return false;
    }
    return true;
  }
  
  private static boolean checkContext(Context paramContext)
  {
    ILogger localILogger = AdjustFactory.getLogger();
    if (paramContext == null)
    {
      localILogger.error("Missing context", new Object[0]);
      return false;
    }
    if (!Util.checkPermission(paramContext, "android.permission.INTERNET"))
    {
      localILogger.error("Missing permission: INTERNET", new Object[0]);
      return false;
    }
    return true;
  }
  
  private static boolean checkEnvironment(String paramString)
  {
    ILogger localILogger = AdjustFactory.getLogger();
    if (paramString == null)
    {
      localILogger.error("Missing environment", new Object[0]);
      return false;
    }
    if (paramString.equals("sandbox"))
    {
      localILogger.Assert("SANDBOX: Adjust is running in Sandbox mode. Use this setting for testing. Don't forget to set the environment to `production` before publishing!", new Object[0]);
      return true;
    }
    if (paramString.equals("production"))
    {
      localILogger.Assert("PRODUCTION: Adjust is running in Production mode. Use this setting only for the build that you want to publish. Set the environment to `sandbox` if you want to test your app!", new Object[0]);
      return true;
    }
    localILogger.error("Unknown environment '%s'", new Object[] { paramString });
    return false;
  }
  
  private boolean isValid(Context paramContext, String paramString1, String paramString2)
  {
    if (!checkAppToken(paramString1)) {}
    while ((!checkEnvironment(paramString2)) || (!checkContext(paramContext))) {
      return false;
    }
    return true;
  }
  
  public boolean hasListener()
  {
    return this.onAttributionChangedListener != null;
  }
  
  public boolean isValid()
  {
    return this.appToken != null;
  }
  
  public void setDefaultTracker(String paramString)
  {
    this.defaultTracker = paramString;
  }
  
  public void setEventBufferingEnabled(Boolean paramBoolean)
  {
    this.eventBufferingEnabled = paramBoolean;
  }
  
  public void setLogLevel(LogLevel paramLogLevel)
  {
    this.logLevel = paramLogLevel;
  }
  
  public void setOnAttributionChangedListener(OnAttributionChangedListener paramOnAttributionChangedListener)
  {
    this.onAttributionChangedListener = paramOnAttributionChangedListener;
  }
  
  public void setSdkPrefix(String paramString)
  {
    this.sdkPrefix = paramString;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/adjust/sdk/AdjustConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */