package com.happyfresh.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import com.happyfresh.fragments.WebFragment;

public class WebContentActivity
  extends BaseActivity
{
  private String mTitle;
  private String mUrl;
  
  protected Fragment createFragment()
  {
    Bundle localBundle = getIntent().getExtras();
    this.mTitle = localBundle.getString("ABOUT_TITLE");
    this.mUrl = localBundle.getString("ABOUT_URL");
    return WebFragment.newInstance(this.mUrl);
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    paramBundle = getIntent().getExtras();
    this.mTitle = paramBundle.getString("ABOUT_TITLE");
    this.mUrl = paramBundle.getString("ABOUT_URL");
    paramBundle = getSupportActionBar();
    if (paramBundle != null)
    {
      paramBundle.setDefaultDisplayHomeAsUpEnabled(false);
      paramBundle.setDisplayHomeAsUpEnabled(true);
      paramBundle.setTitle(this.mTitle);
    }
    setToolbarTitle(this.mTitle);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/activities/WebContentActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */