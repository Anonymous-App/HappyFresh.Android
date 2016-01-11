package com.facebook.share.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import com.facebook.R.style;
import com.facebook.internal.CallbackManagerImpl.RequestCodeOffset;

public final class ShareButton
  extends ShareButtonBase
{
  private static final int DEFAULT_REQUEST_CODE = CallbackManagerImpl.RequestCodeOffset.Share.toRequestCode();
  
  public ShareButton(Context paramContext)
  {
    super(paramContext, null, 0, "fb_share_button_create", DEFAULT_REQUEST_CODE);
  }
  
  public ShareButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet, 0, "fb_share_button_create", DEFAULT_REQUEST_CODE);
  }
  
  public ShareButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt, "fb_share_button_create", DEFAULT_REQUEST_CODE);
  }
  
  protected int getDefaultStyleResource()
  {
    return R.style.com_facebook_button_share;
  }
  
  protected View.OnClickListener getShareOnClickListener()
  {
    new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (ShareButton.this.getFragment() != null) {}
        for (ShareDialog localShareDialog = new ShareDialog(ShareButton.this.getFragment(), ShareButton.this.getRequestCode());; localShareDialog = new ShareDialog(ShareButton.this.getActivity(), ShareButton.this.getRequestCode()))
        {
          localShareDialog.show(ShareButton.this.getShareContent());
          ShareButton.this.callExternalOnClickListener(paramAnonymousView);
          return;
        }
      }
    };
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/share/widget/ShareButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */