package com.happyfresh.models;

public class ProductGridHeader
  implements ProductGrid
{
  public Taxon taxon;
  
  public ProductGridHeader(Taxon paramTaxon)
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
    return 2;
  }
  
  public Taxon getTaxon()
  {
    return this.taxon;
  }
  
  public int getViewType()
  {
    return 1002;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/ProductGridHeader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */