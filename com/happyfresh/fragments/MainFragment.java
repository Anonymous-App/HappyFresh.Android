package com.happyfresh.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.GridLayoutManager.SpanSizeLookup;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.activeandroid.ActiveAndroid;
import com.afollestad.materialdialogs.MaterialDialog;
import com.happyfresh.activities.ChooseLocationActivity;
import com.happyfresh.activities.ChooseStoreActivity;
import com.happyfresh.activities.MainActivity;
import com.happyfresh.activities.TaxonActivity;
import com.happyfresh.activities.ViewProductActivity;
import com.happyfresh.adapters.ProductGridAdapter;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.callbacks.ICartDialogCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.common.ICartNotification;
import com.happyfresh.common.ICartNotification.Type;
import com.happyfresh.common.ICartRestError;
import com.happyfresh.customs.CircularProgressBar;
import com.happyfresh.customs.HidingScrollListener;
import com.happyfresh.interfaces.Listener;
import com.happyfresh.listeners.OnProductClickListener;
import com.happyfresh.loaders.ProductsLoader;
import com.happyfresh.managers.LocationManager;
import com.happyfresh.managers.OrderManager;
import com.happyfresh.managers.ProductManager;
import com.happyfresh.managers.SharedPreferencesManager;
import com.happyfresh.managers.ShoppingListManager;
import com.happyfresh.models.Coordinate;
import com.happyfresh.models.Order;
import com.happyfresh.models.PopularProduct;
import com.happyfresh.models.Product;
import com.happyfresh.models.ProductResponse;
import com.happyfresh.models.StockLocation;
import com.happyfresh.models.StockLocationResponse;
import com.happyfresh.models.Taxon;
import com.happyfresh.models.TaxonResponse;
import com.happyfresh.models.Taxonomy;
import com.happyfresh.utils.DialogColor;
import com.happyfresh.utils.DialogUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainFragment
  extends BaseFragment
  implements LoaderManager.LoaderCallbacks<List<PopularProduct>>, CategoryPickerFragment.CategoryPickerListener
{
  private static final String TAG = MainFragment.class.getSimpleName();
  @InjectView(2131558523)
  View categoryInfo;
  @InjectView(2131558524)
  TextView categoryName;
  @InjectView(2131558772)
  View mCategoryCarat;
  @InjectView(2131558769)
  RelativeLayout mCategoryHeader;
  private Context mContext;
  private String mCurrentCategory;
  private Listener mCurrentOrderSetListener = new Listener()
  {
    public void onHappened(Object paramAnonymousObject, ICartNotification.Type paramAnonymousType, Order paramAnonymousOrder)
    {
      MainFragment.this.mPopularProductAdapter.notifyDataSetChanged();
      ((MainActivity)MainFragment.this.getActivity()).invalidateOptionsMenu();
    }
  };
  Taxon mCurrentTaxon;
  @InjectView(2131558773)
  FrameLayout mLayoutCategoryList;
  private ProductGridAdapter mPopularProductAdapter;
  private GridLayoutManager mProductLayoutManager;
  private List<Product> mProducts = new ArrayList();
  @InjectView(2131558694)
  CircularProgressBar mProgressBar;
  @InjectView(2131558715)
  RecyclerView mRecyclerView;
  private CategoryPickerFragment pickerFragment;
  @InjectView(2131558521)
  TextView shoppingLocation;
  @InjectView(2131558520)
  View storeInfoArea;
  @InjectView(2131558522)
  TextView storeLocation;
  
  private void displayCategoryProducts()
  {
    boolean bool;
    Object localObject;
    String str;
    if ((this.mCurrentTaxon == null) || (this.mCurrentTaxon.countChildren() > 0))
    {
      bool = true;
      this.mPopularProductAdapter.setShowMore(bool);
      localObject = getApplication().getSharedPreferencesManager();
      str = ((SharedPreferencesManager)localObject).getStockLocationName();
      localObject = ((SharedPreferencesManager)localObject).getSubDistrictName();
      this.categoryName.setText(getTaxonDisplayName());
      if (localObject != null) {
        break label90;
      }
      this.shoppingLocation.setText(this.mContext.getString(2131165574));
    }
    for (;;)
    {
      this.storeLocation.setText(str);
      return;
      bool = false;
      break;
      label90:
      this.shoppingLocation.setText(this.mContext.getString(2131165573, new Object[] { localObject }));
    }
  }
  
  private void fetchCategoryTaxon(Long paramLong1, Long paramLong2)
  {
    getApplication().getProductManager().getSubCategories(paramLong1, paramLong2, Integer.valueOf(1), Integer.valueOf(1000), new ICartCallback(TAG)
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        super.onFailure(paramAnonymousThrowable);
        if (MainFragment.this.getActivity() == null) {
          return;
        }
        MainFragment.this.mProgressBar.setVisibility(8);
      }
      
      public void onSuccess(TaxonResponse paramAnonymousTaxonResponse)
      {
        Taxon.deleteAllSubCategories();
        MainFragment.this.saveSubCategories(paramAnonymousTaxonResponse.taxons);
        if (MainFragment.this.getActivity() == null) {
          return;
        }
        MainFragment.this.getPopularProduct();
        MainFragment.this.pickerFragment.initDataSubCat();
      }
    });
  }
  
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
        if (MainFragment.this.getActivity() == null) {
          return;
        }
        MainFragment.this.mProgressBar.setVisibility(8);
      }
      
      public void onSuccess(Object paramAnonymousObject)
      {
        if (MainFragment.this.getActivity() == null) {
          return;
        }
        MainFragment.this.mProgressBar.setVisibility(8);
        MainFragment.this.mPopularProductAdapter.notifyDataSetChanged();
      }
    });
  }
  
  private void getNearestStockLocations(Long paramLong)
  {
    Double localDouble2 = null;
    final SharedPreferencesManager localSharedPreferencesManager = getApplication().getSharedPreferencesManager();
    Object localObject = localSharedPreferencesManager.getCoordinate();
    Double localDouble1;
    if (localObject == null)
    {
      localDouble1 = null;
      if (localObject != null) {
        break label98;
      }
    }
    for (;;)
    {
      localObject = getApplication().getSharedPreferencesManager().getZipCode();
      String str = localSharedPreferencesManager.getSubDistrictName();
      Long localLong = localSharedPreferencesManager.getSubDistrictId();
      getApplication().getLocationManager().getNearestStockLocations(paramLong, localDouble1, localDouble2, (String)localObject, str, localLong, new ICartCallback(TAG)
      {
        public void onFailure(Throwable paramAnonymousThrowable)
        {
          super.onFailure(paramAnonymousThrowable);
          if (MainFragment.this.getActivity() == null) {
            return;
          }
          ICartRestError.showMessage(MainFragment.this.mContext, paramAnonymousThrowable);
        }
        
        public void onSuccess(StockLocationResponse paramAnonymousStockLocationResponse)
        {
          if ((paramAnonymousStockLocationResponse == null) || (MainFragment.this.getActivity() == null)) {
            return;
          }
          if (!paramAnonymousStockLocationResponse.stockLocations.isEmpty())
          {
            final long l = ((StockLocation)paramAnonymousStockLocationResponse.stockLocations.get(0)).remoteId;
            if (localSharedPreferencesManager.getStockLocationId() == l)
            {
              MainFragment.this.viewProduct();
              return;
            }
            if (!MainFragment.this.getApplication().currentOrder.isEmptyCart())
            {
              MainFragment.this.showChangeStockLocationConfirmationDialog(new ICartCallback(MainFragment.TAG)
              {
                public void onSuccess(Object paramAnonymous2Object)
                {
                  MainFragment.this.saveStoreAndLoadProduct(l);
                }
              });
              return;
            }
            MainFragment.this.saveStoreAndLoadProduct(l);
            return;
          }
          DialogUtils.showDialog(MainFragment.this.getActivity(), MainFragment.this.getString(2131165621), MainFragment.this.getString(2131165647), MainFragment.this.getString(2131165478), new ICartDialogCallback()
          {
            public void onCancel()
            {
              MainFragment.this.viewProduct();
            }
            
            public void onNegative(MaterialDialog paramAnonymous2MaterialDialog) {}
            
            public void onNeutral(MaterialDialog paramAnonymous2MaterialDialog) {}
            
            public void onPositive(MaterialDialog paramAnonymous2MaterialDialog)
            {
              if (!MainFragment.this.getApplication().currentOrder.isEmptyCart())
              {
                MainFragment.this.showChangeStockLocationConfirmationDialog(new ICartCallback(MainFragment.TAG)
                {
                  public void onFailure(Throwable paramAnonymous3Throwable)
                  {
                    MainFragment.this.viewProduct();
                  }
                  
                  public void onSuccess(Object paramAnonymous3Object)
                  {
                    MainFragment.this.goToChooseLocation();
                  }
                });
                return;
              }
              MainFragment.this.goToChooseLocation();
            }
          });
        }
      });
      return;
      localDouble1 = ((Coordinate)localObject).latitude;
      break;
      label98:
      localDouble2 = ((Coordinate)localObject).longitude;
    }
  }
  
  private String getTaxonDisplayName()
  {
    String str2 = this.mCurrentCategory;
    String str1 = str2;
    if (str2.contains("#{ALL}"))
    {
      str1 = str2.replace("#{ALL}", "");
      str1 = this.mContext.getString(2131165279, new Object[] { str1 });
    }
    return str1;
  }
  
  private void goToChooseLocation()
  {
    Intent localIntent = new Intent(getActivity(), ChooseLocationActivity.class);
    localIntent.setFlags(268468224);
    startActivity(localIntent);
  }
  
  private void hideCategoryHeader()
  {
    this.mCategoryHeader.animate().setDuration(500L).translationY(-this.mCategoryHeader.getHeight()).setInterpolator(new AccelerateInterpolator(2.0F));
  }
  
  private void saveStoreAndLoadProduct(long paramLong)
  {
    getApplication().getLocationManager().getSingleStockLocation(paramLong, new ICartCallback(TAG)
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        super.onFailure(paramAnonymousThrowable);
        MainFragment.this.mProgressBar.setVisibility(8);
        MainFragment.this.viewProduct();
      }
      
      public void onSuccess(StockLocation paramAnonymousStockLocation)
      {
        if (paramAnonymousStockLocation != null)
        {
          ICartApplication localICartApplication = MainFragment.this.getApplication();
          SharedPreferencesManager localSharedPreferencesManager = localICartApplication.getSharedPreferencesManager();
          localICartApplication.sendEvent("ui_action", "Changed Store", paramAnonymousStockLocation.name);
          MainFragment.this.viewProduct();
          localSharedPreferencesManager.saveStockLocation(paramAnonymousStockLocation);
          localICartApplication.outOfStockItems.clear();
          localICartApplication.clearOrderIdAddedToCartList();
          localICartApplication.currentSubDistrict = null;
          localSharedPreferencesManager.deleteSubDistrict();
          if (!TextUtils.isEmpty(paramAnonymousStockLocation.address1)) {
            localSharedPreferencesManager.saveSubDistrictName(paramAnonymousStockLocation.address1);
          }
          localSharedPreferencesManager.deleteCoordinate();
          if (paramAnonymousStockLocation.location != null) {
            localSharedPreferencesManager.saveCoordinate(new Coordinate(paramAnonymousStockLocation.location.latitude, paramAnonymousStockLocation.location.longitude));
          }
          localSharedPreferencesManager.deleteZipCode();
          localSharedPreferencesManager.saveZipCode(paramAnonymousStockLocation.zipcode);
        }
      }
    });
  }
  
  private void saveSubCategories(final List<Taxon> paramList)
  {
    new Thread(new Runnable()
    {
      public void run()
      {
        ActiveAndroid.beginTransaction();
        Iterator localIterator1 = paramList.iterator();
        while (localIterator1.hasNext())
        {
          Taxon localTaxon1 = (Taxon)localIterator1.next();
          localTaxon1.name = ("#{ALL}" + localTaxon1.name);
          localTaxon1.parentId = Long.valueOf(localTaxon1.remoteId);
          localTaxon1.levelOneId = null;
          localTaxon1.remoteId = (-localTaxon1.remoteId);
          localTaxon1.save();
          Iterator localIterator2 = localTaxon1.taxons.iterator();
          while (localIterator2.hasNext())
          {
            Taxon localTaxon2 = (Taxon)localIterator2.next();
            localTaxon2.levelOneId = localTaxon1.parentId;
            localTaxon2.save();
          }
        }
        ActiveAndroid.setTransactionSuccessful();
        ActiveAndroid.endTransaction();
      }
    }).start();
  }
  
  private void showCategoryHeader()
  {
    this.mCategoryHeader.animate().setDuration(500L).translationY(0.0F).setInterpolator(new DecelerateInterpolator(2.0F));
  }
  
  private void viewProduct()
  {
    if (this.mProgressBar != null) {
      this.mProgressBar.setVisibility(0);
    }
    if (this.mCurrentTaxon == null)
    {
      Taxonomy localTaxonomy = Taxonomy.getCategory();
      long l2 = getApplication().getStockLocationId();
      if (localTaxonomy != null) {}
      for (long l1 = localTaxonomy.remoteId;; l1 = new Long(1L).longValue())
      {
        fetchCategoryTaxon(Long.valueOf(l1), Long.valueOf(l2));
        return;
      }
    }
    getTaxonProducts(this.mCurrentTaxon);
  }
  
  public void getPopularProduct()
  {
    if ((this.mProgressBar != null) && (this.mProgressBar.getVisibility() != 0)) {
      this.mProgressBar.setVisibility(0);
    }
    this.mCurrentCategory = this.mContext.getString(2131165512);
    this.mCurrentTaxon = null;
    if (this.mPopularProductAdapter.getItemCount() > 0)
    {
      this.mPopularProductAdapter.clearAll();
      displayCategoryProducts();
    }
    long l = getApplication().getStockLocationId();
    getApplication().getProductManager().getPopularProductsByLocation(Long.valueOf(l), null, null, new ICartCallback(TAG)
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        super.onFailure(paramAnonymousThrowable);
        if (MainFragment.this.getActivity() == null) {}
        while (MainFragment.this.mProgressBar == null) {
          return;
        }
        MainFragment.this.mProgressBar.setVisibility(8);
      }
      
      public void onSuccess(ProductResponse paramAnonymousProductResponse)
      {
        paramAnonymousProductResponse = paramAnonymousProductResponse.products;
        MainFragment.access$602(MainFragment.this, paramAnonymousProductResponse);
        if (MainFragment.this.isAdded())
        {
          if (MainFragment.this.getLoaderManager().getLoader(0) == null) {
            MainFragment.this.getLoaderManager().initLoader(0, null, MainFragment.this);
          }
        }
        else {
          return;
        }
        MainFragment.this.getLoaderManager().restartLoader(0, null, MainFragment.this);
      }
    });
  }
  
  protected String getScreenName()
  {
    return null;
  }
  
  public void getTaxonProducts(Taxon paramTaxon)
  {
    this.mProgressBar.setVisibility(0);
    this.mCurrentCategory = paramTaxon.name;
    if (paramTaxon.remoteId < 0L) {}
    for (this.mCurrentTaxon = Taxon.findByParent(paramTaxon.parentId);; this.mCurrentTaxon = paramTaxon)
    {
      this.mPopularProductAdapter.clearAll();
      displayCategoryProducts();
      long l = getApplication().getStockLocationId();
      getApplication().getProductManager().getProductsByTaxon(Long.valueOf(l), this.mCurrentTaxon, null, null, new ICartCallback(TAG)
      {
        public void onFailure(Throwable paramAnonymousThrowable)
        {
          super.onFailure(paramAnonymousThrowable);
          if (MainFragment.this.getActivity() == null) {
            return;
          }
          MainFragment.this.mProgressBar.setVisibility(8);
          MainFragment.access$602(MainFragment.this, new ArrayList());
        }
        
        public void onSuccess(ProductResponse paramAnonymousProductResponse)
        {
          paramAnonymousProductResponse = paramAnonymousProductResponse.products;
          MainFragment.access$602(MainFragment.this, paramAnonymousProductResponse);
          if (MainFragment.this.isAdded()) {
            MainFragment.this.getLoaderManager().restartLoader(0, null, MainFragment.this);
          }
        }
      });
      return;
    }
  }
  
  public boolean isCategoryPickerOpen()
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (this.mLayoutCategoryList != null)
    {
      bool1 = bool2;
      if (this.mLayoutCategoryList.getVisibility() == 0) {
        bool1 = true;
      }
    }
    return bool1;
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.mProductLayoutManager = new GridLayoutManager(getActivity(), 3);
    this.mRecyclerView.setLayoutManager(this.mProductLayoutManager);
    this.mPopularProductAdapter = new ProductGridAdapter(getActivity(), true);
    this.mRecyclerView.setAdapter(this.mPopularProductAdapter);
    this.mProductLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup()
    {
      public int getSpanSize(int paramAnonymousInt)
      {
        return MainFragment.this.mPopularProductAdapter.getSpanSize(paramAnonymousInt);
      }
    });
    this.mPopularProductAdapter.setOnItemClickListener(new OnProductClickListener()
    {
      public void onHeaderClick(View paramAnonymousView, Taxon paramAnonymousTaxon)
      {
        MainFragment.this.openTaxonFragment(paramAnonymousTaxon);
      }
      
      public void onItemClick(View paramAnonymousView, Product paramAnonymousProduct)
      {
        MainFragment.this.openProductDetail(paramAnonymousProduct);
      }
      
      public void onViewMoreClick(View paramAnonymousView, Taxon paramAnonymousTaxon)
      {
        MainFragment.this.openTaxonFragment(paramAnonymousTaxon);
      }
    });
    this.mRecyclerView.addOnScrollListener(new HidingScrollListener()
    {
      public void onHide()
      {
        MainFragment.this.hideCategoryHeader();
      }
      
      public void onShow()
      {
        MainFragment.this.showCategoryHeader();
      }
    });
    this.mProgressBar.setVisibility(8);
    if (getArguments() != null)
    {
      paramBundle = getArguments().getString("ICartConstant.EXTRAS.DEEP_LINKS.TYPE");
      String str1 = getArguments().getString("ICartConstant.EXTRAS.DEEP_LINKS.STOCK_LOCATION_ID");
      String str2 = getArguments().getString("ICartConstant.EXTRAS.DEEP_LINKS.SUPPLIER_ID");
      if ("cart".equalsIgnoreCase(paramBundle))
      {
        viewProduct();
        ((MainActivity)getActivity()).openCart();
        return;
      }
      if ((str1 != null) && ("popular".equalsIgnoreCase(paramBundle)))
      {
        final long l = Long.parseLong(str1);
        if (getApplication().getStockLocationId() == l)
        {
          viewProduct();
          return;
        }
        if (!getApplication().currentOrder.isEmptyCart())
        {
          showChangeStockLocationConfirmationDialog(new ICartCallback(TAG)
          {
            public void onSuccess(Object paramAnonymousObject)
            {
              MainFragment.this.saveStoreAndLoadProduct(l);
            }
          });
          return;
        }
        saveStoreAndLoadProduct(l);
        return;
      }
      if ((str2 != null) && ("popular".equalsIgnoreCase(paramBundle)))
      {
        getNearestStockLocations(Long.valueOf(Long.parseLong(str2)));
        return;
      }
      viewProduct();
      return;
    }
    viewProduct();
  }
  
  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    this.mContext = paramActivity.getApplicationContext();
  }
  
  public boolean onBackPressed()
  {
    boolean bool = false;
    if (isCategoryPickerOpen())
    {
      openCategoryPicker(false);
      bool = true;
    }
    return bool;
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    getApplication().getICartNotification().addCurrentOrderSetListener(this.mCurrentOrderSetListener);
  }
  
  public Loader<List<PopularProduct>> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    return new ProductsLoader(getActivity(), this.mProducts, this.mCurrentTaxon);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903136, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    this.pickerFragment = ((CategoryPickerFragment)getChildFragmentManager().findFragmentById(2131558774));
    this.pickerFragment.setListener(this);
    return paramLayoutInflater;
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    getApplication().getICartNotification().removeCurrentOrderSetListener(this.mCurrentOrderSetListener);
  }
  
  public void onDestroyView()
  {
    super.onDestroyView();
    ButterKnife.reset(this);
  }
  
  public void onFavouritesClick(Taxon paramTaxon)
  {
    Intent localIntent = new Intent(getActivity(), TaxonActivity.class);
    localIntent.putExtra("ICartConstant.KEYS.TAXON", paramTaxon);
    localIntent.putExtra("TaxonActivity.KEYS.IS_WITH_CAT_BAR", true);
    startActivity(localIntent);
  }
  
  public void onLoadFinished(Loader<List<PopularProduct>> paramLoader, List<PopularProduct> paramList)
  {
    displayCategoryProducts();
    this.storeInfoArea.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        MainFragment.this.openLocationChooser();
      }
    });
    this.categoryInfo.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        MainFragment.this.openCategoryPicker(false);
      }
    });
    this.mPopularProductAdapter.addAll(paramList);
    getMyFavoriteList();
    if (this.mPopularProductAdapter.isWithHeader())
    {
      this.mCategoryHeader.setVisibility(0);
      return;
    }
    this.mCategoryHeader.setVisibility(8);
  }
  
  public void onLoaderReset(Loader<List<PopularProduct>> paramLoader) {}
  
  public void onSpecialClick(Taxon paramTaxon)
  {
    Intent localIntent = new Intent(getActivity(), TaxonActivity.class);
    localIntent.putExtra("ICartConstant.KEYS.TAXON", paramTaxon);
    localIntent.putExtra("TaxonActivity.KEYS.IS_WITH_CAT_BAR", true);
    startActivity(localIntent);
  }
  
  public void onSubCategoryClick(Taxon paramTaxon1, Taxon paramTaxon2)
  {
    paramTaxon1 = new Intent(getActivity(), TaxonActivity.class);
    paramTaxon1.putExtra("ICartConstant.KEYS.TAXON", paramTaxon2);
    paramTaxon1.putExtra("TaxonActivity.KEYS.IS_WITH_CAT_BAR", true);
    startActivity(paramTaxon1);
  }
  
  public void openCategoryPicker(boolean paramBoolean)
  {
    if (!isCategoryPickerOpen())
    {
      this.mLayoutCategoryList.setVisibility(0);
      this.mCategoryCarat.setRotation(270.0F);
      sendTracker("Category");
    }
    do
    {
      return;
      this.mLayoutCategoryList.setVisibility(8);
      this.mCategoryCarat.setRotation(0.0F);
    } while (paramBoolean);
    sendTracker("Shop");
  }
  
  public void openLocationChooser()
  {
    Object localObject = getApplication().getSharedPreferencesManager();
    FragmentActivity localFragmentActivity = getActivity();
    Intent localIntent = new Intent(localFragmentActivity, ChooseStoreActivity.class);
    localIntent.putExtra("ICartConstant.KEYS.EXTRAS.SUB_DISTRICT_ID", ((SharedPreferencesManager)localObject).getSubDistrictId());
    localIntent.putExtra("ICartConstant.KEYS.EXTRAS.SUB_DISTRICT_NAME", ((SharedPreferencesManager)localObject).getSubDistrictName());
    Coordinate localCoordinate = ((SharedPreferencesManager)localObject).getCoordinate();
    if (localCoordinate != null)
    {
      localIntent.putExtra("ICartConstant.KEYS.EXTRAS.LONGITUDE", localCoordinate.latitude);
      localIntent.putExtra("ICartConstant.KEYS.EXTRAS.LATITUDE", localCoordinate.longitude);
    }
    localObject = ((SharedPreferencesManager)localObject).getZipCode();
    if (localObject != null) {
      localIntent.putExtra("ICartConstant.KEYS.EXTRAS.ZIP_CODE", (String)localObject);
    }
    localFragmentActivity.startActivityForResult(localIntent, 12);
  }
  
  void openProductDetail(Product paramProduct)
  {
    if (getActivity() == null) {
      return;
    }
    Intent localIntent = new Intent(getActivity(), ViewProductActivity.class);
    localIntent.putExtra("ICartConstant.KEYS.EXTRAS.PRODUCT", paramProduct);
    startActivity(localIntent);
  }
  
  public void openTaxonFragment(Taxon paramTaxon)
  {
    if (getActivity() == null) {
      return;
    }
    Intent localIntent = new Intent(getActivity(), TaxonActivity.class);
    localIntent.putExtra("ICartConstant.KEYS.TAXON", paramTaxon);
    startActivity(localIntent);
  }
  
  public void showChangeStockLocationConfirmationDialog(final ICartCallback paramICartCallback)
  {
    View localView = LayoutInflater.from(getActivity()).inflate(2130903073, null);
    ((TextView)localView.findViewById(2131558525)).setText(getString(2131165308, new Object[] { getApplication().getSharedPreferencesManager().getStockLocationName() }));
    DialogColor localDialogColor = new DialogColor();
    localDialogColor.positiveButtonColor = Integer.valueOf(getResources().getColor(2131492920));
    DialogUtils.showDialog(getActivity(), getString(2131165307), null, getString(2131165320), getString(2131165321), null, new ICartDialogCallback()
    {
      public void onCancel() {}
      
      public void onNegative(MaterialDialog paramAnonymousMaterialDialog)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(null);
        }
      }
      
      public void onNeutral(MaterialDialog paramAnonymousMaterialDialog)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(null);
        }
      }
      
      public void onPositive(MaterialDialog paramAnonymousMaterialDialog)
      {
        paramAnonymousMaterialDialog = MainFragment.this.getApplication().currentOrder.number;
        MainFragment.this.getApplication().getOrderManager().emptyCart(paramAnonymousMaterialDialog, new ICartCallback(MainFragment.TAG)
        {
          public void onSuccess(Object paramAnonymous2Object)
          {
            paramAnonymous2Object = (Order)paramAnonymous2Object;
            MainFragment.this.getApplication().currentOrder.clearCart();
            MainFragment.this.getApplication().getICartNotification().raiseCurrentOrderSetEvent(this, (Order)paramAnonymous2Object);
            if (MainFragment.13.this.val$callback != null) {
              MainFragment.13.this.val$callback.onSuccess(null);
            }
          }
        });
      }
    }, localView, localDialogColor);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/MainFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */