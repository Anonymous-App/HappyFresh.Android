package com.happyfresh.managers;

import com.happyfresh.common.ICartApplication;
import com.happyfresh.common.ICartRestClient;

public abstract class BaseManager
{
  protected ICartApplication mICartApplication;
  protected ICartRestClient mICartRestClient = ICartRestClient.INSTANCE;
  
  public BaseManager(ICartApplication paramICartApplication)
  {
    this.mICartApplication = paramICartApplication;
    this.mICartRestClient.setICartApplication(this.mICartApplication);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/managers/BaseManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */