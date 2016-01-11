package com.happyfresh.customs;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;
import com.andexert.library.RippleView;

public class ProductRecyclerViewHolder$$ViewInjector
{
  public static void inject(ButterKnife.Finder paramFinder, ProductRecyclerViewHolder paramProductRecyclerViewHolder, Object paramObject)
  {
    paramProductRecyclerViewHolder.productContainer = ((CardView)paramFinder.findRequiredView(paramObject, 2131559022, "field 'productContainer'"));
    paramProductRecyclerViewHolder.productNameTextView = ((TextView)paramFinder.findRequiredView(paramObject, 2131559027, "field 'productNameTextView'"));
    paramProductRecyclerViewHolder.productPriceTextView = ((TextView)paramFinder.findRequiredView(paramObject, 2131559028, "field 'productPriceTextView'"));
    paramProductRecyclerViewHolder.productOriginalPriceTextView = ((TextView)paramFinder.findRequiredView(paramObject, 2131559029, "field 'productOriginalPriceTextView'"));
    paramProductRecyclerViewHolder.productDiscount = ((TextView)paramFinder.findRequiredView(paramObject, 2131559030, "field 'productDiscount'"));
    paramProductRecyclerViewHolder.productDiscountTotalOrder = ((TextView)paramFinder.findRequiredView(paramObject, 2131559031, "field 'productDiscountTotalOrder'"));
    paramProductRecyclerViewHolder.productFreeDelivery = ((TextView)paramFinder.findRequiredView(paramObject, 2131559032, "field 'productFreeDelivery'"));
    paramProductRecyclerViewHolder.productImageView = ((ImageView)paramFinder.findRequiredView(paramObject, 2131559026, "field 'productImageView'"));
    paramProductRecyclerViewHolder.productCartCounter = ((TextView)paramFinder.findRequiredView(paramObject, 2131559038, "field 'productCartCounter'"));
    paramProductRecyclerViewHolder.rippleView = ((RippleView)paramFinder.findRequiredView(paramObject, 2131559024, "field 'rippleView'"));
    View localView = paramFinder.findRequiredView(paramObject, 2131559033, "field 'addToCartContainer' and method 'onAddToCartContainerClick'");
    paramProductRecyclerViewHolder.addToCartContainer = localView;
    localView.setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.onAddToCartContainerClick();
      }
    });
    paramProductRecyclerViewHolder.minusPlusCartContainer = paramFinder.findRequiredView(paramObject, 2131559036, "field 'minusPlusCartContainer'");
    paramProductRecyclerViewHolder.progressBar = ((CircularProgressBar)paramFinder.findRequiredView(paramObject, 2131559040, "field 'progressBar'"));
    paramFinder.findRequiredView(paramObject, 2131559037, "method 'onMinusCartClick'").setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.onMinusCartClick();
      }
    });
    paramFinder.findRequiredView(paramObject, 2131559039, "method 'onPlusCartClick'").setOnClickListener(new DebouncingOnClickListener()
    {
      public void doClick(View paramAnonymousView)
      {
        this.val$target.onPlusCartClick();
      }
    });
  }
  
  public static void reset(ProductRecyclerViewHolder paramProductRecyclerViewHolder)
  {
    paramProductRecyclerViewHolder.productContainer = null;
    paramProductRecyclerViewHolder.productNameTextView = null;
    paramProductRecyclerViewHolder.productPriceTextView = null;
    paramProductRecyclerViewHolder.productOriginalPriceTextView = null;
    paramProductRecyclerViewHolder.productDiscount = null;
    paramProductRecyclerViewHolder.productDiscountTotalOrder = null;
    paramProductRecyclerViewHolder.productFreeDelivery = null;
    paramProductRecyclerViewHolder.productImageView = null;
    paramProductRecyclerViewHolder.productCartCounter = null;
    paramProductRecyclerViewHolder.rippleView = null;
    paramProductRecyclerViewHolder.addToCartContainer = null;
    paramProductRecyclerViewHolder.minusPlusCartContainer = null;
    paramProductRecyclerViewHolder.progressBar = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/customs/ProductRecyclerViewHolder$$ViewInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */