package com.happyfresh.utils;

import android.content.Context;
import com.google.android.gms.analytics.HitBuilders.ScreenViewBuilder;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.customs.ICartScreenViewBuilder;
import com.happyfresh.managers.SharedPreferencesManager;
import com.happyfresh.models.LineItem;
import com.happyfresh.models.Order;
import com.happyfresh.models.User;
import com.happyfresh.models.Variant;
import java.util.Iterator;
import java.util.List;

public class GAUtils
{
  public static void trackAddToCart(Context paramContext, LineItem paramLineItem, String paramString)
  {
    Object localObject1 = (ICartApplication)paramContext.getApplicationContext();
    paramContext = ((ICartApplication)localObject1).currentOrder;
    if ((paramContext != null) && (paramLineItem != null))
    {
      Object localObject2 = paramLineItem.variant;
      paramLineItem = new com.google.android.gms.analytics.ecommerce.Product().setId(((Variant)localObject2).sku).setName(((Variant)localObject2).name).setPrice(paramLineItem.price.doubleValue()).setQuantity(paramLineItem.quantity.intValue()).setCategory(paramLineItem.getCategory());
      localObject2 = new ProductAction("add");
      paramLineItem = (HitBuilders.ScreenViewBuilder)((HitBuilders.ScreenViewBuilder)ICartScreenViewBuilder.build((Context)localObject1).addProduct(paramLineItem)).setProductAction((ProductAction)localObject2);
      localObject1 = ((ICartApplication)localObject1).getTracker();
      ((Tracker)localObject1).setScreenName(paramString);
      ((Tracker)localObject1).set("&cu", paramContext.currency);
      ((Tracker)localObject1).send(paramLineItem.build());
    }
  }
  
  public static void trackCancelOrder(Context paramContext)
  {
    ((ICartApplication)paramContext.getApplicationContext()).sendEvent("ui_action", "Cancel Order", null);
  }
  
  public static void trackCategoryProduct(Context paramContext, String paramString)
  {
    ((ICartApplication)paramContext.getApplicationContext()).sendEvent("ui_action", "Category Viewed", paramString);
  }
  
  public static void trackCompletePaymentAfterCancelOrder(Context paramContext)
  {
    ((ICartApplication)paramContext.getApplicationContext()).sendEvent("ui_action", "Complete Payment After Cancel Order", null);
  }
  
  public static void trackECommerceProductDetail(Context paramContext, com.happyfresh.models.Product paramProduct, String paramString1, String paramString2)
  {
    ICartApplication localICartApplication = (ICartApplication)paramContext.getApplicationContext();
    Tracker localTracker = localICartApplication.getTracker();
    if (paramProduct.variants.size() > 0) {}
    for (paramContext = (Variant)paramProduct.variants.get(0);; paramContext = null)
    {
      if (paramContext != null)
      {
        paramString2 = new ProductAction("detail").setProductActionList(paramString2);
        paramContext = new com.google.android.gms.analytics.ecommerce.Product().setId(paramContext.sku).setName(paramContext.name).setCategory(paramProduct.category);
        paramContext = (HitBuilders.ScreenViewBuilder)((HitBuilders.ScreenViewBuilder)ICartScreenViewBuilder.build(localICartApplication).addProduct(paramContext)).setProductAction(paramString2);
        localTracker.setScreenName(paramString1);
        localTracker.send(paramContext.build());
      }
      return;
    }
  }
  
  public static void trackECommerceTransaction(Context paramContext, String paramString)
  {
    Object localObject1 = (ICartApplication)paramContext.getApplicationContext();
    paramContext = ((ICartApplication)localObject1).getTracker();
    Order localOrder = ((ICartApplication)localObject1).currentOrder;
    if (localOrder != null)
    {
      Object localObject2 = new ProductAction("purchase").setTransactionId(localOrder.number).setTransactionAffiliation(((ICartApplication)localObject1).getSharedPreferencesManager().getStockLocationName()).setTransactionRevenue(localOrder.total.doubleValue()).setTransactionTax(localOrder.taxTotal.doubleValue()).setTransactionShipping(localOrder.shipTotal.doubleValue());
      if (paramString != null) {
        ((ProductAction)localObject2).setCheckoutOptions(paramString);
      }
      paramString = (HitBuilders.ScreenViewBuilder)ICartScreenViewBuilder.build((Context)localObject1).setProductAction((ProductAction)localObject2);
      localObject1 = localOrder.lineItems.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (LineItem)((Iterator)localObject1).next();
        paramString.addProduct(new com.google.android.gms.analytics.ecommerce.Product().setId(String.valueOf(((LineItem)localObject2).variant.sku)).setName(((LineItem)localObject2).variant.name).setCategory(((LineItem)localObject2).getCategory()).setPrice(((LineItem)localObject2).price.doubleValue()).setQuantity(((LineItem)localObject2).quantity.intValue()));
      }
      paramContext.set("&cu", localOrder.currency);
      paramContext.send(paramString.build());
    }
  }
  
  public static void trackEditOrder(Context paramContext)
  {
    ((ICartApplication)paramContext.getApplicationContext()).sendEvent("ui_action", "Edit Order", null);
  }
  
  public static void trackPopularProduct(Context paramContext, String paramString)
  {
    ((ICartApplication)paramContext.getApplicationContext()).sendEvent("ui_action", "Popular Products Viewed", paramString);
  }
  
  public static void trackRating(Context paramContext, String paramString)
  {
    ((ICartApplication)paramContext.getApplicationContext()).sendEvent("ui_action", "Rated", paramString);
  }
  
  public static void trackRemoveFromCart(Context paramContext, LineItem paramLineItem, String paramString)
  {
    Object localObject1 = (ICartApplication)paramContext.getApplicationContext();
    paramContext = ((ICartApplication)localObject1).currentOrder;
    if ((paramContext != null) && (paramLineItem != null))
    {
      Object localObject2 = paramLineItem.variant;
      paramLineItem = new com.google.android.gms.analytics.ecommerce.Product().setId(((Variant)localObject2).sku).setName(((Variant)localObject2).name).setPrice(paramLineItem.price.doubleValue()).setQuantity(paramLineItem.quantity.intValue()).setCategory(paramLineItem.getCategory());
      localObject2 = new ProductAction("remove");
      paramLineItem = (HitBuilders.ScreenViewBuilder)((HitBuilders.ScreenViewBuilder)ICartScreenViewBuilder.build((Context)localObject1).addProduct(paramLineItem)).setProductAction((ProductAction)localObject2);
      localObject1 = ((ICartApplication)localObject1).getTracker();
      ((Tracker)localObject1).setScreenName(paramString);
      ((Tracker)localObject1).set("&cu", paramContext.currency);
      ((Tracker)localObject1).send(paramLineItem.build());
    }
  }
  
  public static void trackReplacement(Context paramContext, String paramString)
  {
    ((ICartApplication)paramContext.getApplicationContext()).sendEvent("ui_action", "Replacement Chosen", paramString);
  }
  
  public static void trackReplacementOption(Context paramContext, String paramString)
  {
    ((ICartApplication)paramContext.getApplicationContext()).sendEvent("ui_action", "Replacement Option", paramString);
  }
  
  public static void trackReplacementPerProductOption(Context paramContext, int paramInt)
  {
    ((ICartApplication)paramContext.getApplicationContext()).sendEvent("ui_action", "Replacement Per Product Option", String.valueOf(paramInt));
  }
  
  public static void trackSignUp(Context paramContext, User paramUser)
  {
    ((ICartApplication)paramContext.getApplicationContext()).sendEvent("ui_action", "Signed Up", paramUser.email);
  }
  
  public static void trackSignUpFB(Context paramContext, User paramUser)
  {
    ((ICartApplication)paramContext.getApplicationContext()).sendEvent("ui_action", "Signed Up FB", paramUser.email);
  }
  
  public static void trackSortByOption(Context paramContext, String paramString)
  {
    ((ICartApplication)paramContext.getApplicationContext()).sendEvent("ui_action", "Change Sort By", paramString);
  }
  
  public static void trackSwitchReplacement(Context paramContext)
  {
    ((ICartApplication)paramContext.getApplicationContext()).sendEvent("ui_action", "Switched On Replacement", null);
  }
  
  public static void trackingECommerceCheckout(Context paramContext, int paramInt, String paramString1, String paramString2)
  {
    Object localObject2 = (ICartApplication)paramContext.getApplicationContext();
    Tracker localTracker = ((ICartApplication)localObject2).getTracker();
    Object localObject1 = ((ICartApplication)localObject2).currentOrder;
    if (localObject1 != null)
    {
      paramContext = "checkout";
      if (paramString1 != null) {
        paramContext = "checkout_option";
      }
      paramContext = new ProductAction(paramContext).setCheckoutStep(paramInt);
      if (paramString1 != null) {
        paramContext.setCheckoutOptions(paramString1);
      }
      paramContext = (HitBuilders.ScreenViewBuilder)ICartScreenViewBuilder.build((Context)localObject2).setProductAction(paramContext);
      paramString1 = ((Order)localObject1).lineItems.iterator();
      while (paramString1.hasNext())
      {
        localObject1 = (LineItem)paramString1.next();
        localObject2 = ((LineItem)localObject1).variant;
        paramContext.addProduct(new com.google.android.gms.analytics.ecommerce.Product().setId(((Variant)localObject2).sku).setName(((Variant)localObject2).name).setCategory(((LineItem)localObject1).getCategory()));
      }
      localTracker.setScreenName(paramString2);
      localTracker.send(paramContext.build());
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/utils/GAUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */