package com.happyfresh.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import com.happyfresh.fragments.LoginFragment;

public class LoginActivity
  extends BaseActivity
{
  protected Fragment createFragment()
  {
    return new LoginFragment();
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    paramBundle = getSupportActionBar();
    if (paramBundle != null)
    {
      paramBundle.setDefaultDisplayHomeAsUpEnabled(true);
      paramBundle.setDisplayHomeAsUpEnabled(true);
      paramBundle.setHomeAsUpIndicator(2130837765);
      paramBundle.setTitle(null);
    }
  }
  
  protected void setContentView()
  {
    setContentView(2130903066);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/activities/LoginActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */