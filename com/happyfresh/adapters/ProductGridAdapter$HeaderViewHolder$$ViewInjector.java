package com.happyfresh.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;

public class ProductGridAdapter$HeaderViewHolder$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, ProductGridAdapter.HeaderViewHolder paramHeaderViewHolder, Object paramObject)
  {
    paramHeaderViewHolder.categoryIcon = ((ImageView)paramFinder.findRequiredView(paramObject, 2131558947, "field 'categoryIcon'"));
    paramHeaderViewHolder.taxonName = ((TextView)paramFinder.findRequiredView(paramObject, 2131558948, "field 'taxonName'"));
    paramFinder.findRequiredView(paramObject, 2131558946, "method 'onHeaderClick'").setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.onHeaderClick(paramAnonymousView);
      }
    });
  }
  
  public static void reset(ProductGridAdapter.HeaderViewHolder paramHeaderViewHolder)
  {
    paramHeaderViewHolder.categoryIcon = null;
    paramHeaderViewHolder.taxonName = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/adapters/ProductGridAdapter$HeaderViewHolder$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */