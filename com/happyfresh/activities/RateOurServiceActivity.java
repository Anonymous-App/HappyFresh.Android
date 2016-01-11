package com.happyfresh.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.happyfresh.fragments.RateOurServiceFragment;

public class RateOurServiceActivity
  extends BaseActivity
{
  protected Fragment createFragment()
  {
    Bundle localBundle = getIntent().getExtras();
    return RateOurServiceFragment.newInstance(localBundle.getString("ICartConstant.ORDER_NUMBER"), localBundle.getString("ICartConstant.ORDER_PRICE"));
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setToolbarTitle("Rate Our Service");
  }
  
  protected void setContentView()
  {
    setContentView(2130903067);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/activities/RateOurServiceActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */