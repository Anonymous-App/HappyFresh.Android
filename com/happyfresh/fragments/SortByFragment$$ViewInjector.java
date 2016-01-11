package com.happyfresh.fragments;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;

public class SortByFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, SortByFragment paramSortByFragment, Object paramObject)
  {
    paramSortByFragment.sortByOptions = ((RadioGroup)paramFinder.findRequiredView(paramObject, 2131558875, "field 'sortByOptions'"));
    View localView = paramFinder.findRequiredView(paramObject, 2131558877, "field 'sortByPopularity' and method 'onByPopularityCheckedChanged'");
    paramSortByFragment.sortByPopularity = ((RadioButton)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.onByPopularityCheckedChanged();
      }
    });
    localView = paramFinder.findRequiredView(paramObject, 2131558879, "field 'sortByHighestPrice' and method 'onByHighestPriceCheckedChanged'");
    paramSortByFragment.sortByHighestPrice = ((RadioButton)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.onByHighestPriceCheckedChanged();
      }
    });
    localView = paramFinder.findRequiredView(paramObject, 2131558878, "field 'sortByLowestPrice' and method 'onByLowestPriceCheckedChanged'");
    paramSortByFragment.sortByLowestPrice = ((RadioButton)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.onByLowestPriceCheckedChanged();
      }
    });
    localView = paramFinder.findRequiredView(paramObject, 2131558880, "field 'sortByAToZ' and method 'onByAToZCheckedChanged'");
    paramSortByFragment.sortByAToZ = ((RadioButton)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.onByAToZCheckedChanged();
      }
    });
    paramFinder = paramFinder.findRequiredView(paramObject, 2131558881, "field 'sortByZToA' and method 'onByZToACheckedChanged'");
    paramSortByFragment.sortByZToA = ((RadioButton)paramFinder);
    paramFinder.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.onByZToACheckedChanged();
      }
    });
  }
  
  public static void reset(SortByFragment paramSortByFragment)
  {
    paramSortByFragment.sortByOptions = null;
    paramSortByFragment.sortByPopularity = null;
    paramSortByFragment.sortByHighestPrice = null;
    paramSortByFragment.sortByLowestPrice = null;
    paramSortByFragment.sortByAToZ = null;
    paramSortByFragment.sortByZToA = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/SortByFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */