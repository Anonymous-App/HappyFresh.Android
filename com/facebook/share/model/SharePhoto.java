package com.facebook.share.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class SharePhoto
  implements ShareModel
{
  public static final Parcelable.Creator<SharePhoto> CREATOR = new Parcelable.Creator()
  {
    public SharePhoto createFromParcel(Parcel paramAnonymousParcel)
    {
      return new SharePhoto(paramAnonymousParcel);
    }
    
    public SharePhoto[] newArray(int paramAnonymousInt)
    {
      return new SharePhoto[paramAnonymousInt];
    }
  };
  private final Bitmap bitmap;
  private final String caption;
  private final Uri imageUrl;
  private final boolean userGenerated;
  
  SharePhoto(Parcel paramParcel)
  {
    this.bitmap = ((Bitmap)paramParcel.readParcelable(Bitmap.class.getClassLoader()));
    this.imageUrl = ((Uri)paramParcel.readParcelable(Uri.class.getClassLoader()));
    if (paramParcel.readByte() != 0) {}
    for (boolean bool = true;; bool = false)
    {
      this.userGenerated = bool;
      this.caption = paramParcel.readString();
      return;
    }
  }
  
  private SharePhoto(Builder paramBuilder)
  {
    this.bitmap = paramBuilder.bitmap;
    this.imageUrl = paramBuilder.imageUrl;
    this.userGenerated = paramBuilder.userGenerated;
    this.caption = paramBuilder.caption;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  @Nullable
  public Bitmap getBitmap()
  {
    return this.bitmap;
  }
  
  public String getCaption()
  {
    return this.caption;
  }
  
  @Nullable
  public Uri getImageUrl()
  {
    return this.imageUrl;
  }
  
  public boolean getUserGenerated()
  {
    return this.userGenerated;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramInt = 0;
    paramParcel.writeParcelable(this.bitmap, 0);
    paramParcel.writeParcelable(this.imageUrl, 0);
    if (this.userGenerated) {
      paramInt = 1;
    }
    paramParcel.writeByte((byte)paramInt);
    paramParcel.writeString(this.caption);
  }
  
  public static final class Builder
    implements ShareModelBuilder<SharePhoto, Builder>
  {
    private Bitmap bitmap;
    private String caption;
    private Uri imageUrl;
    private boolean userGenerated;
    
    public static List<SharePhoto> readListFrom(Parcel paramParcel)
    {
      ArrayList localArrayList = new ArrayList();
      paramParcel.readTypedList(localArrayList, SharePhoto.CREATOR);
      return localArrayList;
    }
    
    public static void writeListTo(Parcel paramParcel, List<SharePhoto> paramList)
    {
      ArrayList localArrayList = new ArrayList();
      paramList = paramList.iterator();
      while (paramList.hasNext()) {
        localArrayList.add((SharePhoto)paramList.next());
      }
      paramParcel.writeTypedList(localArrayList);
    }
    
    public SharePhoto build()
    {
      return new SharePhoto(this, null);
    }
    
    Bitmap getBitmap()
    {
      return this.bitmap;
    }
    
    Uri getImageUrl()
    {
      return this.imageUrl;
    }
    
    public Builder readFrom(Parcel paramParcel)
    {
      return readFrom((SharePhoto)paramParcel.readParcelable(SharePhoto.class.getClassLoader()));
    }
    
    public Builder readFrom(SharePhoto paramSharePhoto)
    {
      if (paramSharePhoto == null) {
        return this;
      }
      return setBitmap(paramSharePhoto.getBitmap()).setImageUrl(paramSharePhoto.getImageUrl()).setUserGenerated(paramSharePhoto.getUserGenerated()).setCaption(paramSharePhoto.getCaption());
    }
    
    public Builder setBitmap(@Nullable Bitmap paramBitmap)
    {
      this.bitmap = paramBitmap;
      return this;
    }
    
    public Builder setCaption(@Nullable String paramString)
    {
      this.caption = paramString;
      return this;
    }
    
    public Builder setImageUrl(@Nullable Uri paramUri)
    {
      this.imageUrl = paramUri;
      return this;
    }
    
    public Builder setUserGenerated(boolean paramBoolean)
    {
      this.userGenerated = paramBoolean;
      return this;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/share/model/SharePhoto.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */