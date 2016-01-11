package com.happyfresh.listeners;

import android.view.View;
import com.happyfresh.models.Product;
import com.happyfresh.models.Taxon;

public abstract interface OnProductClickListener
{
  public abstract void onHeaderClick(View paramView, Taxon paramTaxon);
  
  public abstract void onItemClick(View paramView, Product paramProduct);
  
  public abstract void onViewMoreClick(View paramView, Taxon paramTaxon);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/listeners/OnProductClickListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */