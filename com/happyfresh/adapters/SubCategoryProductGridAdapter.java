package com.happyfresh.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.customs.BaseRecyclerViewHolder;
import com.happyfresh.customs.ProductRecyclerViewHolder;
import com.happyfresh.listeners.OnProductClickListener;
import com.happyfresh.models.PopularProduct;
import com.happyfresh.models.Product;
import com.happyfresh.models.ProductGrid;
import com.happyfresh.models.ProductGridCategory;
import com.happyfresh.models.ProductGridContent;
import com.happyfresh.models.Taxon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class SubCategoryProductGridAdapter
  extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
  public static String TAG = "SubCategoryProductGridAdapter";
  protected Context mContext;
  protected ICartApplication mICartApplication;
  private OnProductClickListener mItemClickListener;
  protected List<ProductGrid> mProductGrids = new ArrayList();
  private boolean mWithHeader;
  
  public SubCategoryProductGridAdapter(Context paramContext, OnProductClickListener paramOnProductClickListener)
  {
    this.mContext = paramContext;
    this.mICartApplication = ((ICartApplication)paramContext.getApplicationContext());
    this.mItemClickListener = paramOnProductClickListener;
  }
  
  private void addBlankCategory()
  {
    ProductGridCategory localProductGridCategory = new ProductGridCategory();
    this.mProductGrids.add(0, localProductGridCategory);
  }
  
  public void addAll(List<PopularProduct> paramList)
  {
    addAll(paramList, true);
  }
  
  public void addAll(List<PopularProduct> paramList, boolean paramBoolean)
  {
    this.mProductGrids.clear();
    if (paramBoolean) {
      addBlankCategory();
    }
    this.mWithHeader = paramBoolean;
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      PopularProduct localPopularProduct = (PopularProduct)paramList.next();
      Iterator localIterator = localPopularProduct.products.iterator();
      while (localIterator.hasNext())
      {
        Object localObject = (Product)localIterator.next();
        localObject = new ProductGridContent(localPopularProduct.taxon, (Product)localObject);
        this.mProductGrids.add(localObject);
      }
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
    clearAll(true);
  }
  
  public void clearAll(boolean paramBoolean)
  {
    this.mProductGrids.clear();
    if (paramBoolean) {
      addBlankCategory();
    }
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
    return 1001;
  }
  
  public int getSpanSize(int paramInt)
  {
    int i = ((ProductGrid)this.mProductGrids.get(paramInt)).getSpanSize();
    paramInt = i;
    if (i > 2) {
      paramInt = 2;
    }
    return paramInt;
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
    if (paramInt == 2001) {
      return new ProductRecyclerViewHolder(LayoutInflater.from(this.mContext).inflate(2130903186, null), this.mICartApplication, this.mContext, this.mProductGrids, this.mItemClickListener, ProductRecyclerViewHolder.VIEW_GRID);
    }
    return new BlankCategoryHolder(LayoutInflater.from(this.mContext).inflate(2130903071, null));
  }
  
  public void setWithHeader(boolean paramBoolean)
  {
    this.mWithHeader = paramBoolean;
  }
  
  public void sortBy(int paramInt)
  {
    ProductGridContent.sSortByCriteria = paramInt;
    Collections.sort(this.mProductGrids, ProductGridContent.sProductGridComparator);
  }
  
  class BlankCategoryHolder
    extends BaseRecyclerViewHolder
  {
    @InjectView(2131558519)
    View blankSortByView;
    
    public BlankCategoryHolder(View paramView)
    {
      super();
      ButterKnife.inject(this, paramView);
    }
    
    public void bindViewHolder(int paramInt)
    {
      this.blankSortByView.setVisibility(4);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/adapters/SubCategoryProductGridAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */