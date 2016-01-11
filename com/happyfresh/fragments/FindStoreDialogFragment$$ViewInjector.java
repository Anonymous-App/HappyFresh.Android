package com.happyfresh.fragments;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;

public class FindStoreDialogFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, FindStoreDialogFragment paramFindStoreDialogFragment, Object paramObject)
  {
    View localView = paramFinder.findRequiredView(paramObject, 2131558711, "field 'mNavigateImage' and method 'close'");
    paramFindStoreDialogFragment.mNavigateImage = ((ImageButton)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.close();
      }
    });
    paramFindStoreDialogFragment.mDialogTitle = ((TextView)paramFinder.findRequiredView(paramObject, 2131558617, "field 'mDialogTitle'"));
    paramFindStoreDialogFragment.mSearchView = ((SearchView)paramFinder.findRequiredView(paramObject, 2131558714, "field 'mSearchView'"));
    paramFindStoreDialogFragment.mRecyclerList = ((RecyclerView)paramFinder.findRequiredView(paramObject, 2131558715, "field 'mRecyclerList'"));
  }
  
  public static void reset(FindStoreDialogFragment paramFindStoreDialogFragment)
  {
    paramFindStoreDialogFragment.mNavigateImage = null;
    paramFindStoreDialogFragment.mDialogTitle = null;
    paramFindStoreDialogFragment.mSearchView = null;
    paramFindStoreDialogFragment.mRecyclerList = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/FindStoreDialogFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */