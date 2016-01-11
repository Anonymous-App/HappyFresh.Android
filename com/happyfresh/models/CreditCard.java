package com.happyfresh.models;

import com.google.gson.annotations.SerializedName;

public class CreditCard
{
  public String month;
  public String name;
  public String number;
  @SerializedName("id")
  public Long remoteId;
  public String verificationValue;
  public String year;
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/CreditCard.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */