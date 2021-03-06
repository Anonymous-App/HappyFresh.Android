package com.facebook.share.internal;

import com.facebook.internal.DialogFeature;

public enum LikeDialogFeature
  implements DialogFeature
{
  LIKE_DIALOG(20140701);
  
  private int minVersion;
  
  private LikeDialogFeature(int paramInt)
  {
    this.minVersion = paramInt;
  }
  
  public String getAction()
  {
    return "com.facebook.platform.action.request.LIKE_DIALOG";
  }
  
  public int getMinVersion()
  {
    return this.minVersion;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/share/internal/LikeDialogFeature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */