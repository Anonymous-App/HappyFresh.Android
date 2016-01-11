package com.happyfresh.models;

import com.google.gson.annotations.SerializedName;

public class Coordinate
{
  @SerializedName("lat")
  public Double latitude;
  @SerializedName("lon")
  public Double longitude;
  
  public Coordinate(Double paramDouble1, Double paramDouble2)
  {
    this.latitude = paramDouble1;
    this.longitude = paramDouble2;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/Coordinate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */