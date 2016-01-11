package com.happyfresh.services;

import com.happyfresh.models.RecommendedListResponse;
import com.happyfresh.models.ShoppingListItem;
import com.happyfresh.models.ShoppingListResponse;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedInput;

public abstract interface ShoppingListService
{
  @POST("/shopping_lists/{shopping_list_id}/items")
  public abstract void addShoppingListItem(@Path("shopping_list_id") Long paramLong, @Body TypedInput paramTypedInput, Callback<ShoppingListItem> paramCallback);
  
  @DELETE("/shopping_lists/{shopping_list_id}/items")
  public abstract void deleteShoppingListItem(@Path("shopping_list_id") Long paramLong1, @Query("variant_id") Long paramLong2, Callback<Object> paramCallback);
  
  @GET("/shopping_lists/favorite")
  public abstract void getFavoriteList(@Query("stock_location_id") Long paramLong, Callback<ShoppingListResponse> paramCallback);
  
  @GET("/shopping_lists/recommended")
  public abstract void getRecommendedList(@Query("q[stock_location_id_eq]") Long paramLong, Callback<RecommendedListResponse> paramCallback);
  
  @GET("/shopping_lists/{shopping_list_id}")
  public abstract void getShoppingListItem(@Path("shopping_list_id") Long paramLong1, @Query("stock_location_id") Long paramLong2, Callback<ShoppingListResponse> paramCallback);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/services/ShoppingListService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */