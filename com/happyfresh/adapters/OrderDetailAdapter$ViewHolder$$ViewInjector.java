package com.happyfresh.adapters;

import android.widget.TextView;
import butterknife.ButterKnife.Finder;

public class OrderDetailAdapter$ViewHolder$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, OrderDetailAdapter.ViewHolder paramViewHolder, Object paramObject)
  {
    paramViewHolder.itemText = ((TextView)paramFinder.findRequiredView(paramObject, 2131558951, "field 'itemText'"));
    paramViewHolder.priceText = ((TextView)paramFinder.findRequiredView(paramObject, 2131558952, "field 'priceText'"));
  }
  
  public static void reset(OrderDetailAdapter.ViewHolder paramViewHolder)
  {
    paramViewHolder.itemText = null;
    paramViewHolder.priceText = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/adapters/OrderDetailAdapter$ViewHolder$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */