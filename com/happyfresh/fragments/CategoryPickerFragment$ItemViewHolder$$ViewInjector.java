package com.happyfresh.fragments;

import android.widget.TextView;
import butterknife.ButterKnife.Finder;

public class CategoryPickerFragment$ItemViewHolder$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, CategoryPickerFragment.ItemViewHolder paramItemViewHolder, Object paramObject)
  {
    paramItemViewHolder.categoryName = ((TextView)paramFinder.findRequiredView(paramObject, 2131558524, "field 'categoryName'"));
  }
  
  public static void reset(CategoryPickerFragment.ItemViewHolder paramItemViewHolder)
  {
    paramItemViewHolder.categoryName = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/CategoryPickerFragment$ItemViewHolder$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */