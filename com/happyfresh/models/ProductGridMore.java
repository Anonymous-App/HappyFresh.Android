package com.happyfresh.models;

public class ProductGridMore
  implements ProductGrid
{
  public Taxon taxon;
  
  public ProductGridMore(Taxon paramTaxon)
  {
    this.taxon = paramTaxon;
  }
  
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
    return 1;
  }
  
  public Taxon getTaxon()
  {
    return this.taxon;
  }
  
  public int getViewType()
  {
    return 2002;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/ProductGridMore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */