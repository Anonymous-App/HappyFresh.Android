package com.happyfresh.fragments;

import android.support.v4.view.ViewPager;
import butterknife.ButterKnife.Finder;
import com.viewpagerindicator.CirclePageIndicator;

public class FullImageFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, FullImageFragment paramFullImageFragment, Object paramObject)
  {
    paramFullImageFragment.mViewPager = ((ViewPager)paramFinder.findRequiredView(paramObject, 2131558737, "field 'mViewPager'"));
    paramFullImageFragment.mViewPagerIndicator = ((CirclePageIndicator)paramFinder.findRequiredView(paramObject, 2131558738, "field 'mViewPagerIndicator'"));
  }
  
  public static void reset(FullImageFragment paramFullImageFragment)
  {
    paramFullImageFragment.mViewPager = null;
    paramFullImageFragment.mViewPagerIndicator = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/FullImageFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */