package com.happyfresh.fragments;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;
import com.viewpagerindicator.CirclePageIndicator;

public class IntroductionFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, IntroductionFragment paramIntroductionFragment, Object paramObject)
  {
    paramIntroductionFragment.mViewPager = ((ViewPager)paramFinder.findRequiredView(paramObject, 2131558737, "field 'mViewPager'"));
    paramIntroductionFragment.mViewPagerIndicator = ((CirclePageIndicator)paramFinder.findRequiredView(paramObject, 2131558738, "field 'mViewPagerIndicator'"));
    paramFinder = paramFinder.findRequiredView(paramObject, 2131558746, "field 'mButtonStart' and method 'skip'");
    paramIntroductionFragment.mButtonStart = ((Button)paramFinder);
    paramFinder.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.skip();
      }
    });
  }
  
  public static void reset(IntroductionFragment paramIntroductionFragment)
  {
    paramIntroductionFragment.mViewPager = null;
    paramIntroductionFragment.mViewPagerIndicator = null;
    paramIntroductionFragment.mButtonStart = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/IntroductionFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */