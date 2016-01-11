package com.happyfresh.fragments;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;
import com.viewpagerindicator.CirclePageIndicator;

public class ViewProductFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, ViewProductFragment paramViewProductFragment, Object paramObject)
  {
    paramViewProductFragment.mViewPager = ((ViewPager)paramFinder.findRequiredView(paramObject, 2131558737, "field 'mViewPager'"));
    paramViewProductFragment.mViewPagerIndicator = ((CirclePageIndicator)paramFinder.findRequiredView(paramObject, 2131558738, "field 'mViewPagerIndicator'"));
    paramViewProductFragment.mSingleProductName = ((TextView)paramFinder.findRequiredView(paramObject, 2131558899, "field 'mSingleProductName'"));
    paramViewProductFragment.mSingleProductPrice = ((TextView)paramFinder.findRequiredView(paramObject, 2131558900, "field 'mSingleProductPrice'"));
    paramViewProductFragment.mSingleProductDiscountArea = paramFinder.findRequiredView(paramObject, 2131558901, "field 'mSingleProductDiscountArea'");
    paramViewProductFragment.mSingleProductOriginalPrice = ((TextView)paramFinder.findRequiredView(paramObject, 2131558902, "field 'mSingleProductOriginalPrice'"));
    paramViewProductFragment.mSingleProductDiscountPercentage = ((TextView)paramFinder.findRequiredView(paramObject, 2131558903, "field 'mSingleProductDiscountPercentage'"));
    paramViewProductFragment.mSingleProductDiscountCombination = ((TextView)paramFinder.findRequiredView(paramObject, 2131558905, "field 'mSingleProductDiscountCombination'"));
    paramViewProductFragment.mSingleProductProperty = ((TextView)paramFinder.findRequiredView(paramObject, 2131558904, "field 'mSingleProductProperty'"));
    paramViewProductFragment.mSingleProductDescription = ((TextView)paramFinder.findRequiredView(paramObject, 2131558906, "field 'mSingleProductDescription'"));
    paramViewProductFragment.mTotalItemInTheCart = ((TextView)paramFinder.findRequiredView(paramObject, 2131558908, "field 'mTotalItemInTheCart'"));
    View localView = paramFinder.findRequiredView(paramObject, 2131558910, "field 'mAddToCartButton' and method 'addOrUpdateItem'");
    paramViewProductFragment.mAddToCartButton = ((Button)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.addOrUpdateItem();
      }
    });
    localView = paramFinder.findRequiredView(paramObject, 2131558909, "field 'mAddItem' and method 'addItem'");
    paramViewProductFragment.mAddItem = ((ImageView)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.addItem();
      }
    });
    localView = paramFinder.findRequiredView(paramObject, 2131558907, "field 'mSubItem' and method 'subItem'");
    paramViewProductFragment.mSubItem = ((ImageView)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.subItem();
      }
    });
    paramViewProductFragment.mBottomView = paramFinder.findRequiredView(paramObject, 2131558897, "field 'mBottomView'");
    paramFinder.findRequiredView(paramObject, 2131558898, "method 'onClick'").setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.onClick();
      }
    });
  }
  
  public static void reset(ViewProductFragment paramViewProductFragment)
  {
    paramViewProductFragment.mViewPager = null;
    paramViewProductFragment.mViewPagerIndicator = null;
    paramViewProductFragment.mSingleProductName = null;
    paramViewProductFragment.mSingleProductPrice = null;
    paramViewProductFragment.mSingleProductDiscountArea = null;
    paramViewProductFragment.mSingleProductOriginalPrice = null;
    paramViewProductFragment.mSingleProductDiscountPercentage = null;
    paramViewProductFragment.mSingleProductDiscountCombination = null;
    paramViewProductFragment.mSingleProductProperty = null;
    paramViewProductFragment.mSingleProductDescription = null;
    paramViewProductFragment.mTotalItemInTheCart = null;
    paramViewProductFragment.mAddToCartButton = null;
    paramViewProductFragment.mAddItem = null;
    paramViewProductFragment.mSubItem = null;
    paramViewProductFragment.mBottomView = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/ViewProductFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */