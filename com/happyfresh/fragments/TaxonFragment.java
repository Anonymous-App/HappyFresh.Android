package com.happyfresh.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.GridLayoutManager.SpanSizeLookup;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.happyfresh.activities.BaseActivity;
import com.happyfresh.activities.SortByActivity;
import com.happyfresh.activities.TaxonActivity;
import com.happyfresh.activities.ViewProductActivity;
import com.happyfresh.adapters.ProductGridAdapter;
import com.happyfresh.adapters.SubCategoryProductGridAdapter;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.common.ICartNotification;
import com.happyfresh.common.ICartNotification.Type;
import com.happyfresh.customs.CircularProgressBar;
import com.happyfresh.customs.EndlessOnScrollListener;
import com.happyfresh.customs.HidingScrollListener;
import com.happyfresh.interfaces.Listener;
import com.happyfresh.listeners.OnProductClickListener;
import com.happyfresh.loaders.ProductsLoader;
import com.happyfresh.managers.ProductManager;
import com.happyfresh.managers.ShoppingListManager;
import com.happyfresh.models.Order;
import com.happyfresh.models.PopularProduct;
import com.happyfresh.models.Product;
import com.happyfresh.models.ProductResponse;
import com.happyfresh.models.ShoppingListResponse;
import com.happyfresh.models.Taxon;
import java.util.ArrayList;
import java.util.List;

public class TaxonFragment
  extends BaseFragment
  implements LoaderManager.LoaderCallbacks<List<PopularProduct>>, CategoryPickerFragment.CategoryPickerListener
{
  private static final int PER_PAGE = 100;
  private static final String TAG = TaxonFragment.class.getSimpleName();
  @InjectView(2131558523)
  View categoryInfo;
  @InjectView(2131558524)
  TextView categoryName;
  BaseActivity mActivity;
  @InjectView(2131558772)
  View mCategoryCarat;
  @InjectView(2131558769)
  View mCategoryHeader;
  private Listener mCurrentOrderSetListener = new Listener()
  {
    public void onHappened(Object paramAnonymousObject, ICartNotification.Type paramAnonymousType, Order paramAnonymousOrder)
    {
      if (TaxonFragment.this.mTaxonProductAdapter != null) {
        TaxonFragment.this.mTaxonProductAdapter.notifyDataSetChanged();
      }
      if (TaxonFragment.this.mSubTaxonProductAdapter != null) {
        TaxonFragment.this.mSubTaxonProductAdapter.notifyDataSetChanged();
      }
      ((TaxonActivity)TaxonFragment.this.getActivity()).invalidateOptionsMenu();
    }
  };
  Taxon mCurrentTaxon;
  private EndlessOnScrollListener mEndlessOnScrollListener;
  private boolean mIsWithCategoryBar = true;
  private boolean mLastPage = false;
  @InjectView(2131558773)
  FrameLayout mLayoutCategoryList;
  private GridLayoutManager mLayoutManager;
  private OnProductClickListener mOnProductClickListener = new OnProductClickListener()
  {
    public void onHeaderClick(View paramAnonymousView, Taxon paramAnonymousTaxon)
    {
      TaxonFragment.this.openTaxonFragment(paramAnonymousTaxon);
    }
    
    public void onItemClick(View paramAnonymousView, Product paramAnonymousProduct)
    {
      if (TaxonFragment.this.getActivity() == null) {
        return;
      }
      TaxonFragment.this.openProductDetail(paramAnonymousProduct);
    }
    
    public void onViewMoreClick(View paramAnonymousView, Taxon paramAnonymousTaxon)
    {
      TaxonFragment.this.openTaxonFragment(paramAnonymousTaxon);
    }
  };
  private boolean mOpenCategoryPicker = true;
  private List<Product> mProducts = new ArrayList();
  @InjectView(2131558694)
  CircularProgressBar mProgressBar;
  @InjectView(2131558715)
  RecyclerView mRecyclerView;
  private boolean mSubCategory = false;
  private GridLayoutManager mSubCategoryLayoutManager;
  private SubCategoryProductGridAdapter mSubTaxonProductAdapter;
  private ProductGridAdapter mTaxonProductAdapter;
  @InjectView(2131558884)
  TextView sortByCriteriaText;
  @InjectView(2131558882)
  View sortByHeader;
  
  private void getMyFavoriteList()
  {
    if ((this.mProgressBar != null) && (this.mProgressBar.getVisibility() != 0)) {
      this.mProgressBar.setVisibility(0);
    }
    getApplication().getShoppingListManager().getFavoriteList(new ICartCallback(TAG)
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        super.onFailure(paramAnonymousThrowable);
        if ((TaxonFragment.this.getActivity() == null) || (!TaxonFragment.this.isAdded())) {
          return;
        }
        TaxonFragment.this.mProgressBar.setVisibility(8);
      }
      
      public void onSuccess(Object paramAnonymousObject)
      {
        paramAnonymousObject = ((ShoppingListResponse)paramAnonymousObject).products;
        if ((TaxonFragment.this.getActivity() == null) || (!TaxonFragment.this.isAdded())) {
          return;
        }
        TaxonFragment.this.mSubTaxonProductAdapter.addTaxonProductList(TaxonFragment.this.getString(2131165382), (List)paramAnonymousObject);
        TaxonFragment.this.mSubTaxonProductAdapter.sortBy(TaxonFragment.this.getApplication().getSortByCriteria());
        TaxonFragment.this.mSubTaxonProductAdapter.notifyDataSetChanged();
        TaxonFragment.this.mProgressBar.setVisibility(8);
      }
    });
  }
  
  private void getSpecialProduct(final int paramInt)
  {
    if ((this.mProgressBar != null) && (this.mProgressBar.getVisibility() != 0)) {
      this.mProgressBar.setVisibility(0);
    }
    int i = getApplication().getSortByCriteria();
    getApplication().getProductManager().getSpecialProducts(Integer.valueOf(paramInt), Integer.valueOf(100), i, new ICartCallback(TAG)
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        super.onFailure(paramAnonymousThrowable);
        if ((TaxonFragment.this.getActivity() == null) || (!TaxonFragment.this.isAdded())) {
          return;
        }
        TaxonFragment.this.mProgressBar.setVisibility(8);
      }
      
      public void onSuccess(Object paramAnonymousObject)
      {
        paramAnonymousObject = (ProductResponse)paramAnonymousObject;
        List localList = ((ProductResponse)paramAnonymousObject).products;
        if (paramInt == 1) {
          TaxonFragment.this.mSubTaxonProductAdapter.clearAll(TaxonFragment.this.mIsWithCategoryBar);
        }
        if ((TaxonFragment.this.getActivity() == null) || (!TaxonFragment.this.isAdded())) {
          return;
        }
        TaxonFragment.this.mSubTaxonProductAdapter.addTaxonProductList(TaxonFragment.this.mContext.getString(2131165588), localList);
        TaxonFragment.this.mSubTaxonProductAdapter.notifyDataSetChanged();
        if (((ProductResponse)paramAnonymousObject).pages.intValue() == paramInt) {
          TaxonFragment.access$402(TaxonFragment.this, true);
        }
        for (;;)
        {
          TaxonFragment.this.mProgressBar.setVisibility(8);
          return;
          TaxonFragment.access$402(TaxonFragment.this, false);
        }
      }
    });
  }
  
  private String getTaxonDisplayName(Taxon paramTaxon)
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
  
  private void hideCategoryHeader()
  {
    this.mCategoryHeader.animate().setDuration(500L).translationY(-this.mCategoryHeader.getHeight()).setInterpolator(new AccelerateInterpolator(2.0F));
  }
  
  private void initCategoryView()
  {
    this.mSubCategory = false;
    this.mLayoutManager = new GridLayoutManager(getActivity(), 3);
    this.mRecyclerView.setLayoutManager(this.mLayoutManager);
    this.mTaxonProductAdapter = new ProductGridAdapter(getActivity(), false);
    this.mTaxonProductAdapter.setWithHeader(this.mIsWithCategoryBar);
    this.mRecyclerView.setAdapter(this.mTaxonProductAdapter);
    this.mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup()
    {
      public int getSpanSize(int paramAnonymousInt)
      {
        return TaxonFragment.this.mTaxonProductAdapter.getSpanSize(paramAnonymousInt);
      }
    });
    this.mTaxonProductAdapter.setOnItemClickListener(this.mOnProductClickListener);
    this.sortByHeader.setVisibility(8);
  }
  
  private void initSubCategoryView()
  {
    this.mSubCategory = true;
    this.mSubCategoryLayoutManager = new GridLayoutManager(getActivity(), 2);
    this.mRecyclerView.setLayoutManager(this.mSubCategoryLayoutManager);
    this.mSubTaxonProductAdapter = new SubCategoryProductGridAdapter(getActivity(), this.mOnProductClickListener);
    this.mSubTaxonProductAdapter.setWithHeader(this.mIsWithCategoryBar);
    this.mRecyclerView.setAdapter(this.mSubTaxonProductAdapter);
    this.mEndlessOnScrollListener = new EndlessOnScrollListener(this.mSubCategoryLayoutManager, 100)
    {
      public void onLoadMore(int paramAnonymousInt)
      {
        if (!TaxonFragment.this.mLastPage)
        {
          if (!TaxonFragment.this.isSpecialsCategory()) {
            break label34;
          }
          TaxonFragment.this.getSpecialProduct(paramAnonymousInt);
        }
        for (;;)
        {
          super.onLoadMore(paramAnonymousInt);
          return;
          label34:
          if (!TaxonFragment.this.isFavouritesCategory()) {
            TaxonFragment.this.getTaxonProducts(paramAnonymousInt);
          }
        }
      }
    };
    this.mRecyclerView.addOnScrollListener(this.mEndlessOnScrollListener);
    this.mSubCategoryLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup()
    {
      public int getSpanSize(int paramAnonymousInt)
      {
        return TaxonFragment.this.mSubTaxonProductAdapter.getSpanSize(paramAnonymousInt);
      }
    });
    this.sortByHeader.setVisibility(0);
  }
  
  private boolean isFavouritesCategory()
  {
    return (this.mCurrentTaxon != null) && (getString(2131165382).equalsIgnoreCase(this.mCurrentTaxon.name));
  }
  
  private boolean isSpecialsCategory()
  {
    return (this.mCurrentTaxon != null) && (getString(2131165588).equalsIgnoreCase(this.mCurrentTaxon.name));
  }
  
  private void reInitSubTaxonView()
  {
    if (this.mSubTaxonProductAdapter == null) {
      initSubCategoryView();
    }
    this.sortByHeader.setVisibility(0);
    this.mSubCategory = true;
    this.mRecyclerView.setLayoutManager(this.mSubCategoryLayoutManager);
    this.mRecyclerView.setAdapter(this.mSubTaxonProductAdapter);
    this.mSubTaxonProductAdapter.clearAll(this.mIsWithCategoryBar);
    this.mSubTaxonProductAdapter.notifyDataSetChanged();
  }
  
  private void reInitTaxonView()
  {
    if (this.mTaxonProductAdapter == null) {
      initCategoryView();
    }
    this.sortByHeader.setVisibility(8);
    this.mSubCategory = false;
    this.mRecyclerView.setLayoutManager(this.mLayoutManager);
    this.mRecyclerView.setAdapter(this.mTaxonProductAdapter);
    this.mTaxonProductAdapter.clearAll(this.mIsWithCategoryBar);
    this.mTaxonProductAdapter.notifyDataSetChanged();
  }
  
  private void setSortByCriteriaText()
  {
    switch (getApplication().getSortByCriteria())
    {
    default: 
      return;
    case 0: 
      this.sortByCriteriaText.setText(getResources().getString(2131165513));
      return;
    case 1: 
      this.sortByCriteriaText.setText(getResources().getString(2131165453));
      return;
    case 2: 
      this.sortByCriteriaText.setText(getResources().getString(2131165406));
      return;
    case 3: 
      this.sortByCriteriaText.setText(getResources().getString(2131165251));
      return;
    }
    this.sortByCriteriaText.setText(getResources().getString(2131165648));
  }
  
  private void setTitle(Taxon paramTaxon)
  {
    if ((getActivity() instanceof BaseActivity))
    {
      ActionBar localActionBar = ((BaseActivity)getActivity()).getSupportActionBar();
      if (localActionBar != null) {
        localActionBar.setTitle(getTaxonDisplayName(paramTaxon));
      }
      this.categoryName.setText(getTaxonDisplayName(this.mCurrentTaxon));
    }
  }
  
  private void showCategoryHeader()
  {
    this.mCategoryHeader.animate().setDuration(500L).translationY(0.0F).setInterpolator(new DecelerateInterpolator(2.0F));
  }
  
  void fetchProducts()
  {
    this.mProgressBar.setVisibility(0);
    getTaxonProducts(1);
  }
  
  protected String getScreenName()
  {
    return "Subcategory";
  }
  
  public Taxon getTaxon()
  {
    return this.mCurrentTaxon;
  }
  
  public void getTaxonProducts(final int paramInt)
  {
    int i = -1;
    if (this.mSubCategory) {
      i = getApplication().getSortByCriteria();
    }
    long l = getApplication().getStockLocationId();
    getApplication().getProductManager().getProductsByTaxon(Long.valueOf(l), this.mCurrentTaxon, Integer.valueOf(paramInt), Integer.valueOf(100), i, new ICartCallback(TAG)
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        super.onFailure(paramAnonymousThrowable);
        if ((TaxonFragment.this.getActivity() == null) || (!TaxonFragment.this.isAdded())) {
          return;
        }
        TaxonFragment.this.mProgressBar.setVisibility(8);
        TaxonFragment.access$402(TaxonFragment.this, false);
      }
      
      public void onSuccess(ProductResponse paramAnonymousProductResponse)
      {
        List localList1 = paramAnonymousProductResponse.products;
        if (paramInt == 1) {
          synchronized (TaxonFragment.this.mProducts)
          {
            TaxonFragment.this.mProducts.clear();
          }
        }
        synchronized (TaxonFragment.this.mProducts)
        {
          TaxonFragment.this.mProducts.addAll(localList1);
          if (TaxonFragment.this.isAdded()) {
            TaxonFragment.this.getLoaderManager().restartLoader(0, null, TaxonFragment.this);
          }
          if (paramAnonymousProductResponse.pages.intValue() == paramInt)
          {
            TaxonFragment.access$402(TaxonFragment.this, true);
            return;
            paramAnonymousProductResponse = finally;
            throw paramAnonymousProductResponse;
          }
        }
        TaxonFragment.access$402(TaxonFragment.this, false);
      }
    });
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    if (this.mCurrentTaxon != null)
    {
      if (this.mCurrentTaxon.parentId.longValue() == 1L) {
        initCategoryView();
      }
    }
    else
    {
      if (!isFavouritesCategory()) {
        break label144;
      }
      if (this.mSubTaxonProductAdapter == null) {
        initSubCategoryView();
      }
      this.mSubTaxonProductAdapter.clearAll(this.mIsWithCategoryBar);
      getMyFavoriteList();
      label64:
      if (!this.mIsWithCategoryBar) {
        break label188;
      }
      this.mCategoryHeader.setVisibility(0);
      this.mRecyclerView.addOnScrollListener(new HidingScrollListener()
      {
        public void onHide()
        {
          TaxonFragment.this.hideCategoryHeader();
        }
        
        public void onShow()
        {
          TaxonFragment.this.showCategoryHeader();
        }
      });
    }
    for (;;)
    {
      this.categoryInfo.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          TaxonFragment.this.openCategoryPicker();
        }
      });
      ((CategoryPickerFragment)getChildFragmentManager().findFragmentById(2131558774)).setListener(this);
      return;
      if (this.mCurrentTaxon.levelOneId == null) {
        break;
      }
      initSubCategoryView();
      break;
      label144:
      if (isSpecialsCategory())
      {
        if (this.mSubTaxonProductAdapter == null) {
          initSubCategoryView();
        }
        this.mSubTaxonProductAdapter.clearAll(this.mIsWithCategoryBar);
        getSpecialProduct(1);
        break label64;
      }
      fetchProducts();
      break label64;
      label188:
      this.mCategoryHeader.setVisibility(8);
    }
  }
  
  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    this.mActivity = ((BaseActivity)paramActivity);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (getArguments() != null)
    {
      this.mCurrentTaxon = ((Taxon)getArguments().get("ICartConstant.KEYS.TAXON"));
      this.mIsWithCategoryBar = getArguments().getBoolean("TaxonActivity.KEYS.IS_WITH_CAT_BAR");
    }
    getApplication().getICartNotification().addCurrentOrderSetListener(this.mCurrentOrderSetListener);
    getApplication().setDefaultSortByCriteria();
  }
  
  public Loader<List<PopularProduct>> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    if (this.mSubCategory) {
      return new ProductsLoader(getActivity(), this.mProducts, this.mCurrentTaxon, false, false);
    }
    return new ProductsLoader(getActivity(), this.mProducts, this.mCurrentTaxon);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903156, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    this.mProgressBar.setVisibility(4);
    return paramLayoutInflater;
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    ((BaseActivity)getActivity()).disableUpNavigation();
  }
  
  public void onDestroyView()
  {
    super.onDestroyView();
    ButterKnife.reset(this);
    getApplication().getICartNotification().removeCurrentOrderSetListener(this.mCurrentOrderSetListener);
  }
  
  public void onFavouritesClick(Taxon paramTaxon)
  {
    this.mCurrentTaxon = paramTaxon;
    reInitSubTaxonView();
    setTitle(this.mCurrentTaxon);
    getMyFavoriteList();
    openCategoryPicker();
  }
  
  public void onLoadFinished(Loader<List<PopularProduct>> paramLoader, List<PopularProduct> paramList)
  {
    if (!this.mSubCategory)
    {
      this.mTaxonProductAdapter.addAll(paramList, this.mIsWithCategoryBar);
      this.mTaxonProductAdapter.notifyDataSetChanged();
    }
    for (;;)
    {
      this.mProgressBar.setVisibility(8);
      if (!this.mIsWithCategoryBar) {
        break;
      }
      this.mCategoryHeader.setVisibility(0);
      return;
      this.mSubTaxonProductAdapter.addAll(paramList, this.mIsWithCategoryBar);
      this.mSubTaxonProductAdapter.notifyDataSetChanged();
    }
    this.mCategoryHeader.setVisibility(8);
  }
  
  public void onLoaderReset(Loader<List<PopularProduct>> paramLoader) {}
  
  public void onPrepareOptionsMenu(Menu paramMenu)
  {
    super.onPrepareOptionsMenu(paramMenu);
    paramMenu.clear();
  }
  
  public void onResume()
  {
    super.onResume();
    setTitle(this.mCurrentTaxon);
    Object localObject = this.mActivity.getSupportActionBar();
    if (localObject != null)
    {
      ((ActionBar)localObject).setDefaultDisplayHomeAsUpEnabled(true);
      ((ActionBar)localObject).setDisplayHomeAsUpEnabled(true);
    }
    localObject = this.sortByCriteriaText.getText().toString();
    setSortByCriteriaText();
    if (!((String)localObject).equalsIgnoreCase(this.sortByCriteriaText.getText().toString())) {}
    for (int i = 1;; i = 0)
    {
      if ((i != 0) && (this.mSubTaxonProductAdapter != null))
      {
        if (!isFavouritesCategory()) {
          break;
        }
        this.mSubTaxonProductAdapter.sortBy(getApplication().getSortByCriteria());
        this.mSubTaxonProductAdapter.notifyDataSetChanged();
      }
      return;
    }
    if (isSpecialsCategory())
    {
      reInitSubTaxonView();
      getSpecialProduct(1);
      return;
    }
    reInitSubTaxonView();
    fetchProducts();
  }
  
  @OnClick({2131558882})
  void onSortByHeaderClick()
  {
    startActivity(new Intent(getActivity(), SortByActivity.class));
  }
  
  public void onSpecialClick(Taxon paramTaxon)
  {
    this.mCurrentTaxon = paramTaxon;
    reInitSubTaxonView();
    setTitle(this.mCurrentTaxon);
    getSpecialProduct(1);
    openCategoryPicker();
  }
  
  public void onSubCategoryClick(Taxon paramTaxon1, Taxon paramTaxon2)
  {
    this.mCurrentTaxon = paramTaxon2;
    if (paramTaxon2.name.contains(paramTaxon1.name)) {
      reInitTaxonView();
    }
    for (;;)
    {
      setTitle(this.mCurrentTaxon);
      fetchProducts();
      openCategoryPicker();
      return;
      reInitSubTaxonView();
    }
  }
  
  public void openCategoryPicker()
  {
    if (!this.mOpenCategoryPicker)
    {
      this.mOpenCategoryPicker = true;
      return;
    }
    if (this.mLayoutCategoryList.getVisibility() == 8)
    {
      this.mLayoutCategoryList.setVisibility(0);
      this.mCategoryCarat.setRotation(270.0F);
      return;
    }
    this.mLayoutCategoryList.setVisibility(8);
    this.mCategoryCarat.setRotation(90.0F);
  }
  
  void openProductDetail(Product paramProduct)
  {
    Intent localIntent = new Intent(getActivity(), ViewProductActivity.class);
    localIntent.putExtra("ICartConstant.KEYS.EXTRAS.PRODUCT", paramProduct);
    startActivity(localIntent);
  }
  
  void openTaxonFragment(Taxon paramTaxon)
  {
    Intent localIntent = new Intent(getActivity(), TaxonActivity.class);
    localIntent.putExtra("ICartConstant.KEYS.TAXON", paramTaxon);
    startActivity(localIntent);
  }
  
  public void setTaxon(Taxon paramTaxon)
  {
    this.mCurrentTaxon = paramTaxon;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/TaxonFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */