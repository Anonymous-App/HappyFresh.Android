package com.happyfresh.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;

public class ShoppingListItemAdapter$ShoppingListItemViewHolder$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, ShoppingListItemAdapter.ShoppingListItemViewHolder paramShoppingListItemViewHolder, Object paramObject)
  {
    paramShoppingListItemViewHolder.itemImage = ((ImageView)paramFinder.findRequiredView(paramObject, 2131558935, "field 'itemImage'"));
    paramShoppingListItemViewHolder.itemName = ((TextView)paramFinder.findRequiredView(paramObject, 2131558936, "field 'itemName'"));
    paramShoppingListItemViewHolder.itemPrice = ((TextView)paramFinder.findRequiredView(paramObject, 2131558938, "field 'itemPrice'"));
    paramShoppingListItemViewHolder.totalAddToCart = ((TextView)paramFinder.findRequiredView(paramObject, 2131559047, "field 'totalAddToCart'"));
    paramShoppingListItemViewHolder.totalItemView = paramFinder.findRequiredView(paramObject, 2131558939, "field 'totalItemView'");
    paramShoppingListItemViewHolder.totalInCart = ((TextView)paramFinder.findRequiredView(paramObject, 2131559046, "field 'totalInCart'"));
    paramFinder.findRequiredView(paramObject, 2131558909, "method 'addItem'").setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.addItem(paramAnonymousView);
      }
    });
    paramFinder.findRequiredView(paramObject, 2131558907, "method 'subItem'").setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.subItem(paramAnonymousView);
      }
    });
    paramFinder.findRequiredView(paramObject, 2131559045, "method 'clickItem'").setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.clickItem();
      }
    });
  }
  
  public static void reset(ShoppingListItemAdapter.ShoppingListItemViewHolder paramShoppingListItemViewHolder)
  {
    paramShoppingListItemViewHolder.itemImage = null;
    paramShoppingListItemViewHolder.itemName = null;
    paramShoppingListItemViewHolder.itemPrice = null;
    paramShoppingListItemViewHolder.totalAddToCart = null;
    paramShoppingListItemViewHolder.totalItemView = null;
    paramShoppingListItemViewHolder.totalInCart = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/adapters/ShoppingListItemAdapter$ShoppingListItemViewHolder$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */