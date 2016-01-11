package com.happyfresh.fragments;

import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife.Finder;
import com.happyfresh.customs.CircularProgressBar;

public class MainFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, MainFragment paramMainFragment, Object paramObject)
  {
    paramMainFragment.mCategoryHeader = ((RelativeLayout)paramFinder.findRequiredView(paramObject, 2131558769, "field 'mCategoryHeader'"));
    paramMainFragment.mCategoryCarat = paramFinder.findRequiredView(paramObject, 2131558772, "field 'mCategoryCarat'");
    paramMainFragment.mLayoutCategoryList = ((FrameLayout)paramFinder.findRequiredView(paramObject, 2131558773, "field 'mLayoutCategoryList'"));
    paramMainFragment.storeInfoArea = paramFinder.findRequiredView(paramObject, 2131558520, "field 'storeInfoArea'");
    paramMainFragment.categoryInfo = paramFinder.findRequiredView(paramObject, 2131558523, "field 'categoryInfo'");
    paramMainFragment.shoppingLocation = ((TextView)paramFinder.findRequiredView(paramObject, 2131558521, "field 'shoppingLocation'"));
    paramMainFragment.storeLocation = ((TextView)paramFinder.findRequiredView(paramObject, 2131558522, "field 'storeLocation'"));
    paramMainFragment.categoryName = ((TextView)paramFinder.findRequiredView(paramObject, 2131558524, "field 'categoryName'"));
    paramMainFragment.mRecyclerView = ((RecyclerView)paramFinder.findRequiredView(paramObject, 2131558715, "field 'mRecyclerView'"));
    paramMainFragment.mProgressBar = ((CircularProgressBar)paramFinder.findRequiredView(paramObject, 2131558694, "field 'mProgressBar'"));
  }
  
  public static void reset(MainFragment paramMainFragment)
  {
    paramMainFragment.mCategoryHeader = null;
    paramMainFragment.mCategoryCarat = null;
    paramMainFragment.mLayoutCategoryList = null;
    paramMainFragment.storeInfoArea = null;
    paramMainFragment.categoryInfo = null;
    paramMainFragment.shoppingLocation = null;
    paramMainFragment.storeLocation = null;
    paramMainFragment.categoryName = null;
    paramMainFragment.mRecyclerView = null;
    paramMainFragment.mProgressBar = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/MainFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */