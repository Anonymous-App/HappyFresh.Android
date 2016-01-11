package com.facebook.share.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.OnClickListener;
import com.facebook.CallbackManager;
import com.facebook.FacebookButtonBase;
import com.facebook.FacebookCallback;
import com.facebook.share.Sharer.Result;
import com.facebook.share.internal.ShareInternalUtility;
import com.facebook.share.model.ShareContent;

public abstract class ShareButtonBase
  extends FacebookButtonBase
{
  private ShareContent shareContent;
  
  protected ShareButtonBase(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, String paramString, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1, 0, paramString, paramInt2);
  }
  
  protected void configureButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super.configureButton(paramContext, paramAttributeSet, paramInt1, paramInt2);
    setInternalOnClickListener(getShareOnClickListener());
  }
  
  public ShareContent getShareContent()
  {
    return this.shareContent;
  }
  
  protected abstract View.OnClickListener getShareOnClickListener();
  
  public void registerCallback(CallbackManager paramCallbackManager, FacebookCallback<Sharer.Result> paramFacebookCallback)
  {
    ShareInternalUtility.registerSharerCallback(getRequestCode(), paramCallbackManager, paramFacebookCallback);
  }
  
  public void registerCallback(CallbackManager paramCallbackManager, FacebookCallback<Sharer.Result> paramFacebookCallback, int paramInt)
  {
    setRequestCode(paramInt);
    registerCallback(paramCallbackManager, paramFacebookCallback);
  }
  
  public void setShareContent(ShareContent paramShareContent)
  {
    this.shareContent = paramShareContent;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/share/widget/ShareButtonBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */