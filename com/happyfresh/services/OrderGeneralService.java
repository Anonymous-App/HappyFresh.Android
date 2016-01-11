package com.happyfresh.services;

import com.happyfresh.models.Order;
import com.happyfresh.models.OrderResponse;
import com.happyfresh.models.Shipment;
import com.happyfresh.models.SlotResponse;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedInput;

public abstract interface OrderGeneralService
{
  @POST("/feedbacks")
  public abstract void createFeedback(@Body TypedInput paramTypedInput, Callback<Object> paramCallback);
  
  @POST("/orders")
  public abstract void createOrder(Callback<Order> paramCallback);
  
  @GET("/slots/available")
  public abstract void getAvailableSlots(@Query("order_number") String paramString1, @Query("stock_location_id") Long paramLong, @Query("start_date") String paramString2, @Query("end_date") String paramString3, Callback<SlotResponse> paramCallback);
  
  @GET("/orders/mine?q[completed_at_not_null]=1")
  public abstract void getCompletedOrder(@Query("page") Integer paramInteger1, @Query("per_page") Integer paramInteger2, Callback<OrderResponse> paramCallback);
  
  @GET("/orders/mine?q[shipment_state_cont]=delivered&per_page=1")
  public abstract void getLastCompletedOrder(Callback<OrderResponse> paramCallback);
  
  @GET("/orders/mine")
  public abstract void getLastOrder(@Query("page") Integer paramInteger1, @Query("per_page") Integer paramInteger2, Callback<OrderResponse> paramCallback);
  
  @POST("/orders/{orderNumber}/rate")
  public abstract void rateOrder(@Path("orderNumber") String paramString, @Body TypedInput paramTypedInput, Callback<Object> paramCallback);
  
  @POST("/shipments/{shipmentId}/slot")
  public abstract void setSlotToShipment(@Path("shipmentId") Long paramLong, @Body TypedInput paramTypedInput, Callback<Shipment> paramCallback);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/services/OrderGeneralService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */