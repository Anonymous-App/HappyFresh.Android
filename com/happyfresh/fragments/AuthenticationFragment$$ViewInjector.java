package com.happyfresh.fragments;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;
import com.happyfresh.customs.CircularProgressBar;

public class AuthenticationFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, AuthenticationFragment paramAuthenticationFragment, Object paramObject)
  {
    View localView = paramFinder.findRequiredView(paramObject, 2131558615, "field 'mTxtAlreadyHaveAccount' and method 'gotoLoginActivity'");
    paramAuthenticationFragment.mTxtAlreadyHaveAccount = ((TextView)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.gotoLoginActivity();
      }
    });
    localView = paramFinder.findRequiredView(paramObject, 2131558610, "field 'mSignUpFB' and method 'loginFacebook'");
    paramAuthenticationFragment.mSignUpFB = ((Button)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.loginFacebook();
      }
    });
    localView = paramFinder.findRequiredView(paramObject, 2131558613, "field 'mSignUpEmail' and method 'signUpWithEmail'");
    paramAuthenticationFragment.mSignUpEmail = ((Button)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.signUpWithEmail();
      }
    });
    paramAuthenticationFragment.mSignUpEmailProgressBar = ((CircularProgressBar)paramFinder.findRequiredView(paramObject, 2131558614, "field 'mSignUpEmailProgressBar'"));
    paramAuthenticationFragment.mSignUpFBProgressBar = ((CircularProgressBar)paramFinder.findRequiredView(paramObject, 2131558611, "field 'mSignUpFBProgressBar'"));
  }
  
  public static void reset(AuthenticationFragment paramAuthenticationFragment)
  {
    paramAuthenticationFragment.mTxtAlreadyHaveAccount = null;
    paramAuthenticationFragment.mSignUpFB = null;
    paramAuthenticationFragment.mSignUpEmail = null;
    paramAuthenticationFragment.mSignUpEmailProgressBar = null;
    paramAuthenticationFragment.mSignUpFBProgressBar = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/AuthenticationFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */