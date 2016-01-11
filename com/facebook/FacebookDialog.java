package com.facebook;

public abstract interface FacebookDialog<CONTENT, RESULT>
{
  public abstract boolean canShow(CONTENT paramCONTENT);
  
  public abstract void registerCallback(CallbackManager paramCallbackManager, FacebookCallback<RESULT> paramFacebookCallback);
  
  public abstract void registerCallback(CallbackManager paramCallbackManager, FacebookCallback<RESULT> paramFacebookCallback, int paramInt);
  
  public abstract void show(CONTENT paramCONTENT);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/FacebookDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */