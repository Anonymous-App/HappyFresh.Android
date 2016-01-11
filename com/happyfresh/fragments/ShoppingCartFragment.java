package com.happyfresh.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.afollestad.materialdialogs.MaterialDialog;
import com.happyfresh.activities.ShoppingCartActivity;
import com.happyfresh.activities.ViewProductActivity;
import com.happyfresh.adapters.CartItemAdapter;
import com.happyfresh.adapters.CartItemAdapter.OnItemCartActionListener;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.callbacks.ICartDialogCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.common.ICartNotification;
import com.happyfresh.common.ICartNotification.Type;
import com.happyfresh.customs.CircularProgressBar;
import com.happyfresh.customs.RecyclerItemClickListener;
import com.happyfresh.customs.RecyclerItemClickListener.OnItemClickListener;
import com.happyfresh.interfaces.Listener;
import com.happyfresh.managers.OrderManager;
import com.happyfresh.managers.SharedPreferencesManager;
import com.happyfresh.models.LineItem;
import com.happyfresh.models.MultipleLineItem;
import com.happyfresh.models.MultipleLineItemResponse;
import com.happyfresh.models.Order;
import com.happyfresh.models.Product;
import com.happyfresh.models.Variant;
import com.happyfresh.utils.DialogColor;
import com.happyfresh.utils.DialogUtils;
import com.happyfresh.utils.GAUtils;
import com.happyfresh.utils.LogUtils;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;

public class ShoppingCartFragment
  extends BaseFragment
{
  private static final String TAG = ShoppingCartFragment.class.getSimpleName();
  @InjectView(2131558638)
  Button mButtonCheckout;
  @InjectView(2131558639)
  CircularProgressBar mButtonProgressBar;
  @InjectView(2131558635)
  RelativeLayout mCartHasItem;
  private CartItemAdapter mCartItemAdapter;
  private Listener mCurrentOrderSetListener = new Listener()
  {
    public void onHappened(Object paramAnonymousObject, ICartNotification.Type paramAnonymousType, Order paramAnonymousOrder)
    {
      ShoppingCartFragment.this.updateView();
    }
  };
  @InjectView(2131558631)
  RelativeLayout mEmptyCart;
  private LinearLayoutManager mLinearLayoutManager;
  private boolean mOutOfStockAndEmptyCart;
  @InjectView(2131558636)
  RecyclerView mRecyclerView;
  private AutoSubmitTimer mTimer;
  private Map<Long, Integer> mUpdateCartInProgress = new HashMap();
  
  private void refreshCart()
  {
    String str = this.mICartApplication.currentOrder.number;
    this.mICartApplication.getOrderManager().refreshCart(str, new ICartCallback(TAG)
    {
      public void onSuccess(Object paramAnonymousObject)
      {
        paramAnonymousObject = (Order)paramAnonymousObject;
        if (ShoppingCartFragment.this.mICartApplication.currentOrder == null) {}
        do
        {
          return;
          List localList = ShoppingCartFragment.this.mICartApplication.currentOrder.findOutOfStockItems(((Order)paramAnonymousObject).lineItems);
          ShoppingCartFragment.this.mICartApplication.outOfStockItems.clear();
          ShoppingCartFragment.this.mICartApplication.outOfStockItems.addAll(localList);
          ShoppingCartFragment.this.mICartApplication.currentOrder.clearCart();
          ShoppingCartFragment.this.mICartApplication.resetCountryCode();
          ShoppingCartFragment.this.mICartApplication.currentOrder = ((Order)paramAnonymousObject);
          ShoppingCartFragment.this.mICartApplication.getICartNotification().raiseCurrentOrderSetEvent(this, (Order)paramAnonymousObject);
          if (!localList.isEmpty()) {
            Toast.makeText(ShoppingCartFragment.this.mContext, ShoppingCartFragment.this.mContext.getString(2131165423), 1).show();
          }
          ShoppingCartFragment.this.mICartApplication.getSharedPreferencesManager().saveLastFirstTimeAddToCart();
        } while ((ShoppingCartFragment.this.getActivity() == null) || (!ShoppingCartFragment.this.isAdded()));
        ShoppingCartFragment.this.updateView();
      }
    });
  }
  
  private void removeItem(String paramString, final LineItem paramLineItem)
  {
    getApplication().getOrderManager().deleteItemFromTheCart(paramString, paramLineItem.remoteId, paramLineItem.variantId.longValue(), new ICartCallback(TAG)
    {
      public void onSuccess(List<LineItem> paramAnonymousList)
      {
        GAUtils.trackRemoveFromCart(ShoppingCartFragment.this.getApplication(), paramLineItem, ShoppingCartFragment.this.getScreenName());
        if (ShoppingCartFragment.this.getActivity() == null) {
          return;
        }
        ShoppingCartFragment.this.updateView();
      }
    });
  }
  
  private void showCartHasItem()
  {
    this.mCartHasItem.setVisibility(0);
    this.mEmptyCart.setVisibility(8);
    if (getApplication().currentOrder.isEmptyCart())
    {
      this.mOutOfStockAndEmptyCart = true;
      this.mButtonCheckout.setText(getResources().getString(2131165290));
      return;
    }
    this.mOutOfStockAndEmptyCart = false;
    this.mButtonCheckout.setText(getResources().getString(2131165312));
  }
  
  private void showCartHasNoItem()
  {
    this.mCartHasItem.setVisibility(8);
    this.mEmptyCart.setVisibility(0);
    this.mButtonCheckout.setText(getResources().getString(2131165290));
  }
  
  private void updateCart(LineItem paramLineItem, int paramInt)
  {
    this.mUpdateCartInProgress.put(paramLineItem.variantId, Integer.valueOf(paramInt));
    this.mTimer.cancel();
    this.mTimer.start();
    this.mButtonCheckout.setEnabled(false);
  }
  
  private void updatePrice()
  {
    if (getApplication().currentOrder == null) {
      return;
    }
    this.mCartItemAdapter.setSubtotalPrice(getApplication().currentOrder.displayItemTotal);
  }
  
  private void updateView()
  {
    if (getApplication().currentOrder == null)
    {
      getApplication().setCurrentOrder();
      showCartHasNoItem();
      return;
    }
    Object localObject = new ArrayList();
    ((List)localObject).addAll(getApplication().currentOrder.lineItems);
    ((List)localObject).addAll(getApplication().outOfStockItems);
    if (!((List)localObject).isEmpty())
    {
      this.mCartItemAdapter.addAll((List)localObject);
      this.mCartItemAdapter.notifyDataSetChanged();
      showCartHasItem();
    }
    for (;;)
    {
      localObject = getArguments().getString("ICartConstant.KEYS.EXTRAS.PREVIOUS_SCREEN");
      if ((!TextUtils.isEmpty((CharSequence)localObject)) && ("Cancel Order Confirmation".equalsIgnoreCase((String)localObject))) {
        this.mButtonCheckout.setText(getResources().getString(2131165329));
      }
      updatePrice();
      return;
      showCartHasNoItem();
    }
  }
  
  @OnClick({2131558634})
  void backToShopping()
  {
    this.mICartApplication.outOfStockItems.clear();
    getActivity().finish();
  }
  
  @OnClick({2131558934})
  void closeCart()
  {
    backToShopping();
  }
  
  protected String getScreenName()
  {
    return "Cart";
  }
  
  public void onActivityCreated(final Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.mButtonProgressBar.setVisibility(4);
    this.mLinearLayoutManager = new LinearLayoutManager(getActivity());
    this.mRecyclerView.setLayoutManager(this.mLinearLayoutManager);
    this.mCartItemAdapter = new CartItemAdapter(getActivity());
    this.mRecyclerView.setAdapter(this.mCartItemAdapter);
    this.mCartItemAdapter.setOnItemCartActionListener(new CartItemAdapter.OnItemCartActionListener()
    {
      public void onItemClick(LineItem paramAnonymousLineItem)
      {
        if (paramAnonymousLineItem == null) {
          return;
        }
        Product localProduct = new Product();
        localProduct.variants = new ArrayList();
        localProduct.variants.add(paramAnonymousLineItem.variant);
        localProduct.maxOrderQuantity = paramAnonymousLineItem.maxOrderQuantity;
        String str;
        if ((paramAnonymousLineItem.variant.product != null) && (paramAnonymousLineItem.variant.product.name != null))
        {
          str = paramAnonymousLineItem.variant.product.name;
          localProduct.name = str;
          if ((paramAnonymousLineItem.variant.product == null) || (paramAnonymousLineItem.variant.product.remoteId == null)) {
            break label185;
          }
        }
        label185:
        for (long l = paramAnonymousLineItem.variant.product.remoteId.longValue();; l = paramAnonymousLineItem.variant.remoteId)
        {
          localProduct.remoteId = Long.valueOf(l);
          paramAnonymousLineItem = new Intent(ShoppingCartFragment.this.getActivity(), ViewProductActivity.class);
          paramAnonymousLineItem.putExtra("ICartConstant.KEYS.EXTRAS.PRODUCT", localProduct);
          ShoppingCartFragment.this.startActivity(paramAnonymousLineItem);
          return;
          str = paramAnonymousLineItem.variant.name;
          break;
        }
      }
      
      public void onItemUpdated(LineItem paramAnonymousLineItem, int paramAnonymousInt)
      {
        ShoppingCartFragment.this.updateCart(paramAnonymousLineItem, paramAnonymousInt);
      }
    });
    paramBundle = new DialogColor();
    paramBundle.positiveButtonColor = Integer.valueOf(getResources().getColor(2131493090));
    this.mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), this.mRecyclerView, new RecyclerItemClickListener.OnItemClickListener()
    {
      public void onItemClick(View paramAnonymousView, int paramAnonymousInt) {}
      
      public void onItemLongClick(View paramAnonymousView, final int paramAnonymousInt)
      {
        if ((ShoppingCartFragment.this.getActivity() == null) || (!ShoppingCartFragment.this.isAdded())) {
          return;
        }
        ShoppingCartFragment.this.sendTracker("Remove From Cart");
        DialogUtils.showDialog(ShoppingCartFragment.this.getActivity(), null, ShoppingCartFragment.this.getString(2131165525), ShoppingCartFragment.this.getString(2131165478), ShoppingCartFragment.this.getString(2131165293), null, new ICartDialogCallback()
        {
          public void onCancel() {}
          
          public void onNegative(MaterialDialog paramAnonymous2MaterialDialog)
          {
            paramAnonymous2MaterialDialog.dismiss();
          }
          
          public void onNeutral(MaterialDialog paramAnonymous2MaterialDialog) {}
          
          public void onPositive(MaterialDialog paramAnonymous2MaterialDialog)
          {
            LineItem localLineItem = ShoppingCartFragment.this.mCartItemAdapter.getItem(paramAnonymousInt);
            if (localLineItem != null) {
              ShoppingCartFragment.this.removeItem(ShoppingCartFragment.this.getApplication().currentOrder.number, localLineItem);
            }
            paramAnonymous2MaterialDialog.dismiss();
          }
        }, null, paramBundle).setOnDismissListener(new DialogInterface.OnDismissListener()
        {
          public void onDismiss(DialogInterface paramAnonymous2DialogInterface)
          {
            ShoppingCartFragment.this.sendTracker(ShoppingCartFragment.this.getScreenName());
          }
        });
      }
    }));
    updatePrice();
  }
  
  @OnClick({2131558638})
  void onCheckoutClick()
  {
    if (!this.mOutOfStockAndEmptyCart)
    {
      this.mButtonProgressBar.setVisibility(0);
      this.mICartApplication.outOfStockItems.clear();
      ((ShoppingCartActivity)getActivity()).attemptToCheckout();
      return;
    }
    backToShopping();
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mTimer = new AutoSubmitTimer();
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903110, paramViewGroup, false);
    ButterKnife.inject(this, paramLayoutInflater);
    return paramLayoutInflater;
  }
  
  public void onDestroyView()
  {
    super.onDestroyView();
    ButterKnife.reset(this);
  }
  
  public void onPause()
  {
    getApplication().getICartNotification().removeCurrentOrderSetListener(this.mCurrentOrderSetListener);
    getApplication().getOrderManager().clearItemLock();
    super.onPause();
  }
  
  public void onResume()
  {
    super.onResume();
    updateView();
    if ((this.mICartApplication.getSharedPreferencesManager().isAlreadyMoreThanOneHourSinceLastFirstTimeAddToCart()) && (getApplication().currentOrder != null)) {
      refreshCart();
    }
    getApplication().getICartNotification().addCurrentOrderSetListener(this.mCurrentOrderSetListener);
  }
  
  public void showButtonProgressBar(boolean paramBoolean)
  {
    CircularProgressBar localCircularProgressBar;
    if (this.mButtonProgressBar != null)
    {
      localCircularProgressBar = this.mButtonProgressBar;
      if (!paramBoolean) {
        break label24;
      }
    }
    label24:
    for (int i = 0;; i = 4)
    {
      localCircularProgressBar.setVisibility(i);
      return;
    }
  }
  
  private class AutoSubmitTimer
    extends CountDownTimer
  {
    public AutoSubmitTimer()
    {
      super(1000L);
    }
    
    public void onFinish()
    {
      if (ShoppingCartFragment.this.getActivity() == null) {}
      while (ShoppingCartFragment.this.mUpdateCartInProgress.isEmpty()) {
        return;
      }
      if (ShoppingCartFragment.this.mButtonProgressBar.getVisibility() == 4) {
        ShoppingCartFragment.this.mButtonProgressBar.setVisibility(0);
      }
      ArrayList localArrayList = new ArrayList();
      Iterator localIterator = ShoppingCartFragment.this.mUpdateCartInProgress.keySet().iterator();
      while (localIterator.hasNext())
      {
        Long localLong = (Long)localIterator.next();
        MultipleLineItem localMultipleLineItem = new MultipleLineItem();
        localMultipleLineItem.variantId = localLong.longValue();
        localMultipleLineItem.quantity = ((Integer)ShoppingCartFragment.this.mUpdateCartInProgress.get(localLong)).intValue();
        localArrayList.add(localMultipleLineItem);
      }
      ShoppingCartFragment.this.mUpdateCartInProgress.clear();
      try
      {
        ShoppingCartFragment.this.getApplication().getOrderManager().updateMultipleItemToCart(localArrayList, ShoppingCartFragment.this.getScreenName(), new ICartCallback(ShoppingCartFragment.TAG)
        {
          public void onFailure(Throwable paramAnonymousThrowable)
          {
            super.onFailure(paramAnonymousThrowable);
            Toast.makeText(ShoppingCartFragment.this.mContext, ShoppingCartFragment.this.mContext.getString(2131165272), 0).show();
            if (ShoppingCartFragment.this.getActivity() == null) {
              return;
            }
            if (ShoppingCartFragment.this.mButtonProgressBar.getVisibility() == 0)
            {
              ShoppingCartFragment.this.mButtonProgressBar.setVisibility(4);
              ShoppingCartFragment.this.mButtonCheckout.setEnabled(true);
            }
            paramAnonymousThrowable = ShoppingCartFragment.this.getApplication().currentOrder.lineItems.iterator();
            while (paramAnonymousThrowable.hasNext()) {
              ((LineItem)paramAnonymousThrowable.next()).btnSubItemEnabled = true;
            }
            ShoppingCartFragment.this.updateView();
          }
          
          public void onSuccess(MultipleLineItemResponse paramAnonymousMultipleLineItemResponse)
          {
            if (ShoppingCartFragment.this.getActivity() == null) {
              return;
            }
            if (ShoppingCartFragment.this.mButtonProgressBar.getVisibility() == 0)
            {
              ShoppingCartFragment.this.mButtonProgressBar.setVisibility(4);
              ShoppingCartFragment.this.mButtonCheckout.setEnabled(true);
            }
            ShoppingCartFragment.this.updateView();
          }
        });
        return;
      }
      catch (JSONException localJSONException)
      {
        LogUtils.LOG(ShoppingCartFragment.TAG + " " + localJSONException.getLocalizedMessage());
        return;
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        LogUtils.LOG(ShoppingCartFragment.TAG + " " + localUnsupportedEncodingException.getLocalizedMessage());
      }
    }
    
    public void onTick(long paramLong) {}
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/ShoppingCartFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */