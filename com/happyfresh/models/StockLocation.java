package com.happyfresh.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

public class StockLocation
  implements Parcelable
{
  public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
  {
    public StockLocation createFromParcel(Parcel paramAnonymousParcel)
    {
      StockLocation localStockLocation = new StockLocation();
      localStockLocation.remoteId = paramAnonymousParcel.readLong();
      localStockLocation.stateId = ((Long)paramAnonymousParcel.readValue(Long.class.getClassLoader()));
      localStockLocation.countryId = ((Long)paramAnonymousParcel.readValue(Long.class.getClassLoader()));
      localStockLocation.name = paramAnonymousParcel.readString();
      localStockLocation.address1 = paramAnonymousParcel.readString();
      localStockLocation.address2 = paramAnonymousParcel.readString();
      localStockLocation.city = paramAnonymousParcel.readString();
      localStockLocation.zipcode = paramAnonymousParcel.readString();
      localStockLocation.stateName = paramAnonymousParcel.readString();
      if (paramAnonymousParcel.readByte() != 0) {}
      for (boolean bool = true;; bool = false)
      {
        localStockLocation.active = bool;
        localStockLocation.country = ((Country)paramAnonymousParcel.readParcelable(Country.class.getClassLoader()));
        localStockLocation.photo = ((Photo)paramAnonymousParcel.readParcelable(Photo.class.getClassLoader()));
        localStockLocation.state = ((State)paramAnonymousParcel.readParcelable(State.class.getClassLoader()));
        localStockLocation.nextAvailableSlot = ((Slot)paramAnonymousParcel.readParcelable(Slot.class.getClassLoader()));
        return localStockLocation;
      }
    }
    
    public StockLocation[] newArray(int paramAnonymousInt)
    {
      return new StockLocation[paramAnonymousInt];
    }
  };
  public boolean active;
  public String address1;
  public String address2;
  public String city;
  public Country country;
  @SerializedName("country_id")
  public Long countryId;
  public Coordinate location;
  public String name;
  @SerializedName("next_available_slot")
  public Slot nextAvailableSlot;
  public String phone;
  public Photo photo;
  @SerializedName("id")
  public long remoteId;
  public State state;
  @SerializedName("state_id")
  public Long stateId;
  @SerializedName("state_name")
  public String stateName;
  public String zipcode;
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeLong(this.remoteId);
    paramParcel.writeValue(this.stateId);
    paramParcel.writeValue(this.countryId);
    paramParcel.writeString(this.name);
    paramParcel.writeString(this.address1);
    paramParcel.writeString(this.address2);
    paramParcel.writeString(this.city);
    paramParcel.writeString(this.zipcode);
    paramParcel.writeString(this.stateName);
    if (this.active) {}
    for (int i = 1;; i = 0)
    {
      paramParcel.writeByte((byte)i);
      paramParcel.writeParcelable(this.country, paramInt);
      paramParcel.writeParcelable(this.photo, paramInt);
      paramParcel.writeParcelable(this.state, paramInt);
      paramParcel.writeParcelable(this.nextAvailableSlot, paramInt);
      return;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/StockLocation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */