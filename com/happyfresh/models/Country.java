package com.happyfresh.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Column.ConflictAction;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@Table(name="countries")
public class Country
  extends FindStoreModel
  implements Parcelable
{
  public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
  {
    public Country createFromParcel(Parcel paramAnonymousParcel)
    {
      Country localCountry = new Country();
      localCountry.remoteId = Long.valueOf(paramAnonymousParcel.readLong());
      localCountry.isoName = paramAnonymousParcel.readString();
      localCountry.iso = paramAnonymousParcel.readString();
      localCountry.iso3 = paramAnonymousParcel.readString();
      localCountry.name = paramAnonymousParcel.readString();
      localCountry.numcode = paramAnonymousParcel.readString();
      localCountry.currency = paramAnonymousParcel.readString();
      return localCountry;
    }
    
    public Country[] newArray(int paramAnonymousInt)
    {
      return new Country[paramAnonymousInt];
    }
  };
  @SerializedName("cs_phone")
  public String csPhone;
  @Column(name="currency")
  public String currency;
  @Column(name="iso")
  public String iso;
  @Column(name="iso3")
  public String iso3;
  @Column(name="iso_name")
  @SerializedName("iso_name")
  public String isoName;
  @Column(name="name")
  public String name;
  @Column(name="numcode")
  public String numcode;
  @Column(name="remote_id", onUniqueConflict=Column.ConflictAction.IGNORE, unique=true)
  @SerializedName("id")
  public Long remoteId;
  
  public static List<Country> findAll()
  {
    return new Select().from(Country.class).execute();
  }
  
  public static Country findByCode(String paramString)
  {
    return (Country)new Select().from(Country.class).where("iso_name = ?", new Object[] { paramString }).executeSingle();
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public String getCode()
  {
    return this.isoName;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public Long getRemoteId()
  {
    return this.remoteId;
  }
  
  public List<State> getStates()
  {
    return new Select().from(Country.class).where("country_id = ?", new Object[] { this.remoteId }).execute();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeLong(this.remoteId.longValue());
    paramParcel.writeString(this.isoName);
    paramParcel.writeString(this.iso);
    paramParcel.writeString(this.iso3);
    paramParcel.writeString(this.name);
    paramParcel.writeString(this.numcode);
    paramParcel.writeString(this.currency);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/Country.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */