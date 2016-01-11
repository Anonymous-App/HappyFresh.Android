package com.adjust.sdk;

import android.content.Context;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;

public class AdjustFactory
{
  private static IActivityHandler activityHandler;
  private static IAttributionHandler attributionHandler;
  private static HttpClient httpClient;
  private static ILogger logger;
  private static IPackageHandler packageHandler = null;
  private static IRequestHandler requestHandler = null;
  private static long sessionInterval = -1L;
  private static long subsessionInterval = -1L;
  private static long timerInterval;
  private static long timerStart;
  
  static
  {
    attributionHandler = null;
    activityHandler = null;
    logger = null;
    httpClient = null;
    timerInterval = -1L;
    timerStart = -1L;
  }
  
  public static IActivityHandler getActivityHandler(AdjustConfig paramAdjustConfig)
  {
    if (activityHandler == null) {
      return ActivityHandler.getInstance(paramAdjustConfig);
    }
    activityHandler.init(paramAdjustConfig);
    return activityHandler;
  }
  
  public static IAttributionHandler getAttributionHandler(IActivityHandler paramIActivityHandler, ActivityPackage paramActivityPackage, boolean paramBoolean)
  {
    if (attributionHandler == null) {
      return new AttributionHandler(paramIActivityHandler, paramActivityPackage, paramBoolean);
    }
    attributionHandler.init(paramIActivityHandler, paramActivityPackage, paramBoolean);
    return attributionHandler;
  }
  
  public static HttpClient getHttpClient(HttpParams paramHttpParams)
  {
    if (httpClient == null) {
      return new DefaultHttpClient(paramHttpParams);
    }
    return httpClient;
  }
  
  public static ILogger getLogger()
  {
    if (logger == null) {
      logger = new Logger();
    }
    return logger;
  }
  
  public static IPackageHandler getPackageHandler(ActivityHandler paramActivityHandler, Context paramContext, boolean paramBoolean)
  {
    if (packageHandler == null) {
      return new PackageHandler(paramActivityHandler, paramContext, paramBoolean);
    }
    packageHandler.init(paramActivityHandler, paramContext, paramBoolean);
    return packageHandler;
  }
  
  public static IRequestHandler getRequestHandler(IPackageHandler paramIPackageHandler)
  {
    if (requestHandler == null) {
      return new RequestHandler(paramIPackageHandler);
    }
    requestHandler.init(paramIPackageHandler);
    return requestHandler;
  }
  
  public static long getSessionInterval()
  {
    if (sessionInterval == -1L) {
      return 1800000L;
    }
    return sessionInterval;
  }
  
  public static long getSubsessionInterval()
  {
    if (subsessionInterval == -1L) {
      return 1000L;
    }
    return subsessionInterval;
  }
  
  public static long getTimerInterval()
  {
    if (timerInterval == -1L) {
      return 60000L;
    }
    return timerInterval;
  }
  
  public static long getTimerStart()
  {
    if (timerStart == -1L) {
      return 0L;
    }
    return timerStart;
  }
  
  public static void setActivityHandler(IActivityHandler paramIActivityHandler)
  {
    activityHandler = paramIActivityHandler;
  }
  
  public static void setAttributionHandler(IAttributionHandler paramIAttributionHandler)
  {
    attributionHandler = paramIAttributionHandler;
  }
  
  public static void setHttpClient(HttpClient paramHttpClient)
  {
    httpClient = paramHttpClient;
  }
  
  public static void setLogger(ILogger paramILogger)
  {
    logger = paramILogger;
  }
  
  public static void setPackageHandler(IPackageHandler paramIPackageHandler)
  {
    packageHandler = paramIPackageHandler;
  }
  
  public static void setRequestHandler(IRequestHandler paramIRequestHandler)
  {
    requestHandler = paramIRequestHandler;
  }
  
  public static void setSessionInterval(long paramLong)
  {
    sessionInterval = paramLong;
  }
  
  public static void setSubsessionInterval(long paramLong)
  {
    subsessionInterval = paramLong;
  }
  
  public static void setTimerInterval(long paramLong)
  {
    timerInterval = paramLong;
  }
  
  public static void setTimerStart(long paramLong)
  {
    timerStart = paramLong;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/adjust/sdk/AdjustFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */