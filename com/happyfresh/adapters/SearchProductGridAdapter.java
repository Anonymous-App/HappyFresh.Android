package com.happyfresh.adapters;

import android.content.Context;
import com.happyfresh.models.PopularProduct;
import com.happyfresh.models.ProductGrid;
import com.happyfresh.models.ProductGridCategories;
import com.happyfresh.models.ProductGridHeader;
import com.happyfresh.models.ProductGridMore;
import java.util.Iterator;
import java.util.List;

public class SearchProductGridAdapter
  extends ProductGridAdapter
{
  public SearchProductGridAdapter(Context paramContext)
  {
    super(paramContext, false);
    this.mShowMore = false;
  }
  
  public void addAll(List<PopularProduct> paramList)
  {
    this.mProductGrids.clear();
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      Object localObject1 = (PopularProduct)paramList.next();
      Object localObject2 = new ProductGridHeader(((PopularProduct)localObject1).taxon);
      this.mProductGrids.add(localObject2);
      if (!((PopularProduct)localObject1).products.isEmpty())
      {
        localObject2 = new ProductGridMore(((ProductGrid)localObject2).getTaxon());
        this.mProductGrids.add(localObject2);
      }
      localObject1 = new ProductGridCategories((PopularProduct)localObject1);
      this.mProductGrids.add(localObject1);
    }
  }
  
  public void clearAll()
  {
    this.mProductGrids.clear();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/adapters/SearchProductGridAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */