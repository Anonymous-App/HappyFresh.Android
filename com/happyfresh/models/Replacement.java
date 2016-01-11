package com.happyfresh.models;

import com.google.gson.annotations.SerializedName;

public class Replacement
{
  @SerializedName("cost_price")
  public Double costPrice;
  public String currency;
  public Double price;
  public Double priority;
  @SerializedName("id")
  public long remoteId;
  @SerializedName("user_id")
  public Long userId;
  public Variant variant;
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/Replacement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */