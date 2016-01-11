package com.facebook.share.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import com.facebook.R.style;
import com.facebook.internal.CallbackManagerImpl.RequestCodeOffset;

public final class SendButton
  extends ShareButtonBase
{
  private static final int DEFAULT_REQUEST_CODE = CallbackManagerImpl.RequestCodeOffset.Message.toRequestCode();
  
  public SendButton(Context paramContext)
  {
    super(paramContext, null, 0, "fb_send_button_create", DEFAULT_REQUEST_CODE);
  }
  
  public SendButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet, 0, "fb_send_button_create", DEFAULT_REQUEST_CODE);
  }
  
  public SendButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt, "fb_send_button_create", DEFAULT_REQUEST_CODE);
  }
  
  protected int getDefaultStyleResource()
  {
    return R.style.com_facebook_button_send;
  }
  
  protected View.OnClickListener getShareOnClickListener()
  {
    new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (SendButton.this.getFragment() != null) {}
        for (MessageDialog localMessageDialog = new MessageDialog(SendButton.this.getFragment(), SendButton.this.getRequestCode());; localMessageDialog = new MessageDialog(SendButton.this.getActivity(), SendButton.this.getRequestCode()))
        {
          localMessageDialog.show(SendButton.this.getShareContent());
          SendButton.this.callExternalOnClickListener(paramAnonymousView);
          return;
        }
      }
    };
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/share/widget/SendButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */