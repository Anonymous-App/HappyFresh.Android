package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class LocationSettingsResult
  implements Result, SafeParcelable
{
  public static final Parcelable.Creator<LocationSettingsResult> CREATOR = new zzh();
  private final int zzCY;
  private final Status zzOt;
  private final LocationSettingsStates zzayf;
  
  LocationSettingsResult(int paramInt, Status paramStatus, LocationSettingsStates paramLocationSettingsStates)
  {
    this.zzCY = paramInt;
    this.zzOt = paramStatus;
    this.zzayf = paramLocationSettingsStates;
  }
  
  public LocationSettingsResult(Status paramStatus)
  {
    this(1, paramStatus, null);
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public LocationSettingsStates getLocationSettingsStates()
  {
    return this.zzayf;
  }
  
  public Status getStatus()
  {
    return this.zzOt;
  }
  
  public int getVersionCode()
  {
    return this.zzCY;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzh.zza(this, paramParcel, paramInt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/LocationSettingsResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */