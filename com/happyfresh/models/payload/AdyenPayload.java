package com.happyfresh.models.payload;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.happyfresh.models.PaymentService;

public class AdyenPayload
  extends PaymentPayload
  implements Parcelable
{
  public static final Parcelable.Creator<AdyenPayload> CREATOR = new Parcelable.Creator()
  {
    public AdyenPayload createFromParcel(Parcel paramAnonymousParcel)
    {
      return new AdyenPayload(paramAnonymousParcel, null);
    }
    
    public AdyenPayload[] newArray(int paramAnonymousInt)
    {
      return new AdyenPayload[0];
    }
  };
  public String orderNumber;
  public String orderToken;
  public String paymentMethod;
  
  public AdyenPayload() {}
  
  private AdyenPayload(Parcel paramParcel)
  {
    this.orderToken = paramParcel.readString();
    this.orderNumber = paramParcel.readString();
    this.paymentMethod = paramParcel.readString();
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public PaymentService getType()
  {
    return PaymentService.ADYEN;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.orderToken);
    paramParcel.writeString(this.orderNumber);
    paramParcel.writeString(this.paymentMethod);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/payload/AdyenPayload.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */