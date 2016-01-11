package com.happyfresh.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import com.happyfresh.fragments.ContactUsFragment;

public class ContactUsActivity
  extends BaseActivity
{
  protected Fragment createFragment()
  {
    ContactUsFragment localContactUsFragment = new ContactUsFragment();
    String str = getIntent().getStringExtra("ICartConstant.ORDER_NUMBER");
    if (str != null)
    {
      Bundle localBundle = new Bundle();
      localBundle.putString("ICartConstant.ORDER_NUMBER", str);
      localContactUsFragment.setArguments(localBundle);
    }
    return localContactUsFragment;
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    paramBundle = getSupportActionBar();
    if (paramBundle != null)
    {
      paramBundle.setDefaultDisplayHomeAsUpEnabled(false);
      paramBundle.setDisplayHomeAsUpEnabled(true);
      paramBundle.setTitle(2131165326);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/activities/ContactUsActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */