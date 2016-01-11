package com.happyfresh.models;

import android.content.Context;
import android.text.TextUtils;
import com.google.gson.annotations.SerializedName;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Order
{
  @SerializedName("additional_tax_total")
  public Double additionalTaxTotal;
  @SerializedName("adjustment_total")
  public Double adjustmentTotal;
  public List<Adjustment> adjustments = new ArrayList();
  @SerializedName("applied_voucher")
  public String appliedVoucher;
  @SerializedName("banner")
  public String banner;
  @SerializedName("bill_address")
  public Address billAddress;
  public String channel;
  @SerializedName("checkout_steps")
  public List<String> checkoutSteps = new ArrayList();
  @SerializedName("completed_at")
  public Date completedAt;
  @SerializedName("country")
  public Country country;
  @SerializedName("created_at")
  public Date createdAt;
  public String currency;
  @SerializedName("display_additional_tax_total")
  public String displayAdditionalTaxTotal;
  @SerializedName("display_included_tax_total")
  public String displayIncludedTaxTotal;
  @SerializedName("display_item_total")
  public String displayItemTotal;
  @SerializedName("display_ship_total")
  public String displayShipTotal;
  @SerializedName("display_tax_total")
  public String displayTaxTotal;
  @SerializedName("display_total")
  public String displayTotal;
  public String email;
  @SerializedName("included_tax_total")
  public Double includedTaxTotal;
  public boolean isMustSync = false;
  @SerializedName("user_verified")
  public boolean isUserVerified;
  @SerializedName("item_total")
  public Double itemTotal;
  @SerializedName("line_items")
  public List<LineItem> lineItems = new ArrayList();
  public String number;
  @SerializedName("payment_state")
  public String paymentState;
  @SerializedName("payment_total")
  public double paymentTotal;
  public List<Payment> payments = new ArrayList();
  public Permission permissions;
  public Rating rating;
  @SerializedName("id")
  public long remoteId;
  @SerializedName("ship_address")
  public Address shipAddress;
  @SerializedName("ship_total")
  public Double shipTotal;
  @SerializedName("shipment_state")
  public String shipmentState;
  public List<Shipment> shipments = new ArrayList();
  @SerializedName("special_instructions")
  public String specialInstructions;
  public String state;
  @SerializedName("tax_total")
  public Double taxTotal;
  public String token;
  public Double total;
  @SerializedName("total_quantity")
  public Integer totalQuantity;
  @SerializedName("total_USD")
  public Double totalUSD;
  @SerializedName("updated_at")
  public Date updatedAt;
  @SerializedName("user_id")
  public Long userId;
  
  public static String getReplacementOptionGAText(String paramString)
  {
    if ("per_product".equalsIgnoreCase(paramString)) {
      return "Per Product";
    }
    if ("by_shopper".equalsIgnoreCase(paramString)) {
      return "Let Shopper Pick";
    }
    if ("by_call".equalsIgnoreCase(paramString)) {
      return "Call Me";
    }
    if ("dont_replace".equalsIgnoreCase(paramString)) {
      return "Do Not Replace";
    }
    return "";
  }
  
  public void clearCart()
  {
    if (this.lineItems != null) {
      this.lineItems.clear();
    }
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    do
    {
      return true;
      if ((paramObject == null) || (getClass() != paramObject.getClass())) {
        return false;
      }
      paramObject = (Order)paramObject;
      if (this.number == null) {
        break;
      }
    } while (this.number.equals(((Order)paramObject).number));
    for (;;)
    {
      return false;
      if (((Order)paramObject).number == null) {
        break;
      }
    }
  }
  
  public List<LineItem> findOutOfStockItems(List<LineItem> paramList)
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator1 = this.lineItems.iterator();
    while (localIterator1.hasNext())
    {
      LineItem localLineItem1 = (LineItem)localIterator1.next();
      int j = 0;
      Iterator localIterator2 = paramList.iterator();
      LineItem localLineItem2;
      do
      {
        i = j;
        if (!localIterator2.hasNext()) {
          break;
        }
        localLineItem2 = (LineItem)localIterator2.next();
      } while (localLineItem1.remoteId != localLineItem2.remoteId);
      int i = 1;
      if (i == 0)
      {
        localLineItem1.setOutOfStock();
        localArrayList.add(localLineItem1);
      }
    }
    return localArrayList;
  }
  
  public double getDeliveryFee()
  {
    double d1 = this.shipTotal.doubleValue();
    Iterator localIterator1 = this.shipments.iterator();
    if (localIterator1.hasNext())
    {
      Iterator localIterator2 = ((Shipment)localIterator1.next()).adjustments.iterator();
      Adjustment localAdjustment;
      for (double d2 = d1;; d2 += Double.parseDouble(localAdjustment.amount)) {
        do
        {
          d1 = d2;
          if (!localIterator2.hasNext()) {
            break;
          }
          localAdjustment = (Adjustment)localIterator2.next();
        } while ((!localAdjustment.eligible) || (localAdjustment.included));
      }
    }
    return d1;
  }
  
  public double getDiscount()
  {
    double d1 = 0.0D;
    Iterator localIterator = this.lineItems.iterator();
    Object localObject;
    if (localIterator.hasNext())
    {
      localObject = ((LineItem)localIterator.next()).adjustments.iterator();
      Adjustment localAdjustment;
      for (double d2 = d1;; d2 += Double.parseDouble(localAdjustment.amount)) {
        do
        {
          d1 = d2;
          if (!((Iterator)localObject).hasNext()) {
            break;
          }
          localAdjustment = (Adjustment)((Iterator)localObject).next();
        } while ((!localAdjustment.eligible) || (localAdjustment.included) || ("Spree::TaxRate".equals(localAdjustment.sourceType)));
      }
    }
    localIterator = this.adjustments.iterator();
    while (localIterator.hasNext())
    {
      localObject = (Adjustment)localIterator.next();
      if ((((Adjustment)localObject).eligible) && (!((Adjustment)localObject).included) && (!"Spree::TaxRate".equals(((Adjustment)localObject).sourceType))) {
        d1 += Double.parseDouble(((Adjustment)localObject).amount);
      }
    }
    return d1;
  }
  
  public LineItem getLineItem(long paramLong)
  {
    if (this.lineItems == null) {
      return null;
    }
    Iterator localIterator = this.lineItems.iterator();
    while (localIterator.hasNext())
    {
      LineItem localLineItem = (LineItem)localIterator.next();
      if (localLineItem.variantId.longValue() == paramLong) {
        return localLineItem;
      }
    }
    return null;
  }
  
  public LineItem getLineItem(long paramLong1, long paramLong2)
  {
    if (this.lineItems == null) {
      return null;
    }
    Iterator localIterator = this.lineItems.iterator();
    while (localIterator.hasNext())
    {
      LineItem localLineItem = (LineItem)localIterator.next();
      if ((localLineItem.variantId.longValue() == paramLong1) && (localLineItem.stockLocation.remoteId == paramLong2)) {
        return localLineItem;
      }
    }
    return null;
  }
  
  public int getQuantityInCart(long paramLong)
  {
    LineItem localLineItem = getLineItem(paramLong);
    if (localLineItem == null) {
      return 0;
    }
    return localLineItem.quantity.intValue();
  }
  
  public int getQuantityInCart(long paramLong1, long paramLong2)
  {
    LineItem localLineItem = getLineItem(paramLong1, paramLong2);
    if (localLineItem == null) {
      return 0;
    }
    return localLineItem.quantity.intValue();
  }
  
  public ShippingRate getShippingRateByShippingMethodId(Long paramLong)
  {
    if (this.shipments.size() != 0)
    {
      Object localObject1 = ((Shipment)this.shipments.get(0)).shippingRates;
      Object localObject2 = null;
      Iterator localIterator = ((List)localObject1).iterator();
      do
      {
        localObject1 = localObject2;
        if (!localIterator.hasNext()) {
          break;
        }
        localObject1 = (ShippingRate)localIterator.next();
      } while (((ShippingRate)localObject1).shippingMethodId != paramLong);
      return (ShippingRate)localObject1;
    }
    return null;
  }
  
  public String getStatus(Context paramContext)
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("h:mma");
    if ((isCompleted()) && (this.shipments.size() > 0))
    {
      Object localObject = (Shipment)this.shipments.get(0);
      if (localObject != null)
      {
        Job localJob = ((Shipment)localObject).deliveryJob;
        if (localJob != null)
        {
          if (localJob.isFinished())
          {
            StringBuilder localStringBuilder = new StringBuilder().append(paramContext.getString(2131165487)).append(" ");
            if (TextUtils.isEmpty(localJob.receiver)) {}
            for (localObject = "";; localObject = paramContext.getString(2131165291) + " " + localJob.receiver) {
              return (String)localObject + " " + paramContext.getString(2131165287) + " " + localSimpleDateFormat.format(localJob.endTime);
            }
          }
          if (localJob.isFoundAddress()) {
            return paramContext.getString(2131165480);
          }
          if (localJob.isAccepted()) {
            return paramContext.getString(2131165489);
          }
          return paramContext.getString(2131165481);
        }
        if (((Shipment)localObject).shoppingJob != null) {
          return paramContext.getString(2131165481);
        }
      }
    }
    else if (isCanceled())
    {
      return paramContext.getString(2131165486);
    }
    return paramContext.getString(2131165492);
  }
  
  public double getTotalDiscount()
  {
    double d = getDiscount();
    Iterator localIterator = this.adjustments.iterator();
    while (localIterator.hasNext())
    {
      Adjustment localAdjustment = (Adjustment)localIterator.next();
      if ((localAdjustment.eligible) && (!localAdjustment.included) && (("Spree::RejectedUnit".equals(localAdjustment.sourceType)) || ("Spree::UnavailableUnit".equals(localAdjustment.sourceType)) || ("Spree::ReplacementUnit".equals(localAdjustment.sourceType)))) {
        d -= Double.parseDouble(localAdjustment.amount);
      }
    }
    return d;
  }
  
  public double getTotalOutOfStock()
  {
    double d2 = 0.0D;
    Iterator localIterator = this.adjustments.iterator();
    Adjustment localAdjustment;
    do
    {
      d1 = d2;
      if (!localIterator.hasNext()) {
        break;
      }
      localAdjustment = (Adjustment)localIterator.next();
    } while ((!localAdjustment.eligible) || (localAdjustment.included) || (!"Spree::UnavailableUnit".equals(localAdjustment.sourceType)));
    double d1 = Double.parseDouble(localAdjustment.amount);
    return d1;
  }
  
  public double getTotalRejected()
  {
    double d2 = 0.0D;
    Iterator localIterator = this.adjustments.iterator();
    Adjustment localAdjustment;
    do
    {
      d1 = d2;
      if (!localIterator.hasNext()) {
        break;
      }
      localAdjustment = (Adjustment)localIterator.next();
    } while ((!localAdjustment.eligible) || (localAdjustment.included) || (!"Spree::RejectedUnit".equals(localAdjustment.sourceType)));
    double d1 = Double.parseDouble(localAdjustment.amount);
    return d1;
  }
  
  public double getTotalReplacement()
  {
    double d2 = 0.0D;
    Iterator localIterator = this.adjustments.iterator();
    Adjustment localAdjustment;
    do
    {
      d1 = d2;
      if (!localIterator.hasNext()) {
        break;
      }
      localAdjustment = (Adjustment)localIterator.next();
    } while ((!localAdjustment.eligible) || (localAdjustment.included) || (!"Spree::ReplacementUnit".equals(localAdjustment.sourceType)));
    double d1 = Double.parseDouble(localAdjustment.amount);
    return d1;
  }
  
  public int hashCode()
  {
    return this.number.hashCode() * 31;
  }
  
  public boolean isActive()
  {
    boolean bool2 = true;
    boolean bool1;
    if ((isCompleted()) && (this.shipments.size() > 0))
    {
      Job localJob = ((Shipment)this.shipments.get(0)).deliveryJob;
      bool1 = bool2;
      if (localJob != null)
      {
        bool1 = bool2;
        if (localJob.isFinished()) {
          bool1 = false;
        }
      }
    }
    do
    {
      return bool1;
      bool1 = bool2;
    } while (!isCanceled());
    return false;
  }
  
  public boolean isAddress()
  {
    return this.state.equalsIgnoreCase("address");
  }
  
  public boolean isCanceled()
  {
    return this.state.equalsIgnoreCase("canceled");
  }
  
  public boolean isCart()
  {
    return this.state.equalsIgnoreCase("cart");
  }
  
  public boolean isCompleted()
  {
    return this.state.equalsIgnoreCase("complete");
  }
  
  public boolean isEmptyCart()
  {
    if (this.lineItems == null) {
      return true;
    }
    return this.lineItems.isEmpty();
  }
  
  public boolean isPayment()
  {
    return this.state.equalsIgnoreCase("payment");
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/Order.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */