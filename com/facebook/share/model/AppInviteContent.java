package com.facebook.share.model;

import android.os.Parcel;

public final class AppInviteContent
  implements ShareModel
{
  private final String applinkUrl;
  private final String previewImageUrl;
  
  AppInviteContent(Parcel paramParcel)
  {
    this.applinkUrl = paramParcel.readString();
    this.previewImageUrl = paramParcel.readString();
  }
  
  private AppInviteContent(Builder paramBuilder)
  {
    this.applinkUrl = paramBuilder.applinkUrl;
    this.previewImageUrl = paramBuilder.previewImageUrl;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public String getApplinkUrl()
  {
    return this.applinkUrl;
  }
  
  public String getPreviewImageUrl()
  {
    return this.previewImageUrl;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.applinkUrl);
    paramParcel.writeString(this.previewImageUrl);
  }
  
  public static class Builder
    implements ShareModelBuilder<AppInviteContent, Builder>
  {
    private String applinkUrl;
    private String previewImageUrl;
    
    public AppInviteContent build()
    {
      return new AppInviteContent(this, null);
    }
    
    public Builder readFrom(Parcel paramParcel)
    {
      return readFrom((AppInviteContent)paramParcel.readParcelable(AppInviteContent.class.getClassLoader()));
    }
    
    public Builder readFrom(AppInviteContent paramAppInviteContent)
    {
      if (paramAppInviteContent == null) {
        return this;
      }
      return setApplinkUrl(paramAppInviteContent.getApplinkUrl()).setPreviewImageUrl(paramAppInviteContent.getPreviewImageUrl());
    }
    
    public Builder setApplinkUrl(String paramString)
    {
      this.applinkUrl = paramString;
      return this;
    }
    
    public Builder setPreviewImageUrl(String paramString)
    {
      this.previewImageUrl = paramString;
      return this;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/share/model/AppInviteContent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */