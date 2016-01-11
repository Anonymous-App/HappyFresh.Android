package com.happyfresh.services;

import com.happyfresh.callbacks.ICartRetrofitCallback;
import com.happyfresh.models.LineItem;
import com.happyfresh.models.MultipleLineItemResponse;
import com.happyfresh.models.Order;
import com.happyfresh.models.PaymentMethodResponse;
import com.happyfresh.models.payload.AsiaPayload;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedInput;

public abstract interface OrderService
{
  @POST("/orders/{orderNumber}/line_items")
  public abstract void addItemToCart(@Path("orderNumber") String paramString, @Body TypedInput paramTypedInput, Callback<LineItem> paramCallback);
  
  @POST("/orders/{orderNumber}/line_items/add_multiple")
  public abstract void addMultipleItemToCart(@Path("orderNumber") String paramString, @Body TypedInput paramTypedInput, Callback<MultipleLineItemResponse> paramCallback);
  
  @DELETE("/orders/{orderNumber}/line_items/{lineItemId}")
  public abstract void deleteItemFromCart(@Path("orderNumber") String paramString, @Path("lineItemId") Long paramLong, Callback<LineItem> paramCallback);
  
  @PUT("/orders/{orderNumber}/empty")
  public abstract void emptyCart(@Path("orderNumber") String paramString, Callback<Order> paramCallback);
  
  @PUT("/orders/{orderNumber}/replacements/empty")
  public abstract void emptyReplacement(@Path("orderNumber") String paramString, ICartRetrofitCallback<Object> paramICartRetrofitCallback);
  
  @GET("/payments/available")
  public abstract void getAvailablePayments(@Query("order_number") String paramString1, @Query("order_token") String paramString2, Callback<PaymentMethodResponse> paramCallback);
  
  @GET("/orders/{orderNumber}")
  public abstract void getOrder(@Path("orderNumber") String paramString, Callback<Order> paramCallback);
  
  @GET("/payments/payload")
  public abstract void getPaymentPayload(@Query("order_number") String paramString, Callback<AsiaPayload> paramCallback);
  
  @POST("/v2/orders/{orderNumber}/line_items")
  public abstract void newAddItemToCart(@Path("orderNumber") String paramString, @Body TypedInput paramTypedInput, Callback<Order> paramCallback);
  
  @POST("/v2/orders/{orderNumber}/line_items/add_multiple")
  public abstract void newAddMultipleItemToCart(@Path("orderNumber") String paramString, @Body TypedInput paramTypedInput, Callback<MultipleLineItemResponse> paramCallback);
  
  @DELETE("/v2/orders/{orderNumber}/line_items/{lineItemId}")
  public abstract void newDeleteItemFromCart(@Path("orderNumber") String paramString, @Path("lineItemId") Long paramLong, Callback<Order> paramCallback);
  
  @PUT("/v2/orders/{orderNumber}/line_items/{lineItemId}")
  public abstract void newUpdateItemInTheCart(@Path("orderNumber") String paramString, @Path("lineItemId") Long paramLong, @Body TypedInput paramTypedInput, Callback<Order> paramCallback);
  
  @PUT("/checkouts/{orderNumber}/next")
  public abstract void next(@Path("orderNumber") String paramString, Callback<Order> paramCallback);
  
  @PUT("/orders/{orderNumber}/recreate")
  public abstract void recreateOrder(@Path("orderNumber") String paramString, Callback<MultipleLineItemResponse> paramCallback);
  
  @PUT("/orders/{orderNumber}/refresh")
  public abstract void refreshCart(@Path("orderNumber") String paramString, Callback<Order> paramCallback);
  
  @PUT("/checkouts/{orderNumber}")
  public abstract void updateCheckout(@Path("orderNumber") String paramString, @Body TypedInput paramTypedInput, Callback<Order> paramCallback);
  
  @PUT("/orders/{orderNumber}/line_items/{lineItemId}")
  public abstract void updateItemInTheCart(@Path("orderNumber") String paramString, @Path("lineItemId") Long paramLong, @Body TypedInput paramTypedInput, Callback<LineItem> paramCallback);
  
  @POST("/orders/{orderNumber}/line_items/update_multiple")
  public abstract void updateMultipleItemToCart(@Path("orderNumber") String paramString, @Body TypedInput paramTypedInput, Callback<MultipleLineItemResponse> paramCallback);
  
  @POST("/orders/{orderNumber}/line_items/update_replacements")
  public abstract void updateReplacements(@Path("orderNumber") String paramString, @Body TypedInput paramTypedInput, Callback<Object> paramCallback);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/services/OrderService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */