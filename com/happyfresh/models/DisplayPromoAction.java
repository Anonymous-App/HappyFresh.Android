package com.happyfresh.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class DisplayPromoAction
  implements Parcelable
{
  public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
  {
    public DisplayPromoAction createFromParcel(Parcel paramAnonymousParcel)
    {
      DisplayPromoAction localDisplayPromoAction = new DisplayPromoAction();
      localDisplayPromoAction.type = ((String)paramAnonymousParcel.readValue(String.class.getClassLoader()));
      localDisplayPromoAction.displayShortText = ((String)paramAnonymousParcel.readValue(String.class.getClassLoader()));
      localDisplayPromoAction.displayLongText = ((String)paramAnonymousParcel.readValue(String.class.getClassLoader()));
      localDisplayPromoAction.position = paramAnonymousParcel.readInt();
      return localDisplayPromoAction;
    }
    
    public DisplayPromoAction[] newArray(int paramAnonymousInt)
    {
      return new DisplayPromoAction[paramAnonymousInt];
    }
  };
  public static final String PROMO_TYPE_ADJUSTMENT = "Spree::Promotion::Actions::CreateAdjustment";
  public static final String PROMO_TYPE_FREE_DELIVERY = "Spree::Promotion::Actions::FreeShipping";
  @SerializedName("display_long_text")
  public String displayLongText;
  @SerializedName("display_short_text")
  public String displayShortText;
  public int position;
  public String type;
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean isPromoAdjustment()
  {
    return "Spree::Promotion::Actions::CreateAdjustment".equals(this.type);
  }
  
  public boolean isPromoFreeDelivery()
  {
    return "Spree::Promotion::Actions::FreeShipping".equals(this.type);
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeValue(this.type);
    paramParcel.writeValue(this.displayShortText);
    paramParcel.writeValue(this.displayLongText);
    paramParcel.writeInt(this.position);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/DisplayPromoAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */