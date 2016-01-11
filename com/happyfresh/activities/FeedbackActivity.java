package com.happyfresh.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import com.happyfresh.fragments.FeedbackFragment;

public class FeedbackActivity
  extends BaseActivity
{
  protected Fragment createFragment()
  {
    FeedbackFragment localFeedbackFragment = new FeedbackFragment();
    String str = getIntent().getStringExtra("ICartConstant.ORDER_NUMBER");
    if (str != null)
    {
      float f = getIntent().getFloatExtra("ICartConstant.ORDER_STARS", -1.0F);
      Bundle localBundle = new Bundle();
      localBundle.putString("ICartConstant.ORDER_NUMBER", str);
      localBundle.putFloat("ICartConstant.ORDER_STARS", Float.valueOf(f).floatValue());
      localFeedbackFragment.setArguments(localBundle);
    }
    return localFeedbackFragment;
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    paramBundle = getSupportActionBar();
    if (paramBundle != null)
    {
      paramBundle.setDefaultDisplayHomeAsUpEnabled(false);
      paramBundle.setDisplayHomeAsUpEnabled(true);
      paramBundle.setTitle(2131165608);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/activities/FeedbackActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */