package com.ad4screen.sdk.service.modules.tracking;

import android.os.Bundle;
import com.ad4screen.sdk.A4SService.a;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.analytics.Cart;
import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.analytics.Purchase;
import com.ad4screen.sdk.common.persistence.e;
import com.ad4screen.sdk.service.modules.tracking.model.c;
import com.ad4screen.sdk.systems.j;
import org.json.JSONException;
import org.json.JSONObject;

public class b
{
  com.ad4screen.sdk.service.modules.tracking.interfaces.a[] a;
  private final j b;
  
  public b(A4SService.a parama)
  {
    this.b = com.ad4screen.sdk.systems.h.a(parama.a()).e();
    this.a = new com.ad4screen.sdk.service.modules.tracking.interfaces.a[] { new com.ad4screen.sdk.service.modules.tracking.services.a(parama), new com.ad4screen.sdk.service.modules.tracking.services.facebook.a(parama) };
  }
  
  private c b()
  {
    return this.b.e();
  }
  
  public void a()
  {
    com.ad4screen.sdk.service.modules.tracking.interfaces.a[] arrayOfa = this.a;
    int j = arrayOfa.length;
    int i = 0;
    while (i < j)
    {
      com.ad4screen.sdk.service.modules.tracking.interfaces.a locala = arrayOfa[i];
      Log.info("Dispatcher|Tracking dispatched to " + locala.a());
      locala.c();
      i += 1;
    }
  }
  
  public void a(long paramLong, Bundle paramBundle, String... paramVarArgs)
  {
    int i = 0;
    int j = 0;
    Log.debug("Dispatcher|Dispatching event #" + paramLong + " : [ '" + com.ad4screen.sdk.common.h.a("', '", paramVarArgs) + "' ]");
    Object localObject1 = b();
    int k = ((c)localObject1).a(paramLong);
    Object localObject2;
    if (k == -1)
    {
      Log.internal("Dispatcher|No Dispatch rules for this event");
      localObject1 = this.a;
      k = localObject1.length;
      i = j;
      while (i < k)
      {
        localObject2 = localObject1[i];
        Log.info("Dispatcher|Event dispatched to " + ((com.ad4screen.sdk.service.modules.tracking.interfaces.a)localObject2).a());
        ((com.ad4screen.sdk.service.modules.tracking.interfaces.a)localObject2).a(paramLong, paramBundle, paramVarArgs);
        i += 1;
      }
    }
    j = localObject1.a[k].b;
    localObject1 = this.a;
    k = localObject1.length;
    while (i < k)
    {
      localObject2 = localObject1[i];
      int m = ((com.ad4screen.sdk.service.modules.tracking.interfaces.a)localObject2).b();
      if ((j & m) == m)
      {
        Log.info("Dispatcher|Event dispatched to " + ((com.ad4screen.sdk.service.modules.tracking.interfaces.a)localObject2).a());
        ((com.ad4screen.sdk.service.modules.tracking.interfaces.a)localObject2).a(paramLong, paramBundle, paramVarArgs);
      }
      i += 1;
    }
  }
  
  public void a(Cart paramCart, Bundle paramBundle)
  {
    int i = 0;
    try
    {
      Log.debug("Dispatcher|Dispatching add to cart #30 : [ '" + com.ad4screen.sdk.common.h.a("', '", new String[] { new e().a(paramCart).toString() }) + "' ]");
      Object localObject = b();
      j = ((c)localObject).a(30L);
      if (j == -1)
      {
        Log.internal("Dispatcher|No Dispatch rules for this event");
        localObject = this.a;
        j = localObject.length;
        while (i < j)
        {
          locala = localObject[i];
          Log.info("Dispatcher|Event dispatched to " + locala.a());
          locala.a(paramCart, paramBundle);
          i += 1;
        }
      }
    }
    catch (JSONException localJSONException)
    {
      com.ad4screen.sdk.service.modules.tracking.interfaces.a locala;
      for (;;)
      {
        Log.internal("Dispatcher|Error while serializing Cart to JSON", localJSONException);
      }
      int j = localJSONException.a[j].b;
      com.ad4screen.sdk.service.modules.tracking.interfaces.a[] arrayOfa = this.a;
      int k = arrayOfa.length;
      i = 0;
      while (i < k)
      {
        locala = arrayOfa[i];
        int m = locala.b();
        if ((j & m) == m)
        {
          Log.info("Dispatcher|Add To Cart dispatched to " + locala.a());
          locala.a(paramCart, paramBundle);
        }
        i += 1;
      }
    }
  }
  
  public void a(Lead paramLead, Bundle paramBundle)
  {
    int i = 0;
    try
    {
      Log.debug("Dispatcher|Dispatching lead #10 : [ '" + com.ad4screen.sdk.common.h.a("', '", new String[] { new e().a(paramLead).toString() }) + "' ]");
      Object localObject = b();
      j = ((c)localObject).a(10L);
      if (j == -1)
      {
        Log.internal("Dispatcher|No Dispatch rules for this event");
        localObject = this.a;
        j = localObject.length;
        while (i < j)
        {
          locala = localObject[i];
          Log.info("Dispatcher|Event dispatched to " + locala.a());
          locala.a(paramLead, paramBundle);
          i += 1;
        }
      }
    }
    catch (JSONException localJSONException)
    {
      com.ad4screen.sdk.service.modules.tracking.interfaces.a locala;
      for (;;)
      {
        Log.internal("Dispatcher|Error while serializing Lead to JSON", localJSONException);
      }
      int j = localJSONException.a[j].b;
      com.ad4screen.sdk.service.modules.tracking.interfaces.a[] arrayOfa = this.a;
      int k = arrayOfa.length;
      i = 0;
      while (i < k)
      {
        locala = arrayOfa[i];
        int m = locala.b();
        if ((j & m) == m)
        {
          Log.info("Dispatcher|Lead dispatched to " + locala.a());
          locala.a(paramLead, paramBundle);
        }
        i += 1;
      }
    }
  }
  
  public void a(Purchase paramPurchase, Bundle paramBundle)
  {
    int i = 0;
    try
    {
      Log.debug("Dispatcher|Dispatching purchase #50 : [ '" + com.ad4screen.sdk.common.h.a("', '", new String[] { new e().a(paramPurchase).toString() }) + "' ]");
      Object localObject = b();
      j = ((c)localObject).a(50L);
      if (j == -1)
      {
        Log.internal("Dispatcher|No Dispatch rules for this event");
        localObject = this.a;
        j = localObject.length;
        while (i < j)
        {
          locala = localObject[i];
          Log.info("Dispatcher|Event dispatched to " + locala.a());
          locala.a(paramPurchase, paramBundle);
          i += 1;
        }
      }
    }
    catch (JSONException localJSONException)
    {
      com.ad4screen.sdk.service.modules.tracking.interfaces.a locala;
      for (;;)
      {
        Log.internal("Dispatcher|Error while serializing Purchase to JSON", localJSONException);
      }
      int j = localJSONException.a[j].b;
      com.ad4screen.sdk.service.modules.tracking.interfaces.a[] arrayOfa = this.a;
      int k = arrayOfa.length;
      i = 0;
      while (i < k)
      {
        locala = arrayOfa[i];
        int m = locala.b();
        if ((j & m) == m)
        {
          Log.info("Dispatcher|Purchase dispatched to " + locala.a());
          locala.a(paramPurchase, paramBundle);
        }
        i += 1;
      }
    }
  }
  
  public void a(c paramc)
  {
    this.b.a(paramc);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/tracking/b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */