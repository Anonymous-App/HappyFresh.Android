package com.happyfresh.fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.happyfresh.activities.MainActivity;
import com.happyfresh.activities.ViewProductActivity;
import com.happyfresh.activities.ViewRecommendedListActivity;
import com.happyfresh.adapters.FavouritesListItemAdapter;
import com.happyfresh.adapters.RecommendedListAdapter;
import com.happyfresh.adapters.RecommendedListAdapter.OnRecommendedItemClickListener;
import com.happyfresh.adapters.ShoppingListItemAdapter.ShoppingListItemClickListener;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.common.ICartNotification;
import com.happyfresh.common.ICartNotification.Type;
import com.happyfresh.customs.CircularProgressBar;
import com.happyfresh.interfaces.Listener;
import com.happyfresh.listeners.ApplicationStateChangedListener;
import com.happyfresh.managers.ApplicationStateManager;
import com.happyfresh.managers.OrderManager;
import com.happyfresh.managers.ShoppingListManager;
import com.happyfresh.models.Order;
import com.happyfresh.models.Product;
import com.happyfresh.models.ShoppingList;
import com.happyfresh.models.ShoppingListResponse;
import com.happyfresh.utils.AccengageTrackerUtils;
import com.happyfresh.utils.MixpanelTrackerUtils;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;
import org.json.JSONException;

public class ListsFavoriteFragment
  extends BaseFragment
  implements ShoppingListItemAdapter.ShoppingListItemClickListener
{
  private ApplicationStateChangedListener mAppStateChangedListener = new ApplicationStateChangedListener()
  {
    public void onEnterBackground()
    {
      ListsFavoriteFragment.access$002(ListsFavoriteFragment.this, true);
    }
    
    public void onEnterForeground() {}
  };
  @InjectView(2131558855)
  Button mBtnAddToCart;
  @InjectView(2131558854)
  View mButtonArea;
  private Listener mCurrentOrderSetListener = new Listener()
  {
    public void onHappened(Object paramAnonymousObject, ICartNotification.Type paramAnonymousType, Order paramAnonymousOrder)
    {
      paramAnonymousObject = (MainActivity)ListsFavoriteFragment.this.getActivity();
      ((MainActivity)paramAnonymousObject).invalidateOptionsMenu();
      if (!((MainActivity)paramAnonymousObject).isRecommendedSelected()) {
        ListsFavoriteFragment.this.mFavoriteAdapter.notifyDataSetChanged();
      }
    }
  };
  @InjectView(2131558857)
  View mEmptyRecommendationList;
  FavouritesListItemAdapter mFavoriteAdapter;
  private boolean mFromBackground;
  @InjectView(2131558715)
  RecyclerView mList;
  @InjectView(2131558860)
  View mNoConnectionMessage;
  @InjectView(2131558694)
  CircularProgressBar mProgressBar;
  @InjectView(2131558856)
  CircularProgressBar mProgressBarButton;
  RecommendedListAdapter mRecommendedAdapter;
  @InjectView(2131558852)
  RadioButton mTabFavorite;
  @InjectView(2131558853)
  RadioButton mTabRecommended;
  
  private void fetchData(boolean paramBoolean)
  {
    this.mList.setAdapter(null);
    this.mEmptyRecommendationList.setVisibility(8);
    this.mNoConnectionMessage.setVisibility(8);
    this.mButtonArea.setVisibility(8);
    this.mProgressBar.setVisibility(0);
    if (paramBoolean)
    {
      getRecommendedList();
      return;
    }
    getFavoriteList();
  }
  
  private void getFavoriteList()
  {
    getApplication().getShoppingListManager().getFavoriteList(new ICartCallback(getScreenName())
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        super.onFailure(paramAnonymousThrowable);
        if (ListsFavoriteFragment.this.getActivity() == null) {
          return;
        }
        ListsFavoriteFragment.this.mProgressBar.setVisibility(8);
        ListsFavoriteFragment.this.mNoConnectionMessage.setVisibility(0);
      }
      
      public void onSuccess(Object paramAnonymousObject)
      {
        if (ListsFavoriteFragment.this.getActivity() == null) {
          return;
        }
        ListsFavoriteFragment.this.mProgressBar.setVisibility(8);
        int i = 0;
        int j = 0;
        Object localObject1 = (ShoppingListResponse)paramAnonymousObject;
        paramAnonymousObject = ((ShoppingListResponse)localObject1).products;
        if (((List)paramAnonymousObject).isEmpty())
        {
          localObject2 = new Product();
          String str = String.valueOf(3001);
          ((Product)localObject2).description = str;
          ((Product)localObject2).name = str;
          ((List)paramAnonymousObject).add(localObject2);
          i = 1;
        }
        Object localObject2 = new Product();
        ((Product)localObject2).description = String.valueOf(1002);
        ((Product)localObject2).name = ListsFavoriteFragment.this.getResources().getString(2131165514);
        ((List)paramAnonymousObject).add(localObject2);
        localObject1 = ((ShoppingListResponse)localObject1).previouslyPurchasedProducts;
        if (((List)localObject1).isEmpty())
        {
          localObject1 = new Product();
          localObject2 = String.valueOf(3002);
          ((Product)localObject1).description = ((String)localObject2);
          ((Product)localObject1).name = ((String)localObject2);
          ((List)paramAnonymousObject).add(localObject1);
          j = 1;
        }
        for (;;)
        {
          ListsFavoriteFragment.this.mFavoriteAdapter.clearData();
          ListsFavoriteFragment.this.mFavoriteAdapter.setData((List)paramAnonymousObject);
          ListsFavoriteFragment.this.mFavoriteAdapter.notifyDataSetChanged();
          ListsFavoriteFragment.this.mList.setAdapter(ListsFavoriteFragment.this.mFavoriteAdapter);
          ListsFavoriteFragment.this.onCartQtyChanged();
          if ((i == 0) || (j == 0)) {
            break;
          }
          ListsFavoriteFragment.this.mButtonArea.setVisibility(8);
          return;
          ((List)paramAnonymousObject).addAll((Collection)localObject1);
        }
        ListsFavoriteFragment.this.mButtonArea.setVisibility(0);
      }
    });
  }
  
  private void getRecommendedList()
  {
    getApplication().getShoppingListManager().getRecommendedList(new ICartCallback(getScreenName())
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        super.onFailure(paramAnonymousThrowable);
        if (ListsFavoriteFragment.this.getActivity() == null) {
          return;
        }
        ListsFavoriteFragment.this.mProgressBar.setVisibility(8);
        ListsFavoriteFragment.this.mNoConnectionMessage.setVisibility(0);
      }
      
      public void onSuccess(final Object paramAnonymousObject)
      {
        if (ListsFavoriteFragment.this.getActivity() == null) {
          return;
        }
        ListsFavoriteFragment.this.mProgressBar.setVisibility(8);
        paramAnonymousObject = (List)paramAnonymousObject;
        ListsFavoriteFragment.this.mRecommendedAdapter.clearData();
        ListsFavoriteFragment.this.mRecommendedAdapter.setData((List)paramAnonymousObject);
        ListsFavoriteFragment.this.mRecommendedAdapter.notifyDataSetChanged();
        ListsFavoriteFragment.this.mRecommendedAdapter.setOnRecommendedItemClickListener(new RecommendedListAdapter.OnRecommendedItemClickListener()
        {
          public void onItemClick(int paramAnonymous2Int)
          {
            Intent localIntent = new Intent(ListsFavoriteFragment.this.getActivity(), ViewRecommendedListActivity.class);
            localIntent.putExtra("ICartConstant.KEYS.EXTRAS.SHOPPING_LIST_ID", ((ShoppingList)paramAnonymousObject.get(paramAnonymous2Int)).id);
            localIntent.putExtra("ICartConstant.KEYS.EXTRAS.SHOPPING_LIST_NAME", ((ShoppingList)paramAnonymousObject.get(paramAnonymous2Int)).name);
            ListsFavoriteFragment.this.startActivity(localIntent);
          }
        });
        ListsFavoriteFragment.this.mList.setAdapter(ListsFavoriteFragment.this.mRecommendedAdapter);
        ListsFavoriteFragment.this.onCartQtyChanged();
        if (((List)paramAnonymousObject).isEmpty())
        {
          ListsFavoriteFragment.this.mEmptyRecommendationList.setVisibility(0);
          return;
        }
        ListsFavoriteFragment.this.mEmptyRecommendationList.setVisibility(8);
      }
    });
  }
  
  @OnClick({2131558855})
  public void addToCart()
  {
    List localList = this.mFavoriteAdapter.getMultipleLineItem();
    if (localList.isEmpty())
    {
      Toast.makeText(getApplication().getApplicationContext(), getString(2131165508), 0).show();
      return;
    }
    this.mProgressBarButton.setVisibility(0);
    this.mBtnAddToCart.setEnabled(false);
    try
    {
      getApplication().getOrderManager().updateMultipleItemToCart(localList, getScreenName(), new ICartCallback(getScreenName())
      {
        public void onFailure(Throwable paramAnonymousThrowable)
        {
          super.onFailure(paramAnonymousThrowable);
          if (ListsFavoriteFragment.this.getActivity() == null) {
            return;
          }
          ListsFavoriteFragment.this.mProgressBarButton.setVisibility(4);
          ListsFavoriteFragment.this.mBtnAddToCart.setEnabled(true);
        }
        
        public void onSuccess(Object paramAnonymousObject)
        {
          ListsFavoriteFragment.this.mFavoriteAdapter.resetTotalAddToCart();
          ListsFavoriteFragment.this.mFavoriteAdapter.notifyDataSetChanged();
          if (ListsFavoriteFragment.this.getActivity() == null) {
            return;
          }
          ListsFavoriteFragment.this.mProgressBarButton.setVisibility(4);
          if (ListsFavoriteFragment.this.mBtnAddToCart.getText().toString().equals(ListsFavoriteFragment.this.getString(2131165271))) {
            ListsFavoriteFragment.this.mBtnAddToCart.setText(2131165426);
          }
          for (;;)
          {
            ListsFavoriteFragment.this.mBtnAddToCart.setEnabled(false);
            return;
            ListsFavoriteFragment.this.mBtnAddToCart.setText(2131165305);
          }
        }
      });
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
  
  protected String getScreenName()
  {
    return "Recommended List Overview";
  }
  
  public void onCartQtyChanged()
  {
    int i = this.mFavoriteAdapter.isCartEdited();
    if (i == 0)
    {
      this.mBtnAddToCart.setText(2131165271);
      this.mBtnAddToCart.setEnabled(false);
    }
    do
    {
      return;
      if (i == 1)
      {
        this.mBtnAddToCart.setText(2131165271);
        this.mBtnAddToCart.setEnabled(true);
        return;
      }
      if (i == 2)
      {
        this.mBtnAddToCart.setText(2131165624);
        this.mBtnAddToCart.setEnabled(true);
        return;
      }
    } while (i != 3);
    this.mBtnAddToCart.setText(2131165624);
    this.mBtnAddToCart.setEnabled(false);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mFavoriteAdapter = new FavouritesListItemAdapter(getApplication(), this);
    this.mRecommendedAdapter = new RecommendedListAdapter(getApplication());
    getApplication().getICartNotification().addCurrentOrderSetListener(this.mCurrentOrderSetListener);
    getApplication().getApplicationStateManager().addApplicationStateChangedListeners(this.mAppStateChangedListener);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903150, null);
    ButterKnife.inject(this, paramLayoutInflater);
    setHasOptionsMenu(true);
    return paramLayoutInflater;
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    getApplication().getICartNotification().removeCurrentOrderSetListener(this.mCurrentOrderSetListener);
    getApplication().getApplicationStateManager().removeApplicationStateChangedListeners(this.mAppStateChangedListener);
  }
  
  public void onResume()
  {
    super.onResume();
    if (this.mFromBackground) {
      this.mFromBackground = false;
    }
    while (!getApplication().isFavouritesNeedToBeSynced()) {
      return;
    }
    getApplication().setFavouritesNeedToBeSynced(false);
    refreshList();
  }
  
  public void onShoppingListItemClick(Product paramProduct)
  {
    Intent localIntent = new Intent(getActivity(), ViewProductActivity.class);
    localIntent.putExtra("ICartConstant.KEYS.EXTRAS.PRODUCT", paramProduct);
    localIntent.putExtra("ICartConstant.KEYS.EXTRAS.FROM_FAVOURITES_LIST", true);
    startActivity(localIntent);
  }
  
  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    this.mProgressBar.setVisibility(8);
    this.mProgressBarButton.setVisibility(8);
    this.mButtonArea.setVisibility(8);
    paramView = new LinearLayoutManager(getApplication());
    paramView.setOrientation(1);
    this.mList.setLayoutManager(paramView);
    this.mTabRecommended.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        ((MainActivity)ListsFavoriteFragment.this.getActivity()).setRecommendedSelected(true);
        ListsFavoriteFragment.this.sendTracker("Recommended List Overview");
        MixpanelTrackerUtils.trackViewRecommended(ListsFavoriteFragment.this.mICartApplication);
        AccengageTrackerUtils.trackViewRecommended(ListsFavoriteFragment.this.mICartApplication);
        ListsFavoriteFragment.this.fetchData(true);
      }
    });
    this.mTabFavorite.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        ((MainActivity)ListsFavoriteFragment.this.getActivity()).setRecommendedSelected(false);
        ListsFavoriteFragment.this.sendTracker("Favourites List");
        MixpanelTrackerUtils.trackViewFavorites(ListsFavoriteFragment.this.mICartApplication);
        AccengageTrackerUtils.trackViewFavorites(ListsFavoriteFragment.this.mICartApplication);
        ListsFavoriteFragment.this.fetchData(false);
      }
    });
    getApplication().setFavouritesNeedToBeSynced(true);
  }
  
  @OnClick({2131558864})
  public void refreshList()
  {
    fetchData(((MainActivity)getActivity()).isRecommendedSelected());
  }
  
  protected void sendTracker() {}
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/ListsFavoriteFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */