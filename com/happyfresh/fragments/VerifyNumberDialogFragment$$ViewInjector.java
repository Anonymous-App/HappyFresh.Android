package com.happyfresh.fragments;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;
import com.happyfresh.customs.CircularProgressBar;

public class VerifyNumberDialogFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, VerifyNumberDialogFragment paramVerifyNumberDialogFragment, Object paramObject)
  {
    paramVerifyNumberDialogFragment.mEtCountryCode = ((EditText)paramFinder.findRequiredView(paramObject, 2131558720, "field 'mEtCountryCode'"));
    paramVerifyNumberDialogFragment.mEtPhoneNumber = ((EditText)paramFinder.findRequiredView(paramObject, 2131558721, "field 'mEtPhoneNumber'"));
    View localView = paramFinder.findRequiredView(paramObject, 2131558722, "field 'mBtnSubmit' and method 'verifyNumberClicked'");
    paramVerifyNumberDialogFragment.mBtnSubmit = ((Button)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.verifyNumberClicked();
      }
    });
    paramVerifyNumberDialogFragment.mWarningInvalidNumber = paramFinder.findRequiredView(paramObject, 2131558718, "field 'mWarningInvalidNumber'");
    paramVerifyNumberDialogFragment.mProgressBar = ((CircularProgressBar)paramFinder.findRequiredView(paramObject, 2131558694, "field 'mProgressBar'"));
    paramFinder.findRequiredView(paramObject, 2131558711, "method 'close'").setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.close();
      }
    });
  }
  
  public static void reset(VerifyNumberDialogFragment paramVerifyNumberDialogFragment)
  {
    paramVerifyNumberDialogFragment.mEtCountryCode = null;
    paramVerifyNumberDialogFragment.mEtPhoneNumber = null;
    paramVerifyNumberDialogFragment.mBtnSubmit = null;
    paramVerifyNumberDialogFragment.mWarningInvalidNumber = null;
    paramVerifyNumberDialogFragment.mProgressBar = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/VerifyNumberDialogFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */