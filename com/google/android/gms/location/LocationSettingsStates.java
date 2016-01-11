package com.google.android.gms.location;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.safeparcel.zzc;

public final class LocationSettingsStates
  implements SafeParcelable
{
  public static final Parcelable.Creator<LocationSettingsStates> CREATOR = new zzi();
  private final int zzCY;
  private final boolean zzayg;
  private final boolean zzayh;
  private final boolean zzayi;
  private final boolean zzayj;
  private final boolean zzayk;
  private final boolean zzayl;
  private final boolean zzaym;
  
  LocationSettingsStates(int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6, boolean paramBoolean7)
  {
    this.zzCY = paramInt;
    this.zzayg = paramBoolean1;
    this.zzayh = paramBoolean2;
    this.zzayi = paramBoolean3;
    this.zzayj = paramBoolean4;
    this.zzayk = paramBoolean5;
    this.zzayl = paramBoolean6;
    this.zzaym = paramBoolean7;
  }
  
  public static LocationSettingsStates fromIntent(Intent paramIntent)
  {
    return (LocationSettingsStates)zzc.zza(paramIntent, "com.google.android.gms.location.LOCATION_SETTINGS_STATES", CREATOR);
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public int getVersionCode()
  {
    return this.zzCY;
  }
  
  public boolean isBlePresent()
  {
    return this.zzayl;
  }
  
  public boolean isBleUsable()
  {
    return this.zzayi;
  }
  
  public boolean isGpsPresent()
  {
    return this.zzayj;
  }
  
  public boolean isGpsUsable()
  {
    return this.zzayg;
  }
  
  public boolean isLocationPresent()
  {
    return (this.zzayj) || (this.zzayk);
  }
  
  public boolean isLocationUsable()
  {
    return (this.zzayg) || (this.zzayh);
  }
  
  public boolean isNetworkLocationPresent()
  {
    return this.zzayk;
  }
  
  public boolean isNetworkLocationUsable()
  {
    return this.zzayh;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzi.zza(this, paramParcel, paramInt);
  }
  
  public boolean zzus()
  {
    return this.zzaym;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/LocationSettingsStates.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */