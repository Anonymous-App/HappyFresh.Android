package com.ad4screen.sdk;

import android.content.Context;
import com.ad4screen.sdk.common.annotations.API;

@API
public abstract interface A4SIdsProvider
{
  public abstract String getPartnerId(Context paramContext);
  
  public abstract String getPrivateKey(Context paramContext);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/A4SIdsProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */