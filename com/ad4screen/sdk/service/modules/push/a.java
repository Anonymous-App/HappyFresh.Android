package com.ad4screen.sdk.service.modules.push;

import android.os.Bundle;

public abstract interface a
{
  public abstract void a();
  
  public abstract void handleMessage(Bundle paramBundle);
  
  public abstract boolean isEnabled();
  
  public abstract void openedPush(Bundle paramBundle);
  
  public abstract void setEnabled(boolean paramBoolean);
  
  public abstract void updateRegistration(Bundle paramBundle);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/push/a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */