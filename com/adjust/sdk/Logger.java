package com.adjust.sdk;

import android.util.Log;
import java.util.Arrays;
import java.util.Locale;

public class Logger
  implements ILogger
{
  private static String formatErrorMessage = "Error formating log message: %s, with params: %s";
  private LogLevel logLevel;
  
  public Logger()
  {
    setLogLevel(LogLevel.INFO);
  }
  
  public void Assert(String paramString, Object... paramVarArgs)
  {
    try
    {
      Log.println(7, "Adjust", String.format(Locale.US, paramString, paramVarArgs));
      return;
    }
    catch (Exception localException)
    {
      Log.e("Adjust", String.format(Locale.US, formatErrorMessage, new Object[] { paramString, Arrays.toString(paramVarArgs) }));
    }
  }
  
  public void debug(String paramString, Object... paramVarArgs)
  {
    if (this.logLevel.androidLogLevel <= 3) {}
    try
    {
      Log.d("Adjust", String.format(Locale.US, paramString, paramVarArgs));
      return;
    }
    catch (Exception localException)
    {
      Log.e("Adjust", String.format(Locale.US, formatErrorMessage, new Object[] { paramString, Arrays.toString(paramVarArgs) }));
    }
  }
  
  public void error(String paramString, Object... paramVarArgs)
  {
    if (this.logLevel.androidLogLevel <= 6) {}
    try
    {
      Log.e("Adjust", String.format(Locale.US, paramString, paramVarArgs));
      return;
    }
    catch (Exception localException)
    {
      Log.e("Adjust", String.format(Locale.US, formatErrorMessage, new Object[] { paramString, Arrays.toString(paramVarArgs) }));
    }
  }
  
  public void info(String paramString, Object... paramVarArgs)
  {
    if (this.logLevel.androidLogLevel <= 4) {}
    try
    {
      Log.i("Adjust", String.format(Locale.US, paramString, paramVarArgs));
      return;
    }
    catch (Exception localException)
    {
      Log.e("Adjust", String.format(Locale.US, formatErrorMessage, new Object[] { paramString, Arrays.toString(paramVarArgs) }));
    }
  }
  
  public void setLogLevel(LogLevel paramLogLevel)
  {
    this.logLevel = paramLogLevel;
  }
  
  public void setLogLevelString(String paramString)
  {
    if (paramString != null) {}
    try
    {
      setLogLevel(LogLevel.valueOf(paramString.toUpperCase(Locale.US)));
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      error("Malformed logLevel '%s', falling back to 'info'", new Object[] { paramString });
    }
  }
  
  public void verbose(String paramString, Object... paramVarArgs)
  {
    if (this.logLevel.androidLogLevel <= 2) {}
    try
    {
      Log.v("Adjust", String.format(Locale.US, paramString, paramVarArgs));
      return;
    }
    catch (Exception localException)
    {
      Log.e("Adjust", String.format(Locale.US, formatErrorMessage, new Object[] { paramString, Arrays.toString(paramVarArgs) }));
    }
  }
  
  public void warn(String paramString, Object... paramVarArgs)
  {
    if (this.logLevel.androidLogLevel <= 5) {}
    try
    {
      Log.w("Adjust", String.format(Locale.US, paramString, paramVarArgs));
      return;
    }
    catch (Exception localException)
    {
      Log.e("Adjust", String.format(Locale.US, formatErrorMessage, new Object[] { paramString, Arrays.toString(paramVarArgs) }));
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/adjust/sdk/Logger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */