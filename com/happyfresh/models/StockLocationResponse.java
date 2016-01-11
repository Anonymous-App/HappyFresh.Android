package com.happyfresh.models;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class StockLocationResponse
  extends Response
{
  @SerializedName("stock_locations")
  public ArrayList<StockLocation> stockLocations = new ArrayList();
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/StockLocationResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */