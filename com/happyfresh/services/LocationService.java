package com.happyfresh.services;

import com.happyfresh.models.StockLocation;
import com.happyfresh.models.StockLocationResponse;
import com.happyfresh.models.SubDistrict;
import com.happyfresh.models.SubDistrictResponse;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedInput;

public abstract interface LocationService
{
  @GET("/stock_locations/nearby")
  public abstract void getNearbyStockLocations(@Query("lat") Double paramDouble1, @Query("lon") Double paramDouble2, @Query("zipcode") String paramString1, @Query("skip_reverse_geo") boolean paramBoolean, @Query("sublocality") String paramString2, @Query("page") Integer paramInteger1, @Query("per_page") Integer paramInteger2, @Query("get_next_available_slot") Integer paramInteger3, Callback<StockLocationResponse> paramCallback);
  
  @GET("/supplier/{supplier_id}/nearest_stock_location")
  public abstract void getNearestStockLocations(@Path("supplier_id") Long paramLong1, @Query("lat") Double paramDouble1, @Query("lon") Double paramDouble2, @Query("zipcode") String paramString1, @Query("sublocality") String paramString2, @Query("sub_district_id") Long paramLong2, Callback<StockLocationResponse> paramCallback);
  
  @GET("/stock_locations/{stock_location_id}")
  public abstract void getSingleStockLocations(@Path("stock_location_id") Long paramLong, Callback<StockLocation> paramCallback);
  
  @GET("/sub_districts/{sub_district_id}/stock_locations")
  public abstract void getStockLocations(@Path("sub_district_id") Long paramLong, @Query("page") Integer paramInteger1, @Query("per_page") Integer paramInteger2, @Query("get_next_available_slot") Integer paramInteger3, Callback<StockLocationResponse> paramCallback);
  
  @GET("/stock_locations")
  public abstract void getStockLocationsByZipCode(@Query("zipcode") String paramString, @Query("page") Integer paramInteger1, @Query("per_page") Integer paramInteger2, Callback<StockLocationResponse> paramCallback);
  
  @GET("/sub_districts/{sub_district_id}")
  public abstract void getSubDistrict(@Path("sub_district_id") Long paramLong, Callback<SubDistrict> paramCallback);
  
  @GET("/location_suggestions")
  public abstract void getSuggestedLocations(@Query("q") String paramString, @Query("page") Integer paramInteger1, @Query("per_page") Integer paramInteger2, Callback<SubDistrictResponse> paramCallback);
  
  @POST("/location_requests")
  public abstract void locationRequest(@Body TypedInput paramTypedInput, Callback<Object> paramCallback);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/services/LocationService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */