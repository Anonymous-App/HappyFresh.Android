package com.happyfresh.models;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class SubDistrictResponse
  extends Response
{
  @SerializedName("sub_districts")
  public List<SubDistrict> subDistricts = new ArrayList();
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/SubDistrictResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */