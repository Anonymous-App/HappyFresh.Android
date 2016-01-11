package com.happyfresh.adapters;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;

public class CartItemAdapter$CartItemViewHolder$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, CartItemAdapter.CartItemViewHolder paramCartItemViewHolder, Object paramObject)
  {
    View localView = paramFinder.findRequiredView(paramObject, 2131558513, "field 'container' and method 'clickItem'");
    paramCartItemViewHolder.container = ((RelativeLayout)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.clickItem();
      }
    });
    paramCartItemViewHolder.itemImage = ((ImageView)paramFinder.findRequiredView(paramObject, 2131558935, "field 'itemImage'"));
    paramCartItemViewHolder.itemName = ((TextView)paramFinder.findRequiredView(paramObject, 2131558936, "field 'itemName'"));
    paramCartItemViewHolder.itemPriceEach = ((TextView)paramFinder.findRequiredView(paramObject, 2131558937, "field 'itemPriceEach'"));
    paramCartItemViewHolder.itemPrice = ((TextView)paramFinder.findRequiredView(paramObject, 2131558938, "field 'itemPrice'"));
    paramCartItemViewHolder.totalItemInTheCart = ((TextView)paramFinder.findRequiredView(paramObject, 2131558908, "field 'totalItemInTheCart'"));
    paramCartItemViewHolder.totalItemView = paramFinder.findRequiredView(paramObject, 2131558939, "field 'totalItemView'");
    paramCartItemViewHolder.unavailable = ((TextView)paramFinder.findRequiredView(paramObject, 2131558940, "field 'unavailable'"));
    localView = paramFinder.findRequiredView(paramObject, 2131558909, "field 'btnAddItem' and method 'addItem'");
    paramCartItemViewHolder.btnAddItem = ((Button)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.addItem(paramAnonymousView);
      }
    });
    paramFinder = paramFinder.findRequiredView(paramObject, 2131558907, "field 'btnSubItem' and method 'subItem'");
    paramCartItemViewHolder.btnSubItem = ((Button)paramFinder);
    paramFinder.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.subItem(paramAnonymousView);
      }
    });
  }
  
  public static void reset(CartItemAdapter.CartItemViewHolder paramCartItemViewHolder)
  {
    paramCartItemViewHolder.container = null;
    paramCartItemViewHolder.itemImage = null;
    paramCartItemViewHolder.itemName = null;
    paramCartItemViewHolder.itemPriceEach = null;
    paramCartItemViewHolder.itemPrice = null;
    paramCartItemViewHolder.totalItemInTheCart = null;
    paramCartItemViewHolder.totalItemView = null;
    paramCartItemViewHolder.unavailable = null;
    paramCartItemViewHolder.btnAddItem = null;
    paramCartItemViewHolder.btnSubItem = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/adapters/CartItemAdapter$CartItemViewHolder$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */