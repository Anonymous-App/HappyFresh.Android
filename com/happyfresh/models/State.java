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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Table(name="states")
public class State
  extends FindStoreModel
  implements Parcelable
{
  public static final Parcelable.Creator<State> CREATOR = new Parcelable.Creator()
  {
    public State createFromParcel(Parcel paramAnonymousParcel)
    {
      State localState = new State();
      localState.remoteId = Long.valueOf(paramAnonymousParcel.readLong());
      localState.name = paramAnonymousParcel.readString();
      localState.abbr = paramAnonymousParcel.readString();
      localState.countryId = Long.valueOf(paramAnonymousParcel.readLong());
      return localState;
    }
    
    public State[] newArray(int paramAnonymousInt)
    {
      return new State[paramAnonymousInt];
    }
  };
  @Column(name="abbr")
  public String abbr;
  @Column(name="country_id")
  @SerializedName("country_id")
  public Long countryId;
  public Date getDistrictTimestamps;
  @Column(name="name")
  public String name;
  @Column(name="remote_id", onUniqueConflict=Column.ConflictAction.IGNORE, unique=true)
  @SerializedName("id")
  public Long remoteId;
  
  public static void deleteAll()
  {
    new Delete().from(State.class).execute();
  }
  
  public static List<State> findByCountry(Long paramLong)
  {
    return new Select().from(State.class).where("country_id = ?", new Object[] { paramLong }).execute();
  }
  
  public static State findById(Long paramLong)
  {
    return (State)new Select().from(State.class).where("remote_id = ?", new Object[] { paramLong }).executeSingle();
  }
  
  public static List<State> findNotExisting(Long paramLong, List<State> paramList)
  {
    ArrayList localArrayList = new ArrayList();
    paramLong = findByCountry(paramLong).iterator();
    while (paramLong.hasNext()) {
      localArrayList.add(((State)paramLong.next()).remoteId);
    }
    paramLong = new ArrayList();
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      State localState = (State)paramList.next();
      if (!localArrayList.contains(localState.remoteId)) {
        paramLong.add(localState);
      }
    }
    return paramLong;
  }
  
  public static List<State> subtract(List<State> paramList1, List<State> paramList2)
  {
    ArrayList localArrayList = new ArrayList();
    paramList2 = new HashSet(paramList2);
    paramList1 = paramList1.iterator();
    while (paramList1.hasNext())
    {
      State localState = (State)paramList1.next();
      if (!paramList2.contains(localState)) {
        localArrayList.add(localState);
      }
    }
    return localArrayList;
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
    return this.remoteId;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeLong(this.remoteId.longValue());
    paramParcel.writeString(this.name);
    paramParcel.writeString(this.abbr);
    paramParcel.writeLong(this.countryId.longValue());
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/State.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */