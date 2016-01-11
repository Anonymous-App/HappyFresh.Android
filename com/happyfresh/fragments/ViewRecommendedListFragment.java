package com.happyfresh.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.happyfresh.activities.ViewProductActivity;
import com.happyfresh.adapters.ShoppingListItemAdapter;
import com.happyfresh.adapters.ShoppingListItemAdapter.ShoppingListItemClickListener;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.customs.CircularProgressBar;
import com.happyfresh.managers.OrderManager;
import com.happyfresh.managers.ShoppingListManager;
import com.happyfresh.models.Product;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.json.JSONException;

public class ViewRecommendedListFragment
  extends BaseFragment
  implements ShoppingListItemAdapter.ShoppingListItemClickListener
{
  LinearLayoutManager layoutManager;
  ShoppingListItemAdapter mAdapter;
  @InjectView(2131558855)
  Button mButtonUpdateCart;
  @InjectView(2131558694)
  CircularProgressBar mProgressBar;
  @InjectView(2131558856)
  CircularProgressBar mProgressBarButton;
  Long mShoppingListId;
  @InjectView(2131558715)
  RecyclerView recyclerList;
  
  private void fetchData()
  {
    this.mProgressBar.setVisibility(0);
    getApplication().getShoppingListManager().getShoppingListItem(this.mShoppingListId, new ICartCallback(getScreenName())
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        super.onFailure(paramAnonymousThrowable);
        if (ViewRecommendedListFragment.this.getActivity() == null) {
          return;
        }
        ViewRecommendedListFragment.this.mProgressBar.setVisibility(8);
      }
      
      public void onSuccess(Object paramAnonymousObject)
      {
        paramAnonymousObject = (List)paramAnonymousObject;
        ViewRecommendedListFragment.this.mAdapter.setData((List)paramAnonymousObject);
        ViewRecommendedListFragment.this.mAdapter.notifyDataSetChanged();
        if (ViewRecommendedListFragment.this.getActivity() == null) {
          return;
        }
        ViewRecommendedListFragment.this.mProgressBar.setVisibility(8);
        ViewRecommendedListFragment.this.onCartQtyChanged();
      }
    });
  }
  
  @OnClick({2131558855})
  public void addToCart()
  {
    List localList = this.mAdapter.getMultipleLineItem();
    if (localList.size() == 0)
    {
      Toast.makeText(getApplication().getApplicationContext(), getString(2131165508), 0).show();
      return;
    }
    this.mButtonUpdateCart.setEnabled(false);
    this.mProgressBarButton.setVisibility(0);
    try
    {
      getApplication().getOrderManager().updateMultipleItemToCart(localList, getScreenName(), new ICartCallback(getScreenName())
      {
        public void onFailure(Throwable paramAnonymousThrowable)
        {
          super.onFailure(paramAnonymousThrowable);
          if (ViewRecommendedListFragment.this.getActivity() == null) {
            return;
          }
          ViewRecommendedListFragment.this.mProgressBarButton.setVisibility(8);
          ViewRecommendedListFragment.this.mButtonUpdateCart.setEnabled(true);
        }
        
        public void onSuccess(Object paramAnonymousObject)
        {
          if (ViewRecommendedListFragment.this.getActivity() == null) {
            return;
          }
          ViewRecommendedListFragment.this.mProgressBarButton.setVisibility(8);
          ViewRecommendedListFragment.this.mAdapter.resetTotalAddToCart();
          ViewRecommendedListFragment.this.mAdapter.notifyDataSetChanged();
          if (ViewRecommendedListFragment.this.mButtonUpdateCart.getText().toString().equals(ViewRecommendedListFragment.this.getString(2131165271))) {
            ViewRecommendedListFragment.this.mButtonUpdateCart.setText(2131165426);
          }
          for (;;)
          {
            ViewRecommendedListFragment.this.mButtonUpdateCart.setEnabled(false);
            return;
            ViewRecommendedListFragment.this.mButtonUpdateCart.setText(2131165305);
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
    return "Recommended List";
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.mShoppingListId = Long.valueOf(getArguments().getLong("ICartConstant.KEYS.EXTRAS.SHOPPING_LIST_ID"));
    this.layoutManager = new LinearLayoutManager(getApplication());
    this.layoutManager.setOrientation(1);
    this.recyclerList.setLayoutManager(this.layoutManager);
    this.mAdapter = new ShoppingListItemAdapter(getApplication(), this);
    this.recyclerList.setAdapter(this.mAdapter);
    this.mProgressBarButton.setVisibility(4);
    fetchData();
  }
  
  public void onCartQtyChanged()
  {
    int i = this.mAdapter.isCartEdited();
    if (i == 0)
    {
      this.mButtonUpdateCart.setText(2131165271);
      this.mButtonUpdateCart.setEnabled(false);
    }
    do
    {
      return;
      if (i == 1)
      {
        this.mButtonUpdateCart.setText(2131165271);
        this.mButtonUpdateCart.setEnabled(true);
        return;
      }
      if (i == 2)
      {
        this.mButtonUpdateCart.setText(2131165624);
        this.mButtonUpdateCart.setEnabled(true);
        return;
      }
    } while (i != 3);
    this.mButtonUpdateCart.setText(2131165624);
    this.mButtonUpdateCart.setEnabled(false);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903151, null);
    ButterKnife.inject(this, paramLayoutInflater);
    return paramLayoutInflater;
  }
  
  public void onResume()
  {
    super.onResume();
    this.mAdapter.notifyDataSetChanged();
  }
  
  public void onShoppingListItemClick(Product paramProduct)
  {
    Intent localIntent = new Intent(getActivity(), ViewProductActivity.class);
    localIntent.putExtra("ICartConstant.KEYS.EXTRAS.PRODUCT", paramProduct);
    startActivity(localIntent);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/ViewRecommendedListFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */