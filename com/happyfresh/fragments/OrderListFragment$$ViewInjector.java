package com.happyfresh.fragments;

import android.support.v7.widget.RecyclerView;
import butterknife.ButterKnife.Finder;
import com.happyfresh.customs.CircularProgressBar;

public class OrderListFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, OrderListFragment paramOrderListFragment, Object paramObject)
  {
    paramOrderListFragment.mRecyclerView = ((RecyclerView)paramFinder.findRequiredView(paramObject, 2131558715, "field 'mRecyclerView'"));
    paramOrderListFragment.mProgressBar = ((CircularProgressBar)paramFinder.findRequiredView(paramObject, 2131558694, "field 'mProgressBar'"));
  }
  
  public static void reset(OrderListFragment paramOrderListFragment)
  {
    paramOrderListFragment.mRecyclerView = null;
    paramOrderListFragment.mProgressBar = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/OrderListFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */