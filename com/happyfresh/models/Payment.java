package com.happyfresh.models;

import com.google.gson.annotations.SerializedName;

public class Payment
{
  public Order order;
  @SerializedName("id")
  public long remoteId;
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/Payment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */