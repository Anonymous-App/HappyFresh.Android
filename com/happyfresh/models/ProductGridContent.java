package com.happyfresh.models;

import java.util.Comparator;

public class ProductGridContent
  implements ProductGrid
{
  public static Comparator<ProductGrid> sProductGridComparator = new Comparator()
  {
    public int compare(ProductGrid paramAnonymousProductGrid1, ProductGrid paramAnonymousProductGrid2)
    {
      return paramAnonymousProductGrid1.compareTo(paramAnonymousProductGrid2);
    }
  };
  public static int sSortByCriteria = 0;
  private Product product;
  private Taxon taxon;
  
  public ProductGridContent(Taxon paramTaxon, Product paramProduct)
  {
    this.taxon = paramTaxon;
    this.product = paramProduct;
  }
  
  private int compareByNameAsc(Product paramProduct)
  {
    if (this.product.name.trim().compareTo(paramProduct.name.trim()) < 0) {
      return -1;
    }
    if (this.product.name.trim().compareTo(paramProduct.name.trim()) > 0) {
      return 1;
    }
    return 0;
  }
  
  private int compareByNameDesc(Product paramProduct)
  {
    if (this.product.name.trim().compareTo(paramProduct.name.trim()) > 0) {
      return -1;
    }
    if (this.product.name.trim().compareTo(paramProduct.name.trim()) < 0) {
      return 1;
    }
    return 0;
  }
  
  private int compareByPopularityDesc(Product paramProduct)
  {
    if (this.product.popularity.doubleValue() > paramProduct.popularity.doubleValue()) {
      return -1;
    }
    if (this.product.popularity.doubleValue() < paramProduct.popularity.doubleValue()) {
      return 1;
    }
    return 0;
  }
  
  private int compareByPriceAsc(Product paramProduct)
  {
    if (this.product.price.doubleValue() < paramProduct.price.doubleValue()) {
      return -1;
    }
    if (this.product.price.doubleValue() > paramProduct.price.doubleValue()) {
      return 1;
    }
    return 0;
  }
  
  private int compareByPriceDesc(Product paramProduct)
  {
    if (this.product.price.doubleValue() > paramProduct.price.doubleValue()) {
      return -1;
    }
    if (this.product.price.doubleValue() < paramProduct.price.doubleValue()) {
      return 1;
    }
    return 0;
  }
  
  public int compareTo(ProductGrid paramProductGrid)
  {
    if (this == paramProductGrid) {}
    do
    {
      return 0;
      paramProductGrid = paramProductGrid.getProduct();
    } while (paramProductGrid == null);
    switch (sSortByCriteria)
    {
    default: 
      return compareByPopularityDesc(paramProductGrid);
    case 0: 
      return compareByPopularityDesc(paramProductGrid);
    case 2: 
      return compareByPriceDesc(paramProductGrid);
    case 1: 
      return compareByPriceAsc(paramProductGrid);
    case 3: 
      return compareByNameAsc(paramProductGrid);
    }
    return compareByNameDesc(paramProductGrid);
  }
  
  public Product getProduct()
  {
    return this.product;
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
    return 2001;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/ProductGridContent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */