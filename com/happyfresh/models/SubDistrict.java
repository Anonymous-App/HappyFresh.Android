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

@Table(name="sub_districts")
public class SubDistrict
  extends FindStoreModel
  implements Parcelable
{
  public static final Parcelable.Creator<SubDistrict> CREATOR = new Parcelable.Creator()
  {
    public SubDistrict createFromParcel(Parcel paramAnonymousParcel)
    {
      SubDistrict localSubDistrict = new SubDistrict();
      localSubDistrict.remoteId = Long.valueOf(paramAnonymousParcel.readLong());
      localSubDistrict.name = paramAnonymousParcel.readString();
      localSubDistrict.zipcode = paramAnonymousParcel.readString();
      localSubDistrict.district = ((District)paramAnonymousParcel.readParcelable(District.class.getClassLoader()));
      return localSubDistrict;
    }
    
    public SubDistrict[] newArray(int paramAnonymousInt)
    {
      return new SubDistrict[paramAnonymousInt];
    }
  };
  @Column(name="district")
  public District district;
  @Column(name="district_id")
  @SerializedName("district_id")
  public Long districtId;
  @Column(name="name")
  public String name;
  @Column(name="remote_id", onUniqueConflict=Column.ConflictAction.IGNORE, unique=true)
  @SerializedName("id")
  public Long remoteId;
  @Column(name="state_id")
  public Long stateId;
  @Column(name="zipcode")
  public String zipcode;
  
  public static void deleteAll()
  {
    new Delete().from(SubDistrict.class).execute();
  }
  
  public static List<SubDistrict> findByState(Long paramLong)
  {
    return new Select().from(SubDistrict.class).where("state_id = ?", new Object[] { paramLong }).orderBy("name ASC").execute();
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public String getName()
  {
    return this.name + " (" + this.district.getName() + ") - " + this.zipcode;
  }
  
  public Long getRemoteId()
  {
    return this.remoteId;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder(this.name.trim());
    localStringBuilder.append(", ");
    if (this.district != null)
    {
      localStringBuilder.append(this.district.name);
      localStringBuilder.append(", ");
    }
    localStringBuilder.append(this.zipcode);
    return localStringBuilder.toString();
  }
  
  public String toStringChineseTaiwan()
  {
    StringBuilder localStringBuilder = new StringBuilder(this.zipcode);
    if (this.district != null) {
      localStringBuilder.append(this.district.name);
    }
    localStringBuilder.append(this.name.trim());
    return localStringBuilder.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeLong(this.remoteId.longValue());
    paramParcel.writeString(this.name);
    paramParcel.writeString(this.zipcode);
    paramParcel.writeParcelable(this.district, paramInt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/SubDistrict.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */