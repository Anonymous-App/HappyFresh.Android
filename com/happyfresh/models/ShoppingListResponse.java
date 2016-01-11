package com.happyfresh.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ShoppingListResponse
{
  @SerializedName("previously_purchased")
  public List<Product> previouslyPurchasedProducts;
  public List<Product> products;
  @SerializedName("shopping_list")
  public ShoppingList shoppingList;
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/ShoppingListResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */