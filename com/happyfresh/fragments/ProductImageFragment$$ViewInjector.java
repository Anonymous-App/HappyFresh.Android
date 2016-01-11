package com.happyfresh.fragments;

import android.view.View;
import android.widget.ImageView;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;

public class ProductImageFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, ProductImageFragment paramProductImageFragment, Object paramObject)
  {
    paramFinder = paramFinder.findRequiredView(paramObject, 2131558739, "field 'mImageView' and method 'onClick'");
    paramProductImageFragment.mImageView = ((ImageView)paramFinder);
    paramFinder.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.onClick();
      }
    });
  }
  
  public static void reset(ProductImageFragment paramProductImageFragment)
  {
    paramProductImageFragment.mImageView = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/ProductImageFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */