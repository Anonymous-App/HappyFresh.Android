package io.fabric.sdk.android;

public abstract interface Logger
{
  public abstract void d(String paramString1, String paramString2);
  
  public abstract void d(String paramString1, String paramString2, Throwable paramThrowable);
  
  public abstract void e(String paramString1, String paramString2);
  
  public abstract void e(String paramString1, String paramString2, Throwable paramThrowable);
  
  public abstract int getLogLevel();
  
  public abstract void i(String paramString1, String paramString2);
  
  public abstract void i(String paramString1, String paramString2, Throwable paramThrowable);
  
  public abstract boolean isLoggable(String paramString, int paramInt);
  
  public abstract void log(int paramInt, String paramString1, String paramString2);
  
  public abstract void log(int paramInt, String paramString1, String paramString2, boolean paramBoolean);
  
  public abstract void setLogLevel(int paramInt);
  
  public abstract void v(String paramString1, String paramString2);
  
  public abstract void v(String paramString1, String paramString2, Throwable paramThrowable);
  
  public abstract void w(String paramString1, String paramString2);
  
  public abstract void w(String paramString1, String paramString2, Throwable paramThrowable);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/Logger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */