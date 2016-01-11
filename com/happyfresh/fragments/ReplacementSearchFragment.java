package com.happyfresh.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.happyfresh.adapters.ReplacementResultAdapter;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.common.ICartNotification;
import com.happyfresh.common.ICartNotification.Type;
import com.happyfresh.customs.CircularProgressBar;
import com.happyfresh.interfaces.Listener;
import com.happyfresh.listeners.OnReplacementUpdated;
import com.happyfresh.managers.ProductManager;
import com.happyfresh.managers.ReplacementManager;
import com.happyfresh.models.Image;
import com.happyfresh.models.LineItem;
import com.happyfresh.models.Order;
import com.happyfresh.models.Product;
import com.happyfresh.models.ProductResponse;
import com.happyfresh.models.Replacement;
import com.happyfresh.models.Variant;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Deprecated
public class ReplacementSearchFragment
  extends BaseFragment
  implements OnReplacementUpdated
{
  private static final String TAG = ReplacementSearchFragment.class.getSimpleName();
  @InjectView(2131558664)
  CircularProgressBar mButtonProgress;
  Context mContext;
  private Listener mCurrentOrderSetListener = new Listener()
  {
    public void onHappened(Object paramAnonymousObject, ICartNotification.Type paramAnonymousType, Order paramAnonymousOrder)
    {
      ReplacementSearchFragment.this.getApplication().currentOrder = paramAnonymousOrder;
      ReplacementSearchFragment.this.onActivityCreated(null);
    }
  };
  @InjectView(2131558848)
  TextView mDisplayPrice;
  @InjectView(2131558850)
  Button mDoneButton;
  @InjectView(2131558843)
  View mExistingReplacement;
  LineItem mLineItem;
  Long mLineItemId;
  @InjectView(2131558846)
  ImageView mProductImage;
  @InjectView(2131558847)
  TextView mProductName;
  @InjectView(2131558715)
  RecyclerView mRecyclerView;
  @InjectView(2131559101)
  ImageView mReplaceButton;
  @InjectView(2131559100)
  View mReplaceButtonProgress;
  @InjectView(2131559096)
  ImageView mReplacementProductImage;
  @InjectView(2131559098)
  TextView mReplacementProductName;
  @InjectView(2131559099)
  TextView mReplacementProductPrice;
  ReplacementResultAdapter mResultAdapter;
  @InjectView(2131558844)
  CircularProgressBar mSearchProgress;
  @InjectView(2131558849)
  SearchView mSearchView;
  
  private NumberFormat getNumberFormatter()
  {
    Object localObject = getApplication().getCountryCode();
    localObject = NumberFormat.getCurrencyInstance(getApplication().getLocaleByCountryCode((String)localObject));
    ((NumberFormat)localObject).setMinimumFractionDigits(2);
    return (NumberFormat)localObject;
  }
  
  public static ReplacementSearchFragment newInstance(Long paramLong)
  {
    Bundle localBundle = new Bundle();
    localBundle.putLong("ICartConstant.LINE_ITEM_REMOTE_ID", paramLong.longValue());
    paramLong = new ReplacementSearchFragment();
    paramLong.setArguments(localBundle);
    return paramLong;
  }
  
  private void search(String paramString)
  {
    if (paramString.length() == 0) {
      return;
    }
    this.mExistingReplacement.setVisibility(8);
    hideSoftKeyboard();
    this.mSearchProgress.setVisibility(0);
    long l = getApplication().getStockLocationId();
    getApplication().getProductManager().searchProduct(Long.valueOf(l), paramString, null, Integer.valueOf(1000), new ICartCallback(TAG)
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        super.onFailure(paramAnonymousThrowable);
        if (ReplacementSearchFragment.this.getActivity() == null) {
          return;
        }
        ReplacementSearchFragment.this.mSearchProgress.setVisibility(4);
      }
      
      public void onSuccess(ProductResponse paramAnonymousProductResponse)
      {
        if (ReplacementSearchFragment.this.getActivity() == null) {
          return;
        }
        ReplacementSearchFragment.this.mSearchProgress.setVisibility(4);
        ArrayList localArrayList = new ArrayList();
        paramAnonymousProductResponse = paramAnonymousProductResponse.products.iterator();
        while (paramAnonymousProductResponse.hasNext())
        {
          Product localProduct = (Product)paramAnonymousProductResponse.next();
          if (((Variant)localProduct.variants.get(0)).remoteId != ReplacementSearchFragment.this.mLineItem.variant.remoteId) {
            localArrayList.add(localProduct);
          }
        }
        if (localArrayList.size() > 0) {
          ReplacementSearchFragment.this.mResultAdapter.addAll(localArrayList);
        }
        for (;;)
        {
          ReplacementSearchFragment.this.mResultAdapter.notifyDataSetChanged();
          return;
          ReplacementSearchFragment.this.mResultAdapter.clear();
        }
      }
    });
  }
  
  @OnClick({2131559101})
  void cancelReplacement()
  {
    showProgress(true);
    sendDeleteReplacement();
  }
  
  void clearSearchResult()
  {
    this.mResultAdapter.clear();
    this.mResultAdapter.notifyDataSetChanged();
  }
  
  void deleteReplacement()
  {
    List localList = getApplication().currentOrder.lineItems;
    ((LineItem)localList.get(localList.indexOf(this.mLineItem))).setReplacement(null);
    getApplication().currentOrder.lineItems = new ArrayList(localList);
    onUpdated(2131165369);
  }
  
  @OnClick({2131558850})
  void done()
  {
    getActivity().onBackPressed();
  }
  
  void drawCurrentReplacement()
  {
    this.mReplaceButtonProgress.setVisibility(4);
    if (this.mLineItem.getReplacement() != null)
    {
      this.mExistingReplacement.setVisibility(0);
      Object localObject2 = this.mLineItem.getReplacement().variant;
      Object localObject1 = null;
      if (((Variant)localObject2).images.size() > 1) {
        localObject1 = ((Image)((Variant)localObject2).images.get(0)).productUrl;
      }
      Picasso.with(this.mContext).load((String)localObject1).error(2130837739).placeholder(2130837739).into(this.mReplacementProductImage);
      this.mReplacementProductName.setText(((Variant)localObject2).name);
      localObject1 = Double.valueOf(this.mLineItem.getReplacement().price.doubleValue() - this.mLineItem.price.doubleValue());
      localObject2 = getNumberFormatter().format(localObject1);
      if (((Double)localObject1).doubleValue() > 0.0D) {}
      for (int i = 2131165544;; i = 2131165545)
      {
        localObject1 = this.mContext.getString(i, new Object[] { getNumberFormatter().format(this.mLineItem.getReplacement().price), localObject2 });
        this.mReplacementProductPrice.setText(Html.fromHtml((String)localObject1));
        this.mReplaceButton.setImageResource(2130837800);
        return;
      }
    }
    this.mExistingReplacement.setVisibility(8);
  }
  
  protected String getScreenName()
  {
    return "Choose Replacement";
  }
  
  void hideSoftKeyboard()
  {
    ((InputMethodManager)this.mContext.getSystemService("input_method")).hideSoftInputFromWindow(this.mSearchView.getWindowToken(), 0);
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.mButtonProgress.setVisibility(4);
    this.mSearchProgress.setVisibility(4);
    if (getApplication().currentOrder == null)
    {
      getApplication().setCurrentOrder();
      this.mProductName.setText("");
      this.mDisplayPrice.setText("");
      return;
    }
    paramBundle = getApplication().currentOrder.lineItems.iterator();
    while (paramBundle.hasNext())
    {
      LineItem localLineItem = (LineItem)paramBundle.next();
      if (localLineItem.remoteId == this.mLineItemId.longValue()) {
        this.mLineItem = localLineItem;
      }
    }
    if (this.mLineItem.variant == null)
    {
      getActivity().finish();
      return;
    }
    paramBundle = this.mLineItem.variant.getFirstImageProductUrl();
    Picasso.with(this.mContext).cancelRequest(this.mProductImage);
    Picasso.with(this.mContext).load(paramBundle).error(2130837739).placeholder(2130837739).into(this.mProductImage);
    this.mProductName.setText(this.mLineItem.variant.name);
    this.mDisplayPrice.setText(this.mLineItem.singleDisplayAmount);
    this.mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
    {
      public boolean onQueryTextChange(String paramAnonymousString)
      {
        if (paramAnonymousString.length() == 0)
        {
          ReplacementSearchFragment.this.clearSearchResult();
          ReplacementSearchFragment.this.drawCurrentReplacement();
        }
        return false;
      }
      
      public boolean onQueryTextSubmit(String paramAnonymousString)
      {
        ReplacementSearchFragment.this.search(paramAnonymousString);
        return false;
      }
    });
    this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    this.mResultAdapter = new ReplacementResultAdapter(this.mContext, getApplication(), this.mLineItem);
    this.mResultAdapter.setOnReplaceListener(this);
    this.mRecyclerView.setAdapter(this.mResultAdapter);
    drawCurrentReplacement();
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    getApplication().getICartNotification().addCurrentOrderSetListener(this.mCurrentOrderSetListener);
    this.mContext = getActivity();
    this.mLineItemId = Long.valueOf(getArguments().getLong("ICartConstant.LINE_ITEM_REMOTE_ID"));
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903148, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    return paramLayoutInflater;
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    getApplication().getICartNotification().removeCurrentOrderSetListener(this.mCurrentOrderSetListener);
  }
  
  public void onPause()
  {
    hideSoftKeyboard();
    super.onPause();
  }
  
  public void onUpdated(int paramInt)
  {
    if ((getActivity() == null) || (this.mDoneButton == null)) {
      return;
    }
    this.mDoneButton.setText(getString(paramInt));
  }
  
  void sendDeleteReplacement()
  {
    getApplication().getReplacementManager().deleteReplacement(this.mLineItem, new ICartCallback(TAG)
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        ReplacementSearchFragment.this.showProgress(false);
      }
      
      public void onSuccess(Object paramAnonymousObject)
      {
        ReplacementSearchFragment.this.deleteReplacement();
        ReplacementSearchFragment.this.showProgress(false);
        ReplacementSearchFragment.this.mExistingReplacement.setVisibility(8);
      }
    });
  }
  
  public void showProgress(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.mReplaceButton.setVisibility(8);
      this.mReplaceButtonProgress.setVisibility(0);
      return;
    }
    this.mReplaceButton.setVisibility(0);
    this.mReplaceButtonProgress.setVisibility(4);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/ReplacementSearchFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */