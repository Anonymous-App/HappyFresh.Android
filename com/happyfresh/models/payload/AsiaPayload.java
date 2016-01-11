package com.happyfresh.models.payload;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import com.happyfresh.models.PaymentService;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.apache.http.util.EncodingUtils;

public class AsiaPayload
  extends PaymentPayload
  implements Parcelable
{
  public static final Parcelable.Creator<AsiaPayload> CREATOR = new Parcelable.Creator()
  {
    public AsiaPayload createFromParcel(Parcel paramAnonymousParcel)
    {
      return new AsiaPayload(paramAnonymousParcel, null);
    }
    
    public AsiaPayload[] newArray(int paramAnonymousInt)
    {
      return new AsiaPayload[0];
    }
  };
  public Double amount;
  public String cancelUrl;
  public String currCode;
  public String failUrl;
  public String lang;
  public Long merchantId;
  public String mpsMode;
  public String orderRef;
  public String payMethod;
  public String payType;
  @SerializedName("payment_url")
  public String paymentUrl;
  public String secureHash;
  public String successUrl;
  
  public AsiaPayload() {}
  
  private AsiaPayload(Parcel paramParcel)
  {
    this.paymentUrl = paramParcel.readString();
    this.successUrl = paramParcel.readString();
    this.failUrl = paramParcel.readString();
    this.cancelUrl = paramParcel.readString();
    this.merchantId = Long.valueOf(paramParcel.readLong());
    this.amount = Double.valueOf(paramParcel.readDouble());
    this.orderRef = paramParcel.readString();
    this.currCode = paramParcel.readString();
    this.mpsMode = paramParcel.readString();
    this.payType = paramParcel.readString();
    this.payMethod = paramParcel.readString();
    this.secureHash = paramParcel.readString();
    this.lang = paramParcel.readString();
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public PaymentService getType()
  {
    return PaymentService.ASIAPAY;
  }
  
  public byte[] toByteArray()
    throws UnsupportedEncodingException
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("successUrl=" + URLEncoder.encode(this.successUrl, "UTF-8"));
    localStringBuilder.append("&failUrl=" + URLEncoder.encode(this.failUrl, "UTF-8"));
    localStringBuilder.append("&cancelUrl=" + URLEncoder.encode(this.cancelUrl, "UTF-8"));
    localStringBuilder.append("&merchantId=" + this.merchantId);
    localStringBuilder.append("&amount=" + this.amount);
    localStringBuilder.append("&orderRef=" + this.orderRef);
    localStringBuilder.append("&currCode=" + this.currCode);
    localStringBuilder.append("&mpsMode=" + this.mpsMode);
    localStringBuilder.append("&payType=" + this.payType);
    localStringBuilder.append("&payMethod=" + this.payMethod);
    localStringBuilder.append("&secureHash=" + this.secureHash);
    localStringBuilder.append("&lang=" + this.lang);
    return EncodingUtils.getBytes(localStringBuilder.toString(), "BASE64");
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.paymentUrl);
    paramParcel.writeString(this.successUrl);
    paramParcel.writeString(this.failUrl);
    paramParcel.writeString(this.cancelUrl);
    paramParcel.writeLong(this.merchantId.longValue());
    paramParcel.writeDouble(this.amount.doubleValue());
    paramParcel.writeString(this.orderRef);
    paramParcel.writeString(this.currCode);
    paramParcel.writeString(this.mpsMode);
    paramParcel.writeString(this.payType);
    paramParcel.writeString(this.payMethod);
    paramParcel.writeString(this.secureHash);
    paramParcel.writeString(this.lang);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/payload/AsiaPayload.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */