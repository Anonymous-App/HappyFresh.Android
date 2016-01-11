package com.happyfresh.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ProductResponse
  extends Response
{
  public List<Product> products;
  @SerializedName("search_id")
  public long searchId;
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/ProductResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */