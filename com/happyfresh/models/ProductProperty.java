package com.happyfresh.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class ProductProperty
  implements Parcelable
{
  public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
  {
    public ProductProperty createFromParcel(Parcel paramAnonymousParcel)
    {
      ProductProperty localProductProperty = new ProductProperty();
      localProductProperty.remoteId = paramAnonymousParcel.readLong();
      localProductProperty.productId = ((Long)paramAnonymousParcel.readValue(Long.class.getClassLoader()));
      localProductProperty.propertyId = ((Long)paramAnonymousParcel.readValue(Long.class.getClassLoader()));
      localProductProperty.value = ((String)paramAnonymousParcel.readValue(String.class.getClassLoader()));
      localProductProperty.propertyName = ((String)paramAnonymousParcel.readValue(String.class.getClassLoader()));
      return localProductProperty;
    }
    
    public ProductProperty[] newArray(int paramAnonymousInt)
    {
      return new ProductProperty[paramAnonymousInt];
    }
  };
  @SerializedName("product_id")
  public Long productId;
  @SerializedName("property_id")
  public Long propertyId;
  @SerializedName("property_name")
  public String propertyName = "";
  @SerializedName("id")
  public long remoteId;
  public String value;
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean isPackage()
  {
    return "package".equalsIgnoreCase(this.propertyName);
  }
  
  public boolean isSize()
  {
    return "size".equalsIgnoreCase(this.propertyName);
  }
  
  public boolean isUnit()
  {
    return "unit".equalsIgnoreCase(this.propertyName);
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeLong(this.remoteId);
    paramParcel.writeValue(this.productId);
    paramParcel.writeValue(this.propertyId);
    paramParcel.writeValue(this.value);
    paramParcel.writeValue(this.propertyName);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/ProductProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */