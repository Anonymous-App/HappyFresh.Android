package com.happyfresh.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class Photo
  implements Parcelable
{
  public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
  {
    public Photo createFromParcel(Parcel paramAnonymousParcel)
    {
      Photo localPhoto = new Photo();
      localPhoto.url = paramAnonymousParcel.readString();
      localPhoto.mediumUrl = paramAnonymousParcel.readString();
      localPhoto.thumbnailUrl = paramAnonymousParcel.readString();
      return localPhoto;
    }
    
    public Photo[] newArray(int paramAnonymousInt)
    {
      return new Photo[paramAnonymousInt];
    }
  };
  @SerializedName("medium_url")
  public String mediumUrl;
  @SerializedName("thumb_url")
  public String thumbnailUrl;
  public String url;
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.url);
    paramParcel.writeString(this.mediumUrl);
    paramParcel.writeString(this.thumbnailUrl);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/Photo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */