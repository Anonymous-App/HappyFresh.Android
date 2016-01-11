package com.happyfresh.services;

import com.happyfresh.models.Address;
import com.happyfresh.models.AddressResponse;
import com.happyfresh.models.StoreCreditResponse;
import com.happyfresh.models.User;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedInput;

public abstract interface UserService
{
  @POST("/check_verification_code")
  public abstract void checkVerificationCode(@Body TypedInput paramTypedInput, Callback<User> paramCallback);
  
  @POST("/addresses")
  public abstract void createAddress(@Body TypedInput paramTypedInput, Callback<Address> paramCallback);
  
  @DELETE("/accengage_devices/{accengageDeviceId}")
  public abstract void deleteAccengageDevice(@Path("accengageDeviceId") String paramString, Callback<Object> paramCallback);
  
  @DELETE("/addresses/{addressId}")
  public abstract void deleteAddress(@Path("addressId") Long paramLong, Callback<Object> paramCallback);
  
  @GET("/store_credits")
  public abstract void fetchStoreCredits(Callback<StoreCreditResponse> paramCallback);
  
  @GET("/addresses")
  public abstract void getAddresses(@Query("t") String paramString, @Query("page") Integer paramInteger1, @Query("per_page") Integer paramInteger2, Callback<AddressResponse> paramCallback);
  
  @POST("/login_facebook")
  public abstract void loginFacebook(@Body TypedInput paramTypedInput, Callback<User> paramCallback);
  
  @DELETE("/logout")
  public abstract void logout(Callback<Object> paramCallback);
  
  @POST("/password/reset")
  public abstract void resetPassword(@Body TypedInput paramTypedInput, Callback<Object> paramCallback);
  
  @PUT("/users/language")
  public abstract void sendPreferredLanguage(@Body TypedInput paramTypedInput, Callback<User> paramCallback);
  
  @POST("/user_data")
  public abstract void sendUserData(@Body TypedInput paramTypedInput, Callback<Object> paramCallback);
  
  @POST("/accengage_devices")
  public abstract void setAccengageDevice(@Body TypedInput paramTypedInput, Callback<Object> paramCallback);
  
  @POST("/users/referral")
  public abstract void setReferral(@Body TypedInput paramTypedInput, Callback<User> paramCallback);
  
  @PUT("/addresses/{addressId}")
  public abstract void updateAddress(@Path("addressId") Long paramLong, @Body TypedInput paramTypedInput, Callback<Address> paramCallback);
  
  @GET("/referrals/{code}")
  public abstract void validateReferrals(@Path("code") String paramString, Callback<Object> paramCallback);
  
  @POST("/verify")
  public abstract void verifyNumber(@Body TypedInput paramTypedInput, Callback<Object> paramCallback);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/services/UserService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */