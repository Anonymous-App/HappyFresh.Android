package com.happyfresh.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;
import com.happyfresh.customs.CircularProgressBar;

public class ChooseStoreFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, ChooseStoreFragment paramChooseStoreFragment, Object paramObject)
  {
    paramChooseStoreFragment.mStoreRecyclerView = ((RecyclerView)paramFinder.findRequiredView(paramObject, 2131558692, "field 'mStoreRecyclerView'"));
    paramChooseStoreFragment.mProgressBar = ((CircularProgressBar)paramFinder.findRequiredView(paramObject, 2131558694, "field 'mProgressBar'"));
    paramFinder.findRequiredView(paramObject, 2131558695, "method 'onClickChangeArea'").setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.onClickChangeArea(paramAnonymousView);
      }
    });
  }
  
  public static void reset(ChooseStoreFragment paramChooseStoreFragment)
  {
    paramChooseStoreFragment.mStoreRecyclerView = null;
    paramChooseStoreFragment.mProgressBar = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/ChooseStoreFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */