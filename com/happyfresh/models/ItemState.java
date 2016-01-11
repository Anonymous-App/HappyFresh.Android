package com.happyfresh.models;

import com.google.gson.annotations.SerializedName;

public class ItemState
{
  public int delivered = 0;
  @SerializedName("on_hand")
  public int onHand = 0;
  @SerializedName("out_of_stock")
  public int outOfStock = 0;
  public int rejected = 0;
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/ItemState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */