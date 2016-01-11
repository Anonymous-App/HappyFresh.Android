package com.facebook.share.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;

public final class ShareVideo
  implements ShareModel
{
  public static final Parcelable.Creator<ShareVideo> CREATOR = new Parcelable.Creator()
  {
    public ShareVideo createFromParcel(Parcel paramAnonymousParcel)
    {
      return new ShareVideo(paramAnonymousParcel);
    }
    
    public ShareVideo[] newArray(int paramAnonymousInt)
    {
      return new ShareVideo[paramAnonymousInt];
    }
  };
  private final Uri localUrl;
  
  ShareVideo(Parcel paramParcel)
  {
    this.localUrl = ((Uri)paramParcel.readParcelable(Uri.class.getClassLoader()));
  }
  
  private ShareVideo(Builder paramBuilder)
  {
    this.localUrl = paramBuilder.localUrl;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  @Nullable
  public Uri getLocalUrl()
  {
    return this.localUrl;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeParcelable(this.localUrl, 0);
  }
  
  public static final class Builder
    implements ShareModelBuilder<ShareVideo, Builder>
  {
    private Uri localUrl;
    
    public ShareVideo build()
    {
      return new ShareVideo(this, null);
    }
    
    public Builder readFrom(Parcel paramParcel)
    {
      return readFrom((ShareVideo)paramParcel.readParcelable(ShareVideo.class.getClassLoader()));
    }
    
    public Builder readFrom(ShareVideo paramShareVideo)
    {
      if (paramShareVideo == null) {
        return this;
      }
      return setLocalUrl(paramShareVideo.getLocalUrl());
    }
    
    public Builder setLocalUrl(@Nullable Uri paramUri)
    {
      this.localUrl = paramUri;
      return this;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/share/model/ShareVideo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */