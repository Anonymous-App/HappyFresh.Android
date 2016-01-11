package com.happyfresh.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import com.happyfresh.fragments.MyAccountFragment;

public class MyAccountActivity
  extends BaseActivity
{
  protected Fragment createFragment()
  {
    return new MyAccountFragment();
  }
  
  public void initTitle()
  {
    ActionBar localActionBar = getSupportActionBar();
    if (localActionBar != null)
    {
      localActionBar.setDefaultDisplayHomeAsUpEnabled(false);
      localActionBar.setDisplayHomeAsUpEnabled(true);
      localActionBar.setTitle(2131165456);
    }
  }
  
  public void onBackPressed()
  {
    initTitle();
    if (getSupportFragmentManager().getBackStackEntryCount() >= 1)
    {
      sendTracker("Account");
      getSupportFragmentManager().popBackStack();
      return;
    }
    super.onBackPressed();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    initTitle();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/activities/MyAccountActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */