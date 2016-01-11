package com.happyfresh.adapters;

import android.widget.TextView;
import butterknife.ButterKnife.Finder;

public class OrderAdapter$HeaderViewHolder$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, OrderAdapter.HeaderViewHolder paramHeaderViewHolder, Object paramObject)
  {
    paramHeaderViewHolder.orderTitle = ((TextView)paramFinder.findRequiredView(paramObject, 2131559021, "field 'orderTitle'"));
  }
  
  public static void reset(OrderAdapter.HeaderViewHolder paramHeaderViewHolder)
  {
    paramHeaderViewHolder.orderTitle = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/adapters/OrderAdapter$HeaderViewHolder$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */