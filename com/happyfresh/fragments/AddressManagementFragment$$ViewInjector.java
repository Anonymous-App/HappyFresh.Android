package com.happyfresh.fragments;

import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;
import butterknife.ButterKnife.Finder;

public class AddressManagementFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, AddressManagementFragment paramAddressManagementFragment, Object paramObject)
  {
    paramAddressManagementFragment.mCreateNew = ((TextView)paramFinder.findRequiredView(paramObject, 2131558604, "field 'mCreateNew'"));
    paramAddressManagementFragment.mRecyclerView = ((RecyclerView)paramFinder.findRequiredView(paramObject, 2131558605, "field 'mRecyclerView'"));
    paramAddressManagementFragment.mBackButton = ((Button)paramFinder.findRequiredView(paramObject, 2131558607, "field 'mBackButton'"));
  }
  
  public static void reset(AddressManagementFragment paramAddressManagementFragment)
  {
    paramAddressManagementFragment.mCreateNew = null;
    paramAddressManagementFragment.mRecyclerView = null;
    paramAddressManagementFragment.mBackButton = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/AddressManagementFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */