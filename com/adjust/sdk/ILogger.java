package com.adjust.sdk;

public abstract interface ILogger
{
  public abstract void Assert(String paramString, Object... paramVarArgs);
  
  public abstract void debug(String paramString, Object... paramVarArgs);
  
  public abstract void error(String paramString, Object... paramVarArgs);
  
  public abstract void info(String paramString, Object... paramVarArgs);
  
  public abstract void setLogLevel(LogLevel paramLogLevel);
  
  public abstract void setLogLevelString(String paramString);
  
  public abstract void verbose(String paramString, Object... paramVarArgs);
  
  public abstract void warn(String paramString, Object... paramVarArgs);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/adjust/sdk/ILogger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */