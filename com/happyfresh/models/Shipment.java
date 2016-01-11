package com.happyfresh.models;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class Shipment
{
  @SerializedName("adjustments")
  public List<Adjustment> adjustments;
  @SerializedName("canceled_slot")
  public Slot canceledSlot;
  @SerializedName("delivery_job")
  public Job deliveryJob;
  public List<ManifestItem> manifest = new ArrayList();
  public String number;
  public Order order;
  @SerializedName("id")
  public long remoteId;
  @SerializedName("selected_shipping_rate")
  public ShippingRate selectedShippingRate;
  @SerializedName("shipping_rates")
  public List<ShippingRate> shippingRates = new ArrayList();
  @SerializedName("shopping_job")
  public Job shoppingJob;
  public Slot slot;
  @SerializedName("stock_location_name")
  public String stockLocationName;
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/Shipment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */