package com.happyfresh.utils;

import android.content.Context;
import android.text.TextUtils;
import com.crashlytics.android.Crashlytics;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.models.LineItem;
import com.happyfresh.models.Order;
import com.happyfresh.models.Product;
import com.happyfresh.models.SubDistrict;
import com.happyfresh.models.Taxon;
import com.happyfresh.models.Variant;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class MixpanelTrackerUtils
{
  public static void trackAddAllItemFromOrderHistory(Context paramContext, String paramString)
  {
    paramContext = (ICartApplication)paramContext.getApplicationContext();
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("order_number", paramString);
      paramContext.getMixpanel().track("Added All Items From Order History", localJSONObject);
      return;
    }
    catch (JSONException paramString)
    {
      for (;;)
      {
        paramString.printStackTrace();
      }
    }
  }
  
  public static void trackAddReplacement(Context paramContext, Double paramDouble)
  {
    paramContext = (ICartApplication)paramContext.getApplicationContext();
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("total items", paramContext.currentOrder.itemTotal);
      localJSONObject.put("total items replaced", paramDouble);
      paramContext.getMixpanel().track("Added Replacement", localJSONObject);
      return;
    }
    catch (JSONException paramDouble)
    {
      for (;;)
      {
        paramDouble.printStackTrace();
      }
    }
  }
  
  public static void trackAddToCart(Context paramContext, LineItem paramLineItem)
  {
    paramContext = (ICartApplication)paramContext.getApplicationContext();
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("sku", paramLineItem.variant.sku);
      localJSONObject.put("currency", paramContext.currentOrder.currency);
      paramContext.getMixpanel().track("[c]Added to Cart", localJSONObject);
      return;
    }
    catch (JSONException paramLineItem)
    {
      for (;;)
      {
        paramLineItem.printStackTrace();
      }
    }
  }
  
  public static void trackAddToCartFromOrderHistory(Context paramContext, String paramString1, String paramString2)
  {
    paramContext = (ICartApplication)paramContext.getApplicationContext();
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("sku", paramString1);
      localJSONObject.put("name", paramString2);
      localJSONObject.put("supermarket_id", paramContext.getStockLocationId());
      paramContext.getMixpanel().track("Added an Item From Order History", localJSONObject);
      return;
    }
    catch (JSONException paramString1)
    {
      for (;;)
      {
        paramString1.printStackTrace();
      }
    }
  }
  
  public static void trackCompletedPayment(Context paramContext, String paramString)
  {
    paramContext = (ICartApplication)paramContext.getApplicationContext();
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("currency", paramContext.currentOrder.currency);
      localJSONObject.put("total", paramContext.currentOrder.total);
      localJSONObject.put("ship total", paramContext.currentOrder.shipTotal);
      localJSONObject.put("discount", paramContext.currentOrder.getDiscount());
      localJSONObject.put("payment method", paramString);
      paramContext.getMixpanel().track("[c]Completed Payment", localJSONObject);
      return;
    }
    catch (JSONException paramString)
    {
      for (;;)
      {
        paramString.printStackTrace();
      }
    }
  }
  
  public static void trackContinueCheckout(Context paramContext)
  {
    paramContext = (ICartApplication)paramContext.getApplicationContext();
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("currency", paramContext.currentOrder.currency);
      localJSONObject.put("subtotal", paramContext.currentOrder.total);
      localJSONObject.put("line items", paramContext.currentOrder.lineItems.size());
      paramContext.getMixpanel().track("[c]Continued to Checkout", localJSONObject);
      return;
    }
    catch (JSONException localJSONException)
    {
      for (;;)
      {
        localJSONException.printStackTrace();
      }
    }
  }
  
  public static void trackContinuePayment(Context paramContext)
  {
    paramContext = (ICartApplication)paramContext.getApplicationContext();
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("currency", paramContext.currentOrder.currency);
      localJSONObject.put("total", paramContext.currentOrder.total);
      localJSONObject.put("ship total", paramContext.currentOrder.shipTotal);
      localJSONObject.put("discount", paramContext.currentOrder.getDiscount());
      paramContext.getMixpanel().track("[c]Continued to Payment", localJSONObject);
      return;
    }
    catch (JSONException localJSONException)
    {
      for (;;)
      {
        localJSONException.printStackTrace();
      }
    }
  }
  
  public static void trackFBReferral(Context paramContext, String paramString)
  {
    ICartApplication localICartApplication = (ICartApplication)paramContext.getApplicationContext();
    JSONObject localJSONObject = new JSONObject();
    for (;;)
    {
      try
      {
        if (TextUtils.isEmpty(paramString)) {
          continue;
        }
        paramContext = "true";
        localJSONObject.put("referral", paramContext);
      }
      catch (JSONException paramContext)
      {
        paramContext.printStackTrace();
        continue;
      }
      localICartApplication.getMixpanel().track("Referral FB", localJSONObject);
      return;
      paramContext = "false";
    }
  }
  
  public static void trackFBSignUp(Context paramContext)
  {
    ((ICartApplication)paramContext.getApplicationContext()).getMixpanel().track("Signed Up FB", null);
  }
  
  public static void trackLocationOutOfRange(Context paramContext, SubDistrict paramSubDistrict)
  {
    paramContext = (ICartApplication)paramContext.getApplicationContext();
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("selected_subdistrict_id", paramSubDistrict.remoteId);
      localJSONObject.put("selected_subdistrict_name", paramSubDistrict.name);
      paramContext.getMixpanel().track("[c]Couldn't Find Any Store", localJSONObject);
      return;
    }
    catch (JSONException paramSubDistrict)
    {
      for (;;)
      {
        paramSubDistrict.printStackTrace();
      }
    }
  }
  
  public static void trackOpenApp(Context paramContext)
  {
    ((ICartApplication)paramContext.getApplicationContext()).getMixpanel().track("Opened App", null);
  }
  
  public static void trackShared(Context paramContext)
  {
    ((ICartApplication)paramContext.getApplicationContext()).getMixpanel().track("Shared", null);
  }
  
  public static void trackSignUp(Context paramContext, String paramString)
  {
    ICartApplication localICartApplication = (ICartApplication)paramContext.getApplicationContext();
    JSONObject localJSONObject = new JSONObject();
    for (;;)
    {
      try
      {
        if (TextUtils.isEmpty(paramString)) {
          continue;
        }
        paramContext = "true";
        localJSONObject.put("referral", paramContext);
      }
      catch (JSONException paramContext)
      {
        paramContext.printStackTrace();
        continue;
      }
      localICartApplication.getMixpanel().track("[c]Signed Up", localJSONObject);
      return;
      paramContext = "false";
    }
  }
  
  public static void trackSwitchOnReplacement(Context paramContext)
  {
    ((ICartApplication)paramContext.getApplicationContext()).getMixpanel().track("Switched On Replacement", null);
  }
  
  public static void trackViewCategory(Context paramContext, Taxon paramTaxon)
  {
    paramContext = (ICartApplication)paramContext.getApplicationContext();
    JSONObject localJSONObject = new JSONObject();
    try
    {
      if (!TextUtils.isEmpty(paramTaxon.name)) {
        localJSONObject.put("name", paramTaxon.name);
      }
      paramContext.getMixpanel().track("Category Viewed", localJSONObject);
      return;
    }
    catch (JSONException paramTaxon)
    {
      for (;;)
      {
        Crashlytics.log(paramTaxon.getLocalizedMessage());
      }
    }
  }
  
  public static void trackViewFavorites(Context paramContext)
  {
    ((ICartApplication)paramContext.getApplicationContext()).getMixpanel().track("Favourites Viewed", null);
  }
  
  public static void trackViewOrderHistory(Context paramContext)
  {
    ((ICartApplication)paramContext.getApplicationContext()).getMixpanel().track("Viewed Order History", null);
  }
  
  public static void trackViewPopularProduct(Context paramContext)
  {
    ((ICartApplication)paramContext.getApplicationContext()).getMixpanel().track("Popular Products Viewed", null);
  }
  
  public static void trackViewProductDetail(Context paramContext, Product paramProduct)
  {
    paramContext = (ICartApplication)paramContext.getApplicationContext();
    JSONObject localJSONObject = new JSONObject();
    try
    {
      Variant localVariant = (Variant)paramProduct.variants.get(0);
      if (!TextUtils.isEmpty(localVariant.name)) {
        localJSONObject.put("name", paramProduct.name);
      }
      if (!TextUtils.isEmpty(localVariant.sku)) {
        localJSONObject.put("sku", localVariant.sku);
      }
    }
    catch (JSONException paramProduct)
    {
      for (;;)
      {
        paramProduct.printStackTrace();
        Crashlytics.log(paramProduct.getLocalizedMessage());
      }
    }
    paramContext.getMixpanel().track("Product Detail Viewed", localJSONObject);
  }
  
  public static void trackViewRecommended(Context paramContext)
  {
    ((ICartApplication)paramContext.getApplicationContext()).getMixpanel().track("Recommended Viewed", null);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/utils/MixpanelTrackerUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */