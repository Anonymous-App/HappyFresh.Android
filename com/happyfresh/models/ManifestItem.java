package com.happyfresh.models;

import com.google.gson.annotations.SerializedName;

public class ManifestItem
{
  public int quantity;
  @SerializedName("rejected_reason")
  public String rejectedReason;
  @SerializedName("replacement_id")
  public Long replacementId;
  public ItemState states;
  @SerializedName("variant_id")
  public Long variantId;
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/ManifestItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */