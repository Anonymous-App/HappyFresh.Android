package com.happyfresh.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.happyfresh.activities.FullImageActivity;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.callbacks.ICartRetrofitCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.common.ICartNotification;
import com.happyfresh.managers.OrderManager;
import com.happyfresh.managers.ProductManager;
import com.happyfresh.managers.ShoppingListManager;
import com.happyfresh.models.EditingCartInProgress;
import com.happyfresh.models.Image;
import com.happyfresh.models.LineItem;
import com.happyfresh.models.Order;
import com.happyfresh.models.Product;
import com.happyfresh.models.ShoppingList;
import com.happyfresh.models.ShoppingListItem;
import com.happyfresh.models.Variant;
import com.happyfresh.utils.AccengageTrackerUtils;
import com.happyfresh.utils.GAUtils;
import com.happyfresh.utils.MixpanelTrackerUtils;
import com.viewpagerindicator.CirclePageIndicator;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.json.JSONException;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ViewProductFragment
  extends BaseFragment
{
  private static final String TAG = ViewProductFragment.class.getSimpleName();
  @InjectView(2131558909)
  ImageView mAddItem;
  @InjectView(2131558910)
  Button mAddToCartButton;
  @InjectView(2131558897)
  View mBottomView;
  private LineItem mCurrentLineItem;
  private boolean mFromFavouritesList;
  private boolean mFromOrderHistory;
  private boolean mInCart;
  private Product mProduct;
  private List<Image> mProductImages = new ArrayList();
  private ProductPagerAdapter mProductPagerAdapter;
  private Long mSearchId;
  @InjectView(2131558906)
  TextView mSingleProductDescription;
  @InjectView(2131558901)
  View mSingleProductDiscountArea;
  @InjectView(2131558905)
  TextView mSingleProductDiscountCombination;
  @InjectView(2131558903)
  TextView mSingleProductDiscountPercentage;
  @InjectView(2131558899)
  TextView mSingleProductName;
  @InjectView(2131558902)
  TextView mSingleProductOriginalPrice;
  @InjectView(2131558900)
  TextView mSingleProductPrice;
  @InjectView(2131558904)
  TextView mSingleProductProperty;
  @InjectView(2131558907)
  ImageView mSubItem;
  private int mTotalItem;
  @InjectView(2131558908)
  TextView mTotalItemInTheCart;
  private Variant mVariant;
  @InjectView(2131558737)
  ViewPager mViewPager;
  @InjectView(2131558738)
  CirclePageIndicator mViewPagerIndicator;
  private Integer maxOrderQty;
  
  private void addItemToCart()
  {
    if (this.mICartApplication.currentOrder == null)
    {
      Toast.makeText(this.mContext, this.mContext.getString(2131165671), 0).show();
      return;
    }
    this.mICartApplication.getEditingCartInProgress().addItemToAddToCartInProgress(Long.valueOf(this.mVariant.remoteId), Integer.valueOf(this.mTotalItem));
    this.mICartApplication.getICartNotification().raiseCurrentOrderSetEvent(this, this.mICartApplication.currentOrder);
    updateAddToCartButton(CartAction.ADD, true);
    try
    {
      getApplication().getOrderManager().addItemToCart(this.mVariant.remoteId, this.mTotalItem, this.mSearchId, this.mFromOrderHistory);
      return;
    }
    catch (JSONException localJSONException)
    {
      Log.e(TAG, localJSONException.getLocalizedMessage());
      return;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      Log.e(TAG, localUnsupportedEncodingException.getLocalizedMessage());
    }
  }
  
  private void getProduct()
  {
    getApplication().getProductManager().getProductsById(Long.valueOf(getApplication().getStockLocationId()), this.mProduct.remoteId, new ICartRetrofitCallback(getApplication())
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        if (ViewProductFragment.this.getActivity() == null) {}
        while ((paramAnonymousRetrofitError.getResponse() == null) || (paramAnonymousRetrofitError.getResponse().getStatus() != 404)) {
          return;
        }
        ViewProductFragment.this.showNotAvailable();
      }
      
      public void success(Object paramAnonymousObject, Response paramAnonymousResponse)
      {
        ViewProductFragment.access$002(ViewProductFragment.this, (Product)paramAnonymousObject);
        if (ViewProductFragment.this.getActivity() == null) {
          return;
        }
        ViewProductFragment.this.updateView();
      }
    });
  }
  
  private void initialItemValue()
  {
    int j = 1;
    this.mInCart = false;
    long l = getApplication().getStockLocationId();
    int i = j;
    if (getApplication().currentOrder != null)
    {
      localObject = getApplication().currentOrder.getLineItem(this.mVariant.remoteId, Long.valueOf(l).longValue());
      i = j;
      if (localObject != null)
      {
        this.mCurrentLineItem = ((LineItem)localObject);
        i = ((LineItem)localObject).quantity.intValue();
        this.mInCart = true;
      }
    }
    this.mTotalItem = i;
    updateTotalItem();
    if (this.mInCart)
    {
      this.mAddToCartButton.setText(getString(2131165409, new Object[] { Integer.valueOf(this.mTotalItem) }));
      this.mAddToCartButton.setBackgroundResource(2130837607);
      this.mAddToCartButton.setEnabled(false);
    }
    MixpanelTrackerUtils.trackViewProductDetail(getApplication(), this.mProduct);
    AccengageTrackerUtils.trackViewProductDetail(getApplication(), this.mProduct);
    Object localObject = "Popular";
    if (this.mSearchId == null) {
      localObject = "Search Results";
    }
    GAUtils.trackECommerceProductDetail(getActivity(), this.mProduct, getScreenName(), (String)localObject);
  }
  
  private boolean isFavoriteProduct()
  {
    boolean bool2 = false;
    Object localObject = ShoppingList.getFavoriteListItem();
    boolean bool1 = bool2;
    if (localObject != null)
    {
      localObject = ((List)localObject).iterator();
      ShoppingListItem localShoppingListItem;
      do
      {
        bool1 = bool2;
        if (!((Iterator)localObject).hasNext()) {
          break;
        }
        localShoppingListItem = (ShoppingListItem)((Iterator)localObject).next();
      } while (this.mVariant.remoteId != localShoppingListItem.variantId);
      bool1 = true;
    }
    return bool1;
  }
  
  private void sendConversion()
  {
    getApplication().getProductManager().conversionViewProductDetail(Long.valueOf(getApplication().getStockLocationId()), Long.valueOf(this.mVariant.remoteId));
  }
  
  private void showMaxQtyReached()
  {
    Toast.makeText(getApplication(), getString(2131165424), 0).show();
  }
  
  private void showNotAvailable()
  {
    this.mSingleProductPrice.setVisibility(8);
    this.mSingleProductProperty.setText(getString(2131165475));
    this.mSingleProductDescription.setText(this.mProduct.description);
    this.mBottomView.setVisibility(0);
    this.mAddToCartButton.setEnabled(false);
    this.mAddItem.setEnabled(false);
    this.mSubItem.setEnabled(false);
  }
  
  private void updateAddToCartButton(CartAction paramCartAction, boolean paramBoolean)
  {
    if (this.mAddToCartButton != null)
    {
      if (!paramBoolean) {
        break label64;
      }
      localButton = this.mAddToCartButton;
      if (paramCartAction != CartAction.ADD) {
        break label57;
      }
    }
    label57:
    for (int i = 2131165273;; i = 2131165626)
    {
      localButton.setText(i);
      this.mAddToCartButton.setEnabled(false);
      if (getActivity() != null) {
        getActivity().finish();
      }
      return;
    }
    label64:
    Button localButton = this.mAddToCartButton;
    if (paramCartAction == CartAction.ADD) {}
    for (i = 2131165271;; i = 2131165624)
    {
      localButton.setText(i);
      this.mAddToCartButton.setEnabled(true);
      break;
    }
  }
  
  private void updateItemInCart()
  {
    this.mICartApplication.getEditingCartInProgress().addItemToUpdateItemInCartInProgress(Long.valueOf(this.mVariant.remoteId), Integer.valueOf(this.mTotalItem));
    this.mICartApplication.getICartNotification().raiseCurrentOrderSetEvent(this, this.mICartApplication.currentOrder);
    updateAddToCartButton(CartAction.UPDATE, true);
    try
    {
      getApplication().getOrderManager().updateItemInTheCart(getApplication().currentOrder.number, this.mCurrentLineItem, this.mTotalItem, this.mSearchId, this.mFromOrderHistory);
      return;
    }
    catch (JSONException localJSONException)
    {
      Log.e(TAG, localJSONException.getLocalizedMessage());
      return;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      Log.e(TAG, localUnsupportedEncodingException.getLocalizedMessage());
    }
  }
  
  private void updateTotalItem()
  {
    this.mTotalItemInTheCart.setText(String.valueOf(this.mTotalItem));
    this.mAddToCartButton.setBackgroundResource(2130837611);
    this.mAddToCartButton.setEnabled(true);
    if (this.mInCart)
    {
      if ((this.mCurrentLineItem == null) || (this.mCurrentLineItem.quantity.intValue() == this.mTotalItem)) {
        break label122;
      }
      this.mAddToCartButton.setText(2131165624);
      this.mAddToCartButton.setEnabled(true);
    }
    while ((this.maxOrderQty != null) && (this.maxOrderQty.intValue() > 1) && (this.mTotalItem >= this.maxOrderQty.intValue()))
    {
      this.mAddItem.setEnabled(false);
      return;
      label122:
      this.mAddToCartButton.setText(getString(2131165409, new Object[] { Integer.valueOf(this.mTotalItem) }));
      this.mAddToCartButton.setBackgroundResource(2130837607);
      this.mAddToCartButton.setEnabled(false);
    }
    this.mAddItem.setEnabled(true);
  }
  
  private void updateView()
  {
    String str1 = getString(2131165366, new Object[] { this.mProduct.displayPrice, this.mProduct.displayUnit });
    this.mSingleProductPrice.setText(Html.fromHtml(str1));
    if (this.mProduct.hasPromo())
    {
      str1 = this.mProduct.displayNormalPrice;
      String str2 = this.mProduct.displayPromoPercentage;
      if ((!TextUtils.isEmpty(str1)) && (!TextUtils.isEmpty(str2)))
      {
        this.mSingleProductDiscountArea.setVisibility(0);
        this.mSingleProductOriginalPrice.setText(str1);
        this.mSingleProductOriginalPrice.setPaintFlags(this.mSingleProductOriginalPrice.getPaintFlags() | 0x10);
        this.mSingleProductDiscountPercentage.setText(str2);
      }
      str1 = this.mProduct.displayPromoCombinationText;
      if (!TextUtils.isEmpty(str1))
      {
        this.mSingleProductDiscountCombination.setVisibility(0);
        this.mSingleProductDiscountCombination.setText(str1);
      }
    }
    if (this.mProduct.displayAverageWeight == null) {
      this.mSingleProductProperty.setVisibility(8);
    }
    for (;;)
    {
      this.mSingleProductDescription.setText(this.mProduct.description);
      this.mBottomView.setVisibility(0);
      initialItemValue();
      sendConversion();
      return;
      this.mSingleProductProperty.setVisibility(0);
      this.mSingleProductProperty.setText(this.mProduct.displayAverageWeight);
    }
  }
  
  @OnClick({2131558909})
  void addItem()
  {
    if ((this.maxOrderQty != null) && (this.maxOrderQty.intValue() == 1))
    {
      showMaxQtyReached();
      this.mAddItem.setEnabled(false);
      return;
    }
    this.mTotalItem += 1;
    if ((this.maxOrderQty != null) && (this.mTotalItem >= this.maxOrderQty.intValue())) {
      showMaxQtyReached();
    }
    updateTotalItem();
  }
  
  @OnClick({2131558910})
  void addOrUpdateItem()
  {
    if (this.mCurrentLineItem == null)
    {
      addItemToCart();
      return;
    }
    updateItemInCart();
  }
  
  protected String getScreenName()
  {
    return "Product Details";
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.mProductPagerAdapter = new ProductPagerAdapter(getActivity().getSupportFragmentManager());
    this.mViewPager.setAdapter(this.mProductPagerAdapter);
    this.mViewPagerIndicator.setViewPager(this.mViewPager);
    if (getArguments().keySet().contains("ICartConstant.SEARCH_ID")) {
      this.mSearchId = Long.valueOf(getArguments().getLong("ICartConstant.SEARCH_ID"));
    }
    if (this.mVariant != null) {
      this.mProductImages = this.mVariant.images;
    }
    this.mProductPagerAdapter.notifyDataSetChanged();
    this.mSingleProductName.setText(this.mProduct.name);
    if (TextUtils.isEmpty(this.mProduct.displayPrice))
    {
      getProduct();
      return;
    }
    updateView();
  }
  
  @OnClick({2131558898})
  void onClick()
  {
    Intent localIntent = new Intent(getActivity(), FullImageActivity.class);
    localIntent.putParcelableArrayListExtra("ICartConstant.KEYS.EXTRAS.PRODUCT_IMAGES", (ArrayList)this.mProductImages);
    localIntent.putExtra("ICartConstant.KEYS.EXTRAS.PRODUCT_IMAGES_POSITION", this.mViewPager.getCurrentItem());
    startActivity(localIntent);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mProduct = ((Product)getArguments().getParcelable("ICartConstant.KEYS.EXTRAS.PRODUCT"));
    this.mVariant = ((Variant)this.mProduct.variants.get(0));
    this.mFromOrderHistory = getArguments().getBoolean("ICartConstant.KEYS.EXTRAS.FROM_ORDER_HISTORY");
    this.mFromFavouritesList = getArguments().getBoolean("ICartConstant.KEYS.EXTRAS.FROM_FAVOURITES_LIST");
    if (this.mFromFavouritesList) {
      getApplication().setFavouritesNeedToBeSynced(false);
    }
    this.maxOrderQty = this.mProduct.maxOrderQuantity;
    setHasOptionsMenu(true);
  }
  
  public void onCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
  {
    paramMenuInflater.inflate(2131623940, paramMenu);
    if (isFavoriteProduct()) {}
    for (int i = 2130837767;; i = 2130837766)
    {
      paramMenu.findItem(2131559113).setIcon(i);
      return;
    }
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903158, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    return paramLayoutInflater;
  }
  
  public void onDestroyView()
  {
    super.onDestroyView();
    ButterKnife.reset(this);
  }
  
  void onFavoriteClick()
  {
    ICartCallback local2 = new ICartCallback(TAG)
    {
      public void onSuccess(Object paramAnonymousObject)
      {
        if (ViewProductFragment.this.getActivity() == null) {
          return;
        }
        ViewProductFragment.this.getActivity().invalidateOptionsMenu();
        ViewProductFragment.this.getApplication().setFavouritesNeedToBeSynced(true);
      }
    };
    try
    {
      if (isFavoriteProduct())
      {
        getApplication().getShoppingListManager().deleteFromFavorite(this.mVariant, local2);
        return;
      }
      getApplication().getShoppingListManager().addToFavorite(this.mVariant, local2);
      return;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
      return;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      localUnsupportedEncodingException.printStackTrace();
    }
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default: 
      return super.onOptionsItemSelected(paramMenuItem);
    }
    onFavoriteClick();
    return true;
  }
  
  @OnClick({2131558907})
  void subItem()
  {
    if (this.mTotalItem > 1)
    {
      this.mTotalItem -= 1;
      updateTotalItem();
    }
  }
  
  private static enum CartAction
  {
    ADD,  UPDATE;
    
    private CartAction() {}
  }
  
  private class ProductPagerAdapter
    extends FragmentStatePagerAdapter
  {
    public ProductPagerAdapter(FragmentManager paramFragmentManager)
    {
      super();
    }
    
    public int getCount()
    {
      return ViewProductFragment.this.mProductImages.size();
    }
    
    public Fragment getItem(int paramInt)
    {
      Bundle localBundle = new Bundle();
      localBundle.putParcelableArrayList("ICartConstant.KEYS.EXTRAS.PRODUCT_IMAGES", (ArrayList)ViewProductFragment.this.mProductImages);
      localBundle.putInt("ICartConstant.KEYS.EXTRAS.PRODUCT_IMAGES_POSITION", paramInt);
      ProductImageFragment localProductImageFragment = new ProductImageFragment();
      localProductImageFragment.setArguments(localBundle);
      return localProductImageFragment;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/ViewProductFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */