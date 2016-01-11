package com.happyfresh.fragments;

import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife.Finder;

public class OrderStatusFragment$OrderStatusAdapter$ViewHolder$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, OrderStatusFragment.OrderStatusAdapter.ViewHolder paramViewHolder, Object paramObject)
  {
    paramViewHolder.statusLayout = ((RelativeLayout)paramFinder.findRequiredView(paramObject, 2131558513, "field 'statusLayout'"));
    paramViewHolder.statusInfo = ((TextView)paramFinder.findRequiredView(paramObject, 2131559020, "field 'statusInfo'"));
    paramViewHolder.statusInfoTime = ((TextView)paramFinder.findRequiredView(paramObject, 2131559018, "field 'statusInfoTime'"));
    paramViewHolder.statusInfoDay = ((TextView)paramFinder.findRequiredView(paramObject, 2131559019, "field 'statusInfoDay'"));
  }
  
  public static void reset(OrderStatusFragment.OrderStatusAdapter.ViewHolder paramViewHolder)
  {
    paramViewHolder.statusLayout = null;
    paramViewHolder.statusInfo = null;
    paramViewHolder.statusInfoTime = null;
    paramViewHolder.statusInfoDay = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/OrderStatusFragment$OrderStatusAdapter$ViewHolder$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */