package com.happyfresh.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.customs.BaseRecyclerViewHolder;
import com.happyfresh.listeners.OnProductClickListener;
import com.happyfresh.models.PopularProduct;
import com.happyfresh.models.Product;
import com.happyfresh.models.ProductGrid;
import com.happyfresh.models.ProductGridCategories;
import com.happyfresh.models.ProductGridCategory;
import com.happyfresh.models.ProductGridHeader;
import com.happyfresh.models.ProductGridMore;
import com.happyfresh.models.Taxon;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProductGridAdapter
  extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
  protected Context mContext;
  protected ICartApplication mICartApplication;
  private boolean mIsAddSpecialCategory;
  OnProductClickListener mItemClickListener;
  protected List<ProductGrid> mProductGrids = new ArrayList();
  protected boolean mShowMore = true;
  protected PopularProduct mSpecialProduct;
  protected Long mStockLocationId;
  private boolean mWithHeader;
  
  public ProductGridAdapter(Context paramContext, boolean paramBoolean)
  {
    this.mContext = paramContext;
    this.mICartApplication = ((ICartApplication)paramContext.getApplicationContext());
    this.mStockLocationId = Long.valueOf(this.mICartApplication.getStockLocationId());
    this.mIsAddSpecialCategory = paramBoolean;
    this.mSpecialProduct = new PopularProduct();
    this.mSpecialProduct.products = new ArrayList();
    this.mSpecialProduct.taxon = createTaxonSpecial();
  }
  
  private void addBlankCategory()
  {
    ProductGridCategory localProductGridCategory = new ProductGridCategory();
    this.mProductGrids.add(0, localProductGridCategory);
  }
  
  private void addSpecialCategoryHeaderAndMore()
  {
    Object localObject = createTaxonSpecial();
    ProductGridMore localProductGridMore = new ProductGridMore((Taxon)localObject);
    this.mProductGrids.add(0, localProductGridMore);
    localObject = new ProductGridHeader((Taxon)localObject);
    this.mProductGrids.add(0, localObject);
  }
  
  private void addSpecialProduct(Product paramProduct)
  {
    this.mSpecialProduct.products.add(paramProduct);
  }
  
  private Taxon createTaxonSpecial()
  {
    Taxon localTaxon = new Taxon();
    localTaxon.name = this.mContext.getString(2131165588);
    localTaxon.parentId = Long.valueOf(Long.MAX_VALUE);
    localTaxon.levelOneId = Long.valueOf(Long.MAX_VALUE);
    return localTaxon;
  }
  
  public void addAll(List<PopularProduct> paramList)
  {
    addAll(paramList, true);
  }
  
  public void addAll(List<PopularProduct> paramList, boolean paramBoolean)
  {
    this.mProductGrids.clear();
    this.mSpecialProduct.products.clear();
    if (paramBoolean) {
      addBlankCategory();
    }
    this.mWithHeader = paramBoolean;
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      Object localObject = (PopularProduct)localIterator.next();
      paramList = null;
      if (1 != 0)
      {
        paramList = ((PopularProduct)localObject).taxon;
        ProductGridHeader localProductGridHeader = new ProductGridHeader(paramList);
        this.mProductGrids.add(localProductGridHeader);
      }
      if ((1 != 0) && (!((PopularProduct)localObject).products.isEmpty()))
      {
        paramList = new ProductGridMore(paramList);
        this.mProductGrids.add(paramList);
      }
      paramList = new ProductGridCategories((PopularProduct)localObject);
      this.mProductGrids.add(paramList);
      paramList = ((PopularProduct)localObject).products.iterator();
      while (paramList.hasNext())
      {
        localObject = (Product)paramList.next();
        if (((Product)localObject).hasPromo()) {
          addSpecialProduct((Product)localObject);
        }
      }
    }
    if ((this.mIsAddSpecialCategory) && (!this.mSpecialProduct.products.isEmpty()))
    {
      if (1 != 0) {
        this.mProductGrids.remove(0);
      }
      paramList = new ProductGridCategories(this.mSpecialProduct);
      this.mProductGrids.add(0, paramList);
      if (1 != 0) {
        addSpecialCategoryHeaderAndMore();
      }
      if (1 != 0) {
        addBlankCategory();
      }
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
    if (paramInt < this.mProductGrids.size()) {
      return ((ProductGrid)this.mProductGrids.get(paramInt)).getSpanSize();
    }
    return 3;
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
  
  public boolean isWithHeader()
  {
    return this.mWithHeader;
  }
  
  public void onBindViewHolder(RecyclerView.ViewHolder paramViewHolder, int paramInt)
  {
    ((BaseRecyclerViewHolder)paramViewHolder).bindViewHolder(paramInt);
  }
  
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
  {
    if (paramInt == 1003) {
      return new CategoriesViewHolder(LayoutInflater.from(this.mContext).inflate(2130903167, null));
    }
    if (paramInt == 1002) {
      return new HeaderViewHolder(LayoutInflater.from(this.mContext).inflate(2130903172, null));
    }
    if (paramInt == 2002) {
      return new ViewMoreHolder(LayoutInflater.from(this.mContext).inflate(2130903182, null));
    }
    return new BlankCategoryHolder(LayoutInflater.from(this.mContext).inflate(2130903071, null));
  }
  
  public void setOnItemClickListener(OnProductClickListener paramOnProductClickListener)
  {
    this.mItemClickListener = paramOnProductClickListener;
  }
  
  public void setShowMore(boolean paramBoolean)
  {
    this.mShowMore = paramBoolean;
  }
  
  public void setWithHeader(boolean paramBoolean)
  {
    this.mWithHeader = paramBoolean;
  }
  
  class BlankCategoryHolder
    extends BaseRecyclerViewHolder
  {
    public BlankCategoryHolder(View paramView)
    {
      super();
    }
  }
  
  class CategoriesViewHolder
    extends BaseRecyclerViewHolder
  {
    private SubProductGridAdapter mSubProductAdapter;
    private LinearLayoutManager mSubProductLayoutManager;
    @InjectView(2131558715)
    RecyclerView mSubProductRecyclerView;
    
    public CategoriesViewHolder(View paramView)
    {
      super();
      ButterKnife.inject(this, paramView);
      this.mSubProductLayoutManager = new LinearLayoutManager(ProductGridAdapter.this.mContext);
      this.mSubProductLayoutManager.setOrientation(0);
      this.mSubProductRecyclerView.setLayoutManager(this.mSubProductLayoutManager);
      this.mSubProductAdapter = new SubProductGridAdapter(ProductGridAdapter.this.mContext);
      this.mSubProductRecyclerView.setAdapter(this.mSubProductAdapter);
      this.mSubProductAdapter.setOnItemClickListener(new OnProductClickListener()
      {
        public void onHeaderClick(View paramAnonymousView, Taxon paramAnonymousTaxon) {}
        
        public void onItemClick(View paramAnonymousView, Product paramAnonymousProduct)
        {
          if (ProductGridAdapter.this.mItemClickListener != null) {
            ProductGridAdapter.this.mItemClickListener.onItemClick(paramAnonymousView, paramAnonymousProduct);
          }
        }
        
        public void onViewMoreClick(View paramAnonymousView, Taxon paramAnonymousTaxon) {}
      });
      this.mSubProductRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
      {
        public void onScrollStateChanged(RecyclerView paramAnonymousRecyclerView, int paramAnonymousInt)
        {
          super.onScrollStateChanged(paramAnonymousRecyclerView, paramAnonymousInt);
          if (paramAnonymousInt == 0)
          {
            paramAnonymousInt = ProductGridAdapter.CategoriesViewHolder.this.getAdapterPosition();
            if (paramAnonymousInt != -1) {
              break label24;
            }
          }
          label24:
          while (paramAnonymousInt >= ProductGridAdapter.this.getItemCount()) {
            return;
          }
          ((ProductGridCategories)ProductGridAdapter.this.mProductGrids.get(paramAnonymousInt)).setScrollX(ProductGridAdapter.CategoriesViewHolder.this.mSubProductRecyclerView.computeHorizontalScrollOffset());
        }
      });
    }
    
    public void bindViewHolder(int paramInt)
    {
      ProductGridCategories localProductGridCategories = (ProductGridCategories)ProductGridAdapter.this.mProductGrids.get(paramInt);
      PopularProduct localPopularProduct = localProductGridCategories.getPopularProduct();
      this.mSubProductAdapter.addAll(localPopularProduct);
      this.mSubProductAdapter.notifyDataSetChanged();
      try
      {
        paramInt = localProductGridCategories.getScrollX();
        int i = this.mSubProductRecyclerView.computeHorizontalScrollOffset();
        this.mSubProductRecyclerView.scrollBy(paramInt - i, 0);
        if (localProductGridCategories.getScrollX() != this.mSubProductRecyclerView.computeHorizontalScrollOffset())
        {
          paramInt = localProductGridCategories.getScrollX();
          i = this.mSubProductRecyclerView.computeHorizontalScrollOffset();
          this.mSubProductRecyclerView.scrollBy(paramInt - i, 0);
        }
        return;
      }
      catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
      {
        localIndexOutOfBoundsException.printStackTrace();
      }
    }
  }
  
  class HeaderViewHolder
    extends BaseRecyclerViewHolder
  {
    @InjectView(2131558947)
    ImageView categoryIcon;
    @InjectView(2131558948)
    TextView taxonName;
    
    public HeaderViewHolder(View paramView)
    {
      super();
      ButterKnife.inject(this, paramView);
    }
    
    public void bindViewHolder(int paramInt)
    {
      Taxon localTaxon = ((ProductGrid)ProductGridAdapter.this.mProductGrids.get(paramInt)).getTaxon();
      this.taxonName.setText(ProductGridAdapter.this.getTaxonDisplayName(localTaxon));
      if (ProductGridAdapter.this.mContext.getString(2131165382).equalsIgnoreCase(localTaxon.name))
      {
        Picasso.with(ProductGridAdapter.this.mContext).load(2130837767).into(this.categoryIcon);
        return;
      }
      if (ProductGridAdapter.this.mContext.getString(2131165588).equalsIgnoreCase(localTaxon.name))
      {
        Picasso.with(ProductGridAdapter.this.mContext).load(2130837790).into(this.categoryIcon);
        return;
      }
      Picasso.with(ProductGridAdapter.this.mContext).load(localTaxon.iconUrl).into(this.categoryIcon, new Callback()
      {
        public void onError()
        {
          ProductGridAdapter.HeaderViewHolder.this.categoryIcon.setVisibility(8);
        }
        
        public void onSuccess() {}
      });
    }
    
    @OnClick({2131558946})
    public void onHeaderClick(View paramView)
    {
      int i = getAdapterPosition();
      if (i == -1) {}
      Taxon localTaxon;
      do
      {
        do
        {
          return;
        } while (i >= ProductGridAdapter.this.getItemCount());
        localTaxon = ((ProductGrid)ProductGridAdapter.this.mProductGrids.get(i)).getTaxon();
      } while (ProductGridAdapter.this.mItemClickListener == null);
      ProductGridAdapter.this.mItemClickListener.onHeaderClick(paramView, localTaxon);
    }
  }
  
  class ViewMoreHolder
    extends BaseRecyclerViewHolder
  {
    public ViewMoreHolder(View paramView)
    {
      super();
      ButterKnife.inject(this, paramView);
    }
    
    @OnClick({2131559043})
    public void seeMore(View paramView)
    {
      int i = getAdapterPosition();
      if (i == -1) {}
      Taxon localTaxon;
      do
      {
        do
        {
          return;
        } while (i >= ProductGridAdapter.this.getItemCount());
        localTaxon = ((ProductGrid)ProductGridAdapter.this.mProductGrids.get(i)).getTaxon();
      } while (ProductGridAdapter.this.mItemClickListener == null);
      ProductGridAdapter.this.mItemClickListener.onViewMoreClick(paramView, localTaxon);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/adapters/ProductGridAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */