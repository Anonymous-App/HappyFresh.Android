package com.happyfresh.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import com.happyfresh.fragments.CancelOrderConfirmationFragment;
import com.happyfresh.utils.LogUtils;

public class CancelOrderConfirmationActivity
  extends BaseActivity
{
  protected Fragment createFragment()
  {
    CancelOrderConfirmationFragment localCancelOrderConfirmationFragment = new CancelOrderConfirmationFragment();
    String str = getIntent().getExtras().getString("ICartConstant.KEYS.ORDER_NUMBER");
    LogUtils.LOG("ORDER STATUS NUMBER CANCEL >> " + str);
    Bundle localBundle = new Bundle();
    localBundle.putString("ICartConstant.KEYS.ORDER_NUMBER", str);
    localCancelOrderConfirmationFragment.setArguments(localBundle);
    return localCancelOrderConfirmationFragment;
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    paramBundle = getSupportActionBar();
    if (paramBundle != null)
    {
      paramBundle.setDefaultDisplayHomeAsUpEnabled(false);
      paramBundle.setDisplayHomeAsUpEnabled(false);
      paramBundle.setTitle(2131165304);
    }
    this.mToolbar.setVisibility(8);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/activities/CancelOrderConfirmationActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */