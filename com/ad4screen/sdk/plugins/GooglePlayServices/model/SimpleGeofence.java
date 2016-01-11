package com.ad4screen.sdk.plugins.GooglePlayServices.model;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.Geofence.Builder;

public class SimpleGeofence
{
  private long mExpirationDuration;
  private final String mId;
  private final double mLatitude;
  private final double mLongitude;
  private final float mRadius;
  private int mTransitionType;
  
  public SimpleGeofence(String paramString, double paramDouble1, double paramDouble2, float paramFloat, long paramLong, int paramInt)
  {
    this.mId = paramString;
    this.mLatitude = paramDouble1;
    this.mLongitude = paramDouble2;
    this.mRadius = paramFloat;
    this.mExpirationDuration = paramLong;
    this.mTransitionType = paramInt;
  }
  
  public long getExpirationDuration()
  {
    return this.mExpirationDuration;
  }
  
  public String getId()
  {
    return this.mId;
  }
  
  public double getLatitude()
  {
    return this.mLatitude;
  }
  
  public double getLongitude()
  {
    return this.mLongitude;
  }
  
  public float getRadius()
  {
    return this.mRadius;
  }
  
  public int getTransitionType()
  {
    return this.mTransitionType;
  }
  
  public Geofence toGeofence()
  {
    return new Geofence.Builder().setRequestId(getId()).setTransitionTypes(this.mTransitionType).setCircularRegion(getLatitude(), getLongitude(), getRadius()).setExpirationDuration(this.mExpirationDuration).build();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/plugins/GooglePlayServices/model/SimpleGeofence.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */