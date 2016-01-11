package com.happyfresh.models;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

public class Adjustment
{
  @SerializedName("adjustable_id")
  public int adjustableId;
  @SerializedName("adjustable_type")
  public String adjustableType;
  public String amount;
  @SerializedName("created_at")
  public Date created;
  @SerializedName("display_amount")
  public String displayAmount;
  public boolean eligible;
  public boolean included;
  public String label;
  public String mandatory;
  @SerializedName("id")
  public long remoteId;
  @SerializedName("source_id")
  public int sourceId;
  @SerializedName("source_type")
  public String sourceType;
  @SerializedName("updated_at")
  public Date updated;
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/Adjustment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */