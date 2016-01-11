package com.ad4screen.sdk.service.modules.tracking.interfaces;

import android.os.Bundle;
import com.ad4screen.sdk.analytics.Cart;
import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.analytics.Purchase;

public abstract interface a
{
  public abstract String a();
  
  public abstract void a(long paramLong, Bundle paramBundle, String... paramVarArgs);
  
  public abstract void a(Cart paramCart, Bundle paramBundle);
  
  public abstract void a(Lead paramLead, Bundle paramBundle);
  
  public abstract void a(Purchase paramPurchase, Bundle paramBundle);
  
  public abstract int b();
  
  public abstract void c();
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/tracking/interfaces/a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */