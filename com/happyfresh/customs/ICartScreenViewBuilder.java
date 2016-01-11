package com.happyfresh.customs;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.analytics.HitBuilders.ScreenViewBuilder;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.managers.SharedPreferencesManager;
import com.happyfresh.models.User;

public class ICartScreenViewBuilder
{
  public static HitBuilders.ScreenViewBuilder build(Context paramContext)
  {
    HitBuilders.ScreenViewBuilder localScreenViewBuilder = new HitBuilders.ScreenViewBuilder();
    paramContext = (ICartApplication)paramContext.getApplicationContext();
    String str1 = paramContext.getCountryCode();
    String str2 = paramContext.getSharedPreferencesManager().getStockLocationName();
    paramContext = paramContext.getCurrentUser();
    if (paramContext == null) {}
    for (paramContext = null;; paramContext = paramContext.email)
    {
      if (!TextUtils.isEmpty(str1)) {
        localScreenViewBuilder.setCustomDimension(1, str1);
      }
      if (!TextUtils.isEmpty(str2)) {
        localScreenViewBuilder.setCustomDimension(2, str2);
      }
      if (!TextUtils.isEmpty(paramContext)) {
        localScreenViewBuilder.setCustomDimension(3, paramContext);
      }
      return localScreenViewBuilder;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/customs/ICartScreenViewBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */