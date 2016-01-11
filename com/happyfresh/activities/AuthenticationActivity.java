package com.happyfresh.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.happyfresh.fragments.AuthenticationFragment;

public class AuthenticationActivity
  extends BaseActivity
{
  protected Fragment createFragment()
  {
    return new AuthenticationFragment();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setToolbarTitle(null);
  }
  
  protected void setContentView()
  {
    setContentView(2130903066);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/activities/AuthenticationActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */