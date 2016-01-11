package com.happyfresh.services;

import com.happyfresh.models.AutoSuggestionResponse;
import com.happyfresh.models.Product;
import com.happyfresh.models.ProductResponse;
import com.happyfresh.models.TaxonResponse;
import com.happyfresh.models.TaxonomyResponse;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

public abstract interface ProductService
{
  @PUT("/stock_locations/{stock_location_id}/products/track_view")
  public abstract void conversionViewProductDetail(@Path("stock_location_id") Long paramLong1, @Query("variant_id") Long paramLong2, Callback<Object> paramCallback);
  
  @GET("/stock_locations/{stock_location_id}/products?popular=true")
  public abstract void getPopularProductsByLocation(@Path("stock_location_id") long paramLong, @Query("page") Integer paramInteger1, @Query("per_page") Integer paramInteger2, Callback<ProductResponse> paramCallback);
  
  @GET("/stock_locations/{stock_location_id}/products/{id}")
  public abstract void getProduct(@Path("stock_location_id") long paramLong1, @Path("id") long paramLong2, Callback<Product> paramCallback);
  
  @GET("/stock_locations/{stock_location_id}/products")
  public abstract void getProductsByTaxon(@Path("stock_location_id") long paramLong, @Query("taxon_id") Long paramLong1, @Query("page") Integer paramInteger1, @Query("per_page") Integer paramInteger2, @Query("q[s]") String paramString, Callback<ProductResponse> paramCallback);
  
  @GET("/products/search_suggestions")
  public abstract void getSearchSuggestions(@Query("q") String paramString, Callback<AutoSuggestionResponse> paramCallback);
  
  @GET("/stock_locations/{stock_location_id}/products?category=specials")
  public abstract void getSpecialProducts(@Path("stock_location_id") long paramLong, @Query("page") Integer paramInteger1, @Query("per_page") Integer paramInteger2, @Query("q[s]") String paramString, Callback<ProductResponse> paramCallback);
  
  @GET("/taxonomies")
  public abstract void getTaxonomies(@Query("page") Integer paramInteger1, @Query("per_page") Integer paramInteger2, Callback<TaxonomyResponse> paramCallback);
  
  @GET("/taxonomies/{taxonomy_id}/taxons")
  public abstract void getTaxons(@Path("taxonomy_id") Long paramLong1, @Query("stock_location_id") Long paramLong2, Callback<TaxonResponse> paramCallback);
  
  @GET("/stock_locations/{stock_location_id}/products/search")
  public abstract void searchProducts(@Path("stock_location_id") long paramLong, @Query("q") String paramString, @Query("page") Integer paramInteger1, @Query("per_page") Integer paramInteger2, Callback<ProductResponse> paramCallback);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/services/ProductService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */