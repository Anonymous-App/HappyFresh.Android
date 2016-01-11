package com.google.android.gms.analytics.internal;

import android.util.Log;
import com.google.android.gms.analytics.Logger;

class zzs
  implements Logger
{
  private boolean zzIz;
  private int zzKR = 2;
  
  public void error(Exception paramException) {}
  
  public void error(String paramString) {}
  
  public int getLogLevel()
  {
    return this.zzKR;
  }
  
  public void info(String paramString) {}
  
  public void setLogLevel(int paramInt)
  {
    this.zzKR = paramInt;
    if (!this.zzIz)
    {
      Log.i((String)zzy.zzLb.get(), "Logger is deprecated. To enable debug logging, please run:\nadb shell setprop log.tag." + (String)zzy.zzLb.get() + " DEBUG");
      this.zzIz = true;
    }
  }
  
  public void verbose(String paramString) {}
  
  public void warn(String paramString) {}
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/analytics/internal/zzs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */