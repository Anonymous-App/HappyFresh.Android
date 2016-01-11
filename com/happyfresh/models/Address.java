package com.happyfresh.models;

import android.content.Context;
import android.text.TextUtils;
import com.google.gson.annotations.SerializedName;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.managers.SharedPreferencesManager;
import java.util.Date;
import java.util.Locale;

public class Address
{
  public String address1;
  public String address2;
  @SerializedName("address_type")
  public String addressType;
  public String alley;
  @SerializedName("alternative_phone")
  public String alternativePhone;
  public String city;
  public String company;
  @SerializedName("company_tax_id")
  public String companyTaxID;
  public Country country;
  @SerializedName("country_id")
  public Long countryId;
  @SerializedName("created_at")
  public Date createdAt;
  @SerializedName("delivery_instruction")
  public String deliveryInstruction;
  @SerializedName("firstname")
  public String firstName;
  public String floor;
  @SerializedName("full_name")
  public String fullName;
  @SerializedName("primary")
  public boolean isPrimary;
  public String lane;
  @SerializedName("lastname")
  public String lastName;
  public String number;
  public String phone;
  @SerializedName("id")
  public long remoteId;
  public State state;
  @SerializedName("state_id")
  public Long stateId;
  @SerializedName("state_name")
  public String stateName;
  @SerializedName("state_text")
  public String stateText;
  @SerializedName("sub_district")
  public SubDistrict subDistrict;
  public String unit;
  @SerializedName("update_at")
  public Date updatedAt;
  @SerializedName("user_id")
  public Long userId;
  public String zipcode;
  
  private boolean isCountryTaiwan()
  {
    return (this.country != null) && (this.country.getCode().toLowerCase().equals("tw"));
  }
  
  private String toStringChineseTaiwan(Context paramContext, boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if (this.subDistrict != null)
    {
      localStringBuilder.append(this.subDistrict.toStringChineseTaiwan());
      localStringBuilder.append("\n");
    }
    if (!TextUtils.isEmpty(this.lane))
    {
      localStringBuilder.append(this.lane);
      localStringBuilder.append(paramContext.getString(2131165428));
    }
    if (!TextUtils.isEmpty(this.alley))
    {
      localStringBuilder.append(this.alley);
      localStringBuilder.append(paramContext.getString(2131165280));
    }
    if (((!TextUtils.isEmpty(this.lane)) || (!TextUtils.isEmpty(this.alley))) && (!paramBoolean)) {
      localStringBuilder.append("\n");
    }
    localStringBuilder.append(this.number);
    localStringBuilder.append(paramContext.getString(2131165465));
    localStringBuilder.append(this.floor);
    localStringBuilder.append(paramContext.getString(2131165395));
    if (!TextUtils.isEmpty(this.unit))
    {
      localStringBuilder.append(this.unit);
      localStringBuilder.append(paramContext.getString(2131165622));
    }
    return localStringBuilder.toString();
  }
  
  private String toStringEnglishTaiwan(Context paramContext, boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramContext.getString(2131165395));
    localStringBuilder.append(" ");
    localStringBuilder.append(this.floor);
    if (!TextUtils.isEmpty(this.unit))
    {
      localStringBuilder.append(", ");
      localStringBuilder.append(paramContext.getString(2131165622));
      localStringBuilder.append(" ");
      localStringBuilder.append(this.unit);
    }
    localStringBuilder.append(", ");
    localStringBuilder.append(paramContext.getString(2131165465));
    localStringBuilder.append(" ");
    localStringBuilder.append(this.number);
    if (!paramBoolean) {
      localStringBuilder.append("\n");
    }
    if (!TextUtils.isEmpty(this.alley))
    {
      if (paramBoolean) {
        localStringBuilder.append(", ");
      }
      localStringBuilder.append(paramContext.getString(2131165280));
      localStringBuilder.append(" ");
      localStringBuilder.append(this.alley);
      if (!TextUtils.isEmpty(this.lane)) {
        localStringBuilder.append(", ");
      }
    }
    if (!TextUtils.isEmpty(this.lane))
    {
      if ((paramBoolean) && (TextUtils.isEmpty(this.alley))) {
        localStringBuilder.append(", ");
      }
      localStringBuilder.append(paramContext.getString(2131165428));
      localStringBuilder.append(" ");
      localStringBuilder.append(this.lane);
    }
    if ((!TextUtils.isEmpty(this.lane)) || (!TextUtils.isEmpty(this.alley)) || (paramBoolean)) {
      localStringBuilder.append("\n");
    }
    if (this.subDistrict != null) {
      localStringBuilder.append(this.subDistrict.toString());
    }
    return localStringBuilder.toString();
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.address1);
    if (!TextUtils.isEmpty(this.address2))
    {
      localStringBuilder.append("\n");
      localStringBuilder.append(this.address2);
    }
    if (this.subDistrict != null)
    {
      localStringBuilder.append("\n");
      localStringBuilder.append(this.subDistrict.toString());
    }
    if (this.state != null)
    {
      localStringBuilder.append("\n");
      localStringBuilder.append(this.state.name);
    }
    return localStringBuilder.toString();
  }
  
  public String toString(ICartApplication paramICartApplication)
  {
    Context localContext = paramICartApplication.getApplicationContext();
    if (isCountryTaiwan())
    {
      if (paramICartApplication.getSharedPreferencesManager().getLocale().equals(Locale.TAIWAN)) {
        return toStringChineseTaiwan(localContext, false);
      }
      return toStringEnglishTaiwan(localContext, false);
    }
    return toString();
  }
  
  public String toStringTwoLines(ICartApplication paramICartApplication)
  {
    Context localContext = paramICartApplication.getApplicationContext();
    if (isCountryTaiwan())
    {
      if (paramICartApplication.getSharedPreferencesManager().getLocale().equals(Locale.TAIWAN)) {
        return toStringChineseTaiwan(localContext, true);
      }
      return toStringEnglishTaiwan(localContext, true);
    }
    return toString();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/Address.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */