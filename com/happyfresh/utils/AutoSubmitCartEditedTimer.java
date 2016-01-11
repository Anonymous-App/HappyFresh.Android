package com.happyfresh.utils;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.common.ICartNotification;
import com.happyfresh.managers.OrderManager;
import com.happyfresh.models.EditingCartInProgress;
import com.happyfresh.models.LineItem;
import com.happyfresh.models.Order;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.json.JSONException;

public class AutoSubmitCartEditedTimer
  extends CountDownTimer
{
  public static String TAG = "AutoSubmitCartEditedTimer";
  private Callback mCallback;
  private Context mContext;
  private ICartApplication mICartApplication;
  private int mLastAction;
  private LineItem mLineItem;
  private int mTotalItem;
  private long mVariantId;
  
  public AutoSubmitCartEditedTimer(ICartApplication paramICartApplication, Context paramContext, Callback paramCallback)
  {
    super(1000L, 1000L);
    this.mICartApplication = paramICartApplication;
    this.mContext = paramContext;
    this.mCallback = paramCallback;
  }
  
  private void addItemToCart()
  {
    if (this.mICartApplication.currentOrder == null)
    {
      Toast.makeText(this.mContext, this.mContext.getString(2131165671), 0).show();
      return;
    }
    this.mICartApplication.getEditingCartInProgress().removeItemFromAddToCartCountdown(Long.valueOf(this.mVariantId));
    this.mICartApplication.getEditingCartInProgress().addItemToAddToCartInProgress(Long.valueOf(this.mVariantId), Integer.valueOf(this.mTotalItem));
    this.mICartApplication.getICartNotification().raiseCurrentOrderSetEvent(this, this.mICartApplication.currentOrder);
    try
    {
      this.mICartApplication.getOrderManager().addItemToCart(this.mVariantId, this.mTotalItem, null, false, new ICartCallback(TAG)
      {
        public void onFailure(Throwable paramAnonymousThrowable)
        {
          if (AutoSubmitCartEditedTimer.this.mCallback != null) {
            AutoSubmitCartEditedTimer.this.mCallback.onFailureFinish(paramAnonymousThrowable);
          }
        }
        
        public void onSuccess(Object paramAnonymousObject)
        {
          if (AutoSubmitCartEditedTimer.this.mCallback != null) {
            AutoSubmitCartEditedTimer.this.mCallback.onSuccessFinish(paramAnonymousObject);
          }
        }
      });
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
  
  private void removeItemFromCart()
  {
    if (this.mLineItem == null) {
      return;
    }
    this.mICartApplication.getEditingCartInProgress().removeItemFromRemoveFromCartCountdown(Long.valueOf(this.mVariantId));
    this.mICartApplication.getEditingCartInProgress().addItemToRemoveFromCartInProgress(this.mLineItem.variantId, Integer.valueOf(0));
    this.mICartApplication.getICartNotification().raiseCurrentOrderSetEvent(this, this.mICartApplication.currentOrder);
    this.mICartApplication.getOrderManager().deleteItemFromTheCart(this.mICartApplication.currentOrder.number, this.mLineItem.remoteId, this.mLineItem.variantId.longValue(), new ICartCallback(TAG)
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        if (AutoSubmitCartEditedTimer.this.mCallback != null) {
          AutoSubmitCartEditedTimer.this.mCallback.onFailureFinish(paramAnonymousThrowable);
        }
      }
      
      public void onSuccess(List<LineItem> paramAnonymousList)
      {
        GAUtils.trackRemoveFromCart(AutoSubmitCartEditedTimer.this.mICartApplication, AutoSubmitCartEditedTimer.this.mLineItem, "Subcategory");
        if (AutoSubmitCartEditedTimer.this.mCallback != null) {
          AutoSubmitCartEditedTimer.this.mCallback.onSuccessFinish(paramAnonymousList);
        }
      }
    });
  }
  
  private void updateItemInCart()
  {
    if (this.mLineItem == null) {
      return;
    }
    this.mICartApplication.getEditingCartInProgress().removeItemFromUpdateItemInCartCountdown(Long.valueOf(this.mVariantId));
    this.mICartApplication.getEditingCartInProgress().addItemToUpdateItemInCartInProgress(Long.valueOf(this.mVariantId), Integer.valueOf(this.mTotalItem));
    this.mICartApplication.getICartNotification().raiseCurrentOrderSetEvent(this, this.mICartApplication.currentOrder);
    try
    {
      this.mICartApplication.getOrderManager().updateItemInTheCart(this.mICartApplication.currentOrder.number, this.mLineItem, this.mTotalItem, null, false, new ICartCallback(TAG)
      {
        public void onFailure(Throwable paramAnonymousThrowable)
        {
          if (AutoSubmitCartEditedTimer.this.mCallback != null) {
            AutoSubmitCartEditedTimer.this.mCallback.onFailureFinish(paramAnonymousThrowable);
          }
        }
        
        public void onSuccess(Object paramAnonymousObject)
        {
          if (AutoSubmitCartEditedTimer.this.mCallback != null) {
            AutoSubmitCartEditedTimer.this.mCallback.onSuccessFinish(paramAnonymousObject);
          }
        }
      });
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
  
  public void onFinish()
  {
    if (this.mContext == null) {
      return;
    }
    if (this.mCallback != null) {
      this.mCallback.onStart();
    }
    switch (this.mLastAction)
    {
    }
    for (;;)
    {
      this.mLastAction = 0;
      this.mTotalItem = 0;
      this.mLineItem = null;
      return;
      addItemToCart();
      continue;
      updateItemInCart();
      continue;
      removeItemFromCart();
    }
  }
  
  public void onTick(long paramLong) {}
  
  public void setAddAction(long paramLong, int paramInt)
  {
    if (this.mLastAction == 3)
    {
      this.mICartApplication.getEditingCartInProgress().removeItemFromRemoveFromCartCountdown(Long.valueOf(paramLong));
      this.mICartApplication.getEditingCartInProgress().updateItemInUpdateItemInCartCountdown(Long.valueOf(paramLong), Integer.valueOf(paramInt));
    }
    for (this.mLastAction = 2;; this.mLastAction = 1)
    {
      this.mVariantId = paramLong;
      this.mTotalItem = paramInt;
      cancel();
      start();
      return;
      this.mICartApplication.getEditingCartInProgress().removeItemFromUpdateItemInCartCountdown(Long.valueOf(paramLong));
      this.mICartApplication.getEditingCartInProgress().updateItemInAddToCartCountdown(Long.valueOf(paramLong), Integer.valueOf(paramInt));
    }
  }
  
  public void setRemoveAction(LineItem paramLineItem, long paramLong)
  {
    if ((paramLineItem == null) || (paramLineItem.variantId == null)) {}
    while (this.mLastAction == 1)
    {
      this.mICartApplication.getEditingCartInProgress().removeItemFromAddToCartCountdown(Long.valueOf(paramLong));
      this.mLastAction = 0;
      cancel();
      return;
      paramLong = paramLineItem.variantId.longValue();
    }
    this.mICartApplication.getEditingCartInProgress().removeItemFromUpdateItemInCartCountdown(Long.valueOf(paramLong));
    this.mLastAction = 3;
    this.mICartApplication.getEditingCartInProgress().updateItemInRemoveFromCartCountdown(Long.valueOf(paramLong), Integer.valueOf(0));
    this.mLineItem = paramLineItem;
    this.mTotalItem = 0;
    cancel();
    start();
  }
  
  public void setUpdateAction(LineItem paramLineItem, long paramLong, int paramInt)
  {
    if (this.mLastAction == 1) {
      this.mICartApplication.getEditingCartInProgress().updateItemInAddToCartCountdown(Long.valueOf(paramLong), Integer.valueOf(paramInt));
    }
    for (this.mLastAction = 1;; this.mLastAction = 2)
    {
      this.mLineItem = paramLineItem;
      this.mVariantId = paramLong;
      this.mTotalItem = paramInt;
      cancel();
      start();
      return;
      this.mICartApplication.getEditingCartInProgress().removeItemFromRemoveFromCartCountdown(Long.valueOf(paramLong));
      this.mICartApplication.getEditingCartInProgress().updateItemInUpdateItemInCartCountdown(Long.valueOf(paramLong), Integer.valueOf(paramInt));
    }
  }
  
  public static abstract interface Callback
  {
    public abstract void onFailureFinish(Throwable paramThrowable);
    
    public abstract void onStart();
    
    public abstract void onSuccessFinish(Object paramObject);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/utils/AutoSubmitCartEditedTimer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */