package com.google.android.gms.gcm;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class PendingCallback
  implements Parcelable
{
  public static final Parcelable.Creator<PendingCallback> CREATOR = new Parcelable.Creator()
  {
    public PendingCallback zzdW(Parcel paramAnonymousParcel)
    {
      return new PendingCallback(paramAnonymousParcel);
    }
    
    public PendingCallback[] zzgi(int paramAnonymousInt)
    {
      return new PendingCallback[paramAnonymousInt];
    }
  };
  final IBinder zzZQ;
  
  public PendingCallback(Parcel paramParcel)
  {
    this.zzZQ = paramParcel.readStrongBinder();
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public IBinder getIBinder()
  {
    return this.zzZQ;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeStrongBinder(this.zzZQ);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/gcm/PendingCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */