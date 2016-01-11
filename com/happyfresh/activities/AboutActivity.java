package com.happyfresh.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import com.happyfresh.fragments.AboutFragment;

public class AboutActivity
  extends BaseActivity
{
  protected Fragment createFragment()
  {
    return new AboutFragment();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    paramBundle = getSupportActionBar();
    if (paramBundle != null)
    {
      paramBundle.setDefaultDisplayHomeAsUpEnabled(false);
      paramBundle.setDisplayHomeAsUpEnabled(true);
      paramBundle.setTitle(2131165607);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/activities/AboutActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */