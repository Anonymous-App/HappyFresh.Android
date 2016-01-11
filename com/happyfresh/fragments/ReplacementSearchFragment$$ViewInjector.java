package com.happyfresh.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;
import com.happyfresh.customs.CircularProgressBar;

public class ReplacementSearchFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, ReplacementSearchFragment paramReplacementSearchFragment, Object paramObject)
  {
    paramReplacementSearchFragment.mProductImage = ((ImageView)paramFinder.findRequiredView(paramObject, 2131558846, "field 'mProductImage'"));
    paramReplacementSearchFragment.mProductName = ((TextView)paramFinder.findRequiredView(paramObject, 2131558847, "field 'mProductName'"));
    paramReplacementSearchFragment.mDisplayPrice = ((TextView)paramFinder.findRequiredView(paramObject, 2131558848, "field 'mDisplayPrice'"));
    paramReplacementSearchFragment.mSearchView = ((SearchView)paramFinder.findRequiredView(paramObject, 2131558849, "field 'mSearchView'"));
    paramReplacementSearchFragment.mRecyclerView = ((RecyclerView)paramFinder.findRequiredView(paramObject, 2131558715, "field 'mRecyclerView'"));
    paramReplacementSearchFragment.mButtonProgress = ((CircularProgressBar)paramFinder.findRequiredView(paramObject, 2131558664, "field 'mButtonProgress'"));
    paramReplacementSearchFragment.mSearchProgress = ((CircularProgressBar)paramFinder.findRequiredView(paramObject, 2131558844, "field 'mSearchProgress'"));
    View localView = paramFinder.findRequiredView(paramObject, 2131558850, "field 'mDoneButton' and method 'done'");
    paramReplacementSearchFragment.mDoneButton = ((Button)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.done();
      }
    });
    paramReplacementSearchFragment.mExistingReplacement = paramFinder.findRequiredView(paramObject, 2131558843, "field 'mExistingReplacement'");
    paramReplacementSearchFragment.mReplacementProductImage = ((ImageView)paramFinder.findRequiredView(paramObject, 2131559096, "field 'mReplacementProductImage'"));
    paramReplacementSearchFragment.mReplacementProductName = ((TextView)paramFinder.findRequiredView(paramObject, 2131559098, "field 'mReplacementProductName'"));
    paramReplacementSearchFragment.mReplacementProductPrice = ((TextView)paramFinder.findRequiredView(paramObject, 2131559099, "field 'mReplacementProductPrice'"));
    localView = paramFinder.findRequiredView(paramObject, 2131559101, "field 'mReplaceButton' and method 'cancelReplacement'");
    paramReplacementSearchFragment.mReplaceButton = ((ImageView)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.cancelReplacement();
      }
    });
    paramReplacementSearchFragment.mReplaceButtonProgress = paramFinder.findRequiredView(paramObject, 2131559100, "field 'mReplaceButtonProgress'");
  }
  
  public static void reset(ReplacementSearchFragment paramReplacementSearchFragment)
  {
    paramReplacementSearchFragment.mProductImage = null;
    paramReplacementSearchFragment.mProductName = null;
    paramReplacementSearchFragment.mDisplayPrice = null;
    paramReplacementSearchFragment.mSearchView = null;
    paramReplacementSearchFragment.mRecyclerView = null;
    paramReplacementSearchFragment.mButtonProgress = null;
    paramReplacementSearchFragment.mSearchProgress = null;
    paramReplacementSearchFragment.mDoneButton = null;
    paramReplacementSearchFragment.mExistingReplacement = null;
    paramReplacementSearchFragment.mReplacementProductImage = null;
    paramReplacementSearchFragment.mReplacementProductName = null;
    paramReplacementSearchFragment.mReplacementProductPrice = null;
    paramReplacementSearchFragment.mReplaceButton = null;
    paramReplacementSearchFragment.mReplaceButtonProgress = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/ReplacementSearchFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */