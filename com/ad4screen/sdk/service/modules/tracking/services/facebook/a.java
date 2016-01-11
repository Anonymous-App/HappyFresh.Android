package com.ad4screen.sdk.service.modules.tracking.services.facebook;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import com.ad4screen.sdk.A4SService.a;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.analytics.Cart;
import com.ad4screen.sdk.analytics.Item;
import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.analytics.Purchase;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class a
  implements com.ad4screen.sdk.service.modules.tracking.interfaces.a
{
  private A4SService.a a;
  
  public a(A4SService.a parama)
  {
    this.a = parama;
  }
  
  private JSONArray a(Cart paramCart)
  {
    double d1 = paramCart.getItem().getPrice();
    double d2 = paramCart.getItem().getQuantity();
    paramCart = a(this.a.a(), "fb_mobile_add_to_cart", paramCart.getItem().getCategory() + " - " + paramCart.getItem().getLabel(), paramCart.getItem().getCurrency(), paramCart.getItem().getId(), d1 * d2);
    JSONArray localJSONArray = new JSONArray();
    localJSONArray.put(paramCart);
    return localJSONArray;
  }
  
  private JSONArray a(Lead paramLead)
    throws JSONException
  {
    JSONArray localJSONArray = new JSONArray();
    JSONObject localJSONObject = a(this.a.a(), "fb_mobile_complete_registration", null, null, null, 0.0D);
    localJSONObject.put("fb_registration_method", paramLead.getLabel() + " - " + paramLead.getValue());
    localJSONArray.put(localJSONObject);
    return localJSONArray;
  }
  
  private JSONArray a(Purchase paramPurchase)
  {
    if (paramPurchase.getItems() != null) {
      return b(paramPurchase);
    }
    return c(paramPurchase);
  }
  
  private static JSONObject a(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, double paramDouble)
  {
    JSONObject localJSONObject = new JSONObject();
    if (paramString2 != null) {}
    try
    {
      localJSONObject.put("fb_content_type", paramString2);
      if (paramString3 != null) {
        localJSONObject.put("fb_currency", paramString3);
      }
      if (paramString4 != null) {
        localJSONObject.put("fb_content_id", paramString4);
      }
      if (paramString1 != null) {
        localJSONObject.put("_eventName", paramString1);
      }
      if (paramDouble != 0.0D) {
        localJSONObject.put("_valueToSum", paramDouble);
      }
      localJSONObject.put("_logTime", System.currentTimeMillis());
      localJSONObject.put("_appVersion", paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0).versionName);
      return localJSONObject;
    }
    catch (JSONException paramContext)
    {
      Log.error("Facebook|Error while creating event", paramContext);
      return localJSONObject;
    }
    catch (PackageManager.NameNotFoundException paramContext)
    {
      Log.error("Facebook|Error while creating event", paramContext);
      return localJSONObject;
    }
    catch (RuntimeException paramContext) {}
    return localJSONObject;
  }
  
  protected static boolean a(Context paramContext)
  {
    JSONArray localJSONArray = new JSONArray();
    localJSONArray.put(a(paramContext, "fb_mobile_activate_app", null, null, null, 0.0D));
    return a(paramContext, localJSONArray, new Bundle());
  }
  
  private static boolean a(Context paramContext, JSONArray paramJSONArray, Bundle paramBundle)
  {
    com.ad4screen.sdk.common.cache.a.a(paramContext).a(new b(paramContext, paramJSONArray, paramBundle));
    return true;
  }
  
  private JSONArray b(Purchase paramPurchase)
  {
    JSONArray localJSONArray = new JSONArray();
    int i = 0;
    while (i < paramPurchase.getItems().length)
    {
      double d1 = paramPurchase.getItems()[i].getPrice();
      double d2 = paramPurchase.getItems()[i].getQuantity();
      localJSONArray.put(a(this.a.a(), "fb_mobile_purchase", paramPurchase.getItems()[i].getCategory() + " - " + paramPurchase.getItems()[i].getLabel(), paramPurchase.getItems()[i].getCurrency(), paramPurchase.getItems()[i].getId(), d1 * d2));
      i += 1;
    }
    return localJSONArray;
  }
  
  private JSONArray c(Purchase paramPurchase)
  {
    JSONArray localJSONArray = new JSONArray();
    localJSONArray.put(a(this.a.a(), "fb_mobile_purchase", null, paramPurchase.getCurrency(), paramPurchase.getId(), paramPurchase.getTotalPrice()));
    return localJSONArray;
  }
  
  public String a()
  {
    return "Facebook";
  }
  
  public void a(long paramLong, Bundle paramBundle, String... paramVarArgs)
  {
    Log.debug("Facebook|This event can't be dispatched to Facebook (not supported)");
  }
  
  public void a(Cart paramCart, Bundle paramBundle)
  {
    paramCart = a(paramCart);
    a(this.a.a(), paramCart, paramBundle);
  }
  
  public void a(Lead paramLead, Bundle paramBundle)
  {
    try
    {
      paramLead = a(paramLead);
      a(this.a.a(), paramLead, paramBundle);
      return;
    }
    catch (JSONException paramLead)
    {
      Log.internal("Fail to create JSONArray from Lead", paramLead);
    }
  }
  
  public void a(Purchase paramPurchase, Bundle paramBundle)
  {
    paramPurchase = a(paramPurchase);
    a(this.a.a(), paramPurchase, paramBundle);
  }
  
  public int b()
  {
    return 4;
  }
  
  public void c()
  {
    com.ad4screen.sdk.common.cache.a.a(this.a.a()).a(new c(this.a.a()));
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/tracking/services/facebook/a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */