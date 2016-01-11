package com.happyfresh.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;

public class SuggestedSearchAdapter$SuggestedListItemViewHolder$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, SuggestedSearchAdapter.SuggestedListItemViewHolder paramSuggestedListItemViewHolder, Object paramObject)
  {
    paramSuggestedListItemViewHolder.suggestedIcon = ((ImageView)paramFinder.findRequiredView(paramObject, 2131559057, "field 'suggestedIcon'"));
    paramSuggestedListItemViewHolder.suggestedName = ((TextView)paramFinder.findRequiredView(paramObject, 2131559058, "field 'suggestedName'"));
    View localView = paramFinder.findRequiredView(paramObject, 2131559056, "field 'suggestedIconArrow' and method 'clickArrowIcon'");
    paramSuggestedListItemViewHolder.suggestedIconArrow = ((ImageView)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.clickArrowIcon();
      }
    });
    paramFinder.findRequiredView(paramObject, 2131559055, "method 'clickItem'").setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.clickItem();
      }
    });
  }
  
  public static void reset(SuggestedSearchAdapter.SuggestedListItemViewHolder paramSuggestedListItemViewHolder)
  {
    paramSuggestedListItemViewHolder.suggestedIcon = null;
    paramSuggestedListItemViewHolder.suggestedName = null;
    paramSuggestedListItemViewHolder.suggestedIconArrow = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/adapters/SuggestedSearchAdapter$SuggestedListItemViewHolder$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */