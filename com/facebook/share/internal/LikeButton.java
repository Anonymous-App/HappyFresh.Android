package com.facebook.share.internal;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import com.facebook.FacebookButtonBase;
import com.facebook.R.drawable;
import com.facebook.R.string;
import com.facebook.R.style;

public class LikeButton
  extends FacebookButtonBase
{
  public LikeButton(Context paramContext, boolean paramBoolean)
  {
    super(paramContext, null, 0, 0, "fb_like_button_create", 0);
    setSelected(paramBoolean);
  }
  
  private void updateForLikeStatus()
  {
    if (isSelected())
    {
      setCompoundDrawablesWithIntrinsicBounds(R.drawable.com_facebook_button_like_icon_selected, 0, 0, 0);
      setText(getResources().getString(R.string.com_facebook_like_button_liked));
      return;
    }
    setCompoundDrawablesWithIntrinsicBounds(R.drawable.com_facebook_button_icon, 0, 0, 0);
    setText(getResources().getString(R.string.com_facebook_like_button_not_liked));
  }
  
  protected void configureButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super.configureButton(paramContext, paramAttributeSet, paramInt1, paramInt2);
    updateForLikeStatus();
  }
  
  protected int getDefaultStyleResource()
  {
    return R.style.com_facebook_button_like;
  }
  
  public void setSelected(boolean paramBoolean)
  {
    super.setSelected(paramBoolean);
    updateForLikeStatus();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/share/internal/LikeButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */