package com.happyfresh.models;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

public class StoreCredit
{
  public Double amount;
  public Country country;
  @SerializedName("created_at")
  public Date createdAt;
  @SerializedName("original_amount")
  public Double originalAmount;
  @SerializedName("id")
  public Long remoteId;
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/StoreCredit.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */