package com.happyfresh.fragments;

import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife.Finder;
import com.happyfresh.customs.CircularProgressBar;

public class SearchFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, SearchFragment paramSearchFragment, Object paramObject)
  {
    paramSearchFragment.mSearchingLocationText = ((TextView)paramFinder.findRequiredView(paramObject, 2131558835, "field 'mSearchingLocationText'"));
    paramSearchFragment.mRecentLocation = paramFinder.findRequiredView(paramObject, 2131558837, "field 'mRecentLocation'");
    paramSearchFragment.mRecentSearchLocationText = ((TextView)paramFinder.findRequiredView(paramObject, 2131558839, "field 'mRecentSearchLocationText'"));
    paramSearchFragment.mSearchingLocation = ((RelativeLayout)paramFinder.findRequiredView(paramObject, 2131558833, "field 'mSearchingLocation'"));
    paramSearchFragment.mSearchingLocationQuery = ((TextView)paramFinder.findRequiredView(paramObject, 2131558836, "field 'mSearchingLocationQuery'"));
    paramSearchFragment.mList = ((RecyclerView)paramFinder.findRequiredView(paramObject, 2131558715, "field 'mList'"));
    paramSearchFragment.mProgressBar = ((CircularProgressBar)paramFinder.findRequiredView(paramObject, 2131558694, "field 'mProgressBar'"));
  }
  
  public static void reset(SearchFragment paramSearchFragment)
  {
    paramSearchFragment.mSearchingLocationText = null;
    paramSearchFragment.mRecentLocation = null;
    paramSearchFragment.mRecentSearchLocationText = null;
    paramSearchFragment.mSearchingLocation = null;
    paramSearchFragment.mSearchingLocationQuery = null;
    paramSearchFragment.mList = null;
    paramSearchFragment.mProgressBar = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/SearchFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */