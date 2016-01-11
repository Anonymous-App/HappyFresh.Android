package com.happyfresh.models;

public abstract interface ProductGrid
  extends Comparable<ProductGrid>
{
  public abstract Product getProduct();
  
  public abstract int getSpanSize();
  
  public abstract Taxon getTaxon();
  
  public abstract int getViewType();
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/ProductGrid.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */