package com.happyfresh.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MultipleLineItemResponse
{
  @SerializedName("failed_items")
  public List<FailedItem> failedItems;
  @SerializedName("line_items")
  public List<LineItem> lineItems;
  @SerializedName("order")
  public Order order;
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/MultipleLineItemResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */