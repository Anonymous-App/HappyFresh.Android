package com.happyfresh.fragments;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import butterknife.ButterKnife.Finder;
import com.happyfresh.customs.CircularProgressBar;

public class RecomendedListFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, RecomendedListFragment paramRecomendedListFragment, Object paramObject)
  {
    paramRecomendedListFragment.recyclerList = ((RecyclerView)paramFinder.findRequiredView(paramObject, 2131558715, "field 'recyclerList'"));
    paramRecomendedListFragment.mProgressBar = ((CircularProgressBar)paramFinder.findRequiredView(paramObject, 2131558694, "field 'mProgressBar'"));
    paramRecomendedListFragment.mEmptyList = paramFinder.findRequiredView(paramObject, 2131558758, "field 'mEmptyList'");
    paramRecomendedListFragment.mTxtEmptyList = ((TextView)paramFinder.findRequiredView(paramObject, 2131558760, "field 'mTxtEmptyList'"));
  }
  
  public static void reset(RecomendedListFragment paramRecomendedListFragment)
  {
    paramRecomendedListFragment.recyclerList = null;
    paramRecomendedListFragment.mProgressBar = null;
    paramRecomendedListFragment.mEmptyList = null;
    paramRecomendedListFragment.mTxtEmptyList = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/RecomendedListFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */