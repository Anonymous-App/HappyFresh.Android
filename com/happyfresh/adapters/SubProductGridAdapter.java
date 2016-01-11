package com.happyfresh.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.customs.BaseRecyclerViewHolder;
import com.happyfresh.customs.ProductRecyclerViewHolder;
import com.happyfresh.listeners.OnProductClickListener;
import com.happyfresh.models.PopularProduct;
import com.happyfresh.models.Product;
import com.happyfresh.models.ProductGrid;
import com.happyfresh.models.ProductGridContent;
import com.happyfresh.models.Taxon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class SubProductGridAdapter
  extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
  protected Context mContext;
  protected ICartApplication mICartApplication;
  OnProductClickListener mItemClickListener;
  protected List<ProductGrid> mProductGrids = new ArrayList();
  
  public SubProductGridAdapter(Context paramContext)
  {
    this.mContext = paramContext;
    this.mICartApplication = ((ICartApplication)paramContext.getApplicationContext());
  }
  
  public void addAll(PopularProduct paramPopularProduct)
  {
    this.mProductGrids.clear();
    Iterator localIterator = paramPopularProduct.products.iterator();
    while (localIterator.hasNext())
    {
      Object localObject = (Product)localIterator.next();
      localObject = new ProductGridContent(paramPopularProduct.taxon, (Product)localObject);
      this.mProductGrids.add(localObject);
    }
    if (paramPopularProduct.taxon.name.equalsIgnoreCase(this.mContext.getString(2131165588)))
    {
      ProductGridContent.sSortByCriteria = 0;
      Collections.sort(this.mProductGrids, ProductGridContent.sProductGridComparator);
    }
  }
  
  public void addTaxonProductList(String paramString, List<Product> paramList)
  {
    Taxon localTaxon = new Taxon();
    localTaxon.name = paramString;
    paramString = paramList.iterator();
    while (paramString.hasNext())
    {
      paramList = new ProductGridContent(localTaxon, (Product)paramString.next());
      this.mProductGrids.add(paramList);
    }
  }
  
  public void clearAll()
  {
    this.mProductGrids.clear();
  }
  
  public int getItemCount()
  {
    return this.mProductGrids.size();
  }
  
  public int getItemViewType(int paramInt)
  {
    if (paramInt < getItemCount()) {
      return ((ProductGrid)this.mProductGrids.get(paramInt)).getViewType();
    }
    return 1002;
  }
  
  protected String getTaxonDisplayName(Taxon paramTaxon)
  {
    String str = paramTaxon.name;
    paramTaxon = str;
    if (str.contains("#{ALL}"))
    {
      paramTaxon = str.replace("#{ALL}", "");
      paramTaxon = this.mContext.getString(2131165279, new Object[] { paramTaxon });
    }
    return paramTaxon;
  }
  
  public void onBindViewHolder(RecyclerView.ViewHolder paramViewHolder, int paramInt)
  {
    ((BaseRecyclerViewHolder)paramViewHolder).bindViewHolder(paramInt);
  }
  
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
  {
    paramViewGroup = null;
    if (paramInt == 2001) {
      paramViewGroup = new ProductRecyclerViewHolder(LayoutInflater.from(this.mContext).inflate(2130903180, null), this.mICartApplication, this.mContext, this.mProductGrids, this.mItemClickListener, ProductRecyclerViewHolder.VIEW_LINEAR_HORIZONTAL);
    }
    return paramViewGroup;
  }
  
  public void setOnItemClickListener(OnProductClickListener paramOnProductClickListener)
  {
    this.mItemClickListener = paramOnProductClickListener;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/adapters/SubProductGridAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */