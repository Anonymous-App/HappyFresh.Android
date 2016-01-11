package com.happyfresh.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import com.crashlytics.android.Crashlytics;
import com.happyfresh.fragments.OrderStatusFragment;
import com.happyfresh.utils.LogUtils;

public class OrderStatusActivity
  extends BaseActivity
{
  protected Fragment createFragment()
  {
    String str = getIntent().getExtras().getString("ICartConstant.KEYS.ORDER_NUMBER");
    LogUtils.LOG("ORDER STATUS NUMBER >> " + str);
    Crashlytics.log("ORDER STATUS NUMBER >> " + str);
    return OrderStatusFragment.newInstance(str);
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    paramBundle = getSupportActionBar();
    if (paramBundle != null)
    {
      paramBundle.setDefaultDisplayHomeAsUpEnabled(false);
      paramBundle.setDisplayHomeAsUpEnabled(true);
      paramBundle.setTitle(2131165493);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/activities/OrderStatusActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */