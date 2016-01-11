package com.happyfresh.adapters;

import android.view.View;
import android.widget.TextView;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;

public class CartItemAdapter$CartHeaderViewHolder$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, CartItemAdapter.CartHeaderViewHolder paramCartHeaderViewHolder, Object paramObject)
  {
    paramCartHeaderViewHolder.subtotalPrice = ((TextView)paramFinder.findRequiredView(paramObject, 2131558933, "field 'subtotalPrice'"));
    paramCartHeaderViewHolder.mFeeDeliveryReminderView = paramFinder.findRequiredView(paramObject, 2131558931, "field 'mFeeDeliveryReminderView'");
    paramCartHeaderViewHolder.mFeeDeliveryReminder = ((TextView)paramFinder.findRequiredView(paramObject, 2131558932, "field 'mFeeDeliveryReminder'"));
    paramFinder.findRequiredView(paramObject, 2131558934, "method 'closeCart'").setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.closeCart();
      }
    });
  }
  
  public static void reset(CartItemAdapter.CartHeaderViewHolder paramCartHeaderViewHolder)
  {
    paramCartHeaderViewHolder.subtotalPrice = null;
    paramCartHeaderViewHolder.mFeeDeliveryReminderView = null;
    paramCartHeaderViewHolder.mFeeDeliveryReminder = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/adapters/CartItemAdapter$CartHeaderViewHolder$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */