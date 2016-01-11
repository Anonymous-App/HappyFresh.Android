package com.happyfresh.listeners;

import android.view.View;
import com.happyfresh.models.LineItem;

public abstract interface OnAddToCartClickListener
{
  public abstract void onClick(View paramView, LineItem paramLineItem);
  
  public abstract void onReplacementClick(View paramView, LineItem paramLineItem);
  
  public abstract void onShopperReplacementClick(View paramView, LineItem paramLineItem);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/listeners/OnAddToCartClickListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */