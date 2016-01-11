package com.happyfresh.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Column.ConflictAction;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@Table(name="districts")
public class District
  extends FindStoreModel
  implements Parcelable
{
  public static final Parcelable.Creator<District> CREATOR = new Parcelable.Creator()
  {
    public District createFromParcel(Parcel paramAnonymousParcel)
    {
      District localDistrict = new District();
      localDistrict.remoteId = paramAnonymousParcel.readLong();
      localDistrict.name = paramAnonymousParcel.readString();
      localDistrict.state = ((State)paramAnonymousParcel.readParcelable(State.class.getClassLoader()));
      return localDistrict;
    }
    
    public District[] newArray(int paramAnonymousInt)
    {
      return new District[paramAnonymousInt];
    }
  };
  @Column(name="name")
  public String name;
  @Column(name="remote_id", onUniqueConflict=Column.ConflictAction.IGNORE, unique=true)
  @SerializedName("id")
  public long remoteId;
  public State state;
  @Column(name="state_id")
  @SerializedName("state_id")
  public Long stateId;
  @SerializedName("sub_districts")
  public List<SubDistrict> subDistricts;
  
  public static void deleteAll()
  {
    new Delete().from(District.class).execute();
  }
  
  public static List<District> findAll()
  {
    return new Select().from(District.class).execute();
  }
  
  public static District findById(Long paramLong)
  {
    return (District)new Select().from(District.class).where("remote_id = ?", new Object[] { paramLong }).executeSingle();
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public Long getRemoteId()
  {
    return Long.valueOf(this.remoteId);
  }
  
  public State getState()
  {
    State localState2 = this.state;
    State localState1 = localState2;
    if (localState2 == null)
    {
      localState1 = localState2;
      if (this.stateId != null) {
        localState1 = (State)new Select().from(State.class).where("remote_id = ?", new Object[] { this.stateId }).executeSingle();
      }
    }
    return localState1;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeLong(this.remoteId);
    paramParcel.writeString(this.name);
    paramParcel.writeParcelable(this.state, paramInt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/District.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */