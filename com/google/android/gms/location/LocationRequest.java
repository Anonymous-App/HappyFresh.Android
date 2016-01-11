package com.google.android.gms.location;

import android.os.Parcel;
import android.os.SystemClock;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzt;

public final class LocationRequest
  implements SafeParcelable
{
  public static final LocationRequestCreator CREATOR = new LocationRequestCreator();
  public static final int PRIORITY_BALANCED_POWER_ACCURACY = 102;
  public static final int PRIORITY_HIGH_ACCURACY = 100;
  public static final int PRIORITY_LOW_POWER = 104;
  public static final int PRIORITY_NO_POWER = 105;
  int mPriority;
  private final int zzCY;
  boolean zzamB;
  long zzaxU;
  long zzaxV;
  int zzaxW;
  float zzaxX;
  long zzaxY;
  long zzaxz;
  
  public LocationRequest()
  {
    this.zzCY = 1;
    this.mPriority = 102;
    this.zzaxU = 3600000L;
    this.zzaxV = 600000L;
    this.zzamB = false;
    this.zzaxz = Long.MAX_VALUE;
    this.zzaxW = Integer.MAX_VALUE;
    this.zzaxX = 0.0F;
    this.zzaxY = 0L;
  }
  
  LocationRequest(int paramInt1, int paramInt2, long paramLong1, long paramLong2, boolean paramBoolean, long paramLong3, int paramInt3, float paramFloat, long paramLong4)
  {
    this.zzCY = paramInt1;
    this.mPriority = paramInt2;
    this.zzaxU = paramLong1;
    this.zzaxV = paramLong2;
    this.zzamB = paramBoolean;
    this.zzaxz = paramLong3;
    this.zzaxW = paramInt3;
    this.zzaxX = paramFloat;
    this.zzaxY = paramLong4;
  }
  
  public static LocationRequest create()
  {
    return new LocationRequest();
  }
  
  private static void zzK(long paramLong)
  {
    if (paramLong < 0L) {
      throw new IllegalArgumentException("invalid interval: " + paramLong);
    }
  }
  
  private static void zzd(float paramFloat)
  {
    if (paramFloat < 0.0F) {
      throw new IllegalArgumentException("invalid displacement: " + paramFloat);
    }
  }
  
  private static void zzgu(int paramInt)
  {
    switch (paramInt)
    {
    case 101: 
    case 103: 
    default: 
      throw new IllegalArgumentException("invalid quality: " + paramInt);
    }
  }
  
  public static String zzgv(int paramInt)
  {
    switch (paramInt)
    {
    case 101: 
    case 103: 
    default: 
      return "???";
    case 100: 
      return "PRIORITY_HIGH_ACCURACY";
    case 102: 
      return "PRIORITY_BALANCED_POWER_ACCURACY";
    case 104: 
      return "PRIORITY_LOW_POWER";
    }
    return "PRIORITY_NO_POWER";
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
      if (!(paramObject instanceof LocationRequest)) {
        return false;
      }
      paramObject = (LocationRequest)paramObject;
    } while ((this.mPriority == ((LocationRequest)paramObject).mPriority) && (this.zzaxU == ((LocationRequest)paramObject).zzaxU) && (this.zzaxV == ((LocationRequest)paramObject).zzaxV) && (this.zzamB == ((LocationRequest)paramObject).zzamB) && (this.zzaxz == ((LocationRequest)paramObject).zzaxz) && (this.zzaxW == ((LocationRequest)paramObject).zzaxW) && (this.zzaxX == ((LocationRequest)paramObject).zzaxX));
    return false;
  }
  
  public long getExpirationTime()
  {
    return this.zzaxz;
  }
  
  public long getFastestInterval()
  {
    return this.zzaxV;
  }
  
  public long getInterval()
  {
    return this.zzaxU;
  }
  
  public long getMaxWaitTime()
  {
    long l2 = this.zzaxY;
    long l1 = l2;
    if (l2 < this.zzaxU) {
      l1 = this.zzaxU;
    }
    return l1;
  }
  
  public int getNumUpdates()
  {
    return this.zzaxW;
  }
  
  public int getPriority()
  {
    return this.mPriority;
  }
  
  public float getSmallestDisplacement()
  {
    return this.zzaxX;
  }
  
  int getVersionCode()
  {
    return this.zzCY;
  }
  
  public int hashCode()
  {
    return zzt.hashCode(new Object[] { Integer.valueOf(this.mPriority), Long.valueOf(this.zzaxU), Long.valueOf(this.zzaxV), Boolean.valueOf(this.zzamB), Long.valueOf(this.zzaxz), Integer.valueOf(this.zzaxW), Float.valueOf(this.zzaxX) });
  }
  
  public LocationRequest setExpirationDuration(long paramLong)
  {
    long l = SystemClock.elapsedRealtime();
    if (paramLong > Long.MAX_VALUE - l) {}
    for (this.zzaxz = Long.MAX_VALUE;; this.zzaxz = (l + paramLong))
    {
      if (this.zzaxz < 0L) {
        this.zzaxz = 0L;
      }
      return this;
    }
  }
  
  public LocationRequest setExpirationTime(long paramLong)
  {
    this.zzaxz = paramLong;
    if (this.zzaxz < 0L) {
      this.zzaxz = 0L;
    }
    return this;
  }
  
  public LocationRequest setFastestInterval(long paramLong)
  {
    zzK(paramLong);
    this.zzamB = true;
    this.zzaxV = paramLong;
    return this;
  }
  
  public LocationRequest setInterval(long paramLong)
  {
    zzK(paramLong);
    this.zzaxU = paramLong;
    if (!this.zzamB) {
      this.zzaxV = ((this.zzaxU / 6.0D));
    }
    return this;
  }
  
  public LocationRequest setMaxWaitTime(long paramLong)
  {
    zzK(paramLong);
    this.zzaxY = paramLong;
    return this;
  }
  
  public LocationRequest setNumUpdates(int paramInt)
  {
    if (paramInt <= 0) {
      throw new IllegalArgumentException("invalid numUpdates: " + paramInt);
    }
    this.zzaxW = paramInt;
    return this;
  }
  
  public LocationRequest setPriority(int paramInt)
  {
    zzgu(paramInt);
    this.mPriority = paramInt;
    return this;
  }
  
  public LocationRequest setSmallestDisplacement(float paramFloat)
  {
    zzd(paramFloat);
    this.zzaxX = paramFloat;
    return this;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Request[").append(zzgv(this.mPriority));
    if (this.mPriority != 105)
    {
      localStringBuilder.append(" requested=");
      localStringBuilder.append(this.zzaxU + "ms");
    }
    localStringBuilder.append(" fastest=");
    localStringBuilder.append(this.zzaxV + "ms");
    if (this.zzaxY > this.zzaxU)
    {
      localStringBuilder.append(" maxWait=");
      localStringBuilder.append(this.zzaxY + "ms");
    }
    if (this.zzaxz != Long.MAX_VALUE)
    {
      long l1 = this.zzaxz;
      long l2 = SystemClock.elapsedRealtime();
      localStringBuilder.append(" expireIn=");
      localStringBuilder.append(l1 - l2 + "ms");
    }
    if (this.zzaxW != Integer.MAX_VALUE) {
      localStringBuilder.append(" num=").append(this.zzaxW);
    }
    localStringBuilder.append(']');
    return localStringBuilder.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    LocationRequestCreator.zza(this, paramParcel, paramInt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/LocationRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */