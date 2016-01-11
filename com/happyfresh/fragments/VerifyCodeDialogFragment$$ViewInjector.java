package com.happyfresh.fragments;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;
import com.happyfresh.customs.CircularProgressBar;

public class VerifyCodeDialogFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, VerifyCodeDialogFragment paramVerifyCodeDialogFragment, Object paramObject)
  {
    paramVerifyCodeDialogFragment.mSentSMSTo = ((TextView)paramFinder.findRequiredView(paramObject, 2131558888, "field 'mSentSMSTo'"));
    View localView = paramFinder.findRequiredView(paramObject, 2131558895, "field 'mEditPhoneNumber' and method 'editPhoneNumber'");
    paramVerifyCodeDialogFragment.mEditPhoneNumber = ((TextView)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.editPhoneNumber();
      }
    });
    paramVerifyCodeDialogFragment.mEtCode1 = ((EditText)paramFinder.findRequiredView(paramObject, 2131558891, "field 'mEtCode1'"));
    paramVerifyCodeDialogFragment.mEtCode2 = ((EditText)paramFinder.findRequiredView(paramObject, 2131558892, "field 'mEtCode2'"));
    paramVerifyCodeDialogFragment.mEtCode3 = ((EditText)paramFinder.findRequiredView(paramObject, 2131558893, "field 'mEtCode3'"));
    paramVerifyCodeDialogFragment.mEtCode4 = ((EditText)paramFinder.findRequiredView(paramObject, 2131558894, "field 'mEtCode4'"));
    localView = paramFinder.findRequiredView(paramObject, 2131558896, "field 'mBtnSubmit' and method 'verifyNumberClicked'");
    paramVerifyCodeDialogFragment.mBtnSubmit = ((Button)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.verifyNumberClicked();
      }
    });
    paramVerifyCodeDialogFragment.mWarningCodeSent = paramFinder.findRequiredView(paramObject, 2131558885, "field 'mWarningCodeSent'");
    paramVerifyCodeDialogFragment.mWarningStillNotReceived = paramFinder.findRequiredView(paramObject, 2131558886, "field 'mWarningStillNotReceived'");
    localView = paramFinder.findRequiredView(paramObject, 2131558887, "field 'mTxtWarningStillNotReceived' and method 'resendCode'");
    paramVerifyCodeDialogFragment.mTxtWarningStillNotReceived = ((TextView)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.resendCode();
      }
    });
    paramVerifyCodeDialogFragment.mProgressBar = ((CircularProgressBar)paramFinder.findRequiredView(paramObject, 2131558694, "field 'mProgressBar'"));
    paramFinder.findRequiredView(paramObject, 2131558711, "method 'close'").setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.close();
      }
    });
  }
  
  public static void reset(VerifyCodeDialogFragment paramVerifyCodeDialogFragment)
  {
    paramVerifyCodeDialogFragment.mSentSMSTo = null;
    paramVerifyCodeDialogFragment.mEditPhoneNumber = null;
    paramVerifyCodeDialogFragment.mEtCode1 = null;
    paramVerifyCodeDialogFragment.mEtCode2 = null;
    paramVerifyCodeDialogFragment.mEtCode3 = null;
    paramVerifyCodeDialogFragment.mEtCode4 = null;
    paramVerifyCodeDialogFragment.mBtnSubmit = null;
    paramVerifyCodeDialogFragment.mWarningCodeSent = null;
    paramVerifyCodeDialogFragment.mWarningStillNotReceived = null;
    paramVerifyCodeDialogFragment.mTxtWarningStillNotReceived = null;
    paramVerifyCodeDialogFragment.mProgressBar = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/VerifyCodeDialogFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */