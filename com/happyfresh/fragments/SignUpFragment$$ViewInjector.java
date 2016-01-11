package com.happyfresh.fragments;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;
import com.happyfresh.customs.CircularProgressBar;

public class SignUpFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, SignUpFragment paramSignUpFragment, Object paramObject)
  {
    paramSignUpFragment.mFirstName = ((EditText)paramFinder.findRequiredView(paramObject, 2131558584, "field 'mFirstName'"));
    paramSignUpFragment.mLastName = ((EditText)paramFinder.findRequiredView(paramObject, 2131558585, "field 'mLastName'"));
    paramSignUpFragment.mEmail = ((EditText)paramFinder.findRequiredView(paramObject, 2131558762, "field 'mEmail'"));
    paramSignUpFragment.mPassword = ((EditText)paramFinder.findRequiredView(paramObject, 2131558763, "field 'mPassword'"));
    paramSignUpFragment.mCode = ((EditText)paramFinder.findRequiredView(paramObject, 2131558866, "field 'mCode'"));
    paramSignUpFragment.mPromoCode = ((EditText)paramFinder.findRequiredView(paramObject, 2131558867, "field 'mPromoCode'"));
    View localView = paramFinder.findRequiredView(paramObject, 2131558767, "field 'mSignUpButton' and method 'attemptSignUp'");
    paramSignUpFragment.mSignUpButton = ((Button)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.attemptSignUp();
      }
    });
    paramSignUpFragment.mSignUpProgressBar = ((CircularProgressBar)paramFinder.findRequiredView(paramObject, 2131558869, "field 'mSignUpProgressBar'"));
    paramFinder.findRequiredView(paramObject, 2131558765, "method 'gotoLoginActivity'").setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.gotoLoginActivity();
      }
    });
  }
  
  public static void reset(SignUpFragment paramSignUpFragment)
  {
    paramSignUpFragment.mFirstName = null;
    paramSignUpFragment.mLastName = null;
    paramSignUpFragment.mEmail = null;
    paramSignUpFragment.mPassword = null;
    paramSignUpFragment.mCode = null;
    paramSignUpFragment.mPromoCode = null;
    paramSignUpFragment.mSignUpButton = null;
    paramSignUpFragment.mSignUpProgressBar = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/SignUpFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */