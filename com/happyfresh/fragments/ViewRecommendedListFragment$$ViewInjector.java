package com.happyfresh.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;
import com.happyfresh.customs.CircularProgressBar;

public class ViewRecommendedListFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, ViewRecommendedListFragment paramViewRecommendedListFragment, Object paramObject)
  {
    paramViewRecommendedListFragment.recyclerList = ((RecyclerView)paramFinder.findRequiredView(paramObject, 2131558715, "field 'recyclerList'"));
    paramViewRecommendedListFragment.mProgressBar = ((CircularProgressBar)paramFinder.findRequiredView(paramObject, 2131558694, "field 'mProgressBar'"));
    paramViewRecommendedListFragment.mProgressBarButton = ((CircularProgressBar)paramFinder.findRequiredView(paramObject, 2131558856, "field 'mProgressBarButton'"));
    paramFinder = paramFinder.findRequiredView(paramObject, 2131558855, "field 'mButtonUpdateCart' and method 'addToCart'");
    paramViewRecommendedListFragment.mButtonUpdateCart = ((Button)paramFinder);
    paramFinder.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.addToCart();
      }
    });
  }
  
  public static void reset(ViewRecommendedListFragment paramViewRecommendedListFragment)
  {
    paramViewRecommendedListFragment.recyclerList = null;
    paramViewRecommendedListFragment.mProgressBar = null;
    paramViewRecommendedListFragment.mProgressBarButton = null;
    paramViewRecommendedListFragment.mButtonUpdateCart = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/ViewRecommendedListFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */