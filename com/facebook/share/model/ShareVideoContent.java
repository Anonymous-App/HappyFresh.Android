package com.facebook.share.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;

public final class ShareVideoContent
  extends ShareContent<ShareVideoContent, Builder>
  implements ShareModel
{
  public static final Parcelable.Creator<ShareVideoContent> CREATOR = new Parcelable.Creator()
  {
    public ShareVideoContent createFromParcel(Parcel paramAnonymousParcel)
    {
      return new ShareVideoContent(paramAnonymousParcel);
    }
    
    public ShareVideoContent[] newArray(int paramAnonymousInt)
    {
      return new ShareVideoContent[paramAnonymousInt];
    }
  };
  private final String contentDescription;
  private final String contentTitle;
  private final SharePhoto previewPhoto;
  private final ShareVideo video;
  
  ShareVideoContent(Parcel paramParcel)
  {
    super(paramParcel);
    this.contentDescription = paramParcel.readString();
    this.contentTitle = paramParcel.readString();
    SharePhoto.Builder localBuilder = new SharePhoto.Builder().readFrom(paramParcel);
    if ((localBuilder.getImageUrl() != null) || (localBuilder.getBitmap() != null)) {}
    for (this.previewPhoto = localBuilder.build();; this.previewPhoto = null)
    {
      this.video = new ShareVideo.Builder().readFrom(paramParcel).build();
      return;
    }
  }
  
  private ShareVideoContent(Builder paramBuilder)
  {
    super(paramBuilder);
    this.contentDescription = paramBuilder.contentDescription;
    this.contentTitle = paramBuilder.contentTitle;
    this.previewPhoto = paramBuilder.previewPhoto;
    this.video = paramBuilder.video;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  @Nullable
  public String getContentDescription()
  {
    return this.contentDescription;
  }
  
  @Nullable
  public String getContentTitle()
  {
    return this.contentTitle;
  }
  
  @Nullable
  public SharePhoto getPreviewPhoto()
  {
    return this.previewPhoto;
  }
  
  @Nullable
  public ShareVideo getVideo()
  {
    return this.video;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    super.writeToParcel(paramParcel, paramInt);
    paramParcel.writeString(this.contentDescription);
    paramParcel.writeString(this.contentTitle);
    paramParcel.writeParcelable(this.previewPhoto, 0);
    paramParcel.writeParcelable(this.video, 0);
  }
  
  public static final class Builder
    extends ShareContent.Builder<ShareVideoContent, Builder>
  {
    private String contentDescription;
    private String contentTitle;
    private SharePhoto previewPhoto;
    private ShareVideo video;
    
    public ShareVideoContent build()
    {
      return new ShareVideoContent(this, null);
    }
    
    public Builder readFrom(Parcel paramParcel)
    {
      return readFrom((ShareVideoContent)paramParcel.readParcelable(ShareVideoContent.class.getClassLoader()));
    }
    
    public Builder readFrom(ShareVideoContent paramShareVideoContent)
    {
      if (paramShareVideoContent == null) {
        return this;
      }
      return ((Builder)super.readFrom(paramShareVideoContent)).setContentDescription(paramShareVideoContent.getContentDescription()).setContentTitle(paramShareVideoContent.getContentTitle()).setPreviewPhoto(paramShareVideoContent.getPreviewPhoto()).setVideo(paramShareVideoContent.getVideo());
    }
    
    public Builder setContentDescription(@Nullable String paramString)
    {
      this.contentDescription = paramString;
      return this;
    }
    
    public Builder setContentTitle(@Nullable String paramString)
    {
      this.contentTitle = paramString;
      return this;
    }
    
    public Builder setPreviewPhoto(@Nullable SharePhoto paramSharePhoto)
    {
      if (paramSharePhoto == null) {}
      for (paramSharePhoto = null;; paramSharePhoto = new SharePhoto.Builder().readFrom(paramSharePhoto).build())
      {
        this.previewPhoto = paramSharePhoto;
        return this;
      }
    }
    
    public Builder setVideo(@Nullable ShareVideo paramShareVideo)
    {
      if (paramShareVideo == null) {
        return this;
      }
      this.video = new ShareVideo.Builder().readFrom(paramShareVideo).build();
      return this;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/share/model/ShareVideoContent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */