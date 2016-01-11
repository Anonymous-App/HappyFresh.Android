package com.happyfresh.adapters;

import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife.Finder;

public class RecommendedListAdapter$RecommendedViewHolder$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, RecommendedListAdapter.RecommendedViewHolder paramRecommendedViewHolder, Object paramObject)
  {
    paramRecommendedViewHolder.img = ((ImageView)paramFinder.findRequiredView(paramObject, 2131559041, "field 'img'"));
    paramRecommendedViewHolder.txt = ((TextView)paramFinder.findRequiredView(paramObject, 2131559042, "field 'txt'"));
  }
  
  public static void reset(RecommendedListAdapter.RecommendedViewHolder paramRecommendedViewHolder)
  {
    paramRecommendedViewHolder.img = null;
    paramRecommendedViewHolder.txt = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/adapters/RecommendedListAdapter$RecommendedViewHolder$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */