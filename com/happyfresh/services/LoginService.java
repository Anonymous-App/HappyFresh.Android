package com.happyfresh.services;

import com.happyfresh.models.User;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit.mime.TypedInput;

public abstract interface LoginService
{
  @POST("/login")
  public abstract void login(@Query("order_number") String paramString, @Body TypedInput paramTypedInput, Callback<User> paramCallback);
  
  @POST("/users")
  public abstract void signUp(@Query("order_number") String paramString, @Body TypedInput paramTypedInput, Callback<User> paramCallback);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/services/LoginService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */