package com.happyfresh.fragments;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;

public class MyAccountFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, MyAccountFragment paramMyAccountFragment, Object paramObject)
  {
    paramMyAccountFragment.mAccountContainer = ((ViewGroup)paramFinder.findRequiredView(paramObject, 2131558781, "field 'mAccountContainer'"));
    paramMyAccountFragment.mAccountNotLoggedInContainer = ((ViewGroup)paramFinder.findRequiredView(paramObject, 2131558779, "field 'mAccountNotLoggedInContainer'"));
    paramMyAccountFragment.mUserFullName = ((TextView)paramFinder.findRequiredView(paramObject, 2131558783, "field 'mUserFullName'"));
    paramMyAccountFragment.mLogoutButton = paramFinder.findRequiredView(paramObject, 2131558784, "field 'mLogoutButton'");
    paramMyAccountFragment.mAddNewAddress = ((TextView)paramFinder.findRequiredView(paramObject, 2131558785, "field 'mAddNewAddress'"));
    paramMyAccountFragment.mListView = ((ListView)paramFinder.findRequiredView(paramObject, 2131558605, "field 'mListView'"));
    paramMyAccountFragment.mRgLanguage = ((RadioGroup)paramFinder.findRequiredView(paramObject, 2131558787, "field 'mRgLanguage'"));
    paramMyAccountFragment.mRbEnglish = ((RadioButton)paramFinder.findRequiredView(paramObject, 2131558788, "field 'mRbEnglish'"));
    paramMyAccountFragment.mRbLocalLanguage = ((RadioButton)paramFinder.findRequiredView(paramObject, 2131558789, "field 'mRbLocalLanguage'"));
    paramFinder.findRequiredView(paramObject, 2131558765, "method 'login'").setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.login();
      }
    });
    paramFinder.findRequiredView(paramObject, 2131558780, "method 'signup'").setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.signup();
      }
    });
  }
  
  public static void reset(MyAccountFragment paramMyAccountFragment)
  {
    paramMyAccountFragment.mAccountContainer = null;
    paramMyAccountFragment.mAccountNotLoggedInContainer = null;
    paramMyAccountFragment.mUserFullName = null;
    paramMyAccountFragment.mLogoutButton = null;
    paramMyAccountFragment.mAddNewAddress = null;
    paramMyAccountFragment.mListView = null;
    paramMyAccountFragment.mRgLanguage = null;
    paramMyAccountFragment.mRbEnglish = null;
    paramMyAccountFragment.mRbLocalLanguage = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/MyAccountFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */