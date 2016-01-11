package com.adjust.sdk;

import java.util.HashMap;
import java.util.Map;

public class AdjustEvent
{
  private static ILogger logger = ;
  Map<String, String> callbackParameters;
  String currency;
  String eventToken;
  Map<String, String> partnerParameters;
  Double revenue;
  
  public AdjustEvent(String paramString)
  {
    if (!checkEventToken(paramString, logger)) {
      return;
    }
    this.eventToken = paramString;
  }
  
  private static boolean checkEventToken(String paramString, ILogger paramILogger)
  {
    if (paramString == null)
    {
      paramILogger.error("Missing Event Token", new Object[0]);
      return false;
    }
    if (paramString.length() != 6)
    {
      paramILogger.error("Malformed Event Token '%s'", new Object[] { paramString });
      return false;
    }
    return true;
  }
  
  private boolean checkRevenue(Double paramDouble, String paramString)
  {
    if (paramDouble != null)
    {
      if (paramDouble.doubleValue() < 0.0D)
      {
        logger.error("Invalid amount %.4f", new Object[] { paramDouble });
        return false;
      }
      if (paramString == null)
      {
        logger.error("Currency must be set with revenue", new Object[0]);
        return false;
      }
      if (paramString == "")
      {
        logger.error("Currency is empty", new Object[0]);
        return false;
      }
    }
    else if (paramString != null)
    {
      logger.error("Revenue must be set with currency", new Object[0]);
      return false;
    }
    return true;
  }
  
  private boolean isValidParameter(String paramString1, String paramString2, String paramString3)
  {
    if (paramString1 == null)
    {
      logger.error("%s parameter %s is missing", new Object[] { paramString3, paramString2 });
      return false;
    }
    if (paramString1 == "")
    {
      logger.error("%s parameter %s is empty", new Object[] { paramString3, paramString2 });
      return false;
    }
    return true;
  }
  
  public void addCallbackParameter(String paramString1, String paramString2)
  {
    if (!isValidParameter(paramString1, "key", "Callback")) {}
    do
    {
      do
      {
        return;
      } while (!isValidParameter(paramString2, "value", "Callback"));
      if (this.callbackParameters == null) {
        this.callbackParameters = new HashMap();
      }
    } while ((String)this.callbackParameters.put(paramString1, paramString2) == null);
    logger.warn("key %s was overwritten", new Object[] { paramString1 });
  }
  
  public void addPartnerParameter(String paramString1, String paramString2)
  {
    if (!isValidParameter(paramString1, "key", "Partner")) {}
    do
    {
      do
      {
        return;
      } while (!isValidParameter(paramString2, "value", "Partner"));
      if (this.partnerParameters == null) {
        this.partnerParameters = new HashMap();
      }
    } while ((String)this.partnerParameters.put(paramString1, paramString2) == null);
    logger.warn("key %s was overwritten", new Object[] { paramString1 });
  }
  
  public boolean isValid()
  {
    return this.eventToken != null;
  }
  
  public void setRevenue(double paramDouble, String paramString)
  {
    if (!checkRevenue(Double.valueOf(paramDouble), paramString)) {
      return;
    }
    this.revenue = Double.valueOf(paramDouble);
    this.currency = paramString;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/adjust/sdk/AdjustEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */