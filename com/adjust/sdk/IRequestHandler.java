package com.adjust.sdk;

public abstract interface IRequestHandler
{
  public abstract void init(IPackageHandler paramIPackageHandler);
  
  public abstract void sendClickPackage(ActivityPackage paramActivityPackage);
  
  public abstract void sendPackage(ActivityPackage paramActivityPackage);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/adjust/sdk/IRequestHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */