package com.happyfresh.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class RecommendedListResponse
  extends Response
{
  @SerializedName("shopping_lists")
  public List<ShoppingList> shoppingLists;
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/RecommendedListResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */