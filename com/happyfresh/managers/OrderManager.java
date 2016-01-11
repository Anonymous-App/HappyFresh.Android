package com.happyfresh.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.widget.Toast;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.callbacks.ICartRetrofitCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.common.ICartNotification;
import com.happyfresh.common.ICartRestClient;
import com.happyfresh.common.ICartRestError;
import com.happyfresh.models.CreditCard;
import com.happyfresh.models.EditingCartInProgress;
import com.happyfresh.models.LineItem;
import com.happyfresh.models.MultipleLineItem;
import com.happyfresh.models.MultipleLineItemResponse;
import com.happyfresh.models.Order;
import com.happyfresh.models.OrderResponse;
import com.happyfresh.models.PaymentMethodResponse;
import com.happyfresh.models.Rating;
import com.happyfresh.models.RestError;
import com.happyfresh.models.SlotResponse;
import com.happyfresh.models.Variant;
import com.happyfresh.models.payload.AsiaPayload;
import com.happyfresh.services.OrderGeneralService;
import com.happyfresh.services.OrderService;
import com.happyfresh.utils.AccengageTrackerUtils;
import com.happyfresh.utils.AdjustUtils;
import com.happyfresh.utils.GAUtils;
import com.happyfresh.utils.LogUtils;
import com.happyfresh.utils.MixpanelTrackerUtils;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class OrderManager
  extends BaseManager
{
  private static final String TAG = OrderManager.class.getSimpleName();
  private Context mContext;
  public Set<Long> mSyncLineItem = new HashSet();
  
  public OrderManager(ICartApplication paramICartApplication)
  {
    super(paramICartApplication);
    this.mContext = paramICartApplication.getApplicationContext();
  }
  
  private void trackAddToCart(LineItem paramLineItem, boolean paramBoolean)
  {
    AdjustUtils.trackAddToCart(paramLineItem);
    String str = "Product Details";
    if (paramBoolean) {
      str = "Past Orders Details";
    }
    GAUtils.trackAddToCart(this.mICartApplication, paramLineItem, str);
    MixpanelTrackerUtils.trackAddToCart(this.mICartApplication, paramLineItem);
    AccengageTrackerUtils.trackAddToCart(this.mICartApplication, paramLineItem);
  }
  
  private void trackMultipleAddToCart(MultipleLineItemResponse paramMultipleLineItemResponse, String paramString)
  {
    if ((paramMultipleLineItemResponse.order != null) && (paramMultipleLineItemResponse.order.lineItems != null) && (this.mICartApplication.currentOrder != null) && (this.mICartApplication.currentOrder.lineItems != null))
    {
      Iterator localIterator1 = this.mICartApplication.currentOrder.lineItems.iterator();
      label244:
      while (localIterator1.hasNext())
      {
        LineItem localLineItem1 = (LineItem)localIterator1.next();
        int j = 0;
        Iterator localIterator2 = paramMultipleLineItemResponse.order.lineItems.iterator();
        LineItem localLineItem2;
        do
        {
          i = j;
          if (!localIterator2.hasNext()) {
            break;
          }
          localLineItem2 = (LineItem)localIterator2.next();
        } while (localLineItem2.remoteId != localLineItem1.remoteId);
        int i = 1;
        if (localLineItem2.quantity == localLineItem1.quantity) {}
        for (;;)
        {
          if (i != 0) {
            break label244;
          }
          LogUtils.LOG("tracker >> remove from cart: " + localLineItem1.variant.name);
          GAUtils.trackRemoveFromCart(this.mICartApplication, localLineItem1, paramString);
          break;
          if (localLineItem2.quantity.intValue() > localLineItem1.quantity.intValue())
          {
            GAUtils.trackAddToCart(this.mICartApplication, localLineItem2, paramString);
            AdjustUtils.trackAddToCart(localLineItem2);
          }
          else
          {
            GAUtils.trackRemoveFromCart(this.mICartApplication, localLineItem2, paramString);
          }
        }
      }
    }
  }
  
  public void addItemToCart(long paramLong1, long paramLong2, ICartCallback paramICartCallback)
    throws JSONException, UnsupportedEncodingException
  {
    addItemToCart(paramLong1, paramLong2, null, paramICartCallback);
  }
  
  public void addItemToCart(final long paramLong1, long paramLong2, Long paramLong, ICartCallback paramICartCallback)
    throws JSONException, UnsupportedEncodingException
  {
    if (this.mICartApplication.currentOrder == null) {}
    String str;
    long l;
    do
    {
      return;
      str = this.mICartApplication.currentOrder.number;
      l = this.mICartApplication.getStockLocationId();
    } while (this.mSyncLineItem.contains(Long.valueOf(paramLong1)));
    this.mSyncLineItem.add(Long.valueOf(paramLong1));
    JSONObject localJSONObject1 = new JSONObject();
    localJSONObject1.put("variant_id", paramLong1);
    localJSONObject1.put("quantity", paramLong2);
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("line_item", localJSONObject1);
    localJSONObject2.put("stock_location_id", Long.valueOf(l));
    if (paramLong != null) {
      localJSONObject2.put("search_id", paramLong);
    }
    paramLong = new TypedByteArray("application/json", localJSONObject2.toString().getBytes("UTF-8"));
    this.mICartRestClient.getOrderService().newAddItemToCart(str, paramLong, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        OrderManager.this.releaseItemLock(paramLong1);
        if (this.val$callback != null) {
          this.val$callback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(Order paramAnonymousOrder, Response paramAnonymousResponse)
      {
        if (paramAnonymousOrder != null)
        {
          OrderManager.this.mICartApplication.currentOrder = paramAnonymousOrder;
          OrderManager.this.mICartApplication.getICartNotification().raiseCurrentOrderSetEvent(this, OrderManager.this.mICartApplication.currentOrder);
          paramAnonymousOrder = OrderManager.this.mICartApplication.currentOrder.getLineItem(paramLong1);
          if ((paramAnonymousOrder != null) && (this.val$callback != null)) {
            this.val$callback.onSuccess(paramAnonymousOrder);
          }
        }
      }
    });
  }
  
  public void addItemToCart(long paramLong1, long paramLong2, Long paramLong, boolean paramBoolean)
    throws JSONException, UnsupportedEncodingException
  {
    addItemToCart(paramLong1, paramLong2, paramLong, paramBoolean, null);
  }
  
  public void addItemToCart(final long paramLong1, long paramLong2, Long paramLong, boolean paramBoolean, final ICartCallback paramICartCallback)
    throws JSONException, UnsupportedEncodingException
  {
    String str = this.mICartApplication.currentOrder.number;
    long l = this.mICartApplication.getStockLocationId();
    final boolean bool = this.mICartApplication.currentOrder.isEmptyCart();
    JSONObject localJSONObject1 = new JSONObject();
    localJSONObject1.put("variant_id", paramLong1);
    localJSONObject1.put("quantity", paramLong2);
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("line_item", localJSONObject1);
    localJSONObject2.put("stock_location_id", Long.valueOf(l));
    if (paramLong != null) {
      localJSONObject2.put("search_id", paramLong);
    }
    paramLong = new TypedByteArray("application/json", localJSONObject2.toString().getBytes("UTF-8"));
    this.mICartRestClient.getOrderService().newAddItemToCart(str, paramLong, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        OrderManager.this.mICartApplication.getEditingCartInProgress().removeItemFromAddToCartInProgress(Long.valueOf(paramLong1));
        OrderManager.this.mICartApplication.getICartNotification().raiseCurrentOrderSetEvent(this, OrderManager.this.mICartApplication.currentOrder);
        ICartRestError.showMessage(OrderManager.this.mContext, paramAnonymousRetrofitError);
        if (this.val$callback != null) {
          this.val$callback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(Order paramAnonymousOrder, Response paramAnonymousResponse)
      {
        if (paramAnonymousOrder != null)
        {
          OrderManager.this.mICartApplication.getEditingCartInProgress().removeItemFromAddToCartInProgress(Long.valueOf(paramLong1));
          OrderManager.this.mICartApplication.currentOrder = paramAnonymousOrder;
          OrderManager.this.mICartApplication.getICartNotification().raiseCurrentOrderSetEvent(this, OrderManager.this.mICartApplication.currentOrder);
          paramAnonymousResponse = OrderManager.this.mICartApplication.currentOrder.getLineItem(paramLong1);
          if (paramAnonymousResponse != null) {
            OrderManager.this.trackAddToCart(paramAnonymousResponse, bool);
          }
          if (paramICartCallback) {
            OrderManager.this.mICartApplication.getSharedPreferencesManager().saveLastFirstTimeAddToCart();
          }
          if (this.val$callback != null) {
            this.val$callback.onSuccess(paramAnonymousOrder);
          }
        }
      }
    });
  }
  
  public void addMultipleItemToCart(List<MultipleLineItem> paramList, final ICartCallback paramICartCallback)
    throws JSONException, UnsupportedEncodingException
  {
    String str = this.mICartApplication.currentOrder.number;
    long l = this.mICartApplication.getStockLocationId();
    final boolean bool = this.mICartApplication.currentOrder.isEmptyCart();
    JSONArray localJSONArray = new JSONArray();
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      MultipleLineItem localMultipleLineItem = (MultipleLineItem)paramList.next();
      JSONObject localJSONObject = new JSONObject();
      localJSONObject.put("variant_id", localMultipleLineItem.variantId);
      localJSONObject.put("quantity", localMultipleLineItem.quantity);
      localJSONArray.put(localJSONObject);
    }
    paramList = new JSONObject();
    paramList.put("line_items", localJSONArray);
    paramList.put("stock_location_id", Long.valueOf(l));
    paramList = new TypedByteArray("application/json", paramList.toString().getBytes("UTF-8"));
    this.mICartRestClient.getOrderService().newAddMultipleItemToCart(str, paramList, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        ICartRestError.showMessage(OrderManager.this.mContext, paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(MultipleLineItemResponse paramAnonymousMultipleLineItemResponse, Response paramAnonymousResponse)
      {
        if (paramAnonymousMultipleLineItemResponse != null)
        {
          OrderManager.this.mICartApplication.currentOrder = paramAnonymousMultipleLineItemResponse.order;
          OrderManager.this.mICartApplication.getICartNotification().raiseCurrentOrderSetEvent(this, OrderManager.this.mICartApplication.currentOrder);
          if (bool) {
            OrderManager.this.mICartApplication.getSharedPreferencesManager().saveLastFirstTimeAddToCart();
          }
          if (paramICartCallback != null) {
            paramICartCallback.onSuccess(paramAnonymousMultipleLineItemResponse);
          }
        }
      }
    });
  }
  
  public void applyCouponCode(String paramString1, String paramString2, final ICartCallback<Order> paramICartCallback)
    throws JSONException, UnsupportedEncodingException
  {
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put("coupon_code", paramString2);
    paramString2 = new JSONObject();
    paramString2.put("order", localJSONObject);
    paramString2 = new TypedByteArray("application/json", paramString2.toString().getBytes("UTF-8"));
    this.mICartRestClient.getOrderService().updateCheckout(paramString1, paramString2, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(Order paramAnonymousOrder, Response paramAnonymousResponse)
      {
        OrderManager.this.mICartApplication.currentOrder = paramAnonymousOrder;
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousOrder);
        }
      }
    });
  }
  
  public void clearItemLock()
  {
    this.mSyncLineItem.clear();
  }
  
  public void clearReplacements(String paramString, final ICartCallback<Boolean> paramICartCallback)
  {
    this.mICartRestClient.getOrderService().emptyReplacement(paramString, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(Object paramAnonymousObject, Response paramAnonymousResponse)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(Boolean.valueOf(true));
        }
      }
    });
  }
  
  public void createFeedback(String paramString, ICartCallback<Object> paramICartCallback)
    throws JSONException, UnsupportedEncodingException
  {
    createFeedback(null, paramString, paramICartCallback);
  }
  
  public void createFeedback(List<Integer> paramList, String paramString, final ICartCallback<Object> paramICartCallback)
    throws JSONException, UnsupportedEncodingException
  {
    JSONArray localJSONArray = new JSONArray();
    if (paramList != null)
    {
      localObject = paramList.iterator();
      while (((Iterator)localObject).hasNext()) {
        localJSONArray.put((Integer)((Iterator)localObject).next());
      }
    }
    Object localObject = new JSONObject();
    if (paramList != null) {
      ((JSONObject)localObject).put("need_improvement", localJSONArray);
    }
    ((JSONObject)localObject).put("comment", paramString);
    paramList = new TypedByteArray("application/json", ((JSONObject)localObject).toString().getBytes("UTF-8"));
    this.mICartRestClient.getOrderGeneralService().createFeedback(paramList, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(Object paramAnonymousObject, Response paramAnonymousResponse)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousObject);
        }
      }
    });
  }
  
  public void createOrder(final ICartCallback paramICartCallback)
  {
    this.mICartApplication.getSharedPreferencesManager().saveOverallReplacementOption("by_shopper", "by_shopper");
    this.mICartRestClient.getOrderGeneralService().createOrder(new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(Order paramAnonymousOrder, Response paramAnonymousResponse)
      {
        paramAnonymousResponse = PreferenceManager.getDefaultSharedPreferences(OrderManager.this.mICartApplication).edit();
        paramAnonymousResponse.putString("ICartConstant.KEYS.ORDER_NUMBER", paramAnonymousOrder.number);
        paramAnonymousResponse.putString("ICartConstant.KEYS.ORDER_TOKEN", paramAnonymousOrder.token);
        paramAnonymousResponse.commit();
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousOrder);
        }
      }
    });
  }
  
  public void deleteItemFromTheCart(String paramString, long paramLong1, final long paramLong2, ICartCallback paramICartCallback)
  {
    this.mICartRestClient.getOrderService().newDeleteItemFromCart(paramString, Long.valueOf(paramLong1), new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        OrderManager.this.mICartApplication.getEditingCartInProgress().removeItemFromRemoveFromCartInProgress(Long.valueOf(paramLong2));
        OrderManager.this.mICartApplication.getICartNotification().raiseCurrentOrderSetEvent(this, OrderManager.this.mICartApplication.currentOrder);
        if (this.val$callback != null) {
          this.val$callback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(Order paramAnonymousOrder, Response paramAnonymousResponse)
      {
        if (paramAnonymousOrder != null)
        {
          OrderManager.this.mICartApplication.getEditingCartInProgress().removeItemFromRemoveFromCartInProgress(Long.valueOf(paramLong2));
          OrderManager.this.mICartApplication.currentOrder = paramAnonymousOrder;
          OrderManager.this.mICartApplication.getICartNotification().raiseCurrentOrderSetEvent(this, OrderManager.this.mICartApplication.currentOrder);
          if (this.val$callback != null) {
            this.val$callback.onSuccess(paramAnonymousOrder.lineItems);
          }
        }
      }
    });
  }
  
  public void emptyCart(String paramString, final ICartCallback paramICartCallback)
  {
    this.mICartApplication.getSharedPreferencesManager().saveOverallReplacementOption("by_shopper", "by_shopper");
    this.mICartRestClient.getOrderService().emptyCart(paramString, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(Order paramAnonymousOrder, Response paramAnonymousResponse)
      {
        OrderManager.this.mICartApplication.getSharedPreferencesManager().resetLastFirstTimeAddToCart();
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousOrder);
        }
      }
    });
  }
  
  public boolean getAvailablePayments(final ICartCallback<PaymentMethodResponse> paramICartCallback)
  {
    OrderService localOrderService = this.mICartRestClient.getOrderService();
    if (this.mICartApplication.currentOrder == null) {
      return false;
    }
    localOrderService.getAvailablePayments(this.mICartApplication.currentOrder.number, this.mICartApplication.currentOrder.token, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(PaymentMethodResponse paramAnonymousPaymentMethodResponse, Response paramAnonymousResponse)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousPaymentMethodResponse);
        }
      }
    });
    return true;
  }
  
  public void getAvailableSlot(Date paramDate1, Date paramDate2, final ICartCallback<SlotResponse> paramICartCallback)
  {
    OrderGeneralService localOrderGeneralService = this.mICartRestClient.getOrderGeneralService();
    SharedPreferencesManager localSharedPreferencesManager = this.mICartApplication.getSharedPreferencesManager();
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-d H:m:sZ");
    paramDate1 = localSimpleDateFormat.format(paramDate1);
    paramDate2 = localSimpleDateFormat.format(paramDate2);
    localOrderGeneralService.getAvailableSlots(localSharedPreferencesManager.getOrderNumber(), Long.valueOf(localSharedPreferencesManager.getStockLocationId()), paramDate1, paramDate2, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(SlotResponse paramAnonymousSlotResponse, Response paramAnonymousResponse)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousSlotResponse);
        }
      }
    });
  }
  
  public void getLastCompletedOrder(final ICartCallback paramICartCallback)
  {
    this.mICartRestClient.getOrderGeneralService().getLastCompletedOrder(new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(OrderResponse paramAnonymousOrderResponse, Response paramAnonymousResponse)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousOrderResponse);
        }
      }
    });
  }
  
  public void getMyCompletedOrder(Integer paramInteger1, Integer paramInteger2, final ICartCallback paramICartCallback)
  {
    this.mICartRestClient.getOrderGeneralService().getCompletedOrder(paramInteger1, paramInteger2, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(OrderResponse paramAnonymousOrderResponse, Response paramAnonymousResponse)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousOrderResponse);
        }
      }
    });
  }
  
  public void getMyOrder(Integer paramInteger1, Integer paramInteger2, final ICartCallback paramICartCallback)
  {
    this.mICartRestClient.getOrderGeneralService().getLastOrder(paramInteger1, paramInteger2, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(OrderResponse paramAnonymousOrderResponse, Response paramAnonymousResponse)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousOrderResponse);
        }
      }
    });
  }
  
  public void getOrder(String paramString, final ICartCallback paramICartCallback)
  {
    this.mICartRestClient.getOrderService().getOrder(paramString, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(Order paramAnonymousOrder, Response paramAnonymousResponse)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousOrder);
        }
      }
    });
  }
  
  public void getPaymentPayload(String paramString, final ICartCallback<AsiaPayload> paramICartCallback)
  {
    this.mICartRestClient.getOrderService().getPaymentPayload(paramString, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(AsiaPayload paramAnonymousAsiaPayload, Response paramAnonymousResponse)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousAsiaPayload);
        }
      }
    });
  }
  
  public void next(String paramString, final ICartCallback<Order> paramICartCallback)
  {
    this.mICartRestClient.getOrderService().next(paramString, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(Order paramAnonymousOrder, Response paramAnonymousResponse)
      {
        OrderManager.this.mICartApplication.currentOrder = paramAnonymousOrder;
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousOrder);
        }
      }
    });
  }
  
  public void rateOrder(String paramString, Rating paramRating, final ICartCallback paramICartCallback)
    throws JSONException, UnsupportedEncodingException
  {
    OrderGeneralService localOrderGeneralService = this.mICartRestClient.getOrderGeneralService();
    JSONObject localJSONObject = new JSONObject();
    if (paramRating.stars != null) {
      localJSONObject.put("stars", paramRating.stars);
    }
    if (paramRating.needImprovement.size() > 0)
    {
      JSONArray localJSONArray = new JSONArray();
      Iterator localIterator = paramRating.needImprovement.iterator();
      while (localIterator.hasNext()) {
        localJSONArray.put((Integer)localIterator.next());
      }
      localJSONObject.put("need_improvement", localJSONArray);
    }
    if (paramRating.comment != null) {
      localJSONObject.put("comment", paramRating.comment);
    }
    localOrderGeneralService.rateOrder(paramString, new TypedByteArray("application/json", localJSONObject.toString().getBytes("UTF-8")), new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(Object paramAnonymousObject, Response paramAnonymousResponse)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousObject);
        }
      }
    });
  }
  
  public void recreateOrder(String paramString, final ICartCallback paramICartCallback)
  {
    this.mICartRestClient.getOrderService().recreateOrder(paramString, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(MultipleLineItemResponse paramAnonymousMultipleLineItemResponse, Response paramAnonymousResponse)
      {
        if (paramAnonymousMultipleLineItemResponse != null)
        {
          OrderManager.this.mICartApplication.currentOrder = paramAnonymousMultipleLineItemResponse.order;
          OrderManager.this.mICartApplication.getICartNotification().raiseCurrentOrderSetEvent(this, OrderManager.this.mICartApplication.currentOrder);
          if (paramICartCallback != null) {
            paramICartCallback.onSuccess(paramAnonymousMultipleLineItemResponse);
          }
        }
      }
    });
  }
  
  public void refreshCart(String paramString, final ICartCallback paramICartCallback)
  {
    this.mICartRestClient.getOrderService().refreshCart(paramString, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(Order paramAnonymousOrder, Response paramAnonymousResponse)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousOrder);
        }
      }
    });
  }
  
  public void releaseItemLock(long paramLong)
  {
    if (this.mSyncLineItem.contains(Long.valueOf(paramLong))) {
      this.mSyncLineItem.remove(Long.valueOf(paramLong));
    }
  }
  
  public void updateAddressOnCheckout(String paramString, Long paramLong1, Long paramLong2, final ICartCallback<Order> paramICartCallback)
    throws JSONException, UnsupportedEncodingException
  {
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put("shipping_address_id", paramLong1);
    localJSONObject.put("billing_address_id", paramLong2);
    paramLong1 = new JSONObject();
    paramLong1.put("user", localJSONObject);
    paramLong1.put("state", "address");
    paramLong1 = new TypedByteArray("application/json", paramLong1.toString().getBytes("UTF-8"));
    this.mICartRestClient.getOrderService().updateCheckout(paramString, paramLong1, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(Order paramAnonymousOrder, Response paramAnonymousResponse)
      {
        OrderManager.this.mICartApplication.currentOrder = paramAnonymousOrder;
        OrderManager.this.mICartApplication.getICartNotification().raiseUpdateAddressOnCheckoutEvent(OrderManager.this, paramAnonymousOrder);
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousOrder);
        }
      }
    });
  }
  
  public void updateAddressOnCheckout(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, Long paramLong1, Long paramLong2, Long paramLong3, String paramString6, String paramString7, String paramString8, final ICartCallback<Order> paramICartCallback)
    throws JSONException, UnsupportedEncodingException
  {
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put("firstname", paramString2);
    localJSONObject.put("lastname", paramString3);
    localJSONObject.put("address1", paramString4);
    localJSONObject.put("address2", paramString5);
    localJSONObject.put("city", paramString6);
    localJSONObject.put("country_id", paramLong1);
    localJSONObject.put("state_id", paramLong2);
    localJSONObject.put("sub_district_id", paramLong3);
    localJSONObject.put("zipcode", paramString7);
    localJSONObject.put("phone", paramString8);
    localJSONObject.put("primary", true);
    localJSONObject.put("address_type", 0);
    paramString2 = new JSONArray();
    paramString2.put(localJSONObject);
    paramString3 = new JSONObject();
    paramString3.put("shipping_addresses_attributes", paramString2);
    paramString2 = new JSONObject();
    paramString2.put("user", paramString3);
    paramString2 = new TypedByteArray("application/json", paramString2.toString().getBytes("UTF-8"));
    this.mICartRestClient.getOrderService().updateCheckout(paramString1, paramString2, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(Order paramAnonymousOrder, Response paramAnonymousResponse)
      {
        OrderManager.this.mICartApplication.currentOrder = paramAnonymousOrder;
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousOrder);
        }
      }
    });
  }
  
  public void updateItemInTheCart(String paramString, LineItem paramLineItem, long paramLong, Long paramLong1, boolean paramBoolean)
    throws JSONException, UnsupportedEncodingException
  {
    updateItemInTheCart(paramString, paramLineItem, paramLong, paramLong1, paramBoolean, null);
  }
  
  public void updateItemInTheCart(String paramString, final LineItem paramLineItem, long paramLong, Long paramLong1, final boolean paramBoolean, final ICartCallback paramICartCallback)
    throws JSONException, UnsupportedEncodingException
  {
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put("quantity", paramLong);
    if (paramLong1 != null) {
      localJSONObject.put("search_id", paramLong1);
    }
    paramLong1 = new TypedByteArray("application/json", localJSONObject.toString().getBytes("UTF-8"));
    this.mICartRestClient.getOrderService().newUpdateItemInTheCart(paramString, Long.valueOf(paramLineItem.remoteId), paramLong1, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        OrderManager.this.mICartApplication.getEditingCartInProgress().removeItemFromUpdateItemInCartInProgress(paramLineItem.variantId);
        OrderManager.this.mICartApplication.getICartNotification().raiseCurrentOrderSetEvent(this, OrderManager.this.mICartApplication.currentOrder);
        ICartRestError.showMessage(OrderManager.this.mContext, paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(Order paramAnonymousOrder, Response paramAnonymousResponse)
      {
        if (paramAnonymousOrder != null)
        {
          OrderManager.this.mICartApplication.getEditingCartInProgress().removeItemFromUpdateItemInCartInProgress(paramLineItem.variantId);
          OrderManager.this.mICartApplication.currentOrder = paramAnonymousOrder;
          OrderManager.this.mICartApplication.getICartNotification().raiseCurrentOrderSetEvent(this, OrderManager.this.mICartApplication.currentOrder);
          if (OrderManager.this.mICartApplication.currentOrder.getLineItem(paramLineItem.remoteId) != null) {
            OrderManager.this.trackAddToCart(paramLineItem, paramBoolean);
          }
          if (paramICartCallback != null) {
            paramICartCallback.onSuccess(paramAnonymousOrder);
          }
        }
      }
    });
  }
  
  public void updateMultipleItemToCart(List<MultipleLineItem> paramList, final String paramString, final ICartCallback paramICartCallback)
    throws JSONException, UnsupportedEncodingException
  {
    String str = this.mICartApplication.currentOrder.number;
    long l = this.mICartApplication.getStockLocationId();
    JSONArray localJSONArray = new JSONArray();
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      MultipleLineItem localMultipleLineItem = (MultipleLineItem)paramList.next();
      JSONObject localJSONObject = new JSONObject();
      localJSONObject.put("variant_id", localMultipleLineItem.variantId);
      localJSONObject.put("quantity", localMultipleLineItem.quantity);
      localJSONArray.put(localJSONObject);
    }
    paramList = new JSONObject();
    paramList.put("line_items", localJSONArray);
    paramList.put("stock_location_id", Long.valueOf(l));
    paramList = new TypedByteArray("application/json", paramList.toString().getBytes("UTF-8"));
    this.mICartRestClient.getOrderService().updateMultipleItemToCart(str, paramList, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        ICartRestError.showMessage(OrderManager.this.mContext, paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(MultipleLineItemResponse paramAnonymousMultipleLineItemResponse, Response paramAnonymousResponse)
      {
        if (paramAnonymousMultipleLineItemResponse != null)
        {
          OrderManager.this.trackMultipleAddToCart(paramAnonymousMultipleLineItemResponse, paramString);
          OrderManager.this.mICartApplication.currentOrder = paramAnonymousMultipleLineItemResponse.order;
          OrderManager.this.mICartApplication.getICartNotification().raiseCurrentOrderSetEvent(this, OrderManager.this.mICartApplication.currentOrder);
          if (paramICartCallback != null) {
            paramICartCallback.onSuccess(paramAnonymousMultipleLineItemResponse);
          }
        }
      }
    });
  }
  
  public void updatePaymentOnCheckout(String paramString1, String paramString2, CreditCard paramCreditCard, final ICartCallback<Order> paramICartCallback)
    throws JSONException, UnsupportedEncodingException
  {
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put("payment_method_id", paramString2);
    Object localObject = new JSONArray();
    ((JSONArray)localObject).put(localJSONObject);
    localJSONObject = new JSONObject();
    localJSONObject.put("payments_attributes", localObject);
    localObject = new JSONObject();
    ((JSONObject)localObject).put("number", paramCreditCard.number);
    ((JSONObject)localObject).put("month", paramCreditCard.month);
    ((JSONObject)localObject).put("year", paramCreditCard.year);
    ((JSONObject)localObject).put("verification_value", paramCreditCard.verificationValue);
    ((JSONObject)localObject).put("name", paramCreditCard.name);
    paramCreditCard = new JSONObject();
    paramCreditCard.put(paramString2, localObject);
    paramString2 = new JSONObject();
    paramString2.put("order", localJSONObject);
    paramString2.put("payment_source", paramCreditCard);
    paramString2 = new TypedByteArray("application/json", paramString2.toString().getBytes("UTF-8"));
    this.mICartRestClient.getOrderService().updateCheckout(paramString1, paramString2, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(Order paramAnonymousOrder, Response paramAnonymousResponse)
      {
        OrderManager.this.mICartApplication.currentOrder = paramAnonymousOrder;
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousOrder);
        }
      }
    });
  }
  
  public void updateReplacements(List<LineItem> paramList, final ICartCallback paramICartCallback)
    throws JSONException, UnsupportedEncodingException
  {
    String str = this.mICartApplication.currentOrder.number;
    long l = this.mICartApplication.getStockLocationId();
    JSONArray localJSONArray = new JSONArray();
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      LineItem localLineItem = (LineItem)paramList.next();
      JSONObject localJSONObject = new JSONObject();
      localJSONObject.put("variant_id", localLineItem.variantId);
      localJSONObject.put("replacement_type", localLineItem.replacementOption);
      localJSONArray.put(localJSONObject);
    }
    paramList = new JSONObject();
    paramList.put("line_items", localJSONArray);
    paramList.put("stock_location_id", Long.valueOf(l));
    paramList = new TypedByteArray("application/json", paramList.toString().getBytes("UTF-8"));
    this.mICartRestClient.getOrderService().updateReplacements(str, paramList, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        ICartRestError.showMessage(OrderManager.this.mContext, paramAnonymousRetrofitError);
        if (paramICartCallback != null) {
          paramICartCallback.onFailure(paramAnonymousRetrofitError);
        }
      }
      
      public void success(Object paramAnonymousObject, Response paramAnonymousResponse)
      {
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousObject);
        }
      }
    });
  }
  
  public void updateSlotOnCheckout(String paramString, Long paramLong1, Long paramLong2, Long paramLong3, boolean paramBoolean, final ICartCallback<Order> paramICartCallback)
    throws JSONException, UnsupportedEncodingException
  {
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put("selected_shipping_rate_id", paramLong1);
    localJSONObject.put("slot_id", paramLong2);
    localJSONObject.put("id", paramLong3);
    paramLong1 = new JSONObject();
    paramLong1.put("0", localJSONObject);
    paramLong2 = new JSONObject();
    paramLong2.put("shipments_attributes", paramLong1);
    paramLong1 = new JSONObject();
    paramLong1.put("order", paramLong2);
    if (paramBoolean) {
      paramLong1.put("state", "delivery");
    }
    paramLong1 = new TypedByteArray("application/json", paramLong1.toString().getBytes("UTF-8"));
    this.mICartRestClient.getOrderService().updateCheckout(paramString, paramLong1, new ICartRetrofitCallback(this.mICartApplication)
    {
      public void failure(RetrofitError paramAnonymousRetrofitError)
      {
        super.failure(paramAnonymousRetrofitError);
        RestError localRestError;
        String str;
        if (paramAnonymousRetrofitError != null)
        {
          localRestError = RestError.getRestError(paramAnonymousRetrofitError);
          if (localRestError != null)
          {
            str = OrderManager.this.mICartApplication.getString(2131165625);
            if (TextUtils.isEmpty(localRestError.message)) {
              break label77;
            }
            str = localRestError.message;
          }
        }
        for (;;)
        {
          Toast.makeText(OrderManager.this.mICartApplication, str, 1).show();
          if (paramICartCallback != null) {
            paramICartCallback.onFailure(paramAnonymousRetrofitError);
          }
          return;
          label77:
          if (!TextUtils.isEmpty(localRestError.exception)) {
            str = localRestError.exception;
          }
        }
      }
      
      public void success(Order paramAnonymousOrder, Response paramAnonymousResponse)
      {
        OrderManager.this.mICartApplication.currentOrder = paramAnonymousOrder;
        if (paramICartCallback != null) {
          paramICartCallback.onSuccess(paramAnonymousOrder);
        }
      }
    });
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/managers/OrderManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */