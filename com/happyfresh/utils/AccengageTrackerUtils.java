package com.happyfresh.utils;

import android.content.Context;
import android.text.TextUtils;
import com.ad4screen.sdk.A4S;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.models.LineItem;
import com.happyfresh.models.Order;
import com.happyfresh.models.Product;
import com.happyfresh.models.SubDistrict;
import com.happyfresh.models.Taxon;
import com.happyfresh.models.Variant;
import java.util.List;

public class AccengageTrackerUtils
{
  private static final int ADDED_ALL_ITEM_FROM_ORDER_HISTORY = 1004;
  private static final int ADDED_ITEM_FROM_ORDER_HISTORY = 1003;
  private static final int ADDED_REPLACEMENT = 1014;
  private static final int ADDED_TO_CART = 1002;
  private static final int CATEGORY_VIEWED = 1010;
  private static final int COMPLETED_PAYMENT = 1005;
  private static final int CONTINUE_TO_CHECKOUT = 1006;
  private static final int CONTINUE_TO_PAYMENT = 1007;
  private static final int FAVOURITES_VIEWED = 1017;
  private static final int LOCATION_OUT_OF_RANGE = 1015;
  private static final int OPENED_APP = 1000;
  private static final int POPULAR_PRODUCT_VIEWED = 1009;
  private static final int PRODUCT_DETAIL_VIEWED = 1011;
  private static final int RECOMMENDED_VIEWED = 1016;
  private static final int SHARED = 1008;
  private static final int SIGNED_UP = 1001;
  private static final int SWITCHED_ON_REPLACEMENT = 1013;
  private static final int VIEWED_ORDER_HISTORY = 1012;
  
  public static void trackAddAllItemFromOrderHistory(Context paramContext, String paramString)
  {
    paramContext = (ICartApplication)paramContext.getApplicationContext();
    paramString = String.format("order_number: %s", new Object[] { paramString });
    paramContext.getAccengage().trackEvent(1004L, new String[] { paramString });
  }
  
  public static void trackAddReplacement(Context paramContext, Double paramDouble)
  {
    paramContext = (ICartApplication)paramContext.getApplicationContext();
    String str = String.format("total items: %f", new Object[] { paramContext.currentOrder.itemTotal });
    paramDouble = String.format("total items replaced: &f", new Object[] { paramDouble });
    paramContext.getAccengage().trackEvent(1014L, new String[] { str, paramDouble });
  }
  
  public static void trackAddToCart(Context paramContext, LineItem paramLineItem)
  {
    paramContext = (ICartApplication)paramContext.getApplicationContext();
    paramLineItem = String.format("sku: %s", new Object[] { paramLineItem.variant.sku });
    String str = String.format("currency: %s", new Object[] { paramContext.currentOrder.currency });
    paramContext.getAccengage().trackEvent(1002L, new String[] { paramLineItem, str });
  }
  
  public static void trackAddToCartFromOrderHistory(Context paramContext, String paramString1, String paramString2)
  {
    paramContext = (ICartApplication)paramContext.getApplicationContext();
    paramString1 = String.format("sku: %s", new Object[] { paramString1 });
    paramString2 = String.format("name: %s", new Object[] { paramString2 });
    String str = String.format("supermarket_id: %d", new Object[] { Long.valueOf(paramContext.getStockLocationId()) });
    paramContext.getAccengage().trackEvent(1003L, new String[] { paramString1, paramString2, str });
  }
  
  public static void trackCompletedPayment(Context paramContext, String paramString)
  {
    paramContext = (ICartApplication)paramContext.getApplicationContext();
    Object localObject = paramContext.currentOrder;
    String str1 = String.format("currency: %s", new Object[] { ((Order)localObject).currency });
    String str2 = String.format("total: %f", new Object[] { ((Order)localObject).total });
    String str3 = String.format("ship total: %f", new Object[] { ((Order)localObject).shipTotal });
    localObject = String.format("discount: %f", new Object[] { Double.valueOf(((Order)localObject).getDiscount()) });
    paramString = String.format("payment method: %s", new Object[] { paramString });
    paramContext.getAccengage().trackEvent(1005L, new String[] { str1, str2, str3, localObject, paramString });
  }
  
  public static void trackContinueCheckout(Context paramContext)
  {
    paramContext = (ICartApplication)paramContext.getApplicationContext();
    Object localObject = paramContext.currentOrder;
    String str1 = String.format("currency: %s", new Object[] { ((Order)localObject).currency });
    String str2 = String.format("subtotal: %f", new Object[] { ((Order)localObject).total });
    localObject = String.format("line items: %d", new Object[] { Integer.valueOf(((Order)localObject).lineItems.size()) });
    paramContext.getAccengage().trackEvent(1006L, new String[] { str1, str2, localObject });
  }
  
  public static void trackContinuePayment(Context paramContext)
  {
    paramContext = (ICartApplication)paramContext.getApplicationContext();
    Object localObject = paramContext.currentOrder;
    String str1 = String.format("currency: %s", new Object[] { ((Order)localObject).currency });
    String str2 = String.format("total: %f", new Object[] { ((Order)localObject).total });
    String str3 = String.format("ship total: %f", new Object[] { ((Order)localObject).shipTotal });
    localObject = String.format("discount: %f", new Object[] { Double.valueOf(((Order)localObject).getDiscount()) });
    paramContext.getAccengage().trackEvent(1007L, new String[] { str1, str2, str3, localObject });
  }
  
  public static void trackLocationOutOfRange(Context paramContext, SubDistrict paramSubDistrict)
  {
    paramContext = (ICartApplication)paramContext.getApplicationContext();
    String str = String.format("selected_subdistrict_id: %d", new Object[] { paramSubDistrict.remoteId });
    paramSubDistrict = String.format("selected_subdistrict_name: %s", new Object[] { paramSubDistrict.name });
    paramContext.getAccengage().trackEvent(1015L, new String[] { str, paramSubDistrict });
  }
  
  public static void trackOpenApp(Context paramContext)
  {
    ((ICartApplication)paramContext.getApplicationContext()).getAccengage().trackEvent(1000L, new String[0]);
  }
  
  public static void trackShared(Context paramContext)
  {
    ((ICartApplication)paramContext.getApplicationContext()).getAccengage().trackEvent(1008L, new String[0]);
  }
  
  public static void trackSignUp(Context paramContext, String paramString)
  {
    paramContext = (ICartApplication)paramContext.getApplicationContext();
    if (!TextUtils.isEmpty(paramString)) {}
    for (boolean bool = true;; bool = false)
    {
      paramString = String.format("referral: %b", new Object[] { Boolean.valueOf(bool) });
      paramContext.getAccengage().trackEvent(1001L, new String[] { paramString });
      return;
    }
  }
  
  public static void trackSwitchOnReplacement(Context paramContext)
  {
    ((ICartApplication)paramContext.getApplicationContext()).getAccengage().trackEvent(1013L, new String[0]);
  }
  
  public static void trackViewCategory(Context paramContext, Taxon paramTaxon)
  {
    ICartApplication localICartApplication = (ICartApplication)paramContext.getApplicationContext();
    paramContext = null;
    if (!TextUtils.isEmpty(paramTaxon.name)) {
      paramContext = String.format("name", new Object[] { paramTaxon.name });
    }
    localICartApplication.getAccengage().trackEvent(1010L, new String[] { paramContext });
  }
  
  public static void trackViewFavorites(Context paramContext)
  {
    ((ICartApplication)paramContext.getApplicationContext()).getAccengage().trackEvent(1017L, new String[0]);
  }
  
  public static void trackViewOrderHistory(Context paramContext)
  {
    ((ICartApplication)paramContext.getApplicationContext()).getAccengage().trackEvent(1012L, new String[0]);
  }
  
  public static void trackViewPopularProduct(Context paramContext)
  {
    ((ICartApplication)paramContext.getApplicationContext()).getAccengage().trackEvent(1009L, new String[0]);
  }
  
  public static void trackViewProductDetail(Context paramContext, Product paramProduct)
  {
    ICartApplication localICartApplication = (ICartApplication)paramContext.getApplicationContext();
    Variant localVariant = (Variant)paramProduct.variants.get(0);
    paramContext = null;
    if (!TextUtils.isEmpty(localVariant.name)) {
      paramContext = String.format("name", new Object[] { paramProduct.name });
    }
    paramProduct = null;
    if (!TextUtils.isEmpty(localVariant.sku)) {
      paramProduct = String.format("sku", new Object[] { localVariant.sku });
    }
    localICartApplication.getAccengage().trackEvent(1011L, new String[] { paramContext, paramProduct });
  }
  
  public static void trackViewRecommended(Context paramContext)
  {
    ((ICartApplication)paramContext.getApplicationContext()).getAccengage().trackEvent(1016L, new String[0]);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/utils/AccengageTrackerUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */