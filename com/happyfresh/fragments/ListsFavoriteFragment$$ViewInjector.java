package com.happyfresh.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;
import com.happyfresh.customs.CircularProgressBar;

public class ListsFavoriteFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, ListsFavoriteFragment paramListsFavoriteFragment, Object paramObject)
  {
    paramListsFavoriteFragment.mTabRecommended = ((RadioButton)paramFinder.findRequiredView(paramObject, 2131558853, "field 'mTabRecommended'"));
    paramListsFavoriteFragment.mTabFavorite = ((RadioButton)paramFinder.findRequiredView(paramObject, 2131558852, "field 'mTabFavorite'"));
    paramListsFavoriteFragment.mList = ((RecyclerView)paramFinder.findRequiredView(paramObject, 2131558715, "field 'mList'"));
    paramListsFavoriteFragment.mProgressBar = ((CircularProgressBar)paramFinder.findRequiredView(paramObject, 2131558694, "field 'mProgressBar'"));
    paramListsFavoriteFragment.mProgressBarButton = ((CircularProgressBar)paramFinder.findRequiredView(paramObject, 2131558856, "field 'mProgressBarButton'"));
    paramListsFavoriteFragment.mEmptyRecommendationList = paramFinder.findRequiredView(paramObject, 2131558857, "field 'mEmptyRecommendationList'");
    paramListsFavoriteFragment.mNoConnectionMessage = paramFinder.findRequiredView(paramObject, 2131558860, "field 'mNoConnectionMessage'");
    paramListsFavoriteFragment.mButtonArea = paramFinder.findRequiredView(paramObject, 2131558854, "field 'mButtonArea'");
    View localView = paramFinder.findRequiredView(paramObject, 2131558855, "field 'mBtnAddToCart' and method 'addToCart'");
    paramListsFavoriteFragment.mBtnAddToCart = ((Button)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.addToCart();
      }
    });
    paramFinder.findRequiredView(paramObject, 2131558864, "method 'refreshList'").setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.refreshList();
      }
    });
  }
  
  public static void reset(ListsFavoriteFragment paramListsFavoriteFragment)
  {
    paramListsFavoriteFragment.mTabRecommended = null;
    paramListsFavoriteFragment.mTabFavorite = null;
    paramListsFavoriteFragment.mList = null;
    paramListsFavoriteFragment.mProgressBar = null;
    paramListsFavoriteFragment.mProgressBarButton = null;
    paramListsFavoriteFragment.mEmptyRecommendationList = null;
    paramListsFavoriteFragment.mNoConnectionMessage = null;
    paramListsFavoriteFragment.mButtonArea = null;
    paramListsFavoriteFragment.mBtnAddToCart = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/ListsFavoriteFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */