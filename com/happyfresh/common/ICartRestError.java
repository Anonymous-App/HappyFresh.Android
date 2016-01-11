package com.happyfresh.common;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;
import com.google.gson.JsonSyntaxException;
import java.util.List;
import retrofit.RetrofitError;

public class ICartRestError
{
  public String error;
  public ICartRestErrorBase errors;
  public String exception;
  public String type;
  
  public static String getErrorMessage(Context paramContext, Throwable paramThrowable)
  {
    return getErrorMessage(paramThrowable, paramContext.getString(2131165671));
  }
  
  public static String getErrorMessage(Throwable paramThrowable, String paramString)
  {
    ICartRestError localICartRestError = getICartRestError(paramThrowable);
    if (localICartRestError != null)
    {
      paramString = localICartRestError.error;
      paramThrowable = paramString;
      if (TextUtils.isEmpty(paramString)) {
        paramThrowable = localICartRestError.exception;
      }
      paramString = paramThrowable;
      if (TextUtils.isEmpty(paramThrowable))
      {
        paramString = paramThrowable;
        if (localICartRestError.errors != null)
        {
          paramString = paramThrowable;
          if (localICartRestError.errors.base != null)
          {
            paramString = paramThrowable;
            if (localICartRestError.errors.base.size() > 0) {
              paramString = (String)localICartRestError.errors.base.get(0);
            }
          }
        }
      }
    }
    return paramString;
  }
  
  protected static ICartRestError getICartRestError(Throwable paramThrowable)
  {
    try
    {
      paramThrowable = (ICartRestError)((RetrofitError)paramThrowable).getBodyAs(ICartRestError.class);
      return paramThrowable;
    }
    catch (RuntimeException paramThrowable)
    {
      return null;
    }
    catch (JsonSyntaxException paramThrowable) {}
    return null;
  }
  
  public static String getType(Throwable paramThrowable)
  {
    paramThrowable = getICartRestError(paramThrowable);
    if (paramThrowable != null) {
      return paramThrowable.type;
    }
    return "";
  }
  
  public static void showMessage(Context paramContext, Throwable paramThrowable)
  {
    showMessage(paramContext, paramThrowable, paramContext.getString(2131165671));
  }
  
  public static void showMessage(Context paramContext, Throwable paramThrowable, String paramString)
  {
    paramThrowable = getErrorMessage(paramThrowable, paramString);
    if (TextUtils.isEmpty(paramThrowable)) {
      return;
    }
    Toast.makeText(paramContext, paramThrowable, 1).show();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/common/ICartRestError.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */