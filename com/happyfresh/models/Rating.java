package com.happyfresh.models;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class Rating
{
  public String comment;
  @SerializedName("country_id")
  public Long countryId;
  @SerializedName("need_improvement")
  public List<Integer> needImprovement = new ArrayList();
  @SerializedName("rateable_type")
  public String rateableType;
  public Double stars;
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/Rating.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */