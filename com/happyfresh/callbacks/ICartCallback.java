package com.happyfresh.callbacks;

import android.util.Log;

public abstract class ICartCallback<T>
{
  private String mTag;
  
  public ICartCallback(String paramString)
  {
    this.mTag = paramString;
  }
  
  public void onFailure(Throwable paramThrowable)
  {
    if (paramThrowable != null) {
      Log.e(this.mTag, paramThrowable.getLocalizedMessage(), paramThrowable);
    }
  }
  
  public abstract void onSuccess(T paramT);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/callbacks/ICartCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */