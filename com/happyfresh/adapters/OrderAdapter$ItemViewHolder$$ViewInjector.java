package com.happyfresh.adapters;

import android.widget.TextView;
import butterknife.ButterKnife.Finder;

public class OrderAdapter$ItemViewHolder$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, OrderAdapter.ItemViewHolder paramItemViewHolder, Object paramObject)
  {
    paramItemViewHolder.orderDate = ((TextView)paramFinder.findRequiredView(paramObject, 2131558955, "field 'orderDate'"));
    paramItemViewHolder.orderStatus = ((TextView)paramFinder.findRequiredView(paramObject, 2131558957, "field 'orderStatus'"));
    paramItemViewHolder.orderStockLocation = ((TextView)paramFinder.findRequiredView(paramObject, 2131558956, "field 'orderStockLocation'"));
  }
  
  public static void reset(OrderAdapter.ItemViewHolder paramItemViewHolder)
  {
    paramItemViewHolder.orderDate = null;
    paramItemViewHolder.orderStatus = null;
    paramItemViewHolder.orderStockLocation = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/adapters/OrderAdapter$ItemViewHolder$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */