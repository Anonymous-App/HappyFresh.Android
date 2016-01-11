package com.google.android.gms.location.places;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.common.internal.zzt.zza;
import java.util.Set;

public final class NearbyAlertRequest
  implements SafeParcelable
{
  public static final zze CREATOR = new zze();
  private final int zzCY;
  private final int zzaxy;
  private final NearbyAlertFilter zzazA;
  private final int zzazy;
  @Deprecated
  private final PlaceFilter zzazz;
  
  NearbyAlertRequest(int paramInt1, int paramInt2, int paramInt3, PlaceFilter paramPlaceFilter, NearbyAlertFilter paramNearbyAlertFilter)
  {
    this.zzCY = paramInt1;
    this.zzaxy = paramInt2;
    this.zzazy = paramInt3;
    if (paramNearbyAlertFilter != null) {
      this.zzazA = paramNearbyAlertFilter;
    }
    for (;;)
    {
      this.zzazz = null;
      return;
      if (paramPlaceFilter != null)
      {
        if (zza(paramPlaceFilter)) {
          this.zzazA = NearbyAlertFilter.zza(paramPlaceFilter.getPlaceIds(), paramPlaceFilter.getPlaceTypes(), paramPlaceFilter.zzuI());
        } else {
          this.zzazA = null;
        }
      }
      else {
        this.zzazA = null;
      }
    }
  }
  
  @Deprecated
  public static boolean zza(PlaceFilter paramPlaceFilter)
  {
    return ((paramPlaceFilter.getPlaceTypes() != null) && (!paramPlaceFilter.getPlaceTypes().isEmpty())) || ((paramPlaceFilter.getPlaceIds() != null) && (!paramPlaceFilter.getPlaceIds().isEmpty())) || ((paramPlaceFilter.zzuI() != null) && (!paramPlaceFilter.zzuI().isEmpty()));
  }
  
  public int describeContents()
  {
    zze localzze = CREATOR;
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    do
    {
      return true;
      if (!(paramObject instanceof NearbyAlertRequest)) {
        return false;
      }
      paramObject = (NearbyAlertRequest)paramObject;
    } while ((this.zzaxy == ((NearbyAlertRequest)paramObject).zzaxy) && (this.zzazy == ((NearbyAlertRequest)paramObject).zzazy) && (zzt.equal(this.zzazz, ((NearbyAlertRequest)paramObject).zzazz)) && (zzt.equal(this.zzazA, ((NearbyAlertRequest)paramObject).zzazA)));
    return false;
  }
  
  public int getVersionCode()
  {
    return this.zzCY;
  }
  
  public int hashCode()
  {
    return zzt.hashCode(new Object[] { Integer.valueOf(this.zzaxy), Integer.valueOf(this.zzazy) });
  }
  
  public String toString()
  {
    return zzt.zzt(this).zzg("transitionTypes", Integer.valueOf(this.zzaxy)).zzg("loiteringTimeMillis", Integer.valueOf(this.zzazy)).zzg("nearbyAlertFilter", this.zzazA).toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zze localzze = CREATOR;
    zze.zza(this, paramParcel, paramInt);
  }
  
  public int zzuC()
  {
    return this.zzaxy;
  }
  
  public int zzuF()
  {
    return this.zzazy;
  }
  
  @Deprecated
  public PlaceFilter zzuG()
  {
    return this.zzazz;
  }
  
  public NearbyAlertFilter zzuH()
  {
    return this.zzazA;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/NearbyAlertRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */