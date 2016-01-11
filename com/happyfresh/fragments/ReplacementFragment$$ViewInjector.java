package com.happyfresh.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;
import com.happyfresh.customs.CircularProgressBar;

public class ReplacementFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, ReplacementFragment paramReplacementFragment, Object paramObject)
  {
    paramReplacementFragment.mRecyclerView = ((RecyclerView)paramFinder.findRequiredView(paramObject, 2131558715, "field 'mRecyclerView'"));
    View localView = paramFinder.findRequiredView(paramObject, 2131558663, "field 'mSubmitButton' and method 'attemptOpenDelivery'");
    paramReplacementFragment.mSubmitButton = ((Button)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.attemptOpenDelivery();
      }
    });
    paramReplacementFragment.mButtonProgress = ((CircularProgressBar)paramFinder.findRequiredView(paramObject, 2131558664, "field 'mButtonProgress'"));
    paramReplacementFragment.mOverlay = paramFinder.findRequiredView(paramObject, 2131558831, "field 'mOverlay'");
    paramReplacementFragment.mOverlayProgress = paramFinder.findRequiredView(paramObject, 2131558832, "field 'mOverlayProgress'");
  }
  
  public static void reset(ReplacementFragment paramReplacementFragment)
  {
    paramReplacementFragment.mRecyclerView = null;
    paramReplacementFragment.mSubmitButton = null;
    paramReplacementFragment.mButtonProgress = null;
    paramReplacementFragment.mOverlay = null;
    paramReplacementFragment.mOverlayProgress = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/ReplacementFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */