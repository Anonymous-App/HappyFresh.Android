package com.viewpagerindicator;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

public class IconPageIndicator
  extends HorizontalScrollView
  implements PageIndicator
{
  private Runnable mIconSelector;
  private final IcsLinearLayout mIconsLayout;
  private ViewPager.OnPageChangeListener mListener;
  private int mSelectedIndex;
  private ViewPager mViewPager;
  
  public IconPageIndicator(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public IconPageIndicator(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setHorizontalScrollBarEnabled(false);
    this.mIconsLayout = new IcsLinearLayout(paramContext, R.attr.vpiIconPageIndicatorStyle);
    addView(this.mIconsLayout, new FrameLayout.LayoutParams(-2, -1, 17));
  }
  
  private void animateToIcon(int paramInt)
  {
    final View localView = this.mIconsLayout.getChildAt(paramInt);
    if (this.mIconSelector != null) {
      removeCallbacks(this.mIconSelector);
    }
    this.mIconSelector = new Runnable()
    {
      public void run()
      {
        int i = localView.getLeft();
        int j = (IconPageIndicator.this.getWidth() - localView.getWidth()) / 2;
        IconPageIndicator.this.smoothScrollTo(i - j, 0);
        IconPageIndicator.access$002(IconPageIndicator.this, null);
      }
    };
    post(this.mIconSelector);
  }
  
  public void notifyDataSetChanged()
  {
    this.mIconsLayout.removeAllViews();
    IconPagerAdapter localIconPagerAdapter = (IconPagerAdapter)this.mViewPager.getAdapter();
    int j = localIconPagerAdapter.getCount();
    int i = 0;
    while (i < j)
    {
      ImageView localImageView = new ImageView(getContext(), null, R.attr.vpiIconPageIndicatorStyle);
      localImageView.setImageResource(localIconPagerAdapter.getIconResId(i));
      this.mIconsLayout.addView(localImageView);
      i += 1;
    }
    if (this.mSelectedIndex > j) {
      this.mSelectedIndex = (j - 1);
    }
    setCurrentItem(this.mSelectedIndex);
    requestLayout();
  }
  
  public void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    if (this.mIconSelector != null) {
      post(this.mIconSelector);
    }
  }
  
  public void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    if (this.mIconSelector != null) {
      removeCallbacks(this.mIconSelector);
    }
  }
  
  public void onPageScrollStateChanged(int paramInt)
  {
    if (this.mListener != null) {
      this.mListener.onPageScrollStateChanged(paramInt);
    }
  }
  
  public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2)
  {
    if (this.mListener != null) {
      this.mListener.onPageScrolled(paramInt1, paramFloat, paramInt2);
    }
  }
  
  public void onPageSelected(int paramInt)
  {
    setCurrentItem(paramInt);
    if (this.mListener != null) {
      this.mListener.onPageSelected(paramInt);
    }
  }
  
  public void setCurrentItem(int paramInt)
  {
    if (this.mViewPager == null) {
      throw new IllegalStateException("ViewPager has not been bound.");
    }
    this.mSelectedIndex = paramInt;
    this.mViewPager.setCurrentItem(paramInt);
    int j = this.mIconsLayout.getChildCount();
    int i = 0;
    if (i < j)
    {
      View localView = this.mIconsLayout.getChildAt(i);
      if (i == paramInt) {}
      for (boolean bool = true;; bool = false)
      {
        localView.setSelected(bool);
        if (bool) {
          animateToIcon(paramInt);
        }
        i += 1;
        break;
      }
    }
  }
  
  public void setOnPageChangeListener(ViewPager.OnPageChangeListener paramOnPageChangeListener)
  {
    this.mListener = paramOnPageChangeListener;
  }
  
  public void setViewPager(ViewPager paramViewPager)
  {
    if (this.mViewPager == paramViewPager) {
      return;
    }
    if (this.mViewPager != null) {
      this.mViewPager.setOnPageChangeListener(null);
    }
    if (paramViewPager.getAdapter() == null) {
      throw new IllegalStateException("ViewPager does not have adapter instance.");
    }
    this.mViewPager = paramViewPager;
    paramViewPager.setOnPageChangeListener(this);
    notifyDataSetChanged();
  }
  
  public void setViewPager(ViewPager paramViewPager, int paramInt)
  {
    setViewPager(paramViewPager);
    setCurrentItem(paramInt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/viewpagerindicator/IconPageIndicator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */