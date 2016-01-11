package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.common.internal.zzt.zza;
import java.util.concurrent.TimeUnit;

public final class PlaceRequest
  implements SafeParcelable
{
  public static final Parcelable.Creator<PlaceRequest> CREATOR = new zzl();
  static final long zzazM = TimeUnit.HOURS.toMillis(1L);
  private final int mPriority;
  final int zzCY;
  private final long zzaxU;
  private final long zzaxz;
  private final PlaceFilter zzazN;
  
  public PlaceRequest(int paramInt1, PlaceFilter paramPlaceFilter, long paramLong1, int paramInt2, long paramLong2)
  {
    this.zzCY = paramInt1;
    this.zzazN = paramPlaceFilter;
    this.zzaxU = paramLong1;
    this.mPriority = paramInt2;
    this.zzaxz = paramLong2;
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
      return true;
      if (!(paramObject instanceof PlaceRequest)) {
        return false;
      }
      paramObject = (PlaceRequest)paramObject;
    } while ((zzt.equal(this.zzazN, ((PlaceRequest)paramObject).zzazN)) && (this.zzaxU == ((PlaceRequest)paramObject).zzaxU) && (this.mPriority == ((PlaceRequest)paramObject).mPriority) && (this.zzaxz == ((PlaceRequest)paramObject).zzaxz));
    return false;
  }
  
  public long getExpirationTime()
  {
    return this.zzaxz;
  }
  
  public long getInterval()
  {
    return this.zzaxU;
  }
  
  public int getPriority()
  {
    return this.mPriority;
  }
  
  public int hashCode()
  {
    return zzt.hashCode(new Object[] { this.zzazN, Long.valueOf(this.zzaxU), Integer.valueOf(this.mPriority), Long.valueOf(this.zzaxz) });
  }
  
  public String toString()
  {
    return zzt.zzt(this).zzg("filter", this.zzazN).zzg("interval", Long.valueOf(this.zzaxU)).zzg("priority", Integer.valueOf(this.mPriority)).zzg("expireAt", Long.valueOf(this.zzaxz)).toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzl.zza(this, paramParcel, paramInt);
  }
  
  public PlaceFilter zzuG()
  {
    return this.zzazN;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/PlaceRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */