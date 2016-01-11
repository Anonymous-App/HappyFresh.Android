package com.happyfresh.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.From;
import com.google.gson.annotations.SerializedName;

@Table(name="user_config")
public class UserConfig
  extends Model
{
  @SerializedName("discount_code_description")
  public String discountCodeDescription;
  @SerializedName("discount_code_title")
  public String discountCodeTitle;
  @SerializedName("first_order_discount")
  public String firstOrderDiscount;
  @Column(name="invitation_text")
  @SerializedName("invitation_text")
  public String invitationText;
  @SerializedName("store_credit_amount_total")
  public String storeCreditAmountTotal;
  @SerializedName("store_credit_original_amount_total")
  public String storeCreditOriginalAmountTotal;
  @Column(name="user_id")
  public Long userId;
  
  public static void deleteConfig()
  {
    new Delete().from(UserConfig.class).execute();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/UserConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */