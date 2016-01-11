package com.facebook.share.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;

public final class ShareOpenGraphContent
  extends ShareContent<ShareOpenGraphContent, Builder>
{
  public static final Parcelable.Creator<ShareOpenGraphContent> CREATOR = new Parcelable.Creator()
  {
    public ShareOpenGraphContent createFromParcel(Parcel paramAnonymousParcel)
    {
      return new ShareOpenGraphContent(paramAnonymousParcel);
    }
    
    public ShareOpenGraphContent[] newArray(int paramAnonymousInt)
    {
      return new ShareOpenGraphContent[paramAnonymousInt];
    }
  };
  private final ShareOpenGraphAction action;
  private final String previewPropertyName;
  
  ShareOpenGraphContent(Parcel paramParcel)
  {
    super(paramParcel);
    this.action = new ShareOpenGraphAction.Builder().readFrom(paramParcel).build();
    this.previewPropertyName = paramParcel.readString();
  }
  
  private ShareOpenGraphContent(Builder paramBuilder)
  {
    super(paramBuilder);
    this.action = paramBuilder.action;
    this.previewPropertyName = paramBuilder.previewPropertyName;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  @Nullable
  public ShareOpenGraphAction getAction()
  {
    return this.action;
  }
  
  @Nullable
  public String getPreviewPropertyName()
  {
    return this.previewPropertyName;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    super.writeToParcel(paramParcel, paramInt);
    paramParcel.writeParcelable(this.action, 0);
    paramParcel.writeString(this.previewPropertyName);
  }
  
  public static final class Builder
    extends ShareContent.Builder<ShareOpenGraphContent, Builder>
  {
    private ShareOpenGraphAction action;
    private String previewPropertyName;
    
    public ShareOpenGraphContent build()
    {
      return new ShareOpenGraphContent(this, null);
    }
    
    public Builder readFrom(Parcel paramParcel)
    {
      return readFrom((ShareOpenGraphContent)paramParcel.readParcelable(ShareOpenGraphContent.class.getClassLoader()));
    }
    
    public Builder readFrom(ShareOpenGraphContent paramShareOpenGraphContent)
    {
      if (paramShareOpenGraphContent == null) {
        return this;
      }
      return ((Builder)super.readFrom(paramShareOpenGraphContent)).setAction(paramShareOpenGraphContent.getAction()).setPreviewPropertyName(paramShareOpenGraphContent.getPreviewPropertyName());
    }
    
    public Builder setAction(@Nullable ShareOpenGraphAction paramShareOpenGraphAction)
    {
      if (paramShareOpenGraphAction == null) {}
      for (paramShareOpenGraphAction = null;; paramShareOpenGraphAction = new ShareOpenGraphAction.Builder().readFrom(paramShareOpenGraphAction).build())
      {
        this.action = paramShareOpenGraphAction;
        return this;
      }
    }
    
    public Builder setPreviewPropertyName(@Nullable String paramString)
    {
      this.previewPropertyName = paramString;
      return this;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/share/model/ShareOpenGraphContent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */