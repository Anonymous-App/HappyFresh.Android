package com.facebook.share.model;

import android.net.Uri;
import android.os.Parcel;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ShareContent<P extends ShareContent, E extends Builder>
  implements ShareModel
{
  private final Uri contentUrl;
  private final List<String> peopleIds;
  private final String placeId;
  private final String ref;
  
  ShareContent(Parcel paramParcel)
  {
    this.contentUrl = ((Uri)paramParcel.readParcelable(Uri.class.getClassLoader()));
    this.peopleIds = readUnmodifiableStringList(paramParcel);
    this.placeId = paramParcel.readString();
    this.ref = paramParcel.readString();
  }
  
  protected ShareContent(Builder paramBuilder)
  {
    this.contentUrl = paramBuilder.contentUrl;
    this.peopleIds = paramBuilder.peopleIds;
    this.placeId = paramBuilder.placeId;
    this.ref = paramBuilder.ref;
  }
  
  private List<String> readUnmodifiableStringList(Parcel paramParcel)
  {
    ArrayList localArrayList = new ArrayList();
    paramParcel.readStringList(localArrayList);
    if (localArrayList.size() == 0) {
      return null;
    }
    return Collections.unmodifiableList(localArrayList);
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  @Nullable
  public Uri getContentUrl()
  {
    return this.contentUrl;
  }
  
  @Nullable
  public List<String> getPeopleIds()
  {
    return this.peopleIds;
  }
  
  @Nullable
  public String getPlaceId()
  {
    return this.placeId;
  }
  
  @Nullable
  public String getRef()
  {
    return this.ref;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeParcelable(this.contentUrl, 0);
    paramParcel.writeStringList(this.peopleIds);
    paramParcel.writeString(this.placeId);
    paramParcel.writeString(this.ref);
  }
  
  public static abstract class Builder<P extends ShareContent, E extends Builder>
    implements ShareModelBuilder<P, E>
  {
    private Uri contentUrl;
    private List<String> peopleIds;
    private String placeId;
    private String ref;
    
    public E readFrom(P paramP)
    {
      if (paramP == null) {
        return this;
      }
      return setContentUrl(paramP.getContentUrl()).setPeopleIds(paramP.getPeopleIds()).setPlaceId(paramP.getPlaceId()).setRef(paramP.getRef());
    }
    
    public E setContentUrl(@Nullable Uri paramUri)
    {
      this.contentUrl = paramUri;
      return this;
    }
    
    public E setPeopleIds(@Nullable List<String> paramList)
    {
      if (paramList == null) {}
      for (paramList = null;; paramList = Collections.unmodifiableList(paramList))
      {
        this.peopleIds = paramList;
        return this;
      }
    }
    
    public E setPlaceId(@Nullable String paramString)
    {
      this.placeId = paramString;
      return this;
    }
    
    public E setRef(@Nullable String paramString)
    {
      this.ref = paramString;
      return this;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/share/model/ShareContent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */