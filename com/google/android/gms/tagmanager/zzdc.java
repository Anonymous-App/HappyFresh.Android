package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;

class zzdc
{
  private Context mContext;
  private Tracker zzIq;
  private GoogleAnalytics zzIs;
  
  zzdc(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  private void zzeH(String paramString)
  {
    try
    {
      if (this.zzIs == null)
      {
        this.zzIs = GoogleAnalytics.getInstance(this.mContext);
        this.zzIs.setLogger(new zza());
        this.zzIq = this.zzIs.newTracker(paramString);
      }
      return;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  public Tracker zzeG(String paramString)
  {
    zzeH(paramString);
    return this.zzIq;
  }
  
  static class zza
    implements Logger
  {
    private static int zzja(int paramInt)
    {
      switch (paramInt)
      {
      case 6: 
      default: 
        return 3;
      case 5: 
        return 2;
      case 3: 
      case 4: 
        return 1;
      }
      return 0;
    }
    
    public void error(Exception paramException)
    {
      zzbg.zzb("", paramException);
    }
    
    public void error(String paramString)
    {
      zzbg.zzaz(paramString);
    }
    
    public int getLogLevel()
    {
      return zzja(zzbg.getLogLevel());
    }
    
    public void info(String paramString)
    {
      zzbg.zzaA(paramString);
    }
    
    public void setLogLevel(int paramInt)
    {
      zzbg.zzaC("GA uses GTM logger. Please use TagManager.setLogLevel(int) instead.");
    }
    
    public void verbose(String paramString)
    {
      zzbg.zzaB(paramString);
    }
    
    public void warn(String paramString)
    {
      zzbg.zzaC(paramString);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzdc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */