package com.happyfresh.fragments;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;
import com.happyfresh.customs.CircularProgressBar;

public class InviteCodeFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, InviteCodeFragment paramInviteCodeFragment, Object paramObject)
  {
    paramInviteCodeFragment.mEtInvitationCode = ((EditText)paramFinder.findRequiredView(paramObject, 2131558755, "field 'mEtInvitationCode'"));
    View localView = paramFinder.findRequiredView(paramObject, 2131558756, "field 'mBtnSubmit' and method 'submit'");
    paramInviteCodeFragment.mBtnSubmit = ((Button)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.submit();
      }
    });
    paramInviteCodeFragment.mProgressBar = ((CircularProgressBar)paramFinder.findRequiredView(paramObject, 2131558694, "field 'mProgressBar'"));
    paramFinder.findRequiredView(paramObject, 2131558711, "method 'dismissDialog'").setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.dismissDialog();
      }
    });
  }
  
  public static void reset(InviteCodeFragment paramInviteCodeFragment)
  {
    paramInviteCodeFragment.mEtInvitationCode = null;
    paramInviteCodeFragment.mBtnSubmit = null;
    paramInviteCodeFragment.mProgressBar = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/InviteCodeFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */