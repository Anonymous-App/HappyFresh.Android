package com.google.android.gms.location;

import android.os.SystemClock;
import com.google.android.gms.location.internal.ParcelableGeofence;

public abstract interface Geofence
{
  public static final int GEOFENCE_TRANSITION_DWELL = 4;
  public static final int GEOFENCE_TRANSITION_ENTER = 1;
  public static final int GEOFENCE_TRANSITION_EXIT = 2;
  public static final long NEVER_EXPIRE = -1L;
  
  public abstract String getRequestId();
  
  public static final class Builder
  {
    private String zzDK = null;
    private short zzaxA = -1;
    private double zzaxB;
    private double zzaxC;
    private float zzaxD;
    private int zzaxE = 0;
    private int zzaxF = -1;
    private int zzaxy = 0;
    private long zzaxz = Long.MIN_VALUE;
    
    public Geofence build()
    {
      if (this.zzDK == null) {
        throw new IllegalArgumentException("Request ID not set.");
      }
      if (this.zzaxy == 0) {
        throw new IllegalArgumentException("Transitions types not set.");
      }
      if (((this.zzaxy & 0x4) != 0) && (this.zzaxF < 0)) {
        throw new IllegalArgumentException("Non-negative loitering delay needs to be set when transition types include GEOFENCE_TRANSITION_DWELLING.");
      }
      if (this.zzaxz == Long.MIN_VALUE) {
        throw new IllegalArgumentException("Expiration not set.");
      }
      if (this.zzaxA == -1) {
        throw new IllegalArgumentException("Geofence region not set.");
      }
      if (this.zzaxE < 0) {
        throw new IllegalArgumentException("Notification responsiveness should be nonnegative.");
      }
      return new ParcelableGeofence(this.zzDK, this.zzaxy, (short)1, this.zzaxB, this.zzaxC, this.zzaxD, this.zzaxz, this.zzaxE, this.zzaxF);
    }
    
    public Builder setCircularRegion(double paramDouble1, double paramDouble2, float paramFloat)
    {
      this.zzaxA = 1;
      this.zzaxB = paramDouble1;
      this.zzaxC = paramDouble2;
      this.zzaxD = paramFloat;
      return this;
    }
    
    public Builder setExpirationDuration(long paramLong)
    {
      if (paramLong < 0L)
      {
        this.zzaxz = -1L;
        return this;
      }
      this.zzaxz = (SystemClock.elapsedRealtime() + paramLong);
      return this;
    }
    
    public Builder setLoiteringDelay(int paramInt)
    {
      this.zzaxF = paramInt;
      return this;
    }
    
    public Builder setNotificationResponsiveness(int paramInt)
    {
      this.zzaxE = paramInt;
      return this;
    }
    
    public Builder setRequestId(String paramString)
    {
      this.zzDK = paramString;
      return this;
    }
    
    public Builder setTransitionTypes(int paramInt)
    {
      this.zzaxy = paramInt;
      return this;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/Geofence.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */