package com.optimizely.Network;

public abstract interface OptimizelyNetworkResult<T>
{
  public abstract void onDownloadError(int paramInt);
  
  public abstract void onDownloadFinished(T paramT);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Network/OptimizelyNetworkResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */