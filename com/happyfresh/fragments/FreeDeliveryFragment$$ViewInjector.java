package com.happyfresh.fragments;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;

public class FreeDeliveryFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, FreeDeliveryFragment paramFreeDeliveryFragment, Object paramObject)
  {
    paramFreeDeliveryFragment.mDialogTitle = ((TextView)paramFinder.findRequiredView(paramObject, 2131558475, "field 'mDialogTitle'"));
    paramFreeDeliveryFragment.mFreeDeliveryText = ((TextView)paramFinder.findRequiredView(paramObject, 2131558734, "field 'mFreeDeliveryText'"));
    paramFreeDeliveryFragment.mDurationText = ((TextView)paramFinder.findRequiredView(paramObject, 2131558735, "field 'mDurationText'"));
    paramFreeDeliveryFragment.mFreeDetailText = ((TextView)paramFinder.findRequiredView(paramObject, 2131558736, "field 'mFreeDetailText'"));
    View localView = paramFinder.findRequiredView(paramObject, 2131558712, "field 'mGotItButton' and method 'verifyPhoneNumber'");
    paramFreeDeliveryFragment.mGotItButton = ((Button)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.verifyPhoneNumber();
      }
    });
    paramFinder.findRequiredView(paramObject, 2131558711, "method 'dismissDialog'").setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.dismissDialog();
      }
    });
  }
  
  public static void reset(FreeDeliveryFragment paramFreeDeliveryFragment)
  {
    paramFreeDeliveryFragment.mDialogTitle = null;
    paramFreeDeliveryFragment.mFreeDeliveryText = null;
    paramFreeDeliveryFragment.mDurationText = null;
    paramFreeDeliveryFragment.mFreeDetailText = null;
    paramFreeDeliveryFragment.mGotItButton = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/FreeDeliveryFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */