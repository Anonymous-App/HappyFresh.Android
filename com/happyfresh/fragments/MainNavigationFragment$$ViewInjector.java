package com.happyfresh.fragments;

import android.support.v4.view.ViewPager;
import butterknife.ButterKnife.Finder;
import com.happyfresh.customs.SlidingTabLayout;

public class MainNavigationFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, MainNavigationFragment paramMainNavigationFragment, Object paramObject)
  {
    paramMainNavigationFragment.mViewPager = ((ViewPager)paramFinder.findRequiredView(paramObject, 2131558775, "field 'mViewPager'"));
    paramMainNavigationFragment.mSlidingTabLayout = ((SlidingTabLayout)paramFinder.findRequiredView(paramObject, 2131558641, "field 'mSlidingTabLayout'"));
  }
  
  public static void reset(MainNavigationFragment paramMainNavigationFragment)
  {
    paramMainNavigationFragment.mViewPager = null;
    paramMainNavigationFragment.mSlidingTabLayout = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/MainNavigationFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */