package com.happyfresh.adapters;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;

public class ReplacementItemAdapter$OverallReplacementItemViewHolder$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, ReplacementItemAdapter.OverallReplacementItemViewHolder paramOverallReplacementItemViewHolder, Object paramObject)
  {
    paramOverallReplacementItemViewHolder.mReplacementOptions = ((RadioGroup)paramFinder.findRequiredView(paramObject, 2131559091, "field 'mReplacementOptions'"));
    View localView = paramFinder.findRequiredView(paramObject, 2131559092, "field 'mReplacementOption1' and method 'onOverallReplacementOption1Click'");
    paramOverallReplacementItemViewHolder.mReplacementOption1 = ((RadioButton)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.onOverallReplacementOption1Click();
      }
    });
    localView = paramFinder.findRequiredView(paramObject, 2131559093, "field 'mReplacementOption2' and method 'onOverallReplacementOption2Click'");
    paramOverallReplacementItemViewHolder.mReplacementOption2 = ((RadioButton)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.onOverallReplacementOption2Click();
      }
    });
    paramFinder = paramFinder.findRequiredView(paramObject, 2131559094, "field 'mReplacementOption3' and method 'onOverallReplacementOption3Click'");
    paramOverallReplacementItemViewHolder.mReplacementOption3 = ((RadioButton)paramFinder);
    paramFinder.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.onOverallReplacementOption3Click();
      }
    });
  }
  
  public static void reset(ReplacementItemAdapter.OverallReplacementItemViewHolder paramOverallReplacementItemViewHolder)
  {
    paramOverallReplacementItemViewHolder.mReplacementOptions = null;
    paramOverallReplacementItemViewHolder.mReplacementOption1 = null;
    paramOverallReplacementItemViewHolder.mReplacementOption2 = null;
    paramOverallReplacementItemViewHolder.mReplacementOption3 = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/adapters/ReplacementItemAdapter$OverallReplacementItemViewHolder$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */