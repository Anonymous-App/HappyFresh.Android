package com.facebook.share.internal;

import android.os.Bundle;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.internal.AppCall;

public abstract class ResultProcessor
{
  private FacebookCallback appCallback;
  
  public ResultProcessor(FacebookCallback paramFacebookCallback)
  {
    this.appCallback = paramFacebookCallback;
  }
  
  public void onCancel(AppCall paramAppCall)
  {
    if (this.appCallback != null) {
      this.appCallback.onCancel();
    }
  }
  
  public void onError(AppCall paramAppCall, FacebookException paramFacebookException)
  {
    if (this.appCallback != null) {
      this.appCallback.onError(paramFacebookException);
    }
  }
  
  public abstract void onSuccess(AppCall paramAppCall, Bundle paramBundle);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/share/internal/ResultProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */