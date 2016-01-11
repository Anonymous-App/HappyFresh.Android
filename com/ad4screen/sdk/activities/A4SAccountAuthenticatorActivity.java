package com.ad4screen.sdk.activities;

import android.accounts.AccountAuthenticatorActivity;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import com.ad4screen.sdk.A4S;
import com.ad4screen.sdk.common.annotations.API;

@TargetApi(5)
@API
public class A4SAccountAuthenticatorActivity
  extends AccountAuthenticatorActivity
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


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/activities/A4SAccountAuthenticatorActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */