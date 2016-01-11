package com.happyfresh.utils;

import android.content.Context;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.models.Order;
import com.optimizely.Optimizely;

public class OptimizelyUtils
{
  public static void trackRevenue(Context paramContext)
  {
    paramContext = ((ICartApplication)paramContext.getApplicationContext()).currentOrder.totalUSD;
    if (paramContext != null) {
      Optimizely.trackRevenue(Double.valueOf(paramContext.doubleValue() * 100.0D).intValue());
    }
  }
  
  public static void trackSignUp()
  {
    Optimizely.trackEvent("number_signup");
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/utils/OptimizelyUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */