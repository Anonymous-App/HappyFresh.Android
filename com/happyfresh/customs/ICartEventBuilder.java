package com.happyfresh.customs;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.analytics.HitBuilders.EventBuilder;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.managers.SharedPreferencesManager;
import com.happyfresh.models.User;

public class ICartEventBuilder
{
  public static HitBuilders.EventBuilder build(Context paramContext)
  {
    HitBuilders.EventBuilder localEventBuilder = new HitBuilders.EventBuilder();
    paramContext = (ICartApplication)paramContext.getApplicationContext();
    String str1 = paramContext.getCountryCode();
    String str2 = paramContext.getSharedPreferencesManager().getStockLocationName();
    paramContext = paramContext.getCurrentUser();
    if (paramContext == null) {}
    for (paramContext = null;; paramContext = paramContext.email)
    {
      if (!TextUtils.isEmpty(str1)) {
        localEventBuilder.setCustomDimension(1, str1);
      }
      if (!TextUtils.isEmpty(str2)) {
        localEventBuilder.setCustomDimension(2, str2);
      }
      if (!TextUtils.isEmpty(paramContext)) {
        localEventBuilder.setCustomDimension(3, paramContext);
      }
      return localEventBuilder;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/customs/ICartEventBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */