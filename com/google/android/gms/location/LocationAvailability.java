package com.google.android.gms.location;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzt;

public final class LocationAvailability
  implements SafeParcelable
{
  public static final LocationAvailabilityCreator CREATOR = new LocationAvailabilityCreator();
  private final int zzCY;
  int zzaxQ;
  int zzaxR;
  long zzaxS;
  int zzaxT;
  
  LocationAvailability(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong)
  {
    this.zzCY = paramInt1;
    this.zzaxT = paramInt2;
    this.zzaxQ = paramInt3;
    this.zzaxR = paramInt4;
    this.zzaxS = paramLong;
  }
  
  public static LocationAvailability extractLocationAvailability(Intent paramIntent)
  {
    if (!hasLocationAvailability(paramIntent)) {
      return null;
    }
    return (LocationAvailability)paramIntent.getExtras().getParcelable("com.google.android.gms.location.EXTRA_LOCATION_AVAILABILITY");
  }
  
  public static boolean hasLocationAvailability(Intent paramIntent)
  {
    if (paramIntent == null) {
      return false;
    }
    return paramIntent.hasExtra("com.google.android.gms.location.EXTRA_LOCATION_AVAILABILITY");
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof LocationAvailability)) {}
    do
    {
      return false;
      paramObject = (LocationAvailability)paramObject;
    } while ((this.zzaxT != ((LocationAvailability)paramObject).zzaxT) || (this.zzaxQ != ((LocationAvailability)paramObject).zzaxQ) || (this.zzaxR != ((LocationAvailability)paramObject).zzaxR) || (this.zzaxS != ((LocationAvailability)paramObject).zzaxS));
    return true;
  }
  
  int getVersionCode()
  {
    return this.zzCY;
  }
  
  public int hashCode()
  {
    return zzt.hashCode(new Object[] { Integer.valueOf(this.zzaxT), Integer.valueOf(this.zzaxQ), Integer.valueOf(this.zzaxR), Long.valueOf(this.zzaxS) });
  }
  
  public boolean isLocationAvailable()
  {
    return this.zzaxT < 1000;
  }
  
  public String toString()
  {
    return "LocationAvailability[isLocationAvailable: " + isLocationAvailable() + "]";
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    LocationAvailabilityCreator.zza(this, paramParcel, paramInt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/LocationAvailability.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */