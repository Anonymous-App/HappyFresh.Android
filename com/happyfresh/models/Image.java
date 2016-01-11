package com.happyfresh.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.Date;

public class Image
  implements Parcelable
{
  public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
  {
    public Image createFromParcel(Parcel paramAnonymousParcel)
    {
      Image localImage = new Image();
      localImage.remoteId = Long.valueOf(paramAnonymousParcel.readLong());
      localImage.position = ((Integer)paramAnonymousParcel.readValue(Integer.class.getClassLoader()));
      localImage.attachmentContentType = ((String)paramAnonymousParcel.readValue(String.class.getClassLoader()));
      localImage.attachmentFileName = ((String)paramAnonymousParcel.readValue(String.class.getClassLoader()));
      localImage.type = ((String)paramAnonymousParcel.readValue(String.class.getClassLoader()));
      Long localLong = (Long)paramAnonymousParcel.readValue(Long.class.getClassLoader());
      if (localLong != null) {
        localImage.attachmentUpdatedAt = new Date(localLong.longValue());
      }
      localImage.attachmentWidth = ((Integer)paramAnonymousParcel.readValue(Integer.class.getClassLoader()));
      localImage.attachmentHeight = ((Integer)paramAnonymousParcel.readValue(Integer.class.getClassLoader()));
      localImage.alt = ((String)paramAnonymousParcel.readValue(String.class.getClassLoader()));
      localImage.viewableType = ((String)paramAnonymousParcel.readValue(String.class.getClassLoader()));
      localImage.viewableId = ((Long)paramAnonymousParcel.readValue(Long.class.getClassLoader()));
      localImage.miniUrl = ((String)paramAnonymousParcel.readValue(String.class.getClassLoader()));
      localImage.smallUrl = ((String)paramAnonymousParcel.readValue(String.class.getClassLoader()));
      localImage.productUrl = ((String)paramAnonymousParcel.readValue(String.class.getClassLoader()));
      localImage.largeUrl = ((String)paramAnonymousParcel.readValue(String.class.getClassLoader()));
      localImage.originalUrl = ((String)paramAnonymousParcel.readValue(String.class.getClassLoader()));
      return localImage;
    }
    
    public Variant[] newArray(int paramAnonymousInt)
    {
      return new Variant[paramAnonymousInt];
    }
  };
  public String alt;
  @SerializedName("attachment_content_type")
  public String attachmentContentType;
  @SerializedName("attachment_file_name")
  public String attachmentFileName;
  @SerializedName("attachment_height")
  public Integer attachmentHeight;
  @SerializedName("attachment_updated_at")
  public Date attachmentUpdatedAt;
  @SerializedName("attachment_width")
  public Integer attachmentWidth;
  @SerializedName("large_url")
  public String largeUrl;
  @SerializedName("mini_url")
  public String miniUrl;
  @SerializedName("original_url")
  public String originalUrl;
  public Integer position;
  @SerializedName("product_url")
  public String productUrl;
  @SerializedName("id")
  public Long remoteId;
  @SerializedName("small_url")
  public String smallUrl;
  public String type;
  @SerializedName("viewable_id")
  public Long viewableId;
  @SerializedName("viewable_type")
  public String viewableType;
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeLong(this.remoteId.longValue());
    paramParcel.writeValue(this.position);
    paramParcel.writeValue(this.attachmentContentType);
    paramParcel.writeValue(this.attachmentFileName);
    paramParcel.writeValue(this.type);
    paramParcel.writeValue(Long.valueOf(this.attachmentUpdatedAt.getTime()));
    paramParcel.writeValue(this.attachmentWidth);
    paramParcel.writeValue(this.attachmentHeight);
    paramParcel.writeValue(this.alt);
    paramParcel.writeValue(this.viewableType);
    paramParcel.writeValue(this.viewableId);
    paramParcel.writeValue(this.miniUrl);
    paramParcel.writeValue(this.smallUrl);
    paramParcel.writeValue(this.productUrl);
    paramParcel.writeValue(this.largeUrl);
    paramParcel.writeValue(this.originalUrl);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/Image.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */