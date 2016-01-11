package com.happyfresh.models;

import android.content.Context;
import android.widget.Toast;
import com.google.gson.JsonSyntaxException;
import retrofit.RetrofitError;

public class RestError
{
  public String exception;
  public String message;
  public String type;
  
  public static RestError getRestError(Throwable paramThrowable)
  {
    try
    {
      paramThrowable = (RestError)((RetrofitError)paramThrowable).getBodyAs(RestError.class);
      return paramThrowable;
    }
    catch (RuntimeException paramThrowable)
    {
      return null;
    }
    catch (JsonSyntaxException paramThrowable) {}
    return null;
  }
  
  public static void showExceptionToast(Context paramContext, Throwable paramThrowable)
  {
    paramThrowable = getRestError(paramThrowable);
    if ((paramThrowable != null) && (paramThrowable.exception != null)) {
      Toast.makeText(paramContext, paramThrowable.exception, 0).show();
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/RestError.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */