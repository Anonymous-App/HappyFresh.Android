package com.happyfresh.models;

import com.google.gson.annotations.SerializedName;

public abstract class Response
{
  public Integer count;
  @SerializedName("current_page")
  public Integer currentPage;
  public Integer pages;
  @SerializedName("per_page")
  public Integer perPage;
  @SerializedName("total_count")
  public Integer totalCount;
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/Response.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */