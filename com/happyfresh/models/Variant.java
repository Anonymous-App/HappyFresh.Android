package com.happyfresh.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Variant
  implements Parcelable
{
  public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
  {
    public Variant createFromParcel(Parcel paramAnonymousParcel)
    {
      boolean bool2 = true;
      Variant localVariant = new Variant();
      localVariant.remoteId = paramAnonymousParcel.readLong();
      localVariant.name = ((String)paramAnonymousParcel.readValue(String.class.getClassLoader()));
      localVariant.sku = ((String)paramAnonymousParcel.readValue(String.class.getClassLoader()));
      localVariant.price = ((Double)paramAnonymousParcel.readValue(Double.class.getClassLoader()));
      localVariant.weight = ((Double)paramAnonymousParcel.readValue(Double.class.getClassLoader()));
      localVariant.height = ((Double)paramAnonymousParcel.readValue(Double.class.getClassLoader()));
      localVariant.width = ((Double)paramAnonymousParcel.readValue(Double.class.getClassLoader()));
      localVariant.depth = ((Double)paramAnonymousParcel.readValue(Double.class.getClassLoader()));
      if (((Byte)paramAnonymousParcel.readValue(Byte.class.getClassLoader())).byteValue() != 0)
      {
        bool1 = true;
        localVariant.isMaster = Boolean.valueOf(bool1);
        localVariant.costPrice = ((Double)paramAnonymousParcel.readValue(Double.class.getClassLoader()));
        localVariant.slug = ((String)paramAnonymousParcel.readValue(String.class.getClassLoader()));
        localVariant.description = ((String)paramAnonymousParcel.readValue(String.class.getClassLoader()));
        if (((Byte)paramAnonymousParcel.readValue(Byte.class.getClassLoader())).byteValue() == 0) {
          break label350;
        }
        bool1 = true;
        label239:
        localVariant.trackInventory = Boolean.valueOf(bool1);
        localVariant.displayPrice = ((String)paramAnonymousParcel.readValue(String.class.getClassLoader()));
        localVariant.optionsText = ((String)paramAnonymousParcel.readValue(String.class.getClassLoader()));
        if (((Byte)paramAnonymousParcel.readValue(Byte.class.getClassLoader())).byteValue() == 0) {
          break label355;
        }
      }
      label350:
      label355:
      for (boolean bool1 = bool2;; bool1 = false)
      {
        localVariant.inStock = Boolean.valueOf(bool1);
        localVariant.images = paramAnonymousParcel.readArrayList(Image.class.getClassLoader());
        localVariant.product = ((Product)paramAnonymousParcel.readParcelable(Product.class.getClassLoader()));
        return localVariant;
        bool1 = false;
        break;
        bool1 = false;
        break label239;
      }
    }
    
    public Variant[] newArray(int paramAnonymousInt)
    {
      return new Variant[paramAnonymousInt];
    }
  };
  @SerializedName("cost_price")
  public Double costPrice;
  public Double depth;
  public String description;
  @SerializedName("display_price")
  public String displayPrice;
  public Double height;
  public List<Image> images;
  @SerializedName("in_stock")
  public Boolean inStock;
  @SerializedName("is_master")
  public Boolean isMaster;
  public String name;
  @SerializedName("option_values")
  public List<String> optionValues;
  @SerializedName("options_text")
  public String optionsText;
  public Double price;
  @SerializedName("product")
  public Product product;
  @SerializedName("product_id")
  public Long productId;
  @SerializedName("id")
  public long remoteId;
  public String sku;
  public String slug;
  @SerializedName("track_inventory")
  public Boolean trackInventory;
  public Double weight;
  public Double width;
  
  public int describeContents()
  {
    return 0;
  }
  
  public String getFirstImageProductUrl()
  {
    Object localObject2 = null;
    Object localObject1 = localObject2;
    if (!this.images.isEmpty())
    {
      Image localImage = (Image)this.images.get(0);
      localObject1 = localObject2;
      if (localImage != null) {
        localObject1 = localImage.productUrl;
      }
    }
    return (String)localObject1;
  }
  
  public String getFirstImageSmallUrl()
  {
    Object localObject2 = null;
    Object localObject1 = localObject2;
    if (!this.images.isEmpty())
    {
      Image localImage = (Image)this.images.get(0);
      localObject1 = localObject2;
      if (localImage != null) {
        localObject1 = localImage.smallUrl;
      }
    }
    return (String)localObject1;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    int j = 1;
    paramParcel.writeLong(this.remoteId);
    paramParcel.writeValue(this.name);
    paramParcel.writeValue(this.sku);
    paramParcel.writeValue(this.price);
    paramParcel.writeValue(this.weight);
    paramParcel.writeValue(this.height);
    paramParcel.writeValue(this.width);
    paramParcel.writeValue(this.depth);
    if (this.isMaster.booleanValue())
    {
      i = 1;
      paramParcel.writeValue(Byte.valueOf((byte)i));
      paramParcel.writeValue(this.costPrice);
      paramParcel.writeValue(this.slug);
      paramParcel.writeValue(this.description);
      if (!this.trackInventory.booleanValue()) {
        break label194;
      }
      i = 1;
      label124:
      paramParcel.writeValue(Byte.valueOf((byte)i));
      paramParcel.writeValue(this.displayPrice);
      paramParcel.writeValue(this.optionsText);
      if (!this.inStock.booleanValue()) {
        break label199;
      }
    }
    label194:
    label199:
    for (int i = j;; i = 0)
    {
      paramParcel.writeValue(Byte.valueOf((byte)i));
      paramParcel.writeList(this.images);
      paramParcel.writeParcelable(this.product, paramInt);
      return;
      i = 0;
      break;
      i = 0;
      break label124;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/Variant.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */