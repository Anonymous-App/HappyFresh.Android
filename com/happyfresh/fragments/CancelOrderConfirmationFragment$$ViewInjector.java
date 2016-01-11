package com.happyfresh.fragments;

import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;
import com.happyfresh.customs.CircularProgressBar;

public class CancelOrderConfirmationFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, CancelOrderConfirmationFragment paramCancelOrderConfirmationFragment, Object paramObject)
  {
    View localView = paramFinder.findRequiredView(paramObject, 2131558628, "field 'mBackButton' and method 'keepOrder'");
    paramCancelOrderConfirmationFragment.mBackButton = ((Button)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.keepOrder();
      }
    });
    localView = paramFinder.findRequiredView(paramObject, 2131558627, "field 'mProceedButton' and method 'cancelOrder'");
    paramCancelOrderConfirmationFragment.mProceedButton = ((Button)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.cancelOrder();
      }
    });
    paramCancelOrderConfirmationFragment.mScrollView = ((ScrollView)paramFinder.findRequiredView(paramObject, 2131558618, "field 'mScrollView'"));
    paramCancelOrderConfirmationFragment.mProgressContainer = paramFinder.findRequiredView(paramObject, 2131558629, "field 'mProgressContainer'");
    paramCancelOrderConfirmationFragment.mCircularProgressBar = ((CircularProgressBar)paramFinder.findRequiredView(paramObject, 2131558630, "field 'mCircularProgressBar'"));
  }
  
  public static void reset(CancelOrderConfirmationFragment paramCancelOrderConfirmationFragment)
  {
    paramCancelOrderConfirmationFragment.mBackButton = null;
    paramCancelOrderConfirmationFragment.mProceedButton = null;
    paramCancelOrderConfirmationFragment.mScrollView = null;
    paramCancelOrderConfirmationFragment.mProgressContainer = null;
    paramCancelOrderConfirmationFragment.mCircularProgressBar = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/CancelOrderConfirmationFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */