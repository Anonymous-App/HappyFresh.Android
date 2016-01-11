package com.facebook.share.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public abstract class ShareOpenGraphValueContainer<P extends ShareOpenGraphValueContainer, E extends Builder>
  implements ShareModel
{
  private final Bundle bundle;
  
  ShareOpenGraphValueContainer(Parcel paramParcel)
  {
    this.bundle = paramParcel.readBundle(Builder.class.getClassLoader());
  }
  
  protected ShareOpenGraphValueContainer(Builder<P, E> paramBuilder)
  {
    this.bundle = ((Bundle)paramBuilder.bundle.clone());
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  @Nullable
  public Object get(String paramString)
  {
    return this.bundle.get(paramString);
  }
  
  public boolean getBoolean(String paramString, boolean paramBoolean)
  {
    return this.bundle.getBoolean(paramString, paramBoolean);
  }
  
  @Nullable
  public boolean[] getBooleanArray(String paramString)
  {
    return this.bundle.getBooleanArray(paramString);
  }
  
  public Bundle getBundle()
  {
    return (Bundle)this.bundle.clone();
  }
  
  public double getDouble(String paramString, double paramDouble)
  {
    return this.bundle.getDouble(paramString, paramDouble);
  }
  
  @Nullable
  public double[] getDoubleArray(String paramString)
  {
    return this.bundle.getDoubleArray(paramString);
  }
  
  public int getInt(String paramString, int paramInt)
  {
    return this.bundle.getInt(paramString, paramInt);
  }
  
  @Nullable
  public int[] getIntArray(String paramString)
  {
    return this.bundle.getIntArray(paramString);
  }
  
  public long getLong(String paramString, long paramLong)
  {
    return this.bundle.getLong(paramString, paramLong);
  }
  
  @Nullable
  public long[] getLongArray(String paramString)
  {
    return this.bundle.getLongArray(paramString);
  }
  
  public ShareOpenGraphObject getObject(String paramString)
  {
    paramString = this.bundle.get(paramString);
    if ((paramString instanceof ShareOpenGraphObject)) {
      return (ShareOpenGraphObject)paramString;
    }
    return null;
  }
  
  @Nullable
  public ArrayList<ShareOpenGraphObject> getObjectArrayList(String paramString)
  {
    paramString = this.bundle.getParcelableArrayList(paramString);
    if (paramString == null)
    {
      paramString = null;
      return paramString;
    }
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = paramString.iterator();
    for (;;)
    {
      paramString = localArrayList;
      if (!localIterator.hasNext()) {
        break;
      }
      paramString = (Parcelable)localIterator.next();
      if ((paramString instanceof ShareOpenGraphObject)) {
        localArrayList.add((ShareOpenGraphObject)paramString);
      }
    }
  }
  
  @Nullable
  public SharePhoto getPhoto(String paramString)
  {
    paramString = this.bundle.getParcelable(paramString);
    if ((paramString instanceof SharePhoto)) {
      return (SharePhoto)paramString;
    }
    return null;
  }
  
  @Nullable
  public ArrayList<SharePhoto> getPhotoArrayList(String paramString)
  {
    paramString = this.bundle.getParcelableArrayList(paramString);
    if (paramString == null)
    {
      paramString = null;
      return paramString;
    }
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = paramString.iterator();
    for (;;)
    {
      paramString = localArrayList;
      if (!localIterator.hasNext()) {
        break;
      }
      paramString = (Parcelable)localIterator.next();
      if ((paramString instanceof SharePhoto)) {
        localArrayList.add((SharePhoto)paramString);
      }
    }
  }
  
  @Nullable
  public String getString(String paramString)
  {
    return this.bundle.getString(paramString);
  }
  
  @Nullable
  public ArrayList<String> getStringArrayList(String paramString)
  {
    return this.bundle.getStringArrayList(paramString);
  }
  
  public Set<String> keySet()
  {
    return this.bundle.keySet();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeBundle(this.bundle);
  }
  
  public static abstract class Builder<P extends ShareOpenGraphValueContainer, E extends Builder>
    implements ShareModelBuilder<P, E>
  {
    private Bundle bundle = new Bundle();
    
    public E putBoolean(String paramString, boolean paramBoolean)
    {
      this.bundle.putBoolean(paramString, paramBoolean);
      return this;
    }
    
    public E putBooleanArray(String paramString, @Nullable boolean[] paramArrayOfBoolean)
    {
      this.bundle.putBooleanArray(paramString, paramArrayOfBoolean);
      return this;
    }
    
    public E putDouble(String paramString, double paramDouble)
    {
      this.bundle.putDouble(paramString, paramDouble);
      return this;
    }
    
    public E putDoubleArray(String paramString, @Nullable double[] paramArrayOfDouble)
    {
      this.bundle.putDoubleArray(paramString, paramArrayOfDouble);
      return this;
    }
    
    public E putInt(String paramString, int paramInt)
    {
      this.bundle.putInt(paramString, paramInt);
      return this;
    }
    
    public E putIntArray(String paramString, @Nullable int[] paramArrayOfInt)
    {
      this.bundle.putIntArray(paramString, paramArrayOfInt);
      return this;
    }
    
    public E putLong(String paramString, long paramLong)
    {
      this.bundle.putLong(paramString, paramLong);
      return this;
    }
    
    public E putLongArray(String paramString, @Nullable long[] paramArrayOfLong)
    {
      this.bundle.putLongArray(paramString, paramArrayOfLong);
      return this;
    }
    
    public E putObject(String paramString, @Nullable ShareOpenGraphObject paramShareOpenGraphObject)
    {
      this.bundle.putParcelable(paramString, paramShareOpenGraphObject);
      return this;
    }
    
    public E putObjectArrayList(String paramString, @Nullable ArrayList<ShareOpenGraphObject> paramArrayList)
    {
      this.bundle.putParcelableArrayList(paramString, paramArrayList);
      return this;
    }
    
    public E putPhoto(String paramString, @Nullable SharePhoto paramSharePhoto)
    {
      this.bundle.putParcelable(paramString, paramSharePhoto);
      return this;
    }
    
    public E putPhotoArrayList(String paramString, @Nullable ArrayList<SharePhoto> paramArrayList)
    {
      this.bundle.putParcelableArrayList(paramString, paramArrayList);
      return this;
    }
    
    public E putString(String paramString1, @Nullable String paramString2)
    {
      this.bundle.putString(paramString1, paramString2);
      return this;
    }
    
    public E putStringArrayList(String paramString, @Nullable ArrayList<String> paramArrayList)
    {
      this.bundle.putStringArrayList(paramString, paramArrayList);
      return this;
    }
    
    public E readFrom(P paramP)
    {
      if (paramP != null) {
        this.bundle.putAll(paramP.getBundle());
      }
      return this;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/share/model/ShareOpenGraphValueContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */