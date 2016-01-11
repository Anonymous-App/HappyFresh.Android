package com.facebook;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.facebook.internal.FacebookDialogFragment;
import com.facebook.internal.NativeProtocol;
import com.facebook.login.LoginFragment;

public class FacebookActivity
  extends FragmentActivity
{
  private static String FRAGMENT_TAG = "SingleFragment";
  public static String PASS_THROUGH_CANCEL_ACTION = "PassThrough";
  private Fragment singleFragment;
  
  private void handlePassThroughError()
  {
    Intent localIntent = getIntent();
    setResult(0, NativeProtocol.createProtocolResultIntent(localIntent, null, NativeProtocol.getExceptionFromErrorData(NativeProtocol.getMethodArgumentsFromIntent(localIntent))));
    finish();
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    if (this.singleFragment != null) {
      this.singleFragment.onConfigurationChanged(paramConfiguration);
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.com_facebook_activity_layout);
    Intent localIntent = getIntent();
    if (PASS_THROUGH_CANCEL_ACTION.equals(localIntent.getAction()))
    {
      handlePassThroughError();
      return;
    }
    FragmentManager localFragmentManager = getSupportFragmentManager();
    Fragment localFragment = localFragmentManager.findFragmentByTag(FRAGMENT_TAG);
    paramBundle = localFragment;
    if (localFragment == null)
    {
      if (!"FacebookDialogFragment".equals(localIntent.getAction())) {
        break label96;
      }
      paramBundle = new FacebookDialogFragment();
      paramBundle.setRetainInstance(true);
      paramBundle.show(localFragmentManager, FRAGMENT_TAG);
    }
    for (;;)
    {
      this.singleFragment = paramBundle;
      return;
      label96:
      paramBundle = new LoginFragment();
      paramBundle.setRetainInstance(true);
      localFragmentManager.beginTransaction().add(R.id.com_facebook_fragment_container, paramBundle, FRAGMENT_TAG).commit();
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/FacebookActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */