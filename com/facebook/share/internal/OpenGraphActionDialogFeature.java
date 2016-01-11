package com.facebook.share.internal;

import com.facebook.internal.DialogFeature;

public enum OpenGraphActionDialogFeature
  implements DialogFeature
{
  OG_ACTION_DIALOG(20130618);
  
  private int minVersion;
  
  private OpenGraphActionDialogFeature(int paramInt)
  {
    this.minVersion = paramInt;
  }
  
  public String getAction()
  {
    return "com.facebook.platform.action.request.OGACTIONPUBLISH_DIALOG";
  }
  
  public int getMinVersion()
  {
    return this.minVersion;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/share/internal/OpenGraphActionDialogFeature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */