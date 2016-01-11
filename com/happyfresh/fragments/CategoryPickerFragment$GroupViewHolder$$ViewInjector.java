package com.happyfresh.fragments;

import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife.Finder;

public class CategoryPickerFragment$GroupViewHolder$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, CategoryPickerFragment.GroupViewHolder paramGroupViewHolder, Object paramObject)
  {
    paramGroupViewHolder.categoryIcon = ((ImageView)paramFinder.findRequiredView(paramObject, 2131558941, "field 'categoryIcon'"));
    paramGroupViewHolder.categoryName = ((TextView)paramFinder.findRequiredView(paramObject, 2131558524, "field 'categoryName'"));
  }
  
  public static void reset(CategoryPickerFragment.GroupViewHolder paramGroupViewHolder)
  {
    paramGroupViewHolder.categoryIcon = null;
    paramGroupViewHolder.categoryName = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/CategoryPickerFragment$GroupViewHolder$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */