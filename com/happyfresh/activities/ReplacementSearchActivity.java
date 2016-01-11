package com.happyfresh.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import com.happyfresh.fragments.ReplacementSearchFragment;

@Deprecated
public class ReplacementSearchActivity
  extends BaseActivity
{
  protected Fragment createFragment()
  {
    return ReplacementSearchFragment.newInstance(Long.valueOf(getIntent().getLongExtra("ICartConstant.LINE_ITEM_REMOTE_ID", -1L)));
  }
  
  public void onBackPressed()
  {
    setResult(-1);
    finish();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    paramBundle = getSupportActionBar();
    if (paramBundle != null)
    {
      paramBundle.setDefaultDisplayHomeAsUpEnabled(false);
      paramBundle.setDisplayHomeAsUpEnabled(true);
      paramBundle.setTitle(null);
    }
  }
  
  protected void setContentView()
  {
    setContentView(2130903069);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/activities/ReplacementSearchActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */