package com.happyfresh.fragments;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;

public class SingleIntroductionFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, SingleIntroductionFragment paramSingleIntroductionFragment, Object paramObject)
  {
    paramFinder.findRequiredView(paramObject, 2131558870, "method 'enter'").setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.enter();
      }
    });
  }
  
  public static void reset(SingleIntroductionFragment paramSingleIntroductionFragment) {}
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/SingleIntroductionFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */