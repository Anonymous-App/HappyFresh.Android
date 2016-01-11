package com.happyfresh.services;

import com.happyfresh.models.AppConfig;
import com.happyfresh.models.ContactUsConfig;
import com.happyfresh.models.CountryResponse;
import com.happyfresh.models.StateResponse;
import com.happyfresh.models.SubDistrictResponse;
import com.happyfresh.models.UserConfig;
import java.util.Date;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public abstract interface ConfigService
{
  @GET("/config/mobile")
  public abstract void getConfig(Callback<AppConfig> paramCallback);
  
  @GET("/config/contact")
  public abstract void getContactUsConfig(Callback<ContactUsConfig> paramCallback);
  
  @GET("/countries")
  public abstract void getCountries(Callback<CountryResponse> paramCallback);
  
  @GET("/states")
  public abstract void getStates(@Query("q[country_id_eq]") Long paramLong, Callback<StateResponse> paramCallback);
  
  @GET("/sub_districts")
  public abstract void getSubdistrictByState(@Query("q[district_state_id_eq]") Long paramLong, @Query("q[updated_at_gteq]") Date paramDate, @Query("b") String paramString, @Query("per_page") Integer paramInteger1, @Query("page") Integer paramInteger2, Callback<SubDistrictResponse> paramCallback);
  
  @GET("/config/user")
  public abstract void getUserConfig(Callback<UserConfig> paramCallback);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/services/ConfigService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */