package com.happyfresh.fragments;

import android.widget.TextView;
import butterknife.ButterKnife.Finder;

public class MyAccountFragment$AddressItemAdapter$AddressItemViewHolder$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, MyAccountFragment.AddressItemAdapter.AddressItemViewHolder paramAddressItemViewHolder, Object paramObject)
  {
    paramAddressItemViewHolder.deliveryAddress = ((TextView)paramFinder.findRequiredView(paramObject, 2131558516, "field 'deliveryAddress'"));
    paramAddressItemViewHolder.deliveryAddressView = paramFinder.findRequiredView(paramObject, 2131558515, "field 'deliveryAddressView'");
  }
  
  public static void reset(MyAccountFragment.AddressItemAdapter.AddressItemViewHolder paramAddressItemViewHolder)
  {
    paramAddressItemViewHolder.deliveryAddress = null;
    paramAddressItemViewHolder.deliveryAddressView = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/MyAccountFragment$AddressItemAdapter$AddressItemViewHolder$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */