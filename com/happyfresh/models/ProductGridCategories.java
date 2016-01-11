package com.happyfresh.models;

public class ProductGridCategories
  implements ProductGrid
{
  private PopularProduct mPopularProduct;
  private int mScrollX;
  
  public ProductGridCategories(PopularProduct paramPopularProduct)
  {
    this.mPopularProduct = paramPopularProduct;
  }
  
  public int compareTo(ProductGrid paramProductGrid)
  {
    return 0;
  }
  
  public PopularProduct getPopularProduct()
  {
    return this.mPopularProduct;
  }
  
  public Product getProduct()
  {
    return null;
  }
  
  public int getScrollX()
  {
    return this.mScrollX;
  }
  
  public int getSpanSize()
  {
    return 3;
  }
  
  public Taxon getTaxon()
  {
    return null;
  }
  
  public int getViewType()
  {
    return 1003;
  }
  
  public void setScrollX(int paramInt)
  {
    this.mScrollX = paramInt;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/ProductGridCategories.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */