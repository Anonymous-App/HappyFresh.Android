package com.google.android.gms.analytics;

import android.content.Context;
import com.google.android.gms.analytics.internal.zzae;
import java.util.ArrayList;

public class ExceptionReporter
  implements Thread.UncaughtExceptionHandler
{
  private final Context mContext;
  private final Thread.UncaughtExceptionHandler zzIp;
  private final Tracker zzIq;
  private ExceptionParser zzIr;
  private GoogleAnalytics zzIs;
  
  public ExceptionReporter(Tracker paramTracker, Thread.UncaughtExceptionHandler paramUncaughtExceptionHandler, Context paramContext)
  {
    if (paramTracker == null) {
      throw new NullPointerException("tracker cannot be null");
    }
    if (paramContext == null) {
      throw new NullPointerException("context cannot be null");
    }
    this.zzIp = paramUncaughtExceptionHandler;
    this.zzIq = paramTracker;
    this.zzIr = new StandardExceptionParser(paramContext, new ArrayList());
    this.mContext = paramContext.getApplicationContext();
    paramContext = new StringBuilder().append("ExceptionReporter created, original handler is ");
    if (paramUncaughtExceptionHandler == null) {}
    for (paramTracker = "null";; paramTracker = paramUncaughtExceptionHandler.getClass().getName())
    {
      zzae.zzaB(paramTracker);
      return;
    }
  }
  
  public ExceptionParser getExceptionParser()
  {
    return this.zzIr;
  }
  
  public void setExceptionParser(ExceptionParser paramExceptionParser)
  {
    this.zzIr = paramExceptionParser;
  }
  
  public void uncaughtException(Thread paramThread, Throwable paramThrowable)
  {
    Object localObject = "UncaughtException";
    if (this.zzIr != null) {
      if (paramThread == null) {
        break label115;
      }
    }
    label115:
    for (localObject = paramThread.getName();; localObject = null)
    {
      localObject = this.zzIr.getDescription((String)localObject, paramThrowable);
      zzae.zzaB("Reporting uncaught exception: " + (String)localObject);
      this.zzIq.send(new HitBuilders.ExceptionBuilder().setDescription((String)localObject).setFatal(true).build());
      localObject = zzhg();
      ((GoogleAnalytics)localObject).dispatchLocalHits();
      ((GoogleAnalytics)localObject).zzhk();
      if (this.zzIp != null)
      {
        zzae.zzaB("Passing exception to the original handler");
        this.zzIp.uncaughtException(paramThread, paramThrowable);
      }
      return;
    }
  }
  
  GoogleAnalytics zzhg()
  {
    if (this.zzIs == null) {
      this.zzIs = GoogleAnalytics.getInstance(this.mContext);
    }
    return this.zzIs;
  }
  
  Thread.UncaughtExceptionHandler zzhh()
  {
    return this.zzIp;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/analytics/ExceptionReporter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */