package com.google.android.gms.analytics.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Command
  implements Parcelable
{
  @Deprecated
  public static final Parcelable.Creator<Command> CREATOR = new Parcelable.Creator()
  {
    @Deprecated
    public Command[] zzaa(int paramAnonymousInt)
    {
      return new Command[paramAnonymousInt];
    }
    
    @Deprecated
    public Command zzq(Parcel paramAnonymousParcel)
    {
      return new Command(paramAnonymousParcel);
    }
  };
  private String mValue;
  private String zzKI;
  private String zzKJ;
  
  @Deprecated
  public Command() {}
  
  @Deprecated
  Command(Parcel paramParcel)
  {
    readFromParcel(paramParcel);
  }
  
  @Deprecated
  private void readFromParcel(Parcel paramParcel)
  {
    this.zzKI = paramParcel.readString();
    this.zzKJ = paramParcel.readString();
    this.mValue = paramParcel.readString();
  }
  
  @Deprecated
  public int describeContents()
  {
    return 0;
  }
  
  public String getId()
  {
    return this.zzKI;
  }
  
  public String getValue()
  {
    return this.mValue;
  }
  
  @Deprecated
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.zzKI);
    paramParcel.writeString(this.zzKJ);
    paramParcel.writeString(this.mValue);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/analytics/internal/Command.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */