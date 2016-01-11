package com.happyfresh.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;
import com.happyfresh.customs.CircularProgressBar;

public class ShoppingCartFragment$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, ShoppingCartFragment paramShoppingCartFragment, Object paramObject)
  {
    paramShoppingCartFragment.mCartHasItem = ((RelativeLayout)paramFinder.findRequiredView(paramObject, 2131558635, "field 'mCartHasItem'"));
    paramShoppingCartFragment.mEmptyCart = ((RelativeLayout)paramFinder.findRequiredView(paramObject, 2131558631, "field 'mEmptyCart'"));
    paramShoppingCartFragment.mRecyclerView = ((RecyclerView)paramFinder.findRequiredView(paramObject, 2131558636, "field 'mRecyclerView'"));
    paramShoppingCartFragment.mButtonProgressBar = ((CircularProgressBar)paramFinder.findRequiredView(paramObject, 2131558639, "field 'mButtonProgressBar'"));
    View localView = paramFinder.findRequiredView(paramObject, 2131558638, "field 'mButtonCheckout' and method 'onCheckoutClick'");
    paramShoppingCartFragment.mButtonCheckout = ((Button)localView);
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.onCheckoutClick();
      }
    });
    paramFinder.findRequiredView(paramObject, 2131558634, "method 'backToShopping'").setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.backToShopping();
      }
    });
    paramFinder.findRequiredView(paramObject, 2131558934, "method 'closeCart'").setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.closeCart();
      }
    });
  }
  
  public static void reset(ShoppingCartFragment paramShoppingCartFragment)
  {
    paramShoppingCartFragment.mCartHasItem = null;
    paramShoppingCartFragment.mEmptyCart = null;
    paramShoppingCartFragment.mRecyclerView = null;
    paramShoppingCartFragment.mButtonProgressBar = null;
    paramShoppingCartFragment.mButtonCheckout = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/ShoppingCartFragment$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */