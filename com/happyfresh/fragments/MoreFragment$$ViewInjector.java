package com.happyfresh.fragments;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;

public class MoreFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, MoreFragment paramMoreFragment, Object paramObject)
  {
    paramFinder.findRequiredView(paramObject, 2131558778, "method 'shopAtOnClick'").setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.shopAtOnClick();
      }
    });
    paramFinder.findRequiredView(paramObject, 2131558777, "method 'myAccountOnClick'").setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.myAccountOnClick();
      }
    });
    paramFinder.findRequiredView(paramObject, 2131558776, "method 'myOrdersOnClick'").setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.myOrdersOnClick();
      }
    });
  }
  
  public static void reset(MoreFragment paramMoreFragment) {}
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/MoreFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */