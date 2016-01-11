package com.happyfresh.common;

import com.google.gson.JsonSyntaxException;
import retrofit.RetrofitError;

public class ICartErrorException
{
  public String exception;
  
  public static ICartErrorException getICartErrorException(Throwable paramThrowable)
  {
    try
    {
      paramThrowable = (ICartErrorException)((RetrofitError)paramThrowable).getBodyAs(ICartErrorException.class);
      return paramThrowable;
    }
    catch (RuntimeException paramThrowable)
    {
      return null;
    }
    catch (JsonSyntaxException paramThrowable) {}
    return null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/common/ICartErrorException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */