package com.happyfresh.models;

public class ProductGridCategory
  implements ProductGrid
{
  public int compareTo(ProductGrid paramProductGrid)
  {
    return 0;
  }
  
  public Product getProduct()
  {
    return null;
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
    return 1001;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/ProductGridCategory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */