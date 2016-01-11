package com.happyfresh.adapters;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;

public class ProductGridAdapter$ViewMoreHolder$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, ProductGridAdapter.ViewMoreHolder paramViewMoreHolder, Object paramObject)
  {
    paramFinder.findRequiredView(paramObject, 2131559043, "method 'seeMore'").setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.seeMore(paramAnonymousView);
      }
    });
  }
  
  public static void reset(ProductGridAdapter.ViewMoreHolder paramViewMoreHolder) {}
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/adapters/ProductGridAdapter$ViewMoreHolder$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */