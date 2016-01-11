package com.happyfresh.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.happyfresh.activities.OrderListActivity;
import com.happyfresh.activities.OrderStatusActivity;
import com.happyfresh.adapters.OrderAdapter;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.common.ICartNotification;
import com.happyfresh.common.ICartNotification.Type;
import com.happyfresh.customs.CircularProgressBar;
import com.happyfresh.interfaces.Listener;
import com.happyfresh.listeners.OnItemClickListener;
import com.happyfresh.managers.OrderManager;
import com.happyfresh.models.Order;
import com.happyfresh.models.OrderResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrderListFragment
  extends BaseFragment
{
  private static final String TAG = OrderListFragment.class.getSimpleName();
  private static final int perPage = 10000;
  private Listener mCurrentOrderSetListener = new Listener()
  {
    public void onHappened(Object paramAnonymousObject, ICartNotification.Type paramAnonymousType, Order paramAnonymousOrder)
    {
      ((OrderListActivity)OrderListFragment.this.getActivity()).invalidateOptionsMenu();
    }
  };
  private LinearLayoutManager mLinearLayoutManager;
  private OrderAdapter mOrderAdapter;
  @InjectView(2131558694)
  CircularProgressBar mProgressBar;
  @InjectView(2131558715)
  RecyclerView mRecyclerView;
  
  private void fetchOrders(int paramInt)
  {
    this.mProgressBar.setVisibility(0);
    getApplication().getOrderManager().getMyCompletedOrder(Integer.valueOf(paramInt), Integer.valueOf(10000), new ICartCallback(TAG)
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        super.onFailure(paramAnonymousThrowable);
        if (OrderListFragment.this.getActivity() == null) {
          return;
        }
        OrderListFragment.this.mProgressBar.setVisibility(8);
      }
      
      public void onSuccess(Object paramAnonymousObject)
      {
        paramAnonymousObject = (OrderResponse)paramAnonymousObject;
        if (paramAnonymousObject != null)
        {
          paramAnonymousObject = ((OrderResponse)paramAnonymousObject).orders;
          localHashSet = new HashSet(OrderListFragment.this.getApplication().getOrderList());
          localHashSet.addAll((Collection)paramAnonymousObject);
          paramAnonymousObject = new ArrayList(localHashSet);
          Collections.sort((List)paramAnonymousObject, new Comparator()
          {
            public int compare(Order paramAnonymous2Order1, Order paramAnonymous2Order2)
            {
              if (paramAnonymous2Order1.isActive() == paramAnonymous2Order2.isActive()) {
                if (paramAnonymous2Order2.remoteId <= paramAnonymous2Order1.remoteId) {}
              }
              while (paramAnonymous2Order2.isActive())
              {
                return 1;
                if (paramAnonymous2Order1.remoteId > paramAnonymous2Order2.remoteId) {
                  return -1;
                }
                return 0;
              }
              return -1;
            }
          });
          OrderListFragment.this.getApplication().getOrderList().clear();
          OrderListFragment.this.getApplication().getOrderList().addAll((Collection)paramAnonymousObject);
          if (OrderListFragment.this.getActivity() != null) {}
        }
        while (OrderListFragment.this.getActivity() == null)
        {
          HashSet localHashSet;
          return;
          OrderListFragment.this.mProgressBar.setVisibility(8);
          OrderListFragment.this.updateList();
          return;
        }
        OrderListFragment.this.mProgressBar.setVisibility(8);
      }
    });
  }
  
  private void updateList()
  {
    this.mOrderAdapter.addAll(getApplication().getOrderList());
  }
  
  protected String getScreenName()
  {
    return "Orders Overview";
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.mLinearLayoutManager = new LinearLayoutManager(getActivity());
    this.mRecyclerView.setLayoutManager(this.mLinearLayoutManager);
    this.mOrderAdapter = new OrderAdapter(getActivity());
    this.mRecyclerView.setAdapter(this.mOrderAdapter);
    this.mOrderAdapter.setOnItemClickListener(new OnItemClickListener()
    {
      public void onItemClick(View paramAnonymousView, final Object paramAnonymousObject)
      {
        new Handler().postDelayed(new Runnable()
        {
          public void run()
          {
            if ((OrderListFragment.this.getActivity() == null) || (paramAnonymousObject == null) || (!(paramAnonymousObject instanceof Order))) {
              return;
            }
            Intent localIntent = new Intent(OrderListFragment.this.getActivity(), OrderStatusActivity.class);
            localIntent.putExtra("ICartConstant.KEYS.ORDER_NUMBER", ((Order)paramAnonymousObject).number);
            OrderListFragment.this.startActivity(localIntent);
          }
        }, 50L);
      }
    });
    getApplication().getOrderList().clear();
    fetchOrders(1);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    getApplication().getICartNotification().addCurrentOrderSetListener(this.mCurrentOrderSetListener);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903134, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    this.mProgressBar.setVisibility(8);
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
  
  public void onResume()
  {
    super.onResume();
    if (this.mICartApplication.isOrderListNeedToBeSynced())
    {
      this.mICartApplication.setOrderListNeedToBeSynced(false);
      getApplication().getOrderList().clear();
      fetchOrders(1);
    }
    updateList();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/OrderListFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */