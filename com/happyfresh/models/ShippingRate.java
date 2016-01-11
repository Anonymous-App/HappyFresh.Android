package com.happyfresh.models;

import com.google.gson.annotations.SerializedName;

public class ShippingRate
{
  public Double cost;
  @SerializedName("display_cost")
  public String displayCost;
  public String name;
  @SerializedName("id")
  public Long remoteId;
  public Boolean selected;
  @SerializedName("shipping_method_id")
  public Long shippingMethodId;
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/ShippingRate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */