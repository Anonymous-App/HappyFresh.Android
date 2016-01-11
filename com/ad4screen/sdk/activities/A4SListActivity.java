package com.ad4screen.sdk.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import com.ad4screen.sdk.A4S;
import com.ad4screen.sdk.common.annotations.API;

@API
public class A4SListActivity
  extends ListActivity
{
  public A4S getA4S()
  {
    return A4S.get(this);
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }
  
  protected void onNewIntent(Intent paramIntent)
  {
    super.onNewIntent(paramIntent);
    getA4S().setIntent(paramIntent);
  }
  
  protected void onPause()
  {
    super.onPause();
    getA4S().stopActivity(this);
  }
  
  protected void onResume()
  {
    super.onResume();
    getA4S().startActivity(this);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/activities/A4SListActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */