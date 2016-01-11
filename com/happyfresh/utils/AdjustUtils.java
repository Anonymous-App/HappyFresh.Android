package com.happyfresh.utils;

import android.content.Context;
import android.text.TextUtils;
import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustEvent;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.managers.SharedPreferencesManager;
import com.happyfresh.models.LineItem;
import com.happyfresh.models.Order;
import com.happyfresh.models.User;
import com.happyfresh.models.Variant;
import java.util.Iterator;
import java.util.List;

public class AdjustUtils
{
  private static int getOrderTotalItem(Order paramOrder)
  {
    int i = 0;
    paramOrder = paramOrder.lineItems.iterator();
    while (paramOrder.hasNext()) {
      i += ((LineItem)paramOrder.next()).quantity.intValue();
    }
    return i;
  }
  
  public static void trackAddToCart(LineItem paramLineItem)
  {
    AdjustEvent localAdjustEvent = new AdjustEvent("fkg8l6");
    Variant localVariant = paramLineItem.variant;
    localAdjustEvent.addPartnerParameter("sku", localVariant.sku);
    localAdjustEvent.addPartnerParameter("name", localVariant.name);
    localAdjustEvent.addPartnerParameter("price", "" + localVariant.price);
    localAdjustEvent.addPartnerParameter("quantity", "" + paramLineItem.quantity);
    localAdjustEvent.addPartnerParameter("category", paramLineItem.getCategory());
    Adjust.trackEvent(localAdjustEvent);
  }
  
  public static void trackCompletedPayment(Context paramContext)
  {
    AdjustEvent localAdjustEvent = new AdjustEvent("304lnc");
    paramContext = (ICartApplication)paramContext.getApplicationContext();
    Order localOrder = paramContext.currentOrder;
    localAdjustEvent.addCallbackParameter("user_id", String.valueOf(localOrder.userId));
    localAdjustEvent.addCallbackParameter("order_no", localOrder.number);
    localAdjustEvent.addPartnerParameter("order_no", localOrder.number);
    localAdjustEvent.addPartnerParameter("stock_location_name", paramContext.getSharedPreferencesManager().getStockLocationName());
    localAdjustEvent.addPartnerParameter("order_total", "" + localOrder.total);
    localAdjustEvent.addPartnerParameter("order_tax_total", "" + localOrder.taxTotal);
    localAdjustEvent.addPartnerParameter("order_ship_total", "" + localOrder.shipTotal);
    Adjust.trackEvent(localAdjustEvent);
  }
  
  public static void trackContinueCheckout(Context paramContext)
  {
    AdjustEvent localAdjustEvent = new AdjustEvent("8bppy3");
    paramContext = ((ICartApplication)paramContext.getApplicationContext()).currentOrder;
    localAdjustEvent.addCallbackParameter("user_id", String.valueOf(paramContext.userId));
    localAdjustEvent.addPartnerParameter("order_total", "" + paramContext.total);
    localAdjustEvent.addPartnerParameter("item_total", "" + getOrderTotalItem(paramContext));
    Adjust.trackEvent(localAdjustEvent);
  }
  
  public static void trackContinuePayment(Context paramContext, String paramString)
  {
    AdjustEvent localAdjustEvent = new AdjustEvent("o01ghy");
    Order localOrder = ((ICartApplication)paramContext.getApplicationContext()).currentOrder;
    localAdjustEvent.addCallbackParameter("user_id", String.valueOf(localOrder.userId));
    localAdjustEvent.addPartnerParameter("item_total", "" + localOrder.itemTotal);
    localAdjustEvent.addPartnerParameter("ship_cost", "" + localOrder.shipTotal);
    localAdjustEvent.addPartnerParameter("order_total", "" + localOrder.total);
    paramContext = paramString;
    if (TextUtils.isEmpty(paramString)) {
      paramContext = "";
    }
    localAdjustEvent.addPartnerParameter("voucher_code", paramContext);
    localAdjustEvent.setRevenue(localOrder.total.doubleValue(), localOrder.currency);
    Adjust.trackEvent(localAdjustEvent);
  }
  
  public static void trackOpenApp()
  {
    Adjust.trackEvent(new AdjustEvent("6t3qcx"));
  }
  
  public static void trackPopularProduct()
  {
    Adjust.trackEvent(new AdjustEvent("ib38dh"));
  }
  
  public static void trackShared()
  {
    Adjust.trackEvent(new AdjustEvent("h1oled"));
  }
  
  public static void trackSignUp(User paramUser)
  {
    AdjustEvent localAdjustEvent = new AdjustEvent("ut6her");
    localAdjustEvent.addCallbackParameter("user_id", String.valueOf(paramUser.remoteId));
    localAdjustEvent.addPartnerParameter("email", paramUser.email);
    Adjust.trackEvent(localAdjustEvent);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/utils/AdjustUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */