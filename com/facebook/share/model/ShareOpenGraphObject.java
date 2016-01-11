package com.facebook.share.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public final class ShareOpenGraphObject
  extends ShareOpenGraphValueContainer<ShareOpenGraphObject, Builder>
{
  public static final Parcelable.Creator<ShareOpenGraphObject> CREATOR = new Parcelable.Creator()
  {
    public ShareOpenGraphObject createFromParcel(Parcel paramAnonymousParcel)
    {
      return new ShareOpenGraphObject(paramAnonymousParcel);
    }
    
    public ShareOpenGraphObject[] newArray(int paramAnonymousInt)
    {
      return new ShareOpenGraphObject[paramAnonymousInt];
    }
  };
  
  ShareOpenGraphObject(Parcel paramParcel)
  {
    super(paramParcel);
  }
  
  private ShareOpenGraphObject(Builder paramBuilder)
  {
    super(paramBuilder);
  }
  
  public static final class Builder
    extends ShareOpenGraphValueContainer.Builder<ShareOpenGraphObject, Builder>
  {
    public Builder()
    {
      putBoolean("fbsdk:create_object", true);
    }
    
    public ShareOpenGraphObject build()
    {
      return new ShareOpenGraphObject(this, null);
    }
    
    public Builder readFrom(Parcel paramParcel)
    {
      return (Builder)readFrom((ShareOpenGraphObject)paramParcel.readParcelable(ShareOpenGraphObject.class.getClassLoader()));
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/share/model/ShareOpenGraphObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */