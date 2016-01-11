package com.happyfresh.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Slot
  implements Parcelable
{
  public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
  {
    public Slot createFromParcel(Parcel paramAnonymousParcel)
    {
      Slot localSlot = new Slot();
      localSlot.remoteId = paramAnonymousParcel.readLong();
      localSlot.stockLocationId = Long.valueOf(paramAnonymousParcel.readLong());
      localSlot.startTime = ((Date)paramAnonymousParcel.readValue(Date.class.getClassLoader()));
      localSlot.endTime = ((Date)paramAnonymousParcel.readValue(Date.class.getClassLoader()));
      localSlot.shippingMethodId = Long.valueOf(paramAnonymousParcel.readLong());
      if (Byte.valueOf(paramAnonymousParcel.readByte()).byteValue() != 0) {}
      for (boolean bool = true;; bool = false)
      {
        localSlot.available = bool;
        return localSlot;
      }
    }
    
    public Slot[] newArray(int paramAnonymousInt)
    {
      return new Slot[paramAnonymousInt];
    }
  };
  public boolean available;
  @SerializedName("end_time")
  public Date endTime;
  @SerializedName("id")
  public long remoteId;
  @SerializedName("shipping_method_id")
  public Long shippingMethodId;
  @SerializedName("start_time")
  public Date startTime;
  @SerializedName("stock_location_id")
  public Long stockLocationId;
  
  public int describeContents()
  {
    return 0;
  }
  
  public String toString()
  {
    SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat("HH");
    return String.format("%s %s-%s", new Object[] { localSimpleDateFormat1.format(this.startTime), localSimpleDateFormat2.format(this.startTime), localSimpleDateFormat2.format(this.endTime) });
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeLong(this.remoteId);
    paramParcel.writeLong(this.stockLocationId.longValue());
    paramParcel.writeValue(this.startTime);
    paramParcel.writeValue(this.endTime);
    paramParcel.writeLong(this.shippingMethodId.longValue());
    if (this.available) {}
    for (paramInt = 1;; paramInt = 0)
    {
      paramParcel.writeByte((byte)paramInt);
      return;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/models/Slot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */