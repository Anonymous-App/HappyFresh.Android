package com.happyfresh.fragments;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;
import com.happyfresh.customs.CircularProgressBar;

public class LoginFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, LoginFragment paramLoginFragment, Object paramObject)
  {
    paramLoginFragment.mEmail = ((EditText)paramFinder.findRequiredView(paramObject, 2131558762, "field 'mEmail'"));
    paramLoginFragment.mPassword = ((EditText)paramFinder.findRequiredView(paramObject, 2131558763, "field 'mPassword'"));
    View localView = paramFinder.findRequiredView(paramObject, 2131558765, "field 'mLoginButton' and method 'attemptLogin'");
    paramLoginFragment.mLoginButton = ((Button)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.attemptLogin();
      }
    });
    paramLoginFragment.mLoginProgressBar = ((CircularProgressBar)paramFinder.findRequiredView(paramObject, 2131558766, "field 'mLoginProgressBar'"));
    localView = paramFinder.findRequiredView(paramObject, 2131558768, "field 'forgotPassword' and method 'resetPassword'");
    paramLoginFragment.forgotPassword = localView;
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.resetPassword();
      }
    });
    paramFinder.findRequiredView(paramObject, 2131558767, "method 'gotoSignUpActivity'").setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.gotoSignUpActivity();
      }
    });
  }
  
  public static void reset(LoginFragment paramLoginFragment)
  {
    paramLoginFragment.mEmail = null;
    paramLoginFragment.mPassword = null;
    paramLoginFragment.mLoginButton = null;
    paramLoginFragment.mLoginProgressBar = null;
    paramLoginFragment.forgotPassword = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/LoginFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */