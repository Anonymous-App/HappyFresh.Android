package com.happyfresh.services;

import com.happyfresh.models.Replacement;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.mime.TypedInput;

public abstract interface ReplacementService
{
  @POST("/line_items/{lineItemId}/replacements")
  public abstract void createReplacement(@Path("lineItemId") Long paramLong, @Body TypedInput paramTypedInput, Callback<Replacement> paramCallback);
  
  @DELETE("/replacements/{replacementId}")
  public abstract void deleteReplacement(@Path("replacementId") Long paramLong, Callback<Object> paramCallback);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/services/ReplacementService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */