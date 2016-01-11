package com.happyfresh.fragments;

import android.widget.ExpandableListView;
import butterknife.ButterKnife.Finder;

public class CategoryPickerFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, CategoryPickerFragment paramCategoryPickerFragment, Object paramObject)
  {
    paramCategoryPickerFragment.mExpandedListView = ((ExpandableListView)paramFinder.findRequiredView(paramObject, 2131558640, "field 'mExpandedListView'"));
  }
  
  public static void reset(CategoryPickerFragment paramCategoryPickerFragment)
  {
    paramCategoryPickerFragment.mExpandedListView = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/CategoryPickerFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */