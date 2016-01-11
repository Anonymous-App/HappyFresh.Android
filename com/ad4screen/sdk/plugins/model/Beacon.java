package com.ad4screen.sdk.plugins.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.ad4screen.sdk.common.persistence.c;
import com.ad4screen.sdk.common.persistence.d;
import org.json.JSONException;
import org.json.JSONObject;

public class Beacon
  implements Parcelable, c<Beacon>, d
{
  private static final String CLASS_KEY = "com.ad4screen.sdk.plugins.model.Beacon";
  public static final Parcelable.Creator<Beacon> CREATOR = new Parcelable.Creator()
  {
    public Beacon createFromParcel(Parcel paramAnonymousParcel)
    {
      return new Beacon(paramAnonymousParcel);
    }
    
    public Beacon[] newArray(int paramAnonymousInt)
    {
      return new Beacon[paramAnonymousInt];
    }
  };
  public String id;
  public int major = -1;
  public int minor = -1;
  public boolean toRemove = false;
  public String uuid;
  
  public Beacon() {}
  
  public Beacon(Parcel paramParcel)
  {
    this.id = paramParcel.readString();
    this.uuid = paramParcel.readString();
    this.major = paramParcel.readInt();
    this.minor = paramParcel.readInt();
    if (paramParcel.readInt() == 1) {}
    for (;;)
    {
      this.toRemove = bool;
      return;
      bool = false;
    }
  }
  
  public Beacon(String paramString)
  {
    this.id = paramString;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    do
    {
      do
      {
        return true;
        if (paramObject == null) {
          return false;
        }
        if (getClass() != paramObject.getClass()) {
          return false;
        }
        paramObject = (Beacon)paramObject;
        if (this.id != null) {
          break;
        }
      } while (((Beacon)paramObject).id == null);
      return false;
    } while (this.id.equals(((Beacon)paramObject).id));
    return false;
  }
  
  public Beacon fromJSON(String paramString)
    throws JSONException
  {
    paramString = new JSONObject(paramString);
    Beacon localBeacon = new Beacon(paramString.getString("id"));
    localBeacon.uuid = paramString.getString("uuid");
    localBeacon.toRemove = paramString.getBoolean("toRemove");
    localBeacon.major = paramString.getInt("major");
    localBeacon.minor = paramString.getInt("minor");
    return localBeacon;
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.plugins.model.Beacon";
  }
  
  public int hashCode()
  {
    if (this.id == null) {}
    for (int i = 0;; i = this.id.hashCode()) {
      return i + 31;
    }
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put("id", this.id);
    localJSONObject.put("uuid", this.uuid);
    localJSONObject.put("toRemove", this.toRemove);
    localJSONObject.put("major", this.major);
    localJSONObject.put("minor", this.minor);
    return localJSONObject;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.id);
    paramParcel.writeString(this.uuid);
    paramParcel.writeInt(this.major);
    paramParcel.writeInt(this.minor);
    if (this.toRemove) {}
    for (paramInt = 1;; paramInt = 0)
    {
      paramParcel.writeInt(paramInt);
      return;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/plugins/model/Beacon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */