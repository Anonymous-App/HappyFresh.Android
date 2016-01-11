package com.happyfresh.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.activeandroid.Model;

public abstract class FindStoreModel
  extends Model
  implements Parcelable
{
  private boolean mEnabled = true;
  private boolean mProgressing;
  private boolean mSelected;
  
  public int describeContents()
  {
    return 1000;
  }
  
  public String getCode()
  {
    return null;
  }
  
  public abstract String getName();
  
  public abstract Long getRemoteId();
  
  public boolean isEnabled()
  {
    return this.mEnabled;
  }
  
  public boolean isProgressing()
  {
    return this.mProgressing;
  }
  
  public boolean isSelected()
  {
    return this.mSelected;
  }
  
  public void setEnabled(boolean paramBoolean)
  {
    this.mEnabled = paramBoolean;
  }
  
  public void setProgressing(boolean paramBoolean)
  {
    this.mProgressing = paramBoolean;
  }
  
  public void setSelected(boolean paramBoolean)
  {
    this.mSelected = paramBoolean;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {}
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/FindStoreModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */