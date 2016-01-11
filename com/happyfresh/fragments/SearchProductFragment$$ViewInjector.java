package com.happyfresh.fragments;

import android.support.v7.widget.RecyclerView;
import butterknife.ButterKnife.Finder;

public class SearchProductFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, SearchProductFragment paramSearchProductFragment, Object paramObject)
  {
    SearchFragment..ViewInjector.inject(paramFinder, paramSearchProductFragment, paramObject);
    paramSearchProductFragment.transparentView = paramFinder.findRequiredView(paramObject, 2131558840, "field 'transparentView'");
    paramSearchProductFragment.listSuggested = ((RecyclerView)paramFinder.findRequiredView(paramObject, 2131558841, "field 'listSuggested'"));
  }
  
  public static void reset(SearchProductFragment paramSearchProductFragment)
  {
    SearchFragment..ViewInjector.reset(paramSearchProductFragment);
    paramSearchProductFragment.transparentView = null;
    paramSearchProductFragment.listSuggested = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/SearchProductFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */