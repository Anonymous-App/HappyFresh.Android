package com.happyfresh.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;
import com.happyfresh.customs.CircularProgressBar;

public class TaxonFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, TaxonFragment paramTaxonFragment, Object paramObject)
  {
    paramTaxonFragment.mCategoryHeader = paramFinder.findRequiredView(paramObject, 2131558769, "field 'mCategoryHeader'");
    paramTaxonFragment.mCategoryCarat = paramFinder.findRequiredView(paramObject, 2131558772, "field 'mCategoryCarat'");
    paramTaxonFragment.mRecyclerView = ((RecyclerView)paramFinder.findRequiredView(paramObject, 2131558715, "field 'mRecyclerView'"));
    paramTaxonFragment.mProgressBar = ((CircularProgressBar)paramFinder.findRequiredView(paramObject, 2131558694, "field 'mProgressBar'"));
    paramTaxonFragment.mLayoutCategoryList = ((FrameLayout)paramFinder.findRequiredView(paramObject, 2131558773, "field 'mLayoutCategoryList'"));
    paramTaxonFragment.categoryInfo = paramFinder.findRequiredView(paramObject, 2131558523, "field 'categoryInfo'");
    paramTaxonFragment.categoryName = ((TextView)paramFinder.findRequiredView(paramObject, 2131558524, "field 'categoryName'"));
    View localView = paramFinder.findRequiredView(paramObject, 2131558882, "field 'sortByHeader' and method 'onSortByHeaderClick'");
    paramTaxonFragment.sortByHeader = localView;
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.onSortByHeaderClick();
      }
    });
    paramTaxonFragment.sortByCriteriaText = ((TextView)paramFinder.findRequiredView(paramObject, 2131558884, "field 'sortByCriteriaText'"));
  }
  
  public static void reset(TaxonFragment paramTaxonFragment)
  {
    paramTaxonFragment.mCategoryHeader = null;
    paramTaxonFragment.mCategoryCarat = null;
    paramTaxonFragment.mRecyclerView = null;
    paramTaxonFragment.mProgressBar = null;
    paramTaxonFragment.mLayoutCategoryList = null;
    paramTaxonFragment.categoryInfo = null;
    paramTaxonFragment.categoryName = null;
    paramTaxonFragment.sortByHeader = null;
    paramTaxonFragment.sortByCriteriaText = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/TaxonFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */