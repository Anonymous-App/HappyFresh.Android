package com.appsee;

import android.view.View;
import java.util.Map;

public final class Appsee
{
  static String G = "2.1.4";
  static final String i = "https://%s.api.appsee.com";
  static String k = "https://api.appsee.com";
  
  public static void addAppseeListener(AppseeListener paramAppseeListener)
  {
    try
    {
      ol.l(paramAppseeListener);
      return;
    }
    catch (Exception paramAppseeListener)
    {
      vd.l(paramAppseeListener, "Fatal error in Appsee:addAppseeListener.");
    }
  }
  
  public static void addEvent(String paramString)
  {
    try
    {
      jc.i().i(paramString, null);
      return;
    }
    catch (Exception paramString)
    {
      vd.l(paramString, "Fatal error in Appsee:addEvent.");
    }
  }
  
  public static void addEvent(String paramString, Map<String, Object> paramMap)
  {
    try
    {
      jc.i().i(paramString, paramMap);
      return;
    }
    catch (Exception paramString)
    {
      vd.l(paramString, "Fatal error in Appsee:addEvent.");
    }
  }
  
  public static void appendSDKType(String paramString)
  {
    if ((lb.i(paramString)) || (G.endsWith(paramString))) {
      return;
    }
    G += paramString;
  }
  
  public static void forceNewSession()
  {
    try
    {
      ol.i(ak.m);
      return;
    }
    catch (Exception localException)
    {
      vd.l(localException, "Fatal error in Appsee:forceNewSession.");
    }
  }
  
  public static String generate3rdPartyId(String paramString, boolean paramBoolean)
  {
    try
    {
      paramString = mb.i().i(paramString, paramBoolean);
      return paramString;
    }
    catch (Exception paramString)
    {
      vd.l(paramString, "Fatal error in Appsee:generate3rdPartyId.");
    }
    return null;
  }
  
  public static boolean getOptOutStatus()
  {
    try
    {
      boolean bool = xc.i().a();
      return bool;
    }
    catch (Exception localException)
    {
      vd.l(localException, "Fatal error in Appsee:getOptOutStatus.");
    }
    return false;
  }
  
  public static void markViewAsSensitive(View paramView)
  {
    try
    {
      gc.i().l(paramView);
      return;
    }
    catch (Exception paramView)
    {
      vd.l(paramView, "Fatal error in Appsee:markViewAsSensitive.");
    }
  }
  
  public static void pause()
  {
    try
    {
      xc.i().E();
      return;
    }
    catch (Exception localException)
    {
      vd.l(localException, "Fatal error in Appsee:pause.");
    }
  }
  
  public static void removeAppseeListener(AppseeListener paramAppseeListener)
  {
    try
    {
      ol.i(paramAppseeListener);
      return;
    }
    catch (Exception paramAppseeListener)
    {
      vd.l(paramAppseeListener, "Fatal error in Appsee:removeAppseeListener.");
    }
  }
  
  public static void resume()
  {
    try
    {
      xc.i().j();
      return;
    }
    catch (Exception localException)
    {
      vd.l(localException, "Fatal error in Appsee:resume.");
    }
  }
  
  public static void set3rdPartyId(String paramString1, String paramString2, boolean paramBoolean)
  {
    try
    {
      mb.i().i(paramString1, paramString2, paramBoolean);
      return;
    }
    catch (Exception paramString1)
    {
      vd.l(paramString1, "Fatal error in Appsee:set3rdPartyId.");
    }
  }
  
  public static void setDebugToLogcat(boolean paramBoolean)
  {
    if (paramBoolean) {}
    for (int j = 2;; j = 3) {
      try
      {
        mc.i(j);
        return;
      }
      catch (Exception localException)
      {
        vd.l(localException, "Fatal error in Appsee:setDebugToLog.");
      }
    }
  }
  
  public static void setLocation(double paramDouble1, double paramDouble2, float paramFloat1, float paramFloat2)
  {
    try
    {
      jc.i().i(new pd(paramDouble1, paramDouble2, paramFloat1, paramFloat2));
      return;
    }
    catch (Exception localException)
    {
      vd.l(localException, "Fatal error in Appsee:setLocation.");
    }
  }
  
  public static void setLocationDescription(String paramString)
  {
    try
    {
      jc.i().l(paramString);
      return;
    }
    catch (Exception paramString)
    {
      vd.l(paramString, "Fatal error in Appsee:setLocationDescription.");
    }
  }
  
  public static void setOptOutStatus(boolean paramBoolean)
  {
    try
    {
      xc.i().l(paramBoolean);
      return;
    }
    catch (Exception localException)
    {
      vd.l(localException, "Fatal error in Appsee:setOptOutStatus.");
    }
  }
  
  public static void setSkipStartValidation(boolean paramBoolean)
  {
    xc.i().k(paramBoolean);
  }
  
  public static void setUserId(String paramString)
  {
    try
    {
      jc.i().i(paramString);
      return;
    }
    catch (Exception paramString)
    {
      vd.l(paramString, "Fatal error in Appsee:setUserId.");
    }
  }
  
  public static void start(String paramString)
  {
    if (lb.i(paramString)) {
      throw new NullPointerException("apiKey cannot be null or empty");
    }
    try
    {
      k = String.format("https://%s.api.appsee.com", new Object[] { paramString });
      mc.l("Starting Appsee v" + G, 2);
      xc.i().l(paramString);
      return;
    }
    catch (Exception paramString)
    {
      vd.l(paramString, "Fatal error in Appsee:start.");
    }
  }
  
  public static void startScreen(String paramString)
  {
    try
    {
      sc.i().i(paramString, vc.b, false);
      return;
    }
    catch (Exception paramString)
    {
      vd.l(paramString, "Fatal error in Appsee:startScreen.");
    }
  }
  
  public static void stop()
  {
    try
    {
      xc.i().F();
      return;
    }
    catch (Exception localException)
    {
      vd.l(localException, "Fatal error in Appsee:stop.");
    }
  }
  
  public static void stopAndUpload()
  {
    try
    {
      xc.i().a(true);
      return;
    }
    catch (Exception localException)
    {
      vd.l(localException, "Fatal error in Appsee:stopAndUpload.");
    }
  }
  
  public static void unmarkViewAsSensitive(View paramView)
  {
    try
    {
      gc.i().i(paramView);
      return;
    }
    catch (Exception paramView)
    {
      vd.l(paramView, "Fatal error in Appsee:unmarkViewAsSensitive.");
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/Appsee.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */